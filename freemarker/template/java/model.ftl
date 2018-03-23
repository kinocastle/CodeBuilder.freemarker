/**
 * ${table.comment}Model
 */
package ${namespace}.model${module};
public class ${table.nameUpperCamel}
{
<#list columnList as c>
	//${c.comment}
	<#if c.type == 'Long' || c.type == 'long'>
	private ${c.type} ${c.nameLowerCamel} = ${c.defaults!'0'}L;
	<#elseif c.type == 'Integer' || c.type == 'int'>
	private ${c.type} ${c.nameLowerCamel} = ${c.defaults!'0'};
	<#elseif c.type == 'Float' || c.type == 'float'>
	private ${c.type} ${c.nameLowerCamel} = ${c.defaults!'0.0'}F;
	<#elseif c.type == 'String'>
	private ${c.type} ${c.nameLowerCamel} = "${c.defaults!}";
	<#else>
	private ${c.type} ${c.nameLowerCamel};
	</#if>
</#list>
<#list columnList as c>

	public ${c.type} get${c.nameUpperCamel}()
	{
		return ${c.nameLowerCamel};
	}

	public void set${c.nameUpperCamel}(${c.type} ${c.nameLowerCamel})
	{
		this.${c.nameLowerCamel} = ${c.nameLowerCamel};
	}
</#list>
}