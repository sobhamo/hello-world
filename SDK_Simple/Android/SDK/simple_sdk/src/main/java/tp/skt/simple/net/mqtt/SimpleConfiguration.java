package tp.skt.simple.net.mqtt;

/**
 * SimpleConfiguration.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class SimpleConfiguration {

    /**
     * mqtt server address
     **/
    private String mqttServerAddress;
    /**
     * client ID
     **/
    private String clientID;
    /**
     * login name
     **/
    private String loginName;
    /**
     * login password
     **/
    private String loginPassword;

    /**
     * enable secure ( default true )
     */
    private boolean enableSecure = true;

    /**
     *
     *
     */
    public SimpleConfiguration() {
    }

    /**
     * SimpleConfiguration constructor
     *
     * @param mqttServerAddress
     * @param clientID
     * @param loginName
     * @param loginPassword
     */
    public SimpleConfiguration(String mqttServerAddress, String clientID, String loginName, String loginPassword) {
        this.mqttServerAddress = mqttServerAddress;
        this.clientID = clientID;
        this.loginName = loginName;
        this.loginPassword = loginPassword;
    }

    /**
     * @return
     */
    public String getMqttServerAddress() {
        return mqttServerAddress;
    }

    /**
     * @param mqttServerAddress
     */
    public void setMqttServerAddress(String mqttServerAddress) {
        this.mqttServerAddress = mqttServerAddress;
    }

    /**
     * @return
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * @param clientID
     */
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    /**
     * @return
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * @param loginPassword
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public boolean isEnableSecure() {
        return enableSecure;
    }

    public void setEnableSecure(boolean enableSecure) {
        this.enableSecure = enableSecure;
    }
}
