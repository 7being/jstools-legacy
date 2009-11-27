package com.sevenbeing.jstools.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Configuration extends BaseBean
{
	private List<CompressBean> compressBeans;
	private List<JsUnitBean> jsUnitBeans;
	private List<MigrationBean> migrationBeans;

	public List<CompressBean> getCompress()
	{
		return compressBeans;
	}

	public void setCompress(List<CompressBean> compressBeans)
	{
		this.compressBeans = compressBeans;
	}

	public List<JsUnitBean> getJsunit()
	{
		return jsUnitBeans;
	}

	public void setJsunit(List<JsUnitBean> jsUnitBeans)
	{
		this.jsUnitBeans = jsUnitBeans;
	}
	
	public List<MigrationBean> getMigration()
	{
		return migrationBeans;
	}

	public void setMigrationBeans(List<MigrationBean> migrationBeans)
	{
		this.migrationBeans = migrationBeans;
	}

	public static Configuration load(File configFile)
	{
		configFile = configFile.getAbsoluteFile();
		Reader reader = null;
		try
		{
			reader = new FileReader(configFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		Configuration config = (Configuration) getYamlLoader().load(reader);

		fixBaseDirIfNotProvided(config, configFile);

		return config;
	}

	private static Yaml getYamlLoader()
	{
		Constructor constructor = new Constructor(Configuration.class);

		TypeDescription typeDesc = new TypeDescription(Configuration.class);
		typeDesc.putListPropertyType("migration", MigrationBean.class);
		typeDesc.putListPropertyType("compress", CompressBean.class);
		typeDesc.putListPropertyType("jsunit", JsUnitBean.class);
		constructor.addTypeDescription(typeDesc);

		Loader loader = new Loader(constructor);
		return new Yaml(loader);
	}

	private static void fixBaseDirIfNotProvided(Configuration config, File configFile)
	{
		String topDir = configFile.getParentFile().getAbsolutePath();

		List<BaseBean> beans = new ArrayList<BaseBean>();
		if (null != config.getCompress()) beans.addAll(config.getCompress());
		if (null != config.getJsunit()) beans.addAll(config.getJsunit());
		
		for (BaseBean bean : beans)
		{
			String baseDir = (bean.getBasedir() == null) ? topDir : topDir + File.separator + bean.getBasedir();
			bean.setBasedir(baseDir);
		}
	}
}