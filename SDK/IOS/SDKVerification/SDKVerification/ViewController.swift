//
//  ViewController.swift
//  HelloThingPlug
//
//  Created by SK Telecom on 2017. 6. 27..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

import UIKit
import oneM2M

class ViewController: UIViewController, MQTTDelegate, XMLParserDelegate {
    
    var mqttClient: MQTTClient = MQTTClient(host: HOST)
    var binder: oneM2M!
    
    var step: VerificationStep = .VERIFICATION_CSEBASE_RETRIEVE
    
    var subscribeTopics: [SubscribeTopic] = []
    var publishTopic: String = ""
    
    @IBOutlet weak var resultTextView: UITextView!
    
    var nodeLink: String = ""
    var dKey: String = ""
    var nodeResourceName: String = ""
    var remoteCSEResourceName: String = ""
    var AEResourceName: String = ""
    var contentInstanceResourceName: String = ""
    var mgmtCmdResourceName: String = ""
    var containerResourceName: String = ""
    var locationPolicyResourceName: String = ""
    
    var printData: Bool = false
    var parser = XMLParser()
    var element: String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        binder = oneM2M(mqttClient: mqttClient)
        
        subscribeTopics = [SubscribeTopic("/oneM2M/req/+/\(HOST.clientId!)"), SubscribeTopic("/oneM2M/resp/\(HOST.clientId!)/+")]
        
        mqttClient.delegate = self
        mqttClient.connect()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func displayText(_ message: String) {
        resultTextView.insertText("\(message)\r\n")
    }
    
    func mqtt(_ mqtt: MQTTClient, didConnect host: String, port: Int) {
        print("didConnect")
    }
    
    func mqtt(_ mqtt: MQTTClient, didConnectAck ack: MQTTConnAck) {
        print("didConnectAck")
        displayText("connected!")
        // subscribe
        for (_, element) in subscribeTopics.enumerated() {
            mqtt.subscribeTopic(topic: element)
        }
    }
    
    func mqtt(_ mqtt: MQTTClient, didPublishMessage message: Message, id: UInt16) {
        print("didPublishMessage")
    }
    
    func mqtt(_ mqtt: MQTTClient, didPublishAck id: UInt16) {
        print("didPublishAck")
    }
    
    func mqtt(_ mqtt: MQTTClient, didSubscribeTopic topic: String) {
        print("didSubscribeTopic : \(topic):")
        var allSubscribed = true
        for (_, element) in subscribeTopics.enumerated() {
            if element.topicText == topic {
                element.isSubscribed = true
            }
        }
        
        for (_, element) in subscribeTopics.enumerated() {
            if element.isSubscribed == false {
                allSubscribed = false
            }
        }
        
        if allSubscribed {
            print("allSubscribed")
            displayText("subscribed!")
            doVerificationStep()
        }
    }
    
    func mqtt(_ mqtt: MQTTClient, didUnsubscribeTopic topic: String) {
        print("didUnsubscribeTopic : \(topic):")
    }
    
    func mqttDidPing(_ mqtt: MQTTClient) {
        print("mqttDidPing")
    }
    
    func mqttDidReceivePong(_ mqtt: MQTTClient) {
        print("mqttDidReceivePong")
    }
    
    func mqttDidDisconnect(_ mqtt: MQTTClient, withError err: Error?) {
        print("mqttDidDisconnect")
    }
    
    func mqtt(_ mqtt: MQTTClient, didReceive trust: SecTrust, completionHandler: @escaping (Bool) -> Void) {
        print("didReceive")
    }
    
