module JsunitRunner
  class Config
    attr_reader :target_dir, :source_dir, :tests_dir, :logs_dir, :browser_names, :port
	attr_accessor :url

    def initialize(config)
      %w( source_dir tests_dir target_dir logs_dir ).each { |key|  
        raise "#{key} property required!" unless config[key.to_sym] || key == "logs_dir"
		config[key.to_sym] = config[key.to_sym] ? File.expand_path(config[key.to_sym]) : ""
		unless (key != "source_dir" && key != "tests_dir") || File.exist?(config[key.to_sym])
			raise ":#{key} => #{config[key.to_sym]} directory not found"
		end
      }

      @browser_names = config[:browser_names] || ""
      @target_dir = config[:target_dir]
      @source_dir = config[:source_dir]
      @tests_dir = config[:tests_dir]
      @logs_dir = config[:logs_dir]
      @port = config[:port] || 8090
    end

    def write
      ant_build_file = File.join(@target_dir, "build.xml")

      require "rexml/document"
      doc = REXML::Document.new(File.new(ant_build_file))

      replace_value(doc, "browserFileNames", @browser_names)
      replace_value(doc, "logsDirectory", @logs_dir)
      replace_value(doc, "port", @port)
      replace_value(doc, "url", @url)

      File.open(ant_build_file, "w") { |f| doc.write f }
    end

    private

    def replace_value(document, property, value)
      root = document.root
      root.each_element("property[@name=\"#{property}\"]") { |e| e.add_attribute("value", value) } unless value.nil?
    end
  end
end
