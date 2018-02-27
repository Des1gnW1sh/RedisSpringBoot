package com.scnjwh.intellectreport.common.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2017-10-17.
 * Created by Duan
 * 生成UUID作为ID值
 *
 */
public class IdUtils {
    public  static String getUUid(){
        String uuid = UUID.randomUUID().toString();
        System.out.println("----- 生成uuid:"+uuid+"-------");
        return uuid;
    }
}
