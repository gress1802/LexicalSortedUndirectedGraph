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
        VertexNode temp = vertices; //node to traverse through lists
        VertexNode vertexForString1 = vertices; //this will be initialized to a different value 
        VertexNode vertexForString2 = vertices; //this will be initialized to a different value
        //traversing through the list of vertices to find which vertices we are adding edges too
        if(vertices.vertexName.equals(v1)){ //if the related vertex is vertices
            vertexForString1 = vertices; //this is the vertex for the first string
        }else{
            while(temp.nextV != null){ //if the vertex is not vertices
                if(temp.nextV.vertexName.equals(v1)){
                    vertexForString1 = temp.nextV;
                    break;
                }
                temp = temp.nextV;
            }
        }

        temp = vertices; //reset temp to vertices

        if(vertices.vertexName.equals(v2)){ //if the related vertex is vertices
            vertexForString2 = vertices; //this is the vertex for the second string
        }else{
            while(temp.nextV != null){ //if the vertex is not vertices
                if(temp.nextV.vertexName.equals(v2)){
                    vertexForString2 = temp.nextV;
                    break;
                }
                temp = temp.nextV;
            }
        }

        //if either of the Strings are the same as the vertices String then we have to first add an edge to the vertices node in the correct order
        //after that we need to update the second edge list
        if(v1.equals(vertices.vertexName)){
            vertexForString1.edges[0] = new EdgeNode(vertexForString2, vertexForString1);
        }else if(v2.equals(vertices.vertexName)){
            vertexForString2.edges[0] = new EdgeNode(vertexForString1, vertexForString2);
        }
        



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
       VertexNode temp = vertices;
       EdgeNode tempE0 = temp.edges[0];
       EdgeNode tempE1 = temp.edges[1];
       System.out.println(temp.edges[0].edge[0].vertexName);
 /*        while(temp.nextV != null){//traversing through the list of vertices
            tempE0 = temp.edges[0];
            tempE1 = temp.edges[1];
            while(tempE0.nextE != null){//traversing through the edges[0] list

                tempE0 = tempE0.nextE[0];
            }
       }*/
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

        g.addEdge("A", "B");
        g.addEdge("C","A");

        g.printGraph();


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
