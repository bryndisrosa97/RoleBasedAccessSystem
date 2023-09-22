package rmi.server.models;

import rmi.server.utils.AESEncryption;

import java.io.Serializable;

import java.util.ArrayList;


public class User implements Serializable {
    private static final long serialVersionUID = 1012379424897234L;

    private final String username;
    private final byte[] encryptedPassword;
    private final byte[] salt;
    private String[] permissions;
    private String[] roles;
    private int CPR;
    private ArrayList<Appointment> appointments; //maybe have a appointments object
    private ArrayList<Vaccination> vaccinations;
    private ArrayList<Tests> tests;

    public User(int CPR,Credentials credentials) {
        this.CPR= CPR;
        AESEncryption aesEncryption = new AESEncryption();
        this.username = credentials.getUsername();
        this.salt = aesEncryption.getSalt();
        this.encryptedPassword = aesEncryption.encrypt(credentials.getPassword(), this.salt);
        this.appointments = new ArrayList<Appointment>();
        this.vaccinations = new ArrayList<Vaccination>();
        this.tests = new ArrayList<Tests>();
    }

    public String getUsername(){ return username; }

    public byte[] getEncryptedPassword(){ return encryptedPassword;}

    public byte[] getSalt(){ return salt;}

    public void setPermissions(String[] permissions){ this.permissions = permissions;}

    public String[] getPermissions(){return permissions;}

    public void setRole(String[] roles){this.roles = roles;}

    public String[] getRole(){ return roles;}
    
    public int getCPR(){return this.CPR;}
    
    public void setAppointment(Appointment appointment){
        if(this.appointments != null){
            this.appointments.add(appointment);
        }
        else{
            this.appointments = new ArrayList<Appointment>();
            this.appointments.add(appointment);
        }
    }

    public ArrayList<Appointment> getAppointment(){return (appointments);}

    public ArrayList<Vaccination> getVaccination(){return vaccinations;}

    //public Vaccination setVaccination(Vaccination vaccination){this.vaccinations.add(vaccination);return vaccination;} //af hverju

    public void setVaccination(Vaccination vaccination){
        if(this.vaccinations != null){
            this.vaccinations.add(vaccination);
        }
        else{
            this.vaccinations = new ArrayList<Vaccination>();
            this.vaccinations.add(vaccination);
        }
    }

    public ArrayList<Tests> getTests(){return tests;} 

    public void setTests(Tests test){
        if(this.tests != null){
            this.tests.add(test);
        }
        else{
            this.tests = new ArrayList<Tests>();
            this.tests.add(test);
        }
    } 
 
    public Tests setVaccination(Tests tests){this.tests.add(tests);return tests;} 
}
