package tp.skt.simple.element;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import tp.skt.simple.common.Define;

/**
 * Subscribe.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class Subscribe {
    /**
     * command
     **/
    private StringElement cmd;
    /**
     * command ID
     **/
    private NumberElement cmdId;
    /**
     * sensor node id
     **/
    private StringElement sensorNodeId;
    /**
     * service name
     **/
    private StringElement serviceName;
    /**
     * device name
     **/
    private StringElement deviceName;
    /**
     * is target all
     **/
    private BooleanElement isTargetAll;
    /**
     * attribute array
     **/
    private List<String> attribute = new ArrayList<>();
    /**
     * telemetry array
     **/
    private List<String> telemetry = new ArrayList<>();

    /**
     *
     *
     */
    public Subscribe() {
    }

    /**
     * @param cmd
     * @param sensorNodeId
     * @param isTargetAll
     * @param attribute
     * @param telemetry
     * @param cmdId
     */
    public Subscribe(String cmd, String serviceName, String deviceName, String sensorNodeId, boolean isTargetAll, List<String> attribute, List<String> telemetry, int cmdId) {
        this.setCmd(cmd);
        this.setServiceName(serviceName);
        this.setDeviceName(deviceName);
        this.setSensorNodeId(sensorNodeId);
        this.setIsTargetAll(isTargetAll);
        this.setAttribute(attribute);
        this.setTelemetry(telemetry);
        this.setCmdId(cmdId);
    }

    public StringElement getCmd() {
        return cmd;
    }

    public StringElement getServiceName() {
        return serviceName;
    }

    public StringElement getDeviceName() {
        return deviceName;
    }

    public StringElement getSensorNodeId() {
        return sensorNodeId;
    }

    public BooleanElement getIsTargetAll() {
        return isTargetAll;
    }

    public List<String> getAttribute() {
        return attribute;
    }

    public List<String> getTelemetry() {
        return telemetry;
    }

    public NumberElement getCmdId() {
        return cmdId;
    }

    /**
     * @param cmd
     */
    public void setCmd(String cmd) {
        this.cmd = new StringElement(Define.CMD, cmd);
    }

    /**
     * @param serviceName
     */
    public void setServiceName(String serviceName) {
        if (TextUtils.isEmpty(serviceName) == false) {
            this.serviceName = new StringElement(Define.SERVICE_NAME, serviceName);
        }
    }

    /**
     * @param deviceName
     */
    public void setDeviceName(String deviceName) {
        if (TextUtils.isEmpty(deviceName) == false) {
            this.deviceName = new StringElement(Define.DEVICE_NAME, deviceName);
        }
    }

    /**
     * @param sensorNodeId
     */
    public void setSensorNodeId(String sensorNodeId) {
        if (TextUtils.isEmpty(sensorNodeId) == false) {
            this.sensorNodeId = new StringElement(Define.SENSOR_NODE_ID, sensorNodeId);
        }
    }

    /**
     * @param isTargetAll
     */
    public void setIsTargetAll(boolean isTargetAll) {
        this.isTargetAll = new BooleanElement(Define.IS_TARGET_ALL, isTargetAll);
    }

    /**
     * @param attribute
     */
    public void setAttribute(List<String> attribute) {
        if (attribute != null) {
            this.attribute = attribute;
        }
    }

    /**
     * add attribute
     *
     * @param attribute
     */
    public void addAttribute(String attribute) {
        if (TextUtils.isEmpty(attribute) == false) {
            this.attribute.add(attribute);
        }
    }

    /**
     * @param telemetry
     */
    public void setTelemetry(List<String> telemetry) {
        if (telemetry != null) {
            this.telemetry = telemetry;
        }
    }

    /**
     * add telemetry
     *
     * @param telemetry
     */
    public void addTelemetry(String telemetry) {
        if (TextUtils.isEmpty(telemetry) == false) {
            this.telemetry.add(telemetry);
        }
    }

    /**
     * @param cmdId
     */
    public void setCmdId(int cmdId) {
        this.cmdId = new NumberElement(Define.CMD_ID, cmdId);
    }
}
