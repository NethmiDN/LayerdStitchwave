package com.example.stitchwave.bo;

import com.example.stitchwave.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}
    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() :boFactory;
    }

    public enum BOType{
        USER,CUSTOMER,EMPLOYEE,PAYMENT,SUPPLIER,FABRIC,CLOTHES_ORDER_DETAIL,ORDERS,SEWN_CLOTHES_STOCK,STYLE,SUPPLIER_ORDER
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
            case FABRIC:
                return new FabricBOImpl();
            case CLOTHES_ORDER_DETAIL:
                return new ClothesOrderDetailBOImpl();
            case ORDERS:
                return new OrdersBOImpl();
            case SEWN_CLOTHES_STOCK:
                return new SewnClothesStockBOImpl();
            case STYLE:
                return new StyleBOImpl();
            case SUPPLIER_ORDER:
                return new SupplierOrderBOImpl();
            default:
                return null;
        }
    }
}
