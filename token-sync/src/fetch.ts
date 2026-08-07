/**
 * Fetch design tokens from the Figma Variables API and write them in the
 * bpk-foundations-android JSON format that the existing buildSrc Kotlin
 * pipeline already understands — no DTCG, no Style Dictionary required.
 *
 * Output: token-sync/tokens/base.raw.android.json
 *
 * Run: npm run tokens:fetch
 */

import { createRequire } from 'module';
import { mkdirSync, writeFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));

const require = createRequire(import.meta.url);
const dotenv = require('dotenv');
dotenv.config({ path: resolve(__dirname, '../.env') });

const FIGMA_TOKEN = process.env.FIGMA_TOKEN;
const FIGMA_FILE_KEY = process.env.FIGMA_FILE_KEY ?? 'KXf2gHNLDe2cXWUoHl4cTX';
const OUTPUT_DIR = process.env.TOKENS_DIR ?? resolve(__dirname, '../tokens');

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
  scopes: string[];
}

interface FigmaCollection {
  id: string;
  name: string;
  modes: Array<{ modeId: string; name: string }>;
  variableIds: string[];
}

// ─── bpk-foundations-android JSON types ──────────────────────────────────────

interface BpkProp {
  type: 'color' | 'size' | 'font-size' | 'letter-spacing' | 'duration' | 'string' | 'font';
  category: string;
  value: string;
  originalValue?: string;
  name: string;
  darkValue?: string;
  originalDarkValue?: string;
  deprecated?: boolean;
}

interface BpkFoundationsJson {
  aliases: Record<string, string>;
  props: Record<string, BpkProp>;
  propKeys: string[];
}

// ─── Helpers ─────────────────────────────────────────────────────────────────

function isFigmaAlias(v: FigmaValue): v is FigmaAlias {
  return typeof v === 'object' && v !== null && (v as FigmaAlias).type === 'VARIABLE_ALIAS';
}

function isFigmaColor(v: FigmaValue): v is FigmaColor {
  return typeof v === 'object' && v !== null && 'r' in v;
}

function figmaColorToHex({ r, g, b, a }: FigmaColor): string {
  const hex = (n: number) => Math.round(n * 255).toString(16).padStart(2, '0');
  return `#${hex(r)}${hex(g)}${hex(b)}${hex(a)}`;
}

