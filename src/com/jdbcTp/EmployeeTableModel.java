package com.jdbcTp;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class EmployeeTableModel extends AbstractTableModel{

	private static final int LAST_NAME_COL =0;
	private static final int FIRST_NAME_COL =1;
	private static final int EMAIL_COL =2;
	private static final int DEPARTMENT_COL =3;
	private static final int SALARY_COL =4;
	
	private String[] tabeNames = {"Last Name","First Name","Email","Department","Salary"};
	private List<Employee> employees;
	
	public EmployeeTableModel(List<Employee> employees) {
		this.employees=employees;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return employees.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return tabeNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Employee e = employees.get(rowIndex);
		switch (columnIndex) {
		case LAST_NAME_COL: return e.getLastName();
		case FIRST_NAME_COL: return e.getFirstName();
		case EMAIL_COL: return e.getEmail();
		case DEPARTMENT_COL: return e.getDepartement();
		case SALARY_COL: return e.getSalary();
		}
		return null ;
	}
	
	@Override
	public String getColumnName(int column) {
		return tabeNames[column];
	}
	
	@Override
	public Class getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
}
