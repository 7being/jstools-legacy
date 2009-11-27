package com.sevenbeing.jstools.runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.mozilla.javascript.tools.ToolErrorReporter;

import com.sevenbeing.jstools.bean.CompressBean;
import com.sevenbeing.jstools.utility.FileUtility;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class YUICompressorRunner extends AbstractRunner
{
	private static enum Type
	{
		ALL, MIN;
	}

	protected void run(Object compressBean)
	{
		CompressBean bean = (CompressBean) compressBean;
		File tmp = FileUtility.concat(new File(bean.getBasedir()), bean.getIncludes(), bean.getExcludes());

		allInOneAny(bean, tmp);

		try
		{
			switch (bean.getType())
			{
				case JS:
					minInOneJS(bean, tmp);
					break;
				case CSS:
					minInOneCSS(bean, tmp);
					break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		tmp.delete();
	}

	private void allInOneAny(CompressBean bean, File tempAll)
	{
		if (null != bean.getAllfile())
		{
			File realAll = getFinalName(bean, Type.ALL);
			FileUtility.copy(tempAll, realAll);
		}
	}

	private void minInOneJS(CompressBean bean, File tempAll) throws Exception
	{
		if (null != bean.getMinfile())
		{
			JavaScriptCompressor compressor = new JavaScriptCompressor(new InputStreamReader(new FileInputStream(
					tempAll)), new ToolErrorReporter(true));

			File min = getFinalName(bean, Type.MIN);

			Writer writer = new OutputStreamWriter(new FileOutputStream(min), "UTF-8");
			compressor.compress(writer, Integer.MAX_VALUE, false, false, false, false);

			writer.close();
		}

	}

	private void minInOneCSS(CompressBean bean, File tempAll) throws Exception
	{
		if (null != bean.getMinfile())
		{
			CssCompressor compressor = new CssCompressor(new InputStreamReader(new FileInputStream(tempAll)));

			File min = getFinalName(bean, Type.MIN);

			Writer writer = new OutputStreamWriter(new FileOutputStream(min), "UTF-8");
			compressor.compress(writer, Integer.MAX_VALUE);

			writer.close();
		}
	}

	private File getFinalName(CompressBean bean, Type type)
	{
		boolean isMin = (type == Type.MIN);

		String fileName = isMin ? bean.getMinfile() : bean.getAllfile();
		String endWith = (isMin ? "min" : "all") + "." + bean.getType().name().toLowerCase();

		String filePath = bean.getBasedir() + File.separator + fileName + "-" + bean.getVersion() + "."
				+ endWith;
		return new File(filePath);
	}

}
