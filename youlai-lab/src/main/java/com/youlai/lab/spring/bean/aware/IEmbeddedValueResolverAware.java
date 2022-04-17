package com.youlai.lab.spring.bean.aware;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/20 0020 20:32
 */
public class IEmbeddedValueResolverAware implements EmbeddedValueResolverAware {

    private StringValueResolver stringValueResolver;
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
        System.out.println("\n--------------------");
        System.out.println("EmbeddedValueResolverAware:"+resolver);
        System.out.println("--------------------");
    }
}
