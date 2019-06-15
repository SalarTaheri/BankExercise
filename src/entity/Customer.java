package entity;

import exceptions.AccountNotFoundException;
import exceptions.NotEnoughCashException;

import java.util.Scanner;

public class Customer extends User {

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    private enum Action {
        transaction, viewCash, exit
    }

    private int cash = 1000;

    private ActionCallback callback;

    public Customer(ActionCallback callback) {
        this.callback = callback;
    }

    @Override
    public void start() {
        scanner = new Scanner(System.in);
        boolean go = true;
        while (go) {
            System.out.println();
            System.out.println("enter action number: ");
            Action[] values = Action.values();
            for (Action value : values) {
                System.out.println(value.ordinal() + "." + value.name());
            }
            int actionInt = scanner.nextInt();
            Action action;
            if (actionInt < values.length) {
                action = values[actionInt];
                go = runAction(action);
            } else {
                System.out.println("wrong input, try again");
            }

        }
    }

    private boolean runAction(Action action) {
        if (action == Action.exit)
            return false;
        if (action == Action.transaction) {
            System.out.println("enter target id");
            int targetId = scanner.nextInt();
            System.out.println("enter amount");
            int amount = scanner.nextInt();
            if (callback != null) {
                try {
                    callback.onTransaction(targetId, amount);
                } catch (Exception e) {
                    System.out.println("###Error###");
                    e.printStackTrace();
                    System.out.println("   ###   ");
                }
            }
        } else if (action == Action.viewCash) {
            System.out.println("$$ $$ $$");
            System.out.println(cash);
            System.out.println("$$ $$ $$");
        }
        return true;
    }

    public void increaseCash(int amount) {
        this.cash += amount;
    }

    public void decreaseCash(int amount) {
        this.cash -= amount;
    }

    public interface ActionCallback {
        void onTransaction(int targetId, int amount) throws AccountNotFoundException, NotEnoughCashException;
    }

    public ActionCallback getCallback() {
        return callback;
    }

    public void setCallback(ActionCallback callback) {
        this.callback = callback;
    }
}
