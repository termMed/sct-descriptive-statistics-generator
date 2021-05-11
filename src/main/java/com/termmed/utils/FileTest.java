/**
 * Copyright (c) 2016 TermMed SA
 * Organization
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

/**
 * Author: Alejandro Rodriguez
 */
package com.termmed.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;


// TODO: Auto-generated Javadoc
/**
 * The Class FileTest.
 */
public class FileTest {
	
	/**
	 * Gets the file size.
	 *
	 * @param file the file
	 * @return the file size
	 */
	public static long getFileSize(File file){

		long fileSize = file.length();

		return fileSize;
	}

	/**
	 * Creates the checksum.
	 *
	 * @param filename the filename
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public static byte[] createChecksum(String filename) throws Exception {
		InputStream fis =  new FileInputStream(filename);
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}



	// a byte array to a HEX string
	/**
	 * Gets the m d5 checksum.
	 *
	 * @param filename the filename
	 * @return the m d5 checksum
	 * @throws Exception the exception
	 */
	public static String getMD5Checksum(String filename) throws Exception {
		byte[] b = createChecksum(filename);
		String result = "";

		for (int i=0; i < b.length; i++) {
			result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}


}
