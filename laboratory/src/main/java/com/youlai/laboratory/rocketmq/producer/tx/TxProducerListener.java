package com.youlai.laboratory.rocketmq.producer.tx;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zc
 * @date 2022-10-27 00:56
 */
@Slf4j
@RocketMQTransactionListener(rocketMQTemplateBeanName = "txRocketMQTemplate")
public class TxProducerListener implements RocketMQLocalTransactionListener {

    /**
     * 记录各个事务Id的状态:1-正在执行，2-执行成功，3-执行失败
     */
    private ConcurrentHashMap<String, Integer> transMap = new ConcurrentHashMap<>();

    /**
     * 执行本地事务
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 执行本地事务
        String transId = msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID).toString();
        log.info("消息事务id为:" + transId);
        // 状态为正在执行
        transMap.put(transId, 1);
        try {
            log.info("正在执行本地事务");

            // 模拟耗时操作估计出发mq回查操作：当RocketMQ长时间(1分钟)没有收到本地事务的返回结果
            // TimeUnit.SECONDS.sleep(80);

            // 模拟业代码执行,比如模拟插入user数据到数据库中，并且失败的情况
            System.out.println(1 / 0);

            log.info("事务执行完成.");
        } catch (Exception e) {
            // 状态为执行失败
            transMap.put(transId, 3);
            log.error("事务执行异常.");

            // 出现异常
            // 如果不需要重试 则设置为：ROLLBACK
            // 如果需要检查事务重试,1分钟后发起检查 则设置为：UNKNOWN
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        // 状态为执行成功
        transMap.put(transId, 2);
        return RocketMQLocalTransactionState.COMMIT;
    }


    /**
     * 事务超时，回查方法
     * 检查本地事务,如果RocketMQ长时间(1分钟左右)没有收到本地事务的返回结果，则会定时主动执行改方法，查询本地事务执行情况。
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {

        //根据transaction的id回查该事务的状态,并返回给消息队列
        //未知状态:查询事务状态,但始终无结果,或者由于网络原因发送不成功,对mq来说都是未知状态,LocalTransactionState.UNKNOW
        //正确提交返回LocalTransactionState.COMMIT_MESSAGE
        //事务执行失败返回LocalTransactionState.ROLLBACK_MESSAGE
        String transId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        Integer status = transMap.get(transId);
        // 执行状态 1-正在执行，2-执行成功，3-执行失败
        log.info("回查的事务id为:" + transId + ",当前的状态为：" + status);
        //正在执行
        if (status == 1) {
            log.info("回查结果为：正在执行状态");
            return RocketMQLocalTransactionState.UNKNOWN;
        } else if (status == 2) {
            //执行成功,返回commit
            log.info("回查结果为：成功状态");
            return RocketMQLocalTransactionState.COMMIT;
        } else if (status == 3) {
            //执行失败,返回rollback
            log.info("回查结果为：失败状态");
            // 通过伪代码表示 检查本地事务执行情况
            // User user = selectByUserId(userId);
            // if (user!=null) {
            //     //插入成功(本地事务完成)
            //     return RocketMQLocalTransactionState.COMMIT;
            // } else {
            //      // 插入失败
            //      // 如果不需要再重试 则设置为：ROLLBACK
            //      // 如果还需要检查事务重试 则设置为：UNKNOWN
            //     return RocketMQLocalTransactionState.UNKNOWN;
            // }
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        //  其他未知情况，统一返回不重试，删除消息
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
