package com.ccc.test.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.GlobalValues;

public class CommonInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        Bog.print("preHandle="+url);
		UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalValues.SESSION_USER);
		if ( user == null && url != null && !url.contains("login")){
			request.setAttribute("result", GlobalValues.MSG_PLEASE_LOGIN);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return false;
		}
		return super.preHandle(request, response, handler);
	}
}
