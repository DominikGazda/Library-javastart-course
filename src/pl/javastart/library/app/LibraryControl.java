package pl.javastart.library.app;
import pl.javastart.library.exception.DataExportException;
import pl.javastart.library.exception.DataImportException;
import pl.javastart.library.exception.NoSuchOptionException;
import pl.javastart.library.exception.UserAlreadyExistsException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.io.file.FileManager;
import pl.javastart.library.io.file.FileManagerBuilder;
import pl.javastart.library.model.*;
import java.util.Comparator;
import java.util.InputMismatchException;

public class LibraryControl {

    private ConsolePrinter printer=new ConsolePrinter();
    private DataReader dataReader=new DataReader(printer);
    private FileManager fileManager;

    private Library library;

    LibraryControl(){
        fileManager=new FileManagerBuilder(printer,dataReader).build();
        try{
            library=fileManager.importData();
            printer.printLine("Zaimportowane dane z pliku");
        }
        catch(DataImportException e){
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowane nową bazę");
            library=new Library();
        }
    }


    public void controlLoop()
    {
        Option option;

        do{
            printOptions();
            option=getOption();
            switch(option)
            {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case FIND_BOOK:
                    findBook();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Nie ma takiej opcji próbuj ponownie");
            }
        }while(option!=Option.EXIT);
    }

    private void printOptions()
    {
        printer.printLine("Wybierz opcję: ");
        for(Option option:Option.values()){
            printer.printLine(option.toString());
        }

    }
    private Option getOption(){
        boolean optionOk=false;
        Option option=null;

        while(!optionOk){
            try{
                option=Option.createFromInt(dataReader.getInt());
                optionOk=true;
            }
            catch(NoSuchOptionException e){
                printer.printLine(e.getMessage()+" Podaj ponownie");
            }
            catch(InputMismatchException ignored)
            {
                printer.printLine("Wprowadzono wartość, która nie jest liczbą");
            }
        }
        return option;
    }


    private void addBook(){
        try{
            Book book=dataReader.readAndCreateBook();
            library.addPublication(book);
        }
        catch(InputMismatchException e){
            printer.printLine("Nie udało się utowrzyć nowej książki. Błędne dane!");
        }
        catch(ArrayIndexOutOfBoundsException e){
            printer.printLine("Osiągnięto limit. Nie można dodać nowej książki");
        }
    }

    private void printBooks(){
        printer.printBooks(library.getSortedPublications(Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)));
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, błędne dane!");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojemność nie można dodać kolejnego magazynu");
        }
    }
    private void printMagazines(){
        printer.printMagazines(library.getSortedPublications(Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)));
    }

    private void addUser(){
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try{
            library.addUser(libraryUser);
        }
        catch(UserAlreadyExistsException e){
            printer.printLine(e.getMessage());
        }
    }

    private void printUsers(){
        printer.printUsers(library.getSortedUsers(Comparator.comparing(User::getLastName,String.CASE_INSENSITIVE_ORDER)));
    }


    private void deleteMagazine(){
        try{
            Magazine magazine=dataReader.readAndCreateMagazine();
            if(library.removePublication(magazine))
                printer.printLine("Usunięto magazyn");
            else{
                printer.printLine("Brak wsazanego magazynu");
            }
        }catch(InputMismatchException e){
            printer.printLine("Nie udało się usunąć magazynu, złe dane");
        }
    }
    private void deleteBook(){
        try{
            Book book= dataReader.readAndCreateBook();
            if(library.removePublication(book)){
                printer.printLine("Usunięto książkę");
            }
            else{
                printer.printLine("Nie znaleziono takiej książki");
            }

        }catch(InputMismatchException e){
            printer.printLine("Nie udało się usunąć książki, złe dane");
        }
    }
    private void exit(){
        try{
            fileManager.exportData(library);
            printer.printLine("Export zakończony sukcesem");
        }catch(DataExportException e){
            printer.printLine(e.getMessage());
        }
        printer.printLine("Koniec programu");
        dataReader.close();
    }
    private void findBook(){
        printer.printLine("Podaj tytuł książki");
        String title = dataReader.getString();
        String notFoundMessage = "Nie znaleziono takiej książki";
        library.findPublicationByTitle(title).map(Publication::toString)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println(notFoundMessage)
                );

    }

    private enum Option {
        EXIT(0,"Wyjście z programu"),
        ADD_BOOK(1,"Dodanie książki"),
        ADD_MAGAZINE(2,"Dodanie magazynu"),
        PRINT_BOOKS(3,"Wyświetl dostępne książki"),
        PRINT_MAGAZINES(4,"Wyświetl dostępne magazyny"),
        DELETE_BOOK(5,"Usuń książkę"),
        DELETE_MAGAZINE(6,"Usuń magazyn"),
        ADD_USER(7,"Dodaj czytelnika"),
        PRINT_USERS(8,"Wyświetl czytelników"),
        FIND_BOOK(9,"Znajdź książkę po tytule");




        private int value;
        private String description;

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
    }





