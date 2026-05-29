/**
 * Token build pipeline:
 *
 *   tokens/primitives.json          ─┐
 *   tokens/backpack.light.json       ├─ Style Dictionary ─► .kt + .xml
 *   tokens/backpack.dark.json       ─┘
 *
 * Style Dictionary resolves all {reference} values from the DTCG JSON files.
 * Custom formatters then emit Kotlin Compose objects and Android XML resources.
 *
 * Run: npm run tokens:build
 */

import StyleDictionary from 'style-dictionary';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';
import { mkdirSync } from 'fs';

const __dirname = dirname(fileURLToPath(import.meta.url));
// Allow sync.ts to point at a temp directory so JSON files are never stored
const TOKENS_DIR = process.env.TOKENS_DIR ?? resolve(__dirname, '../tokens');
const OUTPUT_DIR = process.env.OUTPUT_DIR ?? resolve(__dirname, '../output');

mkdirSync(`${OUTPUT_DIR}/compose`, { recursive: true });
mkdirSync(`${OUTPUT_DIR}/xml`, { recursive: true });

// ─── Shared utilities ─────────────────────────────────────────────────────────

function toPascalCase(str: string): string {
  return str
    .split(/[\s\-_]+/)
    .filter(Boolean)
    .map(w => w.charAt(0).toUpperCase() + w.slice(1).toLowerCase())
    .join('');
}

// Figma stores hex as #RRGGBB or #RRGGBBAA.
// Compose Color wants 0xAARRGGBB.
function hexToCompose(hex: string): string {
  const h = hex.replace('#', '').toUpperCase();
  if (h.length === 6) return `Color(0xFF${h})`;
  if (h.length === 8) return `Color(0x${h.slice(6)}${h.slice(0, 6)})`;
  throw new Error(`Unexpected hex color format: '${hex}'`);
}

// Semantic token groups that go into BpkColors
const SEMANTIC_GROUPS = new Set(['Text', 'Surface', 'Canvas', 'Core', 'Status', 'Other']);

// Whether a token is a semantic (not primitive) color for BpkColors
function isSemanticColor(token: any): boolean {
  return (
    (token.$type ?? token.type) === 'color' &&
    SEMANTIC_GROUPS.has(token.path[0])
  );
}

// Kotlin property name → Figma DTCG token path
const PROP_TO_FIGMA_PATH: Record<string, string> = {
  canvas:             'Canvas.Default',
  canvasContrast:     'Canvas.Contrast',
  coreAccent:         'Core.Accent',
  corePrimary:        'Core.Primary',
  line:               'Other.Line',
  lineOnDark:         'Other.Line On Dark',
  overlay:            'Other.Overlay',
  scrim:              'Other.Scrim',
  statusDangerFill:   'Surface.Danger Fill',
  statusDangerSpot:   'Status.Danger Spot',
  statusSuccessFill:  'Surface.Success Fill',
  statusSuccessSpot:  'Status.Success Spot',
  statusWarningFill:  'Surface.Warning Fill',
  statusWarningSpot:  'Status.Warning Spot',
  surfaceContrast:    'Surface.Contrast',
  surfaceDefault:     'Surface.Default',
  surfaceElevated:    'Surface.Elevated',
  surfaceHero:        'Surface.Hero',
  surfaceHighlight:   'Surface.Highlight',
  surfaceLowContrast: 'Surface.Low Contrast',
  surfaceSubtle:      'Surface.Subtle',
  surfaceTint:        'Surface.Tint',
  textDisabled:       'Text.Disabled',
  textDisabledOnDark: 'Text.Disabled on Dark',
  textError:          'Text.Error',
  textHero:           'Text.🚧 Hero',
  textOnDark:         'Text.On Dark',
  textOnLight:        'Text.On Light',
  textPrimary:        'Text.Primary',
  textPrimaryInverse: 'Text.Inverse',
  textSecondary:      'Text.Secondary',
  textSuccess:        'Text.Success',
};

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

const FILE_HEADER = `// Auto-generated: do not edit\n@file:Suppress("RedundantVisibilityModifier", "unused")`;

// ─── Step 1: primitives.json → dimension tokens (.kt + .xml) ─────────────────
// Style Dictionary reads primitives.json and resolves values.
// Custom formats emit Kotlin objects and Android XML <dimen> resources.

