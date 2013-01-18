package de.thiss.datasetcomparator.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import de.thiss.datasetcomparator.ReferenceSetLoader;

public class ReferenceSetLoaderImpl implements ReferenceSetLoader {
	
	private HashMap<String, Double> referenceSet;

	private String sqlQueryText;
	private String databaseConnectionString;
	
	public ReferenceSetLoaderImpl( String connectionString, String sqlQueryText ) {
		this.sqlQueryText = sqlQueryText;
		this.databaseConnectionString = connectionString;
		referenceSet = new HashMap<String, Double>();
	}
	
	public ReferenceSetLoaderImpl() {
		referenceSet = new HashMap<String, Double>();
	}

	@Override
	public HashMap<String, Double> initializeReferenceSet() {		
		try {
			Connection connection = DriverManager.getConnection( databaseConnectionString );
			Statement sqlStatement = connection.createStatement();
			ResultSet resultSet = sqlStatement.executeQuery( sqlQueryText );
			
			while( resultSet.next() ) {
				// Concatenate a String holding the values of all key columns for each record
				// and put the resulting key and value (= by contract in the last column of the resultSet
				// in the referenceSet-HashMap
				int numberOfColumnsInResultSet = resultSet.getMetaData().getColumnCount();
				// hol Key aus zeile
				String key = KeyBuilder.buildKey(resultSet);
				referenceSet.put( key, resultSet.getDouble( numberOfColumnsInResultSet ) );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return referenceSet;
	}
	
	public void setDatabaseConnectionString( String connectionString ){
		this.databaseConnectionString = connectionString;
	}
	public void setSqlQueryText( String sqlQueryText ) {
		this.sqlQueryText = sqlQueryText;
	}
	
}