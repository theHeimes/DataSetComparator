package de.thiss.datasetcomparator.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextPane;

import de.thiss.datasetcomparator.ReferenceSetLoader;
import de.thiss.datasetcomparator.gui.DataSetComparatorGUI;
import de.thiss.datasetcomparator.impl.ReferenceSetLoaderImpl;

public class Controller {
	
	private ReferenceSetLoader refSetLoader;
	private DataSetComparatorGUI dataSetComparatorGUI;
	
	public Controller( DataSetComparatorGUI dataSetComparatorGUI ) {
		this.refSetLoader = new ReferenceSetLoaderImpl();
		this.dataSetComparatorGUI = dataSetComparatorGUI;
	}

	public static String buildConnectionString( String vendorName, String serverName, String portNo, String dbName, String userName, char[] password ) {
		// TODO Auto-generated method stub
		String vendor = vendorName == "MS SQL Server" ? "sqlserver" : "mysql";
		String connectionString =
			"jdbc:" 		+ 
			vendor			+ "://" +
			serverName		+ ":" 	+
			portNo			+ ";"	+ 
			"databaseName=" + dbName   + ";"	+
			"user=" 		+ userName + ";"    +
			"password=" 	+ String.valueOf( password );
		System.out.println( connectionString );
		return connectionString;
	}
	
	public void setReferenceDataConnectionString( String vendorName, String serverName, String portNo, String dbName, String userName, char[] password ) {
		String connectionString = buildConnectionString( vendorName, serverName, portNo, dbName, userName, password );
		this.refSetLoader.setDatabaseConnectionString( connectionString );
		updateReferenceSetConnectionStringInGUI( connectionString );
	}
	
	public void updateReferenceSetConnectionStringInGUI( String connectionString ) {
		this.dataSetComparatorGUI.displayReferenceSetConnectionString( connectionString );
	}

	public void handleFileInput( JTextPane textPaneReferenceDataSQLQuery, File file) {
		//String fileName = file.getAbsolutePath();
		textPaneReferenceDataSQLQuery.setText( getTextFileContent( file ) );
	}
	
	public String getTextFileContent( File file ) {
		String textFileContent = "";
		try {
			FileReader fileReader = new FileReader( file );
			BufferedReader bufferedReader = new BufferedReader( fileReader );
			while ( true ) {
				String line = bufferedReader.readLine();
				if ( line != null ) {
					textFileContent += ( line + "\n" );
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
		return textFileContent;
	}

}
