package tp.skt.simple.element;

import java.util.Vector;


/**
 * ArrayElement.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class ArrayElement {

    public Vector elements;

    /**
     *
     *
     */
    public ArrayElement() {
        elements = new Vector();
    }

    /**
     *
     *
     * @param elements
     */
    public ArrayElement(Vector elements) {
        this.elements = elements;
    }

    /**
     * add StringElement, NumberElement, BooleanElement
     *
     * @param element
     */
    public void addElement(Object element) {
        elements.addElement(element);
    }

    /**
     * add StringElement
     *
     * @param name
     * @param value
     */
    public void addStringElement(String name, String value) {
        StringElement stringElement = new StringElement(name, value);
        addElement(stringElement);
    }


    
     /**
     * add IntElement
     *
     * @param name
     * @param value
     */
    public void addNumberElement(String name, int value) {
        IntElement numberElement = new IntElement(name, value);
        addElement(numberElement);
    }
    
     /**
     * add DoubleElement
     *
     * @param name
     * @param value
     */
    public void addNumberElement(String name, double value) {
        DoubleElement numberElement = new DoubleElement(name, value);
        addElement(numberElement);
    }
    
    /**
     * add LongElement
     *
     * @param name
     * @param value
     */
    public void addNumberElement(String name, long value) {
        LongElement numberElement = new LongElement(name, value);
        addElement(numberElement);
    }
    
    /**
     * add FloatElement
     *
     * @param name
     * @param value
     */
    public void addNumberElement(String name, float value) {
        FloatElement numberElement = new FloatElement(name, value);
        addElement(numberElement);
    }
    
    

    /**
     * add BooleanElement
     *
     * @param name
     * @param value
     */
    public void addBooleanElement(String name, boolean value) {
        BooleanElement booleanElement = new BooleanElement(name, value);
        addElement(booleanElement);
    }
}