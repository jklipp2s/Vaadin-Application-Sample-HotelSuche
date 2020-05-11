package org.bonn.se.model.objects.entities;

import org.bonn.se.process.control.JDBC.Repositories.HotelRepository;
import org.bonn.se.process.control.exceptions.DataBaseException;


public class Hotel implements  java.io.Serializable {

    private static final long serialVersionUID = 494998494948949494L;

    private Integer id;
    private String name;
    private String ort;
    private String description;


    public Hotel() {
    }

    public Hotel(Integer id, String name, String ort, String description) {
        this.id = id;
        this.name = name;
        this.ort = ort;
        this.description = description;
    }


    public Hotel(String name, String ort, String description) throws DataBaseException {
        this.id = HotelRepository.CountObjectsinDataBaseTable()+1;
        this.name = name;
        this.ort = ort;
        this.description = description;

        HotelRepository.registerHotel(id, name, ort, description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setMyId(Integer id) {
        this.id = id;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {


        return this.name + ", " + this.ort  .replace("[", "").replace("]", "");
    }
}


