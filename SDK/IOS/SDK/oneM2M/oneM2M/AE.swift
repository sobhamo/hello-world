//
//  AE.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 2..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class AE {
    
    //MARK: Properties
    var ni: String?     // nodeId
    var dKey: String?   // deviceKey
    var api: String?    // AppId
    var apn: String?    // appName
    
    public init() {
    }
    
    public func ni(_ ni: String) -> AE {
        self.ni = ni
        return self
    }
    
    public func api(_ api: String) -> AE {
        self.api = api
        return self
    }
    
    public func dKey(_ dKey: String) -> AE {
        self.dKey = dKey
        return self
    }
    
    public func apn(_ apn: String) -> AE {
        self.apn = apn
        return self
    }
}
