package org.bonn.se.Admin;

import org.bonn.se.model.objects.entities.Hotel;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.JDBC.DataBaseConnection;
import org.bonn.se.process.control.JDBC.DataBaseCreation;
import org.bonn.se.process.control.JDBC.Repositories.BookingRepository;
import org.bonn.se.process.control.JDBC.Repositories.HotelRepository;
import org.bonn.se.process.control.JDBC.Repositories.UserRepository;
import org.bonn.se.process.control.JDBC.Repositories.UserToRoleRepository;
import org.bonn.se.process.control.LocalFileAccess.LocalData;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.services.util.ConnectionTypes;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.TableNames;

import java.io.IOException;

public class AdminClass {

    public static void main(String[] args) {


        try {


            DataBaseConnection.setUPConnection(ConnectionTypes.CONNECTION_TYPE_PostgreSQL);





        } catch (DataBaseException e) {

        }


    }




}
