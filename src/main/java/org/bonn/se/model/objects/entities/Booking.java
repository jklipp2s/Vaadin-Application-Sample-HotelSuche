package org.bonn.se.model.objects.entities;

import java.time.LocalDate;

public class Booking {
    private int id;
    private LocalDate anreise;
    private LocalDate abreise;
    private LocalDate datumBuchung;
    private String iban;
    private int number;
    private Hotel hotel;
    private User user;

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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
