package lk.ijse.teacraft.dao;

import lk.ijse.teacraft.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}
    public static DAOFactory getInstance(){
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        ATTENDANCE,CUSTOMER,EMPLOYEE,FERTILIZER,PACKAGING, FERTILIZER_ORDER,FERTILIZER_ORDER_DETAIL,PACKAGING_DETAILS,PAYMENTS,SALARY,SUPPLIER,TEA_ORDER,TEA_BOOK,TEA_BOOK_TYPE,TEA_LEAVES_STOCK,TEA_TYPE,TEA_ORDER_DETAIL,TEA_CRAFT_DETAIL,USER
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch(daoTypes){
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case FERTILIZER:
                return new FertilizerDAOImpl();
            case PACKAGING:
                return new PackagingDAOImpl();
            case PACKAGING_DETAILS:
                return new PackagingDetailsDAOImpl();
            case PAYMENTS:
                return new PaymentsDAOImpl();
            case FERTILIZER_ORDER:
                return new FertilizerOrderDAOImpl();
            case FERTILIZER_ORDER_DETAIL:
                return new FertilizerOrderDetailDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case TEA_ORDER:
                return new TeaOrderDAOImpl();
            case TEA_BOOK:
                return new TeaBookDAOImpl();
            case TEA_BOOK_TYPE:
                return new TeaBookTypeDAOImpl();
            case TEA_LEAVES_STOCK:
                return new TeaLeavesStockDAOImpl();
            case TEA_TYPE:
                return new TeaTypeDAOImpl();
            case TEA_CRAFT_DETAIL:
                return new TeaCraftDetailDAOImpl();
            case TEA_ORDER_DETAIL:
                return new TeaOrderDetailDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}

