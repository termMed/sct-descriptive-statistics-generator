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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import com.termmed.statistics.model.ReportConfig;


// TODO: Auto-generated Javadoc
/**
 * The Class ResourceUtils.
 */
public class ResourceUtils{

	/** for all elements of java.class.path get a Collection of resources Pattern pattern = Pattern.compile(".*"); gets all resources */

	private static final long serialVersionUID = 7L;
	
	/**
	 * Gets the resources.
	 *
	 * @param pattern the pattern
	 * @param packageName the package name
	 * @return the resources
	 */
	public static Collection<String> getResources(
			final Pattern pattern,final String packageName){
		final ArrayList<String> retval = new ArrayList<String>();
		final String classPath = System.getProperty("java.class.path", packageName);
		final String[] classPathElements = classPath.split(System.getProperty("path.separator"));
		for(final String element : classPathElements){
			retval.addAll(getResources(element, pattern));
		}
		return retval;
	}

	/**
	 * Gets the resources.
	 *
	 * @param element the element
	 * @param pattern the pattern
	 * @return the resources
	 */
	private static Collection<String> getResources(
			final String element,
			final Pattern pattern){
		final ArrayList<String> retval = new ArrayList<String>();
		final File file = new File(element);
		if(file.isDirectory()){
			retval.addAll(getResourcesFromDirectory(file, pattern));
		} else{
			retval.addAll(getResourcesFromJarFile(file, pattern));
		}
		return retval;
	}

	/**
	 * Gets the resources from jar file.
	 *
	 * @param file the file
	 * @param pattern the pattern
	 * @return the resources from jar file
	 */
	private static Collection<String> getResourcesFromJarFile(
			final File file,
			final Pattern pattern){
		final ArrayList<String> retval = new ArrayList<String>();
		ZipFile zf;
		try{
			zf = new ZipFile(file);
		} catch(final ZipException e){
			throw new Error(e);
		} catch(final IOException e){
			throw new Error(e);
		}
		final Enumeration e = zf.entries();
		while(e.hasMoreElements()){
			final ZipEntry ze = (ZipEntry) e.nextElement();
			final String fileName = ze.getName();
			final boolean accept = pattern.matcher(fileName).matches();
			if(accept){
				retval.add(fileName);
			}
		}
		try{
			zf.close();
		} catch(final IOException e1){
			throw new Error(e1);
		}
		return retval;
	}

	/**
	 * Gets the resources from directory.
	 *
	 * @param directory the directory
	 * @param pattern the pattern
	 * @return the resources from directory
	 */
	private static Collection<String> getResourcesFromDirectory(
			final File directory,
			final Pattern pattern){
		final ArrayList<String> retval = new ArrayList<String>();
		final File[] fileList = directory.listFiles();
		for(final File file : fileList){
			if(file.isDirectory()){
				retval.addAll(getResourcesFromDirectory(file, pattern));
			} else{
				try{
					final String fileName = file.getCanonicalPath();
					final boolean accept = pattern.matcher(fileName).matches();
					if(accept){
						retval.add(fileName);
					}
				} catch(final IOException e){
					throw new Error(e);
				}
			}
		}
		return retval;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args){
		//		Pattern pattern;
		//		if(args.length < 1){
		//			pattern = Pattern.compile(".*");
		//		} else{
		//			pattern = Pattern.compile(args[0]);
		//		}
		//		final Collection<String> list = ResourceUtils.getResources(pattern);
		Collection<String> list;
		try {
			String path="org/ihtsdo/utils/";
			list = ResourceUtils.getResourceScripts(path);
			for(final String file : list){
				if(file.startsWith(".")){
					continue;
				}
				String script=FileHelper.getTxtResourceContent(path + "/" + file);
				System.out.println(script);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the file system scripts.
	 *
	 * @param path the path
	 * @return the file system scripts
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Collection<File> getFileSystemScripts(String path) throws IOException{
		return getFileSystemScripts(new File(path));
	}
	
	/**
	 * Gets the file system scripts.
	 *
	 * @param path the path
	 * @return the file system scripts
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Collection<File> getFileSystemScripts(File path) throws IOException{
		Collection<File> files = 
				FileUtils.listFiles(path, null, false);
		return files;
	}
	
	/**
	 * Gets the resource scripts.
	 *
	 * @param path the path
	 * @return the resource scripts
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Collection<String> getResourceScripts(String path) throws IOException{
		System.out.println("getting files from " + path);
		List<String> strFiles=new ArrayList<String>();
		
//		InputStream is=ResourceUtils.class.getResourceAsStream("/" + path);
//		InputStreamReader risr = new InputStreamReader(is);
//		BufferedReader br=new BufferedReader(risr);
//		String line;
//		while((line=br.readLine())!=null){
//			strFiles.add(line);
//		}
		
		String file=null;
		if (new File("stats-gen.jar").exists()){
			file="stats-gen.jar";
		}else{
			file="target/stats-gen.jar";
		}
		ZipInputStream zip = new ZipInputStream(new FileInputStream(file));
		while(true) {
			ZipEntry e = zip.getNextEntry();
			if (e == null)
				break;
			String name = e.getName();
			if (name.startsWith(path) && !name.endsWith("/")) {
//				if (!name.startsWith("/")){
//					name="/" + name;
//				}
				strFiles.add(name);
			}
		}
		
		System.out.println("files:" + strFiles.toString());
		return strFiles;
	}

	/**
	 * Gets the report config.
	 *
	 * @param report the report
	 * @return the report config
	 */
	public static ReportConfig getReportConfig(String report) {
 
		String relativePath="src2/main/resources";
		ReportConfig reportCfg=null;
		String resourcePath="/org/ihtsdo/statistics/reports/" + report + (report.endsWith("xml")?"":".xml");
		String path=relativePath + resourcePath;
		if( new File(relativePath).isDirectory() ){

			reportCfg=XmlMapUtil.getReportConfigFromFileSystem(path);

		}else{
			reportCfg=XmlMapUtil.getReportConfigFromResource(resourcePath);

		}
		return reportCfg;
	}
}  
