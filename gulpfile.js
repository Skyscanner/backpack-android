/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-present Skyscanner Ltd
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

const gulp = require('gulp');
const path = require('path');
const nunjucks = require('gulp-nunjucks');
const rename = require('gulp-rename');
const runSequence = require('run-sequence');
const del = require('del');
const _ = require('lodash');
const tokens = require('bpk-tokens/tokens/base.raw.android.json');

const PATHS = {
  templates: path.join(__dirname, 'templates'),
  outputRes: path.join(__dirname, 'Backpack', 'src', 'main', 'res', 'values'),
};
const VALID_SPACINGS = new Set(['sm', 'md', 'base', 'lg', 'xl', 'xxl']);
const pascalCase = s => _.flow(_.camelCase, _.upperFirst)(s);

const getTokenType = type =>
  Object.values(tokens.props).filter(i => i.type === type);

gulp.task('template:color', () => {
  const getColors = () =>
    getTokenType('color').map(color => {
      const newColor = JSON.parse(JSON.stringify(color));
      newColor.name = `bpk${pascalCase(
        newColor.name.replace(newColor.type.toUpperCase(), ''),
      )}`;
      return newColor;
    });

  return gulp
    .src(`${PATHS.templates}/BackpackColor.njk`)
    .pipe(
      nunjucks.compile({
        data: getColors(),
      }),
    )
    .pipe(rename('backpack.color.xml'))
    .pipe(gulp.dest(PATHS.outputRes));
});

gulp.task('template:spacing', () => {
  const getSpacing = () =>
    getTokenType('size')
      .map(token => JSON.parse(JSON.stringify(token)))
      .filter(token => token.category === 'spacings')
      .filter(({ name }) =>
        VALID_SPACINGS.has(name.toLowerCase().replace('spacing_', '')),
      )
      .map(token => {
        const newToken = token;
        newToken.name = `bpk${pascalCase(
          newToken.name.replace(newToken.type.toUpperCase(), ''),
        )}`;
        return newToken;
      });

  return gulp
    .src(`${PATHS.templates}/BackpackSpacing.njk`)
    .pipe(
      nunjucks.compile({
        data: getSpacing(),
      }),
    )
    .pipe(rename('backpack.dimensions.spacing.xml'))
    .pipe(gulp.dest(PATHS.outputRes));
});

gulp.task('default', () => {
  runSequence('template:color', 'template:spacing');
});

gulp.task('clean', () => del([PATHS.outputRes], { force: true }));
