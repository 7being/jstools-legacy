package com.sevenbeing.jstools;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.sevenbeing.jstools.bean.CompressBean;
import com.sevenbeing.jstools.bean.Configuration;
import com.sevenbeing.jstools.bean.JsUnitBean;
import com.sevenbeing.jstools.runner.ConfigurationRunner;

/**
 * @goal run
 */
public class RunMojo extends AbstractMojo
{

	/**
	 * @parameter expression="${basedir}/src/main/resources/jstools.yaml"
	 * @required
	 */
	private File jstoolsConfig;
	
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
		getLog().info("Running jstools plugin");
		Configuration config = Configuration.load(jstoolsConfig);
		
		setDefaultCompressParams(config.getCompress());
		setDefaultJsUnitParams(config.getJsunit());
		
		new ConfigurationRunner().go(config);
	}
	
	private void setDefaultCompressParams(List<CompressBean> beans)
	{
		if (null == beans) return;
		
		for (CompressBean bean : beans)
		{
			if (null == bean.getBasedir())
			{
				bean.setBasedir(baseDir.getAbsolutePath());
			}
			else
			{
				bean.setBasedir(new File(baseDir, bean.getBasedir()).getAbsolutePath());
			}
		}
	}
	
	private void setDefaultJsUnitParams(List<JsUnitBean> beans)
	{
		if (null == beans) return;
		
		for (JsUnitBean bean : beans)
		{
			bean.setWorkdir(jsunitWorkDirectory.getAbsolutePath());
			bean.setLogsdir(jsunitLogsDirectory.getAbsolutePath());
			bean.setBasedir(baseDir.getAbsolutePath());
		}
	}

}

