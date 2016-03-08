#SIR - TP2 et TP3 : JPA et Servlet/Rest

Ce projet regroupe deux TP de SIR, dans le cadre de la formation de M1 Miage Rennes.Ces deux TP ont pour but de nous faire (re)d�couvrir le JPA (Gestion de la BDD avec du code Java) et les Servlet/Rest (Faire des applications webServices avec une architecture 3 tiers (JPA,Traitement,IHM).

##JPA

Le tp2 permet donc de voir une base sur les JPA.On a vu deux choses:

###Cr�ation des entities

Dans un premier temps on a cr�e les entity de notre BDD (le package domain) o� on a vu:
--> La d�claration d'entit�:
-> @Entity pour la classe.
-> @Id @GeneratedValue pour la variables qui servira d'id.
-> Ne pas oublier les accesseurs pour que les variables soient consid�r�s comme des attributs pour la BDD.

--> La cr�ation de liens entre les Entity:
-> Le OneToMany et le ManyToOne (voir classe Home.java par exemple).
-> Le ManyToMany (o� il ne faut pas oublier l'annotation @JoinTable (voir Person.java)).

--> L'H�ritage:
-> Ici une Table unique pour tous les �lements (@Inheritance(strategy = InheritanceType.SINGLE_TABLE) dans la super classe).

###Utilisation du JPA

Ensuite on a utilis� le JPA pour faire diff�rentes choses (voir fichier JpaTest.java).

--> Cr�ation de donn�es en utilisant les Entity et le m�thode de JPA:
-> Ne pas oublier de d�finir l'entityManager avant dans un pattern Singleton.
-> Toute mise � jour/suppression/ajout se fait dans une transaction ( tx.begin();/tx.commit();).

--> On a fait des requete classiques de selection avec r�cup�rations et affichage des donn�es dans la BDD.

--> On a �galement fait des requetes en crit�ria qui �vite de rentrer en "dur" la requete sql et donc la requete s'adapate � la structure de la table (notament quand elle change avec l'heritage entre les entities).

--> On a abord� les requ�tes nomm�es (@NamedQuery(name="Person.findAll", query="Select s from Person s") dans l'entity puis l'appel dans la classe Jpatest.java)).

--> Enfin on a vu le cas particulier du N+1 Select (quand on fait plusieurs Select au lieu d'un seul):

On a fait une comparaison entre le lazy loading (bonne solution) et le n+1 select (mauvaise solution)

->N+1 Select = 16,830 s.
->Join Fetch (lazy loading) = 0,382 s.

##Servlet et rest

Le TP3 permet de d�couvrir le Servlet puis la mise en place d'une architecture classique trois-tiers et ,�galement, l'utilisation du Rest � la place d'un servlet.

###Servlet

La premi�re �tape est de tester simplement le fonctionnement d'un servlet (MyServlet.java) en get puis en post avec un formulaire (myForm.html) et une r�ponse en JSON suite au remplissage avec le Servlet dans UserInfo.java.

La seconde �tape � �t� de mettre en place une architecture 3-tiers autour de Opower (dont on fait les Entities dans le TP2).

-> Il faut donc faire la couche JPA: c'est la classe OpowerJpa.java qui regroupe l'ensemble des requ�tes JPA qui seront appell�es par la couche de traitement.

-> Il faut ensuite cr�e le ou les servlets: c'est � dire l'ensemble des classes qui r�agiront lorsque le client enverra des requ�tes GET/POST � certaines URL.
Ici on a un get qui liste l'ensemble des donn�es dans la BDD et un post qui rajoute une personne depuis les infos r�cuperer sur un formulaire HTML (OpowerServlet.java).
Attention ne pas oublier d'initialiser et de supprimer l'entity manager lors du lancement et de l'arret du servlet.

-> Les fichiers html et les r�ponses renvoy�es par le servlet consititue la parite IHM/vue de notre architecture (c�t� client). 

####Remarques sur les servlet

-> Attention toujours renvoyer une r�ponse (redirection/html/json...) dans doPsot/doGet.

-> Le lien entre les formulaire html et le servlet se fait avec: <FORM Method="POST" Action="/opower">,
on r�cup�re les param�tres avec le "name" indiqu� pour les elements du formulaires.

-> Eviter que hibernate ecrase toute la base en sql:  <property name="hibernate.hbm2ddl.auto" value="update"/> au lieu de <property name="hibernate.hbm2ddl.auto" value="create"/> dans persistance.xml

-> Limite: En servlet on peut PAS mettre deux path differents dans une meme classe: donc il faut mieux cr�er une classe "metier/service" avec les m�thodes de traitement et les classes de chaque path qui appelle les m�thodes.


###Rest

La seconde partie du TP consiste � utiliser une alternative aux Servlets avec l'architecture REST. 

La premi�re �tape est de tester simplement le fonctionnement du Rest (SampleWebService.java) en get puis en post avec la cr�ation d'un objet et une r�ponse en JSON.

Puis on a refait, avec l'architecture REST, les m�mes choses que dans la classe OpowerServlet.java (classe: OpowerRest.java).

###Remarques sur le REST

--> @FormParam("name") String name => dans les param�tres d'une m�thode pour r�cuperer les donn�es (ici "name") d'un formulaire Html en post

--> Rajouter l'annotation "@XmlRootElement" � tous les objets dont on veut produire le JSON.

--> Attention bien gerer la gestion de l'entity manager du JPA avec le rest (il n'y a pas de m�thode qui est appell�e lors du d�marrage et de l'arret d'un webService Rest)

#TP r�alis� par PHILIP Mika�l et JELASSI Seifeddine
