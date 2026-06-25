# Aufgabe: Grafischer Taschenrechner mit Swing UI Designer

Ihr habt bereits einen konsolenbasierten Taschenrechner gebaut (`KonsolenUI`). Heute baut ihr daraus eine richtige grafische Oberfläche mit **Swing** und dem **IntelliJ Swing UI Designer**.

---

## Vorbereitung

1. Öffnet das Projekt in IntelliJ IDEA.
2. Vergewissert euch, dass die Klasse `forms/Taschenrechner.java` existiert (sie ist bereits angelegt – nur das Package `org.example.forms`).
3. **Lest die Verknüpfung** zwischen einer `.form`-Datei und einer Java-Klasse:
   - https://www.jetbrains.com/help/idea/design-gui-using-swing.html

---

## Schritt 1: GUI-Formular anlegen (45 min)

1. **Erstellt eine neue GUI-Form** in IntelliJ:
   - Rechtsklick auf `src/main/java/org/example/forms` → New → GUI Form
   - Name: `TaschenrechnerForm`
   - Bind to class: `TaschenrechnerForm`
   - Root layout: `GridBagLayout` (später könnt ihr auch `GroupLayout` oder `MigLayout` ausprobieren)

2. **Macht euch mit dem Designer vertraut:**
   - Zieht ein `JTextField` (das Display) in die Mitte
   - Zieht ein `JPanel` für die Tasten darunter
   - Setzt im Properties-Fenster die `editable`-Eigenschaft des Textfelds auf `false`
   - Setzt die `column`-Eigenschaft auf `15`
   - Spielt 10 Minuten mit verschiedenen Layouts und Komponenten herum

3. **Stellt sicher**, dass die `.form`-Datei und die zugehörige Java-Klasse im gleichen Package `org.example.forms` liegen.

> **Hilfestellung:** Wenn ihr IntelliJ fragt, ob die Klasse erstellt werden soll, sagt Ja. Der generierte Code enthält leere Methoden wie `$$$setupUI$$$()` und `$$$getRootComponent$$$()` – die dürft ihr **nicht verändern**, sie werden automatisch generiert.

---

## Schritt 2: Grundgerüst der Swing-UI bauen (1 h)

Erstellt eine neue Klasse `SwingUI` im Package `org.example`, die eine `JFrame`-basierte Oberfläche startet.

```java
package org.example;

import org.example.forms.TaschenrechnerForm;

import javax.swing.*;

public class SwingUI {

    private JFrame frame;
    private Taschenrechner rechner;

    public SwingUI(Taschenrechner rechner) {
        this.rechner = rechner;
        frame = new JFrame("Taschenrechner");
        // TODO: Hier die Komponenten aus der Form laden
    }

    public void start() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        WissenschaftlicherTaschenrechner rechner = new WissenschaftlicherTaschenrechner();
        SwingUI ui = new SwingUI(rechner);
        ui.start();
    }
}
```

**Aufgaben:**
- Ändert die `Main`-Klasse so, dass statt `KonsolenUI` die neue `SwingUI` gestartet wird.
- Das Form aus Schritt 1 muss in der `SwingUI` geladen werden. Wie das geht, seht ihr in der generierten `TaschenrechnerForm`-Klasse – dort gibt es eine statische Factory-Methode oder ein `JPanel`, das ihr auslesen könnt.
- Hängt das geladene `JPanel` in den `frame`.

> **Hilfestellung:** Die generierte Klasse hat eine Methode wie `new TaschenrechnerForm().$$$getRootComponent$$$()` oder ein `JPanel`-Attribut, das ihr über `frame.getContentPane().add(...)` einfügen könnt.

---

## Schritt 3: Das Display und die Zahlen-Tasten (45 min)

**Erweitert die GUI-Form:**

- **Display:** Oben ein `JTextField` oder `JLabel` für Eingabe/Ergebnis
- **Zahlen-Tasten:** 0–9
- **Punkt-Taste:** `.`
- **Steuer-Tasten:** `C` (Clear), `CE` (Clear Entry), `⌫` (Backspace)

