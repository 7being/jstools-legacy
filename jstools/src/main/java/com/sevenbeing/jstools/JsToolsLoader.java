package com.sevenbeing.jstools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.sevenbeing.jstools.bean.BaseBean;
import com.sevenbeing.jstools.bean.JsToolsBean;

public class JsToolsLoader
{

	public static JsToolsBean load(File configFile, File alternateBaseDir)
	{
		configFile = configFile.getAbsoluteFile();
		alternateBaseDir = alternateBaseDir.getAbsoluteFile();
		
		Reader reader = null;
		try
		{
			reader = new FileReader(configFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		JsToolsBean config = (JsToolsBean) JsToolsBean.getYamlLoader().load(reader);

		fixBeansBaseDirTo(config, alternateBaseDir);

		return config;
	}

	private static void fixBeansBaseDirTo(JsToolsBean config, File alternateBaseDir)
	{
		// fix top level base dir for JsToolsBean
		fixBaseDir(config, alternateBaseDir);
		
		File correctBaseDir = new File(config.getBasedir());
		
		// fix children base dir of JsToolsBean
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if (null != config.getCompress()) beans.addAll(config.getCompress());
		if (null != config.getJsunit()) beans.addAll(config.getJsunit());
		
		for (BaseBean bean : beans)
		{
			fixBaseDir(bean, correctBaseDir);
		}
		
	}
	
	private static void fixBaseDir(BaseBean bean, File alternateBaseDir)
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
