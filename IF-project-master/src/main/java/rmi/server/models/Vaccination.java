package rmi.server.models;


public class Vaccination  {

    private String type;
    private int numberOfShots;
    //private boolean vaccinated=false;
    private String date;


    public Vaccination(String type, int numberOfShots, String date)
    {
        this.type = type;
        this.numberOfShots = numberOfShots;
        this.date = date;
        //this.vaccinated=true;
        //this.numberOfShots = 1;
    }

    public String getType() {
        return this.type;
    }

    void setType(String type) {
        this.type = type;
    }

    public int getNumberOfShots() {
        return numberOfShots;
    }

    public void setNumberOfShots(int numberOfShots) {
        this.numberOfShots = numberOfShots;
    }

    //public boolean isVaccinated() {
    //    return vaccinated;
    //}

    /**public void setNumberOfShots() {
        this.numberOfShots +=1;
    }**/

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString()
    {
        return "Vaccinated with " + this.type + " at: " +this.date + "  number of shots: " + this.numberOfShots;
    }
    
}
