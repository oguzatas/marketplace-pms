package elements;


import java.util.*;

public class Market {

    private PriorityQueue<SellingOrder> sellingOrders = new PriorityQueue<SellingOrder>();


    private PriorityQueue<BuyingOrder> buyingOrders = new PriorityQueue<BuyingOrder>();


    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();


    private final int fee;


    public Market(int fee) {
        this.fee = fee;
    }


    public void giveSellOrder(SellingOrder order) {
        this.sellingOrders.add(order);
    }


    public void giveBuyOrder(BuyingOrder order) {
        this.buyingOrders.add(order);
    }


    public void makeOpenMarketOperation(double price, ArrayList<Trader> traders) {
        if (sellingOrders.size() > 0) {
            while (sellingOrders.peek().getPrice() <= price) {
                SellingOrder sOrder = sellingOrders.poll();
                BuyingOrder bOrder = new BuyingOrder(0, sOrder.getAmount(), sOrder.getPrice());
                traders.get(sOrder.getTraderID()).sold(sOrder.getAmount(),
                        sOrder.getPrice() * (double) (1.00 - fee / 1000.00));
                transactions.add(new Transaction(sOrder, bOrder));
            }
        } else if (buyingOrders.size() > 0) {
            while (buyingOrders.peek().getPrice() >= price) {
                BuyingOrder bOrder = buyingOrders.poll();
                SellingOrder sOrder = new SellingOrder(0, bOrder.getAmount(), bOrder.getPrice());
                traders.get(bOrder.getTraderID()).bought(bOrder.getAmount(), bOrder.getPrice());
                transactions.add(new Transaction(sOrder, bOrder));
            }
        }
    }


