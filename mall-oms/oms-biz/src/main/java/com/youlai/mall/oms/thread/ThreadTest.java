package com.youlai.mall.oms.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main...start...");
        /**
         * 1)、继承Thread类
         *         Thread01 thread01 = new Thread01();
         *         thread01.start();
         *         System.out.println("main...end...");
         * 2)、实现Runnable接口
         *         Thread02 thread02 = new Thread02();
         *         new Thread(thread02).start();
         * 3)、实现Callable接口 + FutureTask (可以拿到返回结果，可以处理异常)
         * 4)、线程池
         */
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
        new Thread(futureTask).start();
        //阻塞等待整个线程执行完成，得到返回结果
        Integer result = futureTask.get();
        System.out.println("main...end..." + result);

    }

    public static class Thread01 extends Thread {
        @Override
        public void run() {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }
    }

    public static class Thread02 implements Runnable {

        @Override
        public void run() {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }
    }

    public static class Callable01 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }
    }
}
