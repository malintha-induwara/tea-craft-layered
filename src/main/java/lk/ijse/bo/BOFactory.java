package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory bOFactory;
    private BOFactory(){}

    public static BOFactory getInstance(){
        return (bOFactory==null)?bOFactory=new BOFactory():bOFactory;
    }

    public enum BOTypes{
        CUSTOMER,USER,ATTENDANCE,EMPLOYEE,SUPPLIER,SALARY,FERTILIZER,PACKAGING,FERTILIZER_ORDER,PAYMENTS,TEA_ORDER,TEA_LEAVES_STOCK,TEA_BOOK,TEA_BOOK_TYPE,PROCESSING,TEA_TYPE,PACKAGING_DETAILS,TEA_CRAFT_DETAIL;
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
            case PAYMENTS:
                return new PaymentBOImpl();
            case TEA_LEAVES_STOCK:
                return new TeaLeavesStockBOImpl();
            case TEA_BOOK:
                return new TeaBookBOImpl();
            case TEA_BOOK_TYPE:
                return new TeaBookTypeBOImpl();
            case PROCESSING:
                return new ProcessingBOImpl();
            case TEA_TYPE:
                return new TeaTypeBOImpl();
            case PACKAGING_DETAILS:
                return new PackagingDetailsBOImpl();
            case TEA_CRAFT_DETAIL:
                return new TeaCraftDetailBOImpl();
            case TEA_ORDER:
                return new TeaOrderBOImpl();
            default:
                return null;
        }
    }




}

