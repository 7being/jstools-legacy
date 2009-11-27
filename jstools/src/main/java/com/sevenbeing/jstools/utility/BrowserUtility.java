package com.sevenbeing.jstools.utility;

public class BrowserUtility
{
	private enum PlatformType
	{
		MAC, WINDOWS, LINUX;

		public static PlatformType resolve()
		{
			String osName = System.getProperty("os.name");
			osName = osName.substring(0, osName.indexOf(" "));
			return PlatformType.valueOf(osName.toUpperCase());
		}

		public String path()
		{
			String path = null;

			switch (this)
			{
				case LINUX:
					path = "/unix";
				case MAC:
					path = "/mac";
			}
			return path;
		}
	}

	private enum BrowserType
	{
		DEFAULT, FIREFOX, MOZILLA, SAFARI, OPERA9;

		public static BrowserType resolve(String browserName)
		{
			return BrowserType.valueOf(browserName.toUpperCase());
		}

		public String startSh()
		{
			return "/start-" + name().toLowerCase() + ".sh";
		}
		
		public String stopSh()
		{
			return "/stop-" + name().toLowerCase() + ".sh";
		}
	}

	public static boolean isDefault(String browserName)
	{
		return browserName == "default";
	}
	
	private static String getBrowserSpec(String browserName, boolean withKillCmd, String absParentOfBin)
	{
		String result = null;
		
		PlatformType osType = PlatformType.resolve();
		BrowserType browserType = BrowserType.resolve(browserName);
		
		if (osType == PlatformType.WINDOWS || browserType == BrowserType.DEFAULT)
		{
			result = browserName;
		}
		else
		{

			// start cmd
			result = absParentOfBin + "/bin" + osType.path() + browserType.startSh();
			if (withKillCmd)
			{
				// jsunit predefined delimiter
				result += ";";
				// stop cmd
				result += absParentOfBin + "/bin" + osType.path() + browserType.stopSh();
			}
		}
	
		
		return result;
	}

	public static String[] getBrowserSpecs(String[] browsers, boolean withKillCommand, String absParentOfBin)
	{
		String[] result = new String[browsers.length];
		for (int i = 0; i < browsers.length; i++)
		{
			result[i] = getBrowserSpec(browsers[i], withKillCommand, absParentOfBin);
		}
		
		return result;
	}

}
