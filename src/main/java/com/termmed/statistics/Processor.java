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
package com.termmed.statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.termmed.fileprovider.CurrentFile;
import com.termmed.statistics.db.importer.ImportManager;
import com.termmed.statistics.model.OutputDetailFile;
import com.termmed.statistics.model.OutputFileTableMap;
import com.termmed.statistics.model.OutputInfoFactory;
import com.termmed.statistics.model.ReportConfig;
import com.termmed.statistics.model.ReportInfo;
import com.termmed.statistics.model.SelectTableMap;
import com.termmed.statistics.model.StoredProcedure;
import com.termmed.statistics.runner.ProcessLogger;
import com.termmed.utils.FileHelper;
import com.termmed.utils.I_Constants;
import com.termmed.utils.ResourceUtils;
import com.termmed.utils.SQLStatementExecutor;


// TODO: Auto-generated Javadoc
/**
 * The Class Processor.
 */
public class Processor {

	/** The connection. */
	private Connection connection;
	
	/** The config file. */
	private File configFile;
	
	/** The create details. */
	private String createDetails;

	/** The logger. */
	private ProcessLogger logger;
	
	/**
	 * Instantiates a new processor.
	 *
	 * @param con the con
	 * @param file the file
	 */
	public Processor(Connection con, File file) {
		this.connection=con;
		this.configFile=file;
		logger = new ProcessLogger();
	}
	
	/**
	 * Execute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public void execute() throws IOException, Exception {
		logger.logInfo("Starting report execution");
		createFolders();
		XMLConfiguration xmlConfig;
		try {
			xmlConfig=new XMLConfiguration(configFile);
		} catch (ConfigurationException e) {
			logger.logInfo("ClassificationRunner - Error happened getting params configFile." + e.getMessage());
			throw e;
		}
		createDetails=xmlConfig.getString("createDetailReports");
		List<HierarchicalConfiguration> fields = xmlConfig
				.configurationsAt("reports.reportDescriptor");

		for (HierarchicalConfiguration sub : fields) {

			String report=sub.getString("filename");
			String value=sub.getString("execute");

			if (value.toLowerCase().equals("true")){
				logger.logInfo("Getting report config for " + report);
				ReportConfig reportCfg=ResourceUtils.getReportConfig(report);
				logger.logInfo("Executing report " + report);
				long start=logger.startTime();
				executeReport(reportCfg);

				logger.logInfo("Writing report " + report);
				writeReports(reportCfg,report);

				String msg=logger.endTime(start);
				int posIni=msg.indexOf("ProcessingTime:")+16;
				ReportInfo rInfo=new ReportInfo();
				rInfo.setName(reportCfg.getName());
				if (reportCfg.getOutputFile()!=null){
					for ( OutputFileTableMap file:reportCfg.getOutputFile()){
						rInfo.getOutputFiles().add(file.getFile());
					}
				}
				if (reportCfg.getOutputDetailFile()!=null){
					for ( OutputDetailFile file:reportCfg.getOutputDetailFile()){
						rInfo.getOutputDetailFiles().add(file.getFile());
					}
				}
				rInfo.setTimeTaken(msg.substring(posIni));
				OutputInfoFactory.get().getStatisticProcess().getReports().add(rInfo);
			}
			//				System.out.println(report + " " + value);
		}
	}

	/**
	 * Creates the folders.
	 */
	private void createFolders() {

		File outputFolder=new File(I_Constants.STATS_OUTPUT_FOLDER);
		if (!outputFolder.exists()){
			outputFolder.mkdirs();
		}else{
			FileHelper.emptyFolder(outputFolder);
		}
	}
	
