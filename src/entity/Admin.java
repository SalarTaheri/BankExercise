package entity;

import exceptions.AccountNotFoundException;
import exceptions.NotEnoughCashException;

import java.util.Scanner;

public class Admin extends User {

    private enum Action {
        createAccount, exit
    }

    {
        userId = 1;
        userName = "admin";
        password = "admin";
    }

    private ActionsCallback callback;

    public Admin(ActionsCallback callback) {
        this.callback = callback;
    }

    public Customer createAccount() {
        Customer customer = new Customer(null);
        System.out.println("account username: ");
        scanner = new Scanner(System.in);

        String username = scanner.nextLine();
        customer.setUserName(username);
        String pass, passRepeat;
        while (true) {
            System.out.println("account password: ");
            pass = scanner.nextLine();
            System.out.println("repeat password: ");
            passRepeat = scanner.nextLine();
            if (pass.equals(passRepeat)) {
                customer.setPassword(pass);
                return customer;
            } else {
                System.out.println("pass and its repeat are not same");
            }
        }
    }

    @Override
    public void start() {
        Action action = Action.createAccount;
        while (true) {
            int actionInt;
            System.out.println("Enter action number: ");
            System.out.println("0.Create account");
            System.out.println("1.exit");
            actionInt = scanner.nextInt();
            Action[] values = Action.values();
            if (actionInt < values.length) {
                action = values[actionInt];
                if (action == Action.exit)
                    return;
                runAction(action);
            } else {
                System.out.println("wrong input, try again");
            }
        }


    }

    private void runAction(Action action) {
        if (action == Action.createAccount) {
            Customer account = createAccount();
            System.out.println(account.userName);
            System.out.println(account.password);
            if (callback != null)
                callback.onCreateCustomer(account);
        }
    }

    public interface ActionsCallback {
        void onCreateCustomer(Customer customer);
    }
}
