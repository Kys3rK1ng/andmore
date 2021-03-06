/********************************************************************************
  * Copyright (c) 2007-2010 Motorola Inc.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is 
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Initial Contributors:
 * Daniel Franco (Motorola)
 * 
 * Contributors:
 * Daniel Pastore (Eldorado) - [289870] Moving and renaming Tml to Sequoyah
 ********************************************************************************/

package org.eclipse.sequoyah.device.service.vncviewer.exception;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.sequoyah.device.common.utilities.exception.ExceptionHandler;
import org.eclipse.sequoyah.device.common.utilities.exception.SequoyahException;
import org.eclipse.sequoyah.device.service.vncviewer.VNCViewerServicePlugin;

public class VNCViewerServiceExceptionHandler extends ExceptionHandler {
	

	public static SequoyahException exception(IStatus status) {
		return new SequoyahException(new VNCViewerServiceExceptionStatus(status));
	}
	
	public static SequoyahException exception(int severity, int code, String message, Throwable exception) {
		return new SequoyahException(new VNCViewerServiceExceptionStatus(severity, VNCViewerServicePlugin.PLUGIN_ID, code, message, exception));
	}

	public static SequoyahException exception(int code){
		return new SequoyahException(new VNCViewerServiceExceptionStatus(code,VNCViewerServicePlugin.PLUGIN_ID,null,null));
	}

	public static SequoyahException exception(int code,Throwable exception) {
		return new SequoyahException(new VNCViewerServiceExceptionStatus(code,VNCViewerServicePlugin.PLUGIN_ID,exception));
	}
	
	public static SequoyahException exception(int code,Object[] args,Throwable exception) {
		return new SequoyahException(new VNCViewerServiceExceptionStatus(code,VNCViewerServicePlugin.PLUGIN_ID,args,exception));		
	}
	
}
