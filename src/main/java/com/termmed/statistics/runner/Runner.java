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
package com.termmed.statistics.runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.termmed.control.executor.PatternExecutor;
import com.termmed.fileprovider.CurrentFile;
import com.termmed.fileprovider.PreviousFile;
import com.termmed.statistics.Processor;
import com.termmed.statistics.db.importer.ImportManager;
import com.termmed.statistics.model.Config;
import com.termmed.statistics.model.OutputInfoFactory;
import com.termmed.utils.FileHelper;
import com.termmed.utils.I_Constants;
import com.termmed.utils.XmlMapUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class Runner.
 */
public class Runner {

	/** The logger. */
	private static ProcessLogger logger;
	
	/** The changed date. */
	private static boolean changedDate;
	
	/** The changed previous date. */
	private static boolean changedPreviousDate;

	/** The data folder. */
	static File dataFolder;
	
	/** The previous release date. */
	private static String previousReleaseDate;
	
	/** The release date. */
	private static String releaseDate;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){

		logger=new ProcessLogger();
		if (args.length==0){
			logger.logInfo("Error happened getting params. Params file doesn't exist");
			System.exit(0);
		}
		File infoFolder = new File(I_Constants.PROCESS_INFO_FOLDER);
		if (!infoFolder.exists()){
			infoFolder.mkdirs();
		}
		OutputInfoFactory.get().setExecutionId(UUID.randomUUID().toString());
		String msg;
		int posIni;
		long start=logger.startTime();
		File file =new File(args[0]);
		Config configFile=getConfig(file);
		OutputInfoFactory.get().setConfig(configFile);
		System.setProperty("textdb.allow_full_path", "true");
		Connection c;
		try {
			boolean clean=false;
			if (args.length>=2){
				for (int i=1;i<args.length;i++){
					if (args[i].toLowerCase().equals("-clean")){
						clean=true;
					}
				}
			}
			dataFolder=new File(I_Constants.REPO_FOLDER);
			if (!dataFolder.exists()){
				dataFolder.mkdirs();
			}

			changedDate=true;
			changedPreviousDate=true;
			getParams(file);
			checkDates();
		/*******************************/
//			changedDate=false;
//			changedPreviousDate=false;
		/********************************/
			if (clean || changedDate || changedPreviousDate){
				removeDBFolder();
				removeRepoFolder();
			}
			
			Class.forName("org.hsqldb.jdbcDriver");			
			logger.logInfo("Connecting to DB. This task can take several minutes... wait please.");
			c = DriverManager.getConnection("jdbc:hsqldb:file:" + I_Constants.DB_FOLDER, "sa", "sa");

			
			initFileProviders(file);
//			OutputInfoFactory.get().getStatisticProcess().setOutputFolder(I_Constants.STATS_OUTPUT_FOLDER);
			
			
			/*******************************/
//			DbSetup dbs=new DbSetup(c);
//			dbs.recreatePath("org/ihtsdo/statistics/db/setup/storedprocedure");
//			dbs=null;
			/*******************************/
			
			ImportManager impor =new ImportManager(c,file,changedDate,changedPreviousDate);
			impor.execute();

			impor=null;
			
			Processor proc=new Processor(c, file);
			
			proc.execute();
			
			proc=null;

			msg=logger.endTime(start);
			posIni=msg.indexOf("ProcessingTime:")+16;
			OutputInfoFactory.get().getStatisticProcess().setTimeTaken(msg.substring(posIni));
//			OutputInfoFactory.get().getPatternProcess().setOutputFolder(I_Constants.PATTERN_OUTPUT_FOLDER);
			long startPattern=logger.startTime();
			PatternExecutor pe=new PatternExecutor(file);
			
			pe.execute();
			
			pe=null;
			msg=logger.endTime(startPattern);
			posIni=msg.indexOf("ProcessingTime:")+16;
			OutputInfoFactory.get().getPatternProcess().setTimeTaken(msg.substring(posIni));
			
			OutputInfoFactory.get().setStatus("Complete");
		} catch (Exception e) {
			OutputInfoFactory.get().setStatus("Error: " + e.getMessage() + " - View log for details.");
			e.printStackTrace();
		}
		msg=logger.endTime(start);
		posIni=msg.indexOf("ProcessingTime:")+16;
		OutputInfoFactory.get().setTimeTaken(msg.substring(posIni));

		try {
			saveInfo();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Save info.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void saveInfo() throws IOException {
		Gson gson=new GsonBuilder().setPrettyPrinting().create();

		BufferedWriter bw = FileHelper.getWriter(new File(I_Constants.PROCESS_INFO_FOLDER,"analyticsResults.json"));
		
		bw.append(gson.toJson(OutputInfoFactory.get()).toString());
		
		bw.close();
		
	}

	/**
	 * Gets the config.
	 *
	 * @param file the file
	 * @return the config
	 */
	private static Config getConfig(File file) {
		Config config=XmlMapUtil.getConfigFromFileSystem(file);
		return config;
	}

	/**
	 * Inits the file providers.
	 *
	 * @param file the file
	 * @throws Exception the exception
	 */
	private static void initFileProviders(File file) throws Exception {
		logger.logInfo("Initializing file providers");
		XMLConfiguration xmlConfig;
		try {
			xmlConfig=new XMLConfiguration(file);
		} catch (ConfigurationException e) {
			logger.logInfo("ClassificationRunner - Error happened getting params configFile." + e.getMessage());
			throw e;
		}
		
		String releaseFolder = xmlConfig.getString("releaseFullFolder");
		if (releaseFolder==null || releaseFolder.trim().equals("") || !new File(releaseFolder).exists()){
			throw new Exception ("Release folder doesn't exist.");
		}

		File sourceFolder=new File(releaseFolder);
		
		Object prop = xmlConfig.getProperty("releaseDependencies.releaseFullFolder");
		HashSet<String> releaseDependencies=null;
		if (prop!=null){
			if (prop instanceof Collection) {
				releaseDependencies=new HashSet<String>();
				for (String loopProp : (Collection<String>)prop) {
					releaseDependencies.add(loopProp);
				}
			} else if (prop instanceof String) {
				releaseDependencies=new HashSet<String>();
				releaseDependencies.add((String)prop);
				System.out.println(prop);
			}
		
		}
		String releaseDate = xmlConfig.getString("releaseDate");
		String previousReleaseDate = xmlConfig.getString("previousReleaseDate");
		
		CurrentFile.init(sourceFolder, new File("release" + releaseDate),releaseDependencies, releaseDate);
		PreviousFile.init(sourceFolder, new File("release" + previousReleaseDate),releaseDependencies, previousReleaseDate);
	}

	/**
	 * Removes the db folder.
	 */
	private static void removeDBFolder() {
		File db=new File(I_Constants.DB_ROOT_FOLDER);
		if (db.exists()){
			FileHelper.emptyFolder(db);
		}
		
	}
	
	/**
	 * Removes the repo folder.
	 */
	private static void removeRepoFolder() {
		
		File data=new File(I_Constants.REPO_FOLDER);
		if (data.exists()){
			FileHelper.emptyFolder(data);
		}
	}

	/**
	 * Check dates.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ConfigurationException the configuration exception
	 */
	private static void checkDates() throws IOException, ConfigurationException {
		String relInDB=getOldDate(I_Constants.RELEASE_DATE);
		if (relInDB!=null && relInDB.equals(releaseDate)){
			logger.logInfo("Same release date detected with previous process on db.");
			changedDate=false;
		}
		String prevRelInDB=getOldDate(I_Constants.PREVIOUS_RELEASE_DATE);
		if (prevRelInDB!=null && prevRelInDB.equals(previousReleaseDate)){
			logger.logInfo("Same previous release date detected with previous process on db.");
			changedPreviousDate=false;
		}
		
	}
	
	/**
	 * Gets the old date.
	 *
	 * @param fileName the file name
	 * @return the old date
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String getOldDate(String fileName  ) throws IOException {

		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		File file=new File(dataFolder, fileName + ".dat");
		if (file.exists()){
			FileInputStream rfis = new FileInputStream(file);
			InputStreamReader risr = new InputStreamReader(rfis,"UTF-8");
			BufferedReader rbr = new BufferedReader(risr);
			String ret=rbr.readLine();
			rbr.close();
			rbr=null;
			return ret;
		}
		return "";
	}
	
	/**
	 * Gets the params.
	 *
	 * @param configFile the config file
	 * @return the params
	 * @throws Exception the exception
	 */
	private static void getParams(File configFile) throws Exception{
		XMLConfiguration xmlConfig;
		try {
			xmlConfig=new XMLConfiguration(configFile);
		} catch (ConfigurationException e) {
			logger.logError("ClassificationRunner - Error happened getting params configFile." + e.getMessage());
			throw e;
		}

		releaseDate = xmlConfig.getString("releaseDate");
		previousReleaseDate = xmlConfig.getString("previousReleaseDate");
		if (releaseDate==null || releaseDate.length()!=8){
			throw new Exception ("Release date param is wrong.");
		}
		if (previousReleaseDate==null || previousReleaseDate.length()!=8){
			throw new Exception ("Release date param is wrong.");
		}
	}

}
