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
package com.termmed.statistics.db.setup;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import com.termmed.utils.FileHelper;
import com.termmed.utils.ResourceUtils;
import com.termmed.utils.SQLStatementExecutor;


// TODO: Auto-generated Javadoc
/**
 * The Class DbSetup.
 */
public class DbSetup {


	/** The con. */
	private Connection con;

	/**
	 * Instantiates a new db setup.
	 *
	 * @param con the con
	 */
	public DbSetup(Connection con) {
		super();
		this.con = con;
	}

	/**
	 * Execute.
	 *
	 * @throws Exception the exception
	 */
	public void execute() throws Exception{
		String relativePath="src/main/resources/";
		if( new File(relativePath).isDirectory() ){

			String path=relativePath + "org/ihtsdo/statistics/db/setup/table";
			setFromFileSystem(path);
			path=relativePath + "org/ihtsdo/statistics/db/setup/storedprocedure";
			setFromFileSystem(path);
			path=relativePath + "org/ihtsdo/statistics/db/setup/index";
			setFromFileSystem(path);

		}else{
//			String path="table/";
			String path="org/ihtsdo/statistics/db/setup/table";
			setFromResources(path);
			path="org/ihtsdo/statistics/db/setup/storedprocedure";
			setFromResources(path);
			path="org/ihtsdo/statistics/db/setup/index";
			setFromResources(path);
		}

	}


	/**
	 * Recreate path.
	 *
	 * @param resourcePath the resource path
	 * @throws Exception the exception
	 */
	public void recreatePath(String resourcePath) throws Exception{
		String relativePath="src/main/resources/";
		if( new File(relativePath).isDirectory() ){

			String path=relativePath + resourcePath;
			setFromFileSystem(path);

		}else{
			setFromResources(resourcePath);
		}

	}
	
	/**
	 * Sets the from file system.
	 *
	 * @param path the new from file system
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 * @throws SQLException the sQL exception
	 */
	private void setFromFileSystem(String path) throws IOException, Exception,
			SQLException {
		Collection<File>list = ResourceUtils.getFileSystemScripts(path);
		SQLStatementExecutor exec=new SQLStatementExecutor(con);
		for(File file:list){

			if (file.getAbsolutePath().indexOf("/.")>-1 || file.getName().startsWith(".")){
				continue;
			}
			String script=FileHelper.getTxtFileContent(file);
			System.out.println("file:" + file.getName());
			exec.executeStatement(script, null);
		}
	}

	/**
	 * Sets the from resources.
	 *
	 * @param path the new from resources
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 * @throws SQLException the sQL exception
	 */
	private void setFromResources(String path) throws IOException, Exception,
			SQLException {

		Collection<String>list = ResourceUtils.getResourceScripts(path);
		SQLStatementExecutor exec=new SQLStatementExecutor(con);
		for(String file:list){
			if (file.indexOf("/.")>-1 || file.startsWith(".")){
				continue;
			}
			String script;
			if (file.indexOf("/")>-1){
				script=FileHelper.getTxtResourceContent(file);
			}else{
				script=FileHelper.getTxtResourceContent(path + "/" + file);
			}
			exec.executeStatement(script, null);
		}
	}

}
