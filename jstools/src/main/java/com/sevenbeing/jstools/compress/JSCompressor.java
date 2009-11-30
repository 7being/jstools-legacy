package com.sevenbeing.jstools.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.tools.ToolErrorReporter;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class JSCompressor extends Compressor
{
	private static String SAFE_FILE_SEPARATOR = ";";
	//TODO output this file to a more specify target dir
	private static String LOG_JS_ON_FAIL = "jstools.fail.js";

	@Override
	public void compress(File originalFile, File compressedFile) throws CompressException
	{
		JavaScriptCompressor compressor;
		try
		{
			compressor = new JavaScriptCompressor(new InputStreamReader(new FileInputStream(originalFile)),
					new ToolErrorReporter(true));

			Writer writer = new OutputStreamWriter(new FileOutputStream(compressedFile), "UTF-8");
			compressor.compress(writer, Integer.MAX_VALUE, false, false, false, false);

			writer.close();
		}
		catch (EvaluatorException ee)
		{
			try
			{
				FileUtils.copyFile(originalFile, new File(LOG_JS_ON_FAIL));
				System.err.println("Compress javascript failed, issue file copied to " + new File(LOG_JS_ON_FAIL).getAbsolutePath());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new CompressException(ee);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

	}
	
	@Override
	public void compress(List<File> inputFileList, File outputFile) throws CompressException
	{
		try
		{
			super.compress(inputFileList, outputFile);
		}
		catch (CompressException e)
		{
			traceBadJsFile(inputFileList);
			throw e;
		}
	}

	@Override
	public String getFileSafeSeparator()
	{
		return SAFE_FILE_SEPARATOR;
	}
	
	private void traceBadJsFile(List<File> inputFileList)
	{
		File dummy = null;
		try
		{
			dummy = File.createTempFile("tmp", "tmp");
			for (File input : inputFileList)
			{
				try
				{
					compress(input, dummy);
				}
				catch (CompressException e)
				{
					System.err.println("Javascript error in " + input.getAbsolutePath());
				}
			}
			dummy.delete();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
