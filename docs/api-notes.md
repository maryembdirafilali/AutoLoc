# Atelier 5 : Spring MVC et SpringDoc — Exposer les services en API REST, tester avec Postman et documenter avec Swagger

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Codes d'erreur |
| :--- | :--- | :--- | :--- | :--- |
| `ContratController` | `GET /api/contrats` | `IContratService.findAll()` | 200 OK | — |
| `ContratController` | `GET /api/contrats/{id}` | `IContratService.findById(id)` | 200 OK | 404 Not Found |
| `ContratController` | `POST /api/contrats` | `IContratService.create(contrat)` | 201 Created | 400 Bad Request |
| `ContratController` | `PUT /api/contrats/{id}` | `IContratService.update(id, contrat)` | 200 OK | 400 Bad Request, 404 Not Found |
| `ContratController` | `DELETE /api/contrats/{id}` | `IContratService.deleteById(id)` | 204 No Content | 404 Not Found |
| `PaiementController` | `GET /api/paiements` | `IPaiementService.findAll()` | 200 OK | — |
| `PaiementController` | `GET /api/paiements/{id}` | `IPaiementService.findById(id)` | 200 OK | 404 Not Found |
| `PaiementController` | *(POST /api/paiements)* | *(Non exposé - lecture seule)* | — | 405 Method Not Allowed |
| `AgenceController` | `GET /api/agences` | `IAgenceService.findAll()` | 200 OK | — |
| `AgenceController` | `GET /api/agences/{id}` | `IAgenceService.findById(id)` | 200 OK | 404 Not Found |
| `AgenceController` | `POST /api/agences` | `IAgenceService.create(agence)` | 201 Created | 400 Bad Request |
| `AgenceController` | `PUT /api/agences/{id}` | `IAgenceService.update(id, agence)` | 200 OK | 400 Bad Request, 404 Not Found |
| `AgenceController` | `DELETE /api/agences/{id}` | `IAgenceService.deleteById(id)` | 204 No Content | 404 Not Found |
| `ClientController` | `GET /api/clients` | `IClientService.findAll()` | 200 OK | — |
| `ClientController` | `GET /api/clients/{id}` | `IClientService.findById(id)` | 200 OK | 404 Not Found |
| `ClientController` | `POST /api/clients` | `IClientService.create(client)` | 201 Created | 400 Bad Request |
| `ClientController` | `PUT /api/clients/{id}` | `IClientService.update(id, client)` | 200 OK | 400 Bad Request, 404 Not Found |
| `ClientController` | `DELETE /api/clients/{id}` | `IClientService.deleteById(id)` | 204 No Content | 404 Not Found |
| `VehiculeController` | `GET /api/vehicules` | `IVehiculeService.findAll()` | 200 OK | — |
| `VehiculeController` | `GET /api/vehicules/{id}` | `IVehiculeService.findById(id)` | 200 OK | 404 Not Found |
| `VehiculeController` | `POST /api/vehicules` | `IVehiculeService.create(vehicule)` | 201 Created | 400 Bad Request |
| `VehiculeController` | `PUT /api/vehicules/{id}` | `IVehiculeService.update(id, vehicule)` | 200 OK | 400 Bad Request, 404 Not Found |
| `VehiculeController` | `DELETE /api/vehicules/{id}` | `IVehiculeService.deleteById(id)` | 204 No Content | 404 Not Found |
| `EmployeController` | `GET /api/employes` | `IEmployeService.findAll()` | 200 OK | — |
| `EmployeController` | `GET /api/employes/{id}` | `IEmployeService.findById(id)` | 200 OK | 404 Not Found |
| `EmployeController` | `POST /api/employes` | `IEmployeService.create(employe)` | 201 Created | 400 Bad Request |
| `EmployeController` | `PUT /api/employes/{id}` | `IEmployeService.update(id, employe)` | 200 OK | 400 Bad Request, 404 Not Found |
| `EmployeController` | `DELETE /api/employes/{id}` | `IEmployeService.deleteById(id)` | 204 No Content | 404 Not Found |
| `EquipementController` | `GET /api/equipements` | `IEquipementService.findAll()` | 200 OK | — |
| `EquipementController` | `GET /api/equipements/{id}` | `IEquipementService.findById(id)` | 200 OK | 404 Not Found |
| `EquipementController` | `POST /api/equipements` | `IEquipementService.create(equipement)` | 201 Created | 400 Bad Request |
| `EquipementController` | `PUT /api/equipements/{id}` | `IEquipementService.update(id, equipement)` | 200 OK | 400 Bad Request, 404 Not Found |
| `EquipementController` | `DELETE /api/equipements/{id}` | `IEquipementService.deleteById(id)` | 204 No Content | 404 Not Found |
| `ReservationController` | `GET /api/reservations` | `IReservationService.findAll()` | 200 OK | — |
| `ReservationController` | `GET /api/reservations/{id}` | `IReservationService.findById(id)` | 200 OK | 404 Not Found |
| `ReservationController` | `POST /api/reservations` | `IReservationService.create(reservation)` | 201 Created | 400 Bad Request |
| `ReservationController` | `PUT /api/reservations/{id}` | `IReservationService.update(id, reservation)` | 200 OK | 400 Bad Request, 404 Not Found |
| `ReservationController` | `DELETE /api/reservations/{id}` | `IReservationService.deleteById(id)` | 204 No Content | 404 Not Found |
| `MaintenanceController` | `GET /api/maintenances` | `IMaintenanceService.findAll()` | 200 OK | — |
| `MaintenanceController` | `GET /api/maintenances/{id}` | `IMaintenanceService.findById(id)` | 200 OK | 404 Not Found |
| `MaintenanceController` | `POST /api/maintenances` | `IMaintenanceService.create(maintenance)` | 201 Created | 400 Bad Request |
| `MaintenanceController` | `PUT /api/maintenances/{id}` | `IMaintenanceService.update(id, maintenance)` | 200 OK | 400 Bad Request, 404 Not Found |
| `MaintenanceController` | `DELETE /api/maintenances/{id}` | `IMaintenanceService.deleteById(id)` | 204 No Content | 404 Not Found |

