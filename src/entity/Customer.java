package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
public class Customer {
    @Id
    private String customerId;
    private String name;
    private String address;
    private String contact;
    private String nic;
    @OneToMany(mappedBy = "customer")
    private ArrayList<Order> orderList=new ArrayList<>();

    public Customer() { }

    public Customer(String customerId, String name, String address, String contact, String nic, ArrayList<Order> orderList) {
        this.setCustomerId(customerId);
        this.setName(name);
        this.setAddress(address);
        this.setContact(contact);
        this.setNic(nic);
        this.setOrderList(orderList);
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String id) {
        this.customerId = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNic() {
        return nic;
    }
    public void setNic(String nic) {
        this.nic = nic;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }
    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + getCustomerId() + '\'' +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", contact='" + getContact() + '\'' +
                ", nic='" + getNic() + '\'' +
                ", orderList=" + getOrderList() +
                '}';
    }
}
