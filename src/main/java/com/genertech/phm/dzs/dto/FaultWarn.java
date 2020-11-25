package com.genertech.phm.dzs.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

// 故障预警
public class FaultWarn {

    // 车型
    private String                                 train_type;
    // 车组号
    private String                                 sn;
    // 车辆号
    private int                                    car_no;
    // 所属系统
    private String                                 sys_name;
    // 预警项目
    private String                                 warnName;
    // 预警级别
    private String                                 fault_level;
    // 处理类型
    private String                                 type;
    // 处理状态
    private String                                 status;
    // 预警提示信息
    private String                                 warnRemark;
    // 预警提示时间
    private String                                 time;
    // 故障编码
    private String                                 fault_code;
    // 故障记录的id
    private String                                 id;
    // 故障描述--故障 现象
    private String                                 fault_desc;
    // 故障详细描述
    private String                                 descer;
    // 配属局
    private String                                 jname;
    // 服务站
    private String                                 station;
    // 是否已经发送过短信
    private String                                 isSendNote;
    // 配属所编码
    private String                                 scode;
    // 配属所名称
    private String                                 sname;
    // 车组状态
    private String                                 trainStatus;
    // 配属局编码
    private String                                 jcode;
    // 服务站编码
    private String                                 stationCode;
    // 预警项目
    private String                                 evCode;
    // 所属系统编码
    private String                                 sys_code;
    private String                                 occrMode;           // 故障模式 0:非维护,1:维护
    private String                                 occrStatus;         // 故障状态 0:恢复,1:发生
    private String                                 refId;              // 关联故障记录
    private String                                 colectTime;         // 采集数据时间接收时间
    private String                                 createTime;         // 预警创建时间
    private double                                 param;              // 参数值
    private String                                 toolTip;            // 预警项目提示
    private String                                 warnStatus;
    private String                                 handleStatus;       // 故障处理状态
    private String                                 posId;
    private String                                 lastHappen;
    private List<FaultRecUto>                      faultRecUtos;
    private String                                 field;              // redis中预警存放的hash-key
    private String                                 parmType;           // 预警参数类型[0:整车,1:单元,2:车厢,3:部件]
    private String                                 dataType;
    //private List<NumberRecord>                     numberRecords;
    private double                                 maxNumber;
    private double                                 diffVal;
    private String                                 maxParamVals;
    // 预警规则配置中的所有参数值
    private String                                 maxAllParamVals;
    private String                                 tipType;
    private String                                 maxParmCode;
    private double                                 maxDiffVal;
    // 0:未处理 1:已处理
    private String                                 flag;               // 标志位(默认为0)
    // 判断是否为小齿轮
    private boolean                                fldwh;
    // CRH5
    private boolean                                fldwhCRH5;
    private String                                 faultType;
    private Set<String>                            timeSet;
    private String                                 maxTime;            // 现把这个字段做为 采集数据时间
    private String                                 groundType;         // 接地类型：null-无此类型，接地，非接地
    private String                                 recInfos;           // 详细故障信息
    private int                                    tipTypeOrder;       // 超温超差排序字段
    // 预警导出新加字段
    private String                                 snFlag;
    // 最大参数值
    private double                                 maxVal;
    private String                                 maxEnvTemp;         // 最大差值时对应环境温度
    private boolean                                isCrh5ZhuanXiangJia;// 是否是CRH5型车转向架
    private String                                 nomalPosCode;       // CRH5转向架轴箱对应正常轴位CODE
    private String                                 nomalPosVal;        // CRH5转向架轴箱对应正常轴位value值

    // ---------突变预警增加的字段开始----------
    private Integer                                ruleTime;           // 时间（分钟）
    private String                                 ruleType;           // 规则类型 0 上升 1 下降
    private Integer                                ruleTemper;         // 温度值
    private Long                                   subTime;            // 时间差
    private Double                                 subValue;           // 差值
    private double                                 rate;               // 增长率
    private Set<String>                            times;              // 历史时间
    // ---------突变预警增加的字段结束----------
    private List<Map<String, Map<String, Object>>> recInfosList;
    // 参数名称
    private String                                 subName;

    private byte[]                                 recInfosByte;

    private String                                 maxDifference;
    private String                                 difference;

    private Object                                 faultData;
    private String                                 param_name;
    private String                                 paramVals;
    private String                                 train_no;
    private String                                 maxMessage;
    private String                                 paramValue;
    private String                                 currentParamTime;   // 当前数据预警时间
    private String                                 currentParmVal;     // 当前数据预警温度
    private String                                 maxTrainStatus;     // 运营状态
    private String                                 maxColectTime;      // 接收时间
    private String                                 isPush;             // 推送状态

    private String                                 faultTime;
    private List<WarnTimes>                        warnTimes;

