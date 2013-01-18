package de.thiss.datasetcomparator.impl;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GUI {

	private JFrame frame;
	private Label label;
	private FileDialog dialog;
	private TextField field;
	private Button button;
	
	public GUI() {
		this.frame = new JFrame();
		frame.setLocation( 200, 200 );
		//frame.addWindowListener( new WindowCloseListener() );
		this.field = new TextField( "w" );
		field.addActionListener( new FileChoiceListener() );
		frame.add( field );
		this.label = new Label();
		frame.add( label );
		//this.dialog = new FileDialog();
		this.button = new Button("w");
	}
	
	class WindowCloseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if( event.getActionCommand() == "Close" ) {
				System.exit( 0 );
			}
		}
	}
	
	class FileChoiceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}
	
}
