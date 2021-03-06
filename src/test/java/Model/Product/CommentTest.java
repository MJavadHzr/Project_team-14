package Model.Product;

import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Confirmation;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CommentTest {

    Salesman salesman = new Salesman("salesmanUser", "password", "firstname", "secondName",
            "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company", 1000);

    Customer customer = new Customer("customerUser", "password", "firstname", "secondName",
            "h.hafezi2000@yahoo.com", "09333805288", "CUSTOMER", 1000);

    Product product = new Product("name", salesman.getUsername(), "brand", "description", 10, 10);

    Product product2 = new Product("name2", salesman.getUsername(), "brand2", "description2", 10, 10);

    @Test
    public void getComment2() {
        Comment comment = new Comment("title","text", customer.getUsername(), product.getProductID());
        Assert.assertTrue(comment.isChecking());
        Assert.assertNull(Comment.getCommentByID("aaaaaaaaaaaa"));
        Assert.assertNotNull(Comment.getCommentByID(comment.getCommentID()));
    }

    @Test
    public void getCheckingComment() {
        ArrayList<String> arrayList = new ArrayList<>(Comment.getCheckingComments());
        Assert.assertEquals(arrayList.size(), 1);
    }

    @Test
    public void toStringForProductView() {
        Comment comment = new Comment("title","text", customer.getUsername(), product.getProductID());
        Assert.assertTrue(comment.toStringForProductView().startsWith("Sender: customerUser+Title: title+Message: text+Date:"));
    }

    @Test
    public void toStringForChecking() {
        Comment comment = new Comment("title","text", customer.getUsername(), product.getProductID());
        String result = "Product Name: name\n" +
                "Sender: customerUser\n" +
                "Title: title\n" +
                "Message: text";
        System.out.println(comment.toStringForChecking());
        Assert.assertTrue(comment.toStringForChecking().contains(result));
    }

    @Test
    public void isThereCommentForProduct1() {
        Comment comment = new Comment("title","text", customer.getUsername(), product.getProductID());
        Assert.assertFalse(comment.isConfirmed());
        comment.setConfirmationState(Confirmation.ACCEPTED);
        Assert.assertTrue(Comment.isThereCommentForProduct(product.getProductID()));
    }

    @Test
    public void isThereCommentForProduct2() {
        Comment comment = new Comment("title","text", customer.getUsername(), product.getProductID());
        comment.setConfirmationState(Confirmation.DENIED);
        Assert.assertFalse(Comment.isThereCommentForProduct(product.getProductID()));
    }

    @Test
    public void isThereCommentForProduct3() {
        Assert.assertFalse(Comment.isThereCommentForProduct(product2.getProductID()));
    }


    @Test
    public void getProductID() {
        Comment comment = new Comment("title","text", customer.getUsername(), product.getProductID());
        Assert.assertEquals(product.getProductID(), comment.getProductID());
    }

    @Test
    public void createString() {
        Comment comment = new Comment("title","text", customer.getUsername(), product.getProductID());
        Assert.assertTrue(comment.createID().startsWith("Comment---"));
    }

    @Test
    public void getCommentsForProduct() {
        Comment comment = new Comment("title","text", customer.getUsername(), product.getProductID());
        Comment comment2 = new Comment("title","text", customer.getUsername(), product.getProductID());
        Comment comment3 = new Comment("title","text", customer.getUsername(), product.getProductID());
        comment.setConfirmationState(Confirmation.ACCEPTED);
        comment2.setConfirmationState(Confirmation.ACCEPTED);
        comment3.setConfirmationState(Confirmation.ACCEPTED);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(comment.getCommentID());
        arrayList.add(comment2.getCommentID());
        arrayList.add(comment3.getCommentID());
        Assert.assertEquals(Comment.getCommentsForProductWithID(product.getProductID()), arrayList);
    }

    @Test
    public void getCommentsForProductStringFormatted() {
        Comment comment = new Comment("title","text", customer.getUsername(), product.getProductID());
        Comment comment2 = new Comment("title","text", customer.getUsername(), product.getProductID());
        Comment comment3 = new Comment("title","text", customer.getUsername(), product.getProductID());
        comment.setConfirmationState(Confirmation.ACCEPTED);
        comment2.setConfirmationState(Confirmation.ACCEPTED);
        comment3.setConfirmationState(Confirmation.ACCEPTED);
        String result = comment.toStringForProductView() + comment2.toStringForProductView() + comment3.toStringForProductView();
        Assert.assertEquals(result, Comment.getCommentsForProductStringFormatted(product.getProductID()));

    }

}
