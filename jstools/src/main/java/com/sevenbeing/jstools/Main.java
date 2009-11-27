package com.sevenbeing.jstools;

import java.io.File;

import com.sevenbeing.jstools.bean.JsToolsBean;
import com.sevenbeing.jstools.runner.JsToolsRunner;

public class Main
{
	public static void main(String args[])
	{
		File configFile = null;
		if (args.length == 0)
		{
			configFile = new File("jstools.yaml");
			if (!configFile.exists())
			{
				throw new IllegalArgumentException("Mssing jstools.yaml in " + new File("").getAbsolutePath());
			}
		}
		if (args.length > 0)
		{
			configFile = new File(args[0]);
			if (!configFile.exists())
			{
				throw new IllegalArgumentException("File " + args[0] + " doesn't exists.");
			}
		}
		
		configFile = configFile.getAbsoluteFile();
		System.out.println("JSTools is using config file: " + configFile);
		
		JsToolsBean config = new JsToolsLoader().load(
			configFile, 
			configFile.getParentFile(), 
			configFile.getParentFile()
		);
		new JsToolsRunner().go(config);
	}
}
