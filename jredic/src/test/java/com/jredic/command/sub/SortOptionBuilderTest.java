package com.jredic.command.sub;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author David.W
 */
public class SortOptionBuilderTest {

    private static SortOptionBuilder BUILDER;

    @Before
    public void init(){
        BUILDER = new SortOptionBuilder();
    }

    @Test
    public void testAsc(){
        BUILDER.asc();
        List<String> options = BUILDER.build();
        Assert.assertTrue(options.size() == 1 && options.get(0).equals("ASC"));
    }

    @Test
    public void testDesc(){
        BUILDER.desc();
        List<String> options = BUILDER.build();
        Assert.assertTrue(options.size() == 1 && options.get(0).equals("DESC"));
    }

    @Test
    public void testAlpha(){
        BUILDER.alpha();
        List<String> options = BUILDER.build();
        Assert.assertTrue(options.size() == 1 && options.get(0).equals("ALPHA"));
    }

    @Test
    public void testLimit(){
        BUILDER.limit(0, 10);
        List<String> options = BUILDER.build();
        Assert.assertTrue(options.size() == 3 && options.get(0).equals("LIMIT"));
    }

    @Test
    public void testBy(){
        BUILDER.by("id");
        List<String> options = BUILDER.build();
        Assert.assertTrue(options.size() == 2 && options.get(0).equals("BY") && options.get(1).equals("id"));
    }

    @Test
    public void testGet(){
        BUILDER.get("object");
        BUILDER.get("weight");
        List<String> options = BUILDER.build();
        Assert.assertTrue(options.size() == 4
                && options.get(0).equals("GET")
                && options.get(1).equals("object")
                && options.get(2).equals("GET")
                && options.get(3).equals("weight"));
    }

    @Test
    public void testAll(){
        BUILDER.by("name").limit(1,5).get("id").get("age").desc().alpha();
        List<String> options = BUILDER.build();
        Assert.assertTrue(options.size() == 11);
    }

}
