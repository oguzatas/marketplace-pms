package elements;

public class SellingOrder extends Order implements Comparable<SellingOrder> {


    public SellingOrder(int traderID, double amount, double price) {
        super(traderID, amount, price);
    }


    @Override
    public int compareTo(SellingOrder o) {
        if (this.getPrice() < o.getPrice()) {
            return -1;
        } else if (this.getPrice() == o.getPrice()) {
            if (this.getAmount() > o.getAmount()) {
                return -1;
            } else if (this.getAmount() == o.getAmount()) {
                return this.getTraderID() - o.getTraderID();
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }


}
