package Controller.DataBase;

import Model.Account.Account;
import Model.Account.Boss;
import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Cart.Cart;
import Model.Category.Category;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import Model.Off.OffCode;
import Model.Off.Sale;
import Model.Off.SpecialOffCode;
import Model.Product.Comment;
import Model.Product.Point;
import Model.Product.Product;
import Model.Request.Request;
import Model.Storage;

import java.io.*;

import static Model.Storage.*;

public class startOfProgramme extends DataBase {

    public void startProgramme() throws IOException, ClassNotFoundException {
        makeDirectories();
        updateFiles();
    }

    private void updateFiles() throws IOException, ClassNotFoundException {
        updateBosses();
        updateBuyLogs();
        updateCategories();
        updateComments();
        updateCustomers();
        updateOffCodes();
        updatePoints();
        updateProducts();
        updateSales();
        updateSalesmen();
        updateSpecialOffCodes();
        updateSellLogs();
        updateCarts();
        updateRequests();
    }

    @Override
    protected void updateCustomers() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Customers";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Account s = (Account) in.readObject();
            Storage.getAllAccounts().add(s);
        }
    }

    @Override
    protected void updateBosses() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Bosses";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Account s = (Account) in.readObject();
            Storage.getAllAccounts().add(s);
        }
    }

    @Override
    protected void updateSalesmen() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Salesmen";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Account s = (Account) in.readObject();
            Storage.getAllAccounts().add(s);
        }
    }

    @Override
    protected void updateCategories() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Categories";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Category s = (Category) in.readObject();
            getAllCategories().add(s);
        }
    }

    @Override
    protected void updateBuyLogs() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\BuyLogs";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            BuyLog s = (BuyLog) in.readObject();
            allBuyLogs.add(s);
        }

    }

    @Override
    protected void updateSales() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Sales";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Sale s = (Sale) in.readObject();
            allSales.add(s);
        }
    }

    @Override
    protected void updateOffCodes() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\OffCodes";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            OffCode s = (OffCode) in.readObject();
            allOffCodes.add(s);
        }
    }

    @Override
    protected void updateProducts() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Products";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Product s = (Product) in.readObject();
            Storage.getAllProducts().add(s);
        }
    }

    @Override
    protected void updateComments() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Comments";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Comment s = (Comment) in.readObject();
            allComments.add(s);
        }
    }


    @Override
    protected void updateSpecialOffCodes() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\SpecialOffCodes";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            SpecialOffCode s = (SpecialOffCode) in.readObject();
            allSpecialOffCodes.add(s);
        }

    }

    @Override
    protected void updatePoints() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Points";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Point s = (Point) in.readObject();
            allPoints.add(s);
        }
    }

    @Override
    protected void updateRequests() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Requests";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Request s = (Request) in.readObject();
            getAllRequests().add(s);
        }
    }


    @Override
    protected void updateCarts() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Carts";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Cart s = (Cart) in.readObject();
            allCarts.add(s);
        }

    }

    @Override
    protected void updateSellLogs() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\SellLogs";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            SellLog s = (SellLog) in.readObject();
            allSellLogs.add(s);
        }
    }
}
