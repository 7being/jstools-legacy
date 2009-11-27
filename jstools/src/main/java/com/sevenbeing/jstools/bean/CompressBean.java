package com.sevenbeing.jstools.bean;

public class CompressBean extends BaseBean
{
	private Type type;
	private String version;
	private String minfile;
	private String allfile;
	private String[] includes;
	
	public enum Type
	{
		JS, CSS;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}
	public String getMinfile()
	{
		return minfile;
	}
	public void setMinfile(String minfile)
	{
		this.minfile = minfile;
	}
	public String getAllfile()
	{
		return allfile;
	}
	public void setAllfile(String allfile)
	{
		this.allfile = allfile;
	}
	public String[] getIncludes()
	{
		return includes;
	}
	public void setIncludes(String[] includes)
	{
		this.includes = includes;
	}
}
