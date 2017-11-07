//
//  Host.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 1. 30..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class Host {
    
    //MARK: Properties
    var name: String
    public var host: String
    public var port: String
    public var clientId: String?
    public var userName: String?
    public var password: String?
    public var usingSSL: Bool
    public var cleanSession: Bool
    var status: MQTTConnectionStatus
    
    //MARK: Initialization
    public init(name: String, host: String, port: String, clientId: String?, userName: String?, password: String?, usingSSL: Bool, cleanSession: Bool) {
//        if name.isEmpty || host.isEmpty || port.isEmpty {
//            return nil
//        }
        
        // Initialize stored properties.
//        self.id = id
        self.name = name
        self.host = host
        self.port = port
        self.clientId = clientId
        self.userName = userName
        self.password = password
        self.usingSSL = usingSSL
        self.cleanSession = cleanSession
        self.status = MQTTConnectionStatus.DISCONNECTED
    }
}
