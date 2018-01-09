package com.guoyasoft.commodity.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.guoyasoft.commodity.beans.head.HEAD;


public class CommodityServer extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

			HEAD head=HEAD.unmarshal(reader);
			System.out.println("busiCode="+head.getBusiCode());
			System.out.println("svcContent="+head.getSvcContent());
//			StringReader reader = new StringReader(requestStr);
//			HEAD head = HEAD.unmarshal(reader);
//			String busiCode = head.getBusiCode();
//			String requestor = head.getRequestor();
//			String svcContent = head.getSvcContent();
//			
//			System.out.println("busiCode="+busiCode);
//			
			out.print("测试成功");
			response.flushBuffer();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
