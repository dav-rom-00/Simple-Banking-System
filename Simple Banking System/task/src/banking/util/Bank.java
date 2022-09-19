package banking.util;

import banking.entities.Account;
import banking.entities.Card;

import java.util.Map;

class Bank {
    private Map<String, Account> accounts;

    public Bank() {
        accounts = null;
    }

    public void createAccount() {
        Account acc = new Account();

        if (!Connect.existsAccount(acc.getCardNumber(), acc.getPin())) {
            this.showAccInfo(acc);
            this.insertIntoDB(acc.getCardNumber(), acc.getPin(), acc.getBalance());
        } else {
            this.createAccount();
        }
    }

    private void showAccInfo(Account acc) {
        System.out.println("\nYour card has been created\n" +
                "Your card number:\n" +
                acc.getCardNumber() + "\n" +
                "Your card PIN:\n" +
                acc.getPin() + "\n");
    }

    private void retrieveFromDB() {
        accounts = Connect.getAllAccFromDB();
    }

    public boolean logIn(String cardNumber, String pin) {
        return Connect.existsAccount(cardNumber, pin);
    }

    public boolean logOut(Account acc) {
        return Connect.existsAccount(acc.getCardNumber(), acc.getPin());
    }

    private void insertIntoDB(String number, String pin, int balance) {
        Connect.insert(number, pin, balance);
    }

    public void addIncome(Account acc, int income) {
        acc.addIncome(income);
        Connect.updateBalance(acc.getCardNumber(), acc.getBalance());
    }

    public void deleteAccount(Account acc) {
        Connect.delete(acc.getCardNumber());
    }

    public boolean checkLuhnAlgorithm(String cardNumber) {
        char lastDigit = cardNumber.charAt(cardNumber.length() - 1);
        String correctDigit = Card.checkLuhnAlgorithm(cardNumber);
        char correctLastDigit = correctDigit.charAt(correctDigit.length() - 1);
        return lastDigit == correctLastDigit;
    }

    public void transferMoney(Account acc, int amount, String cardNum) {
        Connect.transfer(acc, cardNum, amount);
    }

    public Map<String, Account> getAccounts() {
        this.retrieveFromDB();
        return accounts;
    }
}
