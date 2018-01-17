/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *    Dave Locke - initial API and implementation and/or initial documentation
 */
package org.eclipse.paho.client.mqttv3.internal;

public class ResourceBundleCatalog extends MessageCatalog {
	
	public ResourceBundleCatalog() {
		//MAY throws MissingResourceException
//		bundle = ResourceBundle.getBundle("org.eclipse.paho.client.mqttv3.internal.nls.messages");
	}

	protected String getLocalizedMessage(int id) {
//          try {
//                return bundle.getString(Integer.toString(id));
//          } catch (MissingResourceException mre) {return "MqttException";}

            String retVal = "ResourceBundleCatalog.java failed.";

            int[] numbers = {1,2,3,4,5,6, 
            32000,32001,32002,
            32100,32101,32102,32103,32104,32105,32106,32107,32108,32109,32110,32111,
            32200,32201,32202};
            int i;
            
            String[] messages = {"Invalid protocol version (#1)", "Invalid client ID", "Broker unavailable",
                "Bad user name or password", "Not authorized to connect", "Unexpected error (#6)",
                "Timed out waiting for a response from the server (#32000)", 
                "Internal error, caused by no new message IDs being available", 
                "Timed out while waiting to write messages to the server", 
                "Client is connected", 
                "Client is disconnected", 
                "Client is currently disconnecting", 
                "Unable to connect to server", 
                "Client is not connected", 
                "The specified SocketFactory type does not match the broker URI", 
                "SSL configuration error", 
                "Disconnecting is not allowed from a callback method", 
                "Unrecognized packet", 
                "Connection lost", 
                "Connect already in progress",
                "Client is closed", 
                "Persistence already in use", 
                "Token already in use", 
                "Too many publishes in progress (#32202)"
            };
        
            if (numbers.length == messages.length) {
                for (i = 0; i < numbers.length; i++) {
                    if (numbers[i] == id) {
                        retVal = messages[i];
                    }                
                }
            }

            return retVal;
        }
}
