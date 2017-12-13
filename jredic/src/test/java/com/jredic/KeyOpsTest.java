package com.jredic;

import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.TimeUnit;

/**
 * Test for Key Ops.
 *
 * @author David.W
 */
public class KeyOpsTest extends OpsTestBase {

    @Test
    public void testDelMulti(){
        jredic.set("k1", "1");
        jredic.set("k2", "2");
        jredic.set("k3", "3");
        long count = jredic.del("k1","k2","k3");

        count = jredic.del("k1","k2","k3");
        Assert.assertEquals(0, count);
    }

    @Test
    public void testDel(){
        jredic.set("sk1", "11");
        Assert.assertEquals(true, jredic.del("sk1"));
        Assert.assertEquals(false, jredic.del("sk1"));
    }

    @Test
    public void testDump(){
        jredic.set("jredic", "FUN");
        Assert.assertArrayEquals(new byte[]{0, 3, 70, 85, 78, 6, 0, 107, -17, -65, -67, 16, 120, 4, -17, -65, -67, -17, -65, -67, 83},
                jredic.dump("jredic"));
    }

    @Test
    public void testExists(){
        String key = "funny";
        String value = "hia";
        jredic.set(key, value);
        Assert.assertEquals(true, jredic.exists(key));
        jredic.del(key);
        Assert.assertEquals(false, jredic.exists(key));
    }

    @Test
    public void testExpire(){
        String key = "expireKey";
        String value = "dumy";
        jredic.set(key, value);
        Assert.assertEquals(value, jredic.get(key));
        jredic.expire(key, 1);
        try {
            TimeUnit.MILLISECONDS.sleep(1005);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(null, jredic.get(key));
    }

}
