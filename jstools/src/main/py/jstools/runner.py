# -*- coding: utf-8 -*-
import subprocess
from os import path
import yaml

yui_jar = path.join(path.dirname(__file__), 'yuicompressor-2.4.2.jar')

test_base = path.abspath(path.join(path.dirname(__file__), '..', '..', '..', 'test', 'resources'))
test_yaml = path.join(test_base, 'config-test.yaml')

jstools_config = yaml.load(file(test_yaml, 'r'))
print type(jstools_config)
print str(jstools_config)

print 'java -jar %s ' % yui_jar
#subprocess.call(['ls', '-l'])
#subprocess.call(['java', '-jar', yui_jar])

class CompressRunner(object):

    def __init__(self, directive):
        pass

    def guess_type(self):
        pass

    def concat_sources(self):
        pass

    def minify_sources(self):
        pass
        
class JsUnitRuner(object):

    def __init__(self, directive):
        self.make_workdir()
        self.copy_jsunit_resources()
        self.copy_sources()
        self.copy_test_sources()
        self.configure_server()
        self.run_test()

    def make_workdir(self):
        pass

    def copy_jsunit_resources(self):
        pass

    def copy_sources(self):
        pass

    def copy_test_sources(self):
        pass

    def configure_server(self):
        pass

    def run_tests(self):
        pass
        
class BeautifyRunner(object):

    def __init__(self, directive):
        pass
