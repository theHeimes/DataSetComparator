package de.thiss.datasetcomparator.test;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;

import de.thiss.datasetcomparator.ComparisonSetLoader;
import de.thiss.datasetcomparator.ReferenceSetLoader;
import de.thiss.datasetcomparator.ResultsetComparator;
import de.thiss.datasetcomparator.gui.ConnectionStringBuilderGUI;
import de.thiss.datasetcomparator.impl.ComparationResultLoggerImpl;
import de.thiss.datasetcomparator.impl.ComparisonSetLoaderImpl;
import de.thiss.datasetcomparator.impl.GUI;
import de.thiss.datasetcomparator.impl.ReferenceSetLoaderImpl;
import de.thiss.datasetcomparator.impl.ResultTypes;
import de.thiss.datasetcomparator.impl.ResultsetComparatorImpl;
public class ComparatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//GUI gui = new GUI();
		//ConnectionStringGUI gui = new ConnectionStringGUI();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ConnectionStringBuilderGUI frame = new ConnectionStringBilderGUI();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		double epsilon = 0.1;
		
		// SQL Query
		String sqlStatementForReferenceData = 	"select top 10000 " +
												"f.OrderNumber" +
												",f.OrderLineNumber" +
												",ru.Code AS ReportingUnit" +
												",mdf.Code AS MDF" +
												",f.MatCodeType AS MaterialType" +
												//",f.ValueOrderCurrencyOrdered AS Value " +
												",f.ValueLocalCurrencyOrdered AS Value " +
												//",f.ValueUSDOrdered AS Value " +
												//",f.ValueInvoiceCurrencyInvoiced AS Value " +
												//",f.ValueLocalCurrencyInvoiced AS Value " +
												//",f.ValueUSDInvoiced AS Value " +
												//",f.BudgetOrderedValueUSD AS Value " +
												//",f.BudgetInvoicedValueUSD AS Value " +
												"from rs.Fact_Orderlines f " +
												"inner join rs.DIM_MDF mdf " +
												"on f.MDFID = mdf.ID " +
												"inner join rs.DIM_REPORTING_UNIT ru " +
												"on f.ReportingID = ru.ID " +
												//"where f.ValueLocalCurrencyOrdered != 0.0" +
												"";
		//System.out.println( sqlStatementForReferenceData );
		//sqlStatementForReferenceData = "select * from ReferenceData";
		sqlStatementForReferenceData = "";
		System.out.println( sqlStatementForReferenceData );
		try {
			FileReader file = new FileReader( new File( "./documents/queryForReferenceData.txt" ) );
			BufferedReader fileReader = new BufferedReader( file );
			while ( true ) {
				String line = fileReader.readLine();
				if ( line != null ) {
					sqlStatementForReferenceData += line;
				} else {
					fileReader.close();
					break;
				}
			}
			//System.out.println( "Hier ist das Ergebnis: " + sqlStatementForReferenceData );
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		//String databaseConnectionString = "jdbc:sqlserver://de-s-0129433.de.abb.com:1433;databaseName=eSMART_RS_Active;" +
		//								  "user=mis_dm;password=abb_2011";
		//databaseConnectionString = "jdbc:mysql://localhost/SCMIS_Test?user=root&password=!Gold007!";
		String databaseConnectionStringForReferenceData = "";
		try {
			FileReader file = new FileReader( new File( "./documents/databaseConnectionStringforReferenceData.txt" ) );
			BufferedReader fileReader = new BufferedReader( file );
			while ( true ) {
				String line = fileReader.readLine();
				if ( line != null ) {
					databaseConnectionStringForReferenceData += line;
				} else {
					fileReader.close();
					break;
				}
			}
			//System.out.println( "Hier ist das Ergebnis: " + databaseConnectionStringForReferenceData );
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		// Load the referenceSet against which another comparisonData is compared
		ReferenceSetLoader refSetLoader = new ReferenceSetLoaderImpl( databaseConnectionStringForReferenceData, sqlStatementForReferenceData );
		HashMap<String, Double> referenceSet = refSetLoader.initializeReferenceSet();
		
		// Create the Comparator object
		ResultsetComparator resultSetComp = new ResultsetComparatorImpl( referenceSet, epsilon );

		System.out.println( "Epsilon:" + epsilon );
		System.out.println( "Lower Bound:" + ( 1 - epsilon ) );
		System.out.println( "Upper Bound:" + ( 1 + epsilon ) );
		
		
		
		String databaseConnectionStringForComparisonData = "";
		try {
			FileReader file = new FileReader( new File( "./documents/databaseConnectionStringforComparisonData.txt" ) );
			BufferedReader fileReader = new BufferedReader( file );
			while ( true ) {
				String line = fileReader.readLine();
				if ( line != null ) {
					databaseConnectionStringForComparisonData += line;
				} else {
					fileReader.close();
					break;
				}
			}
			//System.out.println( "Hier ist das Ergebnis: " + databaseConnectionStringForComparisonData );
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		String sqlStatementForComparisonData = "";
		try {
			FileReader file = new FileReader( new File( "./documents/queryForComparisonData.txt" ) );
			BufferedReader fileReader = new BufferedReader( file );
			while ( true ) {
				String line = fileReader.readLine();
				if ( line != null ) {
					sqlStatementForComparisonData += line;
				} else {
					fileReader.close();
					break;
				}
			}
			//System.out.println( "Hier ist das Ergebnis: " + sqlStatementForComparisonData );
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		// Load the comaparisonSet which is to be compared to the referenceData
		ComparisonSetLoader compSetLoader = new ComparisonSetLoaderImpl( databaseConnectionStringForComparisonData, sqlStatementForComparisonData );
		ResultSet comparisonData = compSetLoader.initializeComparisonData();

		// Add loggers (with error/success type and file path)
		resultSetComp.addLogger( ResultTypes.SUCCESS, new ComparationResultLoggerImpl("documents/successLog.txt") );
		resultSetComp.addLogger( ResultTypes.DIFFERENCE, new ComparationResultLoggerImpl("documents/diffLog.txt") );
		resultSetComp.addLogger( ResultTypes.NOTFOUND, new ComparationResultLoggerImpl("documents/errorLog.txt") );
		resultSetComp.addLogger( new ComparationResultLoggerImpl("documents/log.txt") ); // default logger
	
		// compare referenceData to the comparisonData
		resultSetComp.compareData( comparisonData );
		
		
		System.out.println("Done");

	}
}

/*
SQLServerDataSource dataSource = new SQLServerDataSource();
dataSource.setUser("mis_dm");
ds.setPassword("abb_2012");
ds.setServerName("de-s-0129433.de.abb.com");
ds.setPortNumber(1433); 
ds.setDatabaseName("eSMART_RS_Active");
con = ds.getConnection();
*/
// Connection con = DriverManager.getConnection("jdbc:mysql://localhost/SCMIS_Test?user=root&password=!Gold007!");

/*
HashMap<String,Double> compareSet = new HashMap<String, Double>();
compareSet.put( "1-1-100000-1AA-R", 10.05 );
compareSet.put( "1-2-100000-2AB-D", 20.49 );
compareSet.put( "1-3-100000-1BB-I", 30.01 );
compareSet.put( "2-1-200000-1AA-R",  1.05 );
compareSet.put( "2-2-200000-3AA-D", 14.32 );
compareSet.put( "2-3-200000-2AB-D", 10.99 );
compareSet.put( "3-1-300000-1AA-R",  1.01 );
compareSet.put( "3-2-300000-3BB-I", 21.43 );
*/
