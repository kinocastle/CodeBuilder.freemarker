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
	private BuilderModel builder;
	private static DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

	public BuilderModel parse(String xmlString)
	{
		try
		{
			builder = new BuilderModel();
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
		return builder;
	}

	private void parseConfig(Element e)
	{
		Element charset = (Element)e.getElementsByTagName("charset").item(0);
		builder.charsetOutputEncoding = charset.getAttribute("outputEncoding");
		builder.charsetText = charset.getTextContent();
		parseTemplates((Element)e.getElementsByTagName("templates").item(0));
		parseParams((Element)e.getElementsByTagName("params").item(0));
		Element connect = (Element)e.getElementsByTagName("connect").item(0);
		builder.connectName = connect.getAttribute("name");
		builder.connectType = connect.getAttribute("type");
		builder.connectUrl = connect.getAttribute("url");
		parseBuilder((Element)e.getElementsByTagName("builder").item(0));
	}

	private void parseTemplates(Element e)
	{
		builder.templatesPath = e.getAttribute("path");
		NodeList ns = e.getElementsByTagName("template");
		for(int i = 0; i < ns.getLength(); i++)
		{
			Element n = (Element)ns.item(i);
			BuilderModel.Template template = builder.new Template();
			template.name = n.getAttribute("name");
			template.id = n.getAttribute("id");
			template.viewpath = n.getAttribute("viewpath");
			template.path = n.getAttribute("path");
			template.comment = n.getAttribute("comment");
			builder.templates.add(template);
		}
	}

	private void parseParams(Element e)
	{
		NodeList ns = e.getElementsByTagName("param");
		for(int i = 0; i < ns.getLength(); i++)
		{
			Element n = (Element)ns.item(i);
			builder.params.put(n.getAttribute("name"), "value");
		}
	}

	private void parseBuilder(Element e)
	{
		builder.builderDeveloper = e.getAttribute("developer");
		builder.builderProject = e.getAttribute("project");
		builder.builderTemplatename = e.getAttribute("templatename");
		builder.builderSrc = e.getAttribute("src");
		builder.builderWeb = e.getAttribute("web");
		NodeList ns = e.getElementsByTagName("code");
		for(int i = 0; i < ns.getLength(); i++)
		{
			Element n = (Element)ns.item(i);
			BuilderModel.Code code = builder.new Code();
			code.frame = n.getAttribute("frame");
			code.namespace = n.getAttribute("namespace");
			code.module = n.getAttribute("module");
			code.webmodule = n.getAttribute("webmodule");
			code.table = n.getAttribute("table");
			code.model = n.getAttribute("model");
			code.comment = n.getAttribute("comment");
			builder.builderCodes.add(code);
		}
	}
}
