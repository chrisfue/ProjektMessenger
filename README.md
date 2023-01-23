**ODE Project: MSN Messenger Chat**
===================

**Short Description:**
Die Firma X möchte ein eigenes Programm haben, damit die Mitarbeiter während der Arbeit
miteinander Chatten können. Dort sollen die Mitarbeiter sehen wer aller online ist und einen Chat
starten.

---------------------------------

### Must Have Features ###


* [X] Kommentiert mit JavaDoc
* [x] readme.md für Überblick
* [x] Klassen mit Vererbung, Overriding, etc.
* [x] Zugriffsrechte sinnvoll
* [x] Exeption Handling
* [x] File IO (Status-Log)
* [x] Multithreading (Thread: jeder Client Thread für Client und Server verbindung)
* [x] GUI (Chat-Window)
* [x] Networking (einzelner User am Server)

-------------
### Should Have Features ###

* [x] Networking+ (Mehrere User verbinden sich mit Server)
* [x] Online Status anzeigen lassen in der Client GUI.
* [x] Chat-Log als .txt File am Server

-------------

### Nice to Have Features ###

* [ ] Emojis
* [ ] Chat Log in GUI anzeigen lassen. (letzte h)

--------------
### Overkill ###

* [ ] Direct Messaging (whisper)


-----
## Funktionsweise ##

Das Projekt besteht aus 2 separaten Programmen, aus einer Server- und einer Client-GUI Anwendung.

**Die Client-Anwendung** haendelt die User Eingaben und ermoeglicht bei validen Eingaben eine Verbindung mit dem Server.
Zudem ist der Client die Schnittstelle, eine Art Human Interface GUI zur Messenger Applikation.


**Der Server** ermoeglicht es, die Clients untereinander zu verbinden, um zB. Nachrichten austauschen zu koennen.
Er ist sozusagen der Router bzw. die Schnittstelle der Clients untereinander. 
Zudem ermoeglicht er, ein Logfile zu generieren von den Verbindungen.

_________________________________________________________
### Funktionsweise ###

1.    Der User muss die Client Anwendung starten und sollte ein Server noch nicht gestartet worden sein, auch diese Anwendung starten.
2.    Beim Starten der Client anwendung erscheint ein Login GUI und erwartet eine valide IP adresse eines Servers, als auch einen gewuenschten Usernamen.

     2a.    Sollte ein Fehler mit der ServerVerbindung auftreten oder kein Server unter der angegeben IP-Adresse verfuegbar sein, erscheint eine Fehlermeldung.
 
     2b.    Bei einer positiven Bestätigung durch den Server, erscheint die Messenger GUI in der es dann moeglich ist nachrichten an die Angemeldeten User zu   versenden.
  
3. im Textfeld werden auch verschiedene Server befehle unterstützt:
                 
                 -> 






============================================

*Written by KliWieFue*
---------------------------
**ODE Messenger Application Project**


*last update log:*

```
19.12.22 - readme added 
23.01.23 - rework Readme (feature and Description added)
```
