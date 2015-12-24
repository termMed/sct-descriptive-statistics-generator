package com.termmed.statistics.db.setup;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import com.termmed.utils.FileHelper;
import com.termmed.utils.ResourceUtils;
import com.termmed.utils.SQLStatementExecutor;

public class DbSetup {


	private Connection con;

	public DbSetup(Connection con) {
		super();
		this.con = con;
	}

	public void execute() throws Exception{
		String relativePath="src2/main/resources/";
		if( new File(relativePath).isDirectory() ){

			String path=relativePath + "com/termmed/statistics/db/setup/table";
			setFromFileSystem(path);
			path=relativePath + "com/termmed/statistics/db/setup/storedprocedure";
			setFromFileSystem(path);
			path=relativePath + "com/termmed/statistics/db/setup/index";
			setFromFileSystem(path);

		}else{
			String path="com/termmed/statistics/db/setup/table";
			setFromResources(path);
			path="com/termmed/statistics/db/setup/storedprocedure";
			setFromResources(path);
			path="com/termmed/statistics/db/setup/index";
			setFromResources(path);
		}

	}

	
	private void setFromFileSystem(String path) throws IOException, Exception,
			SQLException {
		Collection<File>list = ResourceUtils.getFileSystemScripts(path);
		SQLStatementExecutor exec=new SQLStatementExecutor(con);
		for(File file:list){
			String script=FileHelper.getTxtFileContent(file);
			exec.executeStatement(script, null);
		}
	}

	private void setFromResources(String path) throws IOException, Exception,
			SQLException {
		Collection<String>list = ResourceUtils.getResourceScripts(path);
		SQLStatementExecutor exec=new SQLStatementExecutor(con);
		for(String file:list){
			if (file.startsWith(".")){
				continue;
			}
			String script=FileHelper.getTxtResourceContent(path + "/" + file);
			exec.executeStatement(script, null);
		}
	}

}
