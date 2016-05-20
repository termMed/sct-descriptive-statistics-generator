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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.termmed.statistics.runner.ProcessLogger;
import com.termmed.utils.FileHelper;
import com.termmed.utils.SQLStatementExecutor;


// TODO: Auto-generated Javadoc
/**
 * The Class TestSql.
 */
public class TestSql {

	/**
	 * Test queries.
	 *
	 * @param con the con
	 * @throws Exception the exception
	 */
	public void testQueries(Connection con) throws Exception{
		ProcessLogger logger = new ProcessLogger();
		//		logger.logInfo("execution of query 1"); 
		//		Long start=logger.startTime();

		SQLStatementExecutor exec=new SQLStatementExecutor(con);

		logger.logInfo("execution of preparement");
		Long start=logger.startTime();
//		String table="testtime";
//		getTableData(con ,table, 3, "eventDate");
		exec.executeStatement("truncate table testtime;", null);
		logger.endTime(start);
//				 exec.executeStatement("DROP TABLE IF EXISTS testtime",null);
//				
//				String statement="CREATE TABLE testtime ( " + 
//						"ID tinyint, " + 
//						"indicator varchar(100), " +
//						"eventDate TIMESTAMP " +  
//						");";
//				
//				exec.executeStatement(statement, null);
				String path="org/ihtsdo/statistics/db/setup/storedprocedure/createChangedConceptStatistics";
				String statement=FileHelper.getTxtResourceContent(path);
				exec.executeStatement(statement, null);
				logger.logInfo("End of execution of preparement");
		//
		//		exec.executeStatement(statement, null);
		//
		//		statement="CREATE INDEX relgroup_tmp_ix1 on relgroup_tmp (id ASC,RELATIONSHIPGROUP ASC, TYPEDESTIN ASC);";
		//		
		//		exec.executeStatement(statement, null);
		//
		//		statement="CREATE INDEX relgroup_tmp_ix2 on relgroup_tmp (TYPEDESTIN ASC);";
		//		exec.executeStatement(statement, null);
		//		
//		logger.logInfo("execution of query 1"); 
//
//		Long start=logger.startTime();
//
//		exec.executeStatement("truncate table inferredChanges;", null);
//		exec.executeStatement("create index s_relationships_ix3 on s_relationships(relationshipgroup asc);", null);
//		exec.executeStatement("create index s_relationships_pre_ix3 on s_relationships_pre(relationshipgroup asc);", null);
//		logger.endTime(start);
//		logger.logInfo("execution of query 2"); 
//		start=logger.startTime();
//		String statement="insert into inferredChanges " + 
//			"select distinct r.sourceid " +
//			"from s_relationships_pre r left join s_relationships rn on rn.sourceid=r.sourceid " +
//			"				and rn.relationshipgroup=r.relationshipgroup and rn.typeid=r.typeid " +
//			"				and rn.destinationid=r.destinationid " +
//			"where r.relationshipgroup=0 and rn.sourceid is null and not exists(select 0 from inferredChanges ic where ic.id=r.sourceid);"; 
//
//		exec.executeStatement(statement, null);
//
//		logger.endTime(start);
//
//		String table="relgroup_tmp";
//		tableSample(con ,table, 3);

//		logger.logInfo("execution of query 2"); 
//
//		start=logger.startTime();
//
//		exec.executeStatement("truncate table cptRootLevel_table4;", null);
//
//		statement="insert into cptRootLevel_table4 " +
//				"select d.conceptId as id, d.term , cast(0 as integer) as AnyChange, cast(0 as integer) as StatedDefChange " +
//				",cast(0 as integer) as InferredDefChange, cast(0 as integer) as Primitive2SD,cast(0 as integer) as SD2Primitive " +
//				",cast(0 as integer) as DescriptionChange,cast(0 as integer) as FSNChange  " +
//				"from s_descriptions d  " +
//				"where d.conceptId=138875005 " + 
//				"and d.active=1 " +
//				"and d.typeId=900000000000003001;" ;
//
//		exec.executeStatement(statement, null);
//
//		logger.endTime(start);
//
//		table="cptRootLevel_table4";
//		tableSample(con ,table, 1);
//
//		logger.logInfo("execution of query 3");
//
//		start=logger.startTime();
//
//		exec.executeStatement("truncate table prim2SD;", null);
//
//		statement="insert into prim2SD " +
//				"select pc.id  " +
//				"from s_concepts_pre pc " +
//				"where pc.definitionStatusId=900000000000074008 " +
//				"and exists(select 0  " +
//				"			from actConcepts ac " +
//				"			where ac.id=pc.id " +
//				"			and ac.definitionStatusid=900000000000073002); ";
//
//		exec.executeStatement(statement, null);
//
//		logger.endTime(start);
//
//		table="prim2SD";
//		tableSample(con ,table, 1);

		//		logger.logInfo("execution of query 4"); 
		//
		//		start=logger.startTime();
		//
		//		exec.executeStatement("truncate table relgroup_new;", null);
		//
		//		statement="insert into relgroup_new " + 
		//				"select r.id ,r.relationshipgroup," +
		//				"group_concat(r.typeDestin order by r.typeDestin)  " +
		//				"from relgroup_tmp r " +
		//				"group by r.id,r.relationshipgroup; " ;
		//
		//		exec.executeStatement(statement, null);
		//
		//		logger.endTime(start);
		//
		//		exec.executeStatement("truncate table relgroup_tmp;", null);
		//
		//
		//		logger.logInfo("execution of query 5"); 
		//		start=logger.startTime();
		//
		//		exec.executeStatement("truncate table inferredChanges;", null);
		//
		//
		//		statement="insert into inferredChanges " + 
		//				"select distinct id " +
		//				"from relgroup_new r1 " +
		//				"where not exists(select 0  " +
		//				"			from relgroup_old r2 " + 
		//				"			where r2.id=r1.id and r2.groupid=r1.groupid) " +
		//				"union " +
		//				"select distinct id " +
		//				"from relgroup_old r1 " +
		//				"where not exists(select 0 " + 
		//				"			from relgroup_new r2 " + 
		//				"			where r2.id=r1.id and r2.groupid=r1.groupid); ";
		//
		//		exec.executeStatement(statement, null);
		//
		//		logger.endTime(start);
		//
		//		exec.executeStatement("truncate table relgroup_new;", null);
		//		exec.executeStatement("truncate table relgroup_old;", null);



	}
	
