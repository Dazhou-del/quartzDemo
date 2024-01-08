package com.dazhou.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-08 22:47
 */
//如果不加这个注解那么这个定时任务是，每一s执行一次，不管你的任务是否执行完，都是一秒一次
    //也就是多线程执行。很多时候我们都不希望这样，得先让上一个任务执行完了才执行下一个任务。添加这个注解即可
@DisallowConcurrentExecution
@PersistJobDataAfterExecution  //将JobDataMap持久化 对trigger无效。有了这个特性就可以进行其他操作
public class TestJob {
    public static void main(String[] args) {
        JobDetail jobDetail =JobBuilder.newJob(Myjob.class) //任务
                .withIdentity("job1","group1") //设置属性，1任务名称，2是组 名称唯一
                .usingJobData("jobDetail","jobDetail") //设置一些参数在 job中可以获取到
                .usingJobData("name","jobDetailname")
                .build();


        Trigger trigger = TriggerBuilder.newTrigger() //触发器
                .withIdentity("trigger1","group1") //设置属性，1触发名称，2是组 名称唯一
                .usingJobData("trigger1","trigger1") //设置一些参数在 job中可以获取到
                .usingJobData("name","nametrigger1") //设置一些参数在 job中可以获取到
                .startNow()     //SimpleScheduleBuilder 可以创建不同的Schedule
                                // withIntervalInSeconds是设置间隔时间
                                //repeatForever是一直重复执行
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1)
                        .repeatForever())
                .build();
        //调度器
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();//执行
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
