# ProjetAlgo
###Processus d'aquisition/stockage de donnés:
- Les fichier sont découpés et suivant qu'ils sont en stop.txt ou stop_time.txt ils sont pré analysés par stopHandler (description des arrets) ou stopTimeHandler (regroupement des n chemins différents d'une ligne)
- Chaque cluster est converti en autant de graph (GraphTools.Graph.graphFromSmallListAndStopContent)
- Tous ces mini graph sont mergés, de même que toutes les lignes de métro peuvent être mergées.
- Puis on utilise gson un outil de google servant à convertir ou extraire un objet en json. On écrit cet objet dans data.json. Bien sur, pour éviter de passer en json des objets qui s'appelent cycliqument, on ne renseigne que les identifiants des noeuds dans les edges (d'où le "transient protected GraphTools.Node from")
- On peut donc charger data.json pour retrouver rapidement le graph, sans passer par les longues étapes de lecture qui précèdent (une dizaine de secondes). Il faut cependans consolider le résultat car les objets qui pointanent vers les memes références ne le font plus (du nottament au transcient nomé plus haut)
 
###Les algorithmes :
