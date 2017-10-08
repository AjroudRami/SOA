# ESB-SOA
Authors : 
  - AJROUD Rami
  - AUBE Antoine
  - BIN AHMAD Danial
  - JUNGBLUTH Günther

# Case Study: The Virtual Travel Agency


## Découpage

Nous avons découpé le projet par métier car nous pensons qu’il est plus vraisemblable de trouver un service qui effectue un métier en particulier et candidat à être utilisé par notre système ; nous jugeons également qu’il sera dans ce cas là simple d’effectuer la modification (plus simple qu’avec une conception où les logiques métiers sont éparpillées à travers plusieurs services).

Nous avons identifié cinq métiers : location de voitures, recherche d’hôtel, constitution et validation d’un trajet, réservation d’un vol, notifications.

Comme le service de notification nous a paru avoir moins de valeur et être externe au projet, nous ne l’avons pas développé.

## Services

### Cars (RPC)

[Interface](https://github.com/scipio3000/polytech-soa/tree/master/services/cars)

#### Paradigme

Dans une moindre mesure, nous avons choisi RPC pour ce service car il y avait la contrainte d’avoir au moins un service de chaque paradigme. C’est celui-ci qui a été choisi pour RPC car il nous semble être le service le moins enclin à évoluer (ce qui est un avantage pour ce paradigme car nous n’avons pas à recharger le stub, …). Nous pensons que les évolutions seront faibles car il n’y a pas beaucoup de variabilité de produit dans la location de voiture.

### Hotels (Resource)

[Interface](https://github.com/scipio3000/polytech-soa/tree/master/services/hotels)

#### Paradigme

Il y a une variabilité de la ressource (exit RPC) mais pas des actions à effectuer dessus (pas besoin de document).
Il est difficile d’anticiper les possibilités offertes par un hôtel car les produits sont extrêmement variés et en évolution. Les actions, elles sont toujours les même (lister, réserver, …) et peuvent être utilisées avec les verbes du paradigme ressources (put, get, post, …).

### Business Travels (Document)

[Interface](https://github.com/scipio3000/polytech-soa/tree/master/services/business-travels)

#### Paradigme

Nous avons choisis ce paradigme car si l’objet “trajet” ne va pas beaucoup évoluer (à notre avis), les manières de le manipuler vont se diversifier par la suite (exit ressources dont la sémantique nous aurait limité). Nous ne choisissons pas RPC également pour cette raison d’évolutivité.

Exemples de nouvelles manipulations des trajets : fournir une explication du refus d’un trajet, éditer les étapes d’un trajet existant.

### Flights (Document)

[Interface](https://github.com/scipio3000/polytech-soa/tree/master/services/flights)

#### Paradigme

Nous avons choisis ce paradigme car la notion de “préférence du voyageur” n’est pas suffisamment définie et est vouée à évoluer. On identifie un vol comme étant une ressource. Cependant, la grande variabilité dans les actions possibles et leurs parametres nous empeche de choisir le paradigme "Resouces". "Resource" ne nous permettrait pas une évolution complexe car toute récupération doit être transcrite dans les paramètres de l’URL. RPC n’est pas raisonnable car cela bloquerait notre définition d’une préférence.
