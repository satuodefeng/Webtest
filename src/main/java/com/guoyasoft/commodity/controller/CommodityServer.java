package com.guoyasoft.commodity.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.guoyasoft.commodity.beans.head.HEAD;
import com.guoyasoft.commodity.service.AbsService;

public class CommodityServer extends HttpServlet {
	public static Map<String, String> beanMap = new HashMap<String, String>();
    ///初始化一次；
    static{
    	beanMap.put("C0001", "com.guoyasoft.commodity.service.C0001Svc");
    }
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		System.out.println("-------------开始接收请求报文----------------------------");
		try {
			InputStream is = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String tmp = "";
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
			String requestStr = sb.toString();
			if (StringUtils.isBlank(requestStr)) {
				out.print("xmlmsg为空");
				return;
			}
			System.out.println("-------------请求报文接收完成：" + requestStr);

			StringReader reader = new StringReader(requestStr);

			HEAD head = HEAD.unmarshal(reader);
			String busiCode = head.getBusiCode();
			String requestor = head.getRequestor();
			String svcContent = head.getSvcContent();

			System.out.println("busiCode=" + busiCode);
			System.out.println("requestor=" + requestor);
			System.out.println("svcContent=" + svcContent);

			AbsService service=this.getBean(busiCode);
			String rspXml=service.execute(svcContent);
			
			System.out.println("响应报文："+rspXml);
			out.print(rspXml);
			response.flushBuffer();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static AbsService getBean(String beanName) {
		AbsService service=null;
		try {
			String className = beanMap.get(beanName);
			service=(AbsService)Class.forName(className).newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return service;
	}

}
