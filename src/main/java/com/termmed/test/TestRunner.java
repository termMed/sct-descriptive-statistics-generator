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
package com.termmed.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import com.termmed.statistics.runner.ProcessLogger;
import com.termmed.utils.I_Constants;


// TODO: Auto-generated Javadoc
/**
 * The Class TestRunner.
 */
public class TestRunner {

	/** The logger. */
	private static ProcessLogger logger;

	/** The data folder. */
	static File dataFolder;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){

//		logger = Logger.getLogger("com.termmed.statistics.runner.Runner");
		logger=new ProcessLogger();
		if (args.length==0){
			logger.logInfo("Error happened getting params. Params file doesn't exist");
			System.exit(0);
		}
		Long start=logger.startTime();
		System.setProperty("textdb.allow_full_path", "true");
		Connection c;
		try {
			
			Class.forName("org.hsqldb.jdbcDriver");			
			logger.logInfo("Connecting to DB. This task can take several minutes... wait please.");
			c = DriverManager.getConnection("jdbc:hsqldb:file:" + I_Constants.DB_FOLDER, "sa", "sa");

			TestSql test =new TestSql();
			test.testQueries(c);
//			test.testSamples(c);
			test.executeSP(c);
			test=null;
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.endTime(start);
	}
	
}
