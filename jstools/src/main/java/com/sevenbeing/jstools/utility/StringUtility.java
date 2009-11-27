package com.sevenbeing.jstools.utility;

public class StringUtility
{
	public static String join(String[] strings, String delimiter)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strings.length; i++)
		{
			if (i != 0) sb.append(delimiter);
			sb.append(strings[i]);
		}
		return sb.toString();
	}
}
