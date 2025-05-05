package com.cdgtaxi.ibs.web.http;

import org.zkoss.zk.au.http.DHtmlUpdateServlet;

import com.cdgtaxi.ibs.acl.Constants;

public class CustomDHtmlUpdateServlet extends DHtmlUpdateServlet{
	
	private String onTimerStr = "onTimer";
	
	protected void doPost(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response)
     throws javax.servlet.ServletException,
            java.io.IOException{
		
//		System.out.println("********** 1."+request.getAuthType()+" **********");
//		System.out.println("********** 2."+request.getContentLength()+" **********");
//		System.out.println("********** 3."+request.getContentType()+" **********");
//		System.out.println("********** 4."+request.getContextPath()+" **********");
//		System.out.println("********** 5."+request.getPathInfo()+" **********");
//		System.out.println("********** 6."+request.getServletPath()+" **********");
//		System.out.println("********** 7."+request.getQueryString()+" **********");
		
		//IE6 uses this to detect whether the calling is from timer
		String queryString = request.getQueryString();
		//Reset session count down
		if(queryString!=null && !queryString.contains(onTimerStr)){
			request.getSession().setAttribute(Constants.SESSION_TIME, Constants.SESSION_TIMEOUT_SETTING);
		}
		
		//Firefox uses this
		//Firefox cant really detect that the incoming AU is a timer updating the label
		int contentLength = request.getContentLength();
		//Reset session count down
		//37,38 = count down timer do post
		//65,66 = update label do post
		if(new Integer(contentLength)!=null && contentLength>0 &&
				(!(contentLength>=37 && contentLength<=38) && !(contentLength>=65 && contentLength<=66))){
			request.getSession().setAttribute(Constants.SESSION_TIME, Constants.SESSION_TIMEOUT_SETTING);
		}
		
		super.doPost(request, response);
	}
}
