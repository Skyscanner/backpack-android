import { createRequire } from 'module';
import { mkdirSync, writeFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));

// Load .env from token-sync/ root
const require = createRequire(import.meta.url);
const dotenv = require('dotenv');
dotenv.config({ path: resolve(__dirname, '../.env') });

// ─── Config ──────────────────────────────────────────────────────────────────

const FIGMA_TOKEN = process.env.FIGMA_TOKEN;
const FIGMA_FILE_KEY = process.env.FIGMA_FILE_KEY ?? 'KXf2gHNLDe2cXWUoHl4cTX';
// Allow callers (e.g. sync.ts) to override the output dir via env var
const OUTPUT_DIR = process.env.TOKENS_DIR ?? resolve(__dirname, '../tokens');

// Names of the Figma variable collections to target
const PRIMITIVES_COLLECTION = 'Primitives';
const SEMANTIC_COLLECTION = 'Backpack';
// Map Figma mode names → output file suffixes
const MODE_MAP: Record<string, string> = { Day: 'light', Night: 'dark', Light: 'light', Dark: 'dark' };

// ─── Figma API types ──────────────────────────────────────────────────────────

interface FigmaColor { r: number; g: number; b: number; a: number }
interface FigmaAlias { type: 'VARIABLE_ALIAS'; id: string }
type FigmaValue = number | string | boolean | FigmaColor | FigmaAlias;

interface FigmaVariable {
  id: string;
  name: string;
  resolvedType: 'COLOR' | 'FLOAT' | 'STRING' | 'BOOLEAN';
  variableCollectionId: string;
  valuesByMode: Record<string, FigmaValue>;
}

interface FigmaCollection {
  id: string;
  name: string;
  modes: Array<{ modeId: string; name: string }>;
  variableIds: string[];
}

// ─── DTCG types ───────────────────────────────────────────────────────────────

type DTCGTokenType = 'color' | 'dimension' | 'fontFamily' | 'fontWeight' | 'number' | 'string' | 'boolean';
interface DTCGToken { $type: DTCGTokenType; $value: string | number | boolean }
type DTCGTree = { [key: string]: DTCGTree | DTCGToken };

// ─── Helpers ──────────────────────────────────────────────────────────────────

function isFigmaAlias(v: FigmaValue): v is FigmaAlias {
  return typeof v === 'object' && v !== null && (v as FigmaAlias).type === 'VARIABLE_ALIAS';
}

function isFigmaColor(v: FigmaValue): v is FigmaColor {
  return typeof v === 'object' && v !== null && 'r' in v;
}

function figmaColorToHex({ r, g, b, a }: FigmaColor): string {
  const hex = (n: number) => Math.round(n * 255).toString(16).padStart(2, '0');
  return a < 0.9999 ? `#${hex(r)}${hex(g)}${hex(b)}${hex(a)}` : `#${hex(r)}${hex(g)}${hex(b)}`;
}

function setByPath(tree: DTCGTree, path: string[], token: DTCGToken): void {
  let node = tree;
  for (let i = 0; i < path.length - 1; i++) {
    const key = path[i];
    if (!(key in node)) node[key] = {} as DTCGTree;
    node = node[key] as DTCGTree;
  }
  node[path[path.length - 1]] = token;
}

function nameToDtcgPath(name: string): string[] {
  return name.split('/').map(s => s.trim());
}

function inferType(resolvedType: FigmaVariable['resolvedType']): DTCGTokenType {
  switch (resolvedType) {
    case 'COLOR': return 'color';
    case 'FLOAT': return 'dimension';
    case 'BOOLEAN': return 'boolean';
    default: return 'string';
  }
}

// ─── Build functions ──────────────────────────────────────────────────────────

function buildPrimitivesTree(
  collection: FigmaCollection,
  variables: Record<string, FigmaVariable>,
): DTCGTree {
  const tree: DTCGTree = {};
  const mode = collection.modes[0];

  for (const varId of collection.variableIds) {
    const variable = variables[varId];
    if (!variable) continue;

    const rawValue = variable.valuesByMode[mode.modeId];
    if (rawValue === undefined || isFigmaAlias(rawValue)) continue;

    let value: string | number | boolean;
    if (isFigmaColor(rawValue)) {
      value = figmaColorToHex(rawValue);
    } else {
      value = rawValue as number | string | boolean;
    }

    setByPath(tree, nameToDtcgPath(variable.name), {
      $type: inferType(variable.resolvedType),
      $value: value,
    });
  }

  return tree;
}

