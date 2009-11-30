package com.sevenbeing.jstools.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.yahoo.platform.yui.compressor.CssCompressor;

public class CSSCompressor extends Compressor
{
	@Override
	public void compress(File inputFile, File outputFile)
	{
		CssCompressor compressor;
		try
		{
			compressor = new CssCompressor(new InputStreamReader(new FileInputStream(inputFile)));
			Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");
			compressor.compress(writer, Integer.MAX_VALUE);

			writer.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
