//Spurning með tvær auka aðferðir

package rmi.server;

import rmi.server.models.Credentials;
import rmi.server.models.User;
import rmi.server.models.Tests;
import rmi.server.models.Appointment;
import rmi.server.models.Vaccination;
import rmi.server.utils.AESEncryption;
import rmi.server.utils.UserStore;

import java.util.*;
import java.text.SimpleDateFormat;

public class AuthManager {
  private static List<User> storedUsers; // {H: H}
  private static final AESEncryption aesEncryption = new AESEncryption(); // {H: H}

  /**
   * Authenticate user with username and password
   * `protected` access adds some extra security from accessing this method
   */
  protected static void authenticate(Credentials credentials) {
    System.out.println("AuthManager: Trying to authenticate user " + credentials.getUsername());
    boolean validCredentials;
    for (User user : storedUsers) {
      validCredentials = (user.getUsername().equals(credentials.getUsername()) &&
          aesEncryption.isPasswordValid(
              credentials.getPassword(),
              user.getEncryptedPassword(),
              user.getSalt()));
      if (validCredentials) {
        System.out.println("AuthManager: User with username " + credentials.getUsername() + " authenticated.");

        return;
      }
    }
    // If the code reaches here it means that there isn't any user with the provided
    // credentials
    throw new SecurityException("AuthManager: User could not be authenticated.");
  }

  public static String getUserName(int cpr){ // {H: H}
    for(int i = 0; i < storedUsers.size(); i++){ // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
      if(storedUsers.get(i).getCPR() == cpr){ // getCPR: {H:H}, cpr: {H:H}, {H:H} -> {H:H} 
        return storedUsers.get(i).getUsername(); // {H:H} // {⊥} -> {H:H}
      }
    }
    return null;
  }

  public static void addVaccination(int cpr, Vaccination vaccination) { // {H: H}
    for(int i = 0; i < storedUsers.size(); i++){ // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
      if(storedUsers.get(i).getCPR() == cpr){ // getCPR: {H:H}, cpr: {H:H}, {H:H} -> {H:H}
        storedUsers.get(i).setVaccination(vaccination); // {H:H} // {⊥} -> {H:H}
      }
    }
  }

  public static void addTest(int cpr, Tests test) { // {H: H}
    for(int i = 0; i < storedUsers.size(); i++){ // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
      if(storedUsers.get(i).getCPR() == cpr){ // getCPR: {H:H}, cpr: {H:H}, {H:H} -> {H:H}
        storedUsers.get(i).setTests(test); // {H:H} // {⊥} -> {H:H}
      }
    }
  }

  public static void addAppointment(int cpr, Appointment appointment) { // {H: H}
    for(int i = 0; i < storedUsers.size(); i++){ // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
      if(storedUsers.get(i).getCPR() == cpr){ // getCPR: {H:H}, cpr: {H:H}, {H:H} -> {H:H}
        storedUsers.get(i).setAppointment(appointment); // {H:H} // {⊥} -> {H:H}
      }
    }
  }

  public static int getPatientCprFromName(Credentials credentials) { // {H: H}
    for (User user : storedUsers) { //user: {H:H}
        if (user.getUsername().equals(credentials.getUsername())) { //credentials.getUsername: {H:H}, {H:H} -> {H:H}
            return user.getCPR(); //{H:H} // {H:H} -> {H:H}
        }
    }
    return -1;
  }

  public static ArrayList<Appointment> getAppointments(int cpr) { // {H: H}
    for(int i = 0; i < storedUsers.size(); i++){ // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
      if(storedUsers.get(i).getCPR() == cpr){ // getCPR: {H:H}, cpr: {H:H}, {H:H} -> {H:H}
        return storedUsers.get(i).getAppointment(); // {H:H} // {⊥} -> {H:H}
      }
    }
    return null;
  }

  public static ArrayList<Vaccination> getUserVaccination(int cpr) { // {H: H}
    for(int i = 0; i < storedUsers.size(); i++){ // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
      if(storedUsers.get(i).getCPR() == cpr){ // getCPR: {H:H}, cpr: {H:H}, {H:H} -> {H:H}
        return storedUsers.get(i).getVaccination(); // {H:H} // {⊥} -> {H:H}
      }
    }
    return null;
  }

  public static ArrayList<Tests> getUserTests(int cpr) { // {H: H}
    for(int i = 0; i < storedUsers.size(); i++){  // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
      if(storedUsers.get(i).getCPR() == cpr){  // getCPR: {H:H}, cpr: {H:H}, {H:H} -> {H:H}
        return storedUsers.get(i).getTests(); // {H:H} // {⊥} -> {H:H}
      }
    }
    return null;
  }