    func mqtt(_ mqtt: MQTTClient, didReceiveMessage message: Message, id: UInt16 ) {
        print("didReceiveMessage")
        let xmlData = message.subscribedMessage.data(using: String.Encoding.utf8)
        parser = XMLParser(data: xmlData!)
        parser.delegate = self
        parser.parse()
        
        switch(step) {
        case .VERIFICATION_CSEBASE_RETRIEVE:
            displayText("CSEBase-Retrieved.")
            step = .VERIFICATION_NODE_CREATE
            break
        case .VERIFICATION_NODE_CREATE:
            displayText("node-Created.")
            step = .VERIFICATION_REMOTECSE_CREATE
            break
        case .VERIFICATION_REMOTECSE_CREATE:
            displayText("remoteCSE-Created.")
            
            step = .VERIFICATION_CONTAINER_CREATE
            break
        case .VERIFICATION_CONTAINER_CREATE:
            displayText("container-Created.")
            step = .VERIFICATION_MGMTCMD_CREATE
            break
        case .VERIFICATION_MGMTCMD_CREATE:
            displayText("mgmtCmd-Created.")
            step = .VERIFICATION_CONTENTINSTANCE_CREATE
            break
        case .VERIFICATION_CONTENTINSTANCE_CREATE:
            displayText("contentInstance-Created.")
            step = .VERIFICATION_LOCATIONPOLICY_CREATE
            break
        case .VERIFICATION_LOCATIONPOLICY_CREATE:
            displayText("locationPolicy-Created.")
            step = .VERIFICATION_AE_CREATE
            break
        case .VERIFICATION_AE_CREATE:
            displayText("AE-Created.")
            step = .VERIFICATION_AE_UPDATE
            break
        case .VERIFICATION_AE_UPDATE:
            displayText("AE-Updated.")
            step = .VERIFICATION_LOCATIONPOLICY_UPDATE
            break
        case .VERIFICATION_LOCATIONPOLICY_UPDATE:
            displayText("locationPolicy-Updated.")
            step = .VERIFICATION_MGMTCMD_UPDATE
            break
        case .VERIFICATION_MGMTCMD_UPDATE:
            displayText("mgmtCmd-Updated.")
            step = .VERIFICATION_CONTAINER_UPDATE
            break
        case .VERIFICATION_CONTAINER_UPDATE:
            displayText("container-Updated.")
            step = .VERIFICATION_REMOTECSE_UPDATE
            break
        case .VERIFICATION_REMOTECSE_UPDATE:
            displayText("remoteCSE-Updated.")
            step = .VERIFICATION_NODE_UPDATE
            break
        case .VERIFICATION_NODE_UPDATE:
            displayText("node-Updated.")
            step = .VERIFICATION_AE_DELETE
            break
        case .VERIFICATION_AE_DELETE:
            displayText("AE-Deleted.")
            step = .VERIFICATION_LOCATIONPOLICY_DELETE
            break
        case .VERIFICATION_LOCATIONPOLICY_DELETE:
            displayText("locationPolicy-Deleted.")
            step = .VERIFICATION_CONTENTINSTANCE_DELETE
            break
        case .VERIFICATION_CONTENTINSTANCE_DELETE:
            displayText("contentInstance-Deleted.")
            step = .VERIFICATION_MGMTCMD_DELETE
            break
        case .VERIFICATION_MGMTCMD_DELETE:
            displayText("mgmtCmd-Deleted.")
            step = .VERIFICATION_CONTAINER_DELETE
            break
        case .VERIFICATION_CONTAINER_DELETE:
            displayText("container-Deleted.")
            step = .VERIFICATION_REMOTECSE_DELETE
            break
        case .VERIFICATION_REMOTECSE_DELETE:
            displayText("remoteCSE-Deleted.")
            step = .VERIFICATION_NODE_DELETE
            break
        case .VERIFICATION_NODE_DELETE:
            displayText("node-Deleted.")
            step = .VERIFICATION_END
            break
        default:
            break
        }
        doVerificationStep();
    }
    
