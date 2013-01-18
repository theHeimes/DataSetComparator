package de.thiss.datasetcomparator;

import java.util.HashMap;

public interface ReferenceSetLoader {

	public HashMap<String, Double> initializeReferenceSet(); 
	public void setDatabaseConnectionString( String connectionString );
	public void setSqlQueryText( String sqlQueryText );
	
}
