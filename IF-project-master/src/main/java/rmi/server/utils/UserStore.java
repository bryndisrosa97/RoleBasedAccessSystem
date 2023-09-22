package rmi.server.utils;

import rmi.server.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to read and write users to disk and adding test users
 */
public class UserStore {
    public static final String STORE_FILE_PATH = "src/data/users.data";

    public static void write(List<User> users) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(STORE_FILE_PATH);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (User user : users) {
                objectOutputStream.writeObject(user);
            }
            // Save a null object at the end. It is helpful when reading data
            objectOutputStream.writeObject(null);

            System.out.println("Users are saved in disk.");
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException notSerializableException) {
            notSerializableException.printStackTrace();
        }
    }

    public static List<User> read() {
        List<User> users = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(STORE_FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            boolean hasContent = true;
            while (hasContent) {
                Object object = objectInputStream.readObject();
                if (object != null)
                    users.add((User) object);
                else
                    hasContent = false;
            }
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}
