#FantaAPI

#####fantateam/
- **GET**: restituisce tutti i fantateam di tutti gli utenti
- **DELETE**: elimina tutti i fantateam di tutti gli utenti
- **POST**: {*userId*, *teamName*} - dato id utente, crea fantateam per quell'utente, potrebbe essere user/fantateam, ma trattandosi di una creazione di fantateam non è meglio /fantateam?
#####fantateam/{id}
- **GET**: restituisce il fantateam
- **DELETE**: elimina il fantateam
#####fantateam/{id}/player
- **GET**: restituisce tutti player di un certo fantateam
- **POST**: {*playerId*,*teamId*} - aggiunge il player al fantateam
- **DELETE**: {*playerId*,*teamId*} - rimuove il player dal fantateam

#####user/
- **GET**: restituisce tutti gli utenti
- **POST**: {*username*, *email*} - crea un utente dati username, email (password non ancora, andrebbe gestita con crittografia)
- **DELETE**: eliminazione dell'utente? serve?

#####coach/
#####coach/{id}

#####president/
#####president/{id}

#####stadium/
#####stadium/{id}