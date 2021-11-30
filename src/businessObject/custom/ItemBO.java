package businessObject.custom;

import businessObject.SuperBO;
import dataTransferObject.ItemDTO;

import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    boolean addItem(ItemDTO dto);
    boolean updateItem(ItemDTO dto);
    boolean deleteItem(String code);
    ArrayList<ItemDTO> getAllItems();
    String getItemCode();
}
