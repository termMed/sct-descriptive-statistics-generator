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
package com.termmed.control.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.termmed.control.model.ControlResultLine;
import com.termmed.control.model.IControlPattern;
import com.termmed.control.model.PatternConfig;
import com.termmed.fileprovider.CurrentFile;
import com.termmed.statistics.model.OutputInfoFactory;
import com.termmed.statistics.runner.ProcessLogger;
import com.termmed.utils.FileHelper;
import com.termmed.utils.I_Constants;
import com.termmed.utils.ResourceUtils;
import com.termmed.utils.XmlMapUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class PatternExecutor.
 */
public class PatternExecutor {
	
	/** The config file. */
	File configFile;
	
	/** The result output folder. */
	private String resultOutputFolder;
	
	/** The excludes. */
	private HashSet<String> excludes;
	
	/** The new concepts. */
	private HashSet<String> newConcepts;
	
	/** The changed concepts. */
	private HashSet<String> changedConcepts;
	
	/** The concept terms. */
	private HashMap<Long,String> conceptTerms;
	
	/** The release date. */
	private String releaseDate;
	
	/** The previous release date. */
	private String previousReleaseDate;
	
	/** The logger. */
	ProcessLogger logger ;

	/**
	 * Instantiates a new pattern executor.
	 *
	 * @param configFile the config file
	 */
	public PatternExecutor( File configFile) {
		super();
		this.configFile = configFile;
		logger = new ProcessLogger();
	}


	/**
	 * Execute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public void execute() throws IOException, Exception {
		logger.logInfo("Starting patterns execution");
		XMLConfiguration xmlConfig;
		try {
			xmlConfig=new XMLConfiguration(configFile);
		} catch (ConfigurationException e) {
			logger.logInfo("ClassificationRunner - Error happened getting params configFile." + e.getMessage());
			throw e;
		}
		String runControls = xmlConfig.getString("patternExecutions.runControlPatterns");

		if (runControls==null || !runControls.toLowerCase().equals("true")){
			return;
		}
		resultOutputFolder=I_Constants.PATTERN_OUTPUT_FOLDER;
		File resultTmpFolder=new File(resultOutputFolder);
		if (!resultTmpFolder.exists()){
			resultTmpFolder.mkdirs();
		}else{
			FileHelper.emptyFolder(resultTmpFolder);
		}
		this.releaseDate = xmlConfig.getString("releaseDate");
		this.previousReleaseDate = xmlConfig.getString("previousReleaseDate");

		getNewConcepts();

		getChangedConcepts();

		excludes=new HashSet<String>();
		Object prop = xmlConfig.getProperty("patternExecutions.exclusions.patternId");
		if (prop!=null){
			if (prop instanceof Collection) {
				for (String loopProp : (Collection<String>)prop) {
					excludes.add(loopProp);
				}
			} else if (prop instanceof String) {
				excludes.add((String)prop);
			}
		}
		String relativePath="src/main/resources/";
		String path="org/ihtsdo/control/patterns";
		if( new File(relativePath).isDirectory() ){

			path=relativePath + path;

			logger.logInfo("Getting patterns from file system: " + path);
			executeFromFileSystem(path);

		}else{
			logger.logInfo("Getting patterns from resources: " + path);
			executeFromResources(path);

		}
	}

	/**
	 * Gets the changed concepts.
	 *
	 * @return the changed concepts
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws Exception the exception
	 */
	private void getChangedConcepts() throws UnsupportedEncodingException, FileNotFoundException, Exception {
		changedConcepts=new HashSet<String>();
		if (CurrentFile.get().getChangedConceptFile()!=null){
			BufferedReader br=FileHelper.getReader(CurrentFile.get().getChangedConceptFile());
			br.readLine();
			String line;
			String[] spl;
			while ((line=br.readLine())!=null){
				spl=line.split(",",-1);
				changedConcepts.add(spl[1]);
			}
			br.close();
		}
	}

	/**
	 * Gets the new concepts.
	 *
	 * @return the new concepts
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws Exception the exception
	 */
	private void getNewConcepts() throws UnsupportedEncodingException, FileNotFoundException, Exception {
		newConcepts=new HashSet<String>();
		if (CurrentFile.get().getNewConceptFile()!=null){
			BufferedReader br=FileHelper.getReader(CurrentFile.get().getNewConceptFile());
			br.readLine();
			String line;
			String[] spl;
			while ((line=br.readLine())!=null){
				spl=line.split(",",-1);
				newConcepts.add(spl[1]);
			}
			br.close();
		}
	}

	/**
	 * Execute from file system.
	 *
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 * @throws SQLException the sQL exception
	 */
	private void executeFromFileSystem(String path) throws IOException, Exception,
	SQLException {
		Collection<File>list = ResourceUtils.getFileSystemScripts(path);
		for(File file:list){

			if (file.getAbsolutePath().indexOf("/.")>-1 || file.getName().startsWith(".")){
				continue;
			}
			PatternConfig patternConfig=XmlMapUtil.getPatternConfigFromFileSystem(file);
			if (!excludes.contains(patternConfig.getPatternId())){
				processPattern(patternConfig);
			}
		}
	}

	/**
	 * Process pattern.
	 *
	 * @param patternConfig the pattern config
	 * @throws Exception the exception
	 */
	private void processPattern(PatternConfig patternConfig) throws Exception {
		logger.logInfo("Executing pattern: " + patternConfig.getName() + " - id:" + patternConfig.getPatternId());
		IControlPattern controlPattern= (IControlPattern) Class.forName(patternConfig.getExecutionClass()).newInstance();

		controlPattern.setConfigFile(configFile);

		controlPattern.setNewConceptsList(newConcepts);
		controlPattern.setChangedConceptsList(changedConcepts);
		controlPattern.setCurrentEffTime(releaseDate);
		controlPattern.setPreviousEffTime(previousReleaseDate);
		controlPattern.setPatternId(patternConfig.getPatternId());
		controlPattern.setResultFile(new File(resultOutputFolder,patternConfig.getResultFileName()));
		controlPattern.setConceptTerms(CurrentFile.get().getConceptTerms());
		long start=logger.startTime();
		controlPattern.execute();

		String msg=logger.endTime(start);
		int posIni=msg.indexOf("ProcessingTime:")+16;
		patternConfig.setTimeTaken(msg.substring(posIni));
		List<ControlResultLine> sample = controlPattern.getSample();
		if (sample!=null){
			patternConfig.setResultSample(sample);
			patternConfig.setResultCount(controlPattern.getResultCount());
			OutputInfoFactory.get().getPatternProcess().getPatterns().add(patternConfig);
		}
		controlPattern=null;

	}


	/**
	 * Execute from resources.
	 *
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 * @throws SQLException the sQL exception
	 */
	private void executeFromResources(String path) throws IOException, Exception,
	SQLException {

		Collection<String>list = ResourceUtils.getResourceScripts(path);
		for(String file:list){
			if (file.indexOf("/.")>-1 || file.startsWith(".") || !file.endsWith(".xml")){
				continue;
			}
			String patternFile;
			if (file.indexOf("/")>-1){
				patternFile=file;
			}else{
				patternFile=path + "/" + file;
			}
			PatternConfig patternConfig=XmlMapUtil.getPatternConfigFromResource(patternFile);
			if (!excludes.contains(patternConfig.getPatternId())){
				processPattern(patternConfig);
			}
		}
	}

}
