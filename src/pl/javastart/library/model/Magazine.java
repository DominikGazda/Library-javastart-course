package pl.javastart.library.model;

import java.time.MonthDay;

public class Magazine extends Publication{

    public static final String TYPE="Magazyn";
    private MonthDay monthDay;
    private String language;


    public Magazine(String title,String publisher,String language, int year,int month,int day)
    {
        super(title,publisher,year);
        this.language=language;
        this.monthDay= MonthDay.of(month,day);
    }


    public MonthDay getMonth() {
        return monthDay;
    }
    public void setMonthDay(MonthDay monthDay){
        this.monthDay=monthDay;
    }

    public String getLanguage() {
        return language;
    }


    public void setLanguage(String language) {
        this.language = language;
    }

    public String toString()
    {
        return super.toString()+" "+monthDay.getMonthValue()+" "+monthDay.getDayOfMonth()+" "+language;
    }
    public String toCsv(){
        return (TYPE+";")+getTitle()+";"+getPublisher()+";"+getYear()+";"+
                monthDay.getMonthValue()+";"+monthDay.getDayOfMonth()+";"+language+"";
    }
}
