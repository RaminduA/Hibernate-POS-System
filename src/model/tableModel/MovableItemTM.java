package model.tableModel;

public class MovableItemTM {
    private String itemCode;
    private int soldCount;
    private int remainingCount;

    public MovableItemTM(){  }

    public MovableItemTM(String itemCode, int soldCount, int remainingCount) {
        this.setItemCode(itemCode);
        this.setSoldCount(soldCount);
        this.setRemainingCount(remainingCount);
    }

    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getSoldCount() {
        return soldCount;
    }
    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public int getRemainingCount() {
        return remainingCount;
    }
    public void setRemainingCount(int remainingCount) {
        this.remainingCount = remainingCount;
    }

    @Override
    public String toString() {
        return "MovableItemTM{" +
                "itemCode='" + getItemCode() + '\'' +
                ", soldCount=" + getSoldCount() +
                ", remainingCount=" + getRemainingCount() +
             '}';
    }
}
