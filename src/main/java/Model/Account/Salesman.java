package Model.Account;

import Model.Confirmation;

import java.io.*;

public class Salesman extends Account implements Serializable {
    private String company;
    private Confirmation confirmationState;
    private int credit;

    public Salesman(String username, String password, String firstName, String secondName, String Email, String telephone, String role, String company) {
        super(username, password, firstName, secondName, Email, telephone, role);
        this.company = company;
        this.credit = 0;
        confirmationState = Confirmation.CHECKING;
    }

    public void setConfirmationState(Confirmation confirmationState) {
        this.confirmationState = confirmationState;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isConfirmed() {
        return confirmationState.equals(Confirmation.ACCEPTED);
    }

    public String toString() {
        String result = super.toString();
        result += "Company: " + this.getCompany() + "\n";
        result += "Confirmation State: " + this.confirmationState.toString() + "\n";
        return result;
    }

}
