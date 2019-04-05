package com.towerint.Model;

import java.util.Iterator;
import java.util.LinkedList;

public class Way {
    private LinkedList<Node> path;

    public Way(){
        path=new LinkedList<>();
    }

    public Way(Node n){
        path=new LinkedList<>();
        path.add(n);
    }

    public void add(Node n){
        path.getLast().setNext(n);
        path.addLast(n);
    }

    public void add(int x, int y){
        add(new Node(x,y));
    }

    public Node getFirstNode(){
        return path.getFirst();
    }

    @Override
    public String toString() {
        String out="";
        for(Node n:path){
            out+=n.toString()+'\n';
        }
        return out;
    }
}
