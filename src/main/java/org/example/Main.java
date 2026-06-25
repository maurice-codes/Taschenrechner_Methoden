package org.example;

public class Main {
    public static void main(String[] args) {
        WissenschaftlicherTaschenrechner rechner = new WissenschaftlicherTaschenrechner();
        KonsolenUI ui = new KonsolenUI(rechner);
        ui.start();
    }
}
