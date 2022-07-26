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
const tinycolor = require('tinycolor2');
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
};

const VALID_SPACINGS = new Set(['sm', 'md', 'base', 'lg', 'xl', 'xxl']);

const FONT_WEIGHTS = {
  normal: 'normal',
  emphasized: 'emphasized',
  heavy: 'heavy',
};

const fontFamilyMappings = {
  [FONT_WEIGHTS.normal]: '?bpkFontFamilyBase',
  [FONT_WEIGHTS.emphasized]: '?bpkFontFamilyEmphasized',
  [FONT_WEIGHTS.heavy]: '?bpkFontFamilyHeavy',
};

const fontWeightMappings = {
  400: FONT_WEIGHTS.normal,
  700: FONT_WEIGHTS.emphasized,
  900: FONT_WEIGHTS.heavy,
};

const pascalCase = s =>
  _.flow(
    _.camelCase,
    _.upperFirst,
  )(s);

const tokensWithType = type => Object.values(tokens.props).filter(i => i.type === type);

const tokensWithCategory = category => Object.values(tokens.props).filter(i => i.category === category && !i.deprecated);

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

const getTextDimensions = () =>
  tokensWithCategory('typesettings')
    .filter(({ name }) => name.startsWith("FONT_SIZE"))
    .map(({ value, name }) => ({
      name: `bpkText${pascalCase(name.split('_')[2])}Size`,
      size: Number.parseInt(value, 10),
  }));

const getTextStyles = () => {
  const result = _.chain(
    [].concat(
      tokensWithCategory('font-sizes'),
      tokensWithCategory('font-weights'),
      tokensWithCategory('letter-spacings'),
      tokensWithCategory('typesettings'),
    ),
  )
    .groupBy(({ name }) =>
      name
        .replace('_FONT_SIZE', '')
        .replace('_FONT_WEIGHT', '')
        .replace('_LETTER_SPACING', '')
        .replace('_LINE_HEIGHT', ''),
    )
    .map((values, key) => [values, key])
    .filter(token => token[1].startsWith('TEXT_'))
    .map(token => {
      const properties = token[0];
      const key = token[1];

      const sizeProp = _.filter(properties, ({ category }) => category === 'font-sizes');
      const weightProp = _.filter(properties, ({ category }) => category === 'font-weights');
      const letterSpacingProp = _.filter(properties, ({ category }) => category === 'letter-spacings');
      const lineHeightProp = _.filter(properties, ({ category }) => category === 'typesettings');
      if (sizeProp.length !== 1 || weightProp.length !== 1) {
        throw new Error('Expected all text sizes to have a weight and font size.');
      }
      const fontWeight = fontWeightMappings[weightProp[0].value];

      return {
        name: `bpk${pascalCase(key)}`,
        size: `@dimen/bpkText${pascalCase(sizeProp[0].originalValue.split('_')[2])}Size`,
        fontFamily: fontFamilyMappings[fontWeight],
        lineHeight: (lineHeightProp.length == 1) ? lineHeightProp[0].value : null,
        letterSpacing: (letterSpacingProp.length == 1) ? letterSpacingProp[0].value : null,
      };
    })
    .value();
  return result;
};

const isSemanticColor = entity => entity.value && entity.darkValue;

gulp.task('template:color', () => {
  const getColors = () =>
    tokensWithType('color')
      .map(color => {
        const colorObject = JSON.parse(JSON.stringify(color));
        colorObject.name = `bpk${pascalCase(colorObject.name.replace(colorObject.type.toUpperCase(), ''))}`;
        colorObject.value = tinycolor(colorObject.value).toHexString();
        return colorObject;
      })
      .filter(entry => !isSemanticColor(entry));

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
        colorObject.name = `bpk${pascalCase(colorObject.name.replace(colorObject.type.toUpperCase(), ''))}`;

        if (isSemanticColor(colorObject)) {
          const light = {
            ...colorObject,
            value: tinycolor(colorObject.value).toHexString(),
          };
          out.light.push(light);
          const dark = {
            ...colorObject,
            value: tinycolor(colorObject.darkValue).toHexString(),
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

gulp.task('template:spacing', () => {
  const getSpacing = () =>
    tokensWithCategory('spacings')
      .map(token => JSON.parse(JSON.stringify(token)))
      .filter(({ name }) => VALID_SPACINGS.has(name.toLowerCase().replace('spacing_', '')))
      .map(token => {
        const newToken = token;
        newToken.name = `bpk${pascalCase(newToken.name.replace(newToken.type.toUpperCase(), ''))}`;
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
        dimensions: [...getTextDimensions()],
        data: [...getTextStyles()],
      }),
    )
    .pipe(rename('values/backpack.text.xml'))
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

gulp.task('template:borders', () => {
  const getBorders = () =>
    tokensWithCategory('borders').map(token => {
      const newToken = JSON.parse(JSON.stringify(token));
      newToken.name = `bpk${pascalCase(newToken.name)}`;
      return newToken;
    });
  return gulp
    .src(`${PATHS.templates}/BackpackBorders.njk`)
    .pipe(
      nunjucks.compile({
        data: getBorders(),
      }),
    )
    .pipe(rename('values/backpack.borders.xml'))
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

gulp.task('template:icons', () =>
  gulp
    .src('node_modules/@skyscanner/bpk-svgs/dist/svgs/icons/**/*.svg')
    .pipe(through.obj(convertToXml))
    .pipe(gulp.dest(PATHS.drawableRes)),
);

gulp.task('template:animation', () => {
  const getAnimation = () =>
    tokensWithCategory('animations').map(token => {
      const newToken = JSON.parse(JSON.stringify(token));
      newToken.name = `bpk${pascalCase(newToken.name)}`;
      newToken.value = newToken.value.slice(0, -2);
      return newToken;
    });
  return gulp
    .src(`${PATHS.templates}/BackpackAnimation.njk`)
    .pipe(
      nunjucks.compile({
        data: getAnimation(),
      }),
    )
    .pipe(rename('values/backpack.animation.xml'))
    .pipe(gulp.dest(PATHS.outputRes));
});

gulp.task(
  'default',
  gulp.series(
    'template:animation',
    'template:borders',
    'template:color',
    'template:semanticColor',
    'template:elevation',
    'template:icons',
    'template:radii',
    'template:spacing',
    'template:text',
  ),
  () => {},
);

gulp.task('clean', () => del([PATHS.outputRes], { force: true }));

gulp.task('printTextAttributes', done => {
  getTextStyles().forEach(({ name }) => {
    console.log(`<attr name="${name}Appearance" format="reference" />`);
  });
  done();
});

/**
 * Generate text styles for our themes
 * Usage:
 *  gulp printTextAppearenceStyles
 *  // Add a suffix to the style name. eg `bpkTextBaseSerif`
 *  gulp printTextAppearenceStyles --serif
 */
gulp.task('printTextAppearenceStyles', done => {
  const parseSuffix = _.flow([s => s.substring(2), _.capitalize]);
  const fontSuffix = parseSuffix(process.argv[3] || '');
  getTextStyles().forEach(({ name }) => {
    console.log(`<item name="${name}Appearance">@style/${name}${fontSuffix}</item>`);
  });
  done();
});
