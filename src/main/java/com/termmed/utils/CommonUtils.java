/*
 * Copyright (c) 2012 International Health Terminology Standards Development
 * Organisation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.termmed.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


/**
 * Helper class for merge, concatenate files 
 * and get a UUID from a string with or without namespace
 * <br><br>
 * Use: for call static methods.
 */
public class CommonUtils {
	
	/** The Constant encoding. */
	private static final String encoding = "8859_1";
	
	/**
	 * Gets the {@link String[]} representing the smallest array.
	 *
	 * @param passedMap the passed map
	 * @param sortColumns the sort columns
	 * @return the {@link String[]}
	 */
	public static String[] getSmallestArray(HashMap<BufferedReader, String[]> passedMap, int[] sortColumns) {

		List<String[]> mapValues = new ArrayList<String[]>(passedMap.values());
		//
		Collections.sort(mapValues, new ArrayComparator(sortColumns, false));

		return mapValues.get(0);
	}

	/**
	 * Gets the {@link byte[]} representing the raw bytes.
	 *
	 * @param uid the uid
	 * @return the {@link byte[]}
	 */
	private static byte[] getRawBytes(UUID uid) {
		String id = uid.toString();
		if (id.length() != 36) {
			throw new NumberFormatException("UUID has to be represented by the standard 36-char representation");
		}
		byte[] rawBytes = new byte[16];

		for (int i = 0, j = 0; i < 36; ++j) {
			// Need to bypass hyphens:
			switch (i) {
			case 8:
			case 13:
			case 18:
			case 23:
				if (id.charAt(i) != '-') {
					throw new NumberFormatException("UUID has to be represented by the standard 36-char representation");
				}
				++i;
			}
			char c = id.charAt(i);

			if (c >= '0' && c <= '9') {
				rawBytes[j] = (byte) ((c - '0') << 4);
			} else if (c >= 'a' && c <= 'f') {
				rawBytes[j] = (byte) ((c - 'a' + 10) << 4);
			} else if (c >= 'A' && c <= 'F') {
				rawBytes[j] = (byte) ((c - 'A' + 10) << 4);
			} else {
				throw new NumberFormatException("Non-hex character '" + c + "'");
			}

			c = id.charAt(++i);

			if (c >= '0' && c <= '9') {
				rawBytes[j] |= (byte) (c - '0');
			} else if (c >= 'a' && c <= 'f') {
				rawBytes[j] |= (byte) (c - 'a' + 10);
			} else if (c >= 'A' && c <= 'F') {
				rawBytes[j] |= (byte) (c - 'A' + 10);
			} else {
				throw new NumberFormatException("Non-hex character '" + c + "'");
			}
			++i;
		}
		return rawBytes;
	}

	/**
	 * Gets the a UUID from string, a UUID is optional.
	 *
	 * @param namespace the namespace
	 * @param name the name
	 * @return the uuid
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static UUID get(UUID namespace, String name) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest sha1Algorithm = MessageDigest.getInstance("SHA-1");

		// Generate the digest.
		sha1Algorithm.reset();
		if (namespace != null) {
			sha1Algorithm.update(getRawBytes(namespace));
		}
		sha1Algorithm.update(name.getBytes(encoding));
		byte[] sha1digest = sha1Algorithm.digest();

		sha1digest[6] &= 0x0f; /* clear version */
		sha1digest[6] |= 0x50; /* set to version 5 */
		sha1digest[8] &= 0x3f; /* clear variant */
		sha1digest[8] |= 0x80; /* set to IETF variant */

		long msb = 0;
		long lsb = 0;
		for (int i = 0; i < 8; i++)
			msb = (msb << 8) | (sha1digest[i] & 0xff);
		for (int i = 8; i < 16; i++)
			lsb = (lsb << 8) | (sha1digest[i] & 0xff);

		return new UUID(msb, lsb);
	}

	/**
	 * Merge files .
	 *
	 * @param hFile the set with files to merge
	 * @param outputfile the outputfile
	 */
	public static void MergeFile(HashSet<File> hFile, File outputfile) {

		try{
			if (outputfile.exists())
				outputfile.delete();
			
			outputfile.createNewFile();

			String fileName=outputfile.getName();
			File fTmp = new File(outputfile.getParentFile()  + "/tmp_" + fileName);


			boolean first = true;
			String nextLine;
			for (File file:hFile){


				if (fTmp.exists())
					fTmp.delete();
				
				FileOutputStream fos = new FileOutputStream( fTmp);
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
				BufferedWriter bw = new BufferedWriter(osw);

				FileInputStream fis = new FileInputStream(file	);
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
				BufferedReader br = new BufferedReader(isr);

				FileInputStream ofis = new FileInputStream(outputfile	);
				InputStreamReader oisr = new InputStreamReader(ofis,"UTF-8");
				BufferedReader obr = new BufferedReader(oisr);

				
				nextLine=br.readLine();
				if (first && nextLine!=null){
					bw.append(nextLine);
					bw.append("\r\n");
					first=false;
				}

				while ((nextLine=obr.readLine())!=null){
					bw.append(nextLine);
					bw.append("\r\n");

				}
				while ((nextLine=br.readLine())!=null){
					bw.append(nextLine);
					bw.append("\r\n");

				}
				bw.close();
				br.close();
				obr.close();

				if (outputfile.exists())
					outputfile.delete();
				fTmp.renameTo(outputfile) ;
			}

			if (fTmp.exists())
				fTmp.delete();

		} catch (IOException e) {
			e.printStackTrace();
		}finally{

		}
	}

	/**
	 * Concatenate files.
	 *
	 * @param hFile the set with files to concatenate
	 * @param outputfile the outputfile
	 */
	public static void concatFile(HashSet<File> hFile, File outputfile) {

		try{
			
			String fileName=outputfile.getName();
			File fTmp = new File(outputfile.getParentFile()  + "/tmp_" + fileName);

			if (fTmp.exists())
				fTmp.delete();
			
			FileOutputStream fos = new FileOutputStream( fTmp);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);

			boolean first = true;
			String nextLine;
			for (File file:hFile){

				FileInputStream fis = new FileInputStream(file	);
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				
				nextLine=br.readLine();
				if (first && nextLine!=null){
					bw.append(nextLine);
					bw.append("\r\n");
					first=false;
				}

				while ((nextLine=br.readLine())!=null){
					bw.append(nextLine);
					bw.append("\r\n");

				}
				br.close();
				isr.close();
				fis.close();
				br=null;
				isr=null;
				fis=null;

			}

			bw.close();

			if (outputfile.exists())
				outputfile.delete();
			fTmp.renameTo(outputfile) ;
			
			if (fTmp.exists())
				fTmp.delete();

		} catch (IOException e) {
			e.printStackTrace();
		}finally{

		}
	}

	 /**
 	 * From snomed.
 	 *
 	 * @param id the id
 	 * @return the uuid
 	 */
 	public static UUID fromSNOMED(String id) {
	        String name = "org.snomed." + id;
	        try {
	            return UUID.nameUUIDFromBytes(name.getBytes(encoding));
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException(e);
	        }
	    }

}
