package com.example.stitchwave.bo;

import com.example.stitchwave.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}
    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() :boFactory;
    }

    public enum BOType{
        USER
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
