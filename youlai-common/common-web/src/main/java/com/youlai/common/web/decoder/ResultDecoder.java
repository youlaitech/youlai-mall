package com.youlai.common.web.decoder;

import cn.hutool.json.JSONUtil;
import com.youlai.common.result.Result;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author haoxr
 * @since 2023/8/23
 */
public class ResultDecoder implements Decoder {

    private final SpringDecoder decoder;

    public ResultDecoder(SpringDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {

        Reader reader = response.body().asReader(StandardCharsets.UTF_8);

        Result<?> responseData = JSONUtil.toBean(, Result.class);
        Assert.notNull(responseData, "feign invoke failed");
        if (ResponseData.SUCCESS.equals(responseData.getCode())) {
            return responseData.getData();
        }
        throw new CustomMessageException("数据解析失败");
    }
}
