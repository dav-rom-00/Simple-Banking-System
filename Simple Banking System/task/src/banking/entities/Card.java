package banking.entities;

import java.util.Random;

public class Card {
    private String pin;
    private String cardNumber;
    private Random random;
    private static final String BIN = "400000";

    public Card() {
        this.random = new Random();
        this.generatePin();
        this.generateCardNumber();
    }

    public Card(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    private void generatePin() {
        int num = random.nextInt(0, 10000);
        this.pin = String.format("%04d", num);
    }

    private void generateCardNumber() {
        StringBuilder num = new StringBuilder();
        String cardNum;

        for (int i = 0; i < 10; i++) {
            num.append(random.nextInt(10));
        }
        cardNum = BIN + num;
        this.cardNumber = checkLuhnAlgorithm(cardNum);
    }

    public static String checkLuhnAlgorithm(String number) {
        number = number.substring(0, number.length() - 1);
        int sum = 0;
        int currentDigit;

        for (int i = 0; i < number.length(); i++) {
            currentDigit = Integer.parseInt(String.valueOf(number.charAt(i)));

            if (i % 2 == 0) {
                currentDigit *= 2;

                if (currentDigit > 9) {
                    currentDigit -= 9;
                }
            }
            sum += currentDigit;
        }

        return number + generateLastDigit(sum);
    }

    private static int generateLastDigit(int number) {
        if (number % 10 == 0) {
            return 0;
        }

        return 10 - number % 10;
    }

    public String getPin() {
        return pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

}
