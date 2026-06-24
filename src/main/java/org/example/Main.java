package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

/** Taschenrechner mit eigenen Methoden: die Rechen-Logik ist in benannte Hilfsmethoden ausgelagert. */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean weitermachen = true;

        System.out.println("=== Taschenrechner ===");

        while (weitermachen) {

            menu();

            try {
                int auswahl = scanner.nextInt();

                if (auswahl == 0) {
                    weitermachen = false;
                } else {
                    verarbeiteAuswahl(auswahl, scanner);
                }

            } catch (InputMismatchException e) {
                System.out.println("Fehler: Bitte nur Zahlen eingeben!");
                scanner.nextLine();
            }
        }

        System.out.println("Taschenrechner beendet. Auf Wiedersehen!");
        scanner.close();
    }



    static void menu() {
        System.out.println("\n=== Menü ===");
        System.out.println("1) Addition       (+)");
        System.out.println("2) Subtraktion    (-)");
        System.out.println("3) Multiplikation (*)");
        System.out.println("4) Division       (/)");
        System.out.println("5) Potenz         (x^y)");
        System.out.println("6) Wurzel         (√x)");
        System.out.println("0) Beenden");
        System.out.print("Deine Auswahl: ");
    }



    static void verarbeiteAuswahl(int auswahl, Scanner scanner) {

        if (auswahl == 6) {
            // Wurzel braucht nur eine Zahl, deshalb wird hier separat abgefragt
            System.out.print("Zahl: ");
            double a = scanner.nextDouble();
            System.out.println("Ergebnis: √" + a + " = " + wurzel(a));

        } else if (auswahl >= 1 && auswahl <= 5) {

            System.out.print("Erste Zahl:  ");
            double a = scanner.nextDouble();
            System.out.print("Zweite Zahl: ");
            double b = scanner.nextDouble();

            switch (auswahl) {
                case 1:
                    System.out.println("Ergebnis: " + a + " + " + b + " = " + addieren(a, b));
                    break;
                case 2:
                    System.out.println("Ergebnis: " + a + " - " + b + " = " + subtrahieren(a, b));
                    break;
                case 3:
                    System.out.println("Ergebnis: " + a + " * " + b + " = " + multiplizieren(a, b));
                    break;
                case 4:
                    if (b == 0) {
                        System.out.println("Fehler: Division durch 0 ist nicht erlaubt!");
                    } else {
                        System.out.println("Ergebnis: " + a + " / " + b + " = " + dividieren(a, b));
                    }
                    break;
                case 5:
                    System.out.println("Ergebnis: " + a + " ^ " + b + " = " + potenzieren(a, b));
                    break;
            }

        } else {
            System.out.println("Ungültige Auswahl! Bitte 0 bis 6 eingeben.");
        }
    }



    static double addieren(double a, double b) {
        return a + b;
    }

    static double subtrahieren(double a, double b) {
        return a - b;
    }

    static double multiplizieren(double a, double b) {
        return a * b;
    }

    static double dividieren(double a, double b) {
        return a / b;
    }

    static double potenzieren(double basis, double exponent) {
        return Math.pow(basis, exponent);
    }

    static double wurzel(double zahl) {
        if (zahl < 0) {
            System.out.println("Fehler: Wurzel aus einer negativen Zahl ist nicht erlaubt!");
            return 0;
        }
        return Math.sqrt(zahl);
    }
}
