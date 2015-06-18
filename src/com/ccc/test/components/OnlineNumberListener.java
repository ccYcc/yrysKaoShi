package com.ccc.test.components;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineNumberListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		ServletContext ctx = event.getSession( ).getServletContext( );   
        Integer onLineUserNum = (Integer) ctx.getAttribute("onLineUserNum");   
        if (onLineUserNum == null) {   
            onLineUserNum = new Integer(1);   
        }else {   
            int count = onLineUserNum.intValue( );   
            onLineUserNum = new Integer(count + 1);   
        }   
        ctx.setAttribute("onLineUserNum", onLineUserNum); 		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		ServletContext ctx = event.getSession( ).getServletContext( );   
        Integer onLineUserNum = (Integer) ctx.getAttribute("onLineUserNum");   
        if (onLineUserNum == null) {   
            onLineUserNum = new Integer(0);   
        }   
        else {   
            int count = onLineUserNum.intValue( );   
            onLineUserNum = new Integer(count - 1);   
        }   
        ctx.setAttribute("onLineUserNum", onLineUserNum); 		
	}

}
