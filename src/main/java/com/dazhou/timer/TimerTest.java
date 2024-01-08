package com.dazhou.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-08 22:02
 */
public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer();//任务启动
        for (int i = 0; i < 2; i++) {
            FooTimerTask task = new FooTimerTask("foo" + i);
            timer.schedule(task,new Date(),2000);//任务添加 10:00:00 10:00:02 10:00:04
            //预设的执行时间nextExecutTime 10:00:00 10:00:02 10:00:04
            //schedule 真正的执行时间，取决于上一个任务的结束时间，ExecutTime 03 05 08
            //可能会导致任务丢失。执行时间超过我们设置的时间间隔就会导致原来的时间延长，本来10s能执行5次，现在可能只有4次
            //scheduleAtFixedRate 严格按照预设时间 10:00:00 10:00:02 10:00:04
            //可能会执行时间会乱。这个任务没执行完 下一个任务就执行了
            //timer是单线程运行的，单线程会导致任务阻塞，任务超时
            //解决方法在真正执行方法的地方使用线程池执行
        }
    }

}
class FooTimerTask extends TimerTask{
    private String name;

    public FooTimerTask(String name){
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
