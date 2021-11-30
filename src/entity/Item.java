package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
public class Item {
    @Id
    private String itemCode;
    private String description;
    private int qtyOnHand;
    private double unitPrice;
    private double discountPercent;
    @OneToMany(mappedBy = "item")
    private ArrayList<OrderDetail> detailList=new ArrayList<>();

    public Item() {  }

    public Item(String itemCode, String description, int qtyOnHand, double unitPrice, double discountPercent, ArrayList<OrderDetail> detailList) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setQtyOnHand(qtyOnHand);
        this.setUnitPrice(unitPrice);
        this.setDiscountPercent(discountPercent);
        this.setDetailList(detailList);
    }

    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }
    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public ArrayList<OrderDetail> getDetailList() {
        return detailList;
    }
    public void setDetailList(ArrayList<OrderDetail> detailList) {
        this.detailList = detailList;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + getItemCode() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", qtyOnHand=" + getQtyOnHand() +
                ", unitPrice=" + getUnitPrice() +
                ", discountPercent=" + getDiscountPercent() +
                ", detailList=" + getDetailList() +
                '}';
    }
}
