package elements;


public abstract class Order {

    private double amount;


    private final double price;


    private int traderID;


    public Order(int traderID, double amount, double price) {
        this.traderID = traderID;
        this.amount = amount;
        this.price = price;
    }


    public double getPrice() {
        return price;
    }


    public double getAmount() {
        return amount;
    }


    public int getTraderID() {
        return traderID;
    }

}