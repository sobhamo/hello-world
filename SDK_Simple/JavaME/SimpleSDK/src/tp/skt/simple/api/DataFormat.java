package tp.skt.simple.api;

/**
 * DataFormat.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public final class DataFormat {
    private int value;
    public final int ord;
    private static int upperBound = 0;
    
    private DataFormat(int value) {
        this.value = value;
        this.ord = upperBound++;
    }
    
    public int value() {return this.value; }
    public static int size() { return upperBound; }
    
    public static final DataFormat FORMAT_JSON = new DataFormat(DataValue.VALUE_JSON);
    public static final DataFormat FORMAT_CSV = new DataFormat(DataValue.VALUE_CSV);
    public static final DataFormat FORMAT_OFFSET = new DataFormat(DataValue.VALUE_OFFSET);
    
    public static final class DataValue{
        public static final int VALUE_JSON = 0;
        public static final int VALUE_CSV = 1;
        public static final int VALUE_OFFSET = 2;
    }
}