const sdDimensions = new StyleDictionary({
  source: [`${TOKENS_DIR}/primitives.json`],
  platforms: {

    'compose-spacing': {
      buildPath: `${OUTPUT_DIR}/compose/`,
      files: [{ destination: 'BpkSpacing.kt',      format: 'kotlin/compose-dp-object', filter: (t: any) => t.path[0] === 'Spacing', options: { namespace: 'BpkSpacing'      } }],
    },
    'compose-radius': {
      buildPath: `${OUTPUT_DIR}/compose/`,
      files: [{ destination: 'BpkBorderRadius.kt', format: 'kotlin/compose-dp-object', filter: (t: any) => t.path[0] === 'Radius',  options: { namespace: 'BpkBorderRadius' } }],
    },
    'compose-border': {
      buildPath: `${OUTPUT_DIR}/compose/`,
      files: [{ destination: 'BpkBorderSize.kt',   format: 'kotlin/compose-dp-object', filter: (t: any) => t.path[0] === 'Border',  options: { namespace: 'BpkBorderSize'   } }],
    },

    'xml-spacing': {
      buildPath: `${OUTPUT_DIR}/xml/`,
      files: [{ destination: 'dimensions.spacing.xml', format: 'android/xml-dimensions', filter: (t: any) => t.path[0] === 'Spacing', options: { prefix: 'bpkSpacing'       } }],
    },
    'xml-radius': {
      buildPath: `${OUTPUT_DIR}/xml/`,
      files: [{ destination: 'radii.xml',              format: 'android/xml-dimensions', filter: (t: any) => t.path[0] === 'Radius',  options: { prefix: 'bpkBorderRadius'  } }],
    },
  },
});

// Generates: public object BpkSpacing { public val Base: Dp = 16.dp }
sdDimensions.registerFormat({
  name: 'kotlin/compose-dp-object',
  format: async ({ dictionary, options }: any) => {
    const { namespace } = options as { namespace: string };
    const props = dictionary.allTokens
      .map((t: any) => `    public val ${toPascalCase(t.path[t.path.length - 1])}: Dp = ${Number(t.$value ?? t.value)}.dp`)
      .join('\n\n');
    return [LICENSE_HEADER, '', FILE_HEADER, '', 'package net.skyscanner.backpack.compose.tokens', '', 'import androidx.compose.ui.unit.Dp', 'import androidx.compose.ui.unit.dp', '', `public object ${namespace} {`, props, '}', ''].join('\n');
  },
});

// Generates: <dimen name="bpkSpacingBase">16dp</dimen>
sdDimensions.registerFormat({
  name: 'android/xml-dimensions',
  format: async ({ dictionary, options }: any) => {
    const { prefix } = options as { prefix: string };
    const items = dictionary.allTokens
      .map((t: any) => `    <dimen name="${prefix}${toPascalCase(t.path[t.path.length - 1])}">${Number(t.$value ?? t.value)}dp</dimen>`)
      .join('\n');
    return ['<?xml version="1.0" encoding="utf-8"?>', '<!--', "  Backpack for Android - Skyscanner's Design System", '  Auto-generated: do not edit', '-->', '<resources>', items, '</resources>', ''].join('\n');
  },
});

// ─── Steps 2 + 3: semantic tokens → BpkColors.kt ─────────────────────────────
// Style Dictionary resolves DTCG references across primitives + semantic files.
// We run it twice (light, dark) using a shared in-memory capture format,
// then a single format generates the full BpkColors.kt from both resolved sets.

// Shared state: SD populates these maps, then the Kotlin generator reads them.
const resolvedLight = new Map<string, string>(); // 'Canvas.Default' → '#ffffff'
const resolvedDark  = new Map<string, string>();

function makeCaptureFormat(target: Map<string, string>) {
  return async ({ dictionary }: any) => {
    for (const token of dictionary.allTokens) {
      if (!isSemanticColor(token)) continue;
      const path = token.path.join('.');
      const value = token.$value ?? token.value;
      if (typeof value === 'string' && value.startsWith('#')) {
        target.set(path, value);
      }
    }
    return ''; // no file output — data captured in memory
  };
}

