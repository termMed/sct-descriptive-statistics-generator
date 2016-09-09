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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.termmed.fileprovider.CurrentFile;
import com.termmed.statistics.db.importer.ImportManager;
import com.termmed.statistics.model.AdditionalList;
import com.termmed.statistics.model.IReportDetail;
import com.termmed.statistics.model.OutputDetailFile;
import com.termmed.statistics.model.OutputFileTableMap;
import com.termmed.statistics.model.OutputInfoFactory;
import com.termmed.statistics.model.ReportConfig;
import com.termmed.statistics.model.ReportInfo;
import com.termmed.statistics.model.ReportListenerDescriptor;
import com.termmed.statistics.model.ReportListeners;
import com.termmed.statistics.model.SelectTableMap;
import com.termmed.statistics.model.StoredProcedure;
import com.termmed.statistics.reportlisteners.IReportListener;
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

	private TreeMap<Integer, IReportDetail> mapExcluyentListFile;

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
		List<HierarchicalConfiguration> listDescriptors = xmlConfig
				.configurationsAt("interestConceptLists.conceptListDescriptor");

		List<HierarchicalConfiguration> fields = xmlConfig
				.configurationsAt("reports.reportDescriptor");
		String excList=xmlConfig.getString("createExcluyentDetailLists");
		boolean createExcluyentDetailLists=false;
		if (excList!=null && excList.equals("true")){
			createExcluyentDetailLists=true;
		}
		mapExcluyentListFile=new TreeMap<Integer,IReportDetail>();

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
				writeReports(reportCfg,report, listDescriptors);

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

				if (createExcluyentDetailLists){
					if (reportCfg.getOutputDetailFile()!=null){

						for ( OutputDetailFile file:reportCfg.getOutputDetailFile()){
							Integer priority= file.getExcluyentListPriority();
							if (priority!=null){
								mapExcluyentListFile.put(priority,file);
							}
						}
					}
				}
			}
			//				System.out.println(report + " " + value);
		}
		if (createExcluyentDetailLists){
			File outputFold = createOutputExcluyentListFolder();
			createExcluyentList(outputFold,listDescriptors);
		}
	}

	private File createOutputExcluyentListFolder() {
		File outputFolder=new File(I_Constants.EXCLUYENT_OUTPUT_FOLDER);
		if (!outputFolder.exists()){
			outputFolder.mkdirs();
		}else{
			FileHelper.emptyFolder(outputFolder);
		}		
		return outputFolder;
	}

	private void createExcluyentList(File outputFold, List<HierarchicalConfiguration> listDescriptors) throws IOException {

		HashSet<Long>conceptList=new HashSet<Long>();
		for (Integer order:mapExcluyentListFile.keySet()){
			IReportDetail file=mapExcluyentListFile.get(order);
			if (file.getPriorityListColumnIndex()==null){
				printExcluyentList(file,conceptList,outputFold);
			}else{
				printInternPriorityList(file,conceptList,outputFold);
			}
		}		
	}

	private void printInternPriorityList(IReportDetail file, HashSet<Long> conceptList, File outputFold) throws IOException {
		File exclFile=new File(I_Constants.EXCLUYENT_OUTPUT_FOLDER + "/" + file.getFile() + (file.getFile().toLowerCase().endsWith(".csv")?"":".csv"));
		File completeDetailFile=new File(I_Constants.STATS_OUTPUT_FOLDER + "/" + file.getFile() + (file.getFile().toLowerCase().endsWith(".csv")?"":".csv"));
		TreeSet<Long> order=getOrder(file,completeDetailFile);
		BufferedWriter bw=FileHelper.getWriter(exclFile);
		Integer sctIdIndex = file.getSctIdIndex();
		if (sctIdIndex==null){
			sctIdIndex=1;
		}
		Integer priorityIndex = file.getPriorityListColumnIndex();
		if (priorityIndex==null){
			priorityIndex=5;
		}
		boolean first=true;
		String line;
		String[] spl;

		for(Long ord:order){
			BufferedReader br = FileHelper.getReader(completeDetailFile);
			if (first){
				bw.append(br.readLine());
				bw.append("\r\n");
				first=false;
			}else{
				br.readLine();
			}

			while ((line=br.readLine())!=null){
				spl=line.split(",",-1);
				Long prior=Long.parseLong(spl[priorityIndex]);
				if (!prior.equals(ord)){
					continue;
				}
				Long cid=Long.parseLong(spl[sctIdIndex]);
				if (conceptList.contains(cid)){
					continue;
				}
				bw.append(line);
				bw.append("\r\n");
				conceptList.add(cid);
			}

			br.close();
		}
		bw.close();

	}

	private TreeSet<Long> getOrder(IReportDetail file, File completeDetailFile) throws IOException {
		BufferedReader br = FileHelper.getReader(completeDetailFile);
		TreeSet<Long> ret=new TreeSet<Long>();

		Integer priorityIndex = file.getPriorityListColumnIndex();
		if (priorityIndex==null){
			priorityIndex=5;
		}
		br.readLine();
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split(",",-1);
			ret.add(Long.parseLong(spl[priorityIndex]));
		}
		br.close();
		return ret;
	}

	private void printExcluyentList(IReportDetail file, HashSet<Long> conceptList, File outputFold) throws IOException {
		File exclFile=new File(I_Constants.EXCLUYENT_OUTPUT_FOLDER + "/" + file.getFile() + (file.getFile().toLowerCase().endsWith(".csv")?"":".csv"));
		File completeDetailFile=new File(I_Constants.STATS_OUTPUT_FOLDER + "/" + file.getFile() + (file.getFile().toLowerCase().endsWith(".csv")?"":".csv"));

		BufferedReader br = FileHelper.getReader(completeDetailFile);
		BufferedWriter bw=FileHelper.getWriter(exclFile);

		Integer sctIdIndex = file.getSctIdIndex();
		if (sctIdIndex==null){
			sctIdIndex=1;
		}
		bw.append(br.readLine());
		bw.append("\r\n");
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split(",",-1);
			Long cid=Long.parseLong(spl[sctIdIndex]);
			if (conceptList.contains(cid)){
				continue;
			}
			bw.append(line);
			bw.append("\r\n");
			conceptList.add(cid);
		}
		br.close();
		bw.close();
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
	 * @param listDescriptors 
	 * @throws Exception the exception
	 */
	private void writeReports(ReportConfig reportCfg, String report, List<HierarchicalConfiguration> listDescriptors) throws Exception {

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
					addHeader(bw,detail, listDescriptors);
					printReport(bw,detail,listDescriptors);
					bw.close();
					bw=null;

					if (report.toLowerCase().indexOf("new_concepts")>-1){
						CurrentFile.get().setNewConceptFile(tmp);
					}else if(report.toLowerCase().indexOf("changed_concept_definition")>-1){
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
	 * @param listDescriptors 
	 * @throws Exception the exception
	 */
	private void printReport(BufferedWriter bw, OutputDetailFile detail, List<HierarchicalConfiguration> listDescriptors) throws Exception {

		SQLStatementExecutor executor=new SQLStatementExecutor(connection);
		AdditionalList[] additionalList=null;
		if (listDescriptors!=null){
			additionalList=getAdditionalList(listDescriptors);
		}
		Integer ixSctIdInReport=detail.getSctIdIndex();
		if (ixSctIdInReport==null){
			ixSctIdInReport=1;
		}
		ReportListeners reportListenersDescriptors = detail.getReportListeners();
		List<IReportListener>dependentReports=null;
		if (reportListenersDescriptors!=null){
			dependentReports=initListeners(reportListenersDescriptors,detail);
		}
		for (StoredProcedure sProc:detail.getStoredProcedure()){
			executor.executeStoredProcedure(sProc, ImportManager.params, null);
			ResultSet rs=executor.getResultSet();
			if (rs!=null){
				ResultSetMetaData meta= rs.getMetaData();
				String fieldValue;
				String sctId="";
				String line;
				while(rs.next()){
					line="";
					for (int i=0;i<meta.getColumnCount();i++){
						fieldValue=rs.getObject(i+1).toString().replaceAll(",","&#44;").trim();
						if (ixSctIdInReport.intValue()==i){
							sctId=fieldValue;
						}
						line+=fieldValue;
						//						bw.append(fieldValue);
						if (i+1<meta.getColumnCount()){
							line+=",";
							//							bw.append(",");
						}else{
							if (additionalList!=null && detail.getCreateInterestConceptList()){
								for (int ix=0;i<additionalList.length;ix++){

									line+=",";
									//									bw.append(",");
									if (additionalList[ix].getIds().contains(sctId)){

										line+="1";
										//										bw.append("1");
									}else{
										line+="0";
										//										bw.append("0");
									}
								}
							}
							bw.append(line);
							bw.append("\r\n");
							if (dependentReports!=null){
								for (IReportListener dependentReport: dependentReports){
									dependentReport.actionPerform(line);
								}
							}
						}
					}
				}
				meta=null;
				rs.close();
			}

		}
		executor=null;		

		if (dependentReports!=null){
			for (IReportListener dependentReport: dependentReports){
				dependentReport.finalizeListener();
			}
		}
	}

	private List<IReportListener> initListeners(ReportListeners reportListeners, OutputDetailFile detail) throws InstantiationException, IllegalAccessException, ClassNotFoundException, UnsupportedEncodingException, FileNotFoundException, IOException {
		List<IReportListener> retReports=null;
		if (reportListeners.getReportListenerDescriptor()!=null){
			retReports=new ArrayList<IReportListener>();
			for (ReportListenerDescriptor reportListenerDescriptor:reportListeners.getReportListenerDescriptor()){
				reportListenerDescriptor.getExecutionClass();
				IReportListener report= (IReportListener) Class.forName(reportListenerDescriptor.getExecutionClass()).newInstance();
				report.setReportFile(new File(I_Constants.STATS_OUTPUT_FOLDER + "/" + reportListenerDescriptor.getFile() + (reportListenerDescriptor.getFile().toLowerCase().endsWith(".csv")?"":".csv")));
				report.setReportFilter(reportListenerDescriptor.getFilter());
				retReports.add(report);
				
				Integer priority= reportListenerDescriptor.getExcluyentListPriority();
				if (priority!=null){
					mapExcluyentListFile.put(priority,reportListenerDescriptor);
				}
			}
		}
		return retReports;
	}

	private AdditionalList[] getAdditionalList(List<HierarchicalConfiguration> listDescriptors) throws IOException {
		AdditionalList[] additionalList=new AdditionalList[listDescriptors.size()];
		int cont=0;
		for (HierarchicalConfiguration additional:listDescriptors){
			AdditionalList addList = new AdditionalList();
			String ix=additional.getString("conceptIdColumnIndex");
			int colIndex=0;
			if (ix!=null ){
				try{
					colIndex=Integer.parseInt(ix);
				}catch(Exception e){}
			}
			String file=additional.getString("filePath");
			if (file==null || file.trim().equals("")){
				continue;
			}
			addList.setFilePath(file);
			String listTitle=additional.getString("listTitle");
			if (listTitle==null || listTitle.trim().equals("")){
				listTitle="list " + cont;
			}
			addList.setListTitle(listTitle);
			addList.setConceptIdColumnIndex(String.valueOf(colIndex));
			HashSet<String> idList=getIdList(file,colIndex);
			addList.setIds(idList);
			additionalList[cont]=addList;
			cont++;
		}
		return additionalList;
	}

	private HashSet<String> getIdList(String file, int colIndex) throws IOException {
		HashSet<String> ret=new HashSet<String>();
		BufferedReader br = FileHelper.getReader(file);
		br.readLine();
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			ret.add(spl[colIndex]);
		}
		return ret;
	}

	/**
	 * Adds the header.
	 *
	 * @param bw the bw
	 * @param detail the detail
	 * @param listDescriptors 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void addHeader(BufferedWriter bw, OutputDetailFile detail, List<HierarchicalConfiguration> listDescriptors) throws IOException {
		bw.append(detail.getReportHeader());
		if (listDescriptors!=null){
			for (HierarchicalConfiguration additionalList: listDescriptors){
				bw.append(",");
				bw.append(additionalList.getString("listTitle"));
			}
		}
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
