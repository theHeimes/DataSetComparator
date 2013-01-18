package de.thiss.datasetcomparator.impl;

import java.sql.ResultSet;

public class KeyBuilder {

	public static String buildKey( ResultSet resultSet ) {
		try {
			int numberOfColumnsInResultSet = resultSet.getMetaData().getColumnCount();
			String concatenatedKey = "";
			for ( int i = 1; i <= numberOfColumnsInResultSet - 1; i++ ) {
				String key = resultSet.getString( i ).trim();
				if ( i == numberOfColumnsInResultSet - 1 ) {
					concatenatedKey += key;
				} else {
					concatenatedKey += ( key + "|" );
				}
			}	
			return concatenatedKey;
		} catch ( Exception e) {
			e.printStackTrace();
		}
		throw new UnsupportedOperationException();
	}
	
}
