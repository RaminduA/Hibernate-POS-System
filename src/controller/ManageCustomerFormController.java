package controller;

import businessObject.BOFactory;
import businessObject.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dataTransferObject.CustomerDTO;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageCustomerFormController {
    public AnchorPane pageContext;

    public TableView<CustomerDTO> tblCustomerList;
    public TableColumn<CustomerDTO,String> colCustomerId;
    public TableColumn<CustomerDTO,String> colName;
    public TableColumn<CustomerDTO,String> colAddress;
    public TableColumn<CustomerDTO,String> colContact;
    public TableColumn<CustomerDTO,String> colNic;

    public JFXTextField customerSearchBar;
    public TextField txtCustId1;
    public TextField txtCustName1;
    public TextField txtCustAddress1;
    public TextField txtCustContact1;
    public TextField txtCustNic1;
    public TextField txtCustId2;
    public TextField txtCustName2;
    public TextField txtCustAddress2;
    public TextField txtCustContact2;
    public TextField txtCustNic2;

    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    private final CustomerBO customerBO=(CustomerBO)BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    ObservableList<CustomerDTO> obList= FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> validationList1=new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> validationList2=new LinkedHashMap<>();

    Pattern idPattern=Pattern.compile("^(C-)[0-9]{4}$");
    Pattern namePattern=Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern addressPattern=Pattern.compile("^[A-Z][A-z0-9,/ ]*$");
    Pattern contactPattern=Pattern.compile("^(07)[01245678]([-][0-9]{7})$");
    Pattern nicPattern=Pattern.compile("^([0-9]{12})|([0-9]{10}[vV])$");

    public void initialize(){
        storeValidations();
        viewAllCustomers();
        setCustomerId();

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        tblCustomerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
                setCustomerData(newValue);
            }
        );

        listenFieldChange(validationList1);
        listenFieldChange(validationList2);

        /*tblCustomerList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
                 try {
                     if((int)newValue>=0){
                         setCustomerData(new CustomerManager().getAllCustomers().get((int)newValue));
                     }
                 } catch (SQLException throwables) {
                     throwables.printStackTrace();
                 } catch (ClassNotFoundException e) {
                     e.printStackTrace();
                 }
             }
        );*/


        /*FilteredList<Customer> filteredList=new FilteredList<>(obList, b->true);
        customerSearchBar.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(customer->{
                return filterSearchCustomers(newValue,customer);
            });
        });
        SortedList<Customer> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tblCustomerList.comparatorProperty());
        tblCustomerList.setItems(sortedList);*/

    }

    private void listenFieldChange(LinkedHashMap<TextField, Pattern> list) {
        for(TextField key: list.keySet()) {
            key.textProperty().addListener((observable,oldValue,newValue)->{
                ValidationUtil.validate(key,list);
            });
        }
    }

    private void storeValidations() {
        validationList1.put(txtCustId1,idPattern);
        validationList1.put(txtCustName1,namePattern);
        validationList1.put(txtCustAddress1,addressPattern);
        validationList1.put(txtCustContact1,contactPattern);
        validationList1.put(txtCustNic1,nicPattern);
        validationList2.put(txtCustId2,idPattern);
        validationList2.put(txtCustName2,namePattern);
        validationList2.put(txtCustAddress2,addressPattern);
        validationList2.put(txtCustContact2,contactPattern);
        validationList2.put(txtCustNic2,nicPattern);
    }

    /*private boolean filterSearchCustomers(String newValue, Customer customer) {
        if(newValue==null || newValue.isEmpty()) {
            return true;
        }
        String search=newValue.toLowerCase();
        if(customer.getId().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customer.getTitle().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customer.getName().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customer.getAddress().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customer.getCity().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customer.getProvince().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customer.getPostalCode().toLowerCase().indexOf(search)>=0){
            return true;
        }else{
            return false;
        }
    }*/

    private void setCustomerId(){
        txtCustId1.setText(customerBO.getCustomerId());
    }

    private void clearAllFields() {
        for(TextField field : validationList1.keySet()) {
           field.clear();
        }

        for(TextField field : validationList2.keySet()) {
           field.clear();
        }
    }

    public void addCustomerOnAction(ActionEvent actionEvent){
        if(ValidationUtil.isValidated(validationList1)){
            CustomerDTO customer=new CustomerDTO(
                    txtCustId1.getText(),
                    txtCustName1.getText(),
                    txtCustAddress1.getText(),
                    txtCustContact1.getText(),
                    txtCustNic1.getText()
            );
            if(customerBO.addCustomer(customer)){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
                clearAllFields();
                setCustomerId();
                viewAllCustomers();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }

    }
    public void updateCustomerOnAction(ActionEvent actionEvent){
        if(ValidationUtil.isValidated(validationList2)){
            CustomerDTO customer=new CustomerDTO(
                    txtCustId2.getText(),
                    txtCustName2.getText(),
                    txtCustAddress2.getText(),
                    txtCustContact2.getText(),
                    txtCustNic2.getText()
            );
            if(customerBO.updateCustomer(customer)){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated...").show();
                clearAllFields();
                setCustomerId();
                viewAllCustomers();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }

    }
    public void deleteCustomerOnAction(ActionEvent actionEvent){
        if(ValidationUtil.isValidated(validationList2)){
            String customerId=txtCustId2.getText();
            if(customerBO.deleteCustomer(customerId)){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted...").show();
                clearAllFields();
                setCustomerId();
                viewAllCustomers();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }

    }

    public void viewAllCustomers(){
        obList.clear();
        ArrayList<CustomerDTO> customers=customerBO.getAllCustomers();
        obList.addAll(customers);
        tblCustomerList.setItems(obList);
    }

    /*public void selectCustomerOnAction(ActionEvent actionEvent){
        String customerId=txtCustId2.getText();
        Customer customer=new CustomerManager().getCustomer(customerId);
        if (customer!=null) {
            setCustomerData(customer);
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        }
    }*/
    private void setCustomerData(CustomerDTO c) {
        if(c!=null){
            txtCustId2.setText(c.getCustomerId());
            txtCustName2.setText(c.getName());
            txtCustAddress2.setText(c.getAddress());
            txtCustContact2.setText(c.getContact());
            txtCustNic2.setText(c.getNic());
        }
    }
}
