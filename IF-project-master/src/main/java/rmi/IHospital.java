package rmi;

import rmi.server.models.Credentials;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IHospital extends Remote {
    boolean auth(Credentials credentials) throws RemoteException;
    boolean access(Credentials credentials, String operation) throws RemoteException;
    void orderPatientTest(Credentials credentials, int testID, String typeOfTest, String dateofTest, int PatientCPR) throws RemoteException;
    void setPatientTestResult(Credentials credentials, int testID, int PatientCPR, boolean patientResults) throws RemoteException;
    ArrayList<String> getPatientTestrecords(Credentials credentials, int PatientCPR) throws RemoteException;
    int[] getStaticalInformation() throws RemoteException;
    ArrayList<String> getPatientVaccination(Credentials credentials, int PatientCPR) throws RemoteException;
    int getPatientCprFromName(Credentials credentials) throws RemoteException;
    void setPatientVaccination(Credentials credentials, String VaccinationName, String VaccinationDate, int CPR) throws RemoteException;
    void setPatientAppointments(Credentials credentials, int PatientCPR, String appointmentInfo, String appointmentDate) throws RemoteException;
    ArrayList<String> getPatientAppointments(Credentials credentials, int PatientCPR) throws RemoteException;
    String[] printInformationofSigninUser(Credentials userCredentials) throws RemoteException;
    String[] printOperationsOfSigninUser(Credentials userCredentials) throws RemoteException;
}


