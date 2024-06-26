package com.tbdev.teaneckminyanim.front;

import com.kosherjava.zmanim.ComplexZmanimCalendar;
import com.kosherjava.zmanim.hebrewcalendar.JewishCalendar;
import com.kosherjava.zmanim.hebrewcalendar.HebrewDateFormatter;
import com.kosherjava.zmanim.hebrewcalendar.JewishDate;
import com.kosherjava.zmanim.util.GeoLocation;
import com.tbdev.teaneckminyanim.global.Zman;

import java.time.LocalDate;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.TimeZone;

public class ZmanimHandler {
    private GeoLocation geoLocation;
//    private ComplexZmanimCalendar complexZmanimCalendar;

    public ZmanimHandler(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
//        this.complexZmanimCalendar = new ComplexZmanimCalendar(geoLocation);
    }

    public ZmanimHandler() {
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
        String locationName = "Deal, NJ";
        double latitude = 40.2523465;
        double longitude = -74.001051;
        double elevation = 30;
        GeoLocation geoLocation = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
        this.geoLocation = geoLocation;
    }


    public Dictionary<Zman, Date> getZmanimForNow() {
        return getZmanim(LocalDate.now());
    }
    public Dictionary<Zman, Date> getZmanim(LocalDate date) {
        Dictionary<Zman, Date> dictionary = new Hashtable();

        ComplexZmanimCalendar complexZmanimCalendar = new ComplexZmanimCalendar(geoLocation);
        complexZmanimCalendar.getCalendar().set(date.getYear(), date.getMonth().getValue() - 1, date.getDayOfMonth());
//        complexZmanimCalendar.getCalendar().

        dictionary.put(Zman.ALOT_HASHACHAR, complexZmanimCalendar.getAlosHashachar());
        dictionary.put(Zman.ETT, complexZmanimCalendar.getMisheyakir7Point65Degrees());
        dictionary.put(Zman.NETZ, complexZmanimCalendar.getSunrise());
        dictionary.put(Zman.SZKS, complexZmanimCalendar.getSofZmanShmaGRA());
        dictionary.put(Zman.SZT, complexZmanimCalendar.getSofZmanTfilaGRA());
        dictionary.put(Zman.CHATZOS, complexZmanimCalendar.getChatzos());
        dictionary.put(Zman.MINCHA_GEDOLA, complexZmanimCalendar.getMinchaGedola());
        dictionary.put(Zman.MINCHA_KETANA, complexZmanimCalendar.getMinchaKetana());
        dictionary.put(Zman.PLAG_HAMINCHA, complexZmanimCalendar.getPlagHamincha());
        dictionary.put(Zman.SHEKIYA, complexZmanimCalendar.getSunset());
        dictionary.put(Zman.EARLIEST_SHEMA, complexZmanimCalendar.getTzaisGeonim5Point88Degrees());
        dictionary.put(Zman.TZES, complexZmanimCalendar.getTzais());
        dictionary.put(Zman.CHATZOS_LAILA, complexZmanimCalendar.getSolarMidnight());

        return dictionary;
    }

    public String getHebrewDate(Date date) {
        JewishDate jd = new JewishDate(date);
        HebrewDateFormatter hdf = new HebrewDateFormatter();
        hdf.setHebrewFormat(true);
        return hdf.format(jd);
    }

    public String getTodaysHebrewDate() {
        return getHebrewDate(new Date());
    }

}
