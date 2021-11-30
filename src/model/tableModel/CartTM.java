package model.tableModel;

public class CartTM {
    private String itemCode;
    private int quantity;
    private double unitPrice;
    private double discount;
    private double price;

    public CartTM() {  }

    public CartTM(String itemCode, int quantity, double unitPrice, double discount, double price) {
        this.setItemCode(itemCode);
        this.setQuantity(quantity);
        this.setUnitPrice(unitPrice);
        this.setDiscount(discount);
        this.setPrice(price);
    }

    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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

    @Override
    public String toString() {
        return "CartTM{" +
                "itemCode='" + getItemCode() + '\'' +
                ", quantity='" + getQuantity() + '\'' +
                ", unitPrice=" + getUnitPrice() +
                ", discount=" + getDiscount() +
                ", price=" + getPrice() +
            '}';
    }
}
