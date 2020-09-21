package com.youlai.auth.component;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.youlai.auth.exception.CustomOAuth2Exception;
import com.youlai.common.core.result.ResultCodeEnum;
import lombok.SneakyThrows;


/**
 * 异常信息格式化
 *
 * @author haoxr
 * @date 2020/09/21
 */
public class OAuth2ExceptionSerializer extends StdSerializer<CustomOAuth2Exception> {

    public OAuth2ExceptionSerializer() {
        super(CustomOAuth2Exception.class);
    }

    @Override
    @SneakyThrows
    public void serialize(CustomOAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("code",e.getOAuth2ErrorCode());
        jsonGenerator.writeStringField("msg", e.getMessage());
        jsonGenerator.writeStringField("data", e.getOAuth2ErrorCode());
        jsonGenerator.writeEndObject();
    }
}
