package model.tableModel;

public class CustomerIncomeTM {
    private String custId;
    private String custName;
    private double totalIncome;

    public CustomerIncomeTM() {  }

    public CustomerIncomeTM(String custId, String custName, double totalIncome) {
        this.setCustId(custId);
        this.setCustName(custName);
        this.setTotalIncome(totalIncome);
    }

    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getTotalIncome() {
        return totalIncome;
    }
    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "CustomerIncomeTM{" +
                "custId='" + getCustId() + '\'' +
                ", custName='" + getCustName() + '\'' +
                ", totalIncome=" + getTotalIncome() +
            '}';
    }
}
