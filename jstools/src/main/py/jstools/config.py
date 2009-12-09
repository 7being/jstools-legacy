# -*- coding: utf-8 -*-
from os import path

class Config(object):

    def __init__(self, conf):
        self.data = yaml.load(file(conf, 'r'))

        self.resolve_basedir(path.dirname(conf))
        self.resolve_version()
        self.resolve_outputs()

    def resolve_basedir(self, alternate):
        self.fix_basedir(self.data, alternate)
        for directive in (self.data.compress + self.data.jsunit):
            self.fix_basedir(directive, self.data.basedir)

    def resolve_version(self):
        root_ver = self.data.version
        for directive in self.data.compress:
            directive.version = directive.version or root_ver

    def resolve_outputs(self, alternate):
        # compress outputs
        for directive in self.data.compress:
            for output in ('minfile', 'allfile'):
                filepath = directive.get(output)
                if filepath and not path.isabs(filepath):
                    directive.set(output, path.abspath(path.join(alternate, filepath)))
        # jsunit outputs
        for directive in self.data.jsunit:
            for output in ('workdir', 'logsdir'):
                if not directive.get(output):
                    directive.set(output, path.abspath(path.join(alternate, \
                        (output == 'workdsdir' and 'jsunit-tests' or 'jsunit-reports'))))

    def fix_basedir(self, directive, alternate):
        if directive.basedir:
            if not path.isabs(directive.basedir):
                directive.basedir = path.join(alternate, directive.basedir)
        else:
            directive.basedir = alternate
        directive.basedir = path.abspath(directive.basedir)

    def as_dict(self):
        return self.data

    def __getitem__(self, key):
        return self.data[key]
