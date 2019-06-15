import entity.Admin;
import entity.Customer;
import entity.Transaction;
import entity.User;
import exceptions.AccountNotFoundException;
import exceptions.NotEnoughCashException;
import javafx.util.Pair;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    List<User> users = new ArrayList<>();
    List<Transaction> transactions = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    int lastId;

    App() {
        Admin admin = new Admin(
                new Admin.ActionsCallback() {
                    @Override
                    public void onCreateCustomer(Customer customer) {
                        int id = getNewId();
                        customer.setUserId(id);
                        System.out.println("account id: " + id);
                        users.add(customer);
                    }
                }
        );
        lastId = admin.getUserId();
        users.add(admin);
    }

    private int getNewId() {
        return ++lastId;
    }

    void start() {
        loginUser();
    }

    void loginUser() {
        while (true) {
            System.out.println("username: ");
            String username = scanner.nextLine();
            System.out.println("password: ");
            String password = scanner.nextLine();
            User user = new User() {
                @Override
                public void start() {

                }
            };
            user.setUserName(username);
            user.setPassword(password);
            boolean find = true;
            for (User user1 : users) {
                if (user1.getUserName().equals(username) && user1.getPassword().equals(password)) {
                    find = false;
                    if (user1 instanceof Admin) {
                        System.out.println("hello admin");
                    } else if (user1 instanceof Customer) {
                        Customer customer = (Customer) user1;
                        customer.setCallback(new Customer.ActionCallback() {
                            @Override
                            public void onTransaction(int targetId, int amount) throws AccountNotFoundException, NotEnoughCashException {
                                Customer targetCustomer = null;
                                for (User user2 : users) {
                                    if (user2.getUserId() == targetId) {
                                        if (user2 instanceof Customer) {
                                            targetCustomer = (Customer) user2;
                                            break;
                                        }
                                    }
                                }
                                if (targetCustomer == null)
                                    throw new AccountNotFoundException();
                                if (amount > customer.getCash()) {
                                    throw new NotEnoughCashException();
                                }
                                customer.decreaseCash(amount);
                                targetCustomer.increaseCash(amount);
                                Transaction transaction = new Transaction();
                                transaction.setAmount(amount);
                                transaction.setCustomerPair(new Pair<>(customer, targetCustomer));
                                transactions.add(transaction);

                            }
                        });
                        System.out.println("hello customer");
                    }
                    user1.start();
                    break;
                }
            }
            if (find)
                System.out.println("not found, try again");
        }
    }

}