    func doVerificationStep() {
        
        var rt: ResourceType?
        var op: RequestType?
        var to: String = ""
        var pc: AnyObject?
        
        switch(step) {
        case .VERIFICATION_CSEBASE_RETRIEVE:
            rt = .CSEBase
            op = .RETRIEVE
            to = ONEM2M_TO
            let CSEBaseObj:CSEBase = CSEBase().ni(ONEM2M_NODEID)
            pc = CSEBaseObj
            break
            
        case .VERIFICATION_NODE_CREATE:
            rt = .node
            op = .CREATE
            to = ONEM2M_TO
            let nodeObj:node = node().ni(ONEM2M_NODEID).mga("MQTT|\(HOST.clientId!)")
            pc = nodeObj
            break
        case .VERIFICATION_REMOTECSE_CREATE:
            rt = .remoteCSE
            op = .CREATE
            to = ONEM2M_TO
            let remoteCSEObj:remoteCSE = remoteCSE().ni(ONEM2M_NODEID).nm(ONEM2M_NODEID).passCode(ONEM2M_PASSCODE).rr("true")
            pc = remoteCSEObj
            break
        case .VERIFICATION_CONTAINER_CREATE:
            rt = .container
            op = .CREATE
            to = "\(ONEM2M_TO)/remoteCSE-\(ONEM2M_NODEID)"
            let containerObj:container = container().ni(ONEM2M_NODEID).dKey(dKey).nm("\(ONEM2M_NODEID)_container_01").lbl("con")
            pc = containerObj
            break
        case .VERIFICATION_MGMTCMD_CREATE:
            rt = .mgmtCmd
            op = .CREATE
            to = ONEM2M_TO
            let mgmtCmdObj:mgmtCmd = mgmtCmd().ni(ONEM2M_NODEID).dKey(dKey).nm("\(ONEM2M_NODEID)_DevReset").cmt("sensor_1").ext(nodeLink)
            pc = mgmtCmdObj
            break
        case .VERIFICATION_CONTENTINSTANCE_CREATE:
            rt = .contentInstance
            op = .CREATE
            to = "\(ONEM2M_TO)/remoteCSE-\(ONEM2M_NODEID)/container-\(containerResourceName)"
            let contentInstanceObj:contentInstance = contentInstance().ni(ONEM2M_NODEID).dKey(dKey).cnf("text").con("45")
            pc = contentInstanceObj
//            binder.tpAddData("111")
//            binder.tpAddData("222")
//            binder.tpAddData("333")
//            binder.tpReport(ONEM2M_NODEID, to, ONEM2M_RI, dKey, "text", nil, true)
//            return
            break
        case .VERIFICATION_LOCATIONPOLICY_CREATE:
            rt = .locationPolicy
            op = .CREATE
            to = ONEM2M_TO
            let locationPolicyObj:locationPolicy = locationPolicy().ni(ONEM2M_NODEID).dKey(dKey).nm("\(ONEM2M_NODEID)_locationPolicy_01").los("2")
            pc = locationPolicyObj
            break
        case .VERIFICATION_AE_CREATE:
            rt = .AE
            op = .CREATE
            to = "\(ONEM2M_TO)/remoteCSE-\(ONEM2M_NODEID)"
            let AEObj:AE = AE().ni(ONEM2M_NODEID).dKey(dKey).api("1.2.481.1.00010021234").apn("\(ONEM2M_NODEID)_AE_01")
            pc = AEObj
            break
            
        case .VERIFICATION_AE_UPDATE:
            rt = .AE
            op = .UPDATE
            to = "\(ONEM2M_TO)/remoteCSE-\(remoteCSEResourceName)/AE-\(AEResourceName)"
            let AEObj:AE = AE().ni(ONEM2M_NODEID).dKey(dKey).apn("\(ONEM2M_NODEID)_AE_02")
            pc = AEObj
            break
        case .VERIFICATION_LOCATIONPOLICY_UPDATE:
            rt = .locationPolicy
            op = .UPDATE
            to = "\(ONEM2M_TO)/locationPolicy-\(locationPolicyResourceName)"
            let locationPolicyObj:locationPolicy = locationPolicy().ni(ONEM2M_NODEID).dKey(dKey).los("3")
            pc = locationPolicyObj
            break
        case .VERIFICATION_MGMTCMD_UPDATE:
            rt = .mgmtCmd
            op = .UPDATE
            to = "\(ONEM2M_TO)/mgmtCmd-\(mgmtCmdResourceName)"
            let mgmtCmdObj:mgmtCmd = mgmtCmd().ni(ONEM2M_NODEID).dKey(dKey).lbl("mgmtCmd-update")
            pc = mgmtCmdObj
            break
        case .VERIFICATION_CONTAINER_UPDATE:
            rt = .container
            op = .UPDATE
            to = "\(ONEM2M_TO)/remoteCSE-\(remoteCSEResourceName)/container-\(containerResourceName)"
            let containerObj:container = container().ni(ONEM2M_NODEID).dKey(dKey).lbl("event")
            pc = containerObj
            break
        case .VERIFICATION_REMOTECSE_UPDATE:
            rt = .remoteCSE
            op = .UPDATE
            to = "\(ONEM2M_TO)/remoteCSE-\(remoteCSEResourceName)"
            let remoteCSEObj:remoteCSE = remoteCSE().ni(ONEM2M_NODEID).dKey(dKey).rr("false")
            pc = remoteCSEObj
            break
        case .VERIFICATION_NODE_UPDATE:
            rt = .node
            op = .UPDATE
            to = "\(ONEM2M_TO)/node-\(nodeResourceName)"
            let nodeObj:node = node().ni(ONEM2M_NODEID).dKey(dKey).mga("http|\(HOST.clientId!)")
            pc = nodeObj
            break
            
        case .VERIFICATION_AE_DELETE:
            rt = .AE
            op = .DELETE
            to = "\(ONEM2M_TO)/remoteCSE-\(remoteCSEResourceName)/AE-\(AEResourceName)"
            let AEObj:AE = AE().ni(ONEM2M_NODEID).dKey(dKey)
            pc = AEObj
            break
        case .VERIFICATION_LOCATIONPOLICY_DELETE:
            rt = .locationPolicy
            op = .DELETE
            to = "\(ONEM2M_TO)/locationPolicy-\(locationPolicyResourceName)"
            let locationPolicyObj:locationPolicy = locationPolicy().ni(ONEM2M_NODEID).dKey(dKey)
            pc = locationPolicyObj
            break
        case .VERIFICATION_MGMTCMD_DELETE:
            rt = .mgmtCmd
            op = .DELETE
            to = "\(ONEM2M_TO)/mgmtCmd-\(mgmtCmdResourceName)"
            let mgmtCmdObj:mgmtCmd = mgmtCmd().ni(ONEM2M_NODEID).dKey(dKey)
            pc = mgmtCmdObj
            break
        case .VERIFICATION_CONTENTINSTANCE_DELETE:
            rt = .contentInstance
            op = .DELETE
            to = "\(ONEM2M_TO)/remoteCSE-\(remoteCSEResourceName)/container-\(containerResourceName)/contentInstance-\(contentInstanceResourceName)"
            let contentInstanceObj:contentInstance = contentInstance().ni(ONEM2M_NODEID).dKey(dKey)
            pc = contentInstanceObj
            break
        case .VERIFICATION_CONTAINER_DELETE:
            rt = .container
            op = .DELETE
            to = "\(ONEM2M_TO)/remoteCSE-\(remoteCSEResourceName)/container-\(containerResourceName)"
            let containerObj:container = container().ni(ONEM2M_NODEID).dKey(dKey)
            pc = containerObj
            break
        case .VERIFICATION_REMOTECSE_DELETE:
            rt = .remoteCSE
            op = .DELETE
            to = "\(ONEM2M_TO)/remoteCSE-\(remoteCSEResourceName)"
            let remoteCSEObj:remoteCSE = remoteCSE().ni(ONEM2M_NODEID).dKey(dKey)
            pc = remoteCSEObj
            break
        case .VERIFICATION_NODE_DELETE:
            rt = .node
            op = .DELETE
            to = "\(ONEM2M_TO)/node-\(nodeResourceName)"
            let nodeObj:node = node().ni(ONEM2M_NODEID).dKey(dKey)
            pc = nodeObj
            break
        default:
            step = .VERIFICATION_END
            mqttClient.disconnect()
            break
        }
        if step != .VERIFICATION_END {
            let messageId = binder.tp_oneM2M_V1_Request(rt!, op!, to, ONEM2M_RI, pc!)
            print("publish message id : \(messageId)")
        }
    }
    