  public static void setPatientTestsResult(int cpr, int testID, boolean testResault) { // {H:H}
    for(int i = 0; i < storedUsers.size(); i++){ // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
      if(storedUsers.get(i).getCPR() == cpr){ // getCPR: {H:H}, cpr: {H:H}, {H:H} -> {H:H}
        ArrayList<Tests> tests = storedUsers.get(i).getTests(); // {H:H}
        if(tests != null){
          for(int j = 0; j < tests.size(); j++){ // j: {H:H}, tests.size(): {H:H}, {H:H} -> {H:H}
            if(tests.get(j).getTestID() == testID){ // getTestID: {H:H}, testID: {H:H}, {H:H} -> {H:H}
              storedUsers.get(i).getTests().get(j).setResult(testResault); // {H:H} //{⊥} -> {H:H}
            }
          }
        }
      }
    }
  }

  public static int getInfectionsLastDay(){ // {H: H}
    int numbersOfInfections= 0; // {H: H}
    Date date = new Date(System.currentTimeMillis()-24*60*60*1000); // {⊥}
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy"); // {⊥}
    String d = formatter.format(date);    // {⊥}
    for(int i = 0; i<storedUsers.size(); i++){ // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
        ArrayList<Tests> tests = (storedUsers.get(i)).getTests(); // {H:H}
        if (tests != null){
            for(int j = 0; j<tests.size(); j++){ // j: {H:H}, tests.size(): {H:H}, {H:H} -> {H:H}
            if (!tests.isEmpty()){ // {H:H}
                if(d.equals(tests.get(j).getDate()) & tests.get(j).getResult()) // getDate: {H:H} getResult: {H:H}
                    numbersOfInfections++; // {H: H} //{⊥} -> {H:H}
                }
            }
        }
    }
    return numbersOfInfections;  // {H: H}
  }

  public static int getNumberOfVaccinations(){ // {H: H}
    int numbersOfVaccinations = 0; // {H: H}
    for(int i = 0; i<storedUsers.size(); i++){ // i: {⊥}, storedUsers.size(): {⊥}, {⊥} -> {⊥}
        if (storedUsers.get(i).getVaccination() != null){ // getVaccination: {H:H}
            numbersOfVaccinations++; // {H: H} //{⊥} -> {H:H}
        }
    }
    return numbersOfVaccinations; // {H: H}
  }

  /**
   * Write test user username and encrypted password to disk
   */
  protected static void initTestUsers() { // init function for formality
    UserStore.write(getTestUsers());
  } // {H: H}

  /**
   * A list containing test users
   */
  private static List<User> getTestUsers() { // {H: H}
    ArrayList<User> testUsers = new ArrayList<>();  // {H: H}
    testUsers.add(new User(1, (new Credentials("Alice", "al"))));     // {H: H}
    testUsers.add(new User(2, (new Credentials("Bob", "432"))));      // {H: H}
    testUsers.add(new User(3, (new Credentials("Cecilia", "43C"))));  // {H: H}
    testUsers.add(new User(4, (new Credentials("David", "4D"))));     // {H: H}
    testUsers.add(new User(5, (new Credentials("Erica", "EE"))));     // {H: H}
    testUsers.add(new User(6, (new Credentials("Fred", "F4"))));      // {H: H}
    testUsers.add(new User(7, (new Credentials("George", "4G3"))));   // {H: H}
    return testUsers; // {H: H}
  }

  protected static void initTestUsersTestResults() { // {H: H}
    Tests test1 = new Tests(1, "PCR", "08.05.22");    // {H: H}
    Tests test2 = new Tests(2, "rapid", "07.05.22");  // {H: H}
    Tests test3 = new Tests(3, "PCR", "09.05.22");    // {H: H}

    addTest(5, test1);                                  // {H: H}
    setPatientTestsResult(5, 1, true);  // {H: H}
    addTest(6, test2);                                  // {H: H}
    setPatientTestsResult(6, 2, true);  // {H: H}
    addTest(7, test3);                                  // {H: H}
    setPatientTestsResult(7, 3, true);  // {H: H}
  }

  protected static void initTestUsersTVaccinationResults() { // {H: H}
    storedUsers = UserStore.read(); // {H: H}
    Vaccination test1 = new Vaccination("Pfizer", 1,"02.05.22");  // {H: H}
    Vaccination test2 = new Vaccination("Janssen", 1, "15.15.22");  // {H: H}
    Vaccination test3 = new Vaccination("Moderna", 1, "02.05.22");  // {H: H}
    Vaccination test4 = new Vaccination("AstraZeneca", 1, "15.15.22");  // {H: H}

    addVaccination(1, test1); // {H: H}
    addVaccination(2, test2); // {H: H}
    addVaccination(3, test3); // {H: H}
    addVaccination(4, test4); // {H: H}
  }
}
