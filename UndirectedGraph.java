/*
 *  Programming Project 2
 *  Joseph Gress
 */

import java.util.*;
import java.io.*;

public class UndirectedGraph {

    private class VertexNode{ 
        private String vertexName; 
        private EdgeNode edges[]; //heads of the two edge lists
        private VertexNode nextV; //next vertex in the vertex list
        private VertexNode(String v, VertexNode n) { 
            vertexName = new String(v); 
            edges = new EdgeNode[2]; 
            nextV = n; 
        }
    }

    private class EdgeNode { 
        private VertexNode edge[]; //the two vertices that make the edge
        private EdgeNode nextE[]; //the next edge node for each edge list
        private EdgeNode(VertexNode v1, VertexNode v2) { 
        //PRE: v1.vertexName < v2.vertexName
        edge = new VertexNode[2]; 
        edge[0] = v1; 
        edge[1] = v2; 
        nextE = new EdgeNode[2]; 
        } 
    }

    private VertexNode vertices; //head of the list of vertices

    public UndirectedGraph() { 
        vertices = null; //no sentinel node 
    } 
 
    public boolean addVertex(String s) { 
        //add a new vertex with name s
        //return true if a vertex is added
        //return false if a vertex with name s already exists
        //the vertex list is kept in ascending sorted order based on the name
        VertexNode temp = vertices;
        if(vertices == null || vertices.vertexName.compareToIgnoreCase(s) > 0){ //Special case for head
            if(vertices != null && vertices.vertexName.equals(s)) return false; //checking for duplicates
            vertices = new VertexNode(s,vertices);
            return true;
        }else{
            if(vertices.vertexName.equals(s)) return false; //checking for duplicates
            while(temp.nextV != null && temp.nextV.vertexName.compareToIgnoreCase(s)<0){
                temp = temp.nextV;
            }
            if(temp.nextV != null && temp.nextV.vertexName.equals(s)) return false; //checking for duplicates
            if(temp.nextV == null && temp.vertexName.compareToIgnoreCase(s) < 0){
                temp.nextV = new VertexNode(s,null);
                return true;
            }
            temp.nextV = new VertexNode(s, temp.nextV);
            return true;

        }



    }

    public void addEdge(String v1, String v2) { 
        //PRE: v1 and v2 are legitimate vertex names
        //(i.e. vertices with names v1 and v2 exist in the vertex list)
        //Assume the edge has not been added
    }

    public void printVertices(){
        String s = vertices.vertexName;
        VertexNode temp = vertices;
        while(temp.nextV!=null){
            s = s+", "+temp.nextV.vertexName;
            temp = temp.nextV;
        }
        System.out.println(s);
    }

    public void printGraph() {
        
    }

    public static void main(String args[]) throws IOException { 
        //DO NOT CHANGE main 
        //This code assumes the input file is syntactically correct 

        UndirectedGraph g = new UndirectedGraph();
        g.addVertex("E");
        g.addVertex("D");
        g.addVertex("F");
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.printVertices();


        /* 
        UndirectedGraph g = new UndirectedGraph(); 
        BufferedReader b = new BufferedReader(new FileReader(args[0])); 
        String line = b.readLine(); 
        Scanner scan = new Scanner(line); 
        while (scan.hasNext()) { 
            g.addVertex(scan.next()); 
        } 
        line = b.readLine(); 
        while (line != null) { 
            scan = new Scanner(line); 
            g.addEdge(scan.next(), scan.next()); 
            line = b.readLine(); 
        } 
        g.printGraph();
        b.close(); //close BufferedReader b
        scan.close(); //close Scanner scan
        */
    } 

}
