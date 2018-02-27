package com.scnjwh.financial;
import com.scnjwh.intellectreport.common.utils.MailUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
    public static void main(String[] args){
        System.out.println(MailUtil.checkEmail("sdegvdfbv"));
        System.out.println(MailUtil.checkEmail("@"));
        System.out.println(MailUtil.checkEmail("asd@"));
        System.out.println(MailUtil.checkEmail("sdvf@qq"));
        System.out.println(MailUtil.checkEmail("sdvf@qq."));
        System.out.println(MailUtil.checkEmail("sd@vf@qq.com"));

    }
}
