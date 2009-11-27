package com.sevenbeing.jstools.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Chmod;
import org.apache.tools.ant.taskdefs.Concat;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.apache.tools.ant.types.FileList;
import org.apache.tools.ant.types.FileSet;

public class FileUtility
{
	private static Project ant;

	static
	{
		ant = new Project();
		ant.init();
	}

	public static long copy(InputStream input, OutputStream output) throws IOException
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

	public static File concat(File baseDir, String[] includes)
	{
		File result = null;
		try
		{
			result = File.createTempFile("merge", "tmp");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		String fileNames = "";

		for (String name : includes)
		{
			DirectoryScanner scanner = new DirectoryScanner();
			scanner.setIncludes(new String[] { name });
			scanner.setExcludes(new String[] { "**/.*/" });
			scanner.setBasedir(baseDir);
			scanner.scan();
			for (String file : scanner.getIncludedFiles())
			{
				if (!fileNames.contains(file)) fileNames += (file + ",");
			}
		}

		fileNames = (fileNames.length() > 0) ? fileNames.substring(0, fileNames.length() - 1) : fileNames;

		FileList fileList = new FileList();
		fileList.setProject(ant);
		fileList.setDir(baseDir);
		fileList.setFiles(fileNames);

		Concat concat = (Concat) ant.createTask("concat");
		concat.setAppend(true);
		concat.setForce(true);
		concat.setDestfile(result);
		concat.addFilelist(fileList);

		concat.execute();
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

	public static void main(String args[])
	{
		createTempFileFromJar("jsunit2.3.zip");
	}
}