**Verbindet die Tasten mit der Java-Klasse:**

1. Setzt für jede Taste im Designer die Eigenschaft `actionCommand` auf einen eindeutigen String (z. B. `"0"`, `"1"`, … `"C"`).
2. Erstellt ein `ActionListener`-Interface, das an alle Tasten gehängt wird.
3. Im `actionPerformed` werdet ihr über `e.getActionCommand()` informiert, welche Taste gedrückt wurde.
4. Implementiert die Logik:

```java
private String aktuellerWert = "";
private double ergebnis = 0;
private String letzteOperation = "";

private void tasteGedrückt(String cmd) {
    switch (cmd) {
        case "C"  -> aktuellerWert = "";
        case "⌫"  -> aktuellerWert = aktuellerWert.isEmpty() ? "" :
                       aktuellerWert.substring(0, aktuellerWert.length() - 1);
        default   -> aktuellerWert += cmd;  // Ziffer
    }
    display.setText(aktuellerWert);
}
```

> **Hilfestellung:** `current_ value` muss ein `String` sein, weil wir die Ziffern einfach konkatenieren. Erst wenn gerechnet wird, parsen wir mit `Double.parseDouble(...)`.

---

## Schritt 4: Grundrechenarten implementieren (1 h)

**Fügt Operations-Tasten hinzu:** `+`, `−`, `×`, `÷`, `=`

**Erweitert die Logik:**

1. Wird eine Operation gedrückt (`+`, `−`, `×`, `÷`):
   - Den aktuellen Wert als `double` speichern (`ergebnis`)
   - Die Operation merken (`letzteOperation`)
   - `aktuellerWert` zurücksetzen
2. Wird `=` gedrückt:
   - Zweiten Operanden einlesen
   - `letzteOperation` ausführen – verwendet dazu eure `Taschenrechner`-Klasse
   - Ergebnis anzeigen

```java
case "+", "−", "×", "÷" -> {
    if (!aktuellerWert.isEmpty()) {
        ergebnis = Double.parseDouble(aktuellerWert);
        letzteOperation = cmd;
        aktuellerWert = "";
    }
}
case "=" -> {
    if (!aktuellerWert.isEmpty() && !letzteOperation.isEmpty()) {
        double zweiter = Double.parseDouble(aktuellerWert);
        ergebnis = switch (letzteOperation) {
            case "+" -> rechner.addiere(ergebnis, zweiter);
            case "−" -> rechner.subtrahiere(ergebnis, zweiter);
            case "×" -> rechner.multipliziere(ergebnis, zweiter);
            case "÷" -> rechner.dividiere(ergebnis, zweiter);
            default -> 0;
        };
        display.setText(String.valueOf(ergebnis));
        aktuellerWert = "";
        letzteOperation = "";
    }
}
```

**Testet:** Führt den Taschenrechner aus und probiert alle Grundrechenarten.

> **Hilfestellung:** Wenn `b == 0` bei der Division, fängt der `dividiere()` keine Exception – das müsst ihr in der `SwingUI` selber abfangen und eine Fehlermeldung ausgeben, z. B. mit `JOptionPane.showMessageDialog(...)`.

---

## Schritt 5: Wissenschaftliche Funktionen (1 h)

**Erweitert die GUI-Form** um folgende Tasten:
- `√x` (Wurzel)
- `x²` (Quadrat)
- `xʸ` (Potenz)
- `sin`, `cos`, `ln`
- `±` (Vorzeichenwechsel)

**Erweitert die Logik:**
- Nutzt die Methoden aus eurem `WissenschaftlicherTaschenrechner`.
- `x²` könnt ihr als Spezialfall von `potenziere(x, 2)` implementieren.
- `±` negiert den aktuellen Wert: `Double.parseDouble(aktuellerWert) * -1`

**Bei den einstelligen Funktionen** (√, sin, cos, ln, x²) rechnet ihr direkt:

```java
case "√x" -> {
    double wert = Double.parseDouble(aktuellerWert);
    ergebnis = rechner.wurzel(wert);
    display.setText(String.valueOf(ergebnis));
    aktuellerWert = "";
}
```

