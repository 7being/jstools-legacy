package com.sevenbeing.jstools.bean;

import java.io.File;
import java.lang.reflect.Method;

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

	public File getFileByProperty(String fieldName)
	{
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String fieldValue = null;

		Method method;
		try
		{
			method = this.getClass().getMethod(methodName, new Class[]{});
			fieldValue = (String) method.invoke(this, new Object[]{});
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Property " + fieldName + " not found.");
		}

		return new File(fieldValue).isAbsolute() ? new File(fieldValue) : new File(basedir, fieldValue);
	}
}
