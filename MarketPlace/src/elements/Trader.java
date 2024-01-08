package elements;


public class Trader {

    private int ID;


    private final Wallet wallet;


    public static int numberOfUsers = 0;


    public static int numberOfInvalidQueries = 0;


    public Trader(double dollars, double coins) {
        wallet = new Wallet(dollars, coins);
    }


    public int sell(double amount, double price, Market market) {
        if (amount <= this.wallet.getCoins() - this.wallet.getBlockedCoins()) {
            this.wallet.blockCoins(amount);
            SellingOrder sOrder = new SellingOrder(ID, amount, price);
            market.giveSellOrder(sOrder);
            return 1;
        } else {
            numberOfInvalidQueries += 1;
            return -1;
        }
    }

    public static int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void sellAtMarketPrice(double amount, Market market) {
        if (market.getBuyingOrders().size() > 0) {
            double price = market.getBuyingOrders().peek().getPrice();
            sell(amount, price, market);
        } else {
            numberOfInvalidQueries += 1;
        }
    }


    public int buy(double amount, double price, Market market) {

        if (amount * price <= this.wallet.getDollars() - this.wallet.getBlockedDollars()) {
            this.wallet.blockDollars(amount * price);
            BuyingOrder bOrder = new BuyingOrder(ID, amount, price);
            market.giveBuyOrder(bOrder);
            return 1;
        } else {
            numberOfInvalidQueries += 1;
            return -1;
        }
    }

    public static void setNumberOfInvalidQueries(int numberOfInvalidQueries) {
        Trader.numberOfInvalidQueries = numberOfInvalidQueries;
    }

    public void buyAtMarketPrice(double amount, Market market) {
        if (market.getSellingOrders().size() > 0) {
            double price = market.getSellingOrders().peek().getPrice();
            sell(amount, price, market);
        } else {
            numberOfInvalidQueries += 1;
        }
    }

    public static int getNumberOfInvalidQueries() {
        return numberOfInvalidQueries;
    }

    public int getID() {
        return ID;
    }

    public static void setNumberOfUsers(int numberOfUsers) {
        Trader.numberOfUsers = numberOfUsers;
    }

    public void setID(int iD) {
        ID = iD;
    }


    public Wallet getWallet() {
        return wallet;
    }


    public void bought(double amount, double price) {
        this.wallet.bought(amount, price);
    }


    public void releaseBlockedDollars(double amount) {
        this.wallet.releaseDollars(amount);
    }


    public void sold(double amount, double price) {
        this.wallet.sold(amount, price);
    }


    public void withdrawDollars(double amount) {
        if (wallet.getDollars() - wallet.getBlockedDollars() >= amount) {
            wallet.withdrawDollars(amount);
        } else {
            numberOfInvalidQueries += 1;
        }
    }


    public void depositDollars(double amount) {
        this.wallet.depositDollars(amount);
    }


    public void giveReward(double amount) {
        this.wallet.giveReward(amount);
    }

}
