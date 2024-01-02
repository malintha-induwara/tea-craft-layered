package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory bOFactory;
    private BOFactory(){}

    public static BOFactory getInstance(){
        return (bOFactory==null)?bOFactory=new BOFactory():bOFactory;
    }

    public enum BOTypes{
        CUSTOMER,USER,ATTENDANCE,EMPLOYEE,SUPPLIER,SALARY,FERTILIZER,PACKAGING,FERTILIZER_ORDER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch(boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case USER:
                return new UserBOImpl();
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case FERTILIZER:
                return new FertilizerBOImpl();
            case PACKAGING:
                return new PackagingBOImpl();
            case FERTILIZER_ORDER:
                return new FertilizerOrderBOImpl();
            default:
                return null;
        }
    }




}

