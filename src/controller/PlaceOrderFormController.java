package controller;

import businessObject.BOFactory;
import businessObject.custom.PlaceOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dataTransferObject.CustomerDTO;
import dataTransferObject.ItemDTO;
import dataTransferObject.OrderDTO;
import dataTransferObject.OrderDetailDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.tableModel.CartTM;
import util.ValidationUtil;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class PlaceOrderFormController {
    public AnchorPane pageContext;

    public Label lblDate;
    public Label lblTime;

    public TextField txtCustId;
    public TextField txtCustName;
    public TextField txtCustAddress;
    public TextField txtCustContact;
    public TextField txtCustNic;

    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtDiscountPercent;

    public TextField txtQuantity;
    public JFXButton btnClear;
    public JFXButton btnAddToCart;
    public Label lblTotal;
    public Label lblOrderId;

    public TableView<CartTM> tblCart;
    public TableColumn<CartTM,String> colItemCode;
    public TableColumn<CartTM,Integer> colQty;
    public TableColumn<CartTM,Double> colUnitPrice;
    public TableColumn<CartTM,Double> colDiscount;
    public TableColumn<CartTM,Double> colPrice;

    public JFXComboBox<String> cmbCustomer;
    public JFXComboBox<String> cmbItem;
    public JFXButton btnPlaceOrder;
    public Label lblItemCount;

    private final PlaceOrderBO placeOrderBO=(PlaceOrderBO)BOFactory.getInstance().getBO(BOFactory.BOType.PLACEORDER);

    LinkedHashMap<TextField, Pattern> validationList=new LinkedHashMap<>();

    Pattern idPattern=Pattern.compile("^(C-)[0-9]{4}$");
    Pattern namePattern=Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern addressPattern=Pattern.compile("^[A-Z][A-z0-9,/ ]*$");
    Pattern contactPattern=Pattern.compile("^(07)[01245678]([-][0-9]{7})$");
    Pattern nicPattern=Pattern.compile("^([0-9]{12})|([0-9]{10}[vV])$");
    Pattern codePattern=Pattern.compile("^(I-)[0-9]{4}$");
    Pattern descPattern=Pattern.compile("^[A-z0-9 /.,]*$");
    Pattern qtyPattern=Pattern.compile("^[1-9][0-9]*$");
    Pattern pricePattern=Pattern.compile("^[1-9][0-9]{0,6}([.][0-9]{2})?$");
    Pattern discountPattern=Pattern.compile("^[0-9]{1,2}(%)$");

    static ObservableList<CartTM> obList = FXCollections.observableArrayList();
    CartTM selectedItem;

    public void initialize() {
        storeValidations();
        setCombos();
        loadDateAndTime();
        setOrderId();
        calculateTotal();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        cmbCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerData(newValue);
        });
        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemData(newValue);
        });

        tblCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem = newValue;
        });

        listenFieldChange(validationList);
        listenDetailChange(txtQuantity);
    }

    private void listenDetailChange(TextField field) {
        field.textProperty().addListener((observable,oldValue,newValue)->{
            if(txtItemCode.getText().isEmpty() || txtItemCode.getText().equals("")) {
                new Alert(Alert.AlertType.WARNING, "No item selected...").show();
            }else if(!(txtQuantity.getText().isEmpty() || txtQuantity.getText().equals("")) && Integer.parseInt(txtQtyOnHand.getText())<Integer.parseInt(field.getText())){
                field.getParent().setStyle("-fx-border-color: #DB0F0F");
                AnchorPane parent=(AnchorPane) field.getParent();
                Label tag=(Label)parent.getChildren().get(2);
                tag.setStyle("-fx-text-fill: #DB0F0F");
                new Alert(Alert.AlertType.WARNING, "Insufficient quantity...").show();
            }
        });
    }

    private void listenFieldChange(LinkedHashMap<TextField, Pattern> list) {
        for(TextField key: list.keySet()) {
            key.textProperty().addListener((observable,oldValue,newValue)->{
                ValidationUtil.validate(key,list);
            });
        }
    }

    private void storeValidations() {
        validationList.put(txtCustId,idPattern);
        validationList.put(txtCustName,namePattern);
        validationList.put(txtCustAddress,addressPattern);
        validationList.put(txtCustContact,contactPattern);
        validationList.put(txtCustNic,nicPattern);
        validationList.put(txtItemCode,codePattern);
        validationList.put(txtDescription,descPattern);
        validationList.put(txtQtyOnHand,qtyPattern);
        validationList.put(txtUnitPrice,pricePattern);
        validationList.put(txtDiscountPercent,discountPattern);
        validationList.put(txtQuantity,qtyPattern);
    }

    private void setCombos() {
        cmbCustomer.getItems().addAll(placeOrderBO.getAllCustomerIds());
        cmbItem.getItems().addAll(placeOrderBO.getAllItemCodes());
    }

    private void setOrderId() {
        lblOrderId.setText(placeOrderBO.getOrderId());
    }

    private void setItemData(String itemCode){
        ItemDTO item=placeOrderBO.getItem(itemCode);
        if (item != null) {
            txtItemCode.setText(item.getItemCode());
            txtDescription.setText(item.getDescription());
            setQuantityOnHand(item);
            txtUnitPrice.setText(String.format("%.2f",item.getUnitPrice()));
            txtDiscountPercent.setText((int)(item.getDiscountPercent()*100)+"%");
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        }
    }

    private void setCustomerData(String customerId){
        CustomerDTO customer=placeOrderBO.getCustomer(customerId);
        if (customer != null) {
            txtCustId.setText(customer.getCustomerId());
            txtCustName.setText(customer.getName());
            txtCustAddress.setText(customer.getAddress());
            txtCustContact.setText(customer.getContact());
            txtCustNic.setText(customer.getNic());
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        }
    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date()));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));//ISO_LOCAL_TIME.substring(0,8)
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        if(!ValidationUtil.isValidated(validationList)) {
            new Alert(Alert.AlertType.WARNING, "Fields not filled properly...").show();
        } else {
            ItemDTO itemDTO=placeOrderBO.getItem(txtItemCode.getText());
            CartTM cartTM=new CartTM(
                    txtItemCode.getText(),
                    Integer.parseInt(txtQuantity.getText()),
                    Double.parseDouble(txtUnitPrice.getText()),
                    itemDTO.getDiscountPercent() * Double.parseDouble(txtQuantity.getText()) * Double.parseDouble(txtUnitPrice.getText()),
                    (1 - itemDTO.getDiscountPercent()) * Double.parseDouble(txtQuantity.getText()) * Double.parseDouble(txtUnitPrice.getText())
            );
            if(isExists(cartTM)){
                int index = 0;
                for (int i = 0; i<obList.size(); i++) {
                    if (cartTM.getItemCode().equals(obList.get(i).getItemCode())) {
                        index=i;
                    }
                }
                CartTM temp = obList.get(index);
                CartTM newCartTM = new CartTM(
                        temp.getItemCode(),
                        temp.getQuantity() + cartTM.getQuantity(),
                        temp.getUnitPrice(),
                        temp.getDiscount() + cartTM.getDiscount(),
                        temp.getPrice() + cartTM.getPrice()
                );
                obList.remove(index);
                obList.add(newCartTM);
            } else {
                obList.add(cartTM);
            }
            tblCart.setItems(obList);
            setQuantityOnHand(itemDTO);
            calculateTotal();
            setItemCount();
        }
    }

    private void setItemCount() {
        lblItemCount.setText("Item Count : "+obList.size());
    }

    private void calculateTotal() {
        double total = 0;
        for(CartTM cartTM:obList) {
            total+=cartTM.getPrice();
        }
        lblTotal.setText("Rs. " + String.format("%.2f",total));
    }

    private boolean isExists(CartTM cartTM) {
        for(CartTM tm : obList) {
            if (cartTM.getItemCode().equals(tm.getItemCode())) {
                return true;
            }
        }
        return false;
    }

    public void clearItemOnAction(ActionEvent actionEvent) {
        if(obList.isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Cart is Empty.").show();
        }else if(selectedItem!=null) {
            ItemDTO item=placeOrderBO.getItem(selectedItem.getItemCode());
            if(item!=null){
                obList.removeIf(cartTM -> item.getItemCode().equals(cartTM.getItemCode()));
                tblCart.setItems(obList);
                if(txtItemCode.getText().equals(item.getItemCode())){
                    setQuantityOnHand(item);
                }
            }
            calculateTotal();
            setItemCount();
            selectedItem=null;
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a row").show();
        }
    }

    private void setQuantityOnHand(ItemDTO itemDTO) {
        int newQty=itemDTO.getQtyOnHand();
        for(CartTM tm: obList){
            if(itemDTO.getItemCode().equals(tm.getItemCode())){
                newQty-=tm.getQuantity();
            }
        }
        txtQtyOnHand.setText(Integer.toString(newQty));
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        ArrayList<OrderDetailDTO> detailList=new ArrayList<>();
        for(CartTM temp:obList){
            detailList.add(new OrderDetailDTO(
                    lblOrderId.getText(),
                    temp.getItemCode(),
                    temp.getQuantity(),
                    temp.getDiscount(),
                    temp.getPrice()
                )
            );
        }
        OrderDTO order=new OrderDTO(
                        lblOrderId.getText(),
                        txtCustId.getText(),
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        new SimpleDateFormat("hh:mm:ss a").format(new Date()),
                        Double.parseDouble(lblTotal.getText().split(" ")[1]),
                        detailList
                    );
        if (placeOrderBO.placeOrder(order)){
            new Alert(Alert.AlertType.CONFIRMATION, "Order placed...").show();
            setOrderId();
            obList.clear();
            tblCart.setItems(obList);
            calculateTotal();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again...").show();
        }
    }
}