function figmaNameToKey(name: string): string {
  return name
    .replace(/[^a-zA-Z0-9/]/g, '_')
    .replace(/\//g, '_')
    .replace(/_+/g, '_')
    .replace(/^_|_$/g, '')
    .toUpperCase();
}

function findCollection(collections: FigmaCollection[], name: string): FigmaCollection | undefined {
  return collections
    .filter(c => c.name === name)
    .sort((a, b) => b.variableIds.length - a.variableIds.length)[0];
}

function getModeId(collection: FigmaCollection, preferred: string[]): string {
  const found = collection.modes.find(m => preferred.includes(m.name));
  return found?.modeId ?? collection.modes[0].modeId;
}

// Resolve a Figma value — follows one level of alias to get the primitive value
// and returns both the resolved hex/number and the original reference key.
function resolveValue(
  raw: FigmaValue,
  variables: Record<string, FigmaVariable>,
  primitivesModeId: string,
): { value: string; originalValue: string } | null {
  if (isFigmaAlias(raw)) {
    const target = variables[raw.id];
    if (!target) return null;
    const aliasKey = figmaNameToKey(target.name);
    const targetRaw = target.valuesByMode[primitivesModeId] ??
      target.valuesByMode[Object.keys(target.valuesByMode)[0]];
    if (!targetRaw || isFigmaAlias(targetRaw)) {
      // Two-level alias — resolve further
      if (isFigmaAlias(targetRaw)) {
        return resolveValue(targetRaw, variables, primitivesModeId);
      }
      return null;
    }
    if (isFigmaColor(targetRaw)) {
      return { value: figmaColorToHex(targetRaw), originalValue: `{!${aliasKey}}` };
    }
    return { value: String(targetRaw), originalValue: `{!${aliasKey}}` };
  }
  if (isFigmaColor(raw)) {
    return { value: figmaColorToHex(raw), originalValue: figmaColorToHex(raw) };
  }
  return { value: String(raw), originalValue: String(raw) };
}

// ─── Main ─────────────────────────────────────────────────────────────────────

async function main(): Promise<void> {
  if (!FIGMA_TOKEN) {
    console.error('Error: FIGMA_TOKEN is not set.');
    console.error('Copy token-sync/.env.example to token-sync/.env and add your token.');
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
    console.log(`  • "${c.name}" — ${c.variableIds.length} vars, modes: [${modes}]`);
  }

  const props: Record<string, BpkProp> = {};
  const aliases: Record<string, string> = {};

  // ── Primitives (Colours, Spacing, Radius, Border, Type etc.) ─────────────────
  const primColl = findCollection(collections, 'Primitives');
  if (!primColl) { console.warn('⚠  No Primitives collection found'); process.exit(1); }
  const primModeId = getModeId(primColl, ['Value', 'Hex']);

  // Category mapping: Figma top-level group → bpk-foundations category + prop type
  const PRIMITIVES_CATEGORY_MAP: Record<string, { category: string; type: BpkProp['type'] }> = {
    'Spacing':       { category: 'spacings',       type: 'size' },
    'Radius':        { category: 'radii',           type: 'size' },
    'Border':        { category: 'borders',         type: 'size' },
    'Heights':       { category: 'spacings',        type: 'size' },
    'Modal':         { category: 'spacings',        type: 'size' },
    'Type/Size':     { category: 'typesettings',    type: 'font-size' },
    'Type/Line-height': { category: 'typesettings', type: 'size' },
    'Type/Letter-spacing': { category: 'letter-spacings', type: 'letter-spacing' },
    'Type/Weight':   { category: 'font-weights',   type: 'string' },
    'Type/Family':   { category: 'typesettings',   type: 'font' },
  };

  for (const varId of primColl.variableIds) {
    const variable = variables[varId];
    if (!variable) continue;
    const raw = variable.valuesByMode[primModeId];
    if (raw === undefined || isFigmaAlias(raw)) continue;

    const key = figmaNameToKey(variable.name);
    const value = isFigmaColor(raw) ? figmaColorToHex(raw) : String(raw);

    // Find matching category
    let mappedCategory: { category: string; type: BpkProp['type'] } | undefined;
    for (const [prefix, cat] of Object.entries(PRIMITIVES_CATEGORY_MAP)) {
      if (variable.name.startsWith(prefix + '/') || variable.name === prefix) {
        mappedCategory = cat;
        break;
      }
    }

    if (mappedCategory) {
      props[key] = { type: mappedCategory.type, category: mappedCategory.category, value, name: key };
    }

    // Always add to aliases map so semantic tokens can reference them
    aliases[key] = value;
  }

  console.log(`✓ Primitives: ${primColl.variableIds.length} variables processed`);

  // ── Semantic tokens (Backpack 368 — colors + typography) ─────────────────────
  const backpackColl = findCollection(collections, 'Backpack');
  if (!backpackColl) { console.warn('⚠  No Backpack collection found'); process.exit(1); }
  const lightModeId = getModeId(backpackColl, ['Day', 'Light']);
  const darkModeId  = getModeId(backpackColl, ['Night', 'Dark']);

  // Semantic color group → bpk-foundations category
  const SEMANTIC_COLOR_GROUPS: Record<string, string> = {
    'Canvas':    'canvas-colors',
    'Core':      'core-colors',
    'Text':      'text-colors',
    'Surface':   'surface-colors',
    'Status':    'status-colors',
    'Other':     'line-colors',
  };

  // Typography property suffix → bpk-foundations category + type
  const TYPOGRAPHY_SUFFIX_MAP: Record<string, { category: string; type: BpkProp['type'] }> = {
    'Size':           { category: 'typesettings',   type: 'font-size' },
    'Line-height':    { category: 'typesettings',   type: 'size' },
    'Letter-spacing': { category: 'letter-spacings', type: 'letter-spacing' },
    'Weight':         { category: 'font-weights',   type: 'string' },
    'Family':         { category: 'typesettings',   type: 'font' },
  };

  // Animation duration tokens — these aren't in Figma variables so we keep the
  // hardcoded values from the existing foundations package
  const ANIMATION_TOKENS: Record<string, number> = {
    ANIMATION_DURATION_XS:   50,
    ANIMATION_DURATION_SM:   200,
    ANIMATION_DURATION_BASE: 400,
  };
  for (const [key, ms] of Object.entries(ANIMATION_TOKENS)) {
    props[key] = { type: 'duration', category: 'animations', value: `${ms}ms`, name: key };
  }

  // Elevation tokens — also not in Figma variables, keep hardcoded
  const ELEVATION_TOKENS: Record<string, number> = {
    ELEVATION_XS: 1, ELEVATION_SM: 2, ELEVATION_BASE: 4,
    ELEVATION_LG: 8, ELEVATION_XL: 16, ELEVATION_XXL: 24,
  };
  for (const [key, val] of Object.entries(ELEVATION_TOKENS)) {
    props[key] = { type: 'size', category: 'elevation', value: String(val), name: key };
  }

  let semanticCount = 0;
  let typographyCount = 0;

  for (const varId of backpackColl.variableIds) {
    const variable = variables[varId];
    if (!variable) continue;

    const lightRaw = variable.valuesByMode[lightModeId];
    const darkRaw  = variable.valuesByMode[darkModeId];
    if (lightRaw === undefined) continue;

    const key = figmaNameToKey(variable.name);
    const topGroup = variable.name.split('/')[0].trim();
    const lastSegment = variable.name.split('/').pop()?.trim() ?? '';

    // ── Semantic colors ───────────────────────────────────────────────────────
    if (variable.resolvedType === 'COLOR' && SEMANTIC_COLOR_GROUPS[topGroup]) {
      const category = SEMANTIC_COLOR_GROUPS[topGroup];
      const light = resolveValue(lightRaw, variables, primModeId);
      if (!light) continue;

      const prop: BpkProp = {
        type: 'color',
        category,
        value: light.value,
        originalValue: light.originalValue,
        name: key,
      };

      if (darkRaw !== undefined) {
        const dark = resolveValue(darkRaw, variables, primModeId);
        if (dark) {
          prop.darkValue = dark.value;
          prop.originalDarkValue = dark.originalValue;
        }
      }

      props[key] = prop;
      semanticCount++;
      continue;
    }

    // ── Typography tokens ─────────────────────────────────────────────────────
    if (topGroup === 'Typography') {
      const mapped = TYPOGRAPHY_SUFFIX_MAP[lastSegment];
      if (!mapped) continue;

      const light = resolveValue(lightRaw, variables, primModeId);
      if (!light) continue;

      // For letter-spacing, convert from px (Figma) to em ratio
      // Figma stores letter-spacing in px; foundations uses em (relative to font size)
      // We map tight/loose values directly
      let value = light.value;
      if (mapped.type === 'letter-spacing') {
        const numVal = parseFloat(value);
        if (!isNaN(numVal)) {
          // Convert px offset to em: divide by a reference size of 16
          const emVal = Math.round((numVal / 16) * 100) / 100;
          value = String(emVal === 0 ? 0 : emVal);
        }
      }

      props[key] = {
        type: mapped.type,
        category: mapped.category,
        value,
        originalValue: light.originalValue,
        name: key,
      };
      typographyCount++;
      continue;
    }
  }

  console.log(`✓ Semantic colors: ${semanticCount} tokens`);
  console.log(`✓ Typography: ${typographyCount} tokens`);
  console.log(`✓ Animations: ${Object.keys(ANIMATION_TOKENS).length} tokens (hardcoded)`);
  console.log(`✓ Elevation: ${Object.keys(ELEVATION_TOKENS).length} tokens (hardcoded)`);

  // ── Write output ──────────────────────────────────────────────────────────────
  const output: BpkFoundationsJson = {
    aliases,
    props,
    propKeys: Object.keys(props),
  };

  mkdirSync(OUTPUT_DIR, { recursive: true });
  const outPath = `${OUTPUT_DIR}/base.raw.android.json`;
  writeFileSync(outPath, JSON.stringify(output, null, 2) + '\n');

  console.log(`\n✓ Written ${Object.keys(props).length} total tokens to ${outPath}`);
}

main().catch(err => {
  console.error(err);
  process.exit(1);
});
