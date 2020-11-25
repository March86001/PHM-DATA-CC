package com.genertech.phm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSON_UPP {
	/**
      * 
      * 描述：将对象格式化成json字符串
      */
     public static String toJson(Object object){
         try {
             return JSON.toJSONString(object, new SerializerFeature[] {
                 SerializerFeature.WriteMapNullValue, 
                 SerializerFeature.DisableCircularReferenceDetect, 
                 SerializerFeature.WriteNonStringKeyAsString });
         } catch (Exception e) {
             e.printStackTrace();
         }
		return "";
     }

}
