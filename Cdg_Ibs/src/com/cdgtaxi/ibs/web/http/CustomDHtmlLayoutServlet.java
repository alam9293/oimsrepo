package com.cdgtaxi.ibs.web.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

import com.cdgtaxi.ibs.acl.Constants;

public class CustomDHtmlLayoutServlet extends DHtmlLayoutServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws javax.servlet.ServletException, java.io.IOException{
		request.getSession().setAttribute(Constants.SESSION_TIME, Constants.SESSION_TIMEOUT_SETTING);
		super.doGet(request, response);
	}
	
	protected void doPost(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response)
     throws javax.servlet.ServletException,
            java.io.IOException{
		System.out.println("********** do post **********");
		super.doPost(request, response);
	}
	
	protected boolean process(Session sess,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response,
            java.lang.String path,
            boolean bRichlet)
     throws javax.servlet.ServletException,
            java.io.IOException{
		return super.process(sess, request, response, path, bRichlet);
	}
}
