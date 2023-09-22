package rmi.server.models;

public class Appointment {
    private String type;
    private String date;

    public Appointment(String type, String date){
        this.type = type;
        this.date = date;
    }

    public String getType(){return type;}
    public void setType(String type){this.type = type;}

    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}  
}
