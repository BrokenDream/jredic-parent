package com.jredic.example.single;

import com.jredic.network.client.Client;
import com.jredic.network.client.support.DefaultClient;
import com.jredic.support.DefaultJredic;

/**
 * @author David.W
 */
public class Example {

    public static void main(String[] args){
        DefaultJredic jredic = new DefaultJredic();
        Client client = new DefaultClient("127.0.0.1", 6379);
        jredic.setClient(client);
        jredic.init();
        jredic.set("foo","bar");
        System.out.println(jredic.get("foo"));
        jredic.destroy();
    }

}
