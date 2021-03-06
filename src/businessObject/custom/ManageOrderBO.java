package businessObject.custom;

import businessObject.SuperBO;
import dataTransferObject.OrderDTO;
import dataTransferObject.OrderDetailDTO;

import java.util.ArrayList;

public interface ManageOrderBO extends SuperBO {
    OrderDTO getOrder(String id);
    ArrayList<OrderDTO> getAllOrders();
    OrderDetailDTO getOrderDetail(String orderId,String itemCode);
    boolean updateOrder(OrderDTO dto);
    boolean deleteOrder(String id);
    boolean updateOrderDetail(OrderDTO dto,OrderDetailDTO detailDTO);
    boolean deleteOrderDetail(OrderDetailDTO detailDTO);
}
