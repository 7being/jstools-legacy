package com.sevenbeing.jstools.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Chmod;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.apache.tools.ant.types.FileSet;

public class FileUtility
{
	private static Project ant;

	static
	{
		ant = new Project();
		ant.init();
	}

	public static long copy(InputStream input, OutputStream output)
			throws IOException
	{
		byte[] buffer = new byte[1024 * 4];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer)))
		{
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
	
	public static void copy(File fromFile, File toFile)
	{
		Copy copy = (Copy) ant.createTask("copy");
		copy.setFile(fromFile);
		copy.setTofile(toFile);
		copy.execute();
	}

	public static void copydir(File fromDir, File toDir)
	{
		Copy copy = (Copy) ant.createTask("copy");
		copy.setTodir(toDir);
		FileSet files = new FileSet();
		files.setDir(fromDir);
		copy.addFileset(files);
		copy.execute();
	}

	public static void chmod(File dir, String includes, String perm)
	{
		Chmod chmod = (Chmod) ant.createTask("chmod");
		chmod.setDir(dir);
		chmod.setPerm(perm);
		chmod.setIncludes(includes);
		chmod.execute();
	}

	public static void mkdir(File path)
	{
		Mkdir mkdir = (Mkdir) ant.createTask("mkdir");
		mkdir.setDir(path);
		mkdir.execute();
	}

	public static void unzip(File zippedFile, File toDir)
	{
		Expand unzip = (Expand) ant.createTask("unzip");
		unzip.setSrc(zippedFile);
		unzip.setDest(toDir);
		unzip.execute();
	}

	public static List<File> scanFiles(File baseDir, String[] includes,
			String[] excludes)
	{
		List<File> result = new ArrayList<File>();
		Set<String> already = new HashSet<String>();

		if (includes == null)
			includes = new String[0];
		if (excludes == null)
			excludes = new String[0];

		List<String> excludeList = new ArrayList<String>();
		excludeList.addAll(Arrays.asList(excludes));
		excludeList.add("**/.*/");
		excludes = excludeList.toArray(new String[0]);

		for (String include : includes)
		{
			DirectoryScanner scanner = new DirectoryScanner();
			scanner.setIncludes(new String[] { include });
			scanner.setExcludes(excludes);
			scanner.setBasedir(baseDir);
			scanner.scan();
			for (String file : scanner.getIncludedFiles())
			{
				if (!already.contains(file))
				{
					result.add(new File(baseDir, file).getAbsoluteFile());
					already.add(file);
				}
			}
		}

		return result;
	}

	public static File createTempFileFromJar(String path)
	{
		File result = null;

		if (!path.startsWith(File.separator))
		{
			path = File.separator + path;
		}

		try
		{
			result = File.createTempFile("tmpfromjar", "");
			OutputStream output = new FileOutputStream(result);

			InputStream input = FileUtility.class.getResourceAsStream(path);

			copy(input, output);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

}
