package com.jredic;

import com.jredic.network.client.support.DefaultClient;
import com.jredic.support.DefaultJredic;
import java.util.concurrent.TimeUnit;

/**
 * @author David.W
 */
public class ReconnecTest {

    public static void main(String[] args){
        DefaultJredic jredic = new DefaultJredic();
        jredic.setClient(new DefaultClient("127.0.0.1", 6379, 2));
        jredic.init();
        for(int i=0;i<500;i++){
            try {
                //shutdown and restart the server and see the console
                TimeUnit.SECONDS.sleep(2);
                System.out.println("randomKey = " + jredic.randomKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        jredic.destroy();
    }

}