// Suppress errors for the 4 external-library tokens that can't be resolved.
const semanticLogConfig = {
  log: {
    errors: { brokenReferences: 'warn' },
    warnings: 'warn',
    // emoji token names (🚧 Hero, 💀 DEPRECATED) normalize to same slug — harmless
    verbosity: 'silent',
  },
} as const;

// Light: primitives.json + backpack.light.json → resolvedLight map
const sdLight = new StyleDictionary({
  source: [`${TOKENS_DIR}/primitives.json`, `${TOKENS_DIR}/backpack.light.json`],
  ...semanticLogConfig,
  platforms: {
    'capture-light': {
      buildPath: `${OUTPUT_DIR}/compose/`,
      files: [{ destination: '.light-capture', format: 'capture/light', filter: isSemanticColor }],
    },
  },
});
sdLight.registerFormat({ name: 'capture/light', format: makeCaptureFormat(resolvedLight) });

// Dark: primitives.json + backpack.dark.json → resolvedDark map
const sdDark = new StyleDictionary({
  source: [`${TOKENS_DIR}/primitives.json`, `${TOKENS_DIR}/backpack.dark.json`],
  ...semanticLogConfig,
  platforms: {
    'capture-dark': {
      buildPath: `${OUTPUT_DIR}/compose/`,
      files: [{ destination: '.dark-capture', format: 'capture/dark', filter: isSemanticColor }],
    },
  },
});
sdDark.registerFormat({ name: 'capture/dark', format: makeCaptureFormat(resolvedDark) });

// BpkColors format: runs after both captures, generates the full Kotlin class.
// Registered on sdLight but uses data from both maps.
sdLight.registerFormat({
  name: 'kotlin/bpk-colors',
  format: async () => {
    const pad = '            '; // 12 spaces — matches existing file style

    const params = Object.keys(PROP_TO_FIGMA_PATH)
      .map(prop => `    public val ${prop}: Color,`)
      .join('\n');

    function factoryArgs(map: Map<string, string>, theme: 'light' | 'dark') {
      return Object.entries(PROP_TO_FIGMA_PATH).map(([prop, figmaPath]) => {
        const hex = map.get(figmaPath);
        if (!hex) throw new Error(`Missing ${theme} color for '${prop}' (figma path: '${figmaPath}')`);
        return `${pad}${prop}: Color = ${hexToCompose(hex)},`;
      }).join('\n');
    }

    const assignments = Object.keys(PROP_TO_FIGMA_PATH)
      .map(prop => `${pad}${prop} = ${prop},`)
      .join('\n');

    return [
      LICENSE_HEADER, '',
      FILE_HEADER, '',
      'package net.skyscanner.backpack.compose.tokens', '',
      'import androidx.compose.ui.graphics.Color', '',
      'public class BpkColors private constructor(',
      '    public val isLight: Boolean,',
      params,
      ') {',
      '    internal companion object {',
      '        public fun light(',
      factoryArgs(resolvedLight, 'light'),
      '        ): BpkColors = BpkColors(',
      '            isLight = true,',
      assignments,
      '        )', '',
      '        public fun dark(',
      factoryArgs(resolvedDark, 'dark'),
      '        ): BpkColors = BpkColors(',
      '            isLight = false,',
      assignments,
      '        )',
      '    }',
      '}',
      '',
    ].join('\n');
  },
});

// Wire a second platform on sdLight that generates BpkColors.kt after capture
(sdLight as any).options.platforms['compose-colors'] = {
  buildPath: `${OUTPUT_DIR}/compose/`,
  files: [{ destination: 'BpkColors.kt', format: 'kotlin/bpk-colors' }],
};

// ─── Run all platforms ────────────────────────────────────────────────────────

console.log('Building dimension tokens (Style Dictionary)...\n');
await sdDimensions.buildAllPlatforms();

console.log('\nResolving light semantic colors (Style Dictionary)...');
await sdDark.buildAllPlatforms();   // dark first — populates resolvedDark

console.log('Resolving dark semantic colors (Style Dictionary)...');
await sdLight.buildAllPlatforms();  // light second — populates resolvedLight, then generates BpkColors.kt

console.log(`\nOutput: ${OUTPUT_DIR}/`);