    func simpleXMLParser(_ text: String,_ name: String,_ value: inout String,_ isPC: Bool) {
    }
    
    func parser(_ parser: XMLParser, didStartElement elementName: String, namespaceURI: String?, qualifiedName qName: String?, attributes attributeDict: [String : String]) {
        self.element = elementName
        if elementName == "rsc" || elementName == "ri" || elementName == "RSM" {
            printData = true
        }
    }
    
    func parser(_ parser: XMLParser, didEndElement elementName: String, namespaceURI: String?, qualifiedName qName: String?) {
        printData = false
    }
    
    func parser(_ parser: XMLParser, foundCharacters string: String) {
        if printData {
            print("[\(element) : \(string)]")
        }
        
        switch(step) {
        case .VERIFICATION_NODE_CREATE:
            if element == "rn" {
                nodeResourceName = string
                print("[\(element) : \(string)]")
            }
            if element == "ri" && string.hasPrefix("ND") {
                nodeLink = string
                print("[nl : \(string)]")
            }
            break;
        case .VERIFICATION_REMOTECSE_CREATE:
            if element == "dKey" {
                dKey = string
                print("[\(element) : \(string)]")
            }
            if element == "rn" {
                remoteCSEResourceName = string
                print("[\(element) : \(string)]")
            }
            break
        case .VERIFICATION_AE_CREATE:
            if element == "rn" {
                AEResourceName = string
                print("[\(element) : \(string)]")
            }
            break
        case .VERIFICATION_CONTAINER_CREATE:
            if element == "rn" {
                containerResourceName = string
                print("[\(element) : \(string)]")
            }
            break
        case .VERIFICATION_MGMTCMD_CREATE:
            if element == "rn" {
                mgmtCmdResourceName = string
                print("[\(element) : \(string)]")
            }
            break
        case .VERIFICATION_CONTENTINSTANCE_CREATE:
            if element == "rn" {
                contentInstanceResourceName = string
                print("[\(element) : \(string)]")
            }
            break
        case .VERIFICATION_LOCATIONPOLICY_CREATE:
            if element == "rn" {
                locationPolicyResourceName = string
                print("[\(element) : \(string)]")
            }
            break
        default:
            break
        }
    }
    
    func parser(_ parser: XMLParser, parseErrorOccurred parseError: Error) {
        print("failure error: ", parseError)
    }
}
