package com.jredic.network.protocol;

import com.jredic.TestUtils;
import com.jredic.network.protocol.data.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tester for {@link RESPEncoder}RESPEncoder.
 *
 * @author David.W
 */
public class RESPEncoderTest {

    private static final RESPEncoder ENCODER = new RESPEncoder();

    @Test
    public void testEncodeSimpleStringsData() throws Exception {
        String content = "OK";
        SimpleStringsData data = new SimpleStringsData(content);
        ByteBuf byteBuf = Unpooled.buffer();
        ENCODER.encode(null, data, byteBuf);
        String result = TestUtils.toString(byteBuf);
        Assert.assertEquals("+"+content+"\r\n", result);
    }

    @Test
    public void testEncodeErrorsData() throws Exception {
        String errorMsg = "System Error !!";
        ErrorsData data = new ErrorsData(errorMsg);
        ByteBuf byteBuf = Unpooled.buffer();
        ENCODER.encode(null, data, byteBuf);
        String result = TestUtils.toString(byteBuf);
        Assert.assertEquals("-"+errorMsg+"\r\n", result);
    }

    @Test
    public void testEncodeIntegersData() throws Exception {
        Long value = new Random().nextLong();
        IntegersData data = new IntegersData(value);
        ByteBuf byteBuf = Unpooled.buffer();
        ENCODER.encode(null, data, byteBuf);
        String result = TestUtils.toString(byteBuf);
        Assert.assertEquals(":"+value+"\r\n", result);
    }

    @Test
    public void testEncodeBulkStringsData() throws Exception {
        String content = "sdlfkjsdlkfjsldkfjsldkfjsdlkf";
        BulkStringsData data = new BulkStringsData(content);
        ByteBuf byteBuf = Unpooled.buffer();
        ChannelHandlerContext context = new MockChannelHandlerContext();
        ENCODER.encode(context, data, byteBuf);
        String result = TestUtils.toString(byteBuf);
        Assert.assertEquals("$"+content.getBytes("utf-8").length + "\r\n" +content+"\r\n", result);
    }

    @Test
    public void testEncodeNullBulkStringsData() throws Exception {
        BulkStringsData data = BulkStringsData.getNullBulkString();
        ByteBuf byteBuf = Unpooled.buffer();
        ChannelHandlerContext context = new MockChannelHandlerContext();
        ENCODER.encode(context, data, byteBuf);
        String result = TestUtils.toString(byteBuf);
        Assert.assertEquals("$-1\r\n", result);
    }

    @Test
    public void testEncodeEmptyBulkStringsData() throws Exception {
        BulkStringsData data = BulkStringsData.getEmptyBulkString();
        ByteBuf byteBuf = Unpooled.buffer();
        ChannelHandlerContext context = new MockChannelHandlerContext();
        ENCODER.encode(context, data, byteBuf);
        String result = TestUtils.toString(byteBuf);
        Assert.assertEquals("$0\r\n\r\n", result);
    }

    @Test
    public void testEncodeArraysData() throws Exception {
        List<Data> elements = new ArrayList<>();
        BulkStringsData foo = new BulkStringsData("foo");
        SimpleStringsData ok = new SimpleStringsData("ok");
        IntegersData num = new IntegersData(123);
        ErrorsData err = new ErrorsData("err");
        elements.add(foo);
        elements.add(ok);
        elements.add(num);
        elements.add(err);
        ArraysData data = new ArraysData(elements);
        ByteBuf byteBuf = Unpooled.buffer();
        ChannelHandlerContext context = new MockChannelHandlerContext();
        ENCODER.encode(context, data, byteBuf);
        String result = TestUtils.toString(byteBuf);
        Assert.assertEquals("*4\r\n$3\r\nfoo\r\n+ok\r\n:123\r\n-err\r\n", result);
    }

    @Test
    public void testEncodeNullArraysData() throws Exception {
        ArraysData data = ArraysData.getNullArray();
        ByteBuf byteBuf = Unpooled.buffer();
        ChannelHandlerContext context = new MockChannelHandlerContext();
        ENCODER.encode(context, data, byteBuf);
        String result = TestUtils.toString(byteBuf);
        Assert.assertEquals("*-1\r\n", result);
    }

    @Test
    public void testEncodeEmptyArraysData() throws Exception {
        ArraysData data = ArraysData.getEmptyArray();
        ByteBuf byteBuf = Unpooled.buffer();
        ChannelHandlerContext context = new MockChannelHandlerContext();
        ENCODER.encode(context, data, byteBuf);
        String result = TestUtils.toString(byteBuf);
        Assert.assertEquals("*0\r\n", result);
    }

}
