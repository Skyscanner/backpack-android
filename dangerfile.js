/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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
 */

// See http://danger.systems/js if you're not sure what this is.
import fs from 'fs';

import { includes } from 'lodash';
import { danger, fail, warn } from 'danger';

const shouldContainLicensingInformation = filePath =>
  filePath.match(/\.(js|sh|kt|java)$/) && !filePath.includes('dist/') && !filePath.includes('build/');

const createdFiles = danger.git.created_files;
const modifiedFiles = danger.git.modified_files;
const declaredTrivial = danger.github.pr.title.includes('#trivial');

// If any files were created, the BpkComponentUsageDetector should have been updated.
const usageDetector = includes(
  modifiedFiles,
  'backpack-lint/src/main/java/net/skyscanner/backpack/lint/check/BpkComponentUsageDetector.kt',
);
const packageFilesCreated = createdFiles.some(filePath => filePath.startsWith('Backpack/src/main/java'));
if (packageFilesCreated && !usageDetector && !declaredTrivial) {
  warn("One or more package files were created, but `BpkComponentUsageDetector.kt` wasn't updated.");
}

// If any files were created, the BpkComposeComponentUsageDetector should have been updated.
const composeUsageDetector = includes(
  modifiedFiles,
  'backpack-lint/src/main/java/net/skyscanner/backpack/lint/check/BpkComposeComponentUsageDetector.kt',
);
const composePackageFilesCreated = createdFiles.some(filePath => filePath.startsWith('backpack-compose/src/main/kotlin'));
if (composePackageFilesCreated && !composeUsageDetector && !declaredTrivial) {
  warn("One or more package files were created, but `BpkComposeComponentUsageDetector.kt` wasn't updated.");
}

// Ensure package-lock changes are intentional.
const lockFileUpdated = includes(modifiedFiles, 'package-lock.json');
if (lockFileUpdated) {
  warn('`package-lock.json` was updated. Ensure that this was intentional.');
}

// New files should include the Backpack license heading.
const unlicensedFiles = createdFiles.filter(filePath => {
  if (shouldContainLicensingInformation(filePath)) {
    const fileContent = fs.readFileSync(filePath);
    return !fileContent.includes('Licensed under the Apache License, Version 2.0 (the "License")');
  }
  return false;
});
if (unlicensedFiles.length > 0) {
  fail(`These new files do not include the license heading: ${unlicensedFiles.join(', ')}`);
}
