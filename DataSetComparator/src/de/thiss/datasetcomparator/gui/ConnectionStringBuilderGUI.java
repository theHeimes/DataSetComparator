package de.thiss.datasetcomparator.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import de.thiss.datasetcomparator.controller.Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectionStringBuilderGUI {

	private Controller controller;
	
	private JFrame frame;
	private JPanel contentPane;
	private JComboBox comboBoxDatabaseVendor;
	private JTextField textFieldServerName;
	private JTextField textFieldPortNumber;
	private JTextField textFieldDatabaseName;
	private JTextField textFieldUserName;
	private JPasswordField passwordFieldUserPassword;

	
	/**
	 * Launch the application.
	 * @param textFieldReferenceDataConnectionString 
	 * @param  
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionStringBuilderGUI frame = new ConnectionStringBuilderGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
	public ConnectionStringBuilderGUI( Controller controller, JTextComponent textFieldReferenceDataConnectionString ) {
		initialize( controller, textFieldReferenceDataConnectionString );
		frame.setVisible( true );
	}
	

	/**
	 * Initialize the contents of thr frame.
	 */
	private void initialize ( Controller controller1 , JTextComponent textComponentToFill) {
		
		this.controller = controller1;
		
		frame = new JFrame();
		frame.setTitle("Configure Database Connection");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][]"));
		
		JLabel lblDatabaseVendor = new JLabel("Database Vendor:");
		contentPane.add(lblDatabaseVendor, "cell 0 0,alignx trailing,aligny center");
		
		comboBoxDatabaseVendor = new JComboBox();
		comboBoxDatabaseVendor.setModel(new DefaultComboBoxModel( new String[] {"MS SQL Server", "MySQL"}));
		contentPane.add(comboBoxDatabaseVendor, "cell 1 0,growx");
		
		JLabel lblServerName = new JLabel("Server Name:");
		contentPane.add(lblServerName, "cell 0 1,alignx left,aligny center");
		
		textFieldServerName = new JTextField();
		contentPane.add(textFieldServerName, "cell 1 1,growx");
		textFieldServerName.setColumns(10);
		
		JLabel lblPortNumber = new JLabel("Port Number:");
		contentPane.add(lblPortNumber, "cell 0 2,alignx left,aligny center");
		
		textFieldPortNumber = new JTextField();
		contentPane.add(textFieldPortNumber, "cell 1 2,growx");
		textFieldPortNumber.setColumns(10);
		
		JLabel lblDatabaseName = new JLabel("Database Name:");
		contentPane.add(lblDatabaseName, "cell 0 3,alignx left,aligny center");
		
		textFieldDatabaseName = new JTextField();
		contentPane.add(textFieldDatabaseName, "cell 1 3,growx");
		textFieldDatabaseName.setColumns(10);
		
		JLabel lblUserName = new JLabel("User Name:");
		contentPane.add(lblUserName, "cell 0 4,alignx left,aligny center");
		
		textFieldUserName = new JTextField();
		contentPane.add(textFieldUserName, "cell 1 4,growx");
		textFieldUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		contentPane.add(lblPassword, "cell 0 5,alignx left,aligny center");
		
		passwordFieldUserPassword = new JPasswordField();
		contentPane.add(passwordFieldUserPassword, "cell 1 5,growx");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				frame.setVisible( false );
			}
		});
		contentPane.add(btnCancel, "flowx,cell 1 7,alignx right");
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setReferenceDataConnectionString( comboBoxDatabaseVendor.getSelectedItem().toString(),
												textFieldServerName.getText(),
												textFieldPortNumber.getText(),
												textFieldDatabaseName.getText(), 
												textFieldUserName.getText(),
												passwordFieldUserPassword.getPassword() );
				frame.setVisible( false );
			}
		});
		contentPane.add(btnOk, "cell 1 7,alignx right");
	}
	

	
}
