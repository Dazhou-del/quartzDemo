package com.dazhou.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-08 22:45
 */
public class Myjob implements Job {
    private String name;

    public void setName(String name){
        this.name=name;
    }

    //相当于之前的task 任务  context相当于一个容器，任务都是在这个容器中执行，可以通过这个容器获取值
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //每一个任务对应的JobDetail和Trigger 都不是同一个。每次都会创建一个新的
        //为的就是保证 每次任务执行时的JobDetail和Trigger 而不是同一个
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        JobDataMap TriggerDataMap = context.getTrigger().getJobDataMap();
        //将两个map合并，key一样会覆盖
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        System.out.println("jobDataMap:"+jobDataMap.get("jobDetail"));
        System.out.println("jobDataMap:"+TriggerDataMap.get("trigger1"));
        System.out.println("mergedJobDataMap:"+mergedJobDataMap.get("jobDetail"));
        System.out.println("myjob："+new Date());

        //如果trigger也设置了会覆盖jobDetail中的值
        System.out.println("name"+name);
    }
}
