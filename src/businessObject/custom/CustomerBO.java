package businessObject.custom;

import businessObject.SuperBO;
import dataTransferObject.CustomerDTO;

import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean addCustomer(CustomerDTO dto);
    boolean updateCustomer(CustomerDTO dto);
    boolean deleteCustomer(String id);
    ArrayList<CustomerDTO> getAllCustomers();
    String getCustomerId();
}
