//
//  SubscribeTopic.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 1. 30..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class SubscribeTopic {
    
    //MARK: Properties
    public var topicText: String
    public var isSubscribed: Bool
    
    //MARK: Initialization
    public init(_ topicText: String) {
        self.topicText = topicText
        self.isSubscribed = false
    }
    
}
