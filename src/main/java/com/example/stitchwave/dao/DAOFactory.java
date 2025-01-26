package com.example.stitchwave.dao;

import com.example.stitchwave.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOType{
        USER,CUSTOMER,EMPLOYEE,PAYMENT,SUPPLIER
    }
    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case USER:
                return new UserDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            default:
                return null;
        }
    }
}
