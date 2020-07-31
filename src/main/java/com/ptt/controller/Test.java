package com.ptt.controller;

import com.whalin.MemCached.MemCachedClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
public class Test {

    @Resource
    MemCachedClient memCachedClient;

    Logger logger = Logger.getLogger(Test.class);

    @RequestMapping("/n")
    @ResponseBody
    public String MapBmb() {

        String a = "ss=1";

        String t = (String) memCachedClient.get("test1");
        logger.info("===="+t);
        try {
            if(t != null){
                logger.info("memcached执行了");
                int k = 1/0;
            }else {
                memCachedClient.add("test1",a);
                System.out.println(a);
                int i = 1/0;
            }
        }catch (Exception e){
            logger.error("打印堆栈信息",e);
            System.out.println(e);
        }

        return "vvvvv";
    }

}