    public void checkTransactions(ArrayList<Trader> traders) {
        while (sellingOrders.size() > 0 && buyingOrders.size() > 0
                && sellingOrders.peek().getPrice() <= buyingOrders.peek().getPrice()) {
            if (sellingOrders.peek().getPrice() == buyingOrders.peek().getPrice()) {
                if (sellingOrders.peek().getAmount() == buyingOrders.peek().getAmount()) {
                    SellingOrder sellingOrder = sellingOrders.poll();
                    BuyingOrder buyingOrder = buyingOrders.poll();

                    Double transactionAmount = buyingOrder.getAmount();
                    Double transactionPrice = sellingOrder.getPrice();

                    Transaction transaction = new Transaction(sellingOrder, buyingOrder);

                    //get the seller
                    traders.get(sellingOrder.getTraderID()).sold(transactionAmount,
                            transactionPrice * (double) (1.00 - fee / 1000.00));


                    traders.get(buyingOrder.getTraderID()).bought(transactionAmount, transactionPrice);
                    transactions.add(transaction);
                } else if (sellingOrders.peek().getAmount() > buyingOrders.peek().getAmount()) {
                    SellingOrder sellingOrder = sellingOrders.poll();
                    BuyingOrder buyingOrder = buyingOrders.poll();

                    Double transactionAmount = buyingOrder.getAmount();
                    Double remainingAmount = sellingOrder.getAmount() - buyingOrder.getAmount();
                    Double transactionPrice = sellingOrder.getPrice();

                    SellingOrder transactionSorder = new SellingOrder(sellingOrder.getTraderID(), transactionAmount,
                            transactionPrice);
                    SellingOrder remainingSorder = new SellingOrder(sellingOrder.getTraderID(), remainingAmount,
                            sellingOrder.getPrice());

                    Transaction transaction = new Transaction(transactionSorder, buyingOrder);
                    traders.get(sellingOrder.getTraderID()).sold(transactionAmount,
                            transactionPrice * (double) (1.00 - fee / 1000.00));
                    traders.get(buyingOrder.getTraderID()).bought(transactionAmount, transactionPrice);
                    transactions.add(transaction);
                    sellingOrders.add(remainingSorder); //requeue remaining
                } else if (sellingOrders.peek().getAmount() < buyingOrders.peek().getAmount()) {
                    SellingOrder sellingOrder = sellingOrders.poll();
                    BuyingOrder buyingOrder = buyingOrders.poll();

                    Double transactionAmount = sellingOrder.getAmount();
                    Double remainingAmount = buyingOrder.getAmount() - sellingOrder.getAmount();
                    Double transactionPrice = sellingOrder.getPrice();

                    BuyingOrder transactionBorder = new BuyingOrder(buyingOrder.getTraderID(), transactionAmount,
                            transactionPrice);
                    BuyingOrder remainingBorder = new BuyingOrder(buyingOrder.getTraderID(), remainingAmount,
                            sellingOrder.getPrice());

                    Transaction transaction = new Transaction(sellingOrder, transactionBorder);
                    traders.get(sellingOrder.getTraderID()).sold(transactionAmount,
                            transactionPrice * (double) (1.00 - fee / 1000.00));
                    traders.get(buyingOrder.getTraderID()).bought(transactionAmount, transactionPrice);
                    transactions.add(transaction);
                    buyingOrders.add(remainingBorder); //requeue remaining
                }
            } else if (sellingOrders.peek().getPrice() < buyingOrders.peek().getPrice()) {
                if (sellingOrders.peek().getAmount() == buyingOrders.peek().getAmount()) {
                    SellingOrder sellingOrder = sellingOrders.poll();
                    BuyingOrder buyingOrder = buyingOrders.poll();

                    Double transactionPrice = sellingOrder.getPrice();
                    Double transactionAmount = buyingOrder.getAmount();

                    Transaction transaction = new Transaction(sellingOrder, buyingOrder);
                    traders.get(sellingOrder.getTraderID()).sold(transactionAmount,
                            transactionPrice * (double) (1.00 - fee / 1000.00));
                    traders.get(buyingOrder.getTraderID()).bought(transactionAmount, transactionPrice);
                    traders.get(buyingOrder.getTraderID())

                            .releaseBlockedDollars(transactionAmount * (buyingOrder.getPrice() - transactionPrice));
                    transactions.add(transaction);


                } else if (sellingOrders.peek().getAmount() > buyingOrders.peek().getAmount()) {

                    SellingOrder sellingOrder = sellingOrders.poll();
                    BuyingOrder buyingOrder = buyingOrders.poll();

                    Double transactionAmount = buyingOrder.getAmount();

                    Double remainingAmount = sellingOrder.getAmount() - buyingOrder.getAmount();

                    Double transactionPrice = sellingOrder.getPrice();

                    SellingOrder transactionSellorder = new SellingOrder(sellingOrder.getTraderID(), transactionAmount,
                            transactionPrice);

                    SellingOrder remainingSellorder = new SellingOrder(sellingOrder.getTraderID(),
                            remainingAmount,
                            sellingOrder.getPrice());

                    Transaction transaction = new Transaction(transactionSellorder, buyingOrder);
                    traders.get(sellingOrder.getTraderID()).sold(transactionAmount,
                            transactionPrice * (double) (1.00 - fee / 1000.00));

                    traders.get(buyingOrder.getTraderID()).bought(transactionAmount, transactionPrice);


                    traders.get(buyingOrder.getTraderID())
                            .releaseBlockedDollars(transactionAmount * (buyingOrder.getPrice() - transactionPrice));
                    transactions.add(transaction);
                    sellingOrders.add(remainingSellorder); //requeue remaining

                } else if (sellingOrders.peek().getAmount() < buyingOrders.peek().getAmount()) {
                    SellingOrder sellOrder = sellingOrders.poll();

                    BuyingOrder buyingOrder = buyingOrders.poll();


                    Double transactionAmount = sellOrder.getAmount();

                    Double remainingAmount = buyingOrder.getAmount() - sellOrder.getAmount();

                    Double transactionPrice = sellOrder.getPrice();

                    BuyingOrder transactionBorder = new BuyingOrder(buyingOrder.getTraderID(), transactionAmount,
                            transactionPrice);


                    BuyingOrder remainingBorder = new BuyingOrder(buyingOrder.getTraderID(), remainingAmount,

                            sellOrder.getPrice());

                    Transaction transaction = new Transaction(sellOrder, transactionBorder);
                    traders.get(sellOrder.getTraderID()).sold(transactionAmount,


                            transactionPrice * (double) (1.00 - fee / 1000.00));
                    traders.get(buyingOrder.getTraderID()).bought(transactionAmount, transactionPrice);

                    traders.get(buyingOrder.getTraderID())
                            .releaseBlockedDollars(transactionAmount * (buyingOrder.getPrice() - transactionPrice));
                    transactions.add(transaction);
                    buyingOrders.add(remainingBorder);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }


    public double totalCoinsForSale() {
        double total = 0;
        if (sellingOrders.size() == 0) {
            return 0;
        } else {
            for (SellingOrder s : this.sellingOrders) {
                total += s.getAmount();
            }
            return total;
        }
    }


    public double totalDollarsForBuying() {
        double total = 0;
        if (buyingOrders.size() == 0) {
            return 0;
        } else {
            for (BuyingOrder b : buyingOrders) {
                total += b.getAmount() * b.getPrice();
            }
            return total;
        }
    }


    public double sellPrice() {
        if (this.sellingOrders.size() == 0) {
            return 0;
        } else {
            return this.sellingOrders.peek().getPrice();
        }
    }


    public double buyPrice() {
        if (this.buyingOrders.size() == 0) {
            return 0;
        } else {
            return this.buyingOrders.peek().getPrice();
        }
    }


    public double averagePrice() {
        return (buyPrice() + sellPrice()) / 2;
    }


    public int getFee() {
        return fee;
    }


    public PriorityQueue<BuyingOrder> getBuyingOrders() {
        return buyingOrders;
    }


    public PriorityQueue<SellingOrder> getSellingOrders() {
        return sellingOrders;
    }


    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

}
