package de.thiss.datasetcomparator.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.thiss.datasetcomparator.ComparationResultLogger;
import de.thiss.datasetcomparator.ResultObject;

public class ComparationResultLoggerImpl implements ComparationResultLogger {

	
	
	private FileWriter file;
	
	public ComparationResultLoggerImpl( String path ){
		// open file 
		try { 
			file = new FileWriter( new File( path ));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void finalize(){
		// close file stream
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void logResult( ResultObject result ) {
		try {
			file.write( result.resultToString() + "\n" );
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
