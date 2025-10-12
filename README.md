# algoForDummies

Projet Java pour l'apprentissage des algorithmes.

## Structure
- `src/main/java` : code source Java
- `src/test/java` : tests unitaires

## Description 

### Chapitre 6 : structure de données

Gestion d'une stack LIFO (last in, first out). Push ajoute un élément, Pop retire le dernier élément ajouté.
Ensuite on a écrit un arbre binaire (similaire au design-pattern composite). On le parcours ensuite de haut en bas.
Enfin on créé un graphe bi-directionnel et l'algorithme permet de trouver un chemin d'un point de départ à un
point de destination.

### Chapitre 7 : tri de données

Le tri est présenté sous différente forme : 
- tri par sélection (selection sort) : 
    - le programme recherche le plus petit élément de la liste et le place en tête de liste,
    - il répète l’opération pour le reste de la liste, en ignorant le début déjà trié.
- tri par insertion (insertion sort) : 
    -  utilise un élément unique comme point de départ, puis ajoute progressivement les autres éléments à la liste triée.
- tri par fusion (merge sort) : 
    - Décompose le jeu de données en parties séparées et trie ces parties.
    - Ensuite il s'agit de fusionner ces parties triées pour obtenir le jeu de données final trié.
- tri rapide (quick sort) :
    - Il choisit un pivot, met tous les nombres plus petits à gauche 
        et tous les nombres plus grands à droite, puis trie récursivement les sous-tableaux.
    - Utilise la récursion : profondeur moyenne O(log n), pire cas O(n).

La page suivante est un rappel pour chaque algo de tri les performances qui sont attendues : 

    https://www.interviewcake.com/sorting-algorithm-cheat-sheet

Pour rechercher des données :
- arbre binaire de recherche (Binary Seach Tree) : 
    - structure de données hiérarchique dans laquelle :
        - chaque nœud contient une valeur (ex. un nombre) ;
        - chaque nœud a au plus deux enfants :
        - un enfant gauche ;
        - un enfant droit ;
        - la structure respecte une règle d’ordre :
          - Toutes les valeurs du sous-arbre gauche < valeur du nœud
          - Toutes les valeurs du sous-arbre droit > valeur du nœud
- pour faire des recherches on peut utiliser le hashage, tel qu'il est utilisé en Java dans la map HashMap
    - HashSet, qui s'occupe de la clé, calcule automatiquement le hashcode de chaque élément (String ici).
    - Lorsqu’on appelle .contains(), Java ne parcourt pas toute la liste : il calcule le même hashcode et va directement à la “case” correspondante → très rapide ⚡ 

### Chapitre 9 : parcours de graphe

- Dans un premier temps, on parcours un graphe en largeur (BFS, pour Breadth-First Search en anglais) afin d'obtenir le plus cours chemin entre deux points. Il fonctionne avec une file dont la structure de données de type premier entré / premier sorti (FIFO). Il est plus rapide et utilise plus de mémoire.

- Dans un deuxième temps, on parcours un graphe en profondeur (DFS, pour Deep-First Search en anglais) afin d'obtenir l'exploration de chaque chemin en totalité. La pile fonctionne sur le principe dernier entré / premier sorti (LIFO). Il est plus détaillé et optimisé, car on est sûr que tous les chemins soient explorés.

## Compilation
Utilisez Maven ou Gradle pour compiler et exécuter le projet.

## Auteur
Jean-Baptiste Renaux