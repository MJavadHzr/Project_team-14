package Controller;

import Model.Account.Account;
import Model.Account.Boss;
import Model.Account.Role;
import Model.Storage;

import java.util.ArrayList;

public class BossManager {

    public void register(String[] information) {
        if (Storage.isThereAccountWithUsername(information[3])) {
            Server.setAnswer("username has already been taken");
        }
        Server.setAnswer("register successful");
        Server.setHasBoss(true);
        new Boss(information[3], information[4], information[1], information[2], information[6], information[7], information[5]);

    }

    private boolean checkRoleFilter(Account account, String filter) {
        if (filter.contains("boss") && account.getRole().equals(Role.BOSS)) {
            return true;
        } else if (filter.contains("customer") && account.getRole().equals(Role.CUSTOMER)) {
            return true;
        } else if (filter.contains("salesman") && account.getRole().equals(Role.SALESMAN)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isAccountInFilter(Account account, ArrayList<Object> filters) {
        for (int i = 0; i < filters.size(); i += 2) {
            if (((String) filters.get(i)).equalsIgnoreCase("role")) {
                if (checkRoleFilter(account, (String) filters.get(i + 1)) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showAccounts(String username, ArrayList<Object> filters) {
        int count = 0;
        ArrayList<Account> accounts = Storage.getAllAccounts();
        StringBuilder answer = new StringBuilder("");
        if (accounts.size() == 0) {
            Server.setAnswer("nothing found");
        } else {
            for (Account account : accounts) {
                if (!account.getUsername().equals(username) && isAccountInFilter(account, filters)) {
                    answer.append(account.toStringForBoss() + "\n");
                    count++;
                }
            }
            if (count == 0) {
                answer = new StringBuilder("nothing found");
            } else {
                answer.append("here are what we found");
            }
            String ans = answer.toString();
            Server.setAnswer(ans);
        }
    }
    /*
    private CategoryManager categoryManager;
    private OffCodeManager offCodeManager;
    private RequestManager requestManager;

    public BossManager() {
        categoryManager = new CategoryManager();
        offCodeManager = new OffCodeManager();
        requestManager = new RequestManager();
    }

     */
}