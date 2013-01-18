package de.thiss.datasetcomparator;

import java.sql.ResultSet;

import de.thiss.datasetcomparator.impl.ComparationResultLoggerImpl;
import de.thiss.datasetcomparator.impl.ResultTypes;


public interface ResultsetComparator {
	
	public void compareData( ResultSet comparisonData );
	
	public ResultObject compareRecord( String key, Double value );
	
	public void addLogger(ResultTypes resultType, ComparationResultLoggerImpl logger);
	
	public void addLogger( ComparationResultLoggerImpl logger);
	
}
