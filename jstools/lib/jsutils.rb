dir = File.dirname(__FILE__)
require "#{dir}/jsutils/jsunit_runner"
require "#{dir}/jsutils/jsunit_config"
require "#{dir}/jsutils/yuicompressor_runner"
require "#{dir}/jsutils/yuicompressor_config"

module JSUtils

  VENDOR = File.expand_path("#{File.dirname(__FILE__)}/../vendor")
  ANT_HOME = "#{VENDOR}/apache-ant-1.7.1'"
  YUI_HOME = "#{VENDOR}/yui"
  JSUNIT_HOME = "#{VENDOR}/jsunit"

  class << self
    def jsunit(config)
      puts "Started running jsunit tests!"	
      JSUnitRunner.new(JSUnitConfig.new(config))
      puts "Finished running jsunit tests!"	
    end

    def minify(config)
      puts "Started YUI compressor compressing!"	
      YUICompressorRunner.new(YUICompressorRunner.new(config))
      puts "Finished YUI compressor compressing!"	
    end
  end
end
