package com.jredic;

import com.jredic.network.client.support.DefaultClient;
import com.jredic.support.DefaultJredic;

import java.util.concurrent.TimeUnit;

/**
 * @author David.W
 */
public class DefaultJredicTest {

    public static void main(String[] args){
       final DefaultJredic jredic = new DefaultJredic();

        DefaultClient client = new DefaultClient("10.10.40.120", 6379);
        client.start();
        jredic.setClient(client);

//        String key = "s";
//
//        String value = "111111111111111111111111111111111111122222222222222222222222";
//
//        jredic.set(key, value);

//        long result = jredic.del("wuhong");
//
//        System.out.println(result);
//
//        jredic.set("wuhong123","860118");
//
//        String dumpValue = jredic.dump("wuhong123");
//
//        System.out.println(dumpValue);
//
//        System.out.println(jredic.get("wuhong123"));

//        System.out.println(jredic.exists("wuhong123"));
//        System.out.println(jredic.exists("wuhong123123"));

//        jredic.set("wuhong123","123");
//        jredic.set("wuhong1234","123");
//        jredic.set("wuhong1235","123");
//        jredic.set("wuhong1236","123");
//        jredic.set("wuhong1237","123");

//        System.out.println(jredic.move("wuhong123", 1));
//        System.out.println(jredic.move("wuhong789", 1));
//        System.out.println(jredic.move("wuhong1234", 100));
//        System.out.println(jredic.keys("wuhong*"));
//        System.out.println(jredic.randomKey());
        for(int i=0;i<1000;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String key = jredic.randomKey();
                    System.out.println("key=["+key+"],type=["+jredic.type(key)+"]");
                }
            }).start();

        }

//        System.out.println(jredic.pexpire("wuhong123",60000));
//        System.out.println(jredic.expire("wuhong123123",10));


        try {
            TimeUnit.MINUTES.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.stop();
    }

}
