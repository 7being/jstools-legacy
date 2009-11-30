package com.sevenbeing.jstools.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;

public abstract class Compressor
{
	private static String DEFAULT_FILE_SEPARATOR = "";

	public String getFileSafeSeparator()
	{
		return DEFAULT_FILE_SEPARATOR;
	}

	public abstract void compress(File inputFile, File outputFile) throws CompressException;

	public void compress(List<File> inputFileList, File outputFile) throws CompressException
	{
		File tmpCombinedFile = null;
		try
		{
			tmpCombinedFile = File.createTempFile("tmp", "tmp");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		combine(inputFileList, tmpCombinedFile);
		compress(tmpCombinedFile, outputFile);
		
		tmpCombinedFile.delete();
	}

	public void combine(List<File> inputFileList, File outputFile)
	{
		try
		{
			OutputStreamWriter resultWriter = new OutputStreamWriter(new FileOutputStream(outputFile));

			for (File file : inputFileList)
			{
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
				IOUtils.copy(isr, resultWriter);
				isr.close();
				IOUtils.write(getFileSafeSeparator(), resultWriter);
			}

			resultWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Compressor getCompressor(FileType type)
	{
		switch (type)
		{
			case JS:
				return new JSCompressor();
			case CSS:
				return new CSSCompressor();
		}
		return null;
	}
}