---

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
| :--- | :--- | :--- |
| `Contrat` $\leftrightarrow$ `Paiement` | `@JsonManagedReference` sur `Contrat.paiements`<br>`@JsonBackReference` sur `Paiement.contrat` | Vraie composition : les paiements font partie intégrante du JSON du contrat et sont créés/sérialisés avec lui tout en évitant la boucle infinie. |
| `Agence` $\leftrightarrow$ `Vehicule` | `@JsonIgnore` sur `Agence.vehicules` | Évite la boucle de sérialisation et le chargement d'une liste massive. Le véhicule désigne son agence par son identifiant. |
| `Agence` $\leftrightarrow$ `Employe` | `@JsonIgnore` sur `Agence.employes` | L'employé porte la référence vers son agence ; la liste inverse dans l'agence est ignorée en sérialisation. |
| `Client` $\leftrightarrow$ `Reservation` | `@JsonIgnore` sur `Client.reservations` | Empêche la récursion infinie tout en permettant à la réservation de porter son objet client (`{"client": {"idClient": 1}}`). |
| `Vehicule` $\leftrightarrow$ `Reservation` | `@JsonIgnore` sur `Vehicule.reservations` | Évite la boucle sans bloquer la sérialisation du véhicule dans la réservation. |
| `Vehicule` $\leftrightarrow$ `Maintenance` | `@JsonIgnore` sur `Vehicule.maintenances` | Évite la récursion cyclique côté véhicule. |
| `Vehicule` $\leftrightarrow$ `Equipement` | `@JsonIgnore` sur `Equipement.vehicules` | Relation ManyToMany : `Vehicule` expose sa liste d'équipements, la référence inverse dans `Equipement` est masquée. |
| `Reservation` $\leftrightarrow$ `Contrat` | `@JsonIgnore` sur `Reservation.contrat` | Relation OneToOne bidirectionnelle : le `Contrat` référence sa `Reservation` ; la référence inverse est ignorée. |

### Différence entre `@JsonBackReference` et `@JsonIgnore` :
- `@JsonBackReference` fonctionne en binôme avec `@JsonManagedReference`. La propriété est omise lors de la sérialisation (évitant la boucle), mais lors de la désérialisation, Jackson peut automatiquement restaurer la référence parent-enfant.
- `@JsonIgnore` ignore totalement la propriété à la sérialisation (lecture) et/ou à la désérialisation. Si on l'utilisait par exemple sur `Reservation.client`, le client serait ignoré lors du `POST`, créant une réservation orpheline.

---

## C. Postman et Swagger

