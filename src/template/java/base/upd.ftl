<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp"%>
<script type="text/javascript">
$${frame}.callback = function(){if($${frame}.result.type == 1){
	location.href = "get${model}.htm?page=${r'$'}{page}";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="get${model}.htm?page=${r'$'}{page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="upd${model}2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
<#list columnList as c>
	<#if !c.iskey>
		<#if c.nameLowerCamel != 'memo'>
	<tr>
		<td class="form_title">${c.comment}</td>
		<td class="form_input"><input type="text" name="${c.nameLowerCamel}" maxlength="100" value="${r'$'}{fn:escapeXml(po.${c.nameLowerCamel})}" /></td>
	</tr>
		<#else>
	<tr>
		<td class="form_title">${c.comment}</td>
		<td class="form_input"><textarea name="${c.nameLowerCamel}" style="width:400px;height:60px;">${r'$'}{fn:escapeXml(po.${c.nameLowerCamel})}</textarea></td>
	</tr>
		</#if>
	</#if>
</#list>
</table>
<#list columnList as c>
	<#if c.iskey>
<input type="hidden" name="${c.nameLowerCamel}" value="${r'$'}{po.${c.nameLowerCamel}}" />
	</#if>
</#list>
</form>
</body>
</html>
