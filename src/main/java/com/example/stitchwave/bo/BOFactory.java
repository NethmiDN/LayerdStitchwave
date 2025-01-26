package com.example.stitchwave.bo;

import com.example.stitchwave.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}
    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() :boFactory;
    }

    public enum BOType{
        USER,CUSTOMER,EMPLOYEE,PAYMENT,SUPPLIER
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case USER:
                return new UserBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            default:
                return null;
        }
    }
}
