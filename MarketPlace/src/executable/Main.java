package executable;

import elements.*;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Main {


    public static Random myRandom;


    public static void main(String[] args) throws FileNotFoundException {


        Scanner reader = new Scanner(new File("input.txt"));


        PrintStream writer = new PrintStream(new File("output.txt"));


        int traderID = 0;


        int seed = reader.nextInt();

        myRandom = new Random(seed);


        int fee = reader.nextInt();


        Market market = new Market(fee);


        int numberOfUsers = reader.nextInt();


        int numberOfQueries = reader.nextInt();


        ArrayList<Trader> traderList = new ArrayList<Trader>();

        for (int i = 0; i < numberOfUsers; i++) {

            double dollars = reader.nextDouble();
            double coins = reader.nextDouble();
            Trader trader = new Trader(dollars, coins);
            trader.setID(traderID);
            traderList.add(trader);
            traderID++;
        }

        for (int i = 0; i < numberOfQueries; i++) {
            int options = reader.nextInt();

            if (options == 10) {
                int trader_id = reader.nextInt();
                double price = reader.nextDouble();
                double amount = reader.nextDouble();
                traderList.get(trader_id).buy(amount, price, market);
                market.checkTransactions(traderList);
            } else if (options == 11) {
                int trader_id = reader.nextInt();
                double amount = reader.nextDouble();
                traderList.get(trader_id).buyAtMarketPrice(amount, market);
                market.checkTransactions(traderList);
            } else if (options == 20) {
                int trader_id = reader.nextInt();
                double price = reader.nextDouble();
                double amount = reader.nextDouble();
                traderList.get(trader_id).sell(amount, price, market);
                market.checkTransactions(traderList);
            } else if (options == 21) {
                int trader_id = reader.nextInt();
                double amount = reader.nextDouble();
                traderList.get(trader_id).sellAtMarketPrice(amount, market);
                market.checkTransactions(traderList);
            } else if (options == 3) {
                int trader_id = reader.nextInt();
                double amount = reader.nextDouble();
                traderList.get(trader_id).depositDollars(amount);
            } else if (options == 4) {
                int trader_id = reader.nextInt();
                double amount = reader.nextDouble();
                traderList.get(trader_id).withdrawDollars(amount);
            } else if (options == 5) {
                int trader_id = reader.nextInt();
                Trader trader = traderList.get(trader_id);
                writer.println("Trader " + trader_id + ": " + trader.getWallet().getDollars() + "$ "
                        + trader.getWallet().getCoins() + "PQ");
            } else if (options == 777) {
                for (Trader t : traderList) {
                    t.giveReward(myRandom.nextDouble() * 10);
                }
            } else if (options == 666) {

                double price = reader.nextDouble();
                market.makeOpenMarketOperation(price, traderList);
                market.checkTransactions(traderList);
            } else if (options == 500) {
                writer.println("Current market size: " + String.format("%.5f", market.totalDollarsForBuying()) + " "
                        + String.format("%.5f", market.totalCoinsForSale()));
            } else if (options == 501) {
                writer.println("Number of successful transactions: " + market.getTransactions().size());
            } else if (options == 502) {
                writer.println("Number of invalid queries: " + Trader.numberOfInvalidQueries);
            } else if (options == 505) {
                writer.println("Current prices: " + String.format("%.5f", market.buyPrice()) + " "
                        + String.format("%.5f", market.sellPrice()) + " "
                        + String.format("%.5f", market.averagePrice()));
            } else if (options == 555) {
                for (Trader t : traderList) {
                    writer.println("Trader " + t.getID() + ": " + String.format("%.5f", t.getWallet().getDollars()) + "$ "
                            + String.format("%.5f", t.getWallet().getCoins()) + "PQ");
                }
            }
        }
    }
}
