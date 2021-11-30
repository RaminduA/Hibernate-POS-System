package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginContext;
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;
    public JFXButton btnAdminLogin;
    public JFXButton btnCashierLogin;

    public void loadAdminDashboard(ActionEvent actionEvent) throws IOException {
        loginContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/AdminDashboardForm.fxml")));
    }

    public void loadCashierDashboard(ActionEvent actionEvent) throws IOException {
        loginContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/CashierDashboardForm.fxml")));
    }
}
