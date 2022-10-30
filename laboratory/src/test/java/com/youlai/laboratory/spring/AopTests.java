package com.youlai.laboratory.spring;


import com.youlai.laboratory.spring.aop.*;
import com.youlai.laboratory.spring.aop.transactional.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * Aop测试类
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/25 0025 23:42
 */
public class AopTests {

    /**
     * EnableAspectJAutoProxy 自动代理实现过程
     */
    @SneakyThrows
    @Test
    void enableAspectj(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserService.class,MyAnnotationAwareAspectJAutoProxyCreator.class,AspectJAop.class);
        MyAnnotationAwareAspectJAutoProxyCreator creator = context.getBean(MyAnnotationAwareAspectJAutoProxyCreator.class);
        UserService userService = new UserService();
        //查找advisors类型的bean或者标注了@AspectJ注解的bean
        List<Advisor> advisors = creator.findEligibleAdvisors(AspectJAop.class,"aspectJAop");
        for (Advisor advisor:advisors){
            System.out.println(advisor);
        }
        //
//        Object o = creator.wrapIfNecessary(userService, "userService", "userService");
//        ((UserService)o).test();

        //创建代理对象
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(userService);
        proxyFactory.addAdvice(ExposeInvocationInterceptor.INSTANCE);
        proxyFactory.addAdvisors(advisors);
        // 统一转换成环绕通知 适配器模式
        List<Object> interceptionAdvice = proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(UserService.class.getMethod("test"), UserService.class);

        //创建调用链
        MyAnnotationAwareAspectJAutoProxyCreator.MyReflectiveMethodInvocation invocation =  MyAnnotationAwareAspectJAutoProxyCreator.createReflectiveMethodInvocation(null, userService, UserService.class.getMethod("test"), new Object[0], UserService.class, interceptionAdvice);
        invocation.proceed();
    }

    /**
     * aspectj切面
     */
    @SneakyThrows
    @Test
    void aspectj(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AspectJAop.class,UserService.class);
       context.getBean(UserService.class).test();
    }

    /**
     * 切点表达式
     */
    @Test
    void pointcut(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyPointcut.class,PointcutService.class);
        MyPointcut myPointcut = context.getBean(MyPointcut.class);
       myPointcut.execution();
       myPointcut.within();
       myPointcut.thisI();
       myPointcut.target();
       myPointcut.args();
       myPointcut.Itarget();
       myPointcut.Iargs();
       myPointcut.Iwithin();
       myPointcut.Iannotation();
       myPointcut.Ibean();
       myPointcut.staticMethodMatcherPointcut();
    }




    /**
     * 手动代理
     * 使用proxyFactory通过编程创建AOP代理
     */
    @Test
    void proxyFactory(){
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(new UserService());
        factory.addAdvisor(new MyPointcutAdvisor());
        UserService userService = (UserService) factory.getProxy();
        userService.test();
    }

    /**
     * 定义自动代理器,通过名称自动代理
     */
    @Test
    void beanNameAutoProxyCreator(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyBeanNameAutoProxyCreator.class, MyPointcutAdvisor.class, UserService.class);
        UserService userService = context.getBean(UserService.class);
        userService.test();
    }

    /**
     * 注册默认代理
     * 只要有defaultAdvisorAutoProxyCreator这个bean,它就会自动识别所有Advisor中的PointCut进行代理
     */
    @Test
    void defaultAdvisorAutoProxyCreator(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyDefaultAdvisorAutoProxyCreator.class, MyPointcutAdvisor.class, UserService.class);
        UserService userService = context.getBean(UserService.class);
        userService.test();
    }

    /**
     * 使用注解开启自动代理
     * 底层通过添加{@link org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator},
     * 重写了{@link org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator#findCandidateAdvisors()}方法，即可以找到Advisor类型的bean，也能把所有@Aspect注解标注的类扫描出来并生成Advisor
     */
    @Test
    void myEnableAspectJAutoProxy(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyEnableAspectJAutoProxy.class, MyPointcutAdvisor.class,UserService.class);
        UserService userService = context.getBean(UserService.class);
        userService.test();
    }


    /**
     *编程式使用事务方式1:使用TransactionTemplate
     */
    @Test
    void transactionTemplate(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class, MyTransactionTemplate.class, UmsMemberService.class);
        UmsMemberService umsMemberService = context.getBean(UmsMemberService.class);
        TransactionTemplate transactionTemplate = context.getBean(TransactionTemplate.class);
        System.out.println("before transaction");
        Long balance = umsMemberService.getBalance(39L);
        System.out.println("before transaction balance: "+balance);
        System.out.println("transaction....");
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try{
                    umsMemberService.deductBalance(39L,100l,true);
                }catch (Exception e){
                    status.setRollbackOnly();
                    e.printStackTrace();
                }
            }
        });
        System.out.println("after transaction");
        Long balance1 = umsMemberService.getBalance(39L);
        System.out.println("after transaction balance: "+balance1);
    }

    /**
     * 编程式使用事务方式2:使用PlatformTransactionManager
     */
    @Test
    void platformTransactionManager(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class, MyPlatformTransactionManager.class, UmsMemberService.class);
        PlatformTransactionManager transactionManager = context.getBean(PlatformTransactionManager.class);
        UmsMemberService umsMemberService = context.getBean(UmsMemberService.class);
        // 构造一个准备使用此事务的定义信息~~~
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setReadOnly(false);
        //隔离级别,-1表示使用数据库默认级别
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 根据此事务属性，拿到一个事务实例   注意此处的入参是一个：TransactionDefinition
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            System.out.println("before transaction");
            Long balance = umsMemberService.getBalance(39L);
            System.out.println("before transaction balance: "+balance);
            System.out.println("transaction....");
            umsMemberService.deductBalance(39L,100L,true);
            // 提交事务
            transactionManager.commit(transaction);
        } catch (Exception e) {
            // 若发现异常  事务进行回滚
            transactionManager.rollback(transaction);
            e.printStackTrace();
        }
        System.out.println("after transaction");
        Long balance1 = umsMemberService.getBalance(39L);
        System.out.println("after transaction balance: "+balance1);
    }

    /**
     * 声明式事务管理
     */
    @Test
     void transactional(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MytransactionManager.class,JdbcConfig.class, UmsMemberService.class);
        UmsMemberService umsMemberService = context.getBean(UmsMemberService.class);
        System.out.println("before transaction");
        Long balance = umsMemberService.getBalance(39L);
        System.out.println("before transaction balance: "+balance);
        System.out.println("transaction....");
        try {
            umsMemberService.transactionalDeductBalance(39L,100l,true);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("after transaction");
        Long balance1 = umsMemberService.getBalance(39L);
        System.out.println("after transaction balance: "+balance1);
    }

    /**
     * 事务的7种事务传播行为
     * 0:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED} spring默认的事务传播行为,必须运行在事务中,支持当前事务,如果当前没有事务就新建一个事务
     * 1:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_SUPPORTS} 支持当前事务,如果当前没有事务,就以非事务的方式执行,如果有事务就在这个事务中执行
     * 2:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_MANDATORY} 必须运行在事务中,支持当前事务,如果当前没有事务,就抛出异常
     * 3:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_MANDATORY} 必须在他自己的事务中执行,如果存在当前事务,就把当前事务挂起,新建一个事务
     * 4:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_NOT_SUPPORTED} 不会在事务中执行,以非事务的方式执行操作,如果当前存在事务,就把当前事务挂起
     * 5:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_NEVER} 不会在事务中执行,以非事务的方式执行操作,如果当前存在事务,就抛出异常
     * 6:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_NESTED} 如果当前存在事务,则在嵌套事务内执行,嵌套的事务可以单独的提交或回滚,如果当前没有事务,则新建一个事务,需要注意厂商对这种嵌套事务的传播行为的支持
     */
    @Test
    void propagation(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MytransactionManager.class,JdbcConfig.class, UmsMemberService.class);
        UmsMemberService umsMemberService = context.getBean(UmsMemberService.class);
