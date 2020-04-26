<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>作业</title>
<jsp:include page="/commons/common-js.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',title:'菜单',split:true"
		style="width: 180px">
		<ul id="tree" class="easyui-tree">
			<li><span>商品列表</span>
				<ul>
					<li>商品目录</li>
					<li>商品操作</li>
					<li><span>目录</span>
						<ul>
							<li  data-options="attributes:{'url':'/page/save'}">新增</li>
						</ul></li>
				</ul></li>
		</ul>
	</div>
	<div id="tab" data-options="region:'center',title:'窗口'">
		<div id="tabs" class="easyui-tabs"></div>
</body>
<script type="text/javascript">
$(function(){
	$('#tree').tree({
		onClick: function(node){
			if($('#menu').tree("isLeaf",node.target)){
				var tabs = $("#tabs");
				var tab = tabs.tabs("getTab",node.text);
				if(tab){
					tabs.tabs("select",node.text);
				}else{
					tabs.tabs('add',{
					    title:node.text,
					    href: node.attributes.url,
					    closable:true,
					    bodyCls:"content"
					});
				}
			}
		}
	});
});
</script>

</html>