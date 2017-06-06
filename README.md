# ProjetAlgo

nécéssite le package gson-2.8.0.jar et stdlib-package.jar;
###Processus d'aquisition/stockage de donnés:
- Les fichier sont découpés et suivant qu'ils sont en stop.txt ou stop_time.txt ils sont pré analysés par stopHandler (description des arrets) ou stopTimeHandler (regroupement des n chemins différents d'une ligne)
- Chaque cluster est converti en autant de graph (GraphTools.Graph.graphFromSmallListAndStopContent)
- Tous ces mini graph sont mergés, de même que toutes les lignes de métro peuvent être mergées.
- Puis on utilise gson un outil de google servant à convertir ou extraire un objet en json. On écrit cet objet dans data.json. Bien sur, pour éviter de passer en json des objets qui s'appelent cycliqument, on ne renseigne que les identifiants des noeuds dans les edges (d'où le "transient protected GraphTools.Node from")
- On peut donc charger data.json pour retrouver rapidement le graph, sans passer par les longues étapes de lecture qui précèdent (une dizaine de secondes). Il faut cependans consolider le résultat car les objets qui pointanent vers les memes références ne le font plus (du nottament au transcient nomé plus haut)
 
###Les algorithmes :
- Pour Djkstra et Bfsn on garde le même principe d'en TP
- Pour les diamètres et centre, on fait un set de dijkstra (classe DijkstraSet), un par noeud, puis on calcule l'exentricité maximum et minimum
- Pour la clusterisation, on ajoute à dijkstra la possiblilité d'ajouter aux Edge, une valeur de beetwiness, puis on utilise la *clusterisation* à des beetwiness maximum décroissante. (Le but est que si on enlève dirrectement les edges à petites beetwiness, on ne prend pas en compte les beetwiness qui auraient pu se former à des échelles intermédiaires)
- Ladite fonction de *clusterisation* est une fonction récursive qui enlève les edges à trop grande beetwiness, sépare le graph en 2 si il n'est plus connecté, puis update la beetwiness afin de réitéter sur chacun des sous graphes.