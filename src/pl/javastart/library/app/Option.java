package pl.javastart.library.app;

import pl.javastart.library.exception.NoSuchOptionException;

enum Option {
    EXIT(0,"Wyjście z programu"),
    ADD_BOOK(1,"Dodanie książki"),
    ADD_MAGAZINE(2,"Dodanie magazynu"),
    PRINT_BOOKS(3,"Wyświetl dostępne książki"),
    PRINT_MAGAZINES(4,"Wyświetl dostępne magazyny"),
    DELETE_BOOK(5,"Usuń książkę"),
    DELETE_MAGAZINE(6,"Usuń magazyn");


    private int value;
    private String description;

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
    Option(int value,String description){
        this.value=value;
        this.description=description;
    }

    public String toString(){
        return value+"-"+description;
    }

    static Option createFromInt(int option) throws NoSuchOptionException {
        try{
            return Option.values()[option];
        }catch(ArrayIndexOutOfBoundsException e){
            throw new NoSuchOptionException("Brak opcji o id "+option);
        }
    }
}