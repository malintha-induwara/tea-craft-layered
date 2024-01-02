package lk.ijse.bo.custom.impl;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dto.SupplierDto;
import lk.ijse.entity.Supplier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);


    @Override
    public boolean saveSupplier(SupplierDto dto) throws SQLException {
        return  supplierDAO.save(new Supplier(
                dto.getSupId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAddress(),
                dto.getBank(),
                dto.getBankNo(),
                dto.getMobileNo()
        ));
    }

    @Override
    public String generateNextSupplierId() throws SQLException {
        return supplierDAO.generateID();
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException {
        return supplierDAO.delete(id);
    }

    @Override
    public List<SupplierDto> getAllSuppliers() throws SQLException {
        ArrayList<Supplier> supplierList = supplierDAO.getAll();
        ArrayList<SupplierDto> dtoList = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            dtoList.add(new SupplierDto(
                    supplier.getSupId(),
                    supplier.getFirstName(),
                    supplier.getLastName(),
                    supplier.getAddress(),
                    supplier.getBank(),
                    supplier.getBankNo(),
                    supplier.getMobileNo()
            ));
        }
        return dtoList;
    }

    @Override
    public SupplierDto searchSupplier(String supId) throws SQLException {
        Supplier supplier = supplierDAO.search(supId);
        if (supplier!=null){
            return new SupplierDto(
                    supplier.getSupId(),
                    supplier.getFirstName(),
                    supplier.getLastName(),
                    supplier.getAddress(),
                    supplier.getBank(),
                    supplier.getBankNo(),
                    supplier.getMobileNo()
            );
        }
        return null;
    }

    @Override
    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        return supplierDAO.update(new Supplier(
                supplierDto.getSupId(),
                supplierDto.getFirstName(),
                supplierDto.getLastName(),
                supplierDto.getAddress(),
                supplierDto.getBank(),
                supplierDto.getBankNo(),
                supplierDto.getMobileNo()
        ));
    }

    @Override
    public String getSupplierName(String supId) throws SQLException {
        return supplierDAO.getSupplierName(supId);
    }
}

