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
        USER,CUSTOMER,EMPLOYEE,PAYMENT,SUPPLIER,FABRIC,CLOTHES_ORDER_DETAIL,ORDERS,SEWN_CLOTHES_STOCK,STYLE,SUPPLIER_ORDER,FABRIC_ORDER
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
            case FABRIC:
                return new FabricDAOImpl();
            case CLOTHES_ORDER_DETAIL:
                return new ClothesOrderDetailDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case SEWN_CLOTHES_STOCK:
                return new SewnClothesStockDAOImpl();
            case STYLE:
                return new StyleDAOImpl();
            case SUPPLIER_ORDER:
                return new SupplierOrderDAOImpl();
            case FABRIC_ORDER:
                return new FabricOrderDAOImpl();
            default:
                return null;
        }
    }
}
