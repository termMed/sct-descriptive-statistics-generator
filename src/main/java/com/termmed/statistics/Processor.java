package com.termmed.statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

import com.termmed.statistics.db.importer.ImportManager;
import com.termmed.statistics.model.OutputDetailFile;
import com.termmed.statistics.model.OutputFileTableMap;
import com.termmed.statistics.model.ReportConfig;
import com.termmed.statistics.model.SelectTableMap;
import com.termmed.statistics.model.StoredProcedure;
import com.termmed.utils.ResourceUtils;
import com.termmed.utils.SQLStatementExecutor;

public class Processor {

	private Connection connection;
	private File configFile;
	private String createDetails;
	private static Logger logger;
	public Processor(Connection con, File file) {
		this.connection=con;
		this.configFile=file;
		logger = Logger.getLogger("com.termmed.statistics.Processor");
	}
	public void execute() throws IOException, Exception {
		logger.info("Starting report execution");
		XMLConfiguration xmlConfig;
		try {
			xmlConfig=new XMLConfiguration(configFile);
		} catch (ConfigurationException e) {
			logger.info("ClassificationRunner - Error happened getting params configFile." + e.getMessage());
			throw e;
		}
		createDetails=xmlConfig.getString("createDetailReports");
		List<HierarchicalConfiguration> fields = xmlConfig
				.configurationsAt("reports");

		for (HierarchicalConfiguration sub : fields) {
			Iterator iter = sub.getKeys();
			while(iter.hasNext()){
				String report=(String) iter.next();
				String value=sub.getString(report);

				if (value.toLowerCase().equals("true")){
					logger.info("Getting report config for " + report);
					ReportConfig reportCfg=ResourceUtils.getReportConfig(report);
					logger.info("Executing report " + report);

					executeReport(reportCfg);

					logger.info("Writing report " + report);
					writeReports(reportCfg);
				}
				//				System.out.println(report + " " + value);
			}
		}
	}

	private void writeReports(ReportConfig reportCfg) throws Exception {

		if (reportCfg.getOutputFile()!=null){
			for(OutputFileTableMap tableMap:reportCfg.getOutputFile()){
				BufferedWriter bw=getWriter(tableMap.getFile() + (tableMap.getFile().toLowerCase().endsWith(".csv")?"":".csv"));
				addHeader(bw,tableMap);
				printReport(bw,tableMap);
				bw.close();
				bw=null;
			}
		}
		if (createDetails.toLowerCase().equals("true")){
			if (reportCfg.getOutputDetailFile()!=null){
				for(OutputDetailFile detail:reportCfg.getOutputDetailFile()){
					BufferedWriter bw=getWriter(detail.getFile() + (detail.getFile().toLowerCase().endsWith(".csv")?"":".csv"));
					addHeader(bw,detail);
					printReport(bw,detail);
					bw.close();
					bw=null;
				}
			}
		}
	}

	private void printReport(BufferedWriter bw, OutputDetailFile detail) throws Exception {

		SQLStatementExecutor executor=new SQLStatementExecutor(connection);

		for (StoredProcedure sProc:detail.getStoredProcedure()){
			executor.executeStoredProcedure(sProc, ImportManager.params, null);
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
		executor=null;		
	}
	private void addHeader(BufferedWriter bw, OutputDetailFile detail) throws IOException {
		bw.append(detail.getReportHeader());
		bw.append("\r\n");

	}
	private void addHeader(BufferedWriter bw, OutputFileTableMap tableMap) throws IOException {
		bw.append(tableMap.getReportHeader());
		bw.append("\r\n");
	}

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

	private BufferedWriter getWriter(String outFile) throws UnsupportedEncodingException, FileNotFoundException {

		FileOutputStream tfos = new FileOutputStream( outFile);
		OutputStreamWriter tfosw = new OutputStreamWriter(tfos,"UTF-8");
		return new BufferedWriter(tfosw);

	}

	private void executeReport(ReportConfig reportCfg) throws Exception {

		SQLStatementExecutor executor=new SQLStatementExecutor(connection);
		executor.executeStoredProcedure(reportCfg.getStoredProcedure(), ImportManager.params, null);
		executor=null;
	}
}
