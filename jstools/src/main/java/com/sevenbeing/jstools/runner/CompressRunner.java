package com.sevenbeing.jstools.runner;

import java.io.File;
import java.util.List;

import com.sevenbeing.jstools.bean.CompressBean;
import com.sevenbeing.jstools.compress.CompressException;
import com.sevenbeing.jstools.compress.Compressor;
import com.sevenbeing.jstools.utility.FileUtility;

public class CompressRunner extends AbstractRunner
{
	private static enum Type
	{
		ALL, MIN;
	}

	protected void run(Object compressBean)
	{
		CompressBean bean = (CompressBean) compressBean;

		List<File> fileList = FileUtility.scanFiles(new File(bean.getBasedir()), bean.getIncludes(), bean.getExcludes());

		Compressor compressor = Compressor.getCompressor(bean.getType());
		
		if (null != bean.getAllfile())
		{
			File result = getFinalFile(bean, Type.ALL);
			compressor.combine(fileList, result);
		}
		
		if (null != bean.getMinfile())
		{
			File result = getFinalFile(bean, Type.MIN);
			try
			{
				compressor.compress(fileList, result);
			}
			catch (CompressException e) {/*ignore*/}
		}
	}

	private File getFinalFile(CompressBean bean, Type type)
	{
		boolean isMin = (type == Type.MIN);

		String fileName = isMin ? bean.getMinfile() : bean.getAllfile();
		String endWith = (isMin ? "min" : "all") + "." + bean.getType().name().toLowerCase();

		String filePath = fileName + "-" + bean.getVersion() + "." + endWith;
		return new File(filePath);
	}

}
