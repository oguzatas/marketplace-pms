package elements;

public class Wallet {

    private double dollars;


    private double coins;


    private double blockedDollars = 0;


    private double blockedCoins = 0;


    public Wallet(double dollars, double coins) {
        this.dollars = dollars;
        this.coins = coins;
    }


    public double getDollars() {
        return dollars;
    }


    public double getCoins() {
        return coins;
    }


    public double getBlockedCoins() {
        return blockedCoins;
    }


    public double getBlockedDollars() {
        return blockedDollars;
    }


    public void blockDollars(double amount) {
        blockedDollars += amount;
    }


    public void blockCoins(double amount) {
        blockedCoins += amount;
    }


    public void depositDollars(double amount) {
        this.dollars += amount;
    }


    public void withdrawDollars(double amount) {
        this.dollars -= amount;
    }


    public void bought(double amount, double price) {
        this.dollars -= (double) amount * price;
        this.coins += (double) amount;
        this.blockedDollars -= (double) amount * price;
    }


    public void sold(double amount, double price) {
        this.dollars += (double) amount * price;
        this.blockedCoins -= (double) amount;
        this.coins -= (double) amount;
    }


    public void releaseDollars(double amount) {
        this.blockedDollars -= amount;
    }


    public void giveReward(double amount) {
        this.coins += amount;
    }
}
