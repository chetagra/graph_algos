package practice;

import java.util.*;

public class adjecencyMapGraph<T> {
    Map<T,Vertex> vertexMap;
    public adjecencyMapGraph(){
        this.vertexMap=new HashMap<>();
    }

    public void addVertex(T val){
        vertexMap.put(val,new Vertex(val));
    }

    public void addEdge(T first, T second, Integer w){
        if (vertexMap.containsKey(first) && vertexMap.containsKey(second)){
            vertexMap.get(first).neighbours.put(vertexMap.get(second),w);
            vertexMap.get(second).neighbours.put(vertexMap.get(first),w);
        }
    }

    public void display(){
        for (Vertex vertex:vertexMap.values()) {
            System.out.print(vertex.val+" => ");
            for (Vertex padosi:vertex.neighbours.keySet()) {
                System.out.print(padosi.val+" ");
            }
            System.out.println();
        }
    }


    Map<Vertex,Vertex> parent=new HashMap<>();
    public adjecencyMapGraph<T> Kruskal(){

        adjecencyMapGraph<T> Kgraph=new adjecencyMapGraph<>();
        setParent();
        for (T val:vertexMap.keySet()) {
            Kgraph.addVertex(val);
        }
        ArrayList<Edge> edgeList=new ArrayList<>();
        for (Vertex v:vertexMap.values()) {
            for (Vertex padosi:v.neighbours.keySet()) {
                edgeList.add(new Edge(v,padosi,v.neighbours.get(padosi)));
            }
        }
        Collections.sort(edgeList);

        for (Edge e:edgeList) {
            if (union(e.first,e.second)){
              Kgraph.addEdge(e.first.val,e.second.val,e.weight);
            }
        }
        return Kgraph;
    }

    private boolean union(Vertex first,Vertex second) {
        Vertex f= find(first);
        Vertex s=find(second);
        if (f!=s){
            parent.put(f,s);
            return true;
        }
        return false;
    }

    private Vertex find(Vertex vertex) {
        while (parent.get(vertex)!=null){
            vertex=parent.get(vertex);
        }
        return vertex;
    }

    private void setParent() {
        for (Vertex vertex:vertexMap.values()) {
            parent.put(vertex,null);
        }
    }

    class Edge implements Comparable<Edge>{
        Vertex first;
        Vertex second;
        Integer weight;

        public Edge(Vertex first, Vertex second, Integer weight) {
            this.first= first;
            this.second=second;
            this.weight=weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight-o.weight;
        }
    }


    public int prims() {

        Queue<Edge> queue = new PriorityQueue<>();
        Set<Vertex> visited = new HashSet<>();

        Vertex v=vertexMap.values().iterator().next();

            for (Vertex padosi : v.neighbours.keySet()) {
                queue.add(new Edge(v, padosi, v.neighbours.get(padosi)));
            }
            visited.add(v);

        int wt=0;

        while (!queue.isEmpty()) {
            Edge e = queue.remove();
            if (visited.contains(e.second)){
                continue;
            }
            visited.add(e.second);
            wt += e.weight;

            for (Vertex padosi:e.second.neighbours.keySet()) {
               if (!visited.contains(padosi)){
                   queue.add(new Edge(e.second,padosi,e.second.neighbours.get(padosi)));
               }
            }
        }

        return wt;
    }


    public Map dijkstra(T src){
        Map<Vertex,Integer> result=new HashMap<>();
        Set<Vertex>Visted =new HashSet<>();
        Queue<djPair> queue=new PriorityQueue<>();
        Vertex start=vertexMap.get(src);
        Visted.add(start);
        for (Vertex vertex:start.neighbours.keySet()) {
            queue.add(new djPair(vertex,start.neighbours.get(vertex)));
        }
        while (!queue.isEmpty()){
            djPair pair=queue.remove();
            Vertex vertex=pair.vertex;
            Integer distance=pair.distance;
            if (Visted.contains(vertex)){
                continue;
            }
            Visted.add(vertex);
            result.put(vertex,distance);
            for (Vertex padosi :vertex.neighbours.keySet()) {
                if (!Visted.contains(padosi)){
                    int dist=distance+vertex.neighbours.get(padosi);
                    boolean check=true;
                    for (djPair pair1:queue) {
                        if (pair1.vertex.equals(padosi)){
                            if (pair1.distance > dist){
                                queue.remove(pair1);
                                queue.add(new djPair(padosi,dist));
                                check=false;
                                break;
                            }
                        }
                    }
                    if (check){
                        queue.add(new djPair(padosi,dist));
                    }
                }
            }
        }
        return result;
    }

   private class djPair implements Comparable<djPair>{
        Vertex vertex;
        Integer distance;
        public djPair(Vertex val,Integer dist){
            this.vertex=val;
            this.distance=dist;
        }

        @Override
        public int compareTo(djPair o) {
            return this.distance-o.distance;
        }

   }


    class Vertex {
        T val;
        Map<Vertex,Integer> neighbours;
        public Vertex(T val){
            this.val=val;
            this.neighbours= new HashMap<>();
        }

    }
}
