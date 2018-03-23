package codebuilder.freemarker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import codebuilder.freemarker.TableModel.Column;

public class MySqlDao extends TableDao
{
	private static final String SQL_TABLE_COMMENT = "select TABLE_COMMENT from information_schema.TABLES where TABLE_SCHEMA='%s' and TABLE_NAME='%s'";
	private static final String SQL_TABLE_COLUMN = "select COLUMN_NAME,COLUMN_DEFAULT,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,COLUMN_COMMENT,COLUMN_KEY from information_schema.COLUMNS where TABLE_SCHEMA='%s' and TABLE_NAME='%s'";
//	private static final String SQL_ALL_TABLES = "select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA='%s'";

	private Connection conn;
	private String dbName;

	static
	{
		String[] drivers = {
			"oracle.jdbc.driver.OracleDriver"
			,"oracle.jdbc.OracleDriver"
			,"com.mysql.jc.jdbc.Driver"// mysql j 6.x for jdk8
			,"com.mysql.jdbc.Driver"// mysql j 5.x及之前
			,"com.microsoft.jdbc.sqlserver.SQLServerDriver"// MS driver for Sql Server 2000
			,"com.microsoft.sqlserver.jdbc.SQLServerDriver"// MS driver for Sql Server 2005
			,"com.ibm.db2.jcc.DB2Driver"// 增加了DB2支持
			,"org.sqlite.JDBC"// 增加了sqlite支持
			,"org.postgresql.Driver"
			,"com.sybase.jdbc2.jdbc.SybDriver"
			,"net.sourceforge.jtds.jdbc.Driver"// sqlServer
			,"weblogic.jdbc.sqlserver.SQLServerDriver"// sqlServer
			,"com.informix.jdbc.IfxDriver"
			,"org.apache.derby.jdbc.ClientDriver"
			,"org.apache.derby.jdbc.EmbeddedDriver"
			,"org.hsqldb.jdbcDriver"
			,"org.h2.Driver"
			,"dm.jdbc.driver.DmDriver"//达梦
			,"com.gbase.jdbc.Driver"//gbase
		};
		for(String driver : drivers)
		{
			try
			{
				Class.forName(driver);
			}
			catch(ClassNotFoundException e)
			{
			}
		}
	}

	@Override
	public void initConnect(String url)
	{
		try
		{
			conn = DriverManager.getConnection(url);
			dbName = conn.getCatalog();
		}
		catch(Exception e)
		{
		}
	}

	private String queryTableComment(String tableName)
	{
		String comment = "";
		String sql = String.format(SQL_TABLE_COMMENT, dbName, tableName);
		try(PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();)
		{
			rs.next();
			comment = rs.getString("TABLE_COMMENT");
		}
		catch(Exception e)
		{
		}
		return comment;
	}

	private List<Column> queryTableColumn(String tableName)
	{
		List<Column> list = new ArrayList<>();
		String sql = String.format(SQL_TABLE_COLUMN, dbName, tableName);
		try(PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();)
		{
			while(rs.next())
			{
				Column col = new Column();
				col.setName(rs.getString("COLUMN_NAME"));
				col.setDefaults(rs.getString("COLUMN_DEFAULT"));
				col.setType(rs.getString("DATA_TYPE"));
				col.setLength(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
				col.setComment(rs.getString("COLUMN_COMMENT"));
				col.setIskey("PRI".equals(rs.getString("COLUMN_KEY")));
				switch(col.getType())
				{
					case "int":
					case "tinyint":
					case "mediumint":
						col.setType("int"); break;
					case "bigint":
						col.setType("Long"); break;
					case "float":
					case "double":
					case "decimal":
						col.setType("float"); break;
					case "char":
					case "varchar":
					case "tinytext":
					case "text":
					case "mediumtext":
					case "longtext":
						col.setType("String"); break;
					case "datetime":
					case "timestamp":
					case "time":
						col.setType("String"); break;
					default:
						break;
				}
				list.add(col);
			}
		}
		catch(Exception e)
		{
		}
		return list;
	}

	@Override
	public TableModel queryTable(String tableName)
	{
		TableModel table = new TableModel();
		table.setName(tableName);
		table.setComment(queryTableComment(table.getName()));
		table.columnList = queryTableColumn(table.getName());
		return table;
	}

	@Override
	public void close()
	{
		try
		{
			if(conn != null)
			{
				conn.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
