package Controller;

import Model.Category.Category;
import Model.Product.Product;
import Model.Storage;

import java.util.ArrayList;

public class PageManager {

    /*
     * -------[ this part is for all products ]-------
     */

    //filter factor: 0 -> [category name] , 1-> [be in sale:boolean]

    public void showProducts(String sortFactor, ArrayList<String> filterFactor) {
        Category category = Storage.getCategoryByName(filterFactor.get(0));
        assert category != null;
        ArrayList<Product> products = new ArrayList<>(ProductManager.getArrayListOfProductsFromArrayListOfProductIDs(category.getAllProductIDs()));
        StringBuilder ans = new StringBuilder("All Products in [" + filterFactor.get(0) + "] category:");
        //ProductSortFactor.sort(sortFactor, products);
        for (Product product : products) {
            ans.append("\n").append(product.toStringForCustomerView());
        }
        Server.setAnswer(ans.toString());
    }

    public void goToProductPage(String productID) throws Exception {
        Product product = Storage.getProductById(productID);
        if (product == null) {
            throw new Exception("the product ID is invalid");
        } else {
            product.increaseSeenCount();
            Server.setAnswer("successful");
        }
    }

    /*
     * -------[ this part is for single product ]-------
     */

    public void showProductDigest(String productID) {
        Product product = Storage.getProductById(productID);
        assert product != null;
        Server.setAnswer(product.toStringForCustomerView());
    }

    public void getProductSeller(String productID) {
        Product product = Storage.getProductById(productID);
        if (product == null) {
            Server.setAnswer("error, product doesn't exist");
        } else {
            StringBuilder productSellers = new StringBuilder("This salesman sell this product, choose one:");
            for (String salesmanID : product.getSeller()) {
                productSellers.append("\n").append(salesmanID);
            }
            Server.setAnswer(productSellers.toString());
        }
    }

    /*public void addProductToCart(String username, String productID, String salesmanID) {
        Cart cart = Cart.getCartBasedOnUsername(username);
        if (cart.addProductToCart(productID, salesmanID, cart.getCartID())) {
            Server.setAnswer("product added to your cart successfully");
        } else {
            Server.setAnswer("error, product has sold out, come back soon");
        }
    }

    public void compareProduct(String productID1, String productID2) throws InvalidProductIDException {
        Product product1 = Product.getProductWithID(productID1);
        Product product2 = Product.getProductWithID(productID2);
        if (product1 == null | product2 == null) {
            throw new InvalidProductIDException("the product ID is invalid");
        }
        String ans = "Comparision [" + productID1 + " VS " + productID2 + "] :" + "Name:\t\t" + product1.getName() + "\t\t\t\t\t\t" + product2.getName() +
                "Brand:\t\t" + product1.getBrand() + "\t\t\t\t\t\t" + product2.getBrand() +
                "Description:\t\t" + product1.getDescription() + "\t\t\t\t\t\t" + product2.getDescription() +
                "Min Price:\t\t" + product1.getMinimumPrice() + "\t\t\t\t\t\t" + product2.getMinimumPrice();
        Server.setAnswer(ans);
    }

    public void showCommentAndPointOfProduct(String productID) throws InvalidProductIDException {
        Product product = Product.getProductWithID(productID);
        if (product == null) {
            throw new InvalidProductIDException("the product ID is invalid");
        }
        StringBuilder ans = new StringBuilder("Average point of this product : " + product.getAveragePoint());
        ans.append("\nComments:\n").append(Comment.getCommentsForProductStringFormatted(productID));
    }

    public void addComment(String title, String content, String username, String productID) {
        new Comment(title, content, username, productID);
    }*/

}
