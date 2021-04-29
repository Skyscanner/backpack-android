/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

const currentYear = new Date().getFullYear();
const shouldContainLicensingInformation = filePath =>
  filePath.match(/\.(js|sh|kt|java)$/) && !filePath.includes('dist/') && !filePath.includes('build/');

const createdFiles = danger.git.created_files;
const modifiedFiles = danger.git.modified_files;
const fileChanges = [...modifiedFiles, ...createdFiles];
const declaredTrivial = danger.github.pr.title.includes('#trivial');

// If any code have changed, the UNRELEASED log should have been updated.
const unreleasedModified = includes(modifiedFiles, 'UNRELEASED.md');
const packagesModified = fileChanges.some(filePath => filePath.startsWith('Backpack/src'));
if (packagesModified && !unreleasedModified && !declaredTrivial) {
  warn("One or more packages have changed, but `UNRELEASED.md` wasn't updated.");
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

// Updated files should include the latest year in licensing header.
const outdatedLicenses = fileChanges.filter(filePath => {
  if (shouldContainLicensingInformation(filePath) && !unlicensedFiles.includes(filePath)) {
    const fileContent = fs.readFileSync(filePath);
    return !fileContent.includes(`Copyright 2018-${currentYear} Skyscanner Ltd`);
  }
  return false;
});
if (outdatedLicenses.length > 0) {
  fail(`These files contain an outdated licensing header: ${outdatedLicenses.join(', ')}. Please update to ${currentYear}.`);
}
