package com.sevenbeing.jstools.runner;

import java.util.List;

import com.sevenbeing.jstools.bean.CompressBean;
import com.sevenbeing.jstools.bean.Configuration;
import com.sevenbeing.jstools.bean.MigrationBean;

public class MediaMigrationRunner extends AbstractRunner
{
	List<CompressBean> compressBeans = null;

	@Override
	protected void run(Object configuration)
	{
		Configuration config = (Configuration) configuration;
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
