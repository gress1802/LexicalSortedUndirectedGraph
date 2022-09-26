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

        //This takes care of all cases where the vertices is v1 or v2
        if(v1.equals(vertices.vertexName)){ //if v1 is the vertex then we need to *add v2 to vertexForString1.edges[0]* and *add v1 to vertexForString2.edges[1]*
            insertSortedEdge(vertexForString1,vertexForString2,v1,v2); //inserts into edges[0] list
        }else if(v2.equals(vertices.vertexName)){ //if v2 is the vertex then we need to *add v1 to vertexForString2.edges[0]* and then *add v2 to vertexForString1.edges[1]*
            insertSortedEdge(vertexForString2, vertexForString1, v2, v1); //inserts into edges[0] list
        }else{//Every other case for B,C,D,E,F
           if(v1.compareToIgnoreCase(v2) < 0){ // this is the normal case where v1 is less than v2
                if(vertexForString1.edges[0] == null || v2.compareToIgnoreCase(vertexForString1.edges[0].edge[0].vertexName) < 0){
                    EdgeNode tempE = vertexForString1.edges[0];
                    vertexForString1.edges[0] = new EdgeNode(vertexForString2,vertexForString1); // creating new edges[0] node
                    vertexForString1.edges[0].nextE[0] = tempE; //adding back on the rest of the list 
                    //now how edges[1] is updated
                    insertEdge1(vertexForString1.edges[0], vertexForString1, vertexForString2, v1, v2);
                    return;
                }else{
                    EdgeNode tempE = vertexForString1.edges[0];
                    while(tempE.nextE[0] != null){
                        if(v1.compareToIgnoreCase(tempE.nextE[0].edge[0].vertexName) < 0){
                            EdgeNode tempp = tempE.nextE[0];
                            tempE.nextE[0] = new EdgeNode(vertexForString2,vertexForString1);
                            tempE.nextE[0].nextE[0] = tempp;
                            //now how edges[1] is updated
                            insertEdge1(tempE.nextE[0], vertexForString1, vertexForString2, v1, v2);
                            return;
                        }
                    }
                    tempE.nextE[0] = new EdgeNode(vertexForString2,vertexForString1);
                    //edge[1] is updated
                    insertEdge1(tempE.nextE[0], vertexForString1, vertexForString2, v1, v2);
                }
           }else{// this is when v2 is less than v1
                if(vertexForString2.edges[0] == null || v2.compareToIgnoreCase(vertexForString2.edges[0].edge[0].vertexName) < 0){
                    EdgeNode tempE = vertexForString2.edges[0];
                    vertexForString2.edges[0] = new EdgeNode(vertexForString1,vertexForString2); // creating new edges[0] node
                    vertexForString2.edges[0].nextE[0] = tempE; //adding back on the rest of the list 
                    //now how edges[1] is updated
                    insertEdge1(vertexForString2.edges[0], vertexForString2, vertexForString1, v2, v1);
                    return;
                }else{
                    EdgeNode tempE = vertexForString2.edges[0];
                    while(tempE.nextE[0] != null){
                        if(v2.compareToIgnoreCase(tempE.nextE[0].edge[0].vertexName) < 0){
                            EdgeNode tempp = tempE.nextE[0];
                            tempE.nextE[0] = new EdgeNode(vertexForString1,vertexForString2);
                            tempE.nextE[0].nextE[0] = tempp;
                            //now how edges[1] is updated
                            insertEdge1(tempE.nextE[0], vertexForString2, vertexForString1, v2, v1);
                            return;
                        }
                    }
                    tempE.nextE[0] = new EdgeNode(vertexForString1,vertexForString2);
                    //edge[1] is updated
                    insertEdge1(tempE.nextE[0], vertexForString2, vertexForString1, v2, v1);
                }
 
           }
        }

        
        
        



        //PRE: v1 and v2 are legitimate vertex names
        //(i.e. vertices with names v1 and v2 exist in the vertex list)
        //Assume the edge has not been added
    }

    //This method inserts an edgenode in the sorted list edges[0]. its parameters are the vertex and the 
    public void insertSortedEdge(VertexNode vertexForString1, VertexNode vertexForString2, String v1, String v2){ 
        //first adding v2 to vertexForString1.edges[0] list
        EdgeNode temp1 = vertexForString1.edges[0];
        if(temp1 == null || v2.compareToIgnoreCase(temp1.edge[0].vertexName)<0){ //This is the case where the head (edges[0]) needs to be changed
            EdgeNode tempNext = vertexForString1.edges[0];
            vertexForString1.edges[0] = new EdgeNode(vertexForString2,vertexForString1);
            vertexForString1.edges[0].nextE[0] = tempNext;
            insertEdge1(vertexForString1.edges[0], vertexForString1, vertexForString2, v1, v2); 
            return;
        }else{
            while(temp1.nextE[0] != null){ //Otherwise traverse the list and look for the correct place for this edgenode
                if(v2.compareToIgnoreCase(temp1.nextE[0].edge[0].vertexName)<0){
                    EdgeNode tempNext = temp1.nextE[0];
                    temp1.nextE[0] = new EdgeNode(vertexForString2,vertexForString1);
                    temp1.nextE[0].nextE[0] = tempNext;
                    insertEdge1(temp1.nextE[0], vertexForString1, vertexForString2, v1, v2); 
                    return;
                }
                temp1 = temp1.nextE[0];
            }//last case is if we are at the end of the list
            temp1.nextE[0] = new EdgeNode(vertexForString2,vertexForString1);
            insertEdge1(temp1.nextE[0], vertexForString1, vertexForString2, v1, v2);
        } 
        //now inserting into edges[1] list

    }


    //This method inserts edges[1] list. its parameter node is the edgenode that has already been added to the list
    public void insertEdge1(EdgeNode node, VertexNode vertexForString1, VertexNode vertexForString2, String v1, String v2){ //This method adds edgenodes to edges[1] list
        EdgeNode temp1 = vertexForString2.edges[1];
        if(temp1 == null || v1.compareToIgnoreCase(temp1.edge[1].vertexName)<0){
            EdgeNode tempEdge = vertexForString2.edges[1];
            vertexForString2.edges[1] = node;
            node.nextE[1] = tempEdge;
            return;
        }else{
            while(temp1.nextE[1] != null){ //Otherwise traverse the list and look for the correct place for this edgenode
                if(v1.compareToIgnoreCase(temp1.nextE[1].edge[1].vertexName) < 0){
                    EdgeNode tempNext = temp1.nextE[1];
                    temp1.nextE[1] = node;
                    node.nextE[1] = tempNext;
                    return;
                }
                temp1 = temp1.nextE[1];
            }
            //This is the case where we have reached the end of the list
            temp1.nextE[1] = node;
        }
    }

