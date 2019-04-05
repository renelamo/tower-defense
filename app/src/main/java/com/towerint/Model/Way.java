package com.towerint.Model;

import java.util.LinkedList;

public class Way {
    private LinkedList<Node> path;

    public Way(){
        path=new LinkedList<>();
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
}
