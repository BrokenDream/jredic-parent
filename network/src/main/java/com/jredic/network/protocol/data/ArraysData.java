package com.jredic.network.protocol.data;

import java.util.List;

/**
 * See <a href="https://redis.io/topics/protocol#resp-arrays">RESP#resp-arrays</a>.
 *
 * @author David.W
 */
public class ArraysData implements Data {

    private List<Data> elements;

    public ArraysData(List<Data> elements) {
        this.elements = elements;
    }

    private ArraysData(){
    }

    @Override
    public DataType getType() {
        return DataType.ARRAYS;
    }

    public List<Data> getElements() {
        return elements;
    }

    private static final ArraysData NULL_ARRAY = new ArraysData();

    public static ArraysData getNullArray(){
        return NULL_ARRAY;
    }

    public boolean isNullArray(){
        return this == NULL_ARRAY;
    }

    private static final ArraysData EMPTY_ARRAY = new ArraysData();

    public static ArraysData getEmptyArray(){
        return EMPTY_ARRAY;
    }

    public boolean isEmptyArray(){
        return this == EMPTY_ARRAY;
    }

    @Override
    public String toString() {
        return "ArraysData{" + elements + "}";
    }
}
