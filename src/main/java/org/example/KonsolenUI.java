package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class KonsolenUI {

    private Taschenrechner rechner;
    private Scanner scanner;

    public KonsolenUI(Taschenrechner rechner) {
        this.rechner = rechner;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean weitermachen = true;

        System.out.println("=== Taschenrechner ===");

        while (weitermachen) {
            zeigeMenu();

            try {
                int auswahl = scanner.nextInt();

                if (auswahl == 0) {
                    weitermachen = false;
                } else {
                    verarbeiteAuswahl(auswahl);
                }

            } catch (InputMismatchException e) {
                System.out.println("Fehler: Bitte nur Zahlen eingeben!");
                scanner.nextLine();
            }
        }

        System.out.println("Taschenrechner beendet. Auf Wiedersehen!");
        scanner.close();
    }

    private void zeigeMenu() {
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

    private void verarbeiteAuswahl(int auswahl) {

        if (auswahl == 6) {
            System.out.print("Zahl: ");
            double a = scanner.nextDouble();
            System.out.println("Ergebnis: √" + a + " = " + rechner.wurzel(a));

        } else if (auswahl >= 1 && auswahl <= 5) {

            System.out.print("Erste Zahl:  ");
            double a = scanner.nextDouble();
            System.out.print("Zweite Zahl: ");
            double b = scanner.nextDouble();

            switch (auswahl) {
                case 1:
                    System.out.println("Ergebnis: " + a + " + " + b + " = " + rechner.addiere(a, b));
                    break;
                case 2:
                    System.out.println("Ergebnis: " + a + " - " + b + " = " + rechner.subtrahiere(a, b));
                    break;
                case 3:
                    System.out.println("Ergebnis: " + a + " * " + b + " = " + rechner.multipliziere(a, b));
                    break;
                case 4:
                    if (b == 0) {
                        System.out.println("Fehler: Division durch 0 ist nicht erlaubt!");
                    } else {
                        System.out.println("Ergebnis: " + a + " / " + b + " = " + rechner.dividiere(a, b));
                    }
                    break;
                case 5:
                    System.out.println("Ergebnis: " + a + " ^ " + b + " = " + rechner.potenziere(a, b));
                    break;
            }

        } else {
            System.out.println("Ungültige Auswahl! Bitte 0 bis 6 eingeben.");
        }
    }
}
