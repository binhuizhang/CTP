package com.desuotest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BrowserFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		String userAgent = request.getHeader("User-Agent");
		
		//�ж������Ƿ�����΢�������
		if(userAgent.contains("MicroMessenger")){
			chain.doFilter(req, resp);
		}
		//���������
		else{
			HttpServletResponse response = (HttpServletResponse)resp;
			response.setCharacterEncoding("GBK");
			PrintWriter out = response.getWriter();
			out.write("��ʹ��΢����������ʣ�");
			out.close();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
