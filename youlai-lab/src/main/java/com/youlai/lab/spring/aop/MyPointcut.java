package com.youlai.lab.spring.aop;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * 切点的使用
 *
 * execution   用于匹配方法执行的连接点
 * within      匹配指定类型内的方法执行 用于匹配当前AOP代理对象类型的 	within(x)匹配规则target.getClass().equals(x)
 * this        用于匹配当前AOP代理对象类型的执行方法，包含引入的接口类型匹配 this(x)匹配规则：x.getClass.isAssingableFrom(proxy.getClass)
 * target      用于匹配当前目标对象类型的执行方法，不包括引入接口的类型匹配 target(x)匹配规则：x.getClass().isAssignableFrom(target.getClass());
 * args        用于匹配当前执行的方法传入的参数为指定类型的执行方法 target.class.getAnnotation(指定的注解类型) != null
 * @target     用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解 target.class.getAnnotation(指定的注解类型) != null
 * @args       用于匹配当前执行的方法传入的参数持有指定注解的执行 传入的目标位置参数.getClass().getAnnotation(@args(对应的参数位置的注解类型))!= null
 * @within     用于匹配所有持有指定注解类型内的方法 被调用的目标方法Method对象.getDeclaringClass().getAnnotation(within中指定的注解类型) != null
 * @annotation 用于匹配当前执行方法持有指定注解的方法 target.getClass().getMethod("目标方法名").getDeclaredAnnotation(@annotation(目标注解))!=null
 * bean Spring AOP扩展的，AspectJ没有对应的指示符，用于匹配特定名称的Bean对象的执行方法 ApplicationContext.getBean("bean表达式中指定的bean名称") != null
 * 通配符:
 *  *:匹配任何数量字符
 *  ..:匹配任何数量字符的重复，如任何数量子包，任何数量方法参数
 *  +:匹配指定类型及其子类型，仅作为后缀防过载类型模式后面。
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/27 0027 20:08
 */
public class MyPointcut {


    /**
     * exection格式
     * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)
     *                 throws-pattern?)
     * 其中带 ?号的 modifiers-pattern?，declaring-type-pattern?，throws-pattern?是可选项
     * ret-type-pattern,name-pattern, parameters-pattern是必选项
     *
     * modifier-pattern? 修饰符匹配，如public 表示匹配公有方法，*表示任意修饰符
     * ret-type-pattern 返回值匹配，* 表示任何返回值，全路径的类名等
     * declaring-type-pattern? 类路径匹配
     * name-pattern 方法名匹配，* 代表所有，xx*代表以xx开头的所有方法
     * (param-pattern) 参数匹配，指定方法参数(声明的类型)，(..)代表所有参数，(*,String)代表第一个参数为任何值,第二个为String类型，(..,String)代表最后一个参数是String类型
     * throws-pattern? 异常类型匹配
     */
    public boolean execution(){
        //com.youlai.laboratory.spring.aop.PointcutService类里的m1方法
        return doMatches("execution(* com.youlai.laboratory.spring.aop.PointcutService.m1(..))");
    }

    /**
     * within(类型表达式)：目标对象target的类型是否和within中指定的类型匹配
     * 匹配规则： target.getClass().equals(within表达式中指定的类型)
     * @return
     */
    public boolean within(){
        //com.youlai.laboratory.spring.aop.PointcutService类里的所有方法
        return doMatches("within(com.youlai.laboratory.spring.aop.PointcutService)");
    };


    /**
     * this(类型全限定名)：通过aop创建的代理对象的类型是否和this中指定的类型匹配；this中使用的表达式必须是类型全限定名，不支持通配符
     * this(x)的匹配规则是：x.getClass.isAssingableFrom(proxy.getClass)
     * @return
     */
    public boolean thisI(){
        return doMatches("this(com.youlai.laboratory.spring.aop.PointcutService)");
    }

    /**
     * target(类型全限定名)：判断目标对象的类型是否和指定的类型匹配；表达式必须是类型全限定名，不支持通配符
     * target(x)匹配规则：x.getClass().isAssignableFrom(target.getClass());
     * @return
     */
    public boolean target(){
        return doMatches("target(com.youlai.laboratory.spring.aop.PointcutService)");
    }


