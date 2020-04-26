package com.jdbcTp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmployeeSearchApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField lastNameTextField;
	private JTable table;
	private EmployeeDAO employeeDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeSearchApp frame = new EmployeeSearchApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeSearchApp() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					employeeDAO.closeConnextion();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Error:" + e1, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		try {
			employeeDAO = new EmployeeDAO();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error:" + e, "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}

		setTitle("EmployeeSearchApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblSearchEmployee = new JLabel("Enter Last Name: ");
		panel.add(lblSearchEmployee);

		lastNameTextField = new JTextField();
		panel.add(lastNameTextField);
		lastNameTextField.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<Employee> employees = new ArrayList<Employee>();
					String text = lastNameTextField.getText();
					if (text.length() > 0) {
						employees = employeeDAO.searchEmployees(text);
					} else {
						employees = employeeDAO.getAllEmployees();
					}

					// System.out.println(employees);
					EmployeeTableModel employeeTableModel = new EmployeeTableModel(
							employees);
					table.setModel(employeeTableModel);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Error:" + e1, "ERROR:", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(btnSearch);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnAddNewEmployee = new JButton("Add New Employee");
		btnAddNewEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddEmployeeDialog addEmp = new AddEmployeeDialog(employeeDAO,
						EmployeeSearchApp.this);
				addEmp.setVisible(true);

			}
		});
		contentPane.add(btnAddNewEmployee, BorderLayout.SOUTH);
	}

	public void refreshEmployees() {
		try {
			 List<Employee> employees = employeeDAO.getAllEmployees();

			EmployeeTableModel dataModel = new EmployeeTableModel(employees);
			table.setModel(dataModel);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(EmployeeSearchApp.this,
					"Error:" + e, "ERROR:", JOptionPane.ERROR_MESSAGE);
		}

	}

}
