/**
 * ${table.comment}Dao
 */
package ${namespace}.dao${module};

import org.springframework.stereotype.Repository;
import ${frame}.core.db.BaseDao;
import ${namespace}.model${module}.${model};

@Repository
@SuppressWarnings("all")
public class ${model}Dao extends BaseDao<${model}, Long>
{
	@Override
	public Class getEntityClass()
	{
		return ${model}.class;
	}
}