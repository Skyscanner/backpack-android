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

const path = require('path');

const gulp = require('gulp');
const merge = require('merge-stream');
const nunjucks = require('gulp-nunjucks');
const rename = require('gulp-rename');
// As del is now an ESM module unless/until we migrate this file to ESM this and want to do the major version bump
// we use the following format to import from CommonJS instead of ESM. See https://gist.github.com/sindresorhus/a39789f98801d908bbc7ff3ecc99d99c
const del = async () => {
  await import('del');
}
const _ = require('lodash');
const through = require('through2');
const svg2vectordrawable = require('svg2vectordrawable');
const xmldom = require('@xmldom/xmldom');
const tokens = require('@skyscanner/bpk-foundations-android/tokens/base.raw.android.json');
const iconsMetadata = require('@skyscanner/bpk-svgs/dist/metadata.json');

const PATHS = {
  templates: path.join(__dirname, 'templates'),
  outputRes: path.join(__dirname, 'Backpack', 'src', 'main', 'res'),
  drawableRes: path.join(__dirname, 'backpack-common', 'src', 'main', 'res', 'drawable-nodpi'),
  lintSrc: path.join(__dirname, 'backpack-lint', 'src', 'main', 'java', 'net', 'skyscanner', 'backpack', 'lint', 'check'),
};

const pascalCase = s =>
  _.flow(
    _.camelCase,
    _.upperFirst,
  )(s);

const tokensWithType = type => Object.values(tokens.props).filter(i => i.type === type);

const shouldAutoMirror = chunk => {
  const iconMetadata = iconsMetadata[chunk.stem];
  return iconMetadata && iconMetadata.autoMirror;
};

const convertToXml = (chunk, enc, cb) => {
  const svgCode = chunk.contents.toString(enc);
  return svg2vectordrawable(svgCode)
    .then(xmlCode => {
      const xmlDoc = new xmldom.DOMParser().parseFromString(xmlCode, 'utf-8');

      if (shouldAutoMirror(chunk)) {
        xmlDoc.documentElement.setAttribute('android:autoMirrored', 'true');
      }
      const paths = xmlDoc.getElementsByTagName('path');
      for (let i = 0; i < paths.length; i += 1) {
        const item = paths.item(i);
        if (item.getAttribute('android:fillColor') === '#000000') {
          item.setAttribute('android:fillColor', '@color/bpkTextPrimary');
        } else if (!item.getAttribute('android:fillColor')) {
          item.setAttribute('android:fillColor', '@color/bpkTextPrimary');
        }
      }

      const xmlContent = new xmldom.XMLSerializer().serializeToString(xmlDoc);
      chunk.contents = Buffer.from(xmlContent, 'utf-8'); // eslint-disable-line no-param-reassign
      const locationPaths = chunk.path.split('/');
      const type = locationPaths[locationPaths.length - 2];
      locationPaths.splice(locationPaths.length - 2, 1);
      let suffix = `_${type}`;
      if (type === 'lg') {
        // lg icon is default
        suffix = '';
      }
      const fileName = locationPaths[locationPaths.length - 1].replace(/-/g, '_').replace('.svg', `${suffix}.xml`);
      locationPaths[locationPaths.length - 1] = `bpk_${fileName}`;
      chunk.path = locationPaths.join('/'); // eslint-disable-line no-param-reassign
      cb(null, chunk);
    })
    .catch(cb);
};

const isSemanticColor = entity => entity.value && entity.darkValue;

const isMarcommsColor = entity => entity.name.startsWith("bpkMarcomms")

const hasNewSemanticSuffix = entity => entity.name.endsWith("Day") || entity.name.endsWith("Night");

const asArgb = color => `#${color.substring(7)}${color.substring(1, 7)}`;

gulp.task('template:color', () => {
  const getColors = () =>
    tokensWithType('color')
      .map(color => {
        const colorObject = JSON.parse(JSON.stringify(color));
        colorObject.name = `bpk${pascalCase(colorObject.name.replace(colorObject.type.toUpperCase(), ''))}`;
        colorObject.value = asArgb(colorObject.value);
        return colorObject;
      })
      .filter(entry => !isSemanticColor(entry) && !hasNewSemanticSuffix(entry) && !isMarcommsColor(entry));

  return gulp
    .src(`${PATHS.templates}/BackpackColor.njk`)
    .pipe(
      nunjucks.compile({
        data: getColors(),
      }),
    )
    .pipe(rename('values/backpack.color.xml'))
    .pipe(gulp.dest(PATHS.outputRes));
});

gulp.task('template:semanticColor', () => {
  const getColors = () =>
    tokensWithType('color').reduce(
      (out, color) => {
        const colorObject = JSON.parse(JSON.stringify(color));
        const tokenName = colorObject.name.replace(colorObject.type.toUpperCase(), '');
        if (tokenName.startsWith("PRIVATE")) {
        colorObject.name = `__${_.camelCase(tokenName)}`;
        } else {
          colorObject.name = `bpk${pascalCase(tokenName)}`;
        }

        if (isSemanticColor(colorObject)) {
          const light = {
            ...colorObject,
            value: asArgb(colorObject.value),
          };
          out.light.push(light);
          const dark = {
            ...colorObject,
            value: asArgb(colorObject.darkValue),
          };
          out.dark.push(dark);
        }
        return out;
      },
      { light: [], dark: [] },
    );
  const colors = getColors();

  const light = gulp
    .src(`${PATHS.templates}/BackpackColor.njk`)
    .pipe(
      nunjucks.compile({
        data: colors.light,
      }),
    )
    .pipe(rename('values/backpack.semantic.color.xml'))
    .pipe(gulp.dest(PATHS.outputRes));

  const dark = gulp
    .src(`${PATHS.templates}/BackpackColor.njk`)
    .pipe(
      nunjucks.compile({
        data: colors.dark,
      }),
    )
    .pipe(rename('values-night/backpack.semantic.color.xml'))
    .pipe(gulp.dest(PATHS.outputRes));

  return merge(light, dark);
});

gulp.task('template:deprecatedTokens', () => {
  const getColors = () =>
    tokensWithType('color')
      .map(color => {
        const colorObject = JSON.parse(JSON.stringify(color));
        colorObject.name = `bpk${pascalCase(colorObject.name.replace(colorObject.type.toUpperCase(), ''))}`;
        return colorObject;
      })
      .filter(entry => entry.deprecated);

  return gulp
    .src(`${PATHS.templates}/BackpackDeprecation.njk`)
    .pipe(
      nunjucks.compile({
        colors: getColors(),
      }),
    )
    .pipe(rename('BpkDeprecatedTokens.kt'))
    .pipe(gulp.dest(PATHS.lintSrc));
});

gulp.task('template:icons', () =>
  gulp
    .src('node_modules/@skyscanner/bpk-svgs/dist/svgs/icons/**/*.svg')
    .pipe(through.obj(convertToXml))
    .pipe(gulp.dest(PATHS.drawableRes)),
);

gulp.task(
  'default',
  gulp.series(
    'template:color',
    'template:semanticColor',
    'template:icons',
    'template:deprecatedTokens',
  ),
  () => {},
);

gulp.task('clean', () => del([PATHS.outputRes], { force: true }));
