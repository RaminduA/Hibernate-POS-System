package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.tableModel.CustomerIncomeTM;
import model.tableModel.MovableItemTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.JasperUtil;

import java.sql.SQLException;

public class ViewReportsFormController {
    public AnchorPane pageContext;

    public Label lblMovableItem;
    public Label lblItemCode;
    public Label lblRemainingCount;
    public Label lblSoldCount;

    public JFXButton btnMostMovable;
    public JFXButton btnLeastMovable;

    public TableView<CustomerIncomeTM> tblCustomerIncomeList;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colTotalIncome;

    public JFXTextField customerSearchBar;
    
    ObservableList<CustomerIncomeTM> obList=FXCollections.observableArrayList();
    MovableItemTM leastMovable;
    MovableItemTM mostMovable;

    public void initialize(){
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colTotalIncome.setCellValueFactory(new PropertyValueFactory<>("totalIncome"));
        /*clearLabels();
        sortItems();
        viewAllRecords();
        setComboBoxes();*/

    }

    public void viewCustomerReports(ActionEvent actionEvent) {
        JasperUtil.sqlReport("Customer");
    }

    public void viewItemReports(ActionEvent actionEvent) {
        JasperUtil.sqlReport("Item");
    }

    public void viewOrderReports(ActionEvent actionEvent) {
        JasperUtil.sqlReport("Order");
    }

    public void viewOrderDetailReports(ActionEvent actionEvent) {
        JasperUtil.sqlReport("OrderDetail");
    }

    /*private void viewAllRecords() {
        try {
            ArrayList<Customer> customers=new CustomerManager().getAllCustomers();
            for(Customer cust: customers){
                obList.add(
                      new CustomerIncomeTM(
                              cust.getId(),
                              cust.getName(),
                              new OrderManager().getCustomerIncome(cust.getId())
                      )
                );
                tblCustomerIncomeList.setItems(obList);
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }*/

    public void selectMostMovableItem(ActionEvent actionEvent) {
        /*if(mostMovable!=null){
            lblMovableItem.setText("Most Movable Item");
            lblItemCode.setText("Item Code : " + mostMovable.getItemCode());
            lblRemainingCount.setText("Remaining Item Count : " + mostMovable.getRemainingCount());
            lblSoldCount.setText("Sold Item Count : " + mostMovable.getSoldCount());
        }*/
    }
    public void selectLeastMovableItem(ActionEvent actionEvent) {
        /*if(leastMovable!=null){
            lblMovableItem.setText("Least Movable Item");
            lblItemCode.setText("Item Code : " + leastMovable.getItemCode());
            lblRemainingCount.setText("Remaining Item Count : " + leastMovable.getRemainingCount());
            lblSoldCount.setText("Sold Item Count : " + leastMovable.getSoldCount());
        }*/
    }


}
