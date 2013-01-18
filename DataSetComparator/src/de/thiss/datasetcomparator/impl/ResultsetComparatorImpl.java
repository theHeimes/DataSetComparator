package de.thiss.datasetcomparator.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import de.thiss.datasetcomparator.ResultObject;
import de.thiss.datasetcomparator.ResultsetComparator;

public class ResultsetComparatorImpl implements ResultsetComparator {

	HashMap<String, Double> referenceSet;
	HashMap<String, ComparationResultLoggerImpl> loggers;
	
	// Configuration setting
	double toleranceLowerBoundaryProportion;
	double toleranceUpperBoundaryProportion;
	// Log files
	
	// Allowed value difference/tolerance
	
	public ResultsetComparatorImpl( HashMap<String, Double> referenceSet, double epsilon ){
		// prepare log files
		 this.referenceSet = referenceSet;
		 this.loggers      = new HashMap<String, ComparationResultLoggerImpl>();
		 this.toleranceLowerBoundaryProportion   = 1 - epsilon;
		 this.toleranceUpperBoundaryProportion   = 1 + epsilon;
	}
	
	public void addLogger(ResultTypes resultType, ComparationResultLoggerImpl logger) {
		loggers.put( resultType.toString(), logger );
	}
	public void addLogger( ComparationResultLoggerImpl logger) {
		loggers.put( "DEFAULT", logger );
	}
	
	
	public void compareData( ResultSet comparisonSet) {
		try {
			int numberOfColumnsInResultSet = comparisonSet.getMetaData().getColumnCount();
			while( comparisonSet.next() ) {
				String key = KeyBuilder.buildKey(comparisonSet);
				//hol Value aus Zeile
				double value = comparisonSet.getDouble( numberOfColumnsInResultSet );	
				System.out.println( key + " ; " + comparisonSet.getDouble( numberOfColumnsInResultSet ) );
				// rufe vergleich auf
				ResultObject result = compareRecord(key, value);
				ResultTypes resultType = result.getResultType();
				if  ( !loggers.isEmpty() ) {
					switch( resultType ){
						case SUCCESS:
							loggers.get( "SUCCESS" ).logResult( result );
							break;
						case DIFFERENCE:
							loggers.get( "DIFFERENCE" ).logResult( result );
							break;
						case NOTFOUND:
							loggers.get( "NOTFOUND" ).logResult( result );
							break;
					}
				loggers.get( "DEFAULT" ).logResult( result );
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultObject compareRecord( String key, Double value) {
		
		// Look for key in referenceSet
		// If found and proportion of coparison value to reference value between epsilon +/- boundaries
		if ( referenceSet.containsKey(key) ) {
			double referenceValue = referenceSet.get( key );
			// order value is 0.0
			if ( referenceValue == 0.0 ) {
				if ( value == 0.0) {
					return new ResultObjectImpl( ResultTypes.SUCCESS, key, value, referenceValue);
				} else {
				return new ResultObjectImpl( ResultTypes.DIFFERENCE, key, value, referenceValue);
				}
			} else {
				double actualValueToReferenceValueProportion = value / referenceValue;
				System.out.println(actualValueToReferenceValueProportion);
				if ( actualValueToReferenceValueProportion >= toleranceLowerBoundaryProportion
				  && actualValueToReferenceValueProportion <= toleranceUpperBoundaryProportion ) {
					return new ResultObjectImpl( ResultTypes.SUCCESS, key, value, referenceValue);
				} else { // If found but value > epsilon log value difference
					return new ResultObjectImpl( ResultTypes.DIFFERENCE, key, value, referenceValue);
				}
			}
		} else { // If not found return failure
			return new ResultObjectImpl( ResultTypes.NOTFOUND, key, value, 0.0);
		}
		
	}

}
