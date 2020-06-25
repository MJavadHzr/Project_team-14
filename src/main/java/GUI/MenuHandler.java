package GUI;

import Controller.Server;
import javafx.scene.layout.Pane;

public class MenuHandler {
    static private String username = null;
    static private Server server;
    static private String productID = null;
    static private String requestID = null;
    static private String seeingUsername = null;
    static private String seeingOffCode = null;
    static private boolean isUserLogin = false;
    static private String userType = null;
    static private String loginBackAddress = null;
    static private Pane pane;

    public static String getRequestID() {
        return requestID;
    }

    public static void setRequestID(String requestID) {
        MenuHandler.requestID = requestID;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MenuHandler.username = username;
    }

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        MenuHandler.server = server;
    }

    public static boolean isIsUserLogin() {
        return isUserLogin;
    }

    public static void setIsUserLogin(boolean isUserLogin) {
        MenuHandler.isUserLogin = isUserLogin;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        MenuHandler.userType = userType;
    }

    public static String getLoginBackAddress() {
        return loginBackAddress;
    }

    public static void setLoginBackAddress(String loginBackAddress) {
        MenuHandler.loginBackAddress = loginBackAddress;
    }

    public static String getSeeingUsername() {
        return seeingUsername;
    }

    public static void setSeeingUsername(String seeingUsername) {
        MenuHandler.seeingUsername = seeingUsername;
    }

    public static String getSeeingOffCode() {
        return seeingOffCode;
    }

    public static void setSeeingOffCode(String seeingOffCode) {
        MenuHandler.seeingOffCode = seeingOffCode;
    }

    public static String getProductID() {
        return productID;
    }

    public static void setProductID(String productID) {
        MenuHandler.productID = productID;
    }

    public static Pane getPane() {
        return pane;
    }

    public static void setPane(Pane pane) {
        MenuHandler.pane = pane;
    }
}
