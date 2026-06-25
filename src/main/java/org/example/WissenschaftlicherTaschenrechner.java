package org.example;

public class WissenschaftlicherTaschenrechner extends Taschenrechner {

    public double cosinus(double a) {
        return Math.cos(a);
    }

    public double sinus(double a) {
        return Math.sin(a);
    }

    public double logarithmus(double a) {
        if (a <= 0) {
            System.out.println("Fehler: Logarithmus nur für positive Zahlen definiert!");
            return 0;
        }
        return Math.log(a);
    }
}
