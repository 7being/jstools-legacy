package com.sevenbeing.jstools.runner;

import java.util.List;

import com.sevenbeing.jstools.bean.CompressBean;
import com.sevenbeing.jstools.bean.JsToolsBean;
import com.sevenbeing.jstools.bean.MigrationBean;

public class MediaMigrationRunner extends AbstractRunner
{
	List<CompressBean> compressBeans = null;

	@Override
	protected void run(Object configuration)
	{
		JsToolsBean config = (JsToolsBean) configuration;
		if (null == config.getMigration()) return;
		compressBeans = config.getCompress();
		
		for (MigrationBean bean : config.getMigration())
		{
			migrate(bean);
		}
	}
	
	private void migrate(MigrationBean bean)
	{
		
	}
	
	private void scanHTMLTemplates()
	{
		
	}
	
	private void updateBlockWithCombo()
	{
		
	}
	
	private void updateBlockWithPartial()
	{
		
	}

	private void resolveMediaDependency()
	{
		
	}
}
