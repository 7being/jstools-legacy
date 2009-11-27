package com.sevenbeing.jstools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sevenbeing.jstools.bean.Configuration;
import com.sevenbeing.jstools.runner.AbstractRunner;
import com.sevenbeing.jstools.runner.ConfigurationRunner;

public class ConfigurationRunnerTest
{
	private AbstractRunner runner;
	
	@Before
	public void setUp()
	{
		runner = new ConfigurationRunner();
	}

	@Test
	public void run() throws IOException
	{
		/*
		Configuration config = Configuration.load(new File("src/test/resources/config-test.yaml"));
		runner.go(config);
		
		Assert.assertTrue(contentEquals(
			new FileInputStream(new File("src/test/resources/js/minfile-0.0.1.min.js.expected")),
			new FileInputStream(new File("src/test/resources/js/minfile-0.0.1.min.js"))));
		*/
	}

	@After
	public void tearDown()
	{
		//new File("src/test/resources/js/minfile-0.0.1.min.js").delete();
	}

	private boolean contentEquals(InputStream input1, InputStream input2)
			throws IOException
	{
		if (!(input1 instanceof BufferedInputStream))
		{
			input1 = new BufferedInputStream(input1);
		}
		if (!(input2 instanceof BufferedInputStream))
		{
			input2 = new BufferedInputStream(input2);
		}

		int ch = input1.read();
		while (-1 != ch)
		{
			int ch2 = input2.read();
			if (ch != ch2) { return false; }
			ch = input1.read();
		}

		int ch2 = input2.read();
		return (ch2 == -1);
	}

}