	/**
	 * Test samples.
	 *
	 * @param con the con
	 * @throws Exception the exception
	 */
	public void testSamples(Connection con) throws Exception{
		ProcessLogger logger = new ProcessLogger();
		//		logger.logInfo("execution of query 1"); 
		//		Long start=logger.startTime();

		logger.logInfo("execution of preparement");

		logger.logInfo("execution of relgroup_tmp");
		String table="relgroup_tmp";
		tableSample(con ,table, 3);

		logger.logInfo("execution of actConcepts");
		table="actConcepts";
		tableSample(con ,table, 2);

		logger.logInfo("execution of cptRootLevel_table4");
		table="cptRootLevel_table4";
		tableSample(con ,table, 9);

		table="s_concepts";
		tableSample(con ,table, 1);

	}
	
	/**
	 * Table sample.
	 *
	 * @param con the con
	 * @param table the table
	 * @param columns the columns
	 * @throws Exception the exception
	 */
	private void tableSample(Connection con, String table, int columns) throws Exception {

		SQLStatementExecutor exec=new SQLStatementExecutor(con);

		exec.executeQuery("Select TOP 10 * from " + table, null);

		ResultSet rs=exec.getResultSet();

		while(rs.next()){
			for (int i =1;i<=columns;i++){
				System.out.print( rs.getObject(i).toString() + "\t") ;
			}
			System.out.println();
		}
		rs.close();
	}

	
	/**
	 * Gets the table data.
	 *
	 * @param con the con
	 * @param table the table
	 * @param columns the columns
	 * @param order the order
	 * @return the table data
	 * @throws Exception the exception
	 */
	private void getTableData(Connection con, String table, int columns, String order) throws Exception {

		SQLStatementExecutor exec=new SQLStatementExecutor(con);
		String fieldOrder="";
		if (order !=null){
			 fieldOrder=" order by " + order;
		}
		exec.executeQuery("Select * from " + table + fieldOrder, null);

		ResultSet rs=exec.getResultSet();

		while(rs.next()){
			for (int i =1;i<=columns;i++){
				System.out.print( rs.getObject(i).toString() + "\t") ;
			}
			System.out.println();
		}
		rs.close();
	}
	
	/**
	 * Execute sp.
	 *
	 * @param con the con
	 * @throws Exception the exception
	 */
	public void executeSP(Connection con) throws Exception{

		ProcessLogger logger = new ProcessLogger();
		logger.logInfo("execution of createChangedConceptStatistics");
		long start=logger.startTime();
		String sp="call createChangedConceptStatistics(20160131);";
		CallableStatement st = con.prepareCall(sp);

		st.execute();

		st.close();
		
		logger.endTime(start);
		logger.logInfo("execution of testtime");
		String table="testtime";
		getTableData(con ,table, 3, "eventDate");

//		logger.logInfo("execution of cptFirstLevel_table4");
//		table="cptFirstLevel_table4";
//		tableSample(con ,table, 9);
	}
}