package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminDashboardFormController {
    public AnchorPane adminDashboardContext;
    public AnchorPane adminPerformanceContext;
    public JFXButton logoutButton;

    public void initialize() throws IOException {
        adminPerformanceContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageItemForm.fxml")));
    }

    public void manageItemsOnAction(ActionEvent actionEvent) throws IOException {
        adminPerformanceContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageItemForm.fxml")));
    }

    public void viewReportsOnAction(ActionEvent actionEvent) throws IOException {
        adminPerformanceContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ViewReportsForm.fxml")));
    }

    public void backToLoginForm(ActionEvent actionEvent) throws IOException {
        adminDashboardContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
    }
}
