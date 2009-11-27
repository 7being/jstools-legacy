package com.sevenbeing.jstools.bean;

public class MigrationBean extends BaseBean
{
	public enum Goal
	{
		COMBO, PARTIAL;
	}
	
	private Goal goal;
	private String includes;
	
	public Goal getGoal()
	{
		return goal;
	}
	public void setGoal(Goal goal)
	{
		this.goal = goal;
	}
	public String getIncludes()
	{
		return includes;
	}
	public void setIncludes(String includes)
	{
		this.includes = includes;
	}
	
}
