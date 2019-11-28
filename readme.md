# FantaAPI
### Build and run
`mvn clean package spring-boot:run`


### Endpoints
##### fantateam/
- **GET**: restituisce tutti i fantateam di tutti gli utenti
- **DELETE**: elimina tutti i fantateam di tutti gli utenti
- **POST**: {*userId*, *teamName*} - dato id utente, crea fantateam per quell'utente, potrebbe essere user/fantateam, ma trattandosi di una creazione di fantateam non è meglio /fantateam?
##### fantateam/{id}
- **GET**: restituisce il fantateam
- **DELETE**: elimina il fantateam
##### fantateam/{id}/player
- **GET**: restituisce tutti player di un certo fantateam
- **POST**: {*playerId*,*teamId*} - aggiunge il player al fantateam
- **DELETE**: {*playerId*,*teamId*} - rimuove il player dal fantateam

##### user/
- **GET**: restituisce tutti gli utenti
- **POST**: {*username*, *email*} - crea un utente dati username, email (password non ancora, andrebbe gestita con crittografia)
- **DELETE**: eliminazione dell'utente? serve?

##### coach/
##### coach/{id}

##### president/
##### president/{id}

##### stadium/
##### stadium/{id}

##### prosecutor/
##### prosecutor/{id}
##### prosecutor/{id}/player
- **GET**: restituisce la lista dei giocatori seguiti dal prosecutor


### TODO
- Prosecutor: il campo playerId dovrebbe essere una lista?
- Fantateam: possibilità di rimuovere i giocatori
- Convenzione: tutti i path devono terminare con " */* "?

### FATTO
- Sistemati indici incrementali per *user* e *fantateam*
- PlayerStats: Aggiunti i valori *average gaussian rating*, *average gaussian fanta rating*, e *season team*
- Fantateam: non è più possibile aggiungere più volte lo stesso giocatore per lo stesso fantateam
- Player: aggiunti *teamId* e *nationality*
- Coach: aggiunto *module*
- Prosecutor: aggiunto il path */prosecutor/{id}/player/* che permette di ottenere tutti i giocatori seguiti dal prosecutor
