package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
public class Order{
    @Id
	private String orderId;
    private String customerId;
    private String orderDate;
    private String orderTime;
    private double cost;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private ArrayList<OrderDetail> detailList=new ArrayList<>();

    public Order() {   }

    public Order(String orderId, String customerId, String orderDate, String orderTime, double cost, Customer customer, ArrayList<OrderDetail> detailList) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCost(cost);
        this.setCustomer(customer);
        this.setDetailList(detailList);
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<OrderDetail> getDetailList() {
        return detailList;
    }
    public void setDetailList(ArrayList<OrderDetail> detailList) {
        this.detailList = detailList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + getOrderId() + '\'' +
                ", customerId='" + getCustomerId() + '\'' +
                ", orderDate='" + getOrderDate() + '\'' +
                ", orderTime='" + getOrderTime() + '\'' +
                ", cost=" + getCost() +
                ", customer=" + getCustomer() +
                ", detailList=" + getDetailList() +
                '}';
    }
}
