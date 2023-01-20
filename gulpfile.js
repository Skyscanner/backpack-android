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
const through = require('through2');
const svg2vectordrawable = require('svg2vectordrawable');
const xmldom = require('@xmldom/xmldom');
const iconsMetadata = require('@skyscanner/bpk-svgs/dist/metadata.json');

const PATHS = {
  drawableRes: path.join(__dirname, 'backpack-common', 'src', 'main', 'res', 'drawable-nodpi'),
};

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

gulp.task('template:icons', () =>
  gulp
    .src('node_modules/@skyscanner/bpk-svgs/dist/svgs/icons/**/*.svg')
    .pipe(through.obj(convertToXml))
    .pipe(gulp.dest(PATHS.drawableRes)),
);

gulp.task(
  'default',
  gulp.series(
    'template:icons',
  ),
  () => {},
);
