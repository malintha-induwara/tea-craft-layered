package lk.ijse.dao;

import lk.ijse.dao.custom.impl.AttendanceDAOImpl;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}
    public static DAOFactory getInstance(){
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,USER,ATTENDANCE
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch(daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case USER:
                return new UserDAOImpl();
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            default:
                return null;
        }
    }





}

