package com.sevenbeing.jstools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.sevenbeing.jstools.bean.BaseBean;
import com.sevenbeing.jstools.bean.CompressBean;
import com.sevenbeing.jstools.bean.JsToolsBean;
import com.sevenbeing.jstools.bean.JsUnitBean;

public class JsToolsLoader
{
	private JsToolsBean parentBean;
	private List<BaseBean> childrenBeans = null;
	
	public JsToolsBean load(File configFile, File alternateBaseDir, File alternateOutputDir)
	{
		configFile = configFile.getAbsoluteFile();
		alternateBaseDir = alternateBaseDir.getAbsoluteFile();
		
		parentBean = parseConfigFile(configFile);
		childrenBeans = getChildrenBeans(parentBean);

		fixBeansBaseDir(alternateBaseDir);
		fixBeansOutputDir(alternateOutputDir);
		
		setVersionIfNotProvided();

		return parentBean;
	}

	private JsToolsBean parseConfigFile(File configFile)
	{
		Reader reader = null;
		try
		{
			reader = new FileReader(configFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return (JsToolsBean) JsToolsBean.getYamlLoader().load(reader);
	}
	
	private List<BaseBean> getChildrenBeans(JsToolsBean config)
	{
		List<BaseBean> result = new ArrayList<BaseBean>();
		if (null != config.getCompress()) result.addAll(config.getCompress());
		if (null != config.getJsunit()) result.addAll(config.getJsunit());
		return result;
	}
	
	private void setVersionIfNotProvided()
	{
		String globalVersion = parentBean.getVersion();
		if (null != globalVersion)
		{
			for (BaseBean bean : childrenBeans)
			{
				if (null == bean.getVersion())
				{
					bean.setVersion(globalVersion);
				}
			}
		}
	}

	private void fixBeansBaseDir(File alternateBaseDir)
	{
		// fix top level base dir for JsToolsBean
		fixBaseDir(parentBean, alternateBaseDir);
		
		File correctBaseDir = new File(parentBean.getBasedir());
		
		// fix children base dir of JsToolsBean
		for (BaseBean bean : childrenBeans)
		{
			fixBaseDir(bean, correctBaseDir);
		}
		
	}
	
	private void fixBeansOutputDir(File alternateOutputDir)
	{
		if (null != parentBean.getCompress())
		{
			for (CompressBean bean : parentBean.getCompress())
			{
				if (null != bean.getAllfile())
				{
					if (!new File(bean.getAllfile()).isAbsolute())
					{
						bean.setAllfile(new File(alternateOutputDir, bean.getAllfile()).getAbsolutePath());
					}
				}
				if (null != bean.getMinfile())
				{
					if (!new File(bean.getMinfile()).isAbsolute())
					{
						bean.setMinfile(new File(alternateOutputDir, bean.getMinfile()).getAbsolutePath());
					}
				}
			}
		}
		
		if (null != parentBean.getJsunit())
		{
			for (JsUnitBean bean : parentBean.getJsunit())
			{
				if (null == bean.getWorkdir())
				{
					bean.setWorkdir(new File(alternateOutputDir, "jsunit-tests").getAbsolutePath());
				}
				else
				{
					if (!new File(bean.getWorkdir()).isAbsolute())
					{
						bean.setWorkdir(new File(alternateOutputDir, bean.getWorkdir()).getAbsolutePath());
					}
				}
				if (null == bean.getLogsdir())
				{
					bean.setLogsdir(new File(alternateOutputDir, "jsunit-reports").getAbsolutePath());
				}
				else
				{
					if (!new File(bean.getLogsdir()).isAbsolute())
					{
						bean.setLogsdir(new File(alternateOutputDir, bean.getLogsdir()).getAbsolutePath());
					}
				}
			}
		}
	}
	
	private void fixBaseDir(BaseBean bean, File alternateBaseDir)
	{
		if (null == bean.getBasedir())
		{
			bean.setBasedir(alternateBaseDir.getAbsolutePath());
		}
		else
		{
			if (!new File(bean.getBasedir()).isAbsolute())
			{
				bean.setBasedir(new File(alternateBaseDir, bean.getBasedir()).getAbsolutePath());
			}
		}
	}
	
}
