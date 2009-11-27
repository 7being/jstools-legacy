package com.sevenbeing.jstools.runner;

import java.io.File;

import com.sevenbeing.jstools.bean.JsUnitBean;
import com.sevenbeing.jstools.utility.AntUtility;
import com.sevenbeing.jstools.utility.BrowserUtility;
import com.sevenbeing.jstools.utility.FileUtility;
import com.sevenbeing.jstools.utility.StringUtility;

public class JsUnitRunner extends AbstractRunner
{
	private JsUnitBean jsUnitBean;
	
	private File workDir = null;
	private File logsDir = null;
	private File sourceDir = null;
	private File testSourceDir = null;
	
	protected void run(Object bean)
	{
		jsUnitBean = (JsUnitBean) bean;
		
		workDir = new File(jsUnitBean.getWorkdir());
		logsDir = new File(jsUnitBean.getLogsdir());
		sourceDir = new File(jsUnitBean.getSource());
		testSourceDir = new File(jsUnitBean.getTest());
		
		makeWorkDirectory();
		copyJsUnitResources();
		
		copySource();
		copyTestSource();
		
		configureServer();
		
		startTesting();
	}
	
	private void makeWorkDirectory()
	{
		FileUtility.mkdir(workDir);
	}
	
	private void copyJsUnitResources()
	{
		File tmp = FileUtility.createTempFileFromJar("/jsunit2.3.zip");
		FileUtility.unzip(tmp, workDir);
		// make browser bins executable
		FileUtility.chmod(new File(workDir, "bin"), "**/*.sh", "u+x");
		
		tmp.delete();
	}
	
	private void copySource()
	{
		FileUtility.copydir(sourceDir, workDir);
	}
	
	private void copyTestSource()
	{
		FileUtility.copydir(testSourceDir, workDir);
		
		File tmp = FileUtility.createTempFileFromJar("/testSuite.html");
		FileUtility.copy(tmp, new File(workDir, "testSuite.html"));
		
		tmp.delete();
	}
	
	private void configureServer()
	{
		System.setProperty("url", getBrowserHitUrl(jsUnitBean.getPort()));
		//System.setProperty("url", "${testRunnerUrl}?testPage=${testPage}&autoRun=${autoRun}&submitResults=localhost:${port}/jsunit/acceptor&showTestFrame=${showTestFrame}");
		System.setProperty("browserFileNames", getBrowserFileNames());
		System.setProperty("port", jsUnitBean.getPort().toString());
		System.setProperty("logsDirectory", logsDir.toString());
		System.setProperty("closeBrowsersAfterTestRuns", jsUnitBean.getClosebro().toString());
		System.setProperty("resourceBase", workDir.toString());
	}
	
	private void startTesting()
	{
		File buildfile = new File(workDir, "build.xml");
		AntUtility.runTaskFromBuildfile(buildfile, "standalone_test");
	}
	
	private String getBrowserHitUrl(int port)
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("http://localhost:").append(port)
			.append("/jsunit/jsunit/testRunner.html?testPage=http://localhost:")
			.append(port).append("/jsunit/testSuite.html");
		System.out.println(buffer);
		return buffer.toString();
	}
	
	private String getBrowserFileNames()
	{
		String[] specs = BrowserUtility.getBrowserSpecs(jsUnitBean.getBrowsers(), jsUnitBean.getClosebro(), workDir.getAbsolutePath());
		
		return StringUtility.join(specs, ",");
	}
	
}
