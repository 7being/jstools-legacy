package com.sevenbeing.jstools.runner;

import com.sevenbeing.jstools.bean.BaseBean;

public abstract class AbstractRunner
{
	public void go(Object bean)
	{
		if (((BaseBean)bean).isSkip()) return;
		run((Object) bean);
	}
	
	protected abstract void run(Object bean);
}
