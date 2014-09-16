package com.yahoo.markcwal.gridimagesearch.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Settings implements Serializable{
	
	private static final long serialVersionUID = 5612033345265423886L;
	
	private ArrayList<String> sizes;
	private ArrayList<String> colors;
	private ArrayList<String> types;
	
	public static final String DEFAULT = "default";
	
	private int size_index;
	private int color_index;
	private int type_index;
	private String site;
	
	public Settings() {
		// TODO Auto-generated constructor stub
		size_index = 0;
		color_index = 0;
		type_index = 0;
		site = "";
		sizes = new ArrayList<String>();
		colors = new ArrayList<String>();
		types = new ArrayList<String>();
		initializeArrays();
	}
	
	public void initializeArrays()
	{
		sizes.add(DEFAULT);
		sizes.add("icon");
		sizes.add("medium");
		sizes.add("xxlarge");
		sizes.add("huge");
		
		colors.add(DEFAULT);
		colors.add("black");
		colors.add("blue");
		colors.add("brown");
		colors.add("gray");
		colors.add("green");
		colors.add("orange");
		colors.add("pink");
		colors.add("purple");
		colors.add("red");
		colors.add("teal");
		colors.add("white");
		colors.add("yellow");
		
		types.add(DEFAULT);
		types.add("face");
		types.add("photo");
		types.add("clipart");
		types.add("lineart");
	}
	
	public int getSizeIndex()
	{
		return size_index;
	}
	
	public void setSize(int index)
	{
		if (index < 0 || index > 4)
		{
			size_index = 0;
		}
		else
		{
			size_index = index;
		}
	}
	
	public String getSize()
	{
		return sizes.get(size_index);
	}
	
	public int getColorIndex()
	{
		return color_index;
	}
	
	public void setColor(int index)
	{
		
		if (index < 0 || index > 12)
		{
			color_index = 0;
		}
		else
		{
			color_index = index;
		}
	}
	
	public String getColor()
	{
		return colors.get(color_index);
	}
	
	public int getTypeIndex()
	{
		return type_index;
	}
	
	public void setType(int index)
	{
		if (index < 0 || index > 4)
		{
			type_index = 0;
		}
		else
		{
			type_index = index;
		}
	}
	
	public String getType()
	{
		return types.get(type_index);
	}
	
	public void setSite(String site)
	{
		this.site = site;
	}
	
	public String getSite()
	{
		return site;
	}

}
