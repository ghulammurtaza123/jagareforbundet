package com.produkt.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.produkt.model.Event;
import com.produkt.model.News;

public class DateComparator implements Comparator<Event> {

	 private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	    private final SimpleDateFormat newsDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	    @Override
	    public int compare(Event event1, Event event2) {
	        return compareDates(event1.getDatepicker(), event2.getDatepicker(), dateFormat);
	    }

	    public int compareNews(News news1, News news2) {
	        return compareDates(news1.getDatepicker(), news2.getDatepicker(), newsDateFormat);
	    }

	    private int compareDates(String date1Str, String date2Str, SimpleDateFormat formatter) {
	        try {
	            Date date1 = formatter.parse(date1Str);
	            Date date2 = formatter.parse(date2Str);

	            // Use compareTo to compare dates
	            return date1.compareTo(date2);
	        } catch (ParseException e) {
	            // Handle the parse exception based on your requirements
	            e.printStackTrace();
	            return 0; // Or throw an exception
	        }
	    }
}
