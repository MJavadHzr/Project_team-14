package Menus.Manages;

import Controller.Server;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ManageOffCodesMenu extends Menu {
    public ManageOffCodesMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new ShowOffCodesMenu(this, "Show OffCodes Menu"));
        subMenus.put(2, getSearchOffCodeMenu());
        subMenus.put(3, getCreateNormalOffCodeMenu());
        subMenus.put(4, getCreateSpecialOffCodeMenu());
        subMenus.put(5, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getSearchOffCodeMenu() {
        return new Menu(this, "Search OffCode Menu") {

            @Override
            public void execute() {
                System.out.println(menuName);
                System.out.println("if you input back we will go back");
                System.out.println("please input offCode ID:");
                String offCodeID = scanner.nextLine();
                server.clientToServer("search offCode+" + Menu.username + "+" + offCodeID);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equalsIgnoreCase("search completed")) {
                    //must add view
                } else {
                    fatherMenu.execute();
                }
            }
        };
    }

    private Menu getCreateNormalOffCodeMenu() {
        return new Menu(this, "Create Normal OffCode Menu") {

            @Override
            public void execute() {
                System.out.println(this.menuName);
                System.out.println("if you input back we will go back");

                //get info from user
                System.out.println("enter Start Time and End Time in separate lines:(format: mm/dd/yyyy)");
                String startTime = scanner.nextLine();
                checkBack(startTime);
                String endTime = scanner.nextLine();
                checkBack(endTime);
                System.out.println("How much do you want to lower the price :(enter a number from 1 to 100)");
                String offPercentage = scanner.nextLine();
                checkBack(offPercentage);
                System.out.println("What would be the maximum of this discount:");
                String ceiling = scanner.nextLine();
                checkBack(ceiling);
                System.out.println("How many time users can use this:");
                String frequency = scanner.nextLine();
                checkBack(frequency);
                System.out.println("Now you can add those users you want to use this code:");
                ArrayList<String> usernameCanUseIt = getUserNames();

                //send info to server
                String message = "create new normal offCode+" + startTime + "+" + endTime + "+" + offPercentage + "+" +
                        ceiling + "+" + frequency + "+" + "Users:" + usernameCanUseIt;
                server.clientToServer(message);
                System.out.println(server.serverToClient());
                if (server.serverToClient().equals("creation of offCode successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }

            private void checkBack(String command) {
                if (command.equalsIgnoreCase("back")) {
                    fatherMenu.execute();
                }
            }

            private ArrayList<String> getUserNames() {
                System.out.println("-add [username] : to add\n-delete [username] : to delete in case of mistaken addition\n-DONE : to end addition");
                ArrayList<String> ans = new ArrayList<>();
                String command;
                while (!(command = scanner.nextLine()).equalsIgnoreCase("done")) {
                    if (command.split("\\s")[0].equalsIgnoreCase("add")) {
                        addUsernameToArray(command.split("\\s")[1], ans);
                    } else if (command.split("\\s")[0].equalsIgnoreCase("delete")) {
                        deleteUsernameFromArray(command.split("\\s")[1], ans);
                    } else {
                        System.err.println("-add [username] : to add\n-delete [username] : to delete in case of mistaken addition\n-DONE : to end addition");
                    }
                }
                return ans;
            }

            private void addUsernameToArray(String username, ArrayList<String> array) {
                //server.clientToServer("is user exist+" + username);
                server.clientToServer("search account+" + Menu.username + "+" + username);
                if (server.serverToClient().equalsIgnoreCase("search completed")) {
                    array.add(username);
                }
                else {
                    System.err.println("this user doesn't exist, try another one or type DONE to finish addition");
                }
            }

            private void deleteUsernameFromArray(String username, ArrayList<String> array) {
                if (array.contains(username)) {
                    array.remove(username);
                }
                else {
                    System.err.println("you haven't add this user to your list yet");
                }
            }
        };
    }

    private Menu getCreateSpecialOffCodeMenu() {
        return new Menu(this, "Create Special OffCode Menu") {

            @Override
            public void execute() {
                System.out.println(this.menuName);
                System.out.println("if you input back we will go back");

                //get info from user
                System.out.println("How often do you want to make a random code:(scale: Day)");
                String period = scanner.nextLine();
                checkBack(period);
                System.out.println("How much do you want to discount:(enter a number from 1 to 100)");
                String percentage = scanner.nextLine();
                checkBack(percentage);
                System.out.println("What is the discount ceiling:");
                String ceiling = scanner.nextLine();
                checkBack(ceiling);
                System.out.println("How many times users can use it:");
                String frequency = scanner.nextLine();
                checkBack(frequency);

                //set info to server
                String message = "create new special offCode+" + period + "+" + percentage + "+" + ceiling + "+" + frequency;
                server.clientToServer(message);
                System.out.println(server.serverToClient());
                if (server.serverToClient().equals("creation of offCode successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }

            private void checkBack(String command) {
                if (command.equalsIgnoreCase("back")) {
                    fatherMenu.execute();
                }
            }
        };
    }
}
