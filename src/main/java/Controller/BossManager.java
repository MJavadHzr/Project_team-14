package Controller;

//import Controller.SortFactorEnum.ListOffCodesSortFactor;
//import Controller.SortFactorEnum.ListProductSortFactor;
//import Model.Category.Category;
//import Model.Off.OffCode;
//import Model.Product.Product;
//import Model.Request.Request;

import Controller.SortFactorEnum.AccountSortFactor;
import Controller.SortFactorEnum.CategorySortFactor;
import Controller.SortFactorEnum.OffCodesSortFactor;
import Model.Account.*;
import Model.Category.Category;
import Model.Off.OffCode;
import Model.Off.SpecialOffCode;
import Model.Product.Product;
import Model.Request.Request;
import Model.Storage;

import java.util.ArrayList;

public class BossManager {

    /*
     * this is Account part
     */

    public void register(String[] information) {
        if (information[0].equalsIgnoreCase("register")) {
            if (Storage.isThereAccountWithUsername(information[3])) {
                Server.setAnswer("the username is already taken, try something else");
                return;
            }
            Server.setAnswer("register successful");
            Server.setHasBoss(true);
            Server.setHasBoss(true);
            new Boss(information[3], information[4], information[1], information[2], information[6], information[7],
                    information[5]).setFatherBoss(null);
        } else {
            if (Storage.isThereAccountWithUsername(information[3])) {
                Server.setAnswer("the username is already taken, try something else");
                return;
            }
            Server.setAnswer("register successful");
            Server.setHasBoss(true);
            new Boss(information[3], information[4], information[1], information[2], information[5], information[6],
                    "BOSS").setFatherBoss(information[7]);
        }
    }

    private boolean checkRoleFilter(Account account, String filter) {
        if (filter.contains("boss") && account.getRole().equals(Role.BOSS)) {
            return true;
        } else if (filter.contains("customer") && account.getRole().equals(Role.CUSTOMER)) {
            return true;
        } else return filter.contains("salesman") && account.getRole().equals(Role.SALESMAN);
    }

    private boolean checkMinCreditFilter(Account account, String filter) {
        if (account instanceof Boss) {
            return true;
        } else if (account instanceof Customer) {
            return (account).getCredit() >= Integer.parseInt(filter);
        } else if (account instanceof Salesman) {
            return (account).getCredit() >= Integer.parseInt(filter);
        }
        return false;
    }

    private boolean checkMaxCreditFilter(Account account, String filter) {
        if (account instanceof Boss) {
            return true;
        } else if (account instanceof Customer) {
            return account.getCredit() <= Integer.parseInt(filter);
        } else if (account instanceof Salesman) {
            return account.getCredit() <= Integer.parseInt(filter);
        }
        return false;
    }

    private boolean checkProductSellers(Account account, String filter) {
        if (!(account instanceof Salesman)) {
            return false;
        }
        Product product = Storage.getProductById(filter);
        return product.doesSalesmanSellProductWithUsername(account.getUsername());
    }

    public void viewAccount(String bossUsername, String username) {
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        Server.setAnswer(account.toString());
    }

    public void searchAccount(String username) {
        if (Storage.isThereAccountWithUsername(username)) {
            Server.setAnswer("search completed");
        } else {
            Server.setAnswer("no account exist with this username");
        }
    }

