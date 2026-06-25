package org.example;

public class Taschenrechner {

    public double addiere(double a, double b) {
        return a + b;
    }

    public double subtrahiere(double a, double b) {
        return a - b;
    }

    public double multipliziere(double a, double b) {
        return a * b;
    }

    public double dividiere(double a, double b) {
        return a / b;
    }

    public double potenziere(double basis, double exponent) {
        return Math.pow(basis, exponent);
    }

    public double wurzel(double zahl) {
        if (zahl < 0) {
            System.out.println("Fehler: Wurzel aus einer negativen Zahl ist nicht erlaubt!");
            return 0;
        }
        return Math.sqrt(zahl);
    }
}
