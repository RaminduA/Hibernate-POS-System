package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CashierDashboardFormController {
    public AnchorPane cashierDashboardContext;
    public JFXButton logoutButton;
    public AnchorPane cashierPerformanceContext;

    public void initialize() throws IOException {
        cashierPerformanceContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml")));
    }

    public void backToLoginForm(ActionEvent actionEvent) throws IOException {
        cashierDashboardContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
    }

    public void manageCustomersOnAction(ActionEvent actionEvent) throws IOException {
        cashierPerformanceContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageCustomerForm.fxml")));
    }

    public void placeOrdersOnAction(ActionEvent actionEvent) throws IOException {
        cashierPerformanceContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml")));
    }

    public void manageOrdersOnAction(ActionEvent actionEvent) throws IOException {
        cashierPerformanceContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageOrderForm.fxml")));

    }
}
