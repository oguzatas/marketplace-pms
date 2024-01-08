package elements;


public class Transaction {


    private SellingOrder sellingOrder;


    private BuyingOrder buyingOrder;


    public Transaction(SellingOrder sellingOrder, BuyingOrder buyingOrder) {
        this.buyingOrder = buyingOrder;
        this.sellingOrder = sellingOrder;
    }


    public BuyingOrder getBuyingOrder() {
        return buyingOrder;
    }


    public SellingOrder getSellingOrder() {
        return sellingOrder;
    }
}
