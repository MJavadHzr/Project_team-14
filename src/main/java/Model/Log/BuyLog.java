package Model.Log;

import Model.Cart.Cart;
import Model.Off.OffCode;
import Model.Product.Product;
import Model.RandomString;
import Model.Storage;
import org.javatuples.Triplet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class BuyLog extends Log implements Serializable {


    //the first argument is productID and the second one is salesmanID the third one is product count
    //----[ new ]-----

    private ArrayList<Triplet<String, String, Integer>> allItems;

    //the first argument is productID and the second one is the prices with consideration of possible sales
    private HashMap<String, Integer> prices;

    //the first argument is productID and the second one is the prices with consideration of possible sales
    private HashMap<String, Integer> pricesAfterSale = new HashMap<>();

    private String customerUsername;
    private String buyLogID;
    private String offCodeID;
    private int totalAmountWithOutOffCode;
    private int totalAmountWithOffCode;
    private Delivery deliveryState;
    private boolean wasOffCodeUsed;

    //mark we should make sure that the offCode is authentic before passing it to BuyLog

    public BuyLog(Cart cart, String offCodeID) {
        super();
        this.allItems = cart.getAllItems();
        this.prices = cart.getPrices();
        this.pricesAfterSale = cart.getPricesAfterSale();
        this.customerUsername = cart.getUsername();
        this.totalAmountWithOutOffCode = cart.getTotalPrice(null);
        this.totalAmountWithOffCode = cart.getTotalPrice(offCodeID);
        this.offCodeID = offCodeID;
        if (offCodeID.isEmpty()) {
            wasOffCodeUsed = false;
        } else {
            this.offCodeID = offCodeID;
            wasOffCodeUsed = true;
            OffCode offCode = OffCode.getOffCodeByID(offCodeID);
            assert offCode != null;
        }
        deliveryState = Delivery.PROCESSING;
        this.buyLogID = createID();
        Storage.allBuyLogs.add(this);
        for (Triplet<String, String, Integer> item : allItems) {
            new SellLog(this, item.getValue0(), item.getValue1());
        }
    }
    //----[ new ]-----
    public int getProductCountByID(String productID) {
        for (Triplet<String, String, Integer> item : allItems) {
            if (item.getValue0().equals(productID)) return item.getValue2();
        }
        return -1;
    }

    public HashMap<String, Integer> getPrices() {
        return prices;
    }

    public Delivery getDeliveryState() {
        return deliveryState;
    }

    public HashMap<String, Integer> getPricesAfterSale() {
        return pricesAfterSale;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String createID() {
        return RandomString.createID("BuyLog");
    }

    public String getBuyLogID() {
        return buyLogID;
    }

    public void updateUserNames(String oldUsername, String newUsername) {
        if (customerUsername.equals(oldUsername)) customerUsername = newUsername;

        ListIterator<Triplet<String, String, Integer>> iterator = allItems.listIterator();
        while (iterator.hasNext()) {
            Triplet<String, String, Integer> oldTriplet;
            if ((oldTriplet = iterator.next()).getValue1().equals(oldUsername)) {
                String productID = oldTriplet.getValue0();
                Integer count = oldTriplet.getValue2();
                iterator.remove();
                iterator.add(new Triplet<>(productID, newUsername, count));
            }
        }
    }

    //----[ new ]-----
    public boolean containProduct(String productID) {
        for (Triplet<String, String, Integer> item : allItems) {
            if (item.getValue0().equals(productID)) return true;
        }
        return false;
    }

    public static ArrayList<BuyLog> getUserBuyLogs(String customerUsername) {
        ArrayList<BuyLog> arrayList = new ArrayList<>();
        for (BuyLog buyLog : Storage.allBuyLogs) {
            if (buyLog.customerUsername.equals(customerUsername)) {
                arrayList.add(buyLog);
            }
        }
        return arrayList;
    }

    public static BuyLog getBuyLogByID(String buyLogID) {
        for (BuyLog buyLog : Storage.allBuyLogs) {
            if (buyLog.getBuyLogID().equals(buyLogID)) return buyLog;
        }
        return null;
    }

    private StringBuilder toStringSingleItem(Triplet<String, String, Integer> item) {
        StringBuilder result = new StringBuilder();
        result.append("Product Name:").append(Product.getNameByID(item.getValue0())).append("\n");
        result.append("Salesman: ").append(item.getValue1()).append("\n");
        result.append("Price Per Unit: ").append(prices.get(item.getValue0())).append("\n");
        result.append("Count: ").append(item.getValue2()).append("\n");
        if (pricesAfterSale.get(item.getValue0()).equals(prices.get(item.getValue0()))) {
            result.append("The product wasn't on sale." + "\n");
        } else {
            result.append("The price after sale: ").append(pricesAfterSale.get(item.getValue0())).append("\n");
        }
        result.append("------------------------------------------").append("\n");
        return result;
    }

    private String toStringProducts() {
        StringBuilder result = new StringBuilder();
        result.append("Products: " + "\n");
        for (Triplet<String, String, Integer> item : allItems) {
            result.append(toStringSingleItem(item));
        }
        return result.toString();
    }

    private String toStringOffCodeUsage() {
        if (wasOffCodeUsed) {
            return "No offCode was used." + "\n";
        } else {
            return "Price after using OffCode: " + totalAmountWithOffCode + "\n";
        }
    }

    public ArrayList<Triplet<String, String, Integer>> getAllItems() {
        return allItems;
    }

    public void setAllItems(ArrayList<Triplet<String, String, Integer>> allItems) {
        this.allItems = allItems;
    }

    public void setPrices(HashMap<String, Integer> prices) {
        this.prices = prices;
    }

    public void setPricesAfterSale(HashMap<String, Integer> pricesAfterSale) {
        this.pricesAfterSale = pricesAfterSale;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public void setBuyLogID(String buyLogID) {
        this.buyLogID = buyLogID;
    }

    public String getOffCodeID() {
        return offCodeID;
    }

    public void setOffCodeID(String offCodeID) {
        this.offCodeID = offCodeID;
    }

    public int getTotalAmountWithOutOffCode() {
        return totalAmountWithOutOffCode;
    }

    public void setTotalAmountWithOutOffCode(int totalAmountWithOutOffCode) {
        this.totalAmountWithOutOffCode = totalAmountWithOutOffCode;
    }

    public int getTotalAmountWithOffCode() {
        return totalAmountWithOffCode;
    }

    public void setTotalAmountWithOffCode(int totalAmountWithOffCode) {
        this.totalAmountWithOffCode = totalAmountWithOffCode;
    }

    public void setDeliveryState(Delivery deliveryState) {
        this.deliveryState = deliveryState;
    }

    public boolean isWasOffCodeUsed() {
        return wasOffCodeUsed;
    }

    public void setWasOffCodeUsed(boolean wasOffCodeUsed) {
        this.wasOffCodeUsed = wasOffCodeUsed;
    }

    @Override
    public String toString() {
        return "Customer: " + customerUsername + "\n" +
                "Amount without OffCode: " + totalAmountWithOutOffCode + "\n" +
                "Delivery State: " + deliveryState + "\n" +
                toStringOffCodeUsage() + toStringProducts() +
                super.toString();
    }
}
