package com.jredic.network.protocol;

import com.jredic.network.protocol.data.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * Encodes {@link Data} into bytes following <a href="http://redis.io/topics/protocol">RESP</a>.
 *
 * @author David.W
 */
public class RESPEncoder extends MessageToByteEncoder<Data> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Data data, ByteBuf out) throws Exception {
        encodeData(ctx, data, out);
    }

    private void encodeData(ChannelHandlerContext ctx, Data data, ByteBuf out) {
        DataType type = data.getType();
        //write data type
        out.writeByte(data.getType().value());
        switch (type){
            case SIMPLE_STRINGS:
                encodeSimpleStrings((SimpleStringsData)data, out);
                break;
            case ERRORS:
                encodeErrors((ErrorsData)data, out);
                break;
            case INTEGERS:
                encodeIntegers((IntegersData)data, out);
                break;
            case BULK_STRINGS:
                encodeBulkStrings(ctx, (BulkStringsData)data, out);
                break;
            case ARRAYS:
                encodeArrays(ctx, (ArraysData)data, out);
                break;
        }
    }

    private void encodeSimpleStrings(SimpleStringsData data, ByteBuf out) {
        //write content
        ByteBufUtil.writeUtf8(out, data.getContent());
        //write CRLF
        out.writeShort(RESPConstants.CRLF);
    }

    private void encodeErrors(ErrorsData data, ByteBuf out) {
        //write error message
        ByteBufUtil.writeUtf8(out, data.getErrorMsg());
        //write CRLF
        out.writeShort(RESPConstants.CRLF);
    }

    private void encodeIntegers(IntegersData data, ByteBuf out) {
        //write value
        out.writeBytes(CodecUtils.longToAsciiBytes(data.getValue()));
        //write CRLF
        out.writeShort(RESPConstants.CRLF);
    }

    private void encodeBulkStrings(ChannelHandlerContext ctx, BulkStringsData data, ByteBuf out) {
        if(data.isNullBulkString()){
            //null bulk string
            //write data length
            out.writeShort(RESPConstants.NULL_DATA_LENGTH);
            //write CRLF
            out.writeShort(RESPConstants.CRLF);
        }else if(data.isEmptyBulkString()) {
            //empty bulk string
            //write data length
            out.writeByte(RESPConstants.EMPTY_DATA_LENGTH);
            //write CRLF
            out.writeShort(RESPConstants.CRLF);
            //write CRLF
            out.writeShort(RESPConstants.CRLF);
        }else{
            String content = data.getContent();
            //write data length
            ByteBufAllocator allocator = ctx.alloc();
            int maxLength = ByteBufUtil.utf8MaxBytes(content);
            ByteBuf buffer = allocator.ioBuffer(maxLength);
            int dataLength = ByteBufUtil.writeUtf8(buffer, content);
            out.writeBytes(CodecUtils.intToAsciiBytes(dataLength));
            //write CRLF
            out.writeShort(RESPConstants.CRLF);
            out.writeBytes(buffer);
            //write CRLF
            out.writeShort(RESPConstants.CRLF);
        }
    }

    private void encodeArrays(ChannelHandlerContext ctx, ArraysData data, ByteBuf out) {
        if(data.isNullArray()){
            //null array
            //write data length
            out.writeShort(RESPConstants.NULL_DATA_LENGTH);
            //write CRLF
            out.writeShort(RESPConstants.CRLF);
        }else if(data.isEmptyArray()){
            //empty array
            //write data length
            out.writeByte(RESPConstants.EMPTY_DATA_LENGTH);
            //write CRLF
            out.writeShort(RESPConstants.CRLF);
        }else{
            List<Data> elements = data.getElements();
            //write data length
            out.writeBytes(CodecUtils.longToAsciiBytes(elements.size()));
            //write CRLF
            out.writeShort(RESPConstants.CRLF);
            //write elements
            for(Data element : elements){
                encodeData(ctx, element, out);
            }
        }
    }










}
