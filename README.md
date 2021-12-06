# API-2021-SMTP

Dans le cadre du cours API à l'HEIG-VD, nous avons implémenté un client SMTP dont l'objectif est d'envoyer des mails pranks à une liste de contacts.
Il est possible de modifier la liste des contacts ainsi que des messages en modifiant les fichiers [victims.txt](victims.txt) et [pranks.txt](pranks.txt).

## MockMock

MockMock est un outil qui permet de tester notre client SMTP.
Si vous désirez tester notre client sur un faux serveur SMPT veuillez suivre les étapes qui suivent.

## Installer MockMock
Prérequis: Docker
### Etapes:
Créer un container:
```
docker build -t mockmock:latest
```
Lancer le container
```
docker run -p 8282:8282 25:25 api2021smtp
```
## UI de MockMock

Pour accéder à l'interface utilisateur de MockMock rendez-vous à cette adresse: [http://localhost:8282](http://localhost:8282)

### Comment utiliser notre client SMTP
