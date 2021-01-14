package com.genertech.phm.dzs.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.genertech.phm.core.action.BaseAction;
import com.genertech.phm.core.model.WebParamsModel;
import com.genertech.phm.dzs.dto.FaultWarn;
import com.genertech.phm.dzs.model.ModelInfo;
import com.genertech.phm.dzs.service.ThresholdWarning;
import com.genertech.phm.log.service.ApiDataCollectService;
import com.genertech.phm.utils.TTPropsUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class DzsWarnAction extends BaseAction {


    private static final String EMIS = "EMIS_";

    private static final Log logger = LogFactory.getLog(DzsWarnAction.class);
    @Autowired
    private ApiDataCollectService apiDataCollectService;

    /**
     * 铁科接口参数
     * @param ClientId 客户端id
     * @returns
     */
    private List<WebParamsModel> params(String ClientId,String phmdata) {
        //参数
        List<WebParamsModel> paramsList = new ArrayList<WebParamsModel>();
        //这里注意参数字名
        paramsList.add(new WebParamsModel("ClientId", ClientId));
        paramsList.add(new WebParamsModel("phmdata", phmdata));
        return paramsList;
    }


    private List<WebParamsModel> params(String ClientId,String phmdata,String errorMsg) {
        //参数
        List<WebParamsModel> paramsList = new ArrayList<WebParamsModel>();
        //这里注意参数字名
        paramsList.add(new WebParamsModel("ClientId", ClientId));
        paramsList.add(new WebParamsModel("phmdata", phmdata));
        paramsList.add(new WebParamsModel("errorMsg", errorMsg));
        return paramsList;
    }


    public void readMessageFromMQ(String message) {
        if (StringUtils.isEmpty(message)) {
            /*logger.info("read message from mq is null");*/
            return;
        }
        logger.info("read message from mq data is " + message);
        Integer source = null;
        JSONObject warnRec = JSON.parseObject(message);
        String dataProducer = warnRec.getString("dataProducer");
        if (StringUtils.isNotEmpty(dataProducer)) {
            if (!dataProducer.equals("1010") && !dataProducer.equals("2004"))
                return;
            if (dataProducer.equals("1010")) {
                source = 1;
            }
            else if (dataProducer.equals("2004")) {
                warnRec.put("warnCode","X001");
                source = 3;
            }
        }
        else
            return;

        String ckId = warnRec.getString("warnCode");
        Long warnTime = warnRec.getLong("warnTime");
        String equipType = warnRec.getString("equipType");
        String equipSn = warnRec.getString("equipSn");
        String areaCode = warnRec.getString("areaCode");
        String warnInfo = warnRec.getString("warnName");
        String occurStatus = warnRec.getString("occurStatus");

        if (StringUtils.isEmpty(ckId) || StringUtils.isEmpty(equipType) || StringUtils.isEmpty(equipSn) || StringUtils.isEmpty(areaCode) ||
                StringUtils.isEmpty(warnInfo) || StringUtils.isEmpty(occurStatus)) {
            /*logger.info("source data do not  meet the conditions, attribute value missing, source data is " + message);*/
            return;
        }

        TTPropsUtil.TTProperties ttProperties = TTPropsUtil.getProps();
        ModelInfo modelInfo = ttProperties.getModelInfo(ckId,equipType,equipSn,source);
        if (modelInfo != null) {
            StringBuffer _phmdata = new StringBuffer();
            _phmdata.append(modelInfo.getModelCode().replaceAll(" ","") + "|");
            _phmdata.append(modelInfo.getOutputCode() + "|");
            _phmdata.append(TimeUnit.MILLISECONDS.toSeconds(warnTime)  + "|");
            _phmdata.append(equipType + "|");
            _phmdata.append(equipSn.replace("长客标动-","") + "|");
            _phmdata.append(areaCode + "|");
            _phmdata.append(warnInfo + "|");
            _phmdata.append(occurStatus);
            String phmdata = _phmdata.toString();
            logger.info("phmdata is {" + phmdata + "}");
            try {
                String result = this.invokeWebServiceMethodString(ADDRESS, NAMESPACEURI, "SendPhmData", params(CLIENTID,phmdata), true);
                logger.info("transfer result is " + result);
            } catch (Exception e) {
                logger.error("transfer exception.");
                e.printStackTrace();
            }
        }
    }

    public void readMessageFromRedis() {
        ThresholdWarning p = new ThresholdWarning();
        List<FaultWarn> faultWarns = p.getThresholdWarning();

        if (faultWarns == null || faultWarns.size() == 0) {
            /*logger.info("read message from redis count null");*/
            return;
        }
        logger.info("read message from redis count is " + faultWarns.size());
        logger.info("read message from redis data is " + JSON.toJSONString(faultWarns,true));
        for (FaultWarn faultWarn: faultWarns) {
            Date time = null;
            if (StringUtils.isNotEmpty(faultWarn.getCreateTime())) {
                SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddhhmmss");
                try {
                    time = sim.parse(faultWarn.getCreateTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String ckId = faultWarn.getMaxParmCode();
            String warnName = faultWarn.getWarnName();
            Long warnTime = time.getTime();
            String equipType = faultWarn.getTrain_type();
            String equipSn = faultWarn.getSn();
            String areaCode =  "" + faultWarn.getCar_no();
            String warnInfo = faultWarn.getWarnRemark();
            String occurStatus = faultWarn.getOccrStatus();

            if (StringUtils.isEmpty(warnName) || StringUtils.isEmpty(equipType) || StringUtils.isEmpty(equipSn) || StringUtils.isEmpty(areaCode) ||
                    StringUtils.isEmpty(warnInfo) || StringUtils.isEmpty(occurStatus)) {
                /*logger.info("source data do not meet the conditions, attribute value missing, source data is " + JSON.toJSONString(faultWarn,true));*/
                return;
            }

            if (occurStatus.equals("恢复")) {
                occurStatus = "0";
            }
            else if (occurStatus.equals("发生")) {
                occurStatus = "1";
            }

            TTPropsUtil.TTProperties ttProperties = TTPropsUtil.getProps();
            ModelInfo modelInfo = ttProperties.getModelInfo2(ckId,equipType,equipSn,warnName,2);
            if (modelInfo != null) {
                StringBuffer _phmdata = new StringBuffer();
                _phmdata.append(modelInfo.getModelCode().replaceAll(" ","") + "|");
                _phmdata.append(modelInfo.getOutputCode() + "|");
                _phmdata.append(TimeUnit.MILLISECONDS.toSeconds(warnTime) + "|");
                _phmdata.append(equipType + "|");
                _phmdata.append(equipSn.replace("长客标动-","") + "|");
                _phmdata.append(areaCode + "|");
                _phmdata.append(warnInfo + "|");
                _phmdata.append(occurStatus);
                String phmdata = _phmdata.toString();
                logger.info("phmdata is {" + phmdata + "}");
                try {
                    String result = this.invokeWebServiceMethodString(ADDRESS, NAMESPACEURI, "SendPhmData", params(CLIENTID,phmdata), true);
                    logger.info("transfer result is " + result);
                } catch (Exception e) {
                    logger.error("transfer exception.");
                    e.printStackTrace();
                }
            }
        }
    }
}
