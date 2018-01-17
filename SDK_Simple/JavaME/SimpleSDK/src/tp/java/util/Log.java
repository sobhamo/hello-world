/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package tp.java.util;

import tp.skt.simple.common.Util;


public class Log {
    private Log(){}
    
    static public void print(String tag, String message){
         Util.log(tag + " "+message);
    }
    
    static public void print(String message){
         Util.log(message);
    }
}
