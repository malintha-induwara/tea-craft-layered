package lk.ijse.bo;

import lk.ijse.bo.custom.impl.CustomerBOImpl;

public class BOFactory {

    private static BOFactory bOFactory;
    private BOFactory(){}

    public static BOFactory getInstance(){
        return (bOFactory==null)?bOFactory=new BOFactory():bOFactory;
    }

    public enum BOTypes{
        CUSTOMER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch(boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            default:
                return null;
        }
    }




}

