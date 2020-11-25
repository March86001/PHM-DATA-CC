package com.genertech.phm.dzs.dto;

/**
 * 
* @ClassName: FaultRecUto 
* @Description: TODO(记录故障发生及恢复时间) 
* @author zwt
* @date 2015-5-11 上午9:19:03 
*
 */
public class FaultRecUto {
	
	//发生时间
	String happen;
	//恢复时间
	String regain;
	
	public String getHappen() {
		return happen;
	}
	public void setHappen(String happen) {
		this.happen = happen;
	}
	public String getRegain() {
		return regain;
	}
	public void setRegain(String regain) {
		this.regain = regain;
	}
}
