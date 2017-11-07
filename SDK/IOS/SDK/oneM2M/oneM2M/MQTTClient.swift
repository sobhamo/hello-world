//
//  MQTTClient.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 3. 29..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//
import Foundation
import CocoaMQTT

public class MQTTClient: CocoaMQTTDelegate {
    
    var mqtt: CocoaMQTT?
    var host: Host?
    var topic: SubscribeTopic?
    var publishTopic: String = ""
    
    public var delegate: MQTTDelegate?
    
    public init(host: Host) {
        self.host = host
        publishTopic = "/oneM2M/req/\(host.clientId!)/ThingPlug"
    }
    
    func getConnectedHost() -> Host? {
        if self.host?.status == MQTTConnectionStatus.CONNECTED  {
            return self.host
        } else {
            return nil
        }
    }
    
    public func connect() {
        _console("connect")
        disconnect()
        if let hosts = self.host {
            hosts.status = MQTTConnectionStatus.CONNECTING
            let clientId = hosts.clientId ?? ""
            let host = hosts.host
            let port = UInt16(hosts.port)
            let usingSsl = hosts.usingSSL
            let userName = hosts.userName
            let password = hosts.password
            let cleanSession = hosts.cleanSession
            self.mqtt = CocoaMQTT(clientID: clientId, host: host, port: port!)
            mqtt!.username = userName
            mqtt!.password = password
            mqtt!.keepAlive = 60
            mqtt!.delegate = self
            mqtt!.enableSSL = usingSsl
            mqtt!.cleanSession = cleanSession
            
            mqtt!.connect()
        }
    }
    
    public func disconnect() {
        mqtt?.disconnect()
    }
    
    public func subscribeTopic(topic: SubscribeTopic!) {
        _console("subscribe topic : \(topic.topicText)")
        self.topic = topic
        
        if let mqtt = mqtt {
            mqtt.subscribe(topic.topicText, qos: CocoaMQTTQOS.qos1)
        }
    }
    
    public func unsubscribeTopic(topic: SubscribeTopic!) {
        _console("unsubscribe topic : \(topic.topicText)")
        if let mqtt = mqtt {
            mqtt.unsubscribe(topic.topicText)
        }
    }
    
    public func publishMessage(message: String) -> UInt16 {
        var rc: UInt16 = 0;
        if let mqtt = self.mqtt {
            rc = mqtt.publish(publishTopic, withString: message, qos: .qos1)
        }
        return rc
    }
    
    public func publishMessage(topic: String, message: String) -> UInt16 {
        var rc: UInt16 = 0;
        if let mqtt = self.mqtt {
            rc = mqtt.publish(topic, withString: message, qos: .qos1)
        }
        return rc
    }
    
    public func mqtt(_ mqtt: CocoaMQTT, didConnect host: String, port: Int) {
        self.delegate?.mqtt(self, didConnect: host, port: port)
    
    }
    
    // Optional ssl CocoaMQTTDelegate
    public func mqtt(_ mqtt: CocoaMQTT, didReceive trust: SecTrust, completionHandler: @escaping (Bool) -> Void) {
        completionHandler(true)
    }
    
    public func mqtt(_ mqtt: CocoaMQTT, didConnectAck ack: CocoaMQTTConnAck) {
        _console("didConnectAck: \(ack)，rawValue: \(ack.rawValue)")
        if let connAck = MQTTConnAck(rawValue: ack.rawValue) {
            self.delegate?.mqtt(self, didConnectAck: connAck)
        }
    }
    
    public func mqtt(_ mqtt: CocoaMQTT, didPublishMessage message: CocoaMQTTMessage, id: UInt16) {
        _console("didPublishMessage with message: \(String(describing: message.string))")
        if let message = Message(topic: message.topic, subscribedMessage: message.string!) {
            self.delegate?.mqtt(self, didPublishMessage: message, id: id)
        }
    }
    
    public func mqtt(_ mqtt: CocoaMQTT, didPublishAck id: UInt16) {
        _console("didPublishAck with id: \(id)")
        self.delegate?.mqtt(self, didPublishAck: id)
    }
    
    public func mqtt(_ mqtt: CocoaMQTT, didReceiveMessage message: CocoaMQTTMessage, id: UInt16 ) {
        _console("didReceivedMessage: \(String(describing: message.string)) with id \(id)")
        if let message = Message(topic: message.topic, subscribedMessage: message.string!) {
            self.delegate?.mqtt(self, didReceiveMessage: message, id: id)
        }
    }
    
    public func mqtt(_ mqtt: CocoaMQTT, didSubscribeTopic topic: String) {
        _console("didSubscribeTopic to \(topic)")
        self.delegate?.mqtt(self, didSubscribeTopic: topic)
    }
    
    public func mqtt(_ mqtt: CocoaMQTT, didUnsubscribeTopic topic: String) {
        _console("didUnsubscribeTopic to \(topic)")
        self.delegate?.mqtt(self, didUnsubscribeTopic: topic)
    }
    
    public func mqttDidPing(_ mqtt: CocoaMQTT) {
        _console("didPing")
        self.delegate?.mqttDidPing(self)
    }
    
    public func mqttDidReceivePong(_ mqtt: CocoaMQTT) {
        _console("didReceivePong")
        self.delegate?.mqttDidReceivePong(self)
    }
    
    public func mqttDidDisconnect(_ mqtt: CocoaMQTT, withError err: Error?) {
        _console("mqttDidDisconnect")
        self.delegate?.mqttDidDisconnect(self, withError: err)
    }
}

/**
 * MQTT Delegate
 */
public protocol MQTTDelegate {
    /// MQTT connected with server
    func mqtt(_ mqtt: MQTTClient, didConnect host: String, port: Int)
    func mqtt(_ mqtt: MQTTClient, didConnectAck ack: MQTTConnAck)
    func mqtt(_ mqtt: MQTTClient, didPublishMessage message: Message, id: UInt16)
    func mqtt(_ mqtt: MQTTClient, didPublishAck id: UInt16)
    func mqtt(_ mqtt: MQTTClient, didReceiveMessage message: Message, id: UInt16)
    func mqtt(_ mqtt: MQTTClient, didSubscribeTopic topic: String)
    func mqtt(_ mqtt: MQTTClient, didUnsubscribeTopic topic: String)
    func mqttDidPing(_ mqtt: MQTTClient)
    func mqttDidReceivePong(_ mqtt: MQTTClient)
    func mqttDidDisconnect(_ mqtt: MQTTClient, withError err: Error?)
    func mqtt(_ mqtt: MQTTClient, didReceive trust: SecTrust, completionHandler: @escaping (Bool) -> Void)
}
