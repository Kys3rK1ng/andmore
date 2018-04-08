/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.andmore;

import java.io.File;
import java.io.IOException;

import com.android.prefs.AndroidLocation;
import com.android.prefs.AndroidLocation.AndroidLocationException;
import com.android.utils.ILogger;

/**
 * Location within user's Android home to install files required by Andmore that must be mounted 
 * on the filesystem as opposed to being packaged in a plugin. Created to deploy desugur tool.
 */
public class AndmoreHome {
    private final static String ANDMORE_HOME_PATH = ".andmore";

	private static final String ANDMORE_HOME_ERROR = "Error creating Andmore home directory %s";

    private File andmoreHomePath;
    
    public AndmoreHome(ILogger logger) {
    	String androidHomeDir = null;
        try {
        	/* getFolder()
		     * Returns the folder used to store android related files.
		     * If the folder is not created yet, it will be created.
		     * @return an OS specific path, terminated by a separator.
        	 */
            androidHomeDir = AndroidLocation.getFolder();
            File androidHomeFile = new File(androidHomeDir);
            if (!androidHomeFile.exists()) {
            	if (!androidHomeFile.mkdirs())
            		throw new IOException(String.format("Cannot create path %s", androidHomeFile.getAbsolutePath()));
            } else if (!androidHomeFile.isDirectory())
        		throw new IOException(String.format("Path %s is not a directory", androidHomeFile.getAbsolutePath()));
        } catch (AndroidLocationException | IOException | SecurityException e) {
        	logger.error(e, "Error finding Android home");
        	// Apply default
        	androidHomeDir = System.getProperty("user.dir") + File.separator + AndroidLocation.FOLDER_DOT_ANDROID;
        }
        andmoreHomePath = new File(androidHomeDir, ANDMORE_HOME_PATH);
    	if (!andmoreHomePath.exists()) {
        	// Following may fail or cause a SecurityException, which cannot be handled in this context
    		// Subsequent Preference store operations will always fail if this operation fails
    		try {
    			andmoreHomePath.mkdirs();
    		} catch (SecurityException t) {
    			logger.error(t, ANDMORE_HOME_ERROR, andmoreHomePath);
    		}
    	}
    	if (!andmoreHomePath.exists()) 
    		throw new IllegalStateException(String.format(ANDMORE_HOME_ERROR, andmoreHomePath));
    }

	public File getAndmoreHomePath() {
		return andmoreHomePath;
	}
}
