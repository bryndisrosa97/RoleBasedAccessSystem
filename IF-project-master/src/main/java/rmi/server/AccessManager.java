package rmi.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rmi.server.models.Credentials;
import rmi.server.models.User;
import rmi.server.utils.UserStore;

public class AccessManager {
    private static final List<User> storedUsers = UserStore.read();
    private static List<User> newStoredUsers = new ArrayList<User>();

    // reads employee permissions from employees-roles.json and roles.json
    // held að það sé sniðugara að nota Rolebased í að gera hlutverkin sem sagt þetta
    //@SuppressWarnings("unchecked")
    public static void readEmployeesRoles() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("./src/access/employees-roles.json")) {
            Object obj = jsonParser.parse(reader);
            JSONObject employeeList = (JSONObject) obj;
            JSONArray employees = (JSONArray) employeeList.get("employees");
            // employees.forEach( emp -> parseEmployeeObjectRole( (JSONObject) emp) );

            for (Object emp : employees) {
                if (emp instanceof JSONObject) {
                    JSONObject employee = (JSONObject) emp;
                    String userName = (String) employee.get("name");
                    JSONArray roleList = (JSONArray) employee.get("role");
                    String[] roles = new String[roleList.toArray().length];
                    for (int i = 0; i < roles.length; i++) {
                        Object role = roleList.get(i);
                        roles[i] = role.toString();
                    }

                    for (User user : storedUsers) {
                        if (user.getUsername().equals(userName)) {
                            user.setRole(roles);
                            newStoredUsers.add(user);
                        }
                    }

                }

            }

            readRoles();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // reads employee permissions from roles.json
    public static void readRoles() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("./src/access/roles.json")) {
            Object obj = jsonParser.parse(reader);
            JSONObject roleList = (JSONObject) obj;

            JSONArray roles = (JSONArray) roleList.get("employee-roles");
            // roles.forEach(role -> parseEmployeePermission((JSONObject) role));
            for (Object role : roles) {
                if (role instanceof JSONObject) {
                    JSONObject role1 = (JSONObject) role;
                    String roleName = (String) role1.get("role");
                    JSONArray permissionList = (JSONArray) role1.get("permission");
                    String[] permissions = new String[permissionList.toArray().length];
                    for (int i = 0; i < permissions.length; i++) {
                        Object permission = permissionList.get(i);
                        permissions[i] = permission.toString();
                    }

                    for (User user : newStoredUsers) {
                        String[] roles1 = user.getRole();
                        String[] userPermissions = user.getPermissions();

                        if (roles1 != null) {
                            for (int j = 0; j < roles1.length; j++) {
                                if (roles1[j].equals(roleName)) {
                                    if (userPermissions == null) {
                                        user.setPermissions(permissions);
                                    } else {
                                        ArrayList<String> userPermissionList = new ArrayList<String>(
                                                Arrays.asList(userPermissions));
                                        userPermissionList.removeAll(Arrays.asList(permissions));
                                        userPermissionList.addAll(Arrays.asList(permissions));
                                        String[] permissionArray = userPermissionList
                                                .toArray(new String[userPermissionList.size()]);
                                        user.setPermissions(permissionArray);                               
                                    }
                                }
                            }
                        }
                    }

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Hjalpar fall sem ma i raun henda ut i lokin
    public static String[] readUserInfo(Credentials vCredentials) {
        String sRole[] = null;

        String vSignedInUsername = vCredentials.getUsername();

        for (User user : storedUsers) {
            if (user.getUsername().equals(vSignedInUsername)) {
                sRole = user.getRole();
            }
        }
        return sRole;
    }

    public static String[] readUserAccess(Credentials vCredentials) {
        String ss[] = null;
        String vSignedInUsername = vCredentials.getUsername();

        for (User user : storedUsers) {
            if (user.getUsername().equals(vSignedInUsername)) {
                ss = user.getPermissions();
            }
        }
        return ss;

    }


    public static String findPatientfromCPR(int CPR) {
        String ss = null;
        for (User user : storedUsers) {
            if (user.getCPR()==CPR) {
                ss = user.getUsername();
            }
        }
        return ss;
    }

    // adds roles for employee
    public static void parseEmployeeObjectRole(JSONObject employee) {
        String userName = (String) employee.get("name");
        JSONArray roleList = (JSONArray) employee.get("role");
        String[] roles = new String[roleList.toArray().length];
        for(int i=0; i<roles.length; i++) {
            Object role = roleList.get(i);
            roles[i]=role.toString();
        }

        for (User user : storedUsers) {
            if(user.getUsername().equals(userName)){
                user.setRole(roles);
                newStoredUsers.add(user);
            }
        }
    }

    // Checks if user has access to operation
    public static Boolean hasAccess(Credentials credentials, String operation){
        for (User user : storedUsers) {
            if (user.getUsername().equals(credentials.getUsername())){
                String[] permissions = user.getPermissions();
                if (permissions != null){
                    for (int i = 0; i < permissions.length; i++){
                        if (permissions[i].equals(operation)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
