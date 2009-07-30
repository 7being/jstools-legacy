require "rake"
require "rake/gempackagetask"

PKG_NAME = "jsunit_runner"
PKG_VERSION = "0.0.1"
PKG_FILES = FileList[
	"lib/**/*.rb"
]

spec = Gem::Specification.new do |s|
	s.name = PKG_NAME
	s.version = PKG_VERSION
	s.summary = "Running jsunit from ruby"
	s.description = s.summary

	s.files = PKG_FILES.to_a
	s.require_path = "lib"

	s.author = "William Wang"
	s.email = "7being@gmail.com"
end

Rake::GemPackageTask.new(spec) do |pkg|
	pkg.need_zip = true
	pkg.need_tar = true
end

task :test do
	rm_rf "pkg"
	system "rake gem"
	system "gem uninstall #{PKG_NAME}"
	system "gem install pkg/#{PKG_NAME}-#{PKG_VERSION}.gem --no-rdoc --no-ri"

	require "jsunit_runner"
end

