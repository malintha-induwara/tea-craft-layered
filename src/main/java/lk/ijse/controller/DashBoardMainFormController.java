package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.AttendanceBO;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.entity.TeaBook;
import lk.ijse.entity.TeaTypes;
import lk.ijse.model.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DashBoardMainFormController {



    @FXML
    private AnchorPane mainPane;

    @FXML
    private Text txtAttendance;

    @FXML
    private Text txtDate;

    @FXML
    private Text txtLeafStock;

    @FXML
    private Text txtOrders;

    @FXML
    private Text txtTime;

    @FXML
    private Text txtUser;

    @FXML
    private AreaChart<?, ?> chartTeaStock;

    @FXML
    private PieChart chartProduction;



    private final TeaBookModel teaBookModel=new TeaBookModel();

    private final TeaOrderModel teaOrderModel=new TeaOrderModel();

    private final TeaTypeModel teaTypeModel=new TeaTypeModel();

    private final AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ATTENDANCE);

    private final PackagingDetailsModel packagingDetailsModel = new PackagingDetailsModel();


    public void initialize() {
       updateTime();
       updateDate();
       createTeaBookRecord();
       setDashBoardTextFields();
       setupPieChart();
       setUpAreaChart();
       setUserName();
       
    }

    private void setUserName() {
        txtUser.setText(UserBOImpl.userName);
    }

    private void setDashBoardTextFields() {

        try{
            int amount= (int) teaBookModel.getAmount(String.valueOf(LocalDate.now()));
            txtLeafStock.setText(amount+" Kg");

            int orderCount=teaOrderModel.getOrderCount(String.valueOf(LocalDate.now()));
            txtOrders.setText(String.valueOf(orderCount));

            int attendanceCount= attendanceBO.getAttendanceCount(String.valueOf(LocalDate.now()));
            txtAttendance.setText(String.valueOf(attendanceCount));


        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void setupPieChart() {

        double blackTea =0;
        double greenTea =0;
        double oolongTea =0;
        try {

            List<TeaTypes> dto = teaTypeModel.getAllTeaTypes();

            blackTea= dto.get(0).getAmount()-packagingDetailsModel.getTotalDecreasedAmount(dto.get(0).getTypeId());
            greenTea= dto.get(1).getAmount()-packagingDetailsModel.getTotalDecreasedAmount(dto.get(1).getTypeId());
            oolongTea= dto.get(2).getAmount()-packagingDetailsModel.getTotalDecreasedAmount(dto.get(2).getTypeId());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Black ", blackTea),
                new PieChart.Data("Green ", greenTea),
                new PieChart.Data("Oolong ", oolongTea));


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(),data.pieValueProperty()," Kg"
                        )
                )
        );

        chartProduction.getData().addAll(pieChartData);



    }

    private void updateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtDate.setText(formattedDate);
    }

    private void setUpAreaChart() {
        XYChart.Series series = new XYChart.Series();

        try {
            List<TeaBook> dtoList = teaBookModel.getAllTeaBookDetails();

            //To limit for last 5 days

            int startIndex = Math.max(0, dtoList.size() - 7); // Ensure startIndex is not negative
            List<TeaBook> finaList = dtoList.subList(startIndex, dtoList.size());




            for (TeaBook dto : finaList) {

                //To Extract the Day Only
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(dto.getDate(), formatter);
                String day = String.valueOf(date.getDayOfMonth());

                series.getData().add(new XYChart.Data(day, dto.getDailyAmount()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

        chartTeaStock.getData().add(series);

    }


    private void updateTime() {
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            txtTime.setText(timeNow());

        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private String timeNow() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss a");
        return dateFormat.format(new Date()) ;
    }



    private void createTeaBookRecord() {
        try {
           boolean isExists=teaBookModel.searchDate(String.valueOf(LocalDate.now()));
           if (!isExists){
               teaBookModel.createTeaBookRecord(String.valueOf(LocalDate.now()));
           }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


}
