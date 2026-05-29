import { readFileSync, writeFileSync, mkdirSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));
const TOKENS_DIR = resolve(__dirname, '../tokens');
const OUTPUT_DIR = resolve(__dirname, '../output');

// ─── Explicit mapping: Kotlin property → Figma DTCG path ─────────────────────

const SEMANTIC_MAPPING: Array<{ prop: string; figmaPath: string }> = [
  { prop: 'canvas',             figmaPath: 'Canvas.Default' },
  { prop: 'canvasContrast',     figmaPath: 'Canvas.Contrast' },
  { prop: 'coreAccent',         figmaPath: 'Core.Accent' },
  { prop: 'corePrimary',        figmaPath: 'Core.Primary' },
  { prop: 'line',               figmaPath: 'Other.Line' },
  { prop: 'lineOnDark',         figmaPath: 'Other.Line On Dark' },
  { prop: 'overlay',            figmaPath: 'Other.Overlay' },
  { prop: 'scrim',              figmaPath: 'Other.Scrim' },
  { prop: 'statusDangerFill',   figmaPath: 'Surface.Danger Fill' },
  { prop: 'statusDangerSpot',   figmaPath: 'Status.Danger Spot' },
  { prop: 'statusSuccessFill',  figmaPath: 'Surface.Success Fill' },
  { prop: 'statusSuccessSpot',  figmaPath: 'Status.Success Spot' },
  { prop: 'statusWarningFill',  figmaPath: 'Surface.Warning Fill' },
  { prop: 'statusWarningSpot',  figmaPath: 'Status.Warning Spot' },
  { prop: 'surfaceContrast',    figmaPath: 'Surface.Contrast' },
  { prop: 'surfaceDefault',     figmaPath: 'Surface.Default' },
  { prop: 'surfaceElevated',    figmaPath: 'Surface.Elevated' },
  { prop: 'surfaceHero',        figmaPath: 'Surface.Hero' },
  { prop: 'surfaceHighlight',   figmaPath: 'Surface.Highlight' },
  { prop: 'surfaceLowContrast', figmaPath: 'Surface.Low Contrast' },
  { prop: 'surfaceSubtle',      figmaPath: 'Surface.Subtle' },
  { prop: 'surfaceTint',        figmaPath: 'Surface.Tint' },
  { prop: 'textDisabled',       figmaPath: 'Text.Disabled' },
  { prop: 'textDisabledOnDark', figmaPath: 'Text.Disabled on Dark' },
  { prop: 'textError',          figmaPath: 'Text.Error' },
  { prop: 'textHero',           figmaPath: 'Text.🚧 Hero' },
  { prop: 'textOnDark',         figmaPath: 'Text.On Dark' },
  { prop: 'textOnLight',        figmaPath: 'Text.On Light' },
  { prop: 'textPrimary',        figmaPath: 'Text.Primary' },
  { prop: 'textPrimaryInverse', figmaPath: 'Text.Inverse' },
  { prop: 'textSecondary',      figmaPath: 'Text.Secondary' },
  { prop: 'textSuccess',        figmaPath: 'Text.Success' },
];

// ─── DTCG helpers ─────────────────────────────────────────────────────────────

type DtcgTree = { [key: string]: DtcgTree | { $value: unknown; $type?: string } };

function flattenTree(node: DtcgTree, prefix = ''): Map<string, string> {
  const map = new Map<string, string>();
  for (const [key, val] of Object.entries(node)) {
    if (key.startsWith('$')) continue;
    const path = prefix ? `${prefix}.${key}` : key;
    if (val && typeof val === 'object' && '$value' in val) {
      map.set(path, String((val as any).$value));
    } else if (val && typeof val === 'object') {
      for (const [k, v] of flattenTree(val as DtcgTree, path)) map.set(k, v);
    }
  }
  return map;
}

function resolveRef(ref: string, ...lookups: Map<string, string>[]): string {
  let current = ref;
  for (let depth = 0; depth < 20; depth++) {
    const match = current.match(/^\{(.+)\}$/);
    if (!match) return current; // it's a literal value
    const key = match[1];
    let resolved: string | undefined;
    for (const lookup of lookups) {
      resolved = lookup.get(key);
      if (resolved !== undefined) break;
    }
    if (resolved === undefined) throw new Error(`Unresolved reference: ${current}`);
    current = resolved;
  }
  throw new Error(`Circular reference: ${ref}`);
}

// ─── Color conversion: #RRGGBB[AA] → Color(0xAARRGGBB) ───────────────────────

function hexToCompose(hex: string): string {
  const h = hex.replace('#', '').toUpperCase();
  if (h.length === 6) return `Color(0xFF${h})`;
  if (h.length === 8) return `Color(0x${h.slice(6)}${h.slice(0, 6)})`;
  throw new Error(`Invalid hex color: '${hex}'`);
}

// ─── Generate BpkColors.kt ────────────────────────────────────────────────────

const LICENSE_HEADER = `/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */`;

export function buildColors(outputDir: string = OUTPUT_DIR): void {
  const primitives = flattenTree(
    JSON.parse(readFileSync(`${TOKENS_DIR}/primitives.json`, 'utf-8'))
  );
  const lightTokens = flattenTree(
    JSON.parse(readFileSync(`${TOKENS_DIR}/backpack.light.json`, 'utf-8'))
  );
  const darkTokens = flattenTree(
    JSON.parse(readFileSync(`${TOKENS_DIR}/backpack.dark.json`, 'utf-8'))
  );

  // Resolve each mapped token to its final hex value
  const resolved = SEMANTIC_MAPPING.map(({ prop, figmaPath }) => {
    const rawLight = lightTokens.get(figmaPath);
    const rawDark = darkTokens.get(figmaPath);

    if (rawLight === undefined) throw new Error(`Missing light token: ${figmaPath}`);
    if (rawDark === undefined) throw new Error(`Missing dark token: ${figmaPath}`);

    const lightHex = resolveRef(rawLight, primitives, lightTokens);
    const darkHex  = resolveRef(rawDark,  primitives, darkTokens);

    return { prop, lightHex, darkHex };
  });

  const indent = '            ';

  // Constructor parameters
  const params = resolved
    .map(({ prop }) => `    public val ${prop}: Color,`)
    .join('\n');

  // light() default args
  const lightArgs = resolved
    .map(({ prop, lightHex }) => `${indent}${prop}: Color = ${hexToCompose(lightHex)},`)
    .join('\n');

  // dark() default args
  const darkArgs = resolved
    .map(({ prop, darkHex }) => `${indent}${prop}: Color = ${hexToCompose(darkHex)},`)
    .join('\n');

  // Constructor call assignments (shared by both light and dark)
  const assignments = resolved
    .map(({ prop }) => `${indent}${prop} = ${prop},`)
    .join('\n');

  const output = `${LICENSE_HEADER}

// Auto-generated: do not edit
@file:Suppress("RedundantVisibilityModifier", "unused")

package net.skyscanner.backpack.compose.tokens

import androidx.compose.ui.graphics.Color

public class BpkColors private constructor(
    public val isLight: Boolean,
${params}
) {
    internal companion object {
        public fun light(
${lightArgs}
        ): BpkColors = BpkColors(
            isLight = true,
${assignments}
        )

        public fun dark(
${darkArgs}
        ): BpkColors = BpkColors(
            isLight = false,
${assignments}
        )
    }
}
`;

  mkdirSync(`${outputDir}/compose`, { recursive: true });
  writeFileSync(`${outputDir}/compose/BpkColors.kt`, output);
  console.log(`✔︎ ${outputDir}/compose/BpkColors.kt`);
}
