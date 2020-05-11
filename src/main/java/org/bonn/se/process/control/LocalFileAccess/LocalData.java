package org.bonn.se.process.control.LocalFileAccess;

import org.bonn.se.model.objects.entities.Hotel;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.JDBC.Repositories.HotelRepository;
import org.bonn.se.process.control.JDBC.Repositories.UserRepository;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.LocalFiles;

import java.io.*;
import java.util.List;

public class LocalData {
    private static final String locationUsers = LocalFiles.USER_FILE;
    private static final String locationHotels = LocalFiles.HOTEL_FILE;



    public static void saveToFile() throws IOException, DataBaseException {


        FileOutputStream userStream = new FileOutputStream(locationUsers);
        ObjectOutputStream users_to_file = new ObjectOutputStream(userStream);

        FileOutputStream hotelStream = new FileOutputStream(locationHotels);
        ObjectOutputStream hotel_to_file = new ObjectOutputStream(hotelStream);

        UserRepository.update();
        HotelRepository.update();

        List persons = UserRepository.getAllRegisteredUsers();
        List hotels = HotelRepository.getAllRegisteredHotels();

        try {
            users_to_file.writeObject(persons);
            hotel_to_file.writeObject(hotels);
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            users_to_file.close();
            hotel_to_file.close();
        }

        System.out.println("Datenexport erfolgreich!");


    }

    public static void loadFromFile(){
        ObjectInputStream userStream = null;
        FileInputStream file_to_User = null;

        ObjectInputStream hotelStream = null;
        FileInputStream file_to_hotel = null;

        HotelRepository.instantiateRepository();
        UserRepository.instantiateRepository();


        try {


            file_to_User = new FileInputStream(locationUsers);
            userStream = new ObjectInputStream(file_to_User);


            file_to_hotel = new FileInputStream(locationHotels);
            hotelStream = new ObjectInputStream(file_to_hotel);


            List<User> persons = (List<User>) userStream.readObject();
            List<Hotel> hotels =  (List<Hotel>) hotelStream.readObject();

            UserRepository.getAllRegisteredUsers().addAll(persons);
            HotelRepository.getAllRegisteredHotels().addAll(hotels);


        } catch(IOException | ClassNotFoundException | DataBaseException e) {
            e.printStackTrace();
        }

        System.out.println("Datenimport erfolgreich!");

    }

    public static void loadIntoDatabase() throws DataBaseException {

        loadFromFile();

        UserRepository.getAllRegisteredUsers().forEach(user -> {

            try {
                UserRepository.registerUser(user.getId(), user.getName(), user.getPrename(), user.getUsername(),user.getPassword());
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        });


        HotelRepository.getAllRegisteredHotels().forEach(hotel -> {
            try {
                HotelRepository.registerHotel(hotel.getId(),hotel.getName(), hotel.getOrt(), hotel.getDescription());
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Datenbankimport erfolgreich!");

    }





}
