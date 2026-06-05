# Lernkarten-App (Java, SQLite, JDBC)

## Projektübersicht
Dieses Projekt ist eine Lernkarten-Anwendung (Flashcard-System) zur Verwaltung und Abfrage von Lernkarten.  
Es wurde im Rahmen eines Universitätsprojekts entwickelt und dient der praktischen Anwendung von Java, Datenbanken und Softwareentwicklungskonzepten.

## Funktionen
- Lernkarten erstellen
- Lernkarten anzeigen
- Lernkarten bearbeiten
- Lernkarten löschen (CRUD-Funktionalität)
- Persistente Speicherung der Daten in einer SQLite-Datenbank

## Verwendete Technologien
- Java
- SQLite
- JDBC
- Objektorientierte Programmierung (OOP)
- Git / GitHub

## Technische Umsetzung
- Implementierung einer Java-Anwendung mit strukturierter Klassenarchitektur
- Datenbankanbindung über JDBC
- Nutzung einer relationalen SQLite-Datenbank zur Speicherung von Lernkarten
- Umsetzung typischer CRUD-Operationen

pk-praktikum/
│
├── src/
│   └── main/
│       └── java/
│           └── com/lernkartenapp/
│               ├── Main.java
│               │
│               ├── model/
│               │   └── Lernkarte.java
│               │
│               ├── repository/
│               │   └── LernkarteRepository.java
│               │
│               ├── database/
│               │   └── Database.java
│               │
│               └── ui/
│
├── resources/
│
├── lib
│
├── README.md
├── .gitignore


## Lernziele
- Anwendung von objektorientierter Programmierung in Java
- Arbeiten mit relationalen Datenbanken
- Datenbankzugriff über JDBC
- Strukturierung eines Java-Projekts
- Versionskontrolle mit Git

## Ausführen des Projekts
1. Repository klonen:
git clone https://github.com/Ouassim-Hammouti/Lernkarten-App.git

2. Projekt in einer Java-IDE öffnen (z. B. IntelliJ IDEA oder Eclipse)

3. Main-Klasse starten

## Autor
Ouassim Hammouti  
Studium: Wirtschaftsinformatik

## Hinweis
Dies ist ein studentisches Projekt zur praktischen Anwendung von Softwareentwicklungskonzepten.
