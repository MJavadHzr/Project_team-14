package Model;

import Model.Account.Account;
import Model.Account.Boss;
import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Cart.Cart;
import Model.Category.Category;
import Model.Log.BuyLog;
import Model.Off.OffCode;
import Model.Off.Sale;
import Model.Off.SpecialOffCode;
import Model.Product.Comment;
import Model.Product.Point;
import Model.Product.Product;
import Model.Request.Request;

import java.util.ArrayList;

public class Storage implements Runnable {
    public static ArrayList<Comment> allComments = new ArrayList<>();
    public static ArrayList<Point> allPoints = new ArrayList<>();
    public static ArrayList<Product> allProducts = new ArrayList<>();
    public static ArrayList<OffCode> allOffCodes = new ArrayList<>();
    public static ArrayList<Sale> allSales = new ArrayList<>();
    public static ArrayList<BuyLog> allBuyLogs = new ArrayList<>();
    public static ArrayList<Category> allCategories = new ArrayList<>();
    public static ArrayList<Account> allAccounts = new ArrayList<>();
    public static ArrayList<SpecialOffCode> allSpecialOffCodes = new ArrayList<>();
    public static ArrayList<Cart> allCarts = new ArrayList<>();
    public static ArrayList<Request> allRequest = new ArrayList<>();

    public static boolean isThereAccountWithUsername(String username) {
        return getAccountWithUsername(username) != null;
    }

    public static Account getAccountWithUsername(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public static ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> arrayList = new ArrayList<>();
        for (Account account : allAccounts) {
            if (account instanceof Customer) {
                arrayList.add((Customer) account);
            }
        }
        return arrayList;
    }

    public static ArrayList<Boss> getAllBosses() {
        ArrayList<Boss> arrayList = new ArrayList<>();
        for (Account account : allAccounts) {
            if (account instanceof Boss) {
                arrayList.add((Boss) account);
            }
        }
        return arrayList;
    }

    public static ArrayList<Salesman> getAllSalesmen() {
        ArrayList<Salesman> arrayList = new ArrayList<>();
        for (Account account : allAccounts) {
            if (account instanceof Salesman) {
                arrayList.add((Salesman) account);
            }
        }
        return arrayList;
    }

    public void garbageCollector() {
        garbageCollectorComments();
        garbageCollectorRequests();
        garbageCollectorSales();
        garbageCollectorSalesmen();
        garbageCollectorProducts();
    }

    public void garbageCollectorComments() {

    }

    public void garbageCollectorRequests() {

    }

    public void garbageCollectorSales() {

    }

    public void garbageCollectorSalesmen() {

    }

    public void garbageCollectorProducts() {

    }

    @Override
    public void run() {
        garbageCollector();
        System.gc();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}