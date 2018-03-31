package codebuilder.freemarker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import codebuilder.freemarker.BuilderConfig.Code;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class Builder
{
	private BuilderConfig config;

	public Builder(BuilderConfig config)
	{
		this.config = config;
	}

	public void build()
	{
		TableDao dao = null;
		try
		{
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
			configuration.setDefaultEncoding(config.charsetText);
			configuration.setDirectoryForTemplateLoading(new File(config.templatesPath));

			Class<?> cls = Class.forName(config.connectType);
			dao = (TableDao)cls.newInstance();
			dao.initConnect(config.connectUrl);
			for(Code code : config.builderCodes)
			{
				TableModel table = dao.queryTable(code.table);
				Map<String, Object> param = getParam(table, code);
				for(BuilderConfig.Template tpl : config.templates)
				{
					if(!tpl.name.equals(config.builderTemplatename))
					{
						continue;
					}
					if(tpl.comment != null && !"".equals(tpl.comment) && !code.comment.equals(tpl.comment))
					{
						continue;
					}
					String viewpath = tpl.viewpath;
					String path = config.builderProject + tpl.path
						.replace("{src}", config.builderSrc)
						.replace("{web}", config.builderWeb)
						.replace("{model}", code.model)
						.replace("{module}", code.module)
						.replace("{namespace}", code.namespace)
						.replace("{webmodule}", code.webmodule)
						.replace("//", "/");
					try
					{
						Template template = configuration.getTemplate(viewpath);
						File file = createNewFile(path);
						template.process(param, new FileWriter(file));
						System.out.println("生成成功：" + path);
					}
					catch(Exception e)
					{
						System.out.println("生成失败：" + path + "，" + e.getMessage());
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("生成失败：" + e.getMessage());
		}
		finally
		{
			if(dao != null)
			{
				dao.close();
			}
		}
	}

	private Map<String, Object> getParam(TableModel table, Code code)
	{
		Map<String, Object> map = new HashMap<>();
		map.put("params", config.params);
		map.put("developer", config.builderDeveloper);
		map.put("namespace", code.namespace);
		map.put("frame", code.frame);
		map.put("model", code.model);
		map.put("module", code.module);
		map.put("webmodule", code.webmodule);
		map.put("columnList", table.columnList);
		map.put("table", table);
		return map;
	}

	private File createNewFile(String path) throws IOException
	{
		File file = new File(path);
		if(file.exists())
		{
			return file;
		}
		if(!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		return file;
	}

	public static String readTextFile(String filePath)
	{
		File file = new File(filePath);
		StringBuilder sb = new StringBuilder();
		if(file.isFile())
		{
			try(BufferedReader reader = new BufferedReader(new FileReader(file)))// try-with-resources
			{
				String s;
				while((s = reader.readLine()) != null)
				{
					sb.append(s);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String getLocation(String name)
	{
		String location = Builder.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if(location.endsWith(".jar"))
		{
			location = location.substring(0, location.lastIndexOf("/") + 1);
		}
		return location + name;
	}

	public static void main(String[] args)
	{
//		String configPath = Builder.class.getResource("/Builder.xml").getPath();
//		String configPath = Builder.class.getClassLoader().getResource("Builder.xml").getPath();
		String configPath = getLocation("Builder.xml");
		if(args.length > 0)
		{
			configPath = System.getProperty("user.dir") + "/" + args[0];
			configPath = configPath.replace("//", "/");
		}
		BuilderConfig builder = new BuilderParser().parse(readTextFile(configPath));
		Builder builderUtil = new Builder(builder);
		builderUtil.build();
	}
}