> **Hilfestellung:** Denkt daran, `JOptionPane.showMessageDialog(...)` zu verwenden, wenn der Logarithmus oder die Wurzel einer negativen Zahl eingegeben wird. Eure `Taschenrechner`-Klassen geben dann 0 zurück – das ist verwirrend für den Benutzer. Besser eine Dialogbox zeigen.

---

## Schritt 6: Tastaturbedienung (45 min)

Macht den Taschenrechner auch über die Tastatur bedienbar:

```java
// Im Konstruktor der SwingUI
JPanel root = ...;
root.registerKeyboardAction(e -> tasteGedrückt("0"), KeyStroke.getKeyStroke('0'), WHEN_IN_FOCUSED_WINDOW);
// Analog für 1–9, +, -, *, /, Enter (=), ESC (C), Backspace (⌫)
```

- `Enter` → `=`
- `Escape` → `C`
- `+`, `-`, `*`, `/` → Operationen
- `Backspace` → `⌫`

> **Hilfestellung:** `registerKeyboardAction` erwartet einen `ActionListener` und einen `KeyStroke`. Für die Zahlen klappt das einfach. Für `+` müsst ihr `KeyStroke.getKeyStroke("ADD")` oder `KeyStroke.getKeyStroke('+')` verwenden.

---

## Schritt 7: Berechnungs-Historie (45 min)

Fügt eine `JList` oder `JTextArea` hinzu, die alle durchgeführten Berechnungen anzeigt:

```
3 + 5 = 8
8 × 2 = 16
√16 = 4
```

**Vorgehen:**
- Ein `JTextArea` (unten oder rechts) mit `setEditable(false)`.
- Nach jeder Berechnung einen Eintrag hinzufügen: `historyTextArea.append(zeile + "\n")`.
- Eine Taste `MC` (Memory Clear) löscht die Historie.

**Optional:** Scrollbarkeit mit `JScrollPane`.

---

## Schritt 8: Abschluss – Design und Extra-Features (30 min)

- **Look & Feel:** Setzt ein modernes Look & Feel:
  ```java
  try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  } catch (Exception ignored) {}
  ```
- **Menüleiste:** `JMenuBar` mit:
  - Datei → Beenden
  - Bearbeiten → Kopieren, Einfügen
  - Ansicht → Wissenschaftlich ein/ausblenden
- **Farben:** Setzt Hintergrundfarben für verschiedene Tasten-Gruppen (Zahlen grau, Operationen orange, Wissenschaftliche Funktionen blau)

---

## Zusammenfassung – Was ihr heute lernt

| Thema | Inhalt |
|---|---|
| **GUI Designer** | Arbeiten mit IntelliJ Swing UI Designer, `.form`-Dateien |
| **Swing-Komponenten** | JFrame, JPanel, JTextField, JButton, JTextArea |
| **Layout-Manager** | GridBagLayout, GroupLayout |
| **Event-Handling** | ActionListener, ActionCommand |
| **Tastatur-Steuerung** | KeyStroke, registerKeyboardAction |
| **Schichtenarchitektur** | GUI trennt sich von Geschäftslogik (Taschenrechner-Klassen) |

---

## Optional: Zusatzaufgaben für Schnelle

1. **Klammerrechnung:** Erweitert den Taschenrechner um Klammern `()` und einen Ausdrucksparser (z. B. `exp4j`-Bibliothek).
2. **Dark Mode:** Fügt einen Button zum Umschalten zwischen hellem und dunklem Design hinzu.
3. **Tastatur-Fokus:** Wenn eine Zahl gedrückt wird, soll die zuletzt gedrückte Zahl-Taste kurz aufleuchten (setzt die `background`-Farbe mit einem `Timer` zurück).
4. **Zwischenablage:** `Strg+C` / `Strg+V` für das Display.
5. **Unit-Tests:** Schreibt JUnit-Tests für die `SwingUI` (z. B. mit `AssertJ Swing`).
