package com.sevenbeing.jstools.bean;

import java.util.List;

import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class JsToolsBean extends BaseBean
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
	
	public static Yaml getYamlLoader()
	{
		Constructor constructor = new Constructor(JsToolsBean.class);

		TypeDescription typeDesc = new TypeDescription(JsToolsBean.class);
		typeDesc.putListPropertyType("migration", MigrationBean.class);
		typeDesc.putListPropertyType("compress", CompressBean.class);
		typeDesc.putListPropertyType("jsunit", JsUnitBean.class);
		constructor.addTypeDescription(typeDesc);

		Loader loader = new Loader(constructor);
		return new Yaml(loader);
	}

}