/**
 * Copyright(C) 2015-2017 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>
 * 版权所有(C)2015-2017 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.7.0_67
 * <p>
 * 版本: 1.0版 文件名：com.genertech.phm.core.action.BaseAction.java
 * <p>
 * 作者: 郑阳文
 * <p>
 * 创建时间: 2017年3月13日下午2:35:39
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p>
 * 修改者：郑阳文
 * <p>
 * 修改时间：2017年3月13日下午2:35:39
 * <p>
 */
package com.genertech.phm.core.action;

import com.alibaba.fastjson.JSONObject;
import com.genertech.phm.core.model.ResultModel;
import com.genertech.phm.core.model.WebParamsModel;
import com.genertech.phm.utils.LogUtil;
import com.genertech.phm.utils.PropsUtil;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BaseAction {
    private static final Log      logger        = LogFactory.getLog(BaseAction.class);

    /**
     * 请求成功壮态
     */
    protected static final String SUCCESS       = "success";

    /**
     * 铁科WebService接口地址
     */
    protected static String       ADDRESS       = PropsUtil.getPropsValue(PropsUtil.EMIS_ASYNC_URL);
    /**
     * 铁科WebService接口命名空间
     */
    protected static String       NAMESPACEURI  = PropsUtil.getPropsValue(PropsUtil.EMIS_ASYNC_NAMESPACE);
    /**
     * 铁科WebService接口用户
     */
    public static String          USERCODE      = PropsUtil.getPropsValue(PropsUtil.EMIS_ASYNC_USERNAME);
    /**
     * 铁科WebService接口密码
     */
    public static String          PASSWORD      = PropsUtil.getPropsValue(PropsUtil.EMIS_ASYNC_PASSWORD);
    /**
     * 铁科WebService客户端ID
     */
    public static String          CLIENTID      = PropsUtil.getPropsValue(PropsUtil.EMIS_ASYNC_CLIENTID);

    /**
     *
     * 直接通过URL访问其他项目的后台地址
     *
     * @param strUrl
     *            请求地址
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     *
     * @author 创建人：邱千 2017年6月15日 上午10:21:48
     * @author 修改人：邱千 2017年6月15日 上午10:21:48
     */
    public String getUrl(String strUrl) throws ClientProtocolException, IOException,URISyntaxException {
        String jsonStr = null;

        HttpClient httpClient = HttpClients.createDefault();

        //带特殊字符URL转化
        /*URL url = new URL(strUrl);
        String hostPort =  url.getPort()==-1?url.getHost():(url.getHost()+":"+url.getPort());
        URI uri = new URI(url.getProtocol(),hostPort, url.getPath(), url.getQuery(), null);*/
        //HttpGet get = new HttpGet(URLEncoder.encode(strUrl, "utf-8"));
        int index =strUrl.indexOf("?");
        String url = strUrl.substring(0, index);
        String params = strUrl.substring(index + 1);

        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(params, ContentType.create("application/x-www-form-urlencoded", "utf-8"));
        post.setEntity(entity);

        logger.info("HTTP请求数据地址 --> " + strUrl);

        HttpResponse response = httpClient.execute(post);
        int status = response.getStatusLine().getStatusCode();

        logger.info("HTTP请求状态 --> " + status);

        if (status == HttpStatus.SC_OK) {
            // 接口的数据都是gbk编码
            jsonStr = EntityUtils.toString(response.getEntity(), "gbk");
            logger.info("HTTP请求结果 --> " + jsonStr);

        }
        post.releaseConnection();
        return jsonStr;
    }

    /**
     * axis2 client动态调用WCF webservice
     * @param endpoint wsdl地址,如http://172.16.5.21/WcfService2/Service1.svc?wsdl
     * @param SOAPActionURI 调用的方法action地址如"wsdl:input wsaw:Action="http://tempuri.org/IService1/GetData1" message="tns:IService1_GetData1_InputMessage"
     * @param namespaceURI 命名空间如targetNamespace="http://tempuri.org/"
     * @param methodName 方法名如wsdl:operation name="GetData1"
     * @param parameterName 方法变量名
     * @param parameterValue 方法变量值
     * @return返回值
     * @throws ServiceException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static String invokeWCF(String endpoint,String SOAPActionURI,String namespaceURI,String methodName,String parameterName,String parameterValue)
            throws ServiceException, MalformedURLException, RemoteException
    {

        List<HashMap<String, Object>> mList = new ArrayList<HashMap<String,Object>>();
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(endpoint));
        call.addParameter(new QName(namespaceURI,parameterName),org.apache.axis.Constants.XSD_STRING, ParameterMode.IN);
        call.setReturnType(org.apache.axis.Constants.XSD_STRING);
        call.setUseSOAPAction(true);
        call.setSOAPActionURI(SOAPActionURI);
        call.setOperationName(new QName(namespaceURI, methodName));
        String remsg=(String) call.invoke(new java.lang.Object[] {parameterValue});
        return remsg;
    }
    /**
     *
     * 铁科Soap WebService接口请求<br/>
     * 使用axis方式调用
     *
     * @param address
     * @param namespaceURI
     * @param method
     * @param paramsList
     * @return
     * @throws Exception
     *
     * @author 创建人：郑阳文 2017年6月26日 下午2:21:09
     * @author 修改人：郑阳文 2017年6月26日 下午2:21:09
     */
    public ResultModel invokeWebServiceMethod(String address, String namespaceURI, String method,
                                              List<WebParamsModel> paramsList, boolean useSoapAction) throws Exception {
        // new 一个服务
        Service service = new Service();
        // 创建一个call对象
        Call call = (Call) service.createCall();

        logger.info("WebService调用地址 --> " + address);
        // 设置要调用的接口地址以上一篇的为例子
        call.setTargetEndpointAddress(new URL(address));

        logger.info("WebService调用方法名 --> " + method);
        // 设置要调用的接口命名字间及方法
        QName qname = new QName(namespaceURI, method);
        call.setOperationName(qname);
        // 设置SoapActon
        if (useSoapAction) {
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(namespaceURI + method);
        }
        // 返回参数类型
        call.setReturnType(XMLType.XSD_STRING);
        Object[] params = new Object[0];
        if (paramsList != null && !paramsList.isEmpty()) {
            logger.info("WebService调用参数 --> " + paramsList);

            params = new Object[paramsList.size()];
            for (int i = 0, len = paramsList.size(); i < len; i++) {
                WebParamsModel webpm = paramsList.get(i);
                // 设置参数名 id,第二个参数表示String类型,第三个参数表示入参
                call.addParameter(new QName(namespaceURI, webpm.getName()), webpm.getDataType(), webpm.getParamMode());
                params[i] = webpm.getValue();
            }
        }
        call.setEncodingStyle(namespaceURI);
        String result = (String) call.invoke(params);
        result = LogUtil.jsonString(result);

        System.out.println("resust == " + result);

        logger.info("WebService返回数据 --> " + result);

        return JSONObject.parseObject(result, ResultModel.class);
    }
    public String invokeWebServiceMethodString(String address, String namespaceURI, String method,
                                               List<WebParamsModel> paramsList, boolean useSoapAction) throws Exception {
        // new 一个服务
        Service service = new Service();
        // 创建一个call对象
        Call call = (Call) service.createCall();

        logger.info("WebService调用地址 --> " + address);
        // 设置要调用的接口地址以上一篇的为例子
        call.setTargetEndpointAddress(new URL(address));

        logger.info("WebService调用方法名 --> " + method);
        // 设置要调用的接口命名字间及方法
        QName qname = new QName(namespaceURI, method);
        call.setOperationName(qname);
        // 设置SoapActon
        if (useSoapAction) {
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(namespaceURI + method);
//            call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
//            call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        }
        // 返回参数类型
        call.setReturnType(XMLType.XSD_STRING);
        Object[] params = new Object[0];
        if (paramsList != null && !paramsList.isEmpty()) {
            logger.info("WebService调用参数 --> " + paramsList);

            params = new Object[paramsList.size()];
            for (int i = 0, len = paramsList.size(); i < len; i++) {
                WebParamsModel webpm = paramsList.get(i);
                // 设置参数名 id,第二个参数表示String类型,第三个参数表示入参
                call.addParameter(new QName(namespaceURI, webpm.getName()), webpm.getDataType(), webpm.getParamMode());
                params[i] = webpm.getValue();
            }
        }
        call.setEncodingStyle(namespaceURI);
        String result = (String) call.invoke(params);
        result = LogUtil.jsonString(result);

        logger.info("WebService返回数据 --> " + result);

        return result;
    }
    /**
     * 使用axis2的RPC方式调用WebService <br/>
     *
     * @param address
     * @param namespaceURI
     * @param method
     * @param params
     * @return
     * @throws AxisFault
     *
     * @author 创建人：郑阳文 2017年7月28日 下午2:58:26
     * @author 修改人：郑阳文 2017年7月28日 下午2:58:26
     */
    @SuppressWarnings("rawtypes")
    public ResultModel invokeWebServiceMethod(String address, String namespaceURI, String method, Object... params)
            throws AxisFault {
        // 使用axis2的RPC方式调用WebService
        RPCServiceClient serviceClient = new RPCServiceClient();
        Options options = serviceClient.getOptions();

        logger.info("WebService2调用地址 --> " + address);

        // 指定调用WebService的URL
        options.setTo(new EndpointReference(address));
        // 设定超时时间
        options.setTimeOutInMilliSeconds(60000);

        logger.info("WebService2调用方法名 --> " + method);

        // 指定要调用的方法及WSDL文件的命名空间
        QName qName = new QName(namespaceURI, method);

        logger.info("WebService2调用参数 --> " + Arrays.toString(params));

        // 指定方法的参数值
        Class[] returnType = new Class[] { String.class };
        Object[] resultObjs = serviceClient.invokeBlocking(qName, params, returnType);
        String result = (String) resultObjs[0];

        logger.info("WebService2返回数据 --> " + result);

        return JSONObject.parseObject(result, ResultModel.class);
    }

}