/*
 *  This is a method that simply prints the vertices
 */
    public void printVertices(){
        String s = vertices.vertexName;
        VertexNode temp = vertices;
        while(temp.nextV!=null){
            s = s+" "+temp.nextV.vertexName;
            temp = temp.nextV;
        }
        System.out.println(s);
    }

    public void printGraph() {
       VertexNode temp = vertices;
       EdgeNode tempE0;
       EdgeNode tempE1;

        EdgeNode tempFirst = vertices.edges[0];
        System.out.print(vertices.vertexName); //printing the vertices
        if(tempFirst == null){

        }else{
            System.out.print(" "+tempFirst.edge[0].vertexName);//printing the first edgenode in the list [0]
            while(tempFirst.nextE[0] != null){//prints the edges[0] of the first vertex (This vertex will have no edges[1])
                System.out.print(" "+tempFirst.nextE[0].edge[0].vertexName);
                tempFirst = tempFirst.nextE[0];
            }
        }

        System.out.println();
        System.out.println();


        while(temp.nextV != null){//traverses through the vertices
            if(temp.nextV.edges[0] == null){
                System.out.println(temp.nextV.vertexName);
                tempE1 = temp.nextV.edges[1];
            }else{
                System.out.print(temp.nextV.vertexName);//printing the edges[0] list
                tempE0 = temp.nextV.edges[0];
                tempE1 = temp.nextV.edges[1];

                if(tempE0 == null){
                    System.out.println();
                }else{
                    System.out.print(" "+tempE0.edge[0].vertexName+" ");
                    while(tempE0.nextE[0] != null){
                        System.out.print(tempE0.nextE[0].edge[0].vertexName+" ");
                        tempE0 = tempE0.nextE[0];
                    }//printing edges[1] list
                    System.out.println();
                }
            }
            //
            tempE1 = temp.nextV.edges[1];
            if(tempE1 != null && tempE1.nextE[1] == null){
                System.out.print("  "+tempE1.edge[1].vertexName);
            }else{
                if(tempE1 != null){
                    System.out.print("  "+tempE1.edge[1].vertexName);
                }
                while(tempE1 != null && tempE1.nextE[1] != null){
                    System.out.print(" "+tempE1.nextE[1].edge[1].vertexName);
                    tempE1 = tempE1.nextE[1];
                }
            }
            System.out.println();
            //
            System.out.println();
            temp = temp.nextV;
        }


    }



    public static void main(String args[]) throws IOException { 
        //DO NOT CHANGE main 
        //This code assumes the input file is syntactically correct 

/*         UndirectedGraph g = new UndirectedGraph();
        g.addVertex("E");
        g.addVertex("D");
        g.addVertex("F");
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        g.addEdge("F", "A");
        g.addEdge("F", "B");
        g.addEdge("A", "B");
        g.addEdge("F", "E");
        g.addEdge("C", "A");
        g.addEdge("B", "D");        
        g.addEdge("D","F");
        g.addEdge("D", "C");
        g.addEdge("C", "E");
        g.addEdge("D", "E");
        g.addEdge("A", "D");
        g.printGraph(); */
        

        

         
        UndirectedGraph g = new UndirectedGraph(); 
        BufferedReader b = new BufferedReader(new FileReader(new File("./TestDrive.Txt"))); 
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
        
    } 

}
