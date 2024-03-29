package utils.storage

import model.Answer.Answer
import model.Quiz.Quiz
import model.stats.{CourseInStats, PlayerStats, QuizInStats}
import model.{Course, Session}
import model.SavedCourse.SavedCourse
import model.CourseIdentifier.CourseIdentifier

import java.util.UUID

object DefaultDataList:

  // Sample default list of course
  val defaultCourseList: List[SavedCourse] = List(
    // Tecnologie Web
    SavedCourse(
      courseId = CourseIdentifier(
        courseName = "Tecnologie Web",
        degreeName = "Laurea in Ingegneria e Scienze Informatiche",
        universityName = "Università di Bologna"
      ),
      description = Option("Descrizione Web1"),
      quizList = List(
        Quiz(
          question = "Cos'è l'usabilità?",
          maxScore = 5,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "La capacità dei sistemi informatici di erogare servizi e fornire informazioni fruibili", false),
            Answer(text = "Una novità introdotta da CSS3", false),
            Answer(text = "Un solution stack", false),
            Answer(text = "Il grado con cui un prodotto può essere usato da un utente per svolgere un lavoro con efficacia, efficienza e soddisfazione in uno specifico contesto", true)
          )
        ),
        Quiz(
          question = "Quali sono le principali novità introdotte da CSS3?",
          maxScore = 6,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Selettori, colori e sfondi", true),
            Answer(text = "Canvas, header, footer", false),
            Answer(text = "Font, gradienti e audio", false),
            Answer(text = "Audio e video", false)
          )
        ),
        Quiz(
          question = "Nell’architettura del Web",
          maxScore = 14,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Le applicazioni web sono eseguite solo sui server", false),
            Answer(text = "I client sono in grado di visualizzare autonomamente tutti i formati", false),
            Answer(text = "Il client inizia sempre l’interazione con i server", true),
            Answer(text = "Il server inizia l’interazione con i client", false)
          )
        ),
        Quiz(
          question = "Definizione del livello AA di accessibilità specificato nelle WCAG",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Chiede di usare colori con sufficiente contrasto", false),
            Answer(text = "Chiede di rendere disponibile diverse versioni dei testi comprensibili per persone con livelli diversi di istruzione", false),
            Answer(text = "E' facilissima da ottenere", false),
            Answer(text = "E' praticamente impossibile da ottenere", true),
            Answer(text = "Indica i criteri più stringenti per il contrasto dei colori e chiede di prestare attenzione anche a come sono organizzate le informazioni nella pagina web", true)
          )
        ),
      )
    ),
    // Paradigmi di Programmazione e Sviluppo
    SavedCourse(
      courseId = CourseIdentifier(
        courseName = "Paradigmi di Programmazione e Sviluppo",
        degreeName = "Laurea Magistrale in Ingegneria e Scienze Informatiche",
        universityName = "Università di Bologna"
      ),
      description = Option("Descrizione PPS"),
      quizList = List(
        Quiz(
          question = "Quale ruolo ha il product owner?",
          maxScore = 5,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Decide i compiti degli sviluppatori del team", false),
            Answer(text = "Deve assicurarsi che la CI del progetto funzioni correttamente", false),
            Answer(text = "Coincide con lo scrum master", false),
            Answer(text = "Interagire con il cliente e identificare le freature del prodotto", true)
          )
        ),
        Quiz(
          question = "Cos'è lo Sprint Backlog?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Un software development framework di tipo incrementale e iterativo", false),
            Answer(text = "La persona che aiuta il gruppo ad utilizzare Scrum", false),
            Answer(text = "La persona che organizza il team di sviluppo", false),
            Answer(text = "Un solution stack", false),
            Answer(text = "Un raffinamento di una parte del Product Backlog per un dato Sprint", true)
          )
        ),
      )
    ),
    // Sistemi Informativi
    SavedCourse(
      courseId = CourseIdentifier(
        courseName = "Sistemi Informativi",
        degreeName = "Laurea Magistrale in Ingegneria e Scienze Informatiche",
        universityName = "Università di Bologna"
      ),
      description = Option("Descrizione SI"),
      quizList = List(
        Quiz(
          question = "Cos'è un sistema SCADA?",
          maxScore = 5,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Il portafoglio applicativo aziendale", false),
            Answer(text = "Deve assicurarsi che la CI del progetto funzioni correttamente", false),
            Answer(text = "L'insieme di portafoglio direzionale, istituzionale e operativo", false),
            Answer(text = "Il componente di un sistema CIM preposto al controllo dei sistemi industriali, all’acquisizione e all’analisi dei dati da essi prodotti", true),
            Answer(text = "Serve per monitorare e controllare grandi impianti industriali e sistemi meccanici/elettronici distribuiti sul territorio", true)
          )
        ),
        Quiz(
          question = "Cosa si intende per CRM?",
          maxScore = 5,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Care Relevant Marketing", false),
            Answer(text = "Care Relevant Management", false),
            Answer(text = "Customer Relationship Marketing", false),
            Answer(text = "Customer Relationship Management", true)
          )
        ),
        Quiz(
          question = "Cosa modella un DFD?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Le classi di un sistema software in UML", false),
            Answer(text = "Un Product Backlog in ottica agile", false),
            Answer(text = "I test automatici di un software", false),
            Answer(text = "Operazioni effettuate sulle informazioni e le dipendenze funzionali che vengono a crearsi fra i vari processi in base ai flussi di informazione", true),
            Answer(text = "Un sistema come una rete di processi funzionali interconnessi da depositi di dati", true)
          )
        ),
      )
    ),
    // Applicazioni e Servizi Web
    SavedCourse(
      courseId = CourseIdentifier(
        courseName = "Applicazioni e Servizi Web",
        degreeName = "Laurea Magistrale in Ingegneria e Scienze Informatiche",
        universityName = "Università di Bologna"
      ),
      description = Option("Descrizione Web2"),
      quizList = List(
        Quiz(
          question = "Quali tecnologie utilizza lo stack MERN?",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "AngularJs, ExpressJs, MongoDB, NodeJs", false),
            Answer(text = "PHP, Apache, Linux, MySQL", false),
            Answer(text = "PHP, Angular, Linux, MySQL", false),
            Answer(text = "React, MongoDB, NodeJs, ExpressJs", true)
          )
        ),
        Quiz(
          question = "Quali sono i principi e le metodologie per lo sviluppo?",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Mobile first, KISS, responsive design", true),
            Answer(text = "Prevenzione dell’errore e corrispondenza tra sistema e mondo reale", false),
            Answer(text = "Mobile fisrt e visibilità dello stato del sistema", false),
            Answer(text = "Cognitive walkthrough, experience prototyping e flessibilità", false)
          )
        ),
        Quiz(
          question = "Quali tecnologie utilizza lo stack LAMP?",
          maxScore = 4,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "PHP, Apache, Linux, MySQL", true),
            Answer(text = "AngularJs, ExpressJs, MongoDB, NodeJs", false),
            Answer(text = "PHP, Angular, Linux, MySQL", false),
            Answer(text = "Python, Angular, Linux, MongoDB", false),
            Answer(text = "Perl, Apache, Linux, MySQL", false)
          )
        ),
        Quiz(
          question = "Qual è il linguaggio più popolare nel mondo?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Perl", false),
            Answer(text = "JavaScript", true),
            Answer(text = "Angular", false),
            Answer(text = "Express", false)
          )
        ),
        Quiz(
          question = "Che cosa sono le Euristiche di Nielsen?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Dieci principi che permettono di misurare quanto più oggettivamente possibile l'usabilità della UI di un software", true),
            Answer(text = "Un'intervista, alla quale partecipano più persone contemporaneamente", false),
            Answer(text = "Uno storyboard realizzato con disegni o sketch o con mockup", false),
            Answer(text = "Attiva collaborazione tra diverse organizzazioni ed enti", false)
          )
        ),
        Quiz(
          question = "Quante sono le principali componenti di un browser?",
          maxScore = 13,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "7", true),
            Answer(text = "15", false),
            Answer(text = "2", false),
            Answer(text = "8", false)
          )
        ),
        Quiz(
          question = "Che cosa sono i focus group?",
          maxScore = 13,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Un linguaggio che estende/aggiunge delle funzionalità e la potenza espressiva al linguaggio di partenza", false),
            Answer(text = "Dei tipi di database non relazionali", false),
            Answer(text = "Un'intervista alla quale partecipano più persone nello stesso gruppo, si incentiva la discussione tra loro, in modo guidato per raccogliere feedback da potenziali utenti", true),
            Answer(text = "Framework per web app server side per Node.js", false)
          )
        ),
      )
    ),
    // Reti di Telecomunicazioni
    SavedCourse(
      courseId = CourseIdentifier(
        courseName = "Reti di Telecomunicazioni",
        degreeName = "Laurea in Ingegneria e Scienze Informatiche",
        universityName = "Università di Bologna"
      ),
      description = Option("Descrizione Reti2"),
      quizList = List(
        Quiz(
          question = "Quale fra i seguenti è un indirizzo valido per un host in una rete IP con numerazione privata",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "121.155.8.15", false),
            Answer(text = "0.1.220.198", false),
            Answer(text = "137.256.121.0", false),
            Answer(text = "192.168.1.1", true),
            Answer(text = "192.168.3.115", true)
          )
        ),
        Quiz(
          question = "Un host connesso in rete utilizzando il protocollo IP",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Deve avere solamente un'interfaccia di rete ed il relativo numero IP", false),
            Answer(text = "Deve avere solamente un'interfaccia di rete e più numeri IP assegnati", false),
            Answer(text = "Può avere più interfacce di rete assegnando a tutte il medesimo numero IP", false),
            Answer(text = "Può avere una o più interfacce e ad ognuna deve essere assegnato un numero IP", true)
          )
        ),
        Quiz(
          question = "Nell'intestazione (header) del datagramma IP il campo Time to live",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Cresce ad ogni nodo (hop) attraversato dal pacchetto", false),
            Answer(text = "Permette di attribuire un maggior tempo di vita ai pacchetti a priorità più alta", false),
            Answer(text = "Aiuta ad ordinare i pacchetti una volta arrivati a destinazione", false),
            Answer(text = "Limita il tempo di permanenza di un pacchetto in Internet", true)
          )
        ),
        Quiz(
          question = "La modalità di instradamento dei datagrammi nella rete Internet",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Garantisce che tutti i datagrammi di una medesima connessione seguano il medesimo percorso", false),
            Answer(text = "Viene fatta sulla base dell'indirizzo HTTP", false),
            Answer(text = "Viene fatta sulla base dell'indirizzo IP di sorgente", false),
            Answer(text = "Viene fatta sulla base dell'indirizzo IP di destinazione", true)
          )
        ),
        Quiz(
          question = "Una rete IP di classe C",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Ha un indirizzo il cui primo byte comincia con 101", false),
            Answer(text = "Non può essere suddivisa in sottoreti (subnet)", false),
            Answer(text = "Ha un indirizzo il cui primo byte comincia con 000", false),
            Answer(text = "Usa 3 byte per l'indirizzo della rete e 1 byte per l'indirizzo dell'Host", true)
          )
        ),
        Quiz(
          question = "Con il termine 'Direct Forwarding' si intende",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "La capacità di un host di inviare datagrammi che i router tratteranno in modo prioritario", false),
            Answer(text = "La capacità di un host di inviare datagrammi ad altri host della sua network senza bisogno di ricorrere ad un router", true),
            Answer(text = "La capacità di un host di inviare pacchetti a sè stesso passando per un router", false),
            Answer(text = "La capacità di un host di consegnare datagrammi interagendo direttamente con un gateway", false)
          )
        ),
        Quiz(
          question = "La tabella ARP in un host",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Viene configurata all'attivazione dell'host e non può essere più modificata", false),
            Answer(text = "Indica tutti i WiFi disponibili", false),
            Answer(text = "Viene aggiornata dal DNS", false),
            Answer(text = "Contiene corrispondenze fra numeri IP e indirizzi MAC", true)
          )
        ),
        Quiz(
          question = "Per il corretto funzionamento dell'interfaccia di rete di un host vanno configurati almeno i seguenti parametri",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Numero IP, Netmask e dafault gateway", false),
            Answer(text = "Numero IP e default gateway", false),
            Answer(text = "DHCP, indirizzi MAC e default gateway", false),
            Answer(text = "Numero IP e Netmask", true)
          )
        ),
        Quiz(
          question = "Nell'elaborazione del routing table lookup",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Si fa uso esclusivamente delle informazioni della NETMASK", false),
            Answer(text = "Si fa uso esclusivamente delle informazioni del campo IP DESTINATION", false),
            Answer(text = "Si fa uso esclusivamente delle informazioni del campo IP SOURCE", false),
            Answer(text = "Si fa uso del campo IP DESTINATION nonchè del contenuto dei campi NETMASK e DESTINATION della tabella di instradamento", true)
          )
        ),
        Quiz(
          question = "Un algoritmo di routing si dice statico quando",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Non permette l'aggiornamento delle tabelle di routing da parte dell'operatore", false),
            Answer(text = "Associa ogni specifico ingresso ad una particolare uscita in modo fisso e non modificabile", false),
            Answer(text = "Il router costruisce la tabella di routing sulla base delle informazioni che puo'ottenere misurando le proprie code di uscita", false),
            Answer(text = "Il router fa uso di una tabella di routing definita a priori in fase di configurazione", true)
          )
        ),
        Quiz(
          question = "Quali fra i protocolli elencati in seguito sono protocolli di Routing utilizzati in Internet",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "UDP", false),
            Answer(text = "TCP/IP", false),
            Answer(text = "UMTS", false),
            Answer(text = "TCP", false),
            Answer(text = "RIP", true)
          )
        ),
        Quiz(
          question = "Il messaggio DHCPACK",
          maxScore = 4,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Viene inviato dal server DHCP e termina la fase di configurazione dell'interfaccia IP del client", true),
            Answer(text = "Viene inviato dal client DHCP e termina la fase di configurazione dell'interfaccia IP del client", false),
            Answer(text = "Viene inviato dal server DHCP e inizia la fase di configurazione dell'interfaccia IP del server", false),
            Answer(text = "Viene inviato dal client DHCP e serve a identificare a quale server si chiede la configurazione dell'interfaccia IP", false)
          )
        ),
        Quiz(
          question = "Per l'organizzazione di Internet un Autonomous System",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "E' l'unità fondamentale in cui sono suddivisi i domini dei nomi degli host", false),
            Answer(text = "E' necessario il three-way handshake", false),
            Answer(text = "E' un dominio di routing che comunica con l'esterno utilizzando un Exterior Gateway Protocol quale il BGP", true),
            Answer(text = "Deve contenere network IP tutte della stessa classe", false)
          )
        ),
        Quiz(
          question = "Qualora si utilizzi in una rete un protocollo di routing di tipo flooding",
          maxScore = 13,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Ciascun nodo di commutazione deve assolutamente ritrasmettere un pacchetto sul collegamento da cui lo ha ricevuto", false),
            Answer(text = "Tutte le possibili destinazioni vengono sicuramente raggiunte dai pacchetti", true),
            Answer(text = "Tutte le possibili destinazioni non vengono raggiunte dai pacchetti", false),
            Answer(text = "Si ottiene il corretto instradamento dei pacchetti con l'utilizzazione minima possibile delle risorse di rete", false)
          )
        ),
      )
    ),
    // English B2
    SavedCourse(
      courseId = CourseIdentifier(
        courseName = "English B2",
        degreeName = "Laurea Magistrale in Ingegneria e Scienze Informatiche",
        universityName = "Università di Bologna"
      ),
      description = Option("English Certification B2 Course"),
      quizList = List(
        Quiz(
          question = "She's married and she has three ...........",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Childs", false),
            Answer(text = "Children", true),
            Answer(text = "Child", false),
            Answer(text = "Childrens", false),
            Answer(text = "Daughter", false),
            Answer(text = "Daughters", true),
            Answer(text = "Chicken", false)
          )
        ),
        Quiz(
          question = ".......... to concerts?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Does Sally go", true),
            Answer(text = "Do Sally go", false),
            Answer(text = "Does Sally goes", false),
            Answer(text = "Do Sally goes", false),
            Answer(text = "Is Sally going", true),
            Answer(text = "Sally go", false)
          )
        ),
        Quiz(
          question = "We always have snow .......... January.",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "in", true),
            Answer(text = "at", false),
            Answer(text = "on", false),
            Answer(text = "for", false),
            Answer(text = "from December to", true),
            Answer(text = "from December at", false)
          )
        ),
        Quiz(
          question = "There isn't .......... sugar in this coffee!",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "any", true),
            Answer(text = "no", false),
            Answer(text = "none", false),
            Answer(text = "some", false),
            Answer(text = "a lot of", true),
            Answer(text = "some lots of", false),
            Answer(text = "not", false)
          )
        ),
        Quiz(
          question = "Is this .......... house?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "your parents's", true),
            Answer(text = "your parents", false),
            Answer(text = "your parents'", false),
            Answer(text = "your parenthesis", false)
          )
        ),
        Quiz(
          question = "You .......... smoke here as you .......... cause a fire.",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "mustn't, may", true),
            Answer(text = "must, might", false),
            Answer(text = "mustn't, have to", false),
            Answer(text = "must not, may not", false),
            Answer(text = "have to, should", false),
            Answer(text = "should, could", false),
            Answer(text = "shouldn't, might", true)
          )
        ),
        Quiz(
          question = ".......... I don't see you before, have a nice holiday.",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Unless", false),
            Answer(text = "In case", true),
            Answer(text = "As soon as", false),
            Answer(text = "I am hoping", false),
            Answer(text = "Since", false),
            Answer(text = "As long as", false)
          )
        ),
        Quiz(
          question = "......... any eggs for breakfast this morning",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "There weren't", true),
            Answer(text = "There was", false),
            Answer(text = "There were", false),
            Answer(text = "There wasn't", false),
            Answer(text = "There is", false),
            Answer(text = "There are", false),
            Answer(text = "There aren't", true)
          )
        ),
        Quiz(
          question = "I eat .......... chocolate.",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "too much", true),
            Answer(text = "as many as", false),
            Answer(text = "too many", false),
            Answer(text = "a lot of", true),
            Answer(text = "much piece of", false),
            Answer(text = "many", false)
          )
        ),
        Quiz(
          question = "How long .......... her?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "have you known", true),
            Answer(text = "do you know", false),
            Answer(text = "you have known", false),
            Answer(text = "will you know", false),
            Answer(text = "had you been knowing", false),
            Answer(text = "you are going to know", false)
          )
        ),
        Quiz(
          question = "he doesn't read the newspaper every day. .......... I.",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "Neither do", true),
            Answer(text = "Neither does", false),
            Answer(text = "Neither doesn't", false),
            Answer(text = "So do", false),
            Answer(text = "Am", false),
            Answer(text = "Not", false)
          )
        ),
        Quiz(
          question = "They .......... English when I saw them. They .......... TV!",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "weren't studying, were watching", true),
            Answer(text = "weren't studying, are watching", false),
            Answer(text = "aren't studying, were watching", false),
            Answer(text = "are studying, are watching", false),
            Answer(text = "weren't studying, are playing", false),
            Answer(text = "were study, were eating the", false)
          )
        ),
        Quiz(
          question = "Why didn't they .......... the beds yesterday?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "make", true),
            Answer(text = "made", false),
            Answer(text = "do", false),
            Answer(text = "did", false),
            Answer(text = "burnt", false),
            Answer(text = "built", false)
          )
        ),
        Quiz(
          question = ".......... no meeting on Monday.",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "There will be", true),
            Answer(text = "There won't be no", false),
            Answer(text = "There isn't", false),
            Answer(text = "There are", false)
          )
        ),
        Quiz(
          question = "She told me .......... him any money.",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "not to lend", true),
            Answer(text = "to lend not", false),
            Answer(text = "not lend to", false),
            Answer(text = "to borrow", false),
            Answer(text = "not to borrow", false),
            Answer(text = "not to send", true),
            Answer(text = "to not borrow", false)
          )
        ),
        Quiz(
          question = "If you ...... everything in a diary, you ... forget anything.",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "did write, wouldn't", true),
            Answer(text = "wrote, wouldn't", false),
            Answer(text = "didn't write, would not", false),
            Answer(text = "write, wouldn't", false),
            Answer(text = "would write, didn't", false),
            Answer(text = "would wrote, won't", false),
            Answer(text = "will write, won't", true)
          )
        ),
      )
    ),
    // Example of Your Course
    SavedCourse(
      courseId = CourseIdentifier(
        courseName = "Your Course Example",
        degreeName = "Degree Name",
        universityName = "University Name"
      ),
      description = Option("Your optional Course Description"),
      quizList = List(
        Quiz(
          question = "Your Question Example",
          maxScore = 5,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "A Wrong Answer Example", false),
            Answer(text = "Another Wrong Answer Example", false),
            Answer(text = "The Wrongest Answer Example", false),
            Answer(text = "A Right Answer Example", true)
          )
        ),
      )
    ),
  )