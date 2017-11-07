//
//  MQTTMessage.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 4. 13..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class Message {
    
    //MARK: Properties
    public var topic: String
    public var subscribedMessage: String
    
    //MARK: Initialization
    init?(topic: String, subscribedMessage: String) {
        
        // Initialize stored properties.
        self.topic = topic
        self.subscribedMessage = subscribedMessage
    }
}