	/**
	 * Write reports.
	 *
	 * @param reportCfg the report cfg
	 * @param report the report
	 * @throws Exception the exception
	 */
	private void writeReports(ReportConfig reportCfg, String report) throws Exception {

		if (reportCfg.getOutputFile()!=null){
			for(OutputFileTableMap tableMap:reportCfg.getOutputFile()){
				BufferedWriter bw=FileHelper.getWriter(I_Constants.STATS_OUTPUT_FOLDER + "/" + tableMap.getFile() + (tableMap.getFile().toLowerCase().endsWith(".csv")?"":".csv"));
				addHeader(bw,tableMap);
				printReport(bw,tableMap);
				bw.close();
				bw=null;
			}
		}
		if (createDetails.toLowerCase().equals("true")){
			if (reportCfg.getOutputDetailFile()!=null){
				for(OutputDetailFile detail:reportCfg.getOutputDetailFile()){
					File tmp=new File(I_Constants.STATS_OUTPUT_FOLDER + "/" + detail.getFile() + (detail.getFile().toLowerCase().endsWith(".csv")?"":".csv"));
					BufferedWriter bw=FileHelper.getWriter(tmp);
					addHeader(bw,detail);
					printReport(bw,detail);
					bw.close();
					bw=null;

					if (report.toLowerCase().indexOf("new_concepts")>-1){
						CurrentFile.get().setNewConceptFile(tmp);
					}else if(report.toLowerCase().indexOf("changed_concepts")>-1){
						CurrentFile.get().setChangedConceptFile(tmp);
					}
				}
			}
		}
	}


	/**
	 * Prints the report.
	 *
	 * @param bw the bw
	 * @param detail the detail
	 * @throws Exception the exception
	 */
	private void printReport(BufferedWriter bw, OutputDetailFile detail) throws Exception {

		SQLStatementExecutor executor=new SQLStatementExecutor(connection);

		for (StoredProcedure sProc:detail.getStoredProcedure()){
			executor.executeStoredProcedure(sProc, ImportManager.params, null);
			ResultSet rs=executor.getResultSet();
			if (rs!=null){
				ResultSetMetaData meta= rs.getMetaData();
				while(rs.next()){
					for (int i=0;i<meta.getColumnCount();i++){
						bw.append(rs.getObject(i+1).toString().replaceAll(",","&#44;").trim());
						if (i+1<meta.getColumnCount()){
							bw.append(",");
						}else{
							bw.append("\r\n");
						}
					}
				}
				meta=null;
				rs.close();
			}

		}
		executor=null;		
	}
	
	/**
	 * Adds the header.
	 *
	 * @param bw the bw
	 * @param detail the detail
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void addHeader(BufferedWriter bw, OutputDetailFile detail) throws IOException {
		bw.append(detail.getReportHeader());
		bw.append("\r\n");

	}
	
	/**
	 * Adds the header.
	 *
	 * @param bw the bw
	 * @param tableMap the table map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void addHeader(BufferedWriter bw, OutputFileTableMap tableMap) throws IOException {
		bw.append(tableMap.getReportHeader());
		bw.append("\r\n");
	}

	/**
	 * Prints the report.
	 *
	 * @param bw the bw
	 * @param tableMap the table map
	 * @throws Exception the exception
	 */
	private void printReport(BufferedWriter bw, OutputFileTableMap tableMap) throws Exception {

		SQLStatementExecutor executor=new SQLStatementExecutor(connection);

		for (SelectTableMap select:tableMap.getSelect()){
			String query="Select * from " + select.getTableName();
			if (executor.executeQuery(query, null)){
				ResultSet rs=executor.getResultSet();

				if (rs!=null){
					ResultSetMetaData meta= rs.getMetaData();
					while(rs.next()){
						for (int i=0;i<meta.getColumnCount();i++){
							bw.append(rs.getObject(i+1).toString());
							if (i+1<meta.getColumnCount()){
								bw.append(",");
							}else{
								bw.append("\r\n");
							}
						}
					}

					meta=null;
					rs.close();
				}
			}
		}
		executor=null;
	}

	/**
	 * Execute report.
	 *
	 * @param reportCfg the report cfg
	 * @throws Exception the exception
	 */
	private void executeReport(ReportConfig reportCfg) throws Exception {

		SQLStatementExecutor executor=new SQLStatementExecutor(connection);
		executor.executeStoredProcedure(reportCfg.getStoredProcedure(), ImportManager.params, null);
		executor=null;
	}
}
