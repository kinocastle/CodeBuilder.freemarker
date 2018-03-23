package codebuilder.freemarker;

import java.util.ArrayList;
import java.util.List;

public class TableModel
{
	public static class Column
	{
		private String name;// 列名
		private String type;// 类型
		private String comment;// 列注释
		private String defaults;// 默认值
		private int length;// 长度
		private boolean iskey = false;// 是否主键

		public String getNameLowerCamel() { return lowerCamel(name); }// 小驼峰命名
		public String getNameUpperCamel() { return upperCamel(name); }// 大驼峰命名
		
		public String getName()
		{
			return name;
		}
		public void setName(String name)
		{
			this.name = name;
		}
		public String getType()
		{
			return type;
		}
		public void setType(String type)
		{
			this.type = type;
		}
		public String getDefaults()
		{
			return defaults;
		}
		public void setDefaults(String defaults)
		{
			this.defaults = defaults;
		}
		public String getComment()
		{
			return comment;
		}
		public void setComment(String comment)
		{
			this.comment = comment;
		}
		public int getLength()
		{
			return length;
		}
		public void setLength(int length)
		{
			this.length = length;
		}
		public boolean isIskey()
		{
			return iskey;
		}
		public void setIskey(boolean iskey)
		{
			this.iskey = iskey;
		}
	}

	private String name;// 表名
	private String comment;// 表注释

	public List<Column> columnList = new ArrayList<>();// 表列list
	public String getNameLowerCamel() { return lowerCamel(name); }// 小驼峰命名
	public String getNameUpperCamel() { return upperCamel(name); }// 大驼峰命名

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public static String lowerCamel(String name)
	{
		String[] ss = name.toLowerCase().split("_");
		String str = ss[0];
		for(int i = 1; i < ss.length; i++)
		{
			str += ss[i].substring(0, 1).toUpperCase() + ss[i].substring(1);
		}
		return str;
	}

	public static String upperCamel(String name)
	{
		String[] ss = name.toLowerCase().split("_");
		String str = "";
		for(String s : ss)
		{
			str += s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		return str;
	}
}
