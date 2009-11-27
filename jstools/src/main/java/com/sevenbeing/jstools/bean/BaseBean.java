package com.sevenbeing.jstools.bean;

public class BaseBean
{
	private String id;
	private String basedir;
	private Boolean skip = false;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getBasedir()
	{
		return basedir;
	}

	public void setBasedir(String basedir)
	{
		this.basedir = basedir;
	}

	public Boolean isSkip()
	{
		return skip;
	}

	public void setSkip(Boolean skip)
	{
		this.skip = skip;
	}
}
