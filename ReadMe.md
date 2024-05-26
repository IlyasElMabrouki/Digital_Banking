# Banque Digitale

Ce projet est une application de banque digitale développée avec Spring Boot pour le backend et Angular pour le frontend. Il permet la gestion des clients et des comptes bancaires avec des fonctionnalités complètes comme la saisie, l'ajout, la suppression, l'édition et la recherche. La sécurité est gérée via JWT (JSON Web Token) pour authentifier et autoriser les utilisateurs.

## Fonctionnalités

### Gestion des Clients
- **Saisie de nouveaux clients** : Permet d'ajouter de nouveaux clients dans le système.
- **Ajout de clients** : Interface utilisateur pour l'ajout de nouveaux clients.
- **Suppression de clients** : Fonctionnalité pour supprimer des clients existants.
- **Édition de clients** : Permet la modification des informations des clients.
- **Recherche de clients** : Fonctionnalité de recherche avancée pour trouver des clients basés sur divers critères.

### Gestion des Comptes
- **Ajout de comptes** : Création de nouveaux comptes pour les clients existants.
- **Recherche de comptes** : Recherche et visualisation des comptes bancaires.
- **Administration des comptes** : Gestion complète des comptes, y compris l'édition et la suppression.

### Gestion des Opérations
- **Débit** : Permet d'effectuer des opérations de débit sur les comptes.
- **Crédit** : Permet d'effectuer des opérations de crédit sur les comptes.
- **Transfert** : Permet d'effectuer des transferts entre comptes.

## Sécurité

### Authentification et Autorisation
La sécurité de l'application est gérée avec Spring Security et JWT. Les utilisateurs doivent s'authentifier pour accéder aux fonctionnalités protégées de l'application.

#### Flux de Travail JWT
1. **Connexion** : L'utilisateur envoie ses identifiants au serveur.
2. **Génération du Token** : Si les identifiants sont valides, un token JWT est généré et renvoyé à l'utilisateur.
3. **Accès Protégé** : L'utilisateur inclut le token JWT dans l'en-tête des requêtes pour accéder aux ressources protégées.
4. **Validation du Token** : Le serveur valide le token JWT pour chaque requête protégée.

## Structure du Projet

Le projet est divisé en deux parties principales :

1. **Backend (Spring Boot)** : Gestion de la logique métier, des contrôleurs et des services pour la gestion des clients, des comptes et des opérations. Implémentation de la sécurité avec JWT.
2. **Frontend (Angular)** : Interface utilisateur pour l'interaction avec le système.
