package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.TeaBookBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.TeaBookDAO;
import lk.ijse.dto.TeaBookDto;
import lk.ijse.entity.TeaBook;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaBookBOImpl implements TeaBookBO {

    TeaBookDAO teaBookDAO = (TeaBookDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_BOOK);

    @Override
    public boolean updateTeaBookAmount(String teaBookId, double amount) throws SQLException {
        return teaBookDAO.updateTeaBookAmount(teaBookId,amount);
    }

    @Override
    public String getTeaBookId(String date) throws SQLException {
        return teaBookDAO.getTeaBookId(date);
    }

    @Override
    public String getTeaBookDate(String teaBookId) throws SQLException {
        return teaBookDAO.getTeaBookDate(teaBookId);
    }

    @Override
    public boolean searchDate(String date) throws SQLException {
        return teaBookDAO.searchDate(date);
    }

    @Override
    public boolean createTeaBookRecord(String date) throws SQLException {
        return teaBookDAO.createTeaBookRecord(date);
    }

    @Override
    public String generateNextTeaBookId() throws SQLException {
        return teaBookDAO.generateID();
    }

    @Override
    public double getAmount(String string) throws SQLException {
        return teaBookDAO.getAmount(string);
    }

    @Override
    public List<TeaBookDto> getAllTeaBookDetails() throws SQLException {
        List<TeaBook> allTeaBookDetails = teaBookDAO.getAll();
        List<TeaBookDto> allTeaBookDto = new ArrayList<>();
        for (TeaBook teaBook : allTeaBookDetails){
            allTeaBookDto.add(new TeaBookDto(
                    teaBook.getTeaBookId(),
                    teaBook.getDailyAmount(),
                    teaBook.getDate()
            ));
        }
        return allTeaBookDto;
    }
}

