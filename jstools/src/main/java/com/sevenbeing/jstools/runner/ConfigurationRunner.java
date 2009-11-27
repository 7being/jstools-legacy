package com.sevenbeing.jstools.runner;

import java.util.List;

import com.sevenbeing.jstools.bean.CompressBean;
import com.sevenbeing.jstools.bean.Configuration;
import com.sevenbeing.jstools.bean.JsUnitBean;

public class ConfigurationRunner extends AbstractRunner
{
	protected void run(Object bean)
	{
		Configuration config = (Configuration) bean;

		runCompress(config.getCompress());
		runJsunit(config.getJsunit());
	}

	private void runCompress(List<CompressBean> compressBeans)
	{
		if (null == compressBeans) return;

		YUICompressorRunner runner = new YUICompressorRunner();

		for (CompressBean bean : compressBeans)
		{
			runner.go(bean);
		}
	}

	private void runJsunit(List<JsUnitBean> jsUnitBeans)
	{
		if (null == jsUnitBeans) return;
		
		JsUnitRunner runner = new JsUnitRunner();
		
		for (JsUnitBean bean : jsUnitBeans)
		{
			runner.go(bean);
		}
	}
}
