package com.sevenbeing.jstools;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.sevenbeing.jstools.bean.JsToolsBean;
import com.sevenbeing.jstools.bean.JsUnitBean;
import com.sevenbeing.jstools.runner.JsToolsRunner;

/**
 * @goal run
 */
public class RunMojo extends AbstractMojo
{

	/**
	 * @parameter expression="${basedir}/src/main/resources/jstools.yaml"
	 * @required
	 */
	private File jstoolsConfigFile;
	
	/**
	 * @parameter expression="${basedir}"
	 */
	private File baseDir;
	
	/**
	 * @parameter expression="${project.build.directory}/jsunit-tests"
	 */
	private File jsunitWorkDirectory;
	
	/**
	 * @parameter expression="${project.build.directory}/jsunit-reports"
	 */
	private File jsunitLogsDirectory;
	
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		welcome();
		
		if (!jstoolsConfigFile.exists())
		{
			throw new MojoFailureException("JsTools requires src/main/resources/jstools.yaml configuration file");
		}
		
		
		JsToolsBean config = JsToolsLoader.load(jstoolsConfigFile, baseDir.getAbsoluteFile());
		
		setDefaultJsUnitParams(config.getJsunit());
		
		new JsToolsRunner().go(config);
	}
	
	private void setDefaultJsUnitParams(List<JsUnitBean> beans)
	{
		if (null == beans) return;
		
		for (JsUnitBean bean : beans)
		{
			bean.setWorkdir(jsunitWorkDirectory.getAbsolutePath());
			bean.setLogsdir(jsunitLogsDirectory.getAbsolutePath());
		}
	}
	
	private void welcome()
	{
		getLog().info("------------------------------------------------------------------------");
		getLog().info("--------------------  Running jstools plugin  --------------------------");
		getLog().info("------------------------------------------------------------------------");
	}
	

}

