package com.xiaochangwei.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleController {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 1000)
	public void reportCurrentTime() {
//		System.out.println("现在时间：" + dateFormat.format(new Date()));
	}
	
	private int count=0;

    @Scheduled(cron="*/1 * * * * ?")
    private void process(){
//        System.out.println("this is scheduler task runing  "+(count++));
    }
}
