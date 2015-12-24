package com.termmed.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.termmed.statistics.model.ReportConfig;

public class ResourceUtils{

	/**
	 * for all elements of java.class.path get a Collection of resources Pattern
	 * pattern = Pattern.compile(".*"); gets all resources
	 * 
	 * @param pattern
	 *            the pattern to match
	 * @return the resources in the order they are found
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
			String path="com/termmed/statistics/db/setup/table";
			list = ResourceUtils.getResourceScripts(path);
			for(final String file : list){

				String script=FileHelper.getTxtResourceContent(path + "/" + file);
				System.out.println(script);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Collection<File> getFileSystemScripts(String path) throws IOException{
		Collection<File> files = 
				FileUtils.listFiles(new File(path), null, false);
		return files;
	}
	public static Collection<String> getResourceScripts(String path) throws IOException{
		List<String> strFiles = IOUtils.readLines(ResourceUtils.class.getClassLoader()
				.getResourceAsStream(path), Charsets.UTF_8);
		return strFiles;
	}

	public static ReportConfig getReportConfig(String report) {
 
		String relativePath="src2/main/resources";
		ReportConfig reportCfg=null;
		String resourcePath="/com/termmed/statistics/reports/" + report + (report.endsWith("xml")?"":".xml");
		String path=relativePath + resourcePath;
		if( new File(relativePath).isDirectory() ){

			reportCfg=XmlMapUtil.getReportConfigFromFileSystem(path);

		}else{
			reportCfg=XmlMapUtil.getReportConfigFromResource(resourcePath);

		}
		return reportCfg;
	}
}  
