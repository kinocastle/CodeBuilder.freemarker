package codebuilder.freemarker;

public abstract class TableDao
{
	public abstract void initConnect(String url);
	public abstract TableModel queryTable(String tableName);
	public abstract void close();
}
