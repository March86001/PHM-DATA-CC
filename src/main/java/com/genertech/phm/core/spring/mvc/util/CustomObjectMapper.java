package com.genertech.phm.core.spring.mvc.util;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

/**
 * <p>
 * 描 述：定制ObjectMapper，主要用于定义Date和Clob类型的序列化
 * </p>
 * <p>
 * 创 建 人：hadoop
 * </p>
 * <p>
 * 创建时间：2015年12月23日上午9:15:21
 * </p>
 */
public class CustomObjectMapper extends ObjectMapper
{

    public CustomObjectMapper()
    {
        changeSerializer("yyyy-MM-dd HH:mm:ss");
        changeDeserializer("yyyy-MM-dd HH:mm:ss");
    }

    public CustomObjectMapper(String dateTimePattern)
    {
        changeSerializer(dateTimePattern);
        changeDeserializer(dateTimePattern);
    }

    public void changeSerializer(final String dateTimePattern)
    {
        CustomSerializerFactory factory = new CustomSerializerFactory();
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>()
        {
            @Override
            public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider)
                    throws IOException, JsonProcessingException
            {
                SimpleDateFormat sdf = new SimpleDateFormat(dateTimePattern);
                jsonGenerator.writeString(sdf.format(value));
            }
        });
        factory.addGenericMapping(Clob.class, new JsonSerializer<Clob>()
        {
            @Override
            public void serialize(Clob clob, JsonGenerator jsonGenerator, SerializerProvider provider)
                    throws IOException, JsonProcessingException
            {
                try
                {
                    jsonGenerator.writeString((clob == null || clob.length() == 0) ? "" : clob.getSubString((long) 1,
                            (int) clob.length()));
                }
                catch (SQLException e)
                {
                    jsonGenerator.writeString("");
                }
            }
        });
        setSerializerFactory(factory);
        setSerializationInclusion(Inclusion.NON_NULL);// null不参加序列化
    }

    public void changeDeserializer(String dateTimePattern)
    {
        this.setDateFormat(new SimpleDateFormat(dateTimePattern));
    }
}