//        umsMemberService.propagaDeductBalance(39L,100l,true,TransactionDefinition.PROPAGATION_REQUIRED);
//        umsMemberService.propagaDeductBalance(39L,100l,true,TransactionDefinition.PROPAGATION_SUPPORTS);
//        umsMemberService.propagaDeductBalance(39L,100l,true,TransactionDefinition.PROPAGATION_MANDATORY);
//        umsMemberService.propagaDeductBalance(39L,100l,true,TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        umsMemberService.propagaDeductBalance(39L,100l,true,TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
//        umsMemberService.propagaDeductBalance(39L,100l,true,TransactionDefinition.PROPAGATION_NEVER);
        umsMemberService.propagaDeductBalance(39L,100l,true,TransactionDefinition.PROPAGATION_NESTED);
    }

    /**
     * 五种事务隔离级别 -1:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT} spring默认使用数据库的隔离级别
     * 五种事务隔离级别 1:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_READ_UNCOMMITTED} 最低的隔离级别,允许读取尚未提交的数据变更,可能会导致脏读,幻读,不可重复读
     * 五种事务隔离级别 2:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT} 允许读取并发事务已经提交的数据,可以阻止脏读,但是还是可能会发生幻读,不可重复读
     * 五种事务隔离级别 4:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT} 对同一字段的多次读取结果都是一致的,除非数据是被事务本身自己所修改,可以阻止脏读,不可重复读,但幻读仍有可能发生
     * 五种事务隔离级别 8:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT} 最高隔离级别,完全服从ACID的隔离级别,可以阻止脏读,不可重复读,幻读
     */
    @Test
    void isolation(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MytransactionManager.class,JdbcConfig.class, UmsMemberService.class);
        UmsMemberService umsMemberService = context.getBean(UmsMemberService.class);
//        umsMemberService.isolationDeductBalance(39L,100L,Boolean.TRUE,TransactionDefinition.ISOLATION_DEFAULT);
//        umsMemberService.isolationDeductBalance(39L,100L,Boolean.TRUE,TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
//        umsMemberService.isolationDeductBalance(39L,100L,Boolean.TRUE,TransactionDefinition.ISOLATION_READ_COMMITTED);
//        umsMemberService.isolationDeductBalance(39L,100L,Boolean.TRUE,TransactionDefinition.ISOLATION_REPEATABLE_READ);
        umsMemberService.isolationDeductBalance(39L,100L,Boolean.TRUE,TransactionDefinition.ISOLATION_SERIALIZABLE);
    }



}
