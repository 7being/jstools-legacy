package com.sevenbeing.jstools.bean;

import com.sevenbeing.jstools.compress.FileType;

public class CompressBean extends BaseBean
{
	private FileType type;
	private String version;
	private String minfile;
	private String allfile;
	private String[] includes;
	private String[] excludes;

	public FileType getType()
	{
		return type;
	}

	public void setType(FileType type)
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

	public String[] getExcludes()
	{
		return excludes;
	}

	public void setExcludes(String[] excludes)
	{
		this.excludes = excludes;
	}

}
