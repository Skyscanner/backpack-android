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
const tinycolor = require('tinycolor2');
const _ = require('lodash');
const through = require('through2');
const svg2vectordrawable = require('svg2vectordrawable');
const tokens = require('bpk-tokens/tokens/base.raw.android.json');

const PATHS = {
  templates: path.join(__dirname, 'templates'),
  outputRes: path.join(__dirname, 'Backpack', 'src', 'main', 'res'),
  drawableRes: path.join(
    __dirname,
    'Backpack',
    'src',
    'main',
    'res',
    'drawable-nodpi',
  ),
};

const VALID_SPACINGS = new Set(['sm', 'md', 'base', 'lg', 'xl', 'xxl']);
const VALID_TEXT_STYLES = new Set([
  'caps',
  'xs',
  'sm',
  'base',
  'lg',
  'xl',
  'xxl',
  'xxxl',
]);
const { FONT_FAMILY, FONT_FAMILY_EMPHASIZE } = tokens.aliases;
const EMPHASIZED_FONT_WEIGHT = tokens.props.TEXT_EMPHASIZED_FONT_WEIGHT.value;

const pascalCase = s =>
  _.flow(
    _.camelCase,
    _.upperFirst,
  )(s);

const fontForWeight = weightString => {
  const cleanWeight = weightString.trim();

  if (cleanWeight === '400') {
    if (!FONT_FAMILY) {
      throw new Error('Expected `FONT_FAMILY` to exist in tokens. It did not');
    }

    return FONT_FAMILY.value.replace(/"/g, '');
  }
  if (cleanWeight === '500') {
    if (!FONT_FAMILY_EMPHASIZE) {
      throw new Error(
        'Expected `FONT_FAMILY_EMPHASIZE` to exist in tokens. It did not',
      );
    }

    return FONT_FAMILY_EMPHASIZE.value.replace(/"/g, '');
  }
  throw new Error(`No font know for font weight \`${cleanWeight}\`.`);
};

const tokensWithType = type =>
  Object.values(tokens.props).filter(i => i.type === type);

const tokensWithCategory = category =>
  Object.values(tokens.props).filter(i => i.category === category);

const convertToXml = (chunk, enc, cb) => {
  const svgCode = chunk.contents.toString(enc);
  return svg2vectordrawable(svgCode)
    .then(xmlCode => {
      chunk.contents = Buffer.from(xmlCode); // eslint-disable-line no-param-reassign
      const s = chunk.path.split('/');
      s[s.length - 1] = `bpk_${s[s.length - 1]
        .replace(/-/g, '_')
        .replace('.svg', '.xml')}`;
      chunk.path = s.join('/'); // eslint-disable-line no-param-reassign
      cb(null, chunk);
    })
    .catch(cb);
};

const getTextStyles = () => {
  const result = _.chain(
    [].concat(
      tokensWithCategory('font-sizes'),
      tokensWithCategory('font-weights'),
      tokensWithCategory('letter-spacings'),
    ),
  )
    .groupBy(({ name }) =>
      name
        .replace('_FONT_SIZE', '')
        .replace('_FONT_WEIGHT', '')
        .replace('LETTER_SPACING_', 'TEXT_'),
    )
    .map((values, key) => [values, key])
    .filter(token =>
      VALID_TEXT_STYLES.has(token[1].replace('TEXT_', '').toLowerCase()),
    )
    .map(token => {
      const properties = token[0];
      const key = token[1];

      const sizeProp = _.filter(
        properties,
        ({ category }) => category === 'font-sizes',
      );
      const weightProp = _.filter(
        properties,
        ({ category }) => category === 'font-weights',
      );
      const letterSpacingProp = _.filter(
        properties,
        ({ category }) => category === 'letter-spacings',
      );
      if (sizeProp.length !== 1 || weightProp.length !== 1) {
        throw new Error(
          'Expected all text sizes to have a weight and font size.',
        );
      }

      return {
        name: `bpk${pascalCase(key)}`,
        size: Number.parseInt(sizeProp[0].value, 10),
        fontFamily: fontForWeight(weightProp[0].value),
        letterSpacing: letterSpacingProp[0].value,
      };
    })
    .flatMap(properties => [
      properties,
      {
        ...properties,
        fontFamily: fontForWeight(EMPHASIZED_FONT_WEIGHT),
        name: `${properties.name}Emphasized`,
      },
    ])
    .value();
  return result;
};

gulp.task('template:color', () => {
  const getColors = () =>
    tokensWithType('color').map(color => {
      const colorObject = JSON.parse(JSON.stringify(color));
      colorObject.name = `bpk${pascalCase(
        colorObject.name.replace(colorObject.type.toUpperCase(), ''),
      )}`;
      colorObject.value = tinycolor(colorObject.value).toHexString();
      return colorObject;
    });

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

gulp.task('template:spacing', () => {
  const getSpacing = () =>
    tokensWithCategory('spacings')
      .map(token => JSON.parse(JSON.stringify(token)))
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
    .pipe(rename('values/backpack.dimensions.spacing.xml'))
    .pipe(gulp.dest(PATHS.outputRes));
});

gulp.task('template:text', () =>
  gulp
    .src(`${PATHS.templates}/BackpackText.njk`)
    .pipe(
      nunjucks.compile({
        data: getTextStyles(),
      }),
    )
    .pipe(rename('values/backpack.text.xml'))
    .pipe(gulp.dest(PATHS.outputRes)),
);

gulp.task('template:text-v21', () =>
  gulp
    .src(`${PATHS.templates}/BackpackTextv21.njk`)
    .pipe(
      nunjucks.compile({
        data: getTextStyles(),
      }),
    )
    .pipe(rename('values-v21/backpack.text.xml'))
    .pipe(gulp.dest(PATHS.outputRes)),
);
gulp.task('template:radii', () => {
  const getRadii = () =>
    tokensWithCategory('radii').map(token => {
      const newToken = JSON.parse(JSON.stringify(token));
      newToken.name = `bpk${pascalCase(newToken.name)}`;
      return newToken;
    });
  return gulp
    .src(`${PATHS.templates}/BackpackRadii.njk`)
    .pipe(
      nunjucks.compile({
        data: getRadii(),
      }),
    )
    .pipe(rename('values/backpack.radii.xml'))
    .pipe(gulp.dest(PATHS.outputRes));
});

gulp.task('template:elevation', () => {
  const getElevation = () =>
    tokensWithCategory('elevation').map(token => {
      const newToken = JSON.parse(JSON.stringify(token));
      newToken.name = `bpk${pascalCase(newToken.name)}`;
      return newToken;
    });
  return gulp
    .src(`${PATHS.templates}/BackpackElevation.njk`)
    .pipe(
      nunjucks.compile({
        data: getElevation(),
      }),
    )
    .pipe(rename('values/backpack.elevation.xml'))
    .pipe(gulp.dest(PATHS.outputRes));
});

gulp.task('template:icons', () => {
  gulp
    .src('node_modules/bpk-svgs/dist/svgs/icons/**/*.svg')
    .pipe(through.obj(convertToXml))
    .pipe(gulp.dest(PATHS.drawableRes));
});

gulp.task('default', () => {
  runSequence(
    'template:color',
    'template:text',
    'template:text-v21',
    'template:spacing',
    'template:radii',
    'template:elevation',
    'template:icons',
  );
});

gulp.task('clean', () => del([PATHS.outputRes], { force: true }));
