//
//  execInstance.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 2..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class execInstance {
    
    //MARK: Properties
    var ni: String?      // nodeId
    var dKey: String?    // deviceKey
    var exr: String?     // execResult
    var exs: String?     // execStatus
    
    public init() {
    }
    
    public func ni(_ ni: String) -> execInstance {
        self.ni = ni
        return self
    }
    
    public func exr(_ exr: String) -> execInstance {
        self.exr = exr
        return self
    }
    
    public func dKey(_ dKey: String) -> execInstance {
        self.dKey = dKey
        return self
    }
    
    public func exs(_ exs: String) -> execInstance {
        self.exs = exs
        return self
    }
}

