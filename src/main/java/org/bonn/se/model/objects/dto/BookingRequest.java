package org.bonn.se.model.objects.dto;

import org.bonn.se.model.objects.entities.Hotel;

import java.time.LocalDate;

public class BookingRequest {
    private LocalDate abreise;
    private LocalDate anreise;
    private String iban;
    private int personNumber;
    private Hotel hotel;
    public void setAbreise(LocalDate abreise) {
        this.abreise = abreise;
    }

    public void setAnreise(LocalDate anreise) {
    this.anreise = anreise;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public LocalDate getAbreise() {
        return abreise;
    }

    public LocalDate getAnreise() {
        return anreise;
    }

    public String getIban() {
        return iban;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public Hotel getHotel() {
        return hotel;
    }
}
