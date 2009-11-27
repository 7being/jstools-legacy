require "fileutils"
module JSUtils
  class JSUnitRunner
    include FileUtils

    def initialize(config)
      wireup_ant unless ENV["ANT_HOME"]

      @config = config

      copy_resources
      generate_test_suite
      run_tests
    end

    private
            
    def copy_resources
      rm_rf @config.target_dir

      @project = "project_dir"
      @project_dir = File.join(@config.target_dir, @project)

      mkdir_p @project_dir

      cp_r "#{@config.source_dir}/.", @project_dir
      cp_r "#{@config.tests_dir}/.", @project_dir

      cp_r "#{VENDOR_DIR}/jsunit/.", @config.target_dir
    end

    def generate_test_suite
	  cd @config.target_dir 
	
	  pattern = %w( Test.htm Tests.htm Test.html Tests.html).collect { |type| "#{@project}/**/*#{type}" }

	  test_pages = "\"#{Dir.glob(pattern).sort.collect{ |test_page|
		  #test_page = js_to_html_page(File.join(@config.target_dir, test_page)) if test_page =~ /\.js$/
		  "http://localhost:#{@config.port}/jsunit/#{test_page}"
	  }.join("\",\"")}\""

	  suite = %{
<html>
	<head>
		<title>JsUnit Test Suite</title>
		<script type="text/javascript" src="../app/jsUnitCore.js"></script>
		<script type="text/javascript">
			function suite() { return new top.jsUnitTestSuite(#{test_pages}) }
		</script>
	</head>
	<body></body>
</html>
	  }.sub(/^\s+/, '')

	  File.open("#{@project_dir}/suite.html", "w") { |file| file.write(suite) }

	  @config.url = "http://localhost:#{@config.port}/jsunit/testRunner.html?testPage=http://localhost:#{@config.port}/jsunit/#{@project}/suite.html"
    end

	def js_to_test_page(file)
		page = file.sub(/js$/, "html")
		puts page
		unless File.exist? page
			content = %{
<html>	
	<head>
		<title>#{page.chomp(".html")}</title>
		<script type="text/javascript" src="../app/jsUnitCore.js"></script>
		<script type="text/javascript">
			#{File.read(page)}
		</script>
	</head>
</html>
			}.sub(/^\s+/, '')

			File.open(page, "w") { |f| f.write content }
		end
	end

    def run_tests
	  cd @config.target_dir

	  @config.write 
	  ant = "#{ENV["ANT_HOME"]}/bin/ant"
      system ant
    end

    def wireup_ant
      ant_home = "#{VENDOR_DIR}/apache-ant-1.7.1"
	  File.chmod(0755, ant_home + "/bin/ant")
      ENV["ANT_HOME"] = ant_home
    end
  end
end
