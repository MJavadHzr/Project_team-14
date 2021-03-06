package GUI.Cart;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class Pay {
    public Button buyFromCreditButton;
    public Label creditLabel;
    public Label creditEnoughLabel;
    public PasswordField password;
    public Button buyFromBank;
    public Label haveBankAccount1;
    public Label haveBankAccount;
    public Label passwordField;

    private String username = MenuHandler.getUsername();
    private String token;

    public void buyFromCredit() throws ParseException, IOException {
        StringBuilder products = new StringBuilder("buy+" + MenuHandler.getUsername() + "+" + Controller.controller.usedOffCode + "\n");
        for (Cart cart : Controller.controller.list) {
            products.append(cart.getSalesman()).append("+").append(cart.getProductId()).append("+").append(cart.getCount()).append("\n");
        }
        MenuHandler.getConnector().clientToServer(products.toString());
        String response = MenuHandler.getConnector().serverToClient();
        if (response.startsWith("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, response, ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, response, ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void ByFromBankButton(ActionEvent actionEvent) throws IOException, ParseException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        MenuHandler.getConnector().clientToServer("bank " + "get token+" + username + "+" + password.getText());
        String token = MenuHandler.getConnector().serverToClient();
        if (!token.equals("fuck off, identification was wrong") && !token.equals("something went wrong")) {
            alert.setContentText("login successful");
            alert.showAndWait();
            this.token = token;
            MenuHandler.getConnector().clientToServer("bank " + "get money from+" + token + "+" + username + "+" + Controller.controller.ans);
            alert.setContentText(MenuHandler.getConnector().serverToClient());
            alert.showAndWait();
            MenuHandler.getConnector().clientToServer("add balance+" + username + "+" + Controller.controller.ans);
            buyFromCredit();
            return;
        }
        alert.setContentText("wrong identification");
        alert.showAndWait();
    }

    public void goToBankPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Cart/Cart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize() throws IOException, ParseException {
        creditLabel.setText("your credit: " + MenuHandler.getCredit());
        if (Controller.controller.ans > MenuHandler.getCredit()) {
            creditEnoughLabel.setText("your credit isn't enough");
            buyFromCreditButton.setDisable(true);
            buyFromCreditButton.setVisible(false);
        }
        MenuHandler.getConnector().clientToServer("bank " + "is there person with username+" + MenuHandler.getUsername());
        String answer = MenuHandler.getConnector().serverToClient();
        if (!answer.equals("true")) {
            buyFromCreditButton.setDisable(true);
            haveBankAccount.setVisible(true);
            haveBankAccount1.setVisible(true);
            password.setVisible(false);
            passwordField.setVisible(false);
        } else {
            buyFromCreditButton.setDisable(false);
            haveBankAccount.setVisible(false);
            haveBankAccount1.setVisible(false);
            password.setVisible(true);
            passwordField.setVisible(true);
        }
    }

}
