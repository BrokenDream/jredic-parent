package com.jredic;

import com.jredic.network.client.support.DefaultClient;
import com.jredic.support.DefaultJredic;

/**
 * @author David.W
 */
public class KeyOpsTest {

    public static void main(String[] args){
        DefaultJredic jredic = new DefaultJredic();
        DefaultClient client = new DefaultClient("10.10.40.120", 6379, true);
        client.start();
        jredic.setClient(client);
        System.out.println(jredic.del("wuhong1234"));
        client.stop();
    }

}
