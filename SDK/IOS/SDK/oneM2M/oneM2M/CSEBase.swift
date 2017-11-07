//
//  CSEBase.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 2..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class CSEBase {
    
    //MARK: Properties
    var ni: String?     // nodeId
    
    //MARK: Initialization
    public init() {
    }
    
    public func ni(_ ni: String) -> CSEBase {
        self.ni = ni
        return self
    }
}