    /**
     * args(参数类型列表)匹配当前执行的方法传入的参数是否为args中指定的类型；参数类型列表中的参数必须是类型全限定名，不支持通配符；args属于动态切入点，也就是执行方法的时候进行判断的，开销非常大，非特殊情况最好不要使用。
     * args(String) //    方法个数为1，类型是String
     * args(*,String) //  方法参数个数2，第2个是String类型
     * args(..,String) // 方法个数不限制，最后一个必须是String
     * @return
     */
    public boolean args(){
        return doMatches("args(String)");
    }


    /**
     * @within(注解类型)：匹配指定的注解内定义的方法。
     * 匹配规则： 被调用的目标方法Method对象.getDeclaringClass().getAnnotation(within中指定的注解类型) != null
     * @return
     */
    public boolean  Iwithin(){
        return doMatches("@within(org.springframework.transaction.annotation.Transactional)");
    }

    /**
     * @target(注解类型)：判断目标对象target类型上是否有指定的注解；@target中注解类型也必须是全限定类型名。
     * 匹配规则： target.class.getAnnotation(指定的注解类型) != null
     * 注意，如果目标注解是标注在父类上的，那么定义目标注解时候应使用@Inherited标注，使子类能继承父类的注解。
     * @return
     */
    public boolean  Itarget(){
        return doMatches("@target(org.springframework.transaction.annotation.Transactional)");
    }


    /**
     * @args(注解类型)：方法参数所属的类上有指定的注解；注意不是参数上有指定的注解，而是参数类型的类上有指定的注解。和args类似，不过针对的是参数类型上的注解。
     * 匹配规则： 传入的目标位置参数.getClass().getAnnotation(@args(对应的参数位置的注解类型))!= null
     * @return
     */
    public boolean  Iargs(){
        return doMatches("@args(org.springframework.transaction.annotation.Transactional)");
    }

    /**
     * @annotation(注解类型)：匹配被调用的目标对象的方法上有指定的注解
     * 匹配规则：target.getClass().getMethod("目标方法名").getDeclaredAnnotation(@annotation(目标注解))!=null
     * @return
     */
    public boolean  Iannotation(){
        return doMatches("@annotation(org.springframework.transaction.annotation.Transactional)");
    }

    /**
     * bean(bean名称)：这个用在spring环境中，匹配容器中指定名称的bean。
     * 匹配格式：ApplicationContext.getBean("bean表达式中指定的bean名称") != null
     * @return
     */
    public boolean Ibean(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("bean(pointcutService)");
        return doMatches("bean(pointcutService)");
    }


    private boolean doMatches(String expression){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        try {
            System.out.println("PointcutService.m1:"+expression+":"+pointcut.matches(PointcutService.class.getMethod("m1"), PointcutService.class));
            System.out.println("PointcutService.m2:"+expression+":"+pointcut.matches(PointcutService.class.getMethod("m2"), PointcutService.class));
            System.out.println("PointcutService.m3:"+expression+":"+pointcut.matches(PointcutService.class.getMethod("m3", String.class), PointcutService.class));
            System.out.println("PointcutService.m4:"+expression+":"+pointcut.matches(PointcutService.class.getMethod("m4"), PointcutService.class));
            System.out.println("-----------------------------------------------------");
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void staticMethodMatcherPointcut(){
        StaticMethodMatcherPointcut matcherPointcut = new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {

                MergedAnnotations annotations = MergedAnnotations.from(method);
                //方法上是否有@transactional注解
                if(annotations.isPresent(Transactional.class)){
                    return true;
                }
               annotations = MergedAnnotations.from(targetClass);
                //类上是否有@transactional注解
                if(annotations.isPresent(Transactional.class)){
                    return true;
                }
                annotations = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                //父类或实现的接口上是否有@transactional注解
                if(annotations.isPresent(Transactional.class)){
                    return true;
                }
                return false;
            }
        };

        try {
            System.out.println("PointcutService.m1:"+matcherPointcut.matches(PointcutService.class.getMethod("m1"), PointcutService.class));
            System.out.println("PointcutService.m2:"+matcherPointcut.matches(PointcutService.class.getMethod("m2"), PointcutService.class));
            System.out.println("PointcutService.m3:"+matcherPointcut.matches(PointcutService.class.getMethod("m3", String.class), PointcutService.class));
            System.out.println("PointcutService.m4:"+matcherPointcut.matches(PointcutService.class.getMethod("m4"), PointcutService.class));
            System.out.println("-----------------------------------------------------");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


}
