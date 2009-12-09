from config import Config
from runner import CompressRunner

from os import path

test_base = path.abspath(path.join(path.dirname(__file__), '..', '..', '..', 'test', 'resources'))
test_yaml = path.join(test_base, 'config-test.yaml')

if  __name__ == '__main__':
    config = Config(test_yaml)
    if config.skip: return

    for directive in config.compress:
        if not directive.skip: CompressRunner(directive)
