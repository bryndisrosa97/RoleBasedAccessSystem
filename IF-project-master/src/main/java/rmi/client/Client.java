package rmi.client;

import rmi.IHospital;
import rmi.server.models.Credentials;
import rmi.server.models.User;
import rmi.server.models.Vaccination;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private static final int PORT = 1300;
    private static final String HOST = "//localhost:" + PORT + "/PrintServer";
    static Map<User, ArrayList<Vaccination>> DatabaseVaccination = new HashMap<>();

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        IHospital printServer = (IHospital) Naming.lookup(HOST);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to this print client!");

        Credentials userCredentials = null;
        boolean isLoggedIn = false;
        boolean active = true;
        while (active) {
	        System.out.println(
	                "\nOperations available:\n 1: Statistical information, 2: Login, 3: Quit");
	        int operation1 = scanner.nextInt();
	        switch (operation1) {
	            case 1: 
                    getAnalysis(printServer); // {⊥}
	            	break;
	            case 2:
	            	while (!isLoggedIn) {
	                    System.out.println("Enter username");
	                    String username = scanner.next();
	                    System.out.println("Enter password");
	                    String password = scanner.next();
	                    if (!username.isEmpty() && !password.isEmpty()) {
	                        Credentials enteredCredentials = new Credentials(username, password);
	                        isLoggedIn = printServer.auth(enteredCredentials);
	                        if (!isLoggedIn) {
	                            System.err.println("Invalid credentials. Try again.\n");
	                        } else {
	                            userCredentials = enteredCredentials;
	                            String[] roleOfSignedinUser = printServer.printInformationofSigninUser(userCredentials);
	                            String[] availableOperationsOfSignedinUser = printServer
	                                    .printOperationsOfSigninUser(userCredentials);
                                System.out.println(" ");
                                System.out.println("---------------------------------------");
	                            System.out.println("Welcome to the hospital server " + userCredentials.getUsername());
	                            System.out.print("You are logged in as a ");
	                            System.out.print(Arrays.toString(roleOfSignedinUser)+"\n");
	                            //System.out.println(" ");
	                            System.out.println("---------------------------------------");
	                            System.out.println("These are the methods that you have access to ");
	                            System.out.println(Arrays.toString(availableOperationsOfSignedinUser));
	                        }
	                    }
	                }
	                boolean activeSession = true;
	
	                while (activeSession) {
	                    System.out.println("\nOperations available:\n 1: Statical Information \n 2: get Patient's Vaccination \n 3: order test for patient \n 4: Log in Patient's Vaccination \n 5: Log in Patient's Test Results \n 6: Get patient's test results \n 7: Get appointments \n 8: Order appointment \n 9: Quit");
	                    int operation = scanner.nextInt();
	                    switch (operation) { // Choose between accesses
	                        case 1:
                                getAnalysis(printServer); // {⊥} 
                            break;

	                        case 2:
	                            if (printServer.access(userCredentials, "getPatientVaccination")) { 
                                    System.out.print("Enter patient CPR: ");
                                    int cpr = scanner.nextInt(); //{H:H}
	                                getPatientVaccinationser(printServer, scanner, userCredentials, cpr); //{H:H}
	                                break;
	                            }
                                else if (printServer.access(userCredentials, "getMyVaccinations")) { 
                                    int cpr = printServer.getPatientCprFromName(userCredentials); //{H:H}
                                    getPatientVaccinationser(printServer, scanner, userCredentials, cpr); //{H:H}
                                    break;
                                }
	                            System.err.println("Client: Access denied");
	                            break;

	                        case 3:
	                            if (printServer.access(userCredentials, "orderPatientTest")) { 
	                                orderPatientTest(scanner, printServer, userCredentials); //{H:H}
	                                break;
	                            }
	                            System.err.println("Client: Access denied");
	                            break;

	                        case 4:
	                            if (printServer.access(userCredentials, "setPatientVaccination")) {
	                                setPatientVaccination(scanner, printServer, userCredentials); //{H:H}
	                                break;
	                            }
	                            System.err.println("Client: Access denied");
	                            break;

	                        case 5:
	                            if (printServer.access(userCredentials, "setPatientTestResult")) {
	                                setPatientTestResult(printServer, scanner, userCredentials); //{H:H}
	                                break;
	                            }
	                            System.err.println("Client: Access denied");
	                            break;

	                        case 6:
	                            if (printServer.access(userCredentials, "getPatientTestrecords")) {
                                    System.out.println("Patient CPR: ");
                                    int cpr = scanner.nextInt(); //{H:H}
	                                getPatientTestrecords(printServer, scanner, userCredentials, cpr); //{H:H}
	                                break;
	                            }
                                else if (printServer.access(userCredentials, "getMyTestrecords")) {
                                    int cpr = printServer.getPatientCprFromName(userCredentials); //{H:H}
                                    getPatientTestrecords(printServer, scanner, userCredentials, cpr); //{H:H}
	                                break;
                                }
	                            System.err.println("Client: Access denied");
	                            break;

	                        case 7:
	                            if (printServer.access(userCredentials, "getPatientAppointments")) {
                                    System.out.println("Patient CPR: ");
                                    int cpr = scanner.nextInt(); //{H:H}                            
	                                getPatientAppointments(scanner, printServer, userCredentials, cpr); //{H:H}
	                                break;
	                            }
                                else if (printServer.access(userCredentials, "getMyAppointments")) {
                                    int cpr = printServer.getPatientCprFromName(userCredentials); //{H:H}
                                    getPatientAppointments(scanner, printServer, userCredentials, cpr); //{H:H}
                                    break;
                                }
	                            System.err.println("Client: Access denied");
	                            break;

	                        case 8:
	                            if (printServer.access(userCredentials, "setPatientAppointments")) {
                                    System.out.println("Patient CPR: ");
                                    int cpr = scanner.nextInt(); //{H:H}                           
	                                setPatientAppointment(scanner, printServer, userCredentials, cpr); //{H:H}
	                                break;
	                            }
                                else if (printServer.access(userCredentials, "setMyAppointments")) {
                                    int cpr = printServer.getPatientCprFromName(userCredentials); //{H:H}
                                    setPatientAppointment(scanner, printServer, userCredentials, cpr); //{H:H}
	                                break;
                                }
	                            System.err.println("Client: Access denied");
	                            break;

	                        case 9:
	                            System.out.println("Quiting program...");
	                            activeSession = false;
	                            isLoggedIn = false;
	                            userCredentials = null;
	                            break;

	                        default: {
	                            System.err.println("Client: Invalid operation. Try again");
	                        }
	                    }
	                }
	                break;

	            case 3:
	            	System.out.println("Quiting program...");
	                active = false;
	                break;

	            default: {
	                System.err.println("Client: Invalid operation. Try again");
	            }
	        }
        }       
        scanner.close();
    }

    private static void getPatientVaccinationser(IHospital printServer, Scanner scanner, Credentials userCredentials, int cpr) throws RemoteException {
        try {
            ArrayList<String> vaccinations = printServer.getPatientVaccination(userCredentials, cpr);
            if(!vaccinations.isEmpty()){
                for(int i = 0; i < vaccinations.size(); i++){
                    System.out.println(vaccinations.get(i));
                }
            }
            else {
                System.out.println("Patient has no vaccinations");
            }
        } catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Client: Operation could not be completed. Try again");
        }
    }

    public static void getAnalysis(IHospital printServer){
        try {
            int numbers[] = printServer.getStaticalInformation(); // {⊥}
            System.out.println("---------------------------------------\nStatistical Information\n---------------------------------------");
            System.out.println(numbers[0] + " infections last day"); // {⊥}
            System.out.println(numbers[1] + " people vaccinated"); // {⊥}
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    } // {⊥}

    private static void orderPatientTest(Scanner scanner, IHospital printServer, Credentials userCredentials) throws RemoteException {
        System.out.println("Please enter information");

        System.out.println("Patient CPR: ");
        int cpr = scanner.nextInt(); //{H:H}

        System.out.println("Type of test: ");
        String typeOfTest = scanner.next(); //{H:H}

        System.out.println("Date in format dd.mm.yy: ");
        String dateOfTest = scanner.next(); //{H:H}

        System.out.println("Test type ID: ");
        int testID = scanner.nextInt(); //{H:H}

        try {
            printServer.orderPatientTest(userCredentials, testID, typeOfTest, dateOfTest, cpr); //{H:H}
        } catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Client: Operation could not be completed. Try again");
        }
    }

    private static void setPatientVaccination(Scanner scanner, IHospital printServer, Credentials userCredentials) throws RemoteException {

        System.out.println("Please enter information:");
        System.out.println("Patient CPR: ");
        int cpr = scanner.nextInt(); //{H:H}

	    System.out.println("Vaccination name: ");
	    String vaccinationName = scanner.next(); //{H:H}
	
	    System.out.println("Vaccination date in format dd.mm.yy: ");
	    String vaccinationDate = scanner.next(); //{H:H}

        try {
            printServer.setPatientVaccination(userCredentials, vaccinationName, vaccinationDate, cpr); //{H:H}
        } catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Client: Operation could not be completed. Try again");
        }
    }

    /*
     * Vinna i þessari adferd
     */

    private static void setPatientTestResult(IHospital printServer, Scanner scanner, Credentials userCredentials) throws RemoteException {
        System.out.println("Please enter information:");
        System.out.println("Patient CPR: ");
        int cpr = scanner.nextInt(); //{H:H}

	    System.out.println("Test id: ");
	    int testID = scanner.nextInt(); //{H:H}
	
	    System.out.println("Test result(true/false): ");
	    boolean testResult = scanner.nextBoolean(); //{H:H}
        //check if test exists
        try {
            printServer.setPatientTestResult(userCredentials, testID, cpr, testResult); //{H:H}
        } catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Client: Operation could not be completed. Try again");
        }
    }//{H:H}

    private static void getPatientTestrecords(IHospital printServer, Scanner scanner, Credentials userCredentials, int cpr) throws RemoteException {
        try {
            ArrayList<String> testRecords = printServer.getPatientTestrecords(userCredentials, cpr); //{H:H}
            if(!testRecords.isEmpty()){ //{H:H}
                for(int i = 0; i < testRecords.size(); i++){ //i: {H:H}, testRecords.size():{H:H}
                    System.out.println(testRecords.get(i)); //{H:H}
                }
            }
            else {
                System.out.println("Patient has no tests");
            }
        } catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Client: Operation could not be completed. Try again");
        }
    }//{H:H}

    private static void getPatientAppointments(Scanner scanner, IHospital printServer, Credentials userCredentials, int cpr) {
        try {
            ArrayList<String> appointments = printServer.getPatientAppointments(userCredentials, cpr); //{H:H}
            if(!appointments.isEmpty()){ //{H:H}
                for(int i = 0; i < appointments.size(); i++){ //i: {H:H}, appoinments.size():{H:H}
                    System.out.println(appointments.get(i)); //{H:H}
                }
            }
            else {
                System.out.println("Patient has no appointments");
            }
        } catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Client: Operation could not be completed. Try again");
        }
    }//{H:H}

    private static void setPatientAppointment(Scanner scanner, IHospital printServer, Credentials userCredentials, int cpr) throws RemoteException {
	    System.out.println("Type of an appointment (v: vaccination| p: PCR): ");
	    String type = scanner.next(); //{H:H}
        if(!type.equals("v") & !type.equals("p")){ //{H:H}
            System.out.println("Please try again");
            setPatientAppointment(scanner, printServer, userCredentials, cpr); //{H:H}
        }
        else {
            if(type.equals("p")){type = "PCR";} //{H:H}
            else {type = "vaccination";} //{H:H}
            System.out.println("Date in format dd.mm.yy:  ");
	        String date = scanner.next(); //{H:H}
            try {
                printServer.setPatientAppointments(userCredentials, cpr, type, date); //{H:H}
            } catch (Exception exception){
                exception.printStackTrace(); 
                System.out.println("Client: Operation could not be completed. Try again");
            }
        }
    } //{H:H}
}