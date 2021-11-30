package controller;

import businessObject.BOFactory;
import businessObject.custom.ItemBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dataTransferObject.ItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageItemFormController {
    public AnchorPane pageContext;

    public TableView<ItemDTO> tblItemList;
    public TableColumn<ItemDTO,String> colItemCode;
    public TableColumn<ItemDTO,String> colDescription;
    public TableColumn<ItemDTO,Integer> colQtyOnHand;
    public TableColumn<ItemDTO,Double> colUnitPrice;
    public TableColumn<ItemDTO,Double> colDiscount;

    public JFXTextField itemSearchBar;
    public TextField txtItemCode1;
    public TextField txtDescription1;
    public TextField txtQtyOnHand1;
    public TextField txtUnitPrice1;
    public TextField txtDiscountPercent1;
    public TextField txtItemCode2;
    public TextField txtDescription2;
    public TextField txtQtyOnHand2;
    public TextField txtUnitPrice2;
    public TextField txtDiscountPercent2;

    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    private final ItemBO itemBO=(ItemBO)BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);

    ObservableList<ItemDTO> obList= FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> validationList1=new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> validationList2=new LinkedHashMap<>();

    Pattern codePattern=Pattern.compile("^(I-)[0-9]{4}$");
    Pattern descPattern=Pattern.compile("^[A-z0-9 /.,]*$");
    Pattern qtyPattern=Pattern.compile("^[1-9][0-9]*$");
    Pattern pricePattern=Pattern.compile("^[1-9][0-9]{0,6}([.][0-9]{2})?$");
    Pattern discountPattern=Pattern.compile("^[0-9]{1,2}(%)$");

    public void initialize(){
        storeValidations();
        viewAllItems();
        setItemCode();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));


        tblItemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
                setItemData(newValue);
            }
        );

        listenFieldChange(validationList1);
        listenFieldChange(validationList2);

        /*FilteredList<Item> filteredList=new FilteredList<>(obList, b->true);
        itemSearchBar.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(item->{
                return filterSearchItems(newValue,item);
            });
        });
        SortedList<Item> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tblItemList.comparatorProperty());
        tblItemList.setItems(sortedList);*/
    }

    /*private boolean filterSearchItems(String newValue, Item item) {
        if(newValue==null || newValue.isEmpty()) {
            return true;
        }
        String search=newValue.toLowerCase();
        if(item.getItemCode().toLowerCase().contains(search)){
            return true;
        }else if(item.getDescription().toLowerCase().contains(search)){
            return true;
        }else if(item.getPackSize().toLowerCase().contains(search)){
            return true;
        }else if(String.valueOf(item.getQtyOnHand()).toLowerCase().contains(search)){
            return true;
        }else if(String.valueOf(item.getUnitPrice()).toLowerCase().contains(search)) {
            return true;
        }else if(String.valueOf(item.getDiscountPercent()).toLowerCase().contains(search)) {
            return true;
        }else{
            return false;
        }
    }*/

    private void listenFieldChange(LinkedHashMap<TextField, Pattern> list) {
        for(TextField key: list.keySet()) {
            key.textProperty().addListener((observable,oldValue,newValue)->{
                ValidationUtil.validate(key,list);
            });
        }
    }

    private void storeValidations() {
        validationList1.put(txtItemCode1,codePattern);
        validationList1.put(txtDescription1,descPattern);
        validationList1.put(txtQtyOnHand1,qtyPattern);
        validationList1.put(txtUnitPrice1,pricePattern);
        validationList1.put(txtDiscountPercent1,discountPattern);
        validationList2.put(txtItemCode2,codePattern);
        validationList2.put(txtDescription2,descPattern);
        validationList2.put(txtQtyOnHand2,qtyPattern);
        validationList2.put(txtUnitPrice2,pricePattern);
        validationList2.put(txtDiscountPercent2,discountPattern);
    }

    public void addItemOnAction(ActionEvent actionEvent){
        if(ValidationUtil.isValidated(validationList1)){
            ItemDTO item=new ItemDTO(
                    txtItemCode1.getText(),
                    txtDescription1.getText(),
                    Integer.parseInt(txtQtyOnHand1.getText()),
                    Double.parseDouble(txtUnitPrice1.getText()),
                    Double.parseDouble(txtDiscountPercent1.getText().split("%")[0])/100
            );
            if(itemBO.addItem(item)){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
                clearAllFields();
                setItemCode();
                viewAllItems();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }

    }
    public void updateItemOnAction(ActionEvent actionEvent){
        if(ValidationUtil.isValidated(validationList2)){
            ItemDTO item=new ItemDTO(
                    txtItemCode2.getText(),
                    txtDescription2.getText(),
                    Integer.parseInt(txtQtyOnHand2.getText()),
                    Double.parseDouble(txtUnitPrice2.getText()),
                    Double.parseDouble(txtDiscountPercent2.getText().split("%")[0])/100
            );
            if(itemBO.updateItem(item)){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated...").show();
                clearAllFields();
                setItemCode();
                viewAllItems();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }

    }
    public void deleteItemOnAction(ActionEvent actionEvent){
        if(ValidationUtil.isValidated(validationList2)){
            String itemCode=txtItemCode2.getText();
            if(itemBO.deleteItem(itemCode)){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted...").show();
                clearAllFields();
                setItemCode();
                viewAllItems();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }
    }

    private void clearAllFields() {
        for(TextField field : validationList1.keySet()) {
            field.clear();
        }

        for(TextField field : validationList2.keySet()) {
            field.clear();
        }
    }

    private void setItemCode(){
        txtItemCode1.setText(itemBO.getItemCode());
    }

    private void viewAllItems(){
        obList.clear();
        ArrayList<ItemDTO> items=itemBO.getAllItems();
        obList.addAll(items);
        tblItemList.setItems(obList);
    }

    /*public void selectItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode=txtItemCode2.getText();
        Item item=new ItemManager().getItem(itemCode);
        if(item!=null){
            setItemData(item);
        }else{
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        }
    }*/

    private void setItemData(ItemDTO i) {
        if(i!=null){
            txtItemCode2.setText(i.getItemCode());
            txtDescription2.setText(i.getDescription());
            txtQtyOnHand2.setText(String.valueOf(i.getQtyOnHand()));
            txtUnitPrice2.setText(String.format("%.2f", i.getUnitPrice()));
            txtDiscountPercent2.setText((int) (i.getDiscountPercent() * 100) + "%");
        }
    }
}
