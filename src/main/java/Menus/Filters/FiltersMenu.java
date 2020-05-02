package Menus.Filters;

import Menus.Menu;

import java.util.ArrayList;

public class FiltersMenu extends Menu {
    protected ArrayList<Object> filters;

    public FiltersMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
    }

    public String getFilters() {
        String filter = new String("");
        for (int i = 0; i < filters.size(); i += 2) {
            if (filters.get(i + 1) != null) {
                filter += " ";
                filter += filters.get(i);
                filter += " ";
                filter += filters.get(i + 1);
            }
        }
        if (filter.equals("")) {
            return filter;
        } else {
            return " filters:" + filter;
        }
    }
}