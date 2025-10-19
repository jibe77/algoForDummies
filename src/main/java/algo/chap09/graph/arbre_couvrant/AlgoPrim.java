package algo.chap09.graph.arbre_couvrant;

/**
 * Algorithme de Prim pour trouver l’arbre couvrant de poids minimum (MST)
 * dans un graphe pondéré et non orienté.
 * 
 * Complexité : O(V^2) où V est le nombre de sommets dans le graphe.
 * 
 * L'algorithme de Prim construit le MST en partant d'un sommet initial
 * et en ajoutant progressivement les arêtes de poids minimum qui
 * connectent les sommets déjà inclus dans le MST aux sommets
 * non inclus, jusqu'à ce que tous les sommets soient inclus.
 * 
 * Prim construit l’arbre couvrant de poids minimal (MST) à partir du sommet 0.
    À chaque étape :
    Il choisit le sommet non encore inclus ayant la plus petite clé (distance minimale vers le MST).
    Il ajoute l’arête correspondante.
    Il met à jour les clés des sommets voisins.
    Ce processus continue jusqu’à ce que tous les sommets soient inclus.
 */
public class AlgoPrim {

    // A utility function to find the vertex with minimum
    // key value, from the set of vertices not yet included
    // in MST
    int minKey(int key[], Boolean mstSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < mstSet.length; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    // A utility function to print the constructed MST
    // stored in parent[]
    String printMST(int parent[], int graph[][])
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Edge \tWeight");
        for (int i = 1; i < graph.length; i++)
            sb.append(parent[i] + " - " + i + "\t"
                               + graph[parent[i]][i]);
        return sb.toString();
    }

    // Function to construct and print MST for a graph
    // represented using adjacency matrix representation
    String primMST(int graph[][])
    {
        int V = graph.length;
        
        // Array to store constructed MST
        int parent[] = new int[V];

        // Key values used to pick minimum weight edge in
        // cut
        int key[] = new int[V];

        // To represent set of vertices included in MST
        Boolean mstSet[] = new Boolean[V];

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Always include first 1st vertex in MST.
        // Make key 0 so that this vertex is
        // picked as first vertex
        key[0] = 0;
      
        // First node is always root of MST
        parent[0] = -1;

        // The MST will have V vertices
        for (int count = 0; count < V - 1; count++) {
            
            // Pick the minimum key vertex from the set of
            // vertices not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of the
            // adjacent vertices of the picked vertex.
            // Consider only those vertices which are not
            // yet included in MST
            for (int v = 0; v < V; v++)

                // graph[u][v] is non zero only for adjacent
                // vertices of m mstSet[v] is false for
                // vertices not yet included in MST Update
                // the key only if graph[u][v] is smaller
                // than key[v]
                if (graph[u][v] != 0 && mstSet[v] == false
                    && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }

        // Print the constructed MST
        return printMST(parent, graph);
    }
}