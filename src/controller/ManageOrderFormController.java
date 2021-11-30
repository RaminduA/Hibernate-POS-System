package controller;

import businessObject.BOFactory;
import businessObject.custom.ManageOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dataTransferObject.OrderDTO;
import dataTransferObject.OrderDetailDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.ValidationUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageOrderFormController {
    public AnchorPane pageContext;

    public TableView<OrderDTO> tblOrderList;
    public TableColumn<OrderDTO,String> colOrderId;
    public TableColumn<OrderDTO,String> colCustId;
    public TableColumn<OrderDTO,String> colDate;
    public TableColumn<OrderDTO,String> colTime;
    public TableColumn<OrderDTO,Double> colCost;

    public TableView<OrderDetailDTO> tblOrderDetailList;
    public TableColumn<OrderDetailDTO,String> colItemCode;
    public TableColumn<OrderDetailDTO,Integer> colQty;
    public TableColumn<OrderDetailDTO,Double> colDiscount;
    public TableColumn<OrderDetailDTO,Double> colPrice;

    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXTextField orderSearchBar;

    public TextField txtOrderId;
    public TextField txtCustId;
    public TextField txtOrderDate;
    public TextField txtOrderTime;
    public TextField txtOrderCost;

    public TextField txtItemCode;
    public TextField txtQuantity;
    public TextField txtDiscount;
    public TextField txtPrice;

    private final ManageOrderBO manageOrderBO=(ManageOrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.MANAGEORDER);

    LinkedHashMap<TextField, Pattern> validationList1=new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> validationList2=new LinkedHashMap<>();

    Pattern orderIdPattern =Pattern.compile("^(O-)[0-9]{4}$");
    Pattern customerIdPattern =Pattern.compile("^(C-)[0-9]{4}$");
    Pattern itemCodePattern=Pattern.compile("^(I-)[0-9]{4}$");
    Pattern datePattern=Pattern.compile("^[0-9]{4}(-)[0-9]{2}(-)[0-9]{2}$");
    Pattern timePattern=Pattern.compile("^[0-9]{2}(:)[0-9]{2}(:)[0-9]{2}[ ](AM|PM)$");
    Pattern pricePattern=Pattern.compile("^[1-9][0-9]{0,6}([.][0-9]{2})?$");
    Pattern qtyPattern=Pattern.compile("^[1-9][0-9]*$");

    ObservableList<OrderDTO> obList=FXCollections.observableArrayList();
    ObservableList<OrderDetailDTO> obDetailList=FXCollections.observableArrayList();

    public void initialize(){
        storeValidations();
        viewAllOrders();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblOrderList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            if(!isOrderDetailNotFilled()){
                executeOrder();
            }
            clearAllFields();
            setOrderData(newValue);
            setOrderDetailsTable(newValue);
        });
        tblOrderDetailList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
                setOrderDetailData(newValue);
            }
        );

        listenFieldChange(validationList1);
        listenFieldChange(validationList2);

    }

    private void listenFieldChange(LinkedHashMap<TextField, Pattern> list) {
        for(TextField key: list.keySet()) {
            key.textProperty().addListener((observable,oldValue,newValue)->{
                ValidationUtil.validate(key,list);
            });
        }
    }

    private void storeValidations() {
        validationList1.put(txtOrderId, orderIdPattern);
        validationList1.put(txtCustId, customerIdPattern);
        validationList1.put(txtOrderDate,datePattern);
        validationList1.put(txtOrderTime,timePattern);
        validationList1.put(txtOrderCost,pricePattern);
        validationList2.put(txtItemCode,itemCodePattern);
        validationList2.put(txtQuantity,qtyPattern);
        validationList2.put(txtDiscount,pricePattern);
        validationList2.put(txtPrice,pricePattern);
    }

    private boolean isOrderDetailNotFilled() {
        for (TextField field : validationList2.keySet()) {
            if (!field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isOrderNotFilled() {
        for (TextField field : validationList1.keySet()) {
            if (!field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void clearAllFields(){
        for (TextField field : validationList1.keySet()) {
            field.clear();
        }
        for (TextField field : validationList2.keySet()) {
            field.clear();
        }
    }

    private void executeOrder(){
        clearAllFields();
        viewAllOrders();
        obDetailList.clear();
        tblOrderDetailList.setItems(obDetailList);
    }

    private void setOrderDetailData(OrderDetailDTO orderDetail) {
        if (orderDetail!=null) {
            txtItemCode.setText(orderDetail.getItemCode());
            txtQuantity.setText(String.valueOf(orderDetail.getOrderQty()));
            txtPrice.setText(String.format("%.2f",orderDetail.getPrice()));
            txtDiscount.setText(String.format("%.2f",orderDetail.getDiscount()));
        }
    }

    private void setOrderDetailsTable(OrderDTO order) {
        obDetailList.clear();
        if(order!=null){
            ArrayList<OrderDetailDTO> orderDetails = order.getDetailList();
            obDetailList.addAll(orderDetails);
            tblOrderDetailList.setItems(obDetailList);
        }
    }

    private void setOrderData(OrderDTO order) {
        clearAllFields();
        if (order!=null) {
            txtOrderId.setText(order.getOrderId());
            txtCustId.setText(order.getCustomerId());
            txtOrderDate.setText(order.getOrderDate());
            txtOrderTime.setText(order.getOrderTime());
            txtOrderCost.setText(String.format("%.2f",order.getCost()));
        }
    }

    public void updateOnAction(ActionEvent actionEvent){
        if(ValidationUtil.isValidated(validationList1)){
            if(isOrderDetailNotFilled()){
                OrderDTO order=manageOrderBO.getOrder(txtOrderId.getText());
                order.setCustomerId(txtCustId.getText());
                order.setOrderDate(txtOrderDate.getText());
                order.setOrderTime(txtOrderTime.getText());
                if(manageOrderBO.updateOrder(order)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated...").show();
                    executeOrder();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try again...").show();
                }
            }else if(ValidationUtil.isValidated(validationList2)){
                OrderDTO order=manageOrderBO.getOrder(txtOrderId.getText());
                order.setCustomerId(txtCustId.getText());
                order.setOrderDate(txtOrderDate.getText());
                order.setOrderTime(txtOrderTime.getText());
                OrderDetailDTO detailDTO=new OrderDetailDTO(txtOrderId.getText(),txtItemCode.getText(),Integer.parseInt(txtQuantity.getText()),Double.parseDouble(txtDiscount.getText()),Double.parseDouble(txtPrice.getText()));
                if(manageOrderBO.updateOrderDetail(order,detailDTO)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated...").show();
                    executeOrder();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try again...").show();
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Fields not filled").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields not filled").show();
        }
    }
    public void deleteOnAction(ActionEvent actionEvent){
        if(ValidationUtil.isValidated(validationList1)){
            if(isOrderDetailNotFilled()){
                String orderId=txtOrderId.getText();
                if(manageOrderBO.deleteOrder(orderId)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted...").show();
                    executeOrder();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try again...").show();
                }
            }else if(ValidationUtil.isValidated(validationList2)){
                String orderId=txtOrderId.getText();
                String itemCode=txtItemCode.getText();
                OrderDetailDTO orderDetail=manageOrderBO.getOrderDetail(orderId,itemCode);
                if(manageOrderBO.deleteOrderDetail(orderDetail)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted...").show();
                    executeOrder();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try again...").show();
                }
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted...").show();
                executeOrder();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields not filled").show();
        }
    }

    private void viewAllOrders(){
        obList.clear();
        ArrayList<OrderDTO> orders=manageOrderBO.getAllOrders();
        obList.addAll(orders);
        tblOrderList.setItems(obList);
    }

    /*private void refreshPage(){
        try {
            pageContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageOrderForm.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
