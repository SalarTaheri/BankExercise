package entity;

import javafx.util.Pair;

public class Transaction {

    protected Pair<Customer , Customer> customerPair;

    protected int amount;

    public Pair<Customer, Customer> getCustomerPair() {
        return customerPair;
    }

    public void setCustomerPair(Pair<Customer, Customer> customerPair) {
        this.customerPair = customerPair;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
