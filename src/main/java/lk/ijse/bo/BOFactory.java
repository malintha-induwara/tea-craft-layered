package lk.ijse.bo;

import lk.ijse.bo.custom.impl.AttendanceBOImpl;
import lk.ijse.bo.custom.impl.CustomerBOImpl;
import lk.ijse.bo.custom.impl.UserBOImpl;

public class BOFactory {

    private static BOFactory bOFactory;
    private BOFactory(){}

    public static BOFactory getInstance(){
        return (bOFactory==null)?bOFactory=new BOFactory():bOFactory;
    }

    public enum BOTypes{
        CUSTOMER,USER,ATTENDANCE
    }

    public SuperBO getBO(BOTypes boTypes){
        switch(boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case USER:
                return new UserBOImpl();
            case ATTENDANCE:
                return new AttendanceBOImpl();
            default:
                return null;
        }
    }




}

