package com.genertech.phm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.genertech.phm.dzs.model.ModelInfo;
import com.genertech.phm.dzs.model.OutputInfo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TTPropsUtil {



	private static TTProperties props = null;
	private TTPropsUtil(){}

	public static TTProperties getProps(){
		if(props == null){
			props = new TTProperties();
			InputStream is = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			StringBuffer stringBuffer = new StringBuffer();
			try {
				Resource resource = new ClassPathResource("tt.properties");
				is = resource.getInputStream();
				isr = new InputStreamReader(is,"UTF-8");
				br = new BufferedReader(isr);
				String data = null;
				while((data = br.readLine()) != null) {
					stringBuffer.append(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if (br != null)br.close();
					if (isr != null)isr.close();
					if (is != null)is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			List<ModelInfo> list = JSON.parseArray(stringBuffer.toString(),ModelInfo.class);
			props.addAll(list);
		}
		return props;
	}

	public static String getPropsValue(String key){
		TTProperties p = getProps();
		return null;
	}

	public static String getPropsValue(String key, String defaultVal){
		TTProperties p = getProps();
		return null;
	}

	public static class TTProperties extends ArrayList<ModelInfo> {
		public TTProperties() {
		}


		public ModelInfo getModelInfo(String ckId,String equipType,String train, Integer source) {
			for (ModelInfo modelInfo:this) {
				if ((modelInfo.getSource().equals(ModelInfo.SOURCE_CZ) || modelInfo.getSource().equals(ModelInfo.SOURCE_XJ))
						&& modelInfo.getCkId().equals(ckId) && modelInfo.getEquipType().equals(equipType)
						&& modelInfo.getSource().equals(source) ) {
					for (String _t:modelInfo.getTrains()) {
						if (_t.equals(train)) {
							return modelInfo;
						}
					}
				}
			}
			return null;
		}
		public ModelInfo getModelInfo2(String ckId,String equipType,String train, String modelName, Integer source) {
			for (ModelInfo modelInfo:this) {
				if (modelInfo.getSource().equals(ModelInfo.SOURCE_SH)
						&& modelInfo.getModelName().equals(modelName) && modelInfo.getEquipType().equals(equipType)
						&& modelInfo.getSource().equals(source) && modelInfo.getCkId().equals(ckId)) {
					for (String _t:modelInfo.getTrains()) {
						if (_t.equals(train)) {
							return modelInfo;
						}
					}
				}
			}
			return null;
		}


	}

}
