package com.jredic;

import com.jredic.network.client.support.DefaultClient;
import com.jredic.support.DefaultJredic;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author David.W
 */
public class OpsTestBase {

    protected static DefaultJredic jredic = new DefaultJredic();

    @BeforeClass
    public static void init(){
        jredic.setClient(new DefaultClient("127.0.0.1", 6379));
        jredic.init();
    }

    @AfterClass
    public static void destroy(){
        jredic.destroy();
    }

}
