package codebuilder.freemarker;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BuilderParser
{
	private BuilderConfig config;
	private static DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

	public BuilderConfig parse(String xmlString)
	{
		try
		{
			config = new BuilderConfig();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			parseConfig((Element)builder.parse(new ByteArrayInputStream(xmlString.getBytes("UTF-8"))).getElementsByTagName("config").item(0));
		}
		catch(SAXException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		return config;
	}

	private void parseConfig(Element e)
	{
		Element charset = (Element)e.getElementsByTagName("charset").item(0);
		config.charsetOutputEncoding = charset.getAttribute("outputEncoding");
		config.charsetText = charset.getTextContent();
		parseTemplates((Element)e.getElementsByTagName("templates").item(0));
		parseParams((Element)e.getElementsByTagName("params").item(0));
		Element connect = (Element)e.getElementsByTagName("connect").item(0);
		config.connectName = connect.getAttribute("name");
		config.connectType = connect.getAttribute("type");
		config.connectUrl = connect.getAttribute("url");
		parseBuilder((Element)e.getElementsByTagName("builder").item(0));
	}

	private void parseTemplates(Element e)
	{
		config.templatesPath = e.getAttribute("path");
		NodeList ns = e.getElementsByTagName("template");
		for(int i = 0; i < ns.getLength(); i++)
		{
			Element n = (Element)ns.item(i);
			BuilderConfig.Template template = config.new Template();
			template.name = n.getAttribute("name");
			template.id = n.getAttribute("id");
			template.viewpath = n.getAttribute("viewpath");
			template.path = n.getAttribute("path");
			template.comment = n.getAttribute("comment");
			config.templates.add(template);
		}
	}

	private void parseParams(Element e)
	{
		NodeList ns = e.getElementsByTagName("param");
		for(int i = 0; i < ns.getLength(); i++)
		{
			Element n = (Element)ns.item(i);
			config.params.put(n.getAttribute("name"), "value");
		}
	}

	private void parseBuilder(Element e)
	{
		config.builderDeveloper = e.getAttribute("developer");
		config.builderProject = e.getAttribute("project");
		config.builderTemplatename = e.getAttribute("templatename");
		config.builderSrc = e.getAttribute("src");
		config.builderWeb = e.getAttribute("web");
		NodeList ns = e.getElementsByTagName("code");
		for(int i = 0; i < ns.getLength(); i++)
		{
			Element n = (Element)ns.item(i);
			BuilderConfig.Code code = config.new Code();
			code.frame = n.getAttribute("frame");
			code.namespace = n.getAttribute("namespace");
			code.module = n.getAttribute("module");
			code.webmodule = n.getAttribute("webmodule");
			code.table = n.getAttribute("table");
			code.model = n.getAttribute("model");
			code.comment = n.getAttribute("comment");
			config.builderCodes.add(code);
		}
	}
}
