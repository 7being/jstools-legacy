package com.sevenbeing.jstools;

import java.io.File;

import com.sevenbeing.jstools.bean.Configuration;
import com.sevenbeing.jstools.runner.ConfigurationRunner;

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
		
		System.out.println("JSTools is using config file: " + configFile.getAbsolutePath());
		
		Configuration config = Configuration.load(configFile);
		new ConfigurationRunner().go(config);
	}
}
