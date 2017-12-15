package com.jredic;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
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
        System.out.println(Arrays.toString(jredic.dump("jredic")));
        Assert.assertArrayEquals(new byte[]{0, 3, 70, 85, 78, 6, 0, 107, -21, 16, 120, 4, -47, -44, 83},
                jredic.dump("jredic"));
    }

    @Test
    public void testDumpForNotExistKey(){
        String key = "notExist";
        jredic.del(key);
        Assert.assertEquals(null, jredic.dump(key));
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

    @Test
    public void testExpireAt(){
        String key = "expireAtKey";
        String value = "dumy";
        jredic.set(key, value);
        Assert.assertEquals(value, jredic.get(key));
        jredic.expireAt(key, TestUtils.getUnixTime() + 1);
        try {
            TimeUnit.MILLISECONDS.sleep(1005);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(null, jredic.get(key));
    }

    @Test
    public void testPexpire(){
        String key = "pexpireKey";
        String value = "dumy";
        jredic.set(key, value);
        Assert.assertEquals(value, jredic.get(key));
        jredic.pexpire(key, 1000);
        try {
            TimeUnit.MILLISECONDS.sleep(1005);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(null, jredic.get(key));
    }

    @Test
    public void testPexpireAt(){
        String key = "pexpireAtKey";
        String value = "dumy";
        jredic.set(key, value);
        Assert.assertEquals(value, jredic.get(key));
        jredic.pexpireAt(key, System.currentTimeMillis() + 1000);
        try {
            TimeUnit.MILLISECONDS.sleep(1005);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(null, jredic.get(key));
    }

    @Test
    public void testKeys(){
        String key1 = "pk1";
        String key2 = "pk2";
        String key3 = "pk3";
        String value = "dumy";
        jredic.set(key1, value);
        jredic.set(key2, value);
        jredic.set(key3, value);
        List<String> keys = jredic.keys("pk*");
        Assert.assertTrue(keys != null && keys.size() >= 3);
        Assert.assertTrue(keys.contains(key1));
        Assert.assertTrue(keys.contains(key2));
        Assert.assertTrue(keys.contains(key3));
    }

    @Test
    public void testRestore(){
        String key = "jredic";
        String value = "这是一个中文字符串";
        String sKey = "jredic_fun";
        jredic.set(key, value);
        byte[] svalue = jredic.dump(key);
        jredic.del(sKey);
        jredic.restore(sKey, 0, svalue);
        Assert.assertEquals(value, jredic.get(sKey));
    }

}
