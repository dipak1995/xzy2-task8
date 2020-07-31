package com.ptt.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;

public class TestRun {
    
//    public static void main(String[] args){
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        ServerRmi rmi = (ServerRmi) context.getBean("clentrmi");
//        System.out.println(rmi.sayHi("rmi"));
//    }

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        BmbService bmbService = (BmbService) context.getBean("rmiProxyFactoryBean1");
        System.out.println(bmbService.getBmbById(5));
    }

    private static Logger logger = Logger.getLogger(TestRun.class);
    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:applicationContext.xml");


    public static BmbService getBmbService() {
    //public static void main(String[] args){
        BmbService bmbService = null;
        Random random = new Random();
        int i = random.nextInt(2);
        logger.info(i);
        if (i == 1) {
            try {
                logger.info("get data from server1");
                bmbService = (BmbService) applicationContext.getBean("rmiProxyFactoryBean1");
                logger.info(bmbService.getBmbById(5)+"1");
            } catch (Exception e) {
                logger.error("get a error,turn to server2");
                bmbService = (BmbService) applicationContext.getBean("rmiProxyFactoryBean2");
                logger.info(bmbService.getBmbById(5)+"2");
            }
        } else {
            try {
                logger.info("get data from server2");
                bmbService = (BmbService) applicationContext.getBean("rmiProxyFactoryBean2");
                logger.info(bmbService.getBmbById(5)+"3");
            } catch (Exception e) {
                logger.error("get a error,turn to server1");
                bmbService = (BmbService) applicationContext.getBean("rmiProxyFactoryBean1");
                logger.info(bmbService.getBmbById(5)+"4");
            }
        }
        return bmbService;
    }

}
