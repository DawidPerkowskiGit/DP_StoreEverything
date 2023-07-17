## 1.  Projekt aplikacji webowej Store Everything, której zadaniem jest przechowywanie informacji/notatek/ogłoszeń. ##

## 2.  Podstawowe funkcjonalności aplikacji : ##

    
**Użytkownicy**

- możliwość rejestracji oraz zalogowania użytkowników

- podział na trzy grupy : z ograniczonym dostępem, z pełnym dostępem, administrator
 
- użytkownik z ograniczonym dostępem ma możliwość przeglądania udostępnionych mu informacji

- użytkownik z pełnym dostępem może dodawać, usuwać, edytować, udostępniać innym użytkownikom informacje oraz dodawać kategorie

- użytkownik z pełnym dostępem może przeglądać tylko swoje infromacje oraz otrzymane od innych

- administrator zarządza użytkownikami oraz informacjami

- używając REST API można uzyskać listę użytkowników

**Informacje**

-  opisane tytułem, treścią, kategorią, datą dodania i datą przypomnienia

-  kategoria jest wybierana z listy, możliwość dodania nowej

-  jeżeli dzisiaj przypada data przypomnienia co najmniej jednej informacji, to po zalogowaniu pokaże się ekran z tymi informacjami. Tyczy się to informacji autorskich oraz udostępnionych przez innych użytkowników.

-  na stronie głównej pokazane są trzy ostatnio dodane informacje oraz trzy ostatnio otrzymane(udostępnione) informacje
-  używając REST API można uzyskać listę informacji

## 3.  Zastosowane technologie i narzędzia

-  Java - główny język programowania

-  Spring Web - framework umożliwiający stworzenie aplikacji webowej

-  Spring Boot - framework umożliwiający utworzenie samodzielnej aplikacji

-  Spring Security - wydzielenie dostępu do konkretnych zasobów apliakcji w zależności od posiadanych uprawnień

-  Junit - framework używany do przeprowadzania testów jednostkowych

-  MySQL - wybrana baza danych do przechowywania danych użytkowników i informacji

-  MySQL Workbench - zarządzanie bazą danych

-  IntelliJ - środowisko do tworzenia aplikacji

-  Maven - zarządzanie frameworkami 

-  Postman - testowanie działania REST API

