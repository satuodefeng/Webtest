package com.guoyasoft.commodity.service;

import java.io.StringReader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.guoyasoft.commodity.beans.C0001.response.CommoditItem;
import com.guoyasoft.commodity.beans.C0001.response.CommodityList;
import com.guoyasoft.commodity.beans.C0001.request.Request;
import com.guoyasoft.commodity.beans.C0001.response.Response;

public class C0001Svc extends AbsService{

	@Override
	public String execute(String xml) {
		System.out.println("----------------C0001业务处理开始----------------");
		Response response=new Response();
		try{
			StringReader reader=new StringReader(xml);
			Request request=Request.unmarshal(reader);
			System.out.println("请求数据："+request.getOperatorId()+","+request.getPromotionType());
			
			response.setRespCode("0000");
			response.setRespDesc("操作成功");
			CommodityList cList=new CommodityList();
			for(int i=0;i<5;i++){
				CommoditItem cItem=new CommoditItem();
				cItem.setCommodityCode("11111"+i);
				cItem.setCommodityType("0"+i);
				cList.addCommoditItem(cItem);
			}
			response.setCommodityList(cList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return response.toXMLString();
		
	}

}
