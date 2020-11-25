<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>首页</title>
</head>
<body>
	<div class="grid-panner">
	<table border="1">
<tr>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisrouting.jsp"><button >运行交路</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emispartresumebytrainname.jsp"><button >车组履历数据</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emistrainsetbaseinfo.jsp"><button >动车组基础数据</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emiscarfaultdata.jsp"><button >检修库内故障数据</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisldchecktrain.jsp"><button >LY数据-车组级</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisldwheelexplorscar.jsp"><button > LY数据-探伤</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisemdiwstanalyse.jsp"><button >LY数据-超限</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisroutinginfo.jsp"><button >交路详细</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisopenstring.jsp"><button >开行字典</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisopenstringinfo.jsp"><button >开行详细字段</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisldwheelcar.jsp"><button > LY数据-擦伤</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisldwheelsizedt.jsp"><button >LY数据-尺寸</button></a></td>
  <td><a href="http://172.22.172.201:8080/PHM/views/emis/emisjcwheeldatanew.jsp"><button >镟修</button></a></td>
  
</tr>
</table>	
<%--<div class="buttons">
			<button type="button" id="searchBtn" class="btn btn-primary btn-sm">查询</button>
			<button type="button" id="insertBtn" class="btn btn-primary btn-sm">添加</button>
			<button type="button" id="updateBtn" class="btn btn-primary btn-sm">修改</button>
			<button type="button" id="deleteBtn" class="btn btn-primary btn-sm">删除</button>
		</div>--%>
		<!-- <table id='grid'></table>
		<div id='pager'></div> -->
	</div>
</body>
</html>