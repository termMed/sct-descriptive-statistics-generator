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
package com.termmed.statistics.db.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.termmed.statistics.db.importer.ImportManager.TABLE;
import com.termmed.statistics.runner.ProcessLogger;



// TODO: Auto-generated Javadoc
/**
 * The Class Importer.
 */
public class Importer {

	/** The importlogger. */
	ProcessLogger importlogger = new ProcessLogger();
	
	/**
	 * Load file to database.
	 *
	 * @param filename the filename
	 * @param table the table
	 * @param con the con
	 * @param queryTimeOut the query time out
	 * @throws SQLException the sQL exception
	 * @throws FileNotFoundException the file not found exception
	 */
	public void loadFileToDatabase(File filename , TABLE table,Connection con, Integer queryTimeOut) throws SQLException, FileNotFoundException  {
		
		
		String fileName = filename.toString().replace('\\', '/');
		String tablename=table.getTableName();
		importlogger.logInfo("loading file :" + filename + " to table " + tablename);
		long startTime = importlogger.startTime();	
		Statement statement = (java.sql.Statement)con.createStatement();
		// Setting queryTimeOut
		if (queryTimeOut != null) {			
			statement.setQueryTimeout(queryTimeOut);
		}
		
//		String query ="TRUNCATE TABLE " + tablename ;
//		statement.executeUpdate(query);

//		query="drop table if exists tmp_import;" ;
//		statement.executeUpdate(query);
//		
//		query="create text table tmp_import " + tabledef + ";";  

//		statement.executeUpdate(query);
		
		String query="set table " + tablename +
	    " source '" + fileName + ";textdb.allow_full_path=true;ignore_first=true;quoted=false;fs=\\t;encoding=UTF-8;cache_rows=10000;cache_size=10000' DESC";
		statement.executeUpdate(query);

//		String fields=table.getFields();
//		if (fields==null || fields.equals("")){
//			fields="*";
//		}
//		query="INSERT INTO " + tablename + "  select " + fields + " from tmp_import;"; 
//		statement.execute(query);

//		query="SET TABLE tmp_import SOURCE OFF";
//		statement.executeUpdate(query);
//		
//		query="drop table tmp_import;";
//		statement.executeUpdate(query);

//		String commitText = "commit";
//		statement.execute(commitText);
		statement.close();

		System.gc();
		importlogger.endTime(startTime);
		
	}
	

	/**
	 * Table exists.
	 *
	 * @param table the table
	 * @param con the con
	 * @return true, if successful
	 */
	public boolean tableExists(TABLE table,Connection con)  {
		try {
			Statement statement = (java.sql.Statement)con.createStatement();
			statement.execute("select top 1 0 from " + table.getTableName());
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
}
