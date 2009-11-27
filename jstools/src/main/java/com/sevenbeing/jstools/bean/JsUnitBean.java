package com.sevenbeing.jstools.bean;

public class JsUnitBean extends BaseBean
{
	private String source;
	private String test;
	private String workdir;
	private String logsdir = "";
	private String[] browsers;
	private Integer port = 8080;
	private Boolean closebro = true;
	
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getTest()
	{
		return test;
	}
	public void setTest(String test)
	{
		this.test = test;
	}
	public String getWorkdir()
	{
		return workdir;
	}
	public void setWorkdir(String workdir)
	{
		this.workdir = workdir;
	}
	public String getLogsdir()
	{
		return logsdir;
	}
	public void setLogsdir(String logsdir)
	{
		this.logsdir = logsdir;
	}
	public String[] getBrowsers()
	{
		return browsers;
	}
	public void setBrowsers(String[] browsers)
	{
		this.browsers = browsers;
	}
	public Integer getPort()
	{
		return port;
	}
	public void setPort(Integer port)
	{
		this.port = port;
	}
	public Boolean getClosebro()
	{
		return closebro;
	}
	public void setClosebro(Boolean closebro)
	{
		this.closebro = closebro;
	}

}
