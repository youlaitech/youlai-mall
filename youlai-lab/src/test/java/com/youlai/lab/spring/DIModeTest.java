package com.youlai.lab.spring;

import com.youlai.lab.spring.DI.ByConstructorService;
import com.youlai.lab.spring.DI.ByDefaultService;
import com.youlai.lab.spring.DI.ByNameService;
import com.youlai.lab.spring.DI.ByTypeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试4种自动装配模型，默认no,(no,byName,byName,constructor)
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/19 0019 18:00
 */
@Slf4j
public class DIModeTest {

    @Test
    void noTest(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/DI/DIModel.xml");
        applicationContext.refresh();
        ByDefaultService no = applicationContext.getBean("no", ByDefaultService.class);
        log.info("装配成功？{}", no.bean==null);
    }


    @Test
    void byName(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/DI/DIModel.xml");
        applicationContext.refresh();
        ByNameService byName = applicationContext.getBean("byName", ByNameService.class);
        log.info("装配成功?：{}",byName.bean != null);
    }

    @Test
    void byType(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/DI/DIModel.xml");
        applicationContext.refresh();
        ByTypeService byType = applicationContext.getBean("byType", ByTypeService.class);
        log.info("装配成功?：{}",byType.bean != null);
    }

    @Test
    void constructor(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/DI/DIModel.xml");
        applicationContext.refresh();
        ByConstructorService constructor = applicationContext.getBean("constructor", ByConstructorService.class);
        log.info("装配成功?：{}",constructor.getBean() != null);
    }



}
