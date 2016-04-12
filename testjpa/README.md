#SIR - TP2 et TP3 : JPA et Servlet/Rest

Ce projet regroupe deux TP de SIR, dans le cadre de la formation de M1 Miage Rennes.Ces deux TP ont pour but de nous faire (re)découvrir le JPA (Gestion de la BDD avec du code Java) et les Servlet/Rest (Faire des applications webServices avec une architecture 3 tiers (JPA,Traitement,IHM).

##JPA

Le tp2 permet donc de voir une base sur les JPA.On a vu deux choses:

###Création des entities

Dans un premier temps on a crée les entity de notre BDD (le package domain) où on a vu:
--> La déclaration d'entité:  
-> @Entity pour la classe.  
-> @Id @GeneratedValue pour la variables qui servira d'id.  
-> Ne pas oublier les accesseurs pour que les variables soient considérés comme des attributs pour la BDD.  

--> La création de liens entre les Entity:  
-> Le OneToMany et le ManyToOne (voir classe Home.java par exemple).  
-> Le ManyToMany (où il ne faut pas oublier l'annotation @JoinTable (voir Person.java)).  

--> L'Héritage:  
-> Ici une Table unique pour tous les élements (@Inheritance(strategy = InheritanceType.SINGLE_TABLE) dans la super classe).

###Utilisation du JPA

Ensuite on a utilisé le JPA pour faire différentes choses (voir fichier JpaTest.java).

--> Création de données en utilisant les Entity et le méthode de JPA:  
-> Ne pas oublier de définir l'entityManager avant dans un pattern Singleton.  
-> Toute mise à jour/suppression/ajout se fait dans une transaction ( tx.begin();/tx.commit();).

--> On a fait des requete classiques de selection avec récupérations et affichage des données dans la BDD.

--> On a également fait des requetes en critéria qui évite de rentrer en "dur" la requete sql et donc la requete s'adapate à la structure de la table (notament quand elle change avec l'heritage entre les entities).

--> On a abordé les requêtes nommées (@NamedQuery(name="Person.findAll", query="Select s from Person s") dans l'entity puis l'appel dans la classe Jpatest.java)).

--> Enfin on a vu le cas particulier du N+1 Select (quand on fait plusieurs Select au lieu d'un seul):

On a fait une comparaison entre le lazy loading (bonne solution) et le n+1 select (mauvaise solution)

->N+1 Select = 16,830 s.  
->Join Fetch (lazy loading) = 0,382 s.

##Servlet et rest

Le TP3 permet de découvrir le Servlet puis la mise en place d'une architecture classique trois-tiers et ,également, l'utilisation du Rest à la place d'un servlet.

###Servlet

La premiére étape est de tester simplement le fonctionnement d'un servlet (MyServlet.java) en get puis en post avec un formulaire (myForm.html) et une réponse en JSON suite au remplissage avec le Servlet dans UserInfo.java.

La seconde étape à été de mettre en place une architecture 3-tiers autour de Opower (dont on fait les Entities dans le TP2).

-> Il faut donc faire la couche JPA: c'est la classe OpowerJpa.java qui regroupe l'ensemble des requêtes JPA qui seront appellées par la couche de traitement.  

-> Il faut ensuite crée le ou les servlets: c'est à dire l'ensemble des classes qui réagiront lorsque le client enverra des requêtes GET/POST à certaines URL.  
Ici on a un get qui liste l'ensemble des données dans la BDD et un post qui rajoute une personne depuis les infos récuperer sur un formulaire HTML (OpowerServlet.java).  
Attention ne pas oublier d'initialiser et de supprimer l'entity manager lors du lancement et de l'arret du servlet.

-> Les fichiers html et les réponses renvoyées par le servlet consititue la parite IHM/vue de notre architecture (côté client). 

####Remarques sur les servlet

-> Attention toujours renvoyer une réponse (redirection/html/json...) dans doPsot/doGet.  

-> Le lien entre les formulaire html et le servlet se fait avec: <FORM Method="POST" Action="/opower">,
on récupére les paramêtres avec le "name" indiqué pour les elements du formulaires.  

-> Eviter que hibernate ecrase toute la base en sql:  <property name="hibernate.hbm2ddl.auto" value="update"/> au lieu de <property name="hibernate.hbm2ddl.auto" value="create"/> dans persistance.xml.  

-> Limite: En servlet on peut PAS mettre deux path differents dans une meme classe: donc il faut mieux créer une classe "metier/service" avec les méthodes de traitement et les classes de chaque path qui appelle les méthodes.  


###Rest

La seconde partie du TP consiste à utiliser une alternative aux Servlets avec l'architecture REST.  

La premiére étape est de tester simplement le fonctionnement du Rest (SampleWebService.java) en get puis en post avec la création d'un objet et une réponse en JSON.  

Puis on a refait, avec l'architecture REST, les mêmes choses que dans la classe OpowerServlet.java (classe: OpowerRest.java).  

###Remarques sur le REST

--> @FormParam("name") String name => dans les paramêtres d'une méthode pour récuperer les données (ici "name") d'un formulaire Html en post

--> Rajouter l'annotation "@XmlRootElement" à tous les objets dont on veut produire le JSON.

--> Attention bien gerer la gestion de l'entity manager du JPA avec le rest (il n'y a pas de méthode qui est appellée lors du démarrage et de l'arret d'un webService Rest)

#TP réalisé par PHILIP Mikaël et JELASSI Seifeddine
