package de.thiss.datasetcomparator.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Dimension;
import javax.swing.JButton;

import de.thiss.datasetcomparator.controller.Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class DataSetComparatorGUI {

	private Controller controller;
	
	private JFrame frame;
	private JTextField textFieldComparisonDataConnectionString;
	private JTextField textFieldReferenceDataConnectionString;
	private JPanel panelReferenceDataConfig;
	private JPanel panelComparisonDataConfig;
	private JButton btnConfigureReferenceDataConnectionString;
	private JButton btnConfigureComparisonDataConnectionString;
	private JTextPane textPaneComparisonDataSQLQuery;
	private JTextPane textPaneReferenceDataSQLQuery;
	private JButton btnLoadReferenceDataSQL;
	private JButton btnLoadComparisonDataSQL;
	private JButton btnCompareData;
	private JButton btnCloseApplication;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataSetComparatorGUI window = new DataSetComparatorGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the application.
	 */
	public DataSetComparatorGUI() {
		initialize();
		frame.setVisible( true );
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		controller = new Controller( this );
		frame = new JFrame();
		frame.setBounds(100, 100, 578, 478);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[]", "[][][]"));
		
		panelReferenceDataConfig = new JPanel();
		panelReferenceDataConfig.setBorder(new TitledBorder(null, "Reference Data Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panelReferenceDataConfig, "cell 0 0,grow");
		panelReferenceDataConfig.setLayout(new MigLayout("", "[76px][300px][118px]", "[30px][100px]"));
		
		JLabel lblReferenceDatabaseConnection = new JLabel("Connection:");
		panelReferenceDataConfig.add(lblReferenceDatabaseConnection, "cell 0 0,alignx left,aligny center");
		
		textFieldReferenceDataConnectionString = new JTextField();
		panelReferenceDataConfig.add(textFieldReferenceDataConnectionString, "cell 1 0,growx,aligny top");
		textFieldReferenceDataConnectionString.setColumns(10);
		
		btnConfigureReferenceDataConnectionString = new JButton("Configure...");
		btnConfigureReferenceDataConnectionString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConnectionStringBuilderGUI( controller, textFieldReferenceDataConnectionString );
			}
		});
		panelReferenceDataConfig.add(btnConfigureReferenceDataConnectionString, "cell 2 0,alignx left,aligny bottom");
		
		JLabel lblReferenceDataSQLQuery = new JLabel("SQL Query:");
		panelReferenceDataConfig.add(lblReferenceDataSQLQuery, "cell 0 1,alignx left,aligny top");
		
		
		textPaneReferenceDataSQLQuery = new JTextPane();
		textPaneReferenceDataSQLQuery.setMinimumSize(new Dimension(300, 100));
		
		JScrollPane refSqlScroller = new JScrollPane( textPaneReferenceDataSQLQuery );
		panelReferenceDataConfig.add(refSqlScroller, "cell 1 1,grow");
		//textPaneReferenceDataSQLQuery.setEditable( false );
		
		btnLoadReferenceDataSQL = new JButton("Load file...");
		btnLoadReferenceDataSQL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter( new FileNameExtensionFilter( "text/sql files (*.txt, *.sql)", "txt", "sql" ) );
				int option = fileChooser.showOpenDialog( frame );
				if ( option == JFileChooser.APPROVE_OPTION ) {
					File file = fileChooser.getSelectedFile();
					controller.handleFileInput( textPaneReferenceDataSQLQuery, file );
				}
			}
		});
		panelReferenceDataConfig.add(btnLoadReferenceDataSQL, "cell 2 1,alignx left,aligny top");
		
		panelComparisonDataConfig = new JPanel();
		panelComparisonDataConfig.setBorder(new TitledBorder(null, "Comparison Data Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panelComparisonDataConfig, "cell 0 1,grow");
		panelComparisonDataConfig.setLayout(new MigLayout("", "[76px][300px][118px]", "[30px][100px]"));
		
		JLabel lblComparisonDatabaseConnection = new JLabel("Connection:");
		panelComparisonDataConfig.add(lblComparisonDatabaseConnection, "cell 0 0,alignx left,aligny center");
		
		textFieldComparisonDataConnectionString = new JTextField();
		panelComparisonDataConfig.add(textFieldComparisonDataConnectionString, "cell 1 0,growx,aligny top");
		textFieldComparisonDataConnectionString.setColumns(10);
		
		btnConfigureComparisonDataConnectionString = new JButton("Configure...");
		btnConfigureComparisonDataConnectionString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConnectionStringBuilderGUI( controller, textFieldComparisonDataConnectionString );
			}
		});
		panelComparisonDataConfig.add(btnConfigureComparisonDataConnectionString, "cell 2 0,alignx left,aligny bottom");
		
		JLabel lblComparisonDataSQLQuery = new JLabel("SQL Query:");
		panelComparisonDataConfig.add(lblComparisonDataSQLQuery, "cell 0 1,alignx left,aligny top");
		
		textPaneComparisonDataSQLQuery = new JTextPane();
		textPaneComparisonDataSQLQuery.setMinimumSize(new Dimension(300, 100));
		panelComparisonDataConfig.add(textPaneComparisonDataSQLQuery, "cell 1 1,grow");
		//textPaneComparisonDataSQLQuery.setEditable( false );
		
		btnLoadComparisonDataSQL = new JButton("Load file...");
		btnLoadComparisonDataSQL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter( new FileNameExtensionFilter( "text/sql files (*.txt, *.sql)", "txt", "sql" ) );
				int option = fileChooser.showOpenDialog( frame );
				if ( option == JFileChooser.APPROVE_OPTION ) {
					File file = fileChooser.getSelectedFile();
					controller.handleFileInput( textPaneReferenceDataSQLQuery, file );
				}
			}
		});
		panelComparisonDataConfig.add(btnLoadComparisonDataSQL, "cell 2 1,alignx left,aligny top");
		
		btnCompareData = new JButton("Compare data");
		btnCompareData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnCloseApplication = new JButton("Close application");
		btnCloseApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit( 0 );
			}
		});
		frame.getContentPane().add(btnCloseApplication, "flowx,cell 0 2,alignx right");
		frame.getContentPane().add(btnCompareData, "cell 0 2,alignx right");
	}

	public void displayReferenceSetConnectionString(String connectionString) {
		textFieldReferenceDataConnectionString.setText( connectionString );
		frame.repaint();
	}

}
