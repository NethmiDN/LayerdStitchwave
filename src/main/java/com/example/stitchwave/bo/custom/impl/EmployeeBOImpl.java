package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.EmployeeBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.custom.EmployeeDAO;
import com.example.stitchwave.dto.EmployeeDTO;
import com.example.stitchwave.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO{
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public boolean saveEmployee(EmployeeDTO DTO) throws SQLException, ClassNotFoundException {
        Employee employee = new Employee(DTO.getEmployee_id(), DTO.getName(), DTO.getContact());
        return employeeDAO.save(employee);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO DTO) throws SQLException, ClassNotFoundException {
        Employee employee = new Employee(DTO.getEmployee_id(), DTO.getName(), DTO.getContact());
        return employeeDAO.update(employee);
    }

    @Override
    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        String nextId = employeeDAO.getNextId();
        if (nextId != null) {
            int id = Integer.parseInt(nextId.substring(1)) + 1;
            return String.format("E%03d", id);
        } else {
            return "E001";
        }
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = (ArrayList<Employee>) employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmployee_id(employee.getEmployee_id());
            employeeDTO.setName(employee.getName());
            employeeDTO.setContact(employee.getContact());
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    @Override
    public ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAllIds();
    }

    @Override
    public Employee findByEmployeeId(String selectedId) throws SQLException, ClassNotFoundException {
        return employeeDAO.findById(selectedId);
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        employeeDAO.delete(id);
        return false;
    }

}
