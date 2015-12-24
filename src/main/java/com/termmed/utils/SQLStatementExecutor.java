package com.termmed.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.termmed.statistics.model.SpParam;
import com.termmed.statistics.model.StoredProcedure;

public class SQLStatementExecutor {

	private Connection con;
	private ResultSet resultSet;
	public SQLStatementExecutor(Connection con) throws Exception {
		this.con = con;
	}
	
	public boolean executeStatement(String statement, Integer queryTimeOut) throws SQLException {
		if (statement != null && statement.length() > 0) {
			Statement st = con.createStatement();
			
			if (queryTimeOut != null ) {			
				st.setQueryTimeout(queryTimeOut);
			}
			
			st.executeUpdate(statement);
			resultSet  = st.getResultSet();
			if (resultSet == null) {
				st.close();
			}
			
			return true;
		}
		
		return false;
	}
	public boolean executeQuery(String query, Integer queryTimeOut) throws SQLException {
		if (query != null && query.length() > 0) {
			Statement st = con.createStatement();
			
			if (queryTimeOut != null ) {			
				st.setQueryTimeout(queryTimeOut);
			}
			
			st.execute(query);
			resultSet  = st.getResultSet();
			if (resultSet == null) {
				st.close();
			}
			
			return true;
		}
		
		return false;
	}
	public boolean executeStoredProcedure(StoredProcedure sProcedure,HashMap<String,String> params, Integer queryTimeOut) throws SQLException{
		if (sProcedure != null ) {
			CallableStatement st = con.prepareCall("call " + sProcedure.getName());
			if (sProcedure.getParam()!=null){
				for(SpParam param:sProcedure.getParam()){
					if (param.getSQLType().compareTo("INT")==0){
						st.setInt(param.getOrder(), Integer.parseInt(params.get(param.getName())));
					}
					// TODO Add other types if necessary
				}
			}
			
			if (queryTimeOut != null ) {			
				st.setQueryTimeout(queryTimeOut);
			}
			
			st.execute();
			resultSet  = st.getResultSet();
			if (resultSet == null) {
				st.close();
			}
			
			return true;
		}
		
		return false;
		
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
}
