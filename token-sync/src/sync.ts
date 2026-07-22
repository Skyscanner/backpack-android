/**
 * tokens:sync — fetch from Figma and build in one step, no JSON stored.
 *
 * DTCG JSON files are written to a temp directory, consumed by Style Dictionary,
 * then deleted. Nothing is ever committed. Requires FIGMA_TOKEN in env or .env.
 *
 *   npm run tokens:sync
 */

import { execSync } from 'child_process';
import { mkdtempSync, rmSync } from 'fs';
import { tmpdir } from 'os';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';
import { createRequire } from 'module';

const __dirname = dirname(fileURLToPath(import.meta.url));
const ROOT = resolve(__dirname, '..');

// Load .env so FIGMA_TOKEN is available to child processes
const require = createRequire(import.meta.url);
const dotenv = require('dotenv');
dotenv.config({ path: resolve(ROOT, '.env') });

if (!process.env.FIGMA_TOKEN) {
  console.error('Error: FIGMA_TOKEN is not set.');
  console.error('Add it to token-sync/.env or export it as an environment variable.');
  process.exit(1);
}

// Create an OS temp directory — automatically unique, cleaned up below
const tmpDir = mkdtempSync(`${tmpdir()}/bpk-tokens-`);

function run(script: string, extraEnv: Record<string, string> = {}): void {
  execSync(`npx tsx src/${script}`, {
    cwd: ROOT,
    stdio: 'inherit',
    env: { ...process.env, ...extraEnv },
  });
}

try {
  console.log(`Temp dir: ${tmpDir}\n`);

  // Step 1: fetch Figma variables → DTCG JSON into temp dir
  run('fetch.ts', { TOKENS_DIR: tmpDir });

  // Step 2: Style Dictionary reads temp dir → generates .kt + .xml
  run('build.ts', { TOKENS_DIR: tmpDir });

} finally {
  rmSync(tmpDir, { recursive: true, force: true });
  console.log('\nTemp files cleaned up.');
}
