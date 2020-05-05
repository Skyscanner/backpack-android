/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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
import { danger, fail, warn, message } from 'danger';

import meta from './meta.json';

const BACKPACK_SQUAD_MEMBERS = meta.maintainers.map(
  maintainer => maintainer.github,
);

const author = danger.github.pr.user.login;
const isPrExternal = !BACKPACK_SQUAD_MEMBERS.includes(author);

const createdFiles = danger.git.created_files;
const modifiedFiles = danger.git.modified_files;
const fileChanges = [...modifiedFiles, ...createdFiles];
const declaredTrivial = danger.github.pr.title.includes('#trivial');

// Be nice to our neighbours.
if (isPrExternal) {
  message('Thanks for the PR 🎉.');
  warn(
    `If this is coming from a fork, CI will fail. This is a known limitation due to CI not sharing secrets to forked repos. Somebody from Backpack can check this manually.`,
  );
}

// If any code have changed, the UNRELEASED log should have been updated.
const unreleasedModified = includes(modifiedFiles, 'UNRELEASED.md');
const packagesModified = fileChanges.some(filePath =>
  filePath.startsWith('Backpack/src'),
);
if (packagesModified && !unreleasedModified && !declaredTrivial) {
  warn(
    "One or more packages have changed, but `UNRELEASED.md` wasn't updated.",
  );
}

// Ensure package-lock changes are intentional.
const lockFileUpdated = includes(modifiedFiles, 'package-lock.json');
if (lockFileUpdated) {
  warn('`package-lock.json` was updated. Ensure that this was intentional.');
}

// New files should include the Backpack license heading.
const unlicensedFiles = createdFiles.filter(filePath => {
  if (
    filePath.match(/\.(js|sh|kt|java)$/) &&
    !filePath.includes('dist/') &&
    !filePath.includes('build/')
  ) {
    const fileContent = fs.readFileSync(filePath);
    return !fileContent.includes(
      'Licensed under the Apache License, Version 2.0 (the "License")',
    );
  }
  return false;
});
if (unlicensedFiles.length > 0) {
  fail(
    `These new files do not include the license heading: ${unlicensedFiles.join(
      ', ',
    )}`,
  );
}
