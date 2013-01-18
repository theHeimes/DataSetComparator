package de.thiss.datasetcomparator.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import de.thiss.datasetcomparator.ComparisonSetLoader;

public class ComparisonSetLoaderImpl implements ComparisonSetLoader {

	ResultSet comparisonSet;
	String sqlQueryText;
	String dataBaseConnectionString;
	
	
	public ComparisonSetLoaderImpl(String databaseConnectionString, String sqlQueryText) {
		this.sqlQueryText = sqlQueryText;
		this.dataBaseConnectionString = databaseConnectionString;
	}

	@Override
	public ResultSet initializeComparisonData() {
		try {
			Connection connection = DriverManager.getConnection( dataBaseConnectionString );
			Statement sqlStatement = connection.createStatement();
			comparisonSet = sqlStatement.executeQuery( sqlQueryText );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comparisonSet;
	}

	

}
