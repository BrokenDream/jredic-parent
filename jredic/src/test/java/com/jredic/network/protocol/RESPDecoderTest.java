package com.jredic.network.protocol;

import com.jredic.network.protocol.data.*;
import com.jredic.network.protocol.RESPDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tester for {@link RESPDecoder}RESPDecoder.
 *
 * @author David.W
 */
public class RESPDecoderTest {

    private static final RESPDecoder DECODER = new RESPDecoder();

    @Test
    public void testDecodeSimpleStringsData() throws Exception {
        String msg = "OK";
        ByteBuf in = Unpooled.buffer();
        ByteBufUtil.writeUtf8(in, "+"+msg+"\r\n");
        List<Object> out = new ArrayList<>();
        DECODER.decode(null, in, out);
        Assert.assertTrue(out.size() ==  1);
        SimpleStringsData data = (SimpleStringsData) out.get(0);
        Assert.assertEquals(msg, data.getContent());
    }

    @Test
    public void testDecodeErrorsData() throws Exception {
        String errorMsg = "WRONGTYPE Operation against a key holding the wrong kind of value";
        ByteBuf in = Unpooled.buffer();
        ByteBufUtil.writeUtf8(in, "-"+errorMsg+"\r\n");
        List<Object> out = new ArrayList<>();
        DECODER.decode(null, in, out);
        Assert.assertTrue(out.size() ==  1);
        ErrorsData data = (ErrorsData) out.get(0);
        Assert.assertEquals(errorMsg, data.getErrorMsg());
    }

    @Test
    public void testIntegersData() throws Exception {
        long value = Long.MIN_VALUE;
        ByteBuf in = Unpooled.buffer();
        ByteBufUtil.writeUtf8(in, ":"+value+"\r\n");
        List<Object> out = new ArrayList<>();
        DECODER.decode(null, in, out);
        Assert.assertTrue(out.size() ==  1);
        IntegersData data = (IntegersData) out.get(0);
        Assert.assertEquals(value, data.getValue());
    }

    @Test
    public void testDecodeBulkStringsData() throws Exception {
        String content = "sdlfkjsdlkfjsldkfjsldkfjsdlkf";
        ByteBuf in = Unpooled.buffer();
        ByteBufUtil.writeUtf8(in, "$"+content.getBytes("utf-8").length + "\r\n" +content+"\r\n");
        List<Object> out = new ArrayList<>();
        DECODER.decode(null, in, out);
        Assert.assertTrue(out.size() ==  1);
        BulkStringsData data = (BulkStringsData) out.get(0);
        Assert.assertEquals(content, data.getContent());
    }

    @Test
    public void testDecodeNullBulkStringsData() throws Exception {
        ByteBuf in = Unpooled.buffer();
        ByteBufUtil.writeUtf8(in, "$-1\r\n");
        List<Object> out = new ArrayList<>();
        DECODER.decode(null, in, out);
        Assert.assertTrue(out.size() ==  1);
        BulkStringsData data = (BulkStringsData) out.get(0);
        Assert.assertTrue(data == BulkStringsData.getNullBulkString());
    }

    @Test
    public void testDecodeEmptyBulkStringsData() throws Exception {
        ByteBuf in = Unpooled.buffer();
        ByteBufUtil.writeUtf8(in, "$0\r\n\r\n");
        List<Object> out = new ArrayList<>();
        DECODER.decode(null, in, out);
        Assert.assertTrue(out.size() ==  1);
        BulkStringsData data = (BulkStringsData) out.get(0);
        Assert.assertTrue(data == BulkStringsData.getEmptyBulkString());
    }

    @Test
    public void testDecodeArraysData() throws Exception {
        ByteBuf in = Unpooled.buffer();
        ByteBufUtil.writeUtf8(in, "*4\r\n$3\r\nfoo\r\n+ok\r\n:123\r\n-err\r\n");
        List<Object> out = new ArrayList<>();
        DECODER.decode(null, in, out);
        Assert.assertTrue(out.size() ==  1);
        ArraysData data = (ArraysData) out.get(0);
        System.out.println(data);
    }

    @Test
    public void testDecodeNullArraysData() throws Exception {
        ByteBuf in = Unpooled.buffer();
        ByteBufUtil.writeUtf8(in, "*-1\r\n");
        List<Object> out = new ArrayList<>();
        DECODER.decode(null, in, out);
        Assert.assertTrue(out.size() ==  1);
        ArraysData data = (ArraysData) out.get(0);
        Assert.assertTrue(ArraysData.getNullArray() == data);
    }

    @Test
    public void testDecodeEmptyArraysData() throws Exception {
        ByteBuf in = Unpooled.buffer();
        ByteBufUtil.writeUtf8(in, "*0\r\n");
        List<Object> out = new ArrayList<>();
        DECODER.decode(null, in, out);
        Assert.assertTrue(out.size() ==  1);
        ArraysData data = (ArraysData) out.get(0);
        Assert.assertTrue(ArraysData.getEmptyArray() == data);
    }

}
