package GUI.SalesmanProfile.ManageSale;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

public class ViewSaleManager {
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField percentage;
    public TextField confirmationStatus;
    public Label errorsLabel;
    public ListView<String> productList;
    public String initStartDate;
    public String initEndDate;
    public String initPercentage;

    @FXML
    public void initialize() throws IOException, ParseException {
        String toServer = "view sale" + "+" + MenuHandler.getUsername() + "+" + MenuHandler.getSeeingSale();
        MenuHandler.getServer().clientToServer(toServer);
        String respond = MenuHandler.getServer().serverToClient();

        String[] lines = respond.split("\n");
        startDate.setValue(LocalDate.parse(lines[1].substring(11)));
        endDate.setValue(LocalDate.parse(lines[2].substring(9)));
        percentage.setText(lines[0].substring(11));
        confirmationStatus.setText(lines[3].substring(13));

        initStartDate = startDate.getValue().toString();
        initEndDate = endDate.getValue().toString();
        initPercentage = percentage.getText();

        for (int i = 5; i < lines.length; i++) {
            productList.getItems().add(lines[i]);
        }
    }

    public void saveChanges(MouseEvent mouseEvent) throws ParseException, IOException {
        Audio.playClick5();
        try {
            checkHasChange();
        } catch (Exception e) {
            errorsLabel.setText(e.getMessage());
            return;
        }

        StringBuilder respond = new StringBuilder();
        if (!startDate.getValue().toString().equals(initStartDate)) {
            String toServer = "edit sale" + "+" + MenuHandler.getSeeingSale() + "+" + "Start Date" + "+" + startDate.getValue().toString();
            MenuHandler.getServer().clientToServer(toServer);
            respond.append(MenuHandler.getServer().serverToClient()).append("\n");
        }
        if (!endDate.getValue().toString().equals(initEndDate)) {
            String toServer = "edit sale" + "+" + MenuHandler.getSeeingSale() + "+" + "End Date" + "+" + endDate.getValue().toString();
            MenuHandler.getServer().clientToServer(toServer);
            respond.append(MenuHandler.getServer().serverToClient()).append("\n");
        }
        if (!percentage.getText().equals(initPercentage)) {
            String toServer = "edit sale" + "+" + MenuHandler.getSeeingSale() + "+" + "percentage" + "+" + percentage.getText();
            MenuHandler.getServer().clientToServer(toServer);
            respond.append(MenuHandler.getServer().serverToClient()).append("\n");
        }

        if (respond.toString().startsWith("ERRORS")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, respond.toString(), ButtonType.OK);
            ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
            ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, respond.toString(), ButtonType.OK);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.showAndWait();

        ((Button) mouseEvent.getSource()).getScene().getWindow().hide();

    }

    public void back(MouseEvent mouseEvent) {
        Audio.playClick5();
        ((Button) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    private void checkHasChange() throws Exception {
        if (startDate.getValue().toString().equals(initStartDate) & endDate.getValue().toString().equals(initEndDate) &
                percentage.getText().equals(initPercentage)) throw new Exception("You haven't changed anything");
        if (startDate.getValue().isAfter(endDate.getValue())) throw new Exception("End Date must be after Start Date");
    }
}