    private boolean isAccountInFilter(Account account, ArrayList<Object> filters) {
        for (int i = 0; i < filters.size(); i += 2) {
            if (((String) filters.get(i)).equalsIgnoreCase("role")) {
                if (!checkRoleFilter(account, (String) filters.get(i + 1))) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("minCredit")) {
                if (!checkMinCreditFilter(account, (String) filters.get(i + 1))) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("maxCredit")) {
                if (!checkMaxCreditFilter(account, (String) filters.get(i + 1))) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("ProductSalesman")) {
                if (!checkProductSellers(account, (String) filters.get(i + 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showAccounts(String username, ArrayList<Object> filters, String sortFactor, String sortType) {
        int count = 0;
        ArrayList<Account> accounts = Storage.getAllAccounts();
        AccountSortFactor.sort(sortFactor, sortType, accounts);
        StringBuilder answer = new StringBuilder("All Accounts Username:").append("\n");
        if (accounts.size() == 0) {
            Server.setAnswer("no account found with this username");
        } else {
            for (Account account : accounts) {
                if (!account.getUsername().equals(username) && isAccountInFilter(account, filters)) {
                    answer.append(account.toStringForBoss()).append("\n");
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

    public void seeAuthorization(String bossUsername, String username) {
        Account account = Storage.getAccountWithUsername(username);
        if (account instanceof Salesman) {
            Server.setAnswer("salesman");
        } else if (account instanceof Customer) {
            Server.setAnswer("customer");
        } else {
            while (true) {
                assert account != null;
                String fatherBoss = ((Boss) account).getFatherBoss();
                if (fatherBoss == null) {
                    Server.setAnswer("boss no");
                    break;
                }
                if (fatherBoss.equals(bossUsername)) {
                    Server.setAnswer("boss yes");
                    break;
                }
                account = Storage.getAccountWithUsername(fatherBoss);
            }
        }
    }

    public static void changeFathers(String bossAccount, String username) {
        for (Account account : Storage.getAllAccounts()) {
            if (((Boss) account).getFatherBoss().equals(username)) {
                ((Boss) account).setFatherBoss(bossAccount);
            }
        }
    }

    private int countWordsBySemicolon(String str) {
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                counter++;
            }
        }
        return counter + 1;
    }

    private boolean checkUsernameFilter(Request request, String username) {
        String[] usernames = username.split(",");
        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i].equals(request.getAccountUsername())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRequestTypeFilter(Request request, String requestType) {
        return requestType.contains(request.getRequestType().toString());
    }

    private boolean checkStateFilter(Request request, String s) {
        return s.contains(request.getConfirmation().toString());
    }

    private boolean isRequestInFilter(Request request, ArrayList<Object> filters) {
        for (int i = 0; i < filters.size(); i += 2) {
            if (((String) filters.get(i)).equalsIgnoreCase("username")) {
                if (!checkUsernameFilter(request, (String) filters.get(i + 1))) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("requestType")) {
                if (!checkRequestTypeFilter(request, (String) filters.get(i + 1))) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("state")) {
                if (!checkStateFilter(request, (String) filters.get(i + 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showRequests(String username, ArrayList<Object> filters) {
        int count = 0;
        ArrayList<Request> requests = Storage.getAllRequests();
        StringBuilder answer = new StringBuilder("");
        if (requests.size() == 0) {
            Server.setAnswer("nothing found");
        } else {
            for (Request request : requests) {
                if (isRequestInFilter(request, filters)) {
                    answer.append(request.toStringForBoss()).append("\n");
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

    public void viewRequest(String username, String requestID) {
        Request request = Storage.getRequestByID(requestID);
        assert request != null;
        Server.setAnswer(request.toString());
    }

    public void searchRequest(String requestID) {
        if (Storage.isThereRequestByID(requestID)) {
            Request request = Storage.getRequestByID(requestID);
            assert request != null;
            Server.setAnswer("search completed " + request.getRequestType());
        } else {
            Server.setAnswer("no request exists with such ID");
        }
    }

    public void deleteRequest(String requestID) {
        Request request = Storage.getRequestByID(requestID);
        Storage.getAllRequests().remove(request);
        Server.setAnswer("deleted successfully");
    }

    /*
     * this is category part
     */

    public void createCategory(String categoryName, String fatherCategoryName, String categoryAttribute) {
        Server.setAnswer("category created");
        if (!Storage.isThereCategoryWithName(categoryName)) {
            if (fatherCategoryName.equalsIgnoreCase("rootCategory")) {
                new Category(categoryName, null, categoryAttribute);
            } else {
                if (Storage.isThereCategoryWithName(fatherCategoryName)) {
                    new Category(categoryName, fatherCategoryName, categoryAttribute);
                } else {
                    Server.setAnswer("there isn't a category with this father category name");
                }
            }
        } else {
            Server.setAnswer("a category with this name has already exists");
        }
    }

    private boolean checkFatherCategoryFilter(Category category, String fatherCategory) {
        if (category.getParentCategoryName() == null) return false;
        String[] fatherCategories = fatherCategory.split(",");
        int wordCount = countWordsBySemicolon(fatherCategory);
        for (int i = 0; i < wordCount; i++) {
            if (category.getParentCategoryName().equals(fatherCategories[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isCategoryInFilter(Category category, ArrayList<Object> filters) {
        for (int i = 0; i < filters.size(); i += 2) {
            if (((String) filters.get(i)).equalsIgnoreCase("fatherCategory")) {
                if (checkFatherCategoryFilter(category, (String) filters.get(i + 1)) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showCategories(String username, ArrayList<Object> filters, String sortFactor, String sortType) {
        int count = 0;
        ArrayList<Category> categories = Storage.getAllCategories();
        CategorySortFactor.sort(sortFactor, sortType, categories);

        StringBuilder answer = new StringBuilder("");
        if (categories.size() == 0) {
            Server.setAnswer("nothing found");
        } else {
            for (Category category : categories) {
                if (isCategoryInFilter(category, filters)) {
                    answer.append(category.toStringForBoss() + "\n");
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

    public void viewCategory(String username, String categoryName) {
        Category category = Storage.getCategoryByName(categoryName);
        Server.setAnswer(category.toString());
    }

    public void searchCategory(String categoryName) {
        if (Storage.isThereCategoryWithName(categoryName)) {
            Category category = Storage.getCategoryByName(categoryName);
            Server.setAnswer("search completed");
        } else {
            Server.setAnswer("no category exists with such ID");
        }
    }

    public void editCategoryName(String oldCategoryName, String newCategoryName) {
        Server.setAnswer("edit successful");
        Category category = Storage.getCategoryByName(oldCategoryName);
        if (Storage.isThereCategoryWithName(newCategoryName)) {
            Server.setAnswer("there is already exists a category with such name");
        } else {
            for (Category subCategory : Storage.getAllCategories()) {
                if (subCategory.getParentCategoryName() == null) {
                    continue;
                }
                if (subCategory.getParentCategoryName().equals(oldCategoryName)) {
                    subCategory.setParentCategoryName(newCategoryName);
                }
            }
            category.setCategoryName(newCategoryName);
        }
    }

    public void editFatherCategory(String categoryName, String newFatherCategory) {
        Server.setAnswer("edit successful");
        Category category = Storage.getCategoryByName(categoryName);
        if (newFatherCategory.equals("none")) {
            category.setParentCategoryName(null);
        }
        if (Storage.isThereCategoryWithName(newFatherCategory)) {
            category.setParentCategoryName(newFatherCategory);
        } else {
            System.out.println("there isn't a category with this name");
        }
    }

    public void addCategoryAttribute(String categoryName, String categoryNewAttribute) {
        Category category = Storage.getCategoryByName(categoryName);
        if (category.getAttribute().equals(categoryNewAttribute)) {
            Server.setAnswer("you added nothing to the attributes");
        } else {
            Server.setAnswer("attributes added successfully");
            category.setAttribute(categoryNewAttribute);
        }
    }

    public void deleteCategoryAttribute(String categoryName, String categoryNewAttribute) {
        Category category = Storage.getCategoryByName(categoryName);
        if (category.getAttribute().equals(categoryNewAttribute)) {
            Server.setAnswer("you deleted nothing from the attributes");
        } else {
            Server.setAnswer("attributes deleted successfully");
            category.setAttribute(categoryNewAttribute);
        }
    }

    public void editCategoryAttribute(String categoryName, String categoryNewAttribute) {
        Category category = Storage.getCategoryByName(categoryName);
        if (category.getAttribute().equals(categoryNewAttribute)) {
            Server.setAnswer("you edited nothing from the attributes");
        } else {
            Server.setAnswer("attributes edited successfully");
            category.setAttribute(categoryNewAttribute);
        }
    }

    public void deleteCategory(String categoryName) {
        Category category = Storage.getCategoryByName(categoryName);
        Storage.getAllCategories().remove(category);
        for (Category subCategory : Storage.getAllCategories()) {
            if (subCategory.getParentCategoryName() == null) {
                continue;
            }
            if (subCategory.getParentCategoryName().equals(categoryName)) {
                subCategory.setParentCategoryName(null);
            }
        }
        /*for (String productID : category.getAllProductIDs()) {
            category.deleteProductFromCategory(productID);
        }*/
        Server.setAnswer("deleted successfully");
    }

    /*
     * this is discount code part
     */

    public void searchOffCode(String offCodeID) {
        if (Storage.isThereOffCodeWithID(offCodeID)) {
            Server.setAnswer("search completed");
        } else {
            Server.setAnswer("no OffCode exist with such ID");
        }
    }

    public void createNormalOffCode(String start, String end, int percentage, int ceiling, int frequency, ArrayList<String> users) {
        new OffCode(start, end, percentage, ceiling, frequency, users);
    }

    public void createSpecialOffCode(int period, int percentage, int ceiling, int frequency) {
        new SpecialOffCode(period, percentage, ceiling, frequency);
    }

    public void viewOffCode(String username, String offCodeID) {
        Account account = Storage.getAccountWithUsername(username);
        if (!Storage.isThereOffCodeWithID(offCodeID)) {
            Server.setAnswer("ERROR: no offCode found with this ID");
        } else {
            OffCode offCode = Storage.getOffCodeById(offCodeID);
            if (account.getRole().equals(Role.CUSTOMER)) {
                if (!offCode.canCustomerUseItWithUsername(username)) {
                    Server.setAnswer("ERROR: you don't access to this offCode");
                } else {
                    Server.setAnswer(((Customer) account).getOffCodeInfo(offCodeID));
                }
            } else {
                Server.setAnswer(offCode.toStringForBoss());
            }
        }
    }

    public void showOffCodes(String username, ArrayList<Object> filters, String sortFactor, String sortType) {
        ArrayList<OffCode> allOffCodes;
        if (Storage.getAccountWithUsername(username).getRole().equals(Role.BOSS)) {
            allOffCodes = Storage.allOffCodes;
        } else {
            allOffCodes = OffCode.getAllCustomerOffCodesByUsername(username);
        }
        OffCodesSortFactor.sort(sortFactor, sortType, allOffCodes);

        StringBuilder ans = new StringBuilder("All OffCodes:");
        boolean found = false;
        for (OffCode offCode : allOffCodes) {
            /*
             * must check filter factor
             */
            if (doOffCodeHasFilterFactor()) {
                ans.append("\n").append(offCode.getOffCodeID()).append("+").append(offCode.getPercentage());
                found = true;
            }
        }
//        ans.append("\n").append("What founded, listed above");
        if (!found) {
            Server.setAnswer("nothing found");
        } else {
            Server.setAnswer(ans.toString());
        }
    }

    //still has word must implement it in OffCode Class
    public boolean doOffCodeHasFilterFactor() {
        return true;
    }

    public void editOffCode(String offCodeID, String attribute, String updatedInfo) {
        if (!Storage.isThereOffCodeWithID(offCodeID)) {
            Server.setAnswer("ERROR: there isn't any offCode with this ID");
        } else {
            StringBuilder ans = new StringBuilder("OffCode Edited Successfully");
            OffCode offCode = Storage.getOffCodeById(offCodeID);
            if (attribute.equalsIgnoreCase("start date")) {
                ans.append("\n").append("START DATE edited");
                offCode.setStart(updatedInfo);
            } else if (attribute.equalsIgnoreCase("end date")) {
                ans.append("\n").append("END DATE edited");
                offCode.setEnd(updatedInfo);
            } else if (attribute.equalsIgnoreCase("percentage")) {
                ans.append("\n").append("PERCENTAGE edited");
                offCode.setPercentage(updatedInfo);
            } else if (attribute.equalsIgnoreCase("ceiling")) {
                ans.append("\n").append("MAX edited");
                offCode.setCeiling(Integer.parseInt(updatedInfo));
            }
            Server.setAnswer(ans.toString());
        }
    }

    public void requestUsername(String username) {
        String ans = "";
        for (Account account : Storage.getAllAccounts()) {
            if (account.getRole().equals(Role.BOSS)) continue;
            if (ans.equals("")) {
                ans = account.getUsername();
            } else {
                ans += "+" + account.getUsername();
            }
        }
        Server.setAnswer(ans);
    }

    /*public void listAllProducts(String sortFactor) throws SortFactorNotAvailableException {
        ArrayList<Product> products = new ArrayList<>(Storage.allProducts);
        ListProductSortFactor.sort(sortFactor, products);
        StringBuilder ans = new StringBuilder("Here are All products:").append("\n");
        for (Product product : products) {
            ans.append(product.toStringForBossView());
        }
        Server.setAnswer(ans.toString());
    }*/

    /*public String getSortFactorsForListingAllProducts() {
        return ListProductSortFactor.getValues();
    }*/

    /*public void deleteProduct(String productID) {
        if (Product.getProductWithID(productID) == null) {
            Server.setAnswer("error, there isn't any product whit this ID");
        } else {
            Storage.allProducts.remove(Product.getProductWithID(productID));
            Server.setAnswer("product with id [" + productID + "] removed successfully");
        }
    }*/

    /*
     * this is discount codes part
     */

    //info: 0 -> start, 1 -> end, 2 -> percentage, 3 -> ceiling, 4 -> numberOfTimeCanBeUsed

    /*public void createDiscountCode(String[] info, ArrayList<String> usernameCanUse) throws ParseException {
        new OffCode(info[0], info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]), usernameCanUse);
        Server.setAnswer("offCode created successfully");
    }

    public void listAllOffCodes(String sortFactor) throws SortFactorNotAvailableException {
        ArrayList<OffCode> offCodes = new ArrayList<>(Storage.allOffCodes);
        StringBuilder ans = new StringBuilder("Here are All OffCodes:").append("\n");
        ListOffCodesSortFactor.sort(sortFactor, offCodes);
        for (OffCode offCode : offCodes) {
            ans.append(offCode.toString());
        }
        Server.setAnswer(ans.toString());
    }

    public static String getSortFactorsForListingOffCodes() {
        return ListOffCodesSortFactor.getValues();
    }

    public void viewOffCode(String offCodeID) throws InvalidOffCodeException {
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCode == null) {
            throw new InvalidOffCodeException("off code ID is not authentic");
        } else {
            Server.setAnswer("OffCode detail: " + "\n" + offCode.toString());
        }
    }

    public void editOffCode(String offCodeID, String attribute, String updatedInfo) throws ParseException, InvalidOffCodeException {
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCode == null) {
            throw new InvalidOffCodeException("off code ID is not authentic");
        } else {
            switch (attribute) {
                case "end":
                    offCode.setEnd(updatedInfo);
                    break;
                case "percentage":
                    offCode.setPercentage(updatedInfo);
                    break;
                case "ceiling":
                    offCode.setCeiling(Integer.parseInt(updatedInfo));
                    break;
                case "numberOfTimesCanBeUsed":
                    offCode.setNumberOfTimesCanBeUsed(Integer.parseInt(updatedInfo));
                    break;
            }
            Server.setAnswer("you updated offCode with ID [" + offCodeID + "]");
        }
    }

    public void removeOffCode(String offCodeID) throws InvalidOffCodeException {
        if (OffCode.getOffCodeByID(offCodeID) == null) {
            throw new InvalidOffCodeException("off code ID is not authentic");
        } else {
            Storage.allOffCodes.remove(OffCode.getOffCodeByID(offCodeID));
            Server.setAnswer("offCode with ID [" + offCodeID + "] removed successfully");
        }
    }*/

}
