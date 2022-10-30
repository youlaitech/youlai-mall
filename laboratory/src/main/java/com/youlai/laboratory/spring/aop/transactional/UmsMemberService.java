package com.youlai.laboratory.spring.aop.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * 用户余额业务,用于演示事务
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/26 0026 19:42
 */
public class UmsMemberService {

    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 查询用户余额
     */
    public Long getBalance(Long id){
        String sql = "select * from ums_member where id = ?";
        RowMapper<UmsMember> rowMapper = new BeanPropertyRowMapper<>(UmsMember.class);
        List<UmsMember> umsMemberList = jdbcTemplate.query(sql,rowMapper,id);
        return umsMemberList.get(0).getBalance();
    }

    /**
     *
     * 扣减会员余额
     * @会员id id
     * @扣减的余额 balance
     * @return
     */
    public boolean deductBalance(Long id,Long balance,boolean flag){
        return doDeductBalance(id, balance, flag);
    }

    /**
     * 加了声明式事务的扣减会员余额
     * @会员id id
     * @扣减的余额 balance
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean transactionalDeductBalance(Long id,Long balance,boolean flag){
        return doDeductBalance(id, balance, flag);
    }

    /**
     * 7种事务传播行为0:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED} spring默认的事务传播行为,必须运行在事务中,支持当前事务,如果当前没有事务就新建一个事务
     * 7种事务传播行为1:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_SUPPORTS} 支持当前事务,如果当前没有事务,就以非事务的方式执行,如果有事务就在这个事务中执行
     * 7种事务传播行为2:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_MANDATORY} 必须运行在事务中,支持当前事务,如果当前没有事务,就抛出异常
     * 7种事务传播行为3:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_MANDATORY} 必须在他自己的事务中执行,如果存在当前事务,就把当前事务挂起,新建一个事务
     * 7种事务传播行为4:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_NOT_SUPPORTED} 不会在事务中执行,以非事务的方式执行操作,如果当前存在事务,就把当前事务挂起
     * 7种事务传播行为5:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_NEVER} 不会在事务中执行,以非事务的方式执行操作,如果当前存在事务,就抛出异常
     * 7种事务传播行为6:{@link org.springframework.transaction.TransactionDefinition#PROPAGATION_NESTED} 如果当前存在事务,则在嵌套事务内执行,嵌套的事务可以单独的提交或回滚,如果当前没有事务,则新建一个事务,需要注意厂商对这种嵌套事务的传播行为的支持
     * @会员id id
     * @扣减的余额 balance
     * @return
     */

    public boolean propagaDeductBalance(Long id,Long balance,boolean flag,int propagation){
        TransactionStatus transactionStatus = buildTransactionStatus(propagation,TransactionDefinition.ISOLATION_DEFAULT);
        return doTransaction(id, balance, flag,transactionStatus);
    }

    /**
     * 五种事务隔离级别 -1:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT} spring默认使用数据库的隔离级别
     * 五种事务隔离级别 1:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_READ_UNCOMMITTED} 最低的隔离级别,允许读取尚未提交的数据变更,可能会导致脏读,幻读,不可重复读
     * 五种事务隔离级别 2:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT} 允许读取并发事务已经提交的数据,可以阻止脏读,但是还是可能会发生幻读,不可重复读
     * 五种事务隔离级别 4:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT} 对同一字段的多次读取结果都是一致的,除非数据是被事务本身自己所修改,可以阻止脏读,不可重复读,但幻读仍有可能发生
     * 五种事务隔离级别 8:{@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT} 最高隔离级别,完全服从ACID的隔离级别,可以阻止脏读,不可重复读,幻读
     *
     * @param id
     * @param balance
     * @param flag
     * @param isolation
     * @return
     */
    public boolean isolationDeductBalance(Long id,Long balance,boolean flag,int isolation){
        TransactionStatus transactionStatus = buildTransactionStatus(TransactionDefinition.PROPAGATION_REQUIRED, isolation);
        return doTransaction(id, balance, flag,transactionStatus);
    }

    /**
     * 获取一个事务实例
     * 获取出来的时候就标志这个事务已经开始了 {@link org.springframework.transaction.support.AbstractPlatformTransactionManager#startTransaction}
     * @return
     */
    public TransactionStatus buildTransactionStatus(int propagation,int isolation){
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setReadOnly(false);
        //事务传播行为
        transactionDefinition.setPropagationBehavior(propagation);
        //隔离级别,-1表示使用数据库默认级别
        transactionDefinition.setIsolationLevel(isolation);
        // 根据此事务属性，拿到一个事务实例   注意此处的入参是一个：TransactionDefinition
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
        return transaction;
    }

    /**
     * 执行一个扣减余额的事务
     * @return
     */
    public boolean doTransaction(Long id,Long balance,boolean flag,TransactionStatus transactionStatus){
        boolean debuct = true;
        try {
            System.out.println("before transaction");
            Long balance1 = this.getBalance(id);
            System.out.println("before transaction balance: "+balance1);
            System.out.println("transaction....");
            deductBalance(id, balance, flag);
            // 提交事务
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            // 若发现异常  事务进行回滚
            transactionManager.rollback(transactionStatus);
            e.printStackTrace();
            debuct = false;
        }
        System.out.println("after transaction");
        Long balance2 = this.getBalance(id);
        System.out.println("after transaction balance: "+balance2);
        return debuct;
    }



    /**
     * 真正执行扣除余额的操作
     * @param id
     * @param balance
     * @param flag
     * @return
     */
    private boolean doDeductBalance(Long id,Long balance,boolean flag){
        String sql = "update ums_member set balance = balance - ? where id = ?";
        int i = jdbcTemplate.update(sql, balance, id);
        if(flag){
            int x = 1/0;
        }
        return i>0;
    }




}
