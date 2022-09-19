import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n1 = scanner.nextInt();
        int n2 = scanner.nextInt();
        int n3 = scanner.nextInt();
        System.out.println(isInBetween(n1, n2, n3));
    }

    static boolean isInBetween(int n1, int n2, int n3) {
        return (n1 <= n3 && n1 >= n2) || (n1 >= n3 && n1 <= n2);
    }

}