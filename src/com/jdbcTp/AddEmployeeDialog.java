package com.jdbcTp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

public class AddEmployeeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField FirstNameTextField;
	private JTextField salaryTextField;
	private JTextField departementTextField;
	private JTextField emailTextField;
	private JTextField lastNameTextField;

	EmployeeDAO employeeDAO;
	EmployeeSearchApp employeeSearchApp;

	public AddEmployeeDialog(EmployeeDAO employeeDAO,
			EmployeeSearchApp employeeSearchApp) {
		this();
		this.employeeDAO = employeeDAO;
		this.employeeSearchApp = employeeSearchApp;
	}

	/**
	 * Create the dialog.
	 */
	public AddEmployeeDialog() {
		setTitle("Add Employees");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("First Name");
			lblNewLabel.setBounds(58, 22, 51, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			FirstNameTextField = new JTextField();
			FirstNameTextField.setBounds(115, 19, 324, 20);
			contentPanel.add(FirstNameTextField);
			FirstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			lblLastName.setBounds(59, 48, 50, 14);
			contentPanel.add(lblLastName);
		}
		{
			lastNameTextField = new JTextField();
			lastNameTextField.setBounds(115, 45, 324, 20);
			contentPanel.add(lastNameTextField);
			lastNameTextField.setColumns(10);
		}
		{
			JLabel lblEmail = new JLabel("Email");
			lblEmail.setBounds(85, 74, 24, 14);
			contentPanel.add(lblEmail);
		}
		{
			emailTextField = new JTextField();
			emailTextField.setBounds(115, 71, 324, 20);
			contentPanel.add(emailTextField);
			emailTextField.setColumns(10);
		}
		{
			JLabel lblDepartement = new JLabel("Departement");
			lblDepartement.setBounds(46, 100, 63, 14);
			contentPanel.add(lblDepartement);
		}
		{
			departementTextField = new JTextField();
			departementTextField.setBounds(115, 97, 324, 20);
			contentPanel.add(departementTextField);
			departementTextField.setColumns(10);
		}
		{
			JLabel lblSalary = new JLabel("Salary");
			lblSalary.setBounds(79, 126, 30, 14);
			contentPanel.add(lblSalary);
		}
		{
			salaryTextField = new JTextField();
			salaryTextField.setBounds(115, 123, 324, 20);
			contentPanel.add(salaryTextField);
			salaryTextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						addEmployee();

					}

					private void addEmployee()  {
						
						try {
						String firstName = FirstNameTextField.getText();
						String lastName = lastNameTextField.getText();
						String email = emailTextField.getText();
						String departement = departementTextField.getText();
						Double salary = Double.valueOf(salaryTextField
								.getText());

						Employee emp = new Employee(null, lastName, firstName,
								email, departement, salary);
						
						setVisible(false);
						dispose();
						
						employeeSearchApp.refreshEmployees();
						
						JOptionPane.showMessageDialog(AddEmployeeDialog.this, "The Employee has been added sucessefly ! " , "INFO", JOptionPane.INFORMATION_MESSAGE);
						
							employeeDAO.addNewEmployee(emp);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(AddEmployeeDialog.this, "Erreur : "+e , "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);				
						dispose();}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
