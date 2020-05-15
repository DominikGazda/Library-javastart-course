package pl.javastart.library.model;
import java.io.Serializable;
import java.util.Objects;
import java.time.Year;
public abstract class Publication implements Serializable, Comparable<Publication>,CsvConvertible{


    private String title;
    private String publisher;
    private Year year;

    Publication(String title,String publisher,int year)
    {
        this.title=title;
        this.publisher=publisher;
        this.year=Year.of(year);
    }

    public Year getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setYear(int year) {
        this.year = Year.of(year);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String toString()
    {
        return title+" "+publisher+" "+year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publisher, year);
    }

    public int compareTo(Publication p){
     return title.compareToIgnoreCase(p.title);
    }
}


