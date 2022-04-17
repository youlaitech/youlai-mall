package com.youlai.lab.spring.beanDefinition;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 *
 * 解析xml文档
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/5 0005 12:51
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	/**
	 * Element对应的类
	 * @param element the {@code Element} that is being parsed
	 * @return
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		return User.class;
	}

	/**
	 * 从element中解析并提取对应的元素
	 * @param element the XML element being parsed
	 * @param parserContext the object encapsulating the current state of the parsing process
	 * @param bean used to define the {@code BeanDefinition}
	 */
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder bean) {
		String userName = element.getAttribute("userName");
		String email = element.getAttribute("email");
		//将提取到的数据放入BeanDefinitionBuilder中,待到完成所有bean的解析后统一注册到beanFactory中
		if(StringUtils.hasText(userName)){
			bean.addPropertyValue("userName",userName);
		}
		if(StringUtils.hasText(email)){
			bean.addPropertyValue("email",email);
		}
	}
}
