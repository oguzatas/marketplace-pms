package elements;

public class BuyingOrder extends Order implements Comparable<BuyingOrder> {


    public BuyingOrder(int traderID, double amount, double price) {
        super(traderID, amount, price);
    }


    @Override
    public int compareTo(BuyingOrder Order) {
        if (this.getPrice() > Order.getPrice()) {
            return -1;
        } else if (this.getPrice() == Order.getPrice()) {
            if (this.getAmount() > Order.getAmount()) {
                return -1;
            } else if (this.getAmount() == Order.getAmount()) {
                return this.getTraderID() - Order.getTraderID();
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}