| Outil | Rôle principal | Apport et exemple concret rencontré |
| :--- | :--- | :--- |
| **Postman** | Client de test dynamique et automatisé | Permet de construire des scénarios de test complets, de valider les codes de retour HTTP (200, 201, 204, 400, 404, 405) et de mémoriser dynamiquement l'identifiant généré (`pm.environment.set("contratId", pm.response.json().idContrat)`). |
| **Swagger / SpringDoc** | Documentation vivante et exploration interactive | Génère automatiquement le contrat OpenAPI à partir du code (`/api/v3/api-docs`) et fournit une interface visuelle (`/api/swagger-ui.html`) pour documenter les modèles, les résumés d'opérations (`@Operation`) et tester rapidement des appels sans configuration préalable. |

---

## D. Anomalies SonarQube for IDE (SonarLint)

| Anomalie | Règle / Explication | Correction apportée |
| :--- | :--- | :--- |
| Injection par champ (`@Autowired private IContratService ...`) | **S6813** : Masque les dépendances et empêche l'immuabilité des contrôleurs. | Remplacée par une injection par constructeur via `@RequiredArgsConstructor` et champs `private final`. |
| Méthodes de contrôleur sans codes de retour explicites | Bonnes pratiques REST / OpenAPI | Ajout des annotations `@ResponseStatus(HttpStatus.CREATED)` pour POST et `@ResponseStatus(HttpStatus.NO_CONTENT)` pour DELETE. |
| Absence de gestion globale des exceptions métier | Gestion d'erreurs HTTP | Création de `GlobalExceptionHandler` annoté `@RestControllerAdvice` pour mapper proprement `ResourceNotFoundException` vers 404 et `IllegalArgumentException` vers 400. |

---

## E. Questions de Compréhension

1. **Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode du contrôleur à appeler ?**  
   C'est la **`DispatcherServlet`** (le contrôleur frontal de Spring MVC). Elle consulte le composant **`HandlerMapping`** qui compare l'URL et la méthode HTTP de la requête entrante avec les annotations `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc., pour identifier la méthode exacte du contrôleur cible.

2. **Pourquoi `ContratController` dépend-il de `IContratService` et non de `ContratRepository` ni de `ContratServiceImpl` ?**  
   Pour respecter le principe d'architecture en couches et le principe d'inversion des dépendances (SOLID) :
   - Le contrôleur ne doit pas accéder directement au repository afin de garantir que toutes les règles métier et validations (ex: montant positif, persistance en cascade) soient appliquées par la couche service.
   - Il dépend de l'interface `IContratService` (et non de l'implémentation) pour un couplage faible et une testabilité aisée avec des mocks.

3. **Pourquoi un POST renvoie-t-il 201 et un DELETE 204 ? Pourquoi une ressource absente donne-t-elle 404 et non 500 ?**  
   - **`201 Created`** indique explicitement qu'une nouvelle ressource a été créée sur le serveur (avec son URI/ID).
   - **`204 No Content`** indique que l'action a réussi avec succès mais qu'il n'y a aucun contenu à renvoyer dans le corps de réponse.
   - **`404 Not Found`** est une erreur côté client (la ressource demandée n'existe pas en base), alors que **`500 Internal Server Error`** désigne un bug ou une défaillance inattendue côté serveur.

4. **Pourquoi `PaiementController` n'expose-t-il que des GET ? Quelle réponse reçoit un POST `/api/paiements` ?**  
   Parce que dans le modèle métier AutoLoc, le cycle de vie d'un paiement dépend exclusivement de son `Contrat` (relation de composition). La création de paiements s'effectue via l'enregistrement du contrat (`Contrat.paiements`). Un appel `POST /api/paiements` reçoit un statut HTTP **`405 Method Not Allowed`**.

5. **Quels risques présente l'exposition directe des entités JPA ? Pourquoi `Contrat.paiements` utilise-t-il `@JsonManagedReference` alors que `Agence.vehicules` utilise `@JsonIgnore` ?**  
   - **Risques de l'exposition directe** : Couplage fort entre le modèle de base de données et l'API publique, risque de sérialiser des champs sensibles ou des proxies Hibernate non initialisés (`LazyInitializationException`), et risque de boucles infinies de sérialisation.
   - **`Contrat.paiements`** utilise `@JsonManagedReference` car c'est une composition : le client a besoin de recevoir et d'envoyer la liste des paiements directement imbriquée dans le JSON du contrat.
   - **`Agence.vehicules`** utilise `@JsonIgnore` car la liste des véhicules n'a pas vocation à être sérialisée en entier à chaque consultation d'agence (volume potentiellement lourd et boucle avec l'attribut `Vehicule.agence`).
