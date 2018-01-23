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
const gutil = require('gulp-util');
const path = require('path');
const nunjucks = require('gulp-nunjucks');
const fs = require('fs');
const rename = require('gulp-rename');
const runSequence = require('run-sequence');
const del = require('del');
const _ = require('lodash');
const tinycolor = require("tinycolor2");
const tokens = require('bpk-tokens/tokens/base.raw.android.json');

const __PATHS__ = {
    templates: path.join(__dirname, 'templates'),
    outputRes: path.join(__dirname, 'Backpack', 'src', 'main', 'res', 'values'),

};


const pascalCase = (s) => _.flow(_.camelCase, _.upperFirst)(s);


const parseColor = (c) => {
    let str = tinycolor(c).toHex8().toUpperCase();
    let alpha = str.substr(6);
    return '#' + alpha + str.substr(0, 6);
};

const getTokenType = (type) => {
    return Object.values(tokens.props)
        .filter(i => i.type === type);
}
const getColors = () => {
    return getTokenType("color").map(color => {
        color.name = `bpk${pascalCase(color.name.replace(color.type.toUpperCase(), ""))}`;
        return color;
    });
}

gulp.task('template:color', () => (
    gulp.src(__PATHS__.templates + '/BackpackColor.njk')
        .pipe(nunjucks.compile({
            'data': getColors()
        }))
        .pipe(rename('backpack.color.xml'))
        .pipe(gulp.dest(__PATHS__.outputRes))
));

 gulp.task('default', () => {
     runSequence('template:color')
 });

 gulp.task('clean', () => del([__PATHS__.outputRes], { force: true }));
