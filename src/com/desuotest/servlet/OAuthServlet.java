package com.desuotest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.desuotest.pojo.SNSUserInfo;
import com.desuotest.pojo.WeixinOauth2Token;
import com.desuotest.util.AdvanceUtil;
import com.desuotest.util.ConstArgs;

public class OAuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		//用户同意授权，就可以获得code
		String code = req.getParameter("code");
		
		if(!code.equals("authdeny")){
			//通过code 来获取网页授权的access_token;
			
			WeixinOauth2Token weixinOauth2Token = 
					AdvanceUtil.getOauth2AccessToken(ConstArgs.appId,ConstArgs.appSecret,code);
			String scope = weixinOauth2Token.getScope();
			if(scope.equals("snsapi_userinfo")){
				String access_token = weixinOauth2Token.getAccessToken();
				String openid = weixinOauth2Token.getOpenId();
				
				SNSUserInfo userInfo = AdvanceUtil.getSNSUserInfo(access_token,openid);
				
				if(userInfo != null){
					//设置要传递的参数
					req.setAttribute("snsUserInfo", userInfo);
					req.setAttribute("access_token", access_token);
					req.setAttribute("openid", openid);
					req.setAttribute("errmsg", "OK");
					
			        try{
			        	String host = req.getHeader("BAE_ENV_ADDR_SQL_IP");
			            String port = req.getHeader("BAE_ENV_ADDR_SQL_PORT");
			            String username = req.getHeader("BAE_ENV_AK");
			            String password = req.getHeader("BAE_ENV_SK");
			            req.setAttribute("testing", host+port+username+password);

			        }catch(Exception e){
			        	req.setAttribute("testing", e.getStackTrace().toString());
			        }
				}else{
					//设置要传递的参数
					req.setAttribute("snsUserInfo", userInfo);
					req.setAttribute("access_token", access_token);
					req.setAttribute("openid", openid);
					req.setAttribute("errmsg", "userInfo null");
				}
				
			}

		}
		
		req.getRequestDispatcher("/userinfo.jsp").forward(req, resp);


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
}
