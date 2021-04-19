package it.polimi.ingsw.net.server;

public class InvalidResponseException extends Exception{
    private String message;

    public InvalidResponseException(String message){
        this.message = message;
    }

    public String getErrorMessage(){
        return message;
    }
}
