package banking;

import banking.util.Program;

public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        program.start();

        while (Program.running) {
            program.showMenu();
        }
    }
}