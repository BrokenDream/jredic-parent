package com.jredic;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for Get And Set Ops.
 *
 * @author David.W
 */
public class GetAndSetOpsTest extends OpsTestBase {

    @Test
    public void testSetAndGet(){
        String key = "jredic";
        String value = "fun";
        jredic.set(key, value);
        Assert.assertEquals(value, jredic.get(key));
    }

}
