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

    const folderPath = path.join('docs', componentName, 'screenshots');
    const screenShotPath = path.join(
      folderPath,
      `${screenShotName.join('_')}.png`,
    );

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
