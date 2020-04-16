package Model.Account;

import java.io.*;
import java.util.ArrayList;

public class Boss extends Account implements Serializable {
    private ArrayList<Boss> allBosses = new ArrayList<>();

    public Boss(String username, String password, String firstName, String secondName, String Email, String telephone, String role) {
        super(username, password, firstName, secondName, Email, telephone, role);
        allBosses.add(this);
    }

    public boolean isThereBoss() {
        return !allBosses.isEmpty();
    }

    @Override
    public String toString() {
        return this.toStringGenerals();
    }
}