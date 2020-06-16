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
    private String email;
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

    public User(String name, String prename, String email, String username, String password) throws DataBaseException {
        this.id = UserRepository.getNextId()+1;
        this.name = name;
        this.prename = prename;
        this.username = username;
        this.password = password;

        UserRepository.registerUser(id, name, prename, email, username, password);



    }

    public static User getreducedUser (String prename, String username, String password ) {
       User reducedUser = new User();
       reducedUser.prename = prename;
       reducedUser.username = username;
       reducedUser.password = password;

       return reducedUser;
    }



    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public String getPrename() { return prename; }

    public String getName() { return name; }

    public List<String> getRoles()  {

        try {

            if (roles == null) roles = UserToRoleRepository.getRoles(this);

        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return this.roles; }

    public boolean hasRole(String role) {
        return this.getRoles().contains(role);
    }


    public String getFullname() {
        return this.name + " " + this.prename;
    }

    public String getRolesAsList() {
        getRoles();
        String result = "";
        for (int i = 0; i < roles.size(); i++) {
            result += " " + roles.get(i);

            if(i<roles.size()-1) result += ",";

        }
        return result;
    }





}
