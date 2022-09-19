package banking.util;

import banking.entities.Account;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static boolean running;
    private final Bank bank;
    private final Scanner scanner;
    private String option;

    public Program() {
        bank = new Bank();
        scanner = new Scanner(System.in);
    }

    public void start() {
        running = true;
        Connect.createTable();
    }

    public void showMenu() {
        System.out.println("""
                1. Create account
                2. Log into account
                0. Exit""");
        this.chooseOption();
    }

    private void chooseOption() {
        option = scanner.next();

        switch (option) {
            case "1" -> createAccount();
            case "2" -> logIn();
            case "0" -> exit();
        }
    }

    private void createAccount() {
        bank.createAccount();
        this.showMenu();
    }

    private void logIn() {
        System.out.println("\nEnter your card number:");
        String cardNumber = scanner.next();
        System.out.println("Enter your PIN:");
        String pin = scanner.next();

        if (bank.logIn(cardNumber, pin)) {
            System.out.println("\nYou have successfully logged in!\n");
            loginMenu(cardNumber);
        } else {
            System.out.println("\nWrong card number or PIN!\n");
            showMenu();
        }
    }

    private void loginMenu(String cardNumber) {
        Account acc = bank.getAccounts().get(cardNumber);

        while (!option.equals("5") && running) {
            System.out.println("""
                    1. Balance
                    2. Add income
                    3. Do transfer
                    4. Close account
                    5. Log out
                    0. Exit""");
            option = scanner.next();

            switch (option) {
                case "1" -> System.out.println("\nBalance: " + acc.getBalance() + "\n");
                case "2" -> addIncome(acc);
                case "3" -> doTransfer(acc);
                case "4" -> deleteAccount(acc);
                case "5" -> logOut(acc);
                case "0" -> exit();
            }
        }
    }

    private void addIncome(Account acc) {
        System.out.println("\nEnter income:");
        try {
            bank.addIncome(acc, scanner.nextInt());
            System.out.println("Income was added!\n");
        } catch (InputMismatchException e) {
            System.out.println("\nNumeric values only!\n");
        }
    }

    private void doTransfer(Account acc) {
        System.out.println("""

                Transfer
                Enter card number""");
        String accNumber = scanner.next();

        if (!bank.checkLuhnAlgorithm(accNumber)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!\n");
            return;
        }
        if (accNumber.equals(acc.getCardNumber())) {
            System.out.println("You can't transfer money to the same account!\n");
            return;
        }
        if (!bank.getAccounts().containsKey(accNumber)) {
            System.out.println("Such a card does not exist.\n");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");

        try {
            int money = scanner.nextInt();

            if (money > acc.getBalance()) {
                System.out.println("Not enough money!\n");
                return;
            }

            if (money <= 0) {
                System.out.println("Please enter a valid amount\n");
                return;
            }

            bank.transferMoney(acc, money, accNumber);
            System.out.println("Success!\n");
            this.loginMenu(acc.getCardNumber());
        } catch (InputMismatchException e) {
            System.out.println("\nNumeric values only!\n");
        }
    }

    private void deleteAccount(Account acc) {
        bank.deleteAccount(acc);
        System.out.println("\nThe account has been closed!\n");
        showMenu();
    }

    private void logOut(Account account) {
        if (bank.logOut(account)) {
            System.out.println("\nYou have successfully logged out!\n");
        } else {
            System.out.println("***** There was an error *****");
        }
    }

    private void exit() {
        System.out.println("\nBye!");
        running = false;
    }


}
