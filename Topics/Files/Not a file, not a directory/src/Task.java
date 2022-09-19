// You can experiment here, it won’t be checked

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Task {
    public static void main(String[] args) {

        File file = new File("C:\\Users\\david\\Downloads\\dataset_91069.txt");
        String[] parts;
        String population;
        long diff = 0;
        long prevYear = 0;
        long intPopulation;
        long maxPopulation = Integer.MIN_VALUE;
        int year = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                parts = scanner.nextLine().split("\t");
                population = parts[1];

                if (Character.isLetter(parts[1].charAt(0))) {
                    continue;
                }

                population = population.replaceAll(",", "");
                intPopulation = Long.parseLong(population);
                diff = intPopulation - prevYear;

                if ((diff > maxPopulation) && (prevYear > 0)) {
                    maxPopulation = diff;
                    year = Integer.parseInt(parts[0]);
                }

                prevYear = intPopulation;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("The largest increase in population (" + maxPopulation + ")");
        System.out.println("Occurred in " + year);

    }
}
