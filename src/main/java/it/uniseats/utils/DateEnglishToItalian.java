package it.uniseats.utils;

import java.util.Date;

public class DateEnglishToItalian {
    public DateEnglishToItalian(){}


    public String transform(String s){



        String year= (String) s.subSequence(0,4);
        String month= (String) s.subSequence(5,7);
        String day= (String) s.subSequence(8,10);
        return day+"/"+month+"/"+year;
    }
}
