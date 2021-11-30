package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderDetail {
    @Id
    private String orderId;
    @Id
    private String itemCode;
    private int orderQty;
    private double discount;
    private double price;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Item item;

    public OrderDetail() {  }

    public OrderDetail(String orderId, String itemCode, int orderQty, double discount, double price, Order order, Item item) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
        this.setOrderQty(orderQty);
        this.setDiscount(discount);
        this.setPrice(price);
        this.setOrder(order);
        this.setItem(item);
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQty() {
        return orderQty;
    }
    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + getOrderId() + '\'' +
                ", itemCode='" + getItemCode() + '\'' +
                ", orderQty=" + getOrderQty() +
                ", discount=" + getDiscount() +
                ", price=" + getPrice() +
                ", order=" + getOrder() +
                ", item=" + getItem() +
                '}';
    }
}
