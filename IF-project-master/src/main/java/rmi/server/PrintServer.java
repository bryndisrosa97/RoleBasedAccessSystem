package rmi.server;

import rmi.IHospital;
import rmi.server.models.Credentials;
import rmi.server.models.Tests;
import rmi.server.models.Vaccination;
import rmi.server.models.Appointment;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import static rmi.server.AuthManager.authenticate;
import static rmi.server.AccessManager.readEmployeesRoles;
import static rmi.server.AccessManager.readUserInfo;
import static rmi.server.AccessManager.readUserAccess;

import static rmi.server.AccessManager.hasAccess;


public class PrintServer extends UnicastRemoteObject implements IHospital {
    private static final int PORT = 1300;
    private static final long serialVersionUID = 1126235328952234L;

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(PORT);
            Naming.rebind("//localhost:" + PORT + "/PrintServer", new PrintServer());
            System.err.println("PrintServer bound to registry on port " + PORT);
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
        readEmployeesRoles();
    }

    protected PrintServer() throws RemoteException {
        super(PORT);
        AuthManager.initTestUsersTVaccinationResults();
        AuthManager.initTestUsersTestResults();
    }

    @Override
    public boolean auth(Credentials credentials) throws RemoteException {
        System.out.println("--- auth() for username " + credentials.getUsername() + " CALLED ---"+ "   passss "  );
        try {
            authenticate(credentials);
            return true;
        } catch (SecurityException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean access(Credentials credentials, String operation) {
        authenticate(credentials);
        System.out.println("--- Check if user " + credentials.getUsername() + " has access to operation: " + operation + "---");
        return hasAccess(credentials,operation);
    }

    @Override
    public void orderPatientTest(Credentials credentials, int testID, String typeOfTest, String dateofTest, int PatientCPR) throws RemoteException {
        authenticate(credentials);
        Tests test1 = new Tests(testID, typeOfTest, dateofTest); //{H:H}
        AuthManager.addTest(PatientCPR, test1); //{H:H}
    }

    @Override
    public void setPatientTestResult(Credentials credentials, int testID, int PatientCPR, boolean patientResults ) throws RemoteException {
        authenticate(credentials);
        AuthManager.setPatientTestsResult(PatientCPR, testID, patientResults); //{H:H}
    }

    @Override
    public ArrayList<String> getPatientTestrecords(Credentials credentials, int PatientCPR) throws RemoteException {
        authenticate(credentials);
        ArrayList<Tests> testList = AuthManager.getUserTests(PatientCPR); //{H:H}
        ArrayList<String> testDetailsArray = new ArrayList<String>();   //{H:H}
        if(testList != null){
            int sizes = testList.size();    //{H:H}
            for (int i = 0; i < sizes; i++) {   //i: {H:H}
                String type = testList.get(i).getTypeofTest();  //{H:H}
                String date = testList.get(i).getDate();        //{H:H}
                boolean results = testList.get(i).getResult();  //{H:H}
                String testDetails = "Tested with " + type + " at: " + date + " results are: " + results;   //{H:H}
                testDetailsArray.add(testDetails);  //{H:H}
            }
        }
        return testDetailsArray;
    } //{H:H}

    @Override
    public int[] getStaticalInformation() throws RemoteException {
        int numbers[] = {AuthManager.getInfectionsLastDay(), AuthManager.getNumberOfVaccinations()};    //declassify {H:H} -> {⊥}
        return numbers; //{⊥}
    } //{⊥}
    
    @Override
    public ArrayList<String> getPatientVaccination(Credentials credentials, int PatientCPR) throws RemoteException {
        authenticate(credentials);
        ArrayList<Vaccination> vaccination = AuthManager.getUserVaccination(PatientCPR); //{H:H}
        ArrayList<String> vaccinationDetailsArray = new ArrayList<String>(); //{H:H}
        if(vaccination != null){
            int sizes = vaccination.size(); //{H:H}
            for (int i = 0; i < sizes; i++) { //i: {H:H}
                String type = vaccination.get(i).getType(); //{H:H}
                String date = vaccination.get(i).getDate(); //{H:H}
                int numberOfShots = vaccination.get(i).getNumberOfShots(); //{H:H}
                String vaccinationDetails = "Vaccinated with " + type + " at: " + date + " number of shots:"
                        + numberOfShots; //{H:H}
                vaccinationDetailsArray.add(vaccinationDetails); //{H:H}
            }
        }
        return vaccinationDetailsArray; //{H:H}
    } //{H:H}

    @Override
    public String[] printInformationofSigninUser(Credentials credentials) throws RemoteException {
        String[] role = readUserInfo(credentials);
        return role;
    }

    @Override
    public String[] printOperationsOfSigninUser(Credentials credentials) throws RemoteException {
        String[] access = readUserAccess(credentials);
        return access;
    }

    @Override
    public int getPatientCprFromName(Credentials credentials) throws RemoteException {
        int cprnumber = AuthManager.getPatientCprFromName(credentials); //{H:H}
        return cprnumber; //{H:H}
    }

    @Override
    public void setPatientVaccination(Credentials credentials, String VaccinationName, String VaccinationDate, int CPR) throws RemoteException {
        authenticate(credentials);
        Vaccination test = new Vaccination(VaccinationName, 1,VaccinationDate); //{H:H}
        AuthManager.addVaccination(CPR, test); //{H:H}
    }

    @Override
    public void setPatientAppointments(Credentials credentials, int PatientCPR, String appointmentInfo, String appointmentDate) throws RemoteException {
        authenticate(credentials);
        Appointment appointment = new Appointment(appointmentInfo, appointmentDate); //{H:H}
        AuthManager.addAppointment(PatientCPR, appointment); //{H:H}
    }

    @Override
    public ArrayList<String> getPatientAppointments(Credentials credentials, int PatientCPR) throws RemoteException {
        authenticate(credentials);
        ArrayList<Appointment> appointments= AuthManager.getAppointments(PatientCPR); //{H:H}
        ArrayList<String> appointmentDetailsArray = new ArrayList<String>(); //{H:H}
        if(appointments != null){
            int sizes = appointments.size(); //{H:H}
            for (int i = 0; i < sizes; i++) { //i: {H:H}
                String type = appointments.get(i).getType(); //{H:H}
                String date = appointments.get(i).getDate(); //{H:H}
                String appointmentDetails = "Appointment for " + type + " at: " + date; //{H:H}
                appointmentDetailsArray.add(appointmentDetails);  //{H:H}
            }
        }
        return appointmentDetailsArray; //{H:H}
    }
}
