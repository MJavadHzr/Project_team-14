package Menus.Edits;

import Menus.Menu;

public class EditsMenu extends Menu {
    private String itemID;
    private String type;
    private String attribute;
    private String updatedInfo;

    public EditsMenu(Menu fatherMenu, String menuName, String type, String itemID) {
        super(fatherMenu, menuName);
        this.type = type;
        this.itemID = itemID;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setUpdatedInfo(String updatedInfo) {
        this.updatedInfo = updatedInfo;
    }

    public Menu getChangeSubMenu(Menu fatherMenu, String wantedAttribute, String format) {
        return new Menu(fatherMenu, "Edit " + wantedAttribute + " Menu (Changeable)") {

            @Override
            public void execute() {
                System.out.println(menuName);
                System.out.println("if you input back we will go back");

                //get info
                System.out.println("Insert the new value of " + wantedAttribute + " :" + format);
                String updatedInfo = scanner.nextLine();
                checkBack(updatedInfo);

                ((EditsMenu) fatherMenu).setAttribute(wantedAttribute);
                ((EditsMenu) fatherMenu).setUpdatedInfo(updatedInfo);
                String serverAnswer = ((EditsMenu) fatherMenu).sendInfoToServerToChange();
                System.out.println(serverAnswer);
                if (serverAnswer.equalsIgnoreCase("edit successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    /*public Menu getAdditionSubMenu(Menu fatherMenu, String wantedAttribute) {
        return new Menu(fatherMenu, "Edit " + wantedAttribute + " Menu (Addable)") {

            @Override
            public void execute() {
                System.out.println(menuName);
                System.out.println("if you input back we will go back");

                //get info
                System.out.println("\nCurrent Items:\n");
                ((EditsMenu) fatherMenu).setAttribute(wantedAttribute);
                String currentItem = ((EditsMenu) fatherMenu).getListFromServer();
                System.out.println(currentItem);

            }
        };
    }*/

    /*public String getListFromServer() {
        server.clientToServer("list " + type + " " + attribute + "+" + itemID);
        return server.serverToClient();
    }*/

    public String sendInfoToServerToChange() {
        server.clientToServer("edit " + type + "+" + itemID + "+" + attribute + "+" + updatedInfo);
        return server.serverToClient();
    }
}
