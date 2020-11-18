package practice;

import java.util.Map;

public class client {
    public static void main(String[] args) {
        adjecencyMapGraph<String> graph=new adjecencyMapGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addEdge("A","B",1);
        graph.addEdge("A","C",2);
        graph.addEdge("B","D",4);
        graph.addEdge("D","C",3);
        graph.addEdge("C","E",5);
        graph.addEdge("E","D",6);
//        graph.display();
        System.out.println();

//        adjecencyMapGraph<String> graph1=graph.Kruskal();
//        graph1.display();
        System.out.println(graph.prims());
        Map map=graph.dijkstra("A");
        System.out.println(map);
    }
}
