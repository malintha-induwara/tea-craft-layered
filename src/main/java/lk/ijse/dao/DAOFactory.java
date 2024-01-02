package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}
    public static DAOFactory getInstance(){
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,USER,ATTENDANCE,EMPLOYEE,SUPPLIER,SALARY
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch(daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case USER:
                return new UserDAOImpl();
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            default:
                return null;
        }
    }





}

