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
/**
 * FileLock - used to obtain a lock that can be used to prevent other MQTT clients
 * using the same persistent store. If the lock is already held then an exception
 * is thrown. 
 * 
 * Some Java runtimes such as JME MIDP do not support file locking or even 
 * the Java classes that support locking.  The class is coded to both compile 
 * and work on all Java runtimes.  In Java runtimes that do not support 
 * locking it will look as though a lock has been obtained but in reality
 * no lock has been obtained. 
 */

public class FileLock {
	private Object fileLock;
	
	/**
	 * Creates an NIO FileLock on the specified file if on a suitable Java runtime. 
	 * @param clientDir the a File of the directory to contain the lock file. 
	 * @param lockFilename name of the the file to lock
	 * @throws Exception if the lock could not be obtained for any reason
	 */
	public FileLock(String lockFilename) throws Exception {
		// Create a file to obtain a lock on.               
	}
	
	/**
	 * Releases the lock.
	 */
	public void release() {

	}	
}
