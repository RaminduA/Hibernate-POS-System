package businessObject.custom;

import businessObject.SuperBO;
import dataTransferObject.CustomerDTO;
import dataTransferObject.ItemDTO;
import dataTransferObject.OrderDTO;

import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    ArrayList<String> getAllCustomerIds();
    ArrayList<String> getAllItemCodes();
    String getOrderId();
    ItemDTO getItem(String code);
    CustomerDTO getCustomer(String id);
    boolean placeOrder(OrderDTO dto);
}