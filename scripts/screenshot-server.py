#!/bin/python

from http.server import HTTPServer, BaseHTTPRequestHandler
from urlparse import parse_qs
import SocketServer
import subprocess
import os

class S(BaseHTTPRequestHandler):
  def log_message(self, format, *args):
    # args are in this format ('POST ?name=<name> HTTP', ...)
    print args[0].split(' ')[1].split('=')[1]

  def do_POST(self):
    fileName = parse_qs(self.path[2:])["name"][0]
    (componentName, screenShotName) = fileName.split('_', 1)
    folderPath = os.path.join('docs', componentName, 'screenshots')
    screenShotPath = os.path.join(folderPath, screenShotName + '.png')

    if not os.path.exists(folderPath):
      os.makedirs(folderPath)

    subprocess.call(["adb", "exec-out", "screencap", "-p"], stdout=open(screenShotPath, 'w'))
    self.send_response(200, "ok\n")

PORT = 8888

httpd = HTTPServer(("", PORT), S)

print "screenshot server running at port", PORT
httpd.serve_forever()