function buildSemanticTree(
  collection: FigmaCollection,
  modeId: string,
  variables: Record<string, FigmaVariable>,
): DTCGTree {
  const tree: DTCGTree = {};

  for (const varId of collection.variableIds) {
    const variable = variables[varId];
    if (!variable) continue;

    const rawValue = variable.valuesByMode[modeId];
    if (rawValue === undefined) continue;

    let value: string | number | boolean;

    if (isFigmaAlias(rawValue)) {
      // Preserve as DTCG reference: {Group.Token.Name}
      const aliasedVar = variables[rawValue.id];
      if (!aliasedVar) {
        console.warn(`  ⚠  Unresolved alias in "${variable.name}", skipping`);
        continue;
      }
      value = `{${nameToDtcgPath(aliasedVar.name).join('.')}}`;
    } else if (isFigmaColor(rawValue)) {
      value = figmaColorToHex(rawValue);
    } else {
      value = rawValue as number | string | boolean;
    }

    setByPath(tree, nameToDtcgPath(variable.name), {
      $type: inferType(variable.resolvedType),
      $value: value,
    });
  }

  return tree;
}

// ─── Main ─────────────────────────────────────────────────────────────────────

async function main(): Promise<void> {
  if (!FIGMA_TOKEN) {
    console.error('Error: FIGMA_TOKEN is not set.');
    console.error('Copy token-sync/.env.example to token-sync/.env and add your Figma token.');
    process.exit(1);
  }

  console.log(`Fetching variables from Figma file: ${FIGMA_FILE_KEY}`);

  const res = await fetch(
    `https://api.figma.com/v1/files/${encodeURIComponent(FIGMA_FILE_KEY)}/variables/local`,
    { headers: { 'X-Figma-Token': FIGMA_TOKEN, Accept: 'application/json' } },
  );

  if (!res.ok) {
    const body = await res.text();
    console.error(`Figma API error ${res.status}: ${body}`);
    process.exit(1);
  }

  const { meta } = (await res.json()) as {
    meta: {
      variables: Record<string, FigmaVariable>;
      variableCollections: Record<string, FigmaCollection>;
    };
  };

  const { variables, variableCollections } = meta;
  const collections = Object.values(variableCollections);

  console.log(`\nFound ${collections.length} collection(s):`);
  for (const c of collections) {
    const modes = c.modes.map(m => m.name).join(', ');
    console.log(`  • "${c.name}" — ${c.variableIds.length} variables, modes: [${modes}]`);
  }
  console.log(`Total variables: ${Object.keys(variables).length}\n`);

  mkdirSync(OUTPUT_DIR, { recursive: true });

  // When multiple collections share the same name, pick the largest (most variables).
  const findCollection = (name: string): FigmaCollection | undefined =>
    collections
      .filter(c => c.name === name)
      .sort((a, b) => b.variableIds.length - a.variableIds.length)[0];

  // ── Primitives ──
  const primitivesColl = findCollection(PRIMITIVES_COLLECTION);
  if (primitivesColl) {
    const tree = buildPrimitivesTree(primitivesColl, variables);
    const out = `${OUTPUT_DIR}/primitives.json`;
    writeFileSync(out, JSON.stringify(tree, null, 2) + '\n');
    const count = primitivesColl.variableIds.length;
    console.log(`✓ primitives.json  (${count} variables)`);
  } else {
    console.warn(`⚠  No collection named "${PRIMITIVES_COLLECTION}" found — skipping primitives.json`);
    console.warn(`   Available: ${collections.map(c => c.name).join(', ')}`);
  }

  // ── Semantic (light + dark) ──
  const semanticColl = findCollection(SEMANTIC_COLLECTION);
  if (semanticColl) {
    for (const mode of semanticColl.modes) {
      const suffix = MODE_MAP[mode.name] ?? mode.name.toLowerCase();
      const tree = buildSemanticTree(semanticColl, mode.modeId, variables);
      const out = `${OUTPUT_DIR}/backpack.${suffix}.json`;
      writeFileSync(out, JSON.stringify(tree, null, 2) + '\n');
      const count = semanticColl.variableIds.length;
      console.log(`✓ backpack.${suffix}.json  (${count} variables, mode: ${mode.name})`);
    }
  } else {
    console.warn(`⚠  No collection named "${SEMANTIC_COLLECTION}" found — skipping semantic tokens`);
    console.warn(`   Available: ${collections.map(c => c.name).join(', ')}`);
  }

  console.log(`\nOutput: ${OUTPUT_DIR}/`);
}

main().catch(err => {
  console.error(err);
  process.exit(1);
});
