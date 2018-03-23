/**
 * 功能:${table.comment}Controller
 * 开发人员:${developer}
 * 创建时间:${.now}
 */
package ${namespace}.controller${module};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ${frame}.mvc.BaseController;
import ${frame}.core.page.Page;
import ${frame}.core.page.PageNav;
import ${frame}.core.util.CollectionUtil;
import ${namespace}.model${module}.${model};
import ${namespace}.service${module}.${model}Service;

@Scope("prototype")
@Controller
@RequestMapping("${webmodule}")
public class ${model}Controller extends BaseController
{
	@Autowired
	private ${model}Service service;

	//添加
	@RequestMapping("/add${model}1")
	public String add${model}1()
	{
		return "${webmodule}/add${model}.jsp";
	}

	@RequestMapping("/add${model}2")
	public void add${model}2(${model} po)
	{
		try
		{
			service.save(po);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	//删除
	@RequestMapping("/del${model}")
	public void del${model}()
	{
		try
		{
			service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	//修改
	@RequestMapping("/upd${model}1")
	public String upd${model}1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "${webmodule}/upd${model}.jsp";
	}

	@RequestMapping("/upd${model}2")
	public void upd${model}2(${model} po)
	{
		try
		{
			service.update(po);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	//获得分页
	@RequestMapping("/get${model}")
	public String get${model}()
	{
		Page<${model}> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<${model}>(request, pageModel));
		return "${webmodule}/get${model}.jsp";
	}

	//明细
	@RequestMapping("/get${model}ById")
	public String get${model}ById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "${webmodule}/get${model}ById.jsp";
	}
}
