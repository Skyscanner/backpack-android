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

const http = require('http');
const url = require('url');
const querystring = require('querystring');
const fs = require('fs');
const path = require('path');
const { spawn } = require('child_process');

const PORT = 8888;

const takeScreenshot = fileName => {
  const outFile = fs.createWriteStream(fileName);
  const ps = spawn('adb', ['exec-out', 'screencap', '-p']);
  ps.stdout.pipe(outFile);
  ps.stderr.pipe(process.stderr);

  return new Promise((resolve, reject) => {
    ps.on('close', code => {
      if (code !== 0) {
        reject(code);
      } else {
        resolve();
      }
    });
  });
};

http
  .createServer(async (req, res) => {
    const { query: rawQuery } = url.parse(req.url);
    const query = querystring.parse(rawQuery);

    console.log(`${query.name}`);

    const [componentName, ...screenShotName] = query.name.split('_');

    const folderPath = path.join(`${query.path}`, componentName, 'screenshots');
    const screenShotPath = path.join(folderPath, `${screenShotName.join('_')}.png`);

    try {
      if (!fs.existsSync(folderPath)) {
        fs.mkdirSync(folderPath, { recursive: true });
      }

      await takeScreenshot(screenShotPath);
      res.writeHead(200, { 'Content-Type': 'text/plain' });
      res.write('ok\n');
    } catch (e) {
      console.log(e.stack);
      res.writeHead(500, { 'Content-Type': 'text/plain' });
      res.write('Internal server error\n');
    }

    res.end();
  })
  .listen(PORT, () => {
    console.log(`screenshot server running at port ${PORT}`);
  });
