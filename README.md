# PPS-22-ISIquiz

Repository contenente il software ISIQuiz, un progetto accademico per il corso "Paradigmi di Programmazione e Sviluppo" dell'Università di Bologna A.A. 2021/2022.

L'applicazione ha lo scopo di gamificare la revisione pre-esame degli argomenti principali dei corsi universitari dell'area Ingegneria e Scienze Informatiche.

##Guida utente

### Installazione
Nel terminale:
```bash
java -jar ISIQuiz.jar
```

### Come giocare
Una volta aperta l'applicazione, viene visualizzato un menu iniziale:

__Gioca__
Seleziona almeno un corso per poter iniziare una partita con quiz riguardanti quei corsi.
Successivamente, scegli la modalità di gioco:
- partita standard: 10 quiz, ciascuno da rispondere in massimo 15 secondi; ogni quiz ha 4 possibili risposte, 1 sola risposta è corretta; scoprirai la correttezza o meno della risposta data dopo ogni quesito
- partita blitz: rispondi a più quiz possibili in 2 minuti (120 secondi); ogni quiz ha 4 possibili risposte, 1 sola risposta è corretta; scoprirai la correttezza o meno della risposta solo al termine della partita
- partita personalizzata: scegli il numero di quiz a cui rispondere e il tempo massimo (in secondi) in cui puoi rispondere a ciascuno; ogni quiz ha 4 possibili risposte, 1 sola risposta è corretta

Al termine di una partita c'è il riepilogo con: 
- il numero totale di domande risposte correttamente
- il numero totale di punti guadagnati
- 

__Statistiche__
Visualizza le statistiche relative ai tuoi ripassi:
- totale dei punti acquisiti
- totale dei quiz risposte
- totale delle risposte corrette
- precisione
- tempo medio di risposta

__Impostazioni__
Per poter apportare modifiche nelle impostazioni generali:
- Importa quiz: importa un file JSON dal file system contenente nuovi quiz
- Esporta quiz: esporta il file JSON contenente i corsi con i relativi quiz
- Modifica quiz
- Aggiungi corso: inserisci un nuovo corso
- Aggiungi quiz: inserisci nuovi quiz ad un determinato corso, così da poterti esercitare su più argomenti

__Esci__
Per uscire dall'applicazione

## Team

I membri del team di sviluppo di questo progetto sono:
- Daniele Gambaletta
- Igor Lirussi
- Riccardo Omiccioli
- Cecilia Teodorani

## Info

**La Documentazione del software è disponibile online al [sito dedicato qui](https://isiquiz.github.io/PPS-22-isiquiz/docs/ "Documentazione")** <br>

**La Coverage del software è disponibile online al [sito dedicato qui](https://isiquiz.github.io/PPS-22-isiquiz/coverage/ "Coverage")** <br>