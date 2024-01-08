package com.dazhou.pool;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务线程池
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-08 22:24
 */
public class ScheduleThreadPoolTest {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 2; i++) {
            scheduledThreadPool.scheduleAtFixedRate(new Task("test-"+i),0,2, TimeUnit.SECONDS);
            //schedule 没有间隔时间只执行一次
        }
    }

}
class Task implements Runnable{
    private String name;

    public Task(String name){
        this.name=name;
    }
    @Override
    public void run() {
        try {
            System.out.println("name="+name+",startTime"+new Date());
            Thread.sleep(3000);
            System.out.println("name="+name+",endTime"+new Date());
            //解决方法在真正执行方法的地方使用线程池执行
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
