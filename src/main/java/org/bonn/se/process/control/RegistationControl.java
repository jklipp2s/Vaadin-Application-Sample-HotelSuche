package org.bonn.se.process.control;

import org.bonn.se.model.objects.dto.UserDTO;
import org.bonn.se.model.objects.entities.User;
import org.bonn.se.process.control.JDBC.DataBaseConnection;
import org.bonn.se.process.control.JDBC.Repositories.UserRepository;
import org.bonn.se.process.control.exceptions.DataBaseException;
import org.bonn.se.process.control.exceptions.RegisterFailException;
import org.bonn.se.services.util.ConnectionTypes;

public class RegistationControl {




    public static boolean register(UserDTO userDTO) throws DataBaseException, RegisterFailException {

        String exception = "";
        DataBaseConnection.setUPConnection(ConnectionTypes.CONNECTION_TYPE_PostgreSQL);

        if (UserRepository.emailIsAlreadyUsed(userDTO.getEmail())) exception += "Email is already In Use\n";
        if (userDTO.getName().length() > 35) exception += "Name is too Long\n";
        if (userDTO.getPrename().length() > 35) exception += "PreName is too Long\n";
        if (userDTO.getPassword().length() > 18) exception += "Password is too Long\n";


        if (userDTO.getName().isEmpty() || userDTO.getPrename().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty()){
            exception += "Please fill in all fields";
        }

        if (exception.isEmpty()) {
            new User(userDTO.getName(), userDTO.getPrename(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword());
            return true;
        }

        RegisterFailException ex = new RegisterFailException();
        ex.setReason(exception);
        throw ex;

    }

}
