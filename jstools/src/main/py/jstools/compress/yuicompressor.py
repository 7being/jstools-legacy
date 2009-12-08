# -*- coding: utf-8 -*-
import os, subprocess

yui_jar = os.path.join(os.path.dirname(__file__), 'yuicompressor-2.4.2.jar')

#subprocess.Popen(yui_jar)
print 'java -jar %s ' % yui_jar
subprocess.call(['ls', '-l'])
subprocess.call(['java', '-jar', yui_jar])