    public List<WarnTimes> getWarnTimes() {
        return warnTimes;
    }

    public void setWarnTimes(List<WarnTimes> warnTimes) {
        this.warnTimes = warnTimes;
    }

    public double getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(double maxVal) {
        this.maxVal = maxVal;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public String getIsPush() {
        return isPush;
    }

    public void setIsPush(String isPush) {
        this.isPush = isPush;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public Set<String> getTimeSet() {
        return timeSet;
    }

    public void setTimeSet(Set<String> timeSet) {
        this.timeSet = timeSet;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public boolean isFldwh() {
        return fldwh;
    }

    public void setFldwh(boolean fldwh) {
        this.fldwh = fldwh;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMaxParmCode() {
        return maxParmCode;
    }

    public void setMaxParmCode(String maxParmCode) {
        this.maxParmCode = maxParmCode;
    }

    public String getTipType() {
        return tipType;
    }

    public void setTipType(String tipType) {
        this.tipType = tipType;
    }

    public String getMaxParamVals() {
        return maxParamVals;
    }

    public void setMaxParamVals(String maxParamVals) {
        this.maxParamVals = maxParamVals;
    }

    public double getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(double maxNumber) {
        this.maxNumber = maxNumber;
    }

    /*public List<NumberRecord> getNumberRecords() {
        return numberRecords;
    }

    public void setNumberRecords(List<NumberRecord> numberRecords) {
        this.numberRecords = numberRecords;
    }*/

    public String getLastHappen() {
        return lastHappen;
    }

    public void setLastHappen(String lastHappen) {
        this.lastHappen = lastHappen;
    }

    public List<FaultRecUto> getFaultRecUtos() {
        return faultRecUtos;
    }

    public void setFaultRecUtos(List<FaultRecUto> faultRecUtos) {
        this.faultRecUtos = faultRecUtos;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getTrain_type() {
        return train_type;
    }

    public void setTrain_type(String train_type) {
        this.train_type = train_type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public String getFault_level() {
        return fault_level;
    }

    public void setFault_level(String fault_level) {
        this.fault_level = fault_level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWarnRemark() {
        return StringUtils.isEmpty(this.maxMessage) ? this.warnRemark : this.maxMessage;
    }

    public void setWarnRemark(String warnRemark) {
        this.warnRemark = warnRemark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFault_code() {
        return fault_code;
    }

    public void setFault_code(String fault_code) {
        this.fault_code = fault_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFault_desc() {
        return fault_desc;
    }

    public void setFault_desc(String fault_desc) {
        this.fault_desc = fault_desc;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getIsSendNote() {
        return isSendNote;
    }

    public void setIsSendNote(String isSendNote) {
        this.isSendNote = isSendNote;
    }

    public String getTrainStatus() {
        return trainStatus;
    }

    public void setTrainStatus(String trainStatus) {
        this.trainStatus = trainStatus;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getEvCode() {
        return evCode;
    }

    public void setEvCode(String evCode) {
        this.evCode = evCode;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getJcode() {
        return jcode;
    }

    public void setJcode(String jcode) {
        this.jcode = jcode;
    }

    public String getSys_code() {
        return sys_code;
    }

    public void setSys_code(String sys_code) {
        this.sys_code = sys_code;
    }

    public String getOccrMode() {
        return occrMode;
    }

    public void setOccrMode(String occrMode) {
        this.occrMode = occrMode;
    }

    public String getOccrStatus() {
        return occrStatus;
    }

    public void setOccrStatus(String occrStatus) {
        this.occrStatus = occrStatus;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getColectTime() {
        return colectTime;
    }

    public void setColectTime(String colectTime) {
        this.colectTime = colectTime;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public String getDescer() {
        return descer;
    }

    public void setDescer(String descer) {
        this.descer = descer;
    }

    public String getWarnStatus() {
        return warnStatus;
    }

    public void setWarnStatus(String warnStatus) {
        this.warnStatus = warnStatus;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getParmType() {
        return parmType;
    }

    public void setParmType(String parmType) {
        this.parmType = parmType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getGroundType() {
        return groundType;
    }

    public void setGroundType(String groundType) {
        this.groundType = groundType;
    }

    public String getRecInfos() {
        return recInfos;
    }

    public void setRecInfos(String recInfos) {
        this.recInfos = recInfos;
    }

    public int getTipTypeOrder() {
        return tipTypeOrder;
    }

    public void setTipTypeOrder(int tipTypeOrder) {
        this.tipTypeOrder = tipTypeOrder;
    }

    public double getParam() {
        return param;
    }

    public void setParam(double param) {
        this.param = param;
    }

    public double getDiffVal() {
        return diffVal;
    }

    public void setDiffVal(double diffVal) {
        this.diffVal = diffVal;
    }

    public double getMaxDiffVal() {
        return maxDiffVal;
    }

    public void setMaxDiffVal(double maxDiffVal) {
        this.maxDiffVal = maxDiffVal;
    }

    public int getCar_no() {
        return car_no;
    }

    public void setCar_no(int car_no) {
        this.car_no = car_no;
    }

    public String getSnFlag() {
        return snFlag;
    }

    public void setSnFlag(String snFlag) {
        this.snFlag = snFlag;
    }

    public String getMaxEnvTemp() {
        return maxEnvTemp;
    }

    public void setMaxEnvTemp(String maxEnvTemp) {
        this.maxEnvTemp = maxEnvTemp;
    }

    public boolean isFldwhCRH5() {
        return fldwhCRH5;
    }

    public void setFldwhCRH5(boolean fldwhCRH5) {
        this.fldwhCRH5 = fldwhCRH5;
    }

    public boolean isCrh5ZhuanXiangJia() {
        return isCrh5ZhuanXiangJia;
    }

    public void setCrh5ZhuanXiangJia(boolean isCrh5ZhuanXiangJia) {
        this.isCrh5ZhuanXiangJia = isCrh5ZhuanXiangJia;
    }

    public String getNomalPosCode() {
        return nomalPosCode;
    }

    public void setNomalPosCode(String nomalPosCode) {
        this.nomalPosCode = nomalPosCode;
    }

    public String getNomalPosVal() {
        return nomalPosVal;
    }

    public void setNomalPosVal(String nomalPosVal) {
        this.nomalPosVal = nomalPosVal;
    }

    public Integer getRuleTime() {
        return ruleTime;
    }

    public void setRuleTime(Integer ruleTime) {
        this.ruleTime = ruleTime;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getRuleTemper() {
        return ruleTemper;
    }

    public void setRuleTemper(Integer ruleTemper) {
        this.ruleTemper = ruleTemper;
    }

    public Long getSubTime() {
        return subTime;
    }

    public void setSubTime(Long subTime) {
        this.subTime = subTime;
    }

    public Double getSubValue() {
        return subValue;
    }

    public void setSubValue(Double subValue) {
        this.subValue = subValue;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Set<String> getTimes() {
        return times;
    }

    public void setTimes(Set<String> times) {
        this.times = times;
    }

    public byte[] getRecInfosByte() {
        return recInfosByte;
    }

    public void setRecInfosByte(byte[] recInfosByte) {
        this.recInfosByte = recInfosByte;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getMaxDifference() {
        if (maxDifference == null) {
            return getDifference();
        }
        return maxDifference;
    }

    public void setMaxDifference(String maxDifference) {
        this.maxDifference = maxDifference;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }

    public Object getFaultData() {
        return faultData;
    }

    public void setFaultData(Object faultData) {
        this.faultData = faultData;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public List<Map<String, Map<String, Object>>> getRecInfosList() {
        return recInfosList;
    }

    public void setRecInfosList(List<Map<String, Map<String, Object>>> recInfosList) {
        this.recInfosList = recInfosList;
    }

    public String getParamVals() {
        return paramVals;
    }

    public void setParamVals(String paramVals) {
        this.paramVals = paramVals;
    }

    public String getTrain_no() {
        return train_no;
    }

    public void setTrain_no(String train_no) {
        this.train_no = train_no;
    }

    public String getMaxMessage() {
        return maxMessage;
    }

    public void setMaxMessage(String maxMessage) {
        this.maxMessage = maxMessage;
    }

    /**
     * 获取maxAllParamVals
     */
    public String getMaxAllParamVals() {
        return maxAllParamVals;
    }

    /**
     * 设置maxAllParamVals
     */
    public void setMaxAllParamVals(String maxAllParamVals) {
        this.maxAllParamVals = maxAllParamVals;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getCurrentParamTime() {
        return currentParamTime;
    }

    public void setCurrentParamTime(String currentParamTime) {
        this.currentParamTime = currentParamTime;
    }

    public String getCurrentParmVal() {
        return currentParmVal;
    }

    public void setCurrentParmVal(String currentParmVal) {
        this.currentParmVal = currentParmVal;
    }

    public String getMaxTrainStatus() {
        return maxTrainStatus;
    }

    public void setMaxTrainStatus(String maxTrainStatus) {
        this.maxTrainStatus = maxTrainStatus;
    }

    public String getMaxColectTime() {
        return maxColectTime;
    }

    public void setMaxColectTime(String maxColectTime) {
        this.maxColectTime = maxColectTime;
    }

    public String getFaultTime() {
        return faultTime;
    }

    public void setFaultTime(String faultTime) {
        this.faultTime = faultTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
