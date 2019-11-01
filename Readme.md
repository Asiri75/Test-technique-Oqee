# Trax Test technique
Ce projet est un test technique pour Trax. C'est une application Android développée en Kotlin.
Elle a pour objectif de charger et afficher une liste de films provenant d'un api protégé.  

## Description de l'application
## Analyse fonctionnelle

### Comportement de l'application
L'application charge et affiche la liste des films.
Pour chaque élément, elle affiche la couverture et le titre du film.
Lorsque l'on clique sur l'un d'entre eux, elle lance un nouvel écran permettant de lire la bande d'annonce.

### Comportement du code
Pour faire cela, l'application charge les données en utilisant le CA fourni par l'entreprise.
Nous récupérons les informations dont nous avons besoin (titre du film, url de couverture, url du trailer). Ces informations sont directement envoyées à la vue (pas de stockage en base de données interne).

### Choix des composants du JSON de l'api
L'API fourni par Trax contient énormément d'informations. Ces dernières ne sont pas toutes utiles pour le test technique. Nous avons donc sélectionné les informations dont nous avions besoin:
#### Titre du film:
Chemin: page/movie_title
#### URL de la couverture du film:
Chemin: heros/0/imageurl
#### URL de la bande d'annonce du film:
Chemin: clips/0/versions/enus/sizes/sd/srcAlt
Nous avons choisi "srcAlt" car le format de la vidéo de "src" (.mov) n'est pas supportée par ExoPlayer
Nous avons également choisi "sd" pour des raisons de performance en lien avec le test #YouSaidKeepItSimple

## Analyse technique
### Architecture
La structure de l'application est en MVP.
Elle dispose donc d'une logique métier détachée de la vue.
Le "Presenter" assure l'échange entre les deux.

Pour assurer cette structure, nous utilisons un système d'interface à double sens (deux interfaces, la vue implémentant l'une et le presenter l'autre)

### Choix des librairies

#### Récupération des données de l'API
 
Pour faire une requête HTTP vers le l'API public, nous utilisons la librairie **Retrofit** développée par Square.
Cette librairie a également l'avantage de simplifier l'appel vers le serveur. Accompagnée de la librairie **GSON converter**, elle permet également de simplifier la conversion de l'objet JSON reçu en liste d'objets prête à être intégrée dans la base de données. Nous avons également personnalisé le convertisseur afin de seulement récupérer les informations dont nous avons besoin.


#### Lecture de la bande d'annonce
Pour charger et lire la vidéo de la bande d'annonce, nous utilisons la librairie **ExoPlayer** développée par Google. Il est vrai que la librairie de base suffisait largement à réaliser ce que nous souhaitions mais le développeur a souhaité s'initier à la librairie **ExoPlayer**. 

#### Autres librairies utilisées

**RxJava**: Cette librairie est intervenue en complément de Retrofit afin de réaliser l'appel en asynchrone assez simplement.
**RecyclerView**: Pour l'affichage des albums et titre sous forme de liste
**Glide**: Pour le chargement simplifié des images des titres
**Timber**: Pour simplifier les logs (pas besoin de TAG, la librairie prend directement le nom de la classe contenant le log)