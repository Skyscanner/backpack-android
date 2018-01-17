// Copyright (c) 2015-present, salesforce.com, inc. All rights reserved
// Licensed under BSD 3-Clause - see LICENSE.txt or git.io/sfdc-license


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
console.log("LOL");
console.log(getColors());
// console.log([...getColors()]);

// gulp.task('default', () => {
//     runSequence('template:color')
// });

// gulp.task('clean', () => del([__PATHS__.outputRes], { force: true }));
