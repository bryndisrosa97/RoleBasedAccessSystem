package rmi.server.models;

public class Tests {
    private int testID;
    private String Typeoftest;
    private boolean result;
    private String date;


    public Tests(int testID, String Typeoftest, String date)
    {
        this.testID = testID;
        this.Typeoftest = Typeoftest;
        this.date = date;
    }

    public int getTestID() {
        return testID;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getTypeofTest() {
        return this.Typeoftest;
    }

    public String getDate() {
        return this.date;
    }

    public String toString()
    {
        return "Tested with " + this.Typeoftest + " at: " +this.date + "  result: " + this.result;
    }

    
}
