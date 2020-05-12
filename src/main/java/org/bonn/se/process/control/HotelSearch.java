package org.bonn.se.process.control;

import org.bonn.se.model.objects.entities.Hotel;
import org.bonn.se.process.control.JDBC.Repositories.HotelRepository;
import org.bonn.se.process.control.exceptions.DataBaseException;

import java.util.List;

public class HotelSearch {



    private HotelSearch() {

    }

    public static HotelSearch search = null;

    public static HotelSearch getInstance() {
        if(search == null) {
            search = new HotelSearch();
        }
        return search;
    }

    public List<Hotel> getHotelByOrt( String ort) throws DataBaseException { return HotelRepository.getHotels(ort); }



}
