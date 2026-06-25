package org.example;

public class Main {
    public static void main(String[] args) {
        Taschenrechner rechner = new Taschenrechner();
        KonsolenUI ui = new KonsolenUI(rechner);
        ui.start();
    }
}
