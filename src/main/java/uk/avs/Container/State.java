package uk.avs.Container;

import Message.abstractions.BinaryMessage;

public class State implements BinaryMessage {
    public State(String FileName){

    };
    public State(boolean request, boolean wait, boolean approve, boolean decline){
        this.request = request;
        this.decline = decline;
        this.approve =  approve;
        this.wait = wait;

    };
    public boolean wait;
    public boolean approve;
    public boolean decline;
    public boolean request;
    public void save(){

    };


}
