package com.genertech.phm.gener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.google.gson.Gson;

public class PHMTsWebService {

	public static void main(String[] args) {
		try {
//			getMeasuringPoint();
//			getParamCode();
//			getParamVaule();
			getParamVaule2();
//			getRuleCodeName();
			
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
	
	//取量测点列表接口
	public static void getMeasuringPoint() throws AxisFault {
		Gson gson = new Gson();
		//使用RPC方式调用WebService          
        RPCServiceClient serviceClient = new RPCServiceClient();  
        Options options = serviceClient.getOptions();  
        //指定调用WebService的URL  
        EndpointReference targetEPR = new EndpointReference("http://192.168.3.134:9090/PHM-SERVICE/CkParamBeiService?wsdl");
        options.setTo(targetEPR);
        //设定超时时间
        options.setTimeOutInMilliSeconds(60000);
        
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("motorcycle_type", "CRH380B");//车型
        map.put("installation", "3-A");//bei
        //数据转化
        String str = gson.toJson(map);
        //指定方法的参数值  
        Object[] opAddEntryArgs = new Object[] {str};
        System.out.println(str);
        Class[] classes = new Class[] {String.class};  
        //指定要调用的方法及WSDL文件的命名空间  
        QName opAddEntry = new QName("http://webservice.gener.phm.genertech.com/", "getMeasuringPoint");  
        String result=(String) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
        System.out.println(result); 
	}
	
	//取量测点code接口
	public static void getParamCode() throws AxisFault {
		Gson gson = new Gson();
		//使用RPC方式调用WebService          
        RPCServiceClient serviceClient = new RPCServiceClient();  
        Options options = serviceClient.getOptions();  
        //指定调用WebService的URL  
        EndpointReference targetEPR = new EndpointReference("http://192.168.3.134:9090/PHM-SERVICE/CkParamBeiService?wsdl");
        options.setTo(targetEPR);
        //设定超时时间
        options.setTimeOutInMilliSeconds(60000);
        
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("entity_pathname", "CRH380B/3-A/CRH30047");//路径
        //数据转化
        String str = gson.toJson(map);
        //指定方法的参数值  
        Object[] opAddEntryArgs = new Object[] {str};
        System.out.println(str);
        Class[] classes = new Class[] {String.class};  
        //指定要调用的方法及WSDL文件的命名空间  
        QName opAddEntry = new QName("http://webservice.gener.phm.genertech.com/", "getParamCode");  
        String result=(String) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
        System.out.println(result); 
	}
	
	//取量测点数据接口
	public static void getParamVaule() throws AxisFault {
//		Gson gson = new Gson();
//		//使用RPC方式调用WebService          
//        RPCServiceClient serviceClient = new RPCServiceClient();  
//        Options options = serviceClient.getOptions();  
//        //指定调用WebService的URL  
//        EndpointReference targetEPR = new EndpointReference("http://192.168.3.153:9090/PHM-SERVICE/ParamBeiService?wsdl");
//        options.setTo(targetEPR);
//        //设定超时时间
//        options.setTimeOutInMilliSeconds(60000);
//        
//        Map<String,Object> map = new HashMap<>();
//        map.put("sn", "3640");//车组
//        map.put("meas_id", "C1671A25");//量测点code
//        map.put("start_time", "20170711052152");//开始时间
//        map.put("end_time", "20170711054926");//结束时间
//        //数据转化
//        String str = gson.toJson(map);
//        //指定方法的参数值  
//        Object[] opAddEntryArgs = new Object[] {str};
//        System.out.println(str);
//        Class[] classes = new Class[] {String.class};  
//        //指定要调用的方法及WSDL文件的命名空间  
//        QName opAddEntry = new QName("http://webservice.gener.phm.genertech.com/", "getParamVaule");  
//        String result=(String) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
//        System.out.println(result); 
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sn", "5747");//车组
		map.put("meas_id", "5");//量测点code
		map.put("start_time", "20180425092152");//开始时间
		map.put("end_time", "20180425094926");//结束时间
		map.put("limit_val", "0");//分页
		//map.put("assessType","对位置参数的显著性检验:0;拟合度:0;对形状参数的显著性检验:0");
		// 数据转化
		String str = gson.toJson(map);
		
		
		String endpoint = "http://192.168.3.134:9090/PHM-SERVICE/CkParamBeiService?wsdl";
		String opName = "getParamVaule";

		Object[] opArgs = new Object[] { str };

		Class<?>[] opReturnType = new Class[] { String[].class };

		RPCServiceClient serviceClient = new RPCServiceClient();// 此处RPCServiceClient
																// 对象实例建议定义成类中的static变量，否则多次调用会出现连接超时的错误。
		Options options = serviceClient.getOptions();
		//设定超时时间
		options.setTimeOutInMilliSeconds(60000);
		EndpointReference targetEPR = new EndpointReference(endpoint);
		options.setTo(targetEPR);

		QName opQName = new QName("http://webservice.gener.phm.genertech.com/", opName);
		Object[] ret = serviceClient.invokeBlocking(opQName, opArgs, opReturnType);
		System.out.println(((String[]) ret[0])[0]);
	}
	
	public static void getParamVaule2() throws AxisFault {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sn", "5747");//车组
		map.put("code", "CRH30043");//量测点code
		map.put("coach", "1");//量测点code
		map.put("start_time", "20180425092152");//开始时间
		map.put("end_time", "20180425094926");//结束时间
		//map.put("assessType","对位置参数的显著性检验:0;拟合度:0;对形状参数的显著性检验:0");
		// 数据转化
		String str = gson.toJson(map);
		
		
		String endpoint = "http://localhost:9090/PHM-SERVICE/CkParamBeiService?wsdl";
		String opName = "getParamVaule2";

		Object[] opArgs = new Object[] { str };

		Class<?>[] opReturnType = new Class[] { String[].class };

		RPCServiceClient serviceClient = new RPCServiceClient();// 此处RPCServiceClient
																// 对象实例建议定义成类中的static变量，否则多次调用会出现连接超时的错误。
		Options options = serviceClient.getOptions();
		//设定超时时间
		options.setTimeOutInMilliSeconds(60000);
		EndpointReference targetEPR = new EndpointReference(endpoint);
		options.setTo(targetEPR);

		QName opQName = new QName("http://webservice.gener.phm.genertech.com/", opName);
		Object[] ret = serviceClient.invokeBlocking(opQName, opArgs, opReturnType);
		System.out.println(((String[]) ret[0])[0]);
	}
	
	public static void getRuleCodeName() throws AxisFault {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rule_id", "00000000000000000486");//车组
		// 数据转化
		String str = gson.toJson(map);
		
		String endpoint = "http://192.168.3.134:9090/PHM-SERVICE/CkParamBeiService?wsdl";
		String opName = "getRuleCodeName";

		Object[] opArgs = new Object[] { str };

		Class<?>[] opReturnType = new Class[] { String[].class };

		RPCServiceClient serviceClient = new RPCServiceClient();// 此处RPCServiceClient
																// 对象实例建议定义成类中的static变量，否则多次调用会出现连接超时的错误。
		Options options = serviceClient.getOptions();
		//设定超时时间
		options.setTimeOutInMilliSeconds(60000);
		EndpointReference targetEPR = new EndpointReference(endpoint);
		options.setTo(targetEPR);

		QName opQName = new QName("http://webservice.gener.phm.genertech.com/", opName);
		Object[] ret = serviceClient.invokeBlocking(opQName, opArgs, opReturnType);
		System.out.println(((String[]) ret[0])[0]);
	}
	
}
