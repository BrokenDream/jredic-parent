package com.jredic;

import com.jredic.network.client.support.DefaultClient;
import com.jredic.support.DefaultJredic;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author David.W
 */
public class MultiThreadTest {

    public static void main(String[] args){
        final DefaultJredic jredic = new DefaultJredic();
        jredic.setClient(new DefaultClient("127.0.0.1", 6379, false));
        jredic.init();
        final CountDownLatch latch = new CountDownLatch(500);
        for(int i=0;i<500;i++){
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                        String key = "key" + index;
                        String value = "value" + index;
                        jredic.set(key, value);
                        System.out.println("key=" + key + ", value=" + jredic.get(key));
                        latch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jredic.destroy();
    }

}
