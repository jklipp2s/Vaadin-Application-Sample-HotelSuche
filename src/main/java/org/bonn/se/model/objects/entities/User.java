package org.bonn.se.model.objects.entities;

import org.bonn.se.process.control.JDBC.Repositories.RoleRepository;
import org.bonn.se.process.control.JDBC.Repositories.UserRepository;
import org.bonn.se.process.control.JDBC.Repositories.UserToRoleRepository;
import org.bonn.se.process.control.exceptions.DataBaseException;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class User implements Serializable {

    private static final long serialVersionUID = 494998494948949495L;


    private int id;
    private String name;
    private String prename;
    private String username;
    private String password;
    private List<String> roles;


    public User(){

    }


    public User(int id, String name, String prename, String username, String password) {
        this.id = id;
        this.name = name;
        this.prename = prename;
        this.username = username;
        this.password = password;
    }

    public User(String name, String prename, String username, String password) throws DataBaseException {
        this.id = UserRepository.getNextId()+1;
        this.name = name;
        this.prename = prename;
        this.username = username;
        this.password = password;

        UserRepository.registerUser(id, name, prename, username, password);



    }

    public static User getreducedUser (String prename, String username, String password ) {
       User reducedUser = new User();
       reducedUser.prename = prename;
       reducedUser.username = username;
       reducedUser.password = password;

       return reducedUser;
    }


    @Override
    public String toString() {
        return
                "username='" + username + '\'' +
                ", password=" + password;
    }


    private void loadRoles() {
      // roles = UserToRoleRepository.getRoles();
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { return password; }

    public String getPrename() { return prename; }

    public String getName() { return name; }

    public List<String> getRoles() { return this.roles; }

    public boolean hasRole(String role) {
        boolean b = false;
        try {
            b = UserToRoleRepository.UserhasRole(this.username, role);
        } catch (DataBaseException e) {
            System.out.println("Hat nicht die Entsprechende Rolle");
        }

        finally {
            return b;
        }

    }



}
