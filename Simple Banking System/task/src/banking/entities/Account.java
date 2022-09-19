package banking.entities;

public class Account {
    private final int id;
    private final Card card;
    private int balance;

    public Account() {
        this.id = 0;
        this.balance = 0;
        this.card = new Card();
    }

    public Account(int id, String number, String pin, int balance) {
        this.id = id;
        this.balance = balance;
        this.card = new Card(number, pin);
    }

    public String getPin() {
        return card.getPin();
    }

    public String getCardNumber() {
        return card.getCardNumber();
    }

    public int getBalance() {
        return balance;
    }

    public void addIncome(int income) {
        this.balance += income;
    }
}
