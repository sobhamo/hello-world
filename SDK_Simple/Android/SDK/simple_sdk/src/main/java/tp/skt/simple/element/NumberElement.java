package tp.skt.simple.element;

/**
 * NumberElement.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class NumberElement {
    public String name;
    public Number value;

    /**
     *
     *
     */
    public NumberElement() {
    }

    /**
     * @param name
     * @param value
     */
    public NumberElement(String name, Number value) {
        this.name = name;
        this.value = value;
    }
}
