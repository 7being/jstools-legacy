package com.sevenbeing.jstools.utility;

import java.io.File;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class AntUtility
{
	public static void runTaskFromBuildfile(File buildFile, String target)
	{
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		
		Project prj = new Project();
		prj.init();
		prj.setBasedir(buildFile.getParentFile().getAbsolutePath());
		prj.setProjectReference(helper);

		helper.parse(prj, buildFile);

		DefaultLogger logger = new DefaultLogger();
		logger.setErrorPrintStream(System.err);
		logger.setOutputPrintStream(System.out);

		prj.addBuildListener(logger);
		prj.executeTarget(target);
	}
	
}