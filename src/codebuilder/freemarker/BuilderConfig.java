package codebuilder.freemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuilderConfig
{
	public class Code
	{
		public String frame;
		public String namespace;
		public String module;
		public String webmodule;
		public String table;
		public String model;
		public String comment;
	}
	public class Template
	{
		public String name;
		public String id;
		public String viewpath;
		public String path;
		public String comment;
	}
	//charset
	public String charsetOutputEncoding;
	public String charsetText;
	//templates
	public String templatesPath;
	public List<Template> templates = new ArrayList<>();
	//params
	public Map<String, String> params = new HashMap<>();
	//connect
	public String connectName;
	public String connectType;
	public String connectUrl;
	//builder
	public String builderDeveloper;
	public String builderProject;
	public String builderTemplatename;
	public String builderSrc;
	public String builderWeb;
	public List<Code> builderCodes = new ArrayList<>();
}
