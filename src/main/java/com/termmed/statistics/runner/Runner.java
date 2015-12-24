package com.termmed.statistics.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

import com.termmed.statistics.Processor;
import com.termmed.statistics.db.importer.ImportManager;
import com.termmed.statistics.db.setup.DbSetup;
import com.termmed.utils.FileHelper;
import com.termmed.utils.I_Constants;

public class Runner {

	private static ProcessLogger logger;
	private static boolean changedDate;
	private static boolean changedPreviousDate;

	static File dataFolder;
	private static String previousReleaseDate;
	private static String releaseDate;
	public static void main(String[] args){

		logger=new ProcessLogger();
		if (args.length==0){
			logger.logInfo("Error happened getting params. Params file doesn't exist");
			System.exit(0);
		}
		Long start=logger.startTime();
		File file =new File(args[0]);
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
			changedDate=false;
			changedPreviousDate=false;
		/********************************/
			if (clean || changedDate || changedPreviousDate){
				removeDBFolder();
				removeRepoFolder();
			}
			
			Class.forName("org.hsqldb.jdbcDriver");			
			logger.logInfo("Connecting to DB. This task can take several minutes... wait please.");
			c = DriverManager.getConnection("jdbc:hsqldb:file:" + I_Constants.DB_FOLDER, "sa", "sa");

			ImportManager impor =new ImportManager(c,file,changedDate,changedPreviousDate);
			impor.execute();

			impor=null;
			
			Processor proc=new Processor(c, file);
			
			proc.execute();
			
			proc=null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.endTime(start);
	}

	private static void removeDBFolder() {
		File db=new File(I_Constants.DB_ROOT_FOLDER);
		if (db.exists()){
			FileHelper.emptyFolder(db);
		}
		
	}
	
	private static void removeRepoFolder() {
		
		File data=new File(I_Constants.REPO_FOLDER);
		if (data.exists()){
			FileHelper.emptyFolder(data);
		}
	}

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
