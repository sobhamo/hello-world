//
//  Configuration.swift
//  SDKVerification
//
//  Created by SK Telecom on 2017. 7. 1..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

import Foundation
import oneM2M

let ONEM2M_CSEBASE = "ThingPlug"
let ONEM2M_TO = "/ThingPlug/v1_0"
let ONEM2M_RI = "12345"

let ONEM2M_NODEID = "1.2.481.1.999.01023345678"
let ONEM2M_PASSCODE = "123456"

let TEST_HOST = Host(name: "eclipse", host: "iot.eclipse.org",
                     port: "8883", clientId: "", userName: "", password: "", usingSSL: true, cleanSession: true)

let HOST = Host(name: "thingplugtest", host: "mqtt.sktiot.com",
                port: "1883", clientId: "demoday_0099", userName: "demoday", password: "eThFNmhvZkJFdzBNSmZpRFJabjZLVXV6NjhreHNEWExMZXlxMmxMT0o4NFVoeVFUWDY2R0M1bFpLd0s4WXRKZw==", usingSSL: false, cleanSession: true)


enum VerificationStep: UInt8 {
    case VERIFICATION_CSEBASE_RETRIEVE = 0
    case VERIFICATION_NODE_CREATE
    case VERIFICATION_REMOTECSE_CREATE
    case VERIFICATION_CONTAINER_CREATE
    case VERIFICATION_MGMTCMD_CREATE
    case VERIFICATION_CONTENTINSTANCE_CREATE
    case VERIFICATION_LOCATIONPOLICY_CREATE
    case VERIFICATION_AE_CREATE
    case VERIFICATION_AREANWKINFO_CREATE
    
    case VERIFICATION_AREANWKINFO_UPDATE = 9
    case VERIFICATION_AE_UPDATE
    case VERIFICATION_LOCATIONPOLICY_UPDATE
    case VERIFICATION_MGMTCMD_UPDATE
    case VERIFICATION_CONTAINER_UPDATE
    case VERIFICATION_REMOTECSE_UPDATE
    case VERIFICATION_NODE_UPDATE
    
    case VERIFICATION_AREANWKINFO_DELETE = 16
    case VERIFICATION_AE_DELETE
    case VERIFICATION_LOCATIONPOLICY_DELETE
    case VERIFICATION_CONTENTINSTANCE_DELETE
    case VERIFICATION_MGMTCMD_DELETE
    case VERIFICATION_CONTAINER_DELETE
    case VERIFICATION_REMOTECSE_DELETE
    case VERIFICATION_NODE_DELETE
    
    case VERIFICATION_END
}
