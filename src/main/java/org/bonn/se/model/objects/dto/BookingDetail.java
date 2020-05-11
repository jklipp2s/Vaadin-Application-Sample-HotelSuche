package org.bonn.se.model.objects.dto;

import java.time.LocalDate;

public class BookingDetail {
    private int id;
    private String customername;
    private LocalDate anreise;
    private LocalDate abreise;



    private LocalDate datumBuchung;
    private int number;
    private String hotel;
    private String user;

    public BookingDetail() {

    }

    public BookingDetail(int id, String customername, LocalDate anreise, LocalDate abreise, LocalDate datumBuchung, int number, String hotel, String user) {
        this.id = id;
        this.customername = customername;
        this.anreise = anreise;
        this.abreise = abreise;
        this.datumBuchung = datumBuchung;
        this.number = number;
        this.hotel = hotel;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getAnreise() {
        return anreise;
    }

    public void setAnreise(LocalDate anreise) {
        this.anreise = anreise;
    }

    public LocalDate getAbreise() {
        return abreise;
    }

    public void setAbreise(LocalDate abreise) {
        this.abreise = abreise;
    }

    public LocalDate getDatumBuchung() {
        return datumBuchung;
    }

    public void setDatumBuchung(LocalDate datumBuchung) {
        this.datumBuchung = datumBuchung;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCustomername() { return customername;
    }

    public void setCustomername(String customername) { this.customername = customername;
    }




}
