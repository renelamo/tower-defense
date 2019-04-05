package com.towerint.Model;

public class Node {
    private Vector2 position;
    private Node next;

    public Node(Vector2 v){
        position=v;
        next =null;
    }

    public Node(int x, int y){
        position=new Vector2(x,y);
        next =null;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void setNext(Node n){
        next =n;
    }

    public boolean hasNext(){
        return next !=null;
    }

    public Node getNext(){
        return next;
    }

    public Vector2 getDirection(){
        return this.next.getPosition().diff(this.position);
    }
}
