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
package org.eclipse.paho.client.mqttv3.logging;
import tp.java.util.PropertyResourceBundle;

/**
 * Implementation of the the logger interface that uses java.uti.logging
 * 
 * A Logger that utilises Java's built in logging facility - java.util.logging.
 * <p>A sample java.util.logging properties file - jsr47min.properties is provided that demonstrates
 * how to run with a memory based trace facility that runs with minimal performance 
 * overhead. The memory buffer can be dumped when a log/trace record is written matching 
 * the MemoryHandlers trigger level or when the push method is invoked on the MemoryHandler. 
 * {@link org.eclipse.paho.client.mqttv3.util.Debug Debug} provides method to make it easy
 * to dump the memory buffer as well as other useful debug info. 
 */
public class JSR47Logger implements Logger {
//	private java.util.logging.Logger	julLogger			= null;
	private PropertyResourceBundle				logMessageCatalog	= null;
	private PropertyResourceBundle				traceMessageCatalog	= null;
	private String						catalogID			= null;
	private String						resourceName		= null;
	private String						loggerName			= null;

	/**
	 * 
	 * @param logMsgCatalog  The resource bundle associated with this logger
	 * @param loggerID			The suffix for the loggerName (will be appeneded to org.eclipse.paho.client.mqttv3
	 * @param resourceContext	A context for the logger e.g. clientID or appName...
	 */
	public void initialise(PropertyResourceBundle logMsgCatalog, String loggerID, String resourceContext ) { 
		this.traceMessageCatalog = logMessageCatalog;
		this.resourceName = resourceContext;
		loggerName = loggerID;
		this.logMessageCatalog = logMsgCatalog;
		this.traceMessageCatalog = logMsgCatalog;
		this.catalogID = logMessageCatalog.getString("0");
		
	}
	
	public void setResourceName(String logContext) {
		this.resourceName = logContext;
	}

	public boolean isLoggable(int level) {
                return false;
        }

	public void severe(String sourceClass, String sourceMethod, String msg) {
		log(SEVERE, sourceClass, sourceMethod, msg, null, null);
	}

	public void severe(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
		log(SEVERE, sourceClass, sourceMethod, msg, inserts, null);
	}

	public void severe(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
		log(SEVERE, sourceClass, sourceMethod, msg, inserts, thrown);
	}

	public void warning(String sourceClass, String sourceMethod, String msg) {
		log(WARNING, sourceClass, sourceMethod, msg, null, null);
	}

	public void warning(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
		log(WARNING, sourceClass, sourceMethod, msg, inserts, null);
	}

	public void warning(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
		log(WARNING, sourceClass, sourceMethod, msg, inserts, thrown);
	}

	public void info(String sourceClass, String sourceMethod, String msg) {
		log(INFO, sourceClass, sourceMethod, msg, null, null);
	}

	public void info(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
		log(INFO, sourceClass, sourceMethod, msg, inserts, null);
	}

	public void info(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
		log(INFO, sourceClass, sourceMethod, msg, inserts, thrown);
	}

	public void config(String sourceClass, String sourceMethod, String msg) {
		log(CONFIG, sourceClass, sourceMethod, msg, null, null);
	}

	public void config(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
		log(CONFIG, sourceClass, sourceMethod, msg, inserts, null);
	}

	public void config(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
		log(CONFIG, sourceClass, sourceMethod, msg, inserts, thrown);
	}

	public void log(int level, String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable thrown) {
//		InternalTracer.log(this.catalogID, level, sourceClass, sourceMethod, msg, inserts, thrown);
//		java.util.logging.Level julLevel = mapJULLevel(level);
//		if (julLogger.isLoggable(julLevel)) {
//			logToJsr47(julLevel, sourceClass, sourceMethod, this.catalogID, this.logMessageCatalog, msg, inserts, thrown);
//		}
	}

//	public void setTrace(Trace trace) {
//		InternalTracer.setTrace(trace);
//	}

	public void fine(String sourceClass, String sourceMethod, String msg) {
		trace(FINE, sourceClass, sourceMethod, msg, null, null);
	}

	public void fine(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
		trace(FINE, sourceClass, sourceMethod, msg, inserts, null);
	}
	
	public void fine(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable ex) {
		trace(FINE, sourceClass, sourceMethod, msg, inserts, ex);
	}

	public void finer(String sourceClass, String sourceMethod, String msg) {
		trace(FINER, sourceClass, sourceMethod, msg, null, null);
	}

	public void finer(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
		trace(FINER, sourceClass, sourceMethod, msg, inserts, null);
	}
	
	public void finer(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable ex) {
		trace(FINER, sourceClass, sourceMethod, msg, inserts, ex);
	}

	public void finest(String sourceClass, String sourceMethod, String msg) {
		trace(FINEST, sourceClass, sourceMethod, msg, null, null);
	}

	public void finest(String sourceClass, String sourceMethod, String msg, Object[] inserts) {
		trace(FINEST, sourceClass, sourceMethod, msg, inserts, null);
	}
	
	public void finest(String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable ex) {
		trace(FINEST, sourceClass, sourceMethod, msg, inserts, ex);
	}


	public void trace(int level, String sourceClass, String sourceMethod, String msg, Object[] inserts, Throwable ex) {
//		java.util.logging.Level julLevel = mapJULLevel(level);
//		boolean isJULLoggable = julLogger.isLoggable(julLevel);
//		if (FINE == level || isJULLoggable || InternalTracer.isLoggable(level)) {
//			InternalTracer.traceForced(level, sourceClass, sourceMethod, msg, inserts);
//		}
//		if (isJULLoggable) {
//			logToJsr47(julLevel, sourceClass, sourceMethod, this.catalogID, this.traceMessageCatalog, msg, inserts, ex);
//		}
	}

	
	private String getResourceMessage(PropertyResourceBundle messageCatalog, String msg) {
		String message;
		try {
			message = messageCatalog.getString(msg);
		} catch (Exception e) {
			// This is acceptable, simply return the given msg string.
			message = msg;
		}
		return message;
	}

	public String formatMessage(String msg, Object[] inserts) {
		String formatString;
		try {
			formatString = logMessageCatalog.getString(msg);
		} catch (Exception e) {
			formatString = msg;
		}
		return formatString;
	}

	public void dumpTrace() {

	}
}
