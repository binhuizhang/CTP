<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.desuotest.pojo.SNSUserInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- width=device-width适应手机屏幕宽度  user-scalable禁止手动缩放-->
<meta name="viewport" content="width=device-width,user-scalable=0">

<title>OAuth2.0 网页授权</title>
</head>
<body>
	
	<% 
		//获取由OAuthServlet 传入的参数
		SNSUserInfo userinfo = (SNSUserInfo)request.getAttribute("snsUserInfo");
		
		String access_token = request.getAttribute("access_token").toString();
		String openid = request.getAttribute("openid").toString();
		String errmsg = request.getAttribute("errmsg").toString();
		String testing = request.getAttribute("testing").toString();
		if(userinfo != null){
				
	%>
	<!-- cellpadding 属性规定单元边沿与其内容之间的空白。 cellspacing 属性规定单元格之间的空白。 -->
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td width="20%">属性</td> <td width="80%">值</td>
		</tr>
		<tr>
			<td>OpenID</td> <td ><%=userinfo.getOpenId()%></td>
		</tr>
		<tr>
			<td>昵称</td> <td ><%=userinfo.getNickName()%></td>
		</tr>
		<tr>
			<td>性别</td> <td ><%=userinfo.getSex()%></td>
		</tr>
		<tr>
			<td>国家</td> <td ><%=userinfo.getCountry()%></td>
		</tr>
		
		<tr>
			<td>省份</td> <td ><%=userinfo.getProvince()%></td>
		</tr>
		<tr>
			<td>城市</td> <td ><%=userinfo.getCity()%></td>
		</tr>
		
		<tr>
			<td>头像</td><td ><img src=<%=userinfo.getHeadimgurl()%>></td>
		</tr>
		
		<tr>
			<td>头像</td><td ><%=userinfo.getHeadimgurl()%></td>
		</tr>
		
		<tr>
			<td>特权</td> <td ><%=userinfo.getPrivilege()%></td>
		</tr>
		
		<tr>
			<td>测试</td> <td ><%=request.getAttribute("testing").toString()%></td>
		</tr>
	</table>
	
	<%
		}
		else{
			out.println(errmsg);
			out.println(access_token);
			out.println(openid);
		}
	%>
	
</body>
</html>