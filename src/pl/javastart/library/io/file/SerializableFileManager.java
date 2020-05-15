package pl.javastart.library.io.file;
import pl.javastart.library.exception.DataImportException;
import pl.javastart.library.exception.DataExportException;
import pl.javastart.library.model.Library;

import java.io.*;


public class SerializableFileManager implements FileManager{

    private static final String FILE_NAME="Library.o";

    public void exportData(Library library){
        try(
                var oos= new ObjectOutputStream(new FileOutputStream(FILE_NAME));
                )
        {
            oos.writeObject(library);
        }
        catch(FileNotFoundException e){
            throw new DataExportException("Brak pliku "+FILE_NAME);
        }
        catch(IOException e){
            throw new DataExportException("Błąd zapisu do plku "+FILE_NAME);
        }
    }

    public Library importData(){
        try (
                var reader=new ObjectInputStream(new FileInputStream(FILE_NAME));
                )
        {
            return (Library)reader.readObject();
        }
        catch(FileNotFoundException e){
            throw new DataImportException("Brak pliku "+FILE_NAME);
        }
        catch(IOException e){
            throw new DataImportException("Błąd zapisu do pliku "+FILE_NAME);
        }
        catch(ClassNotFoundException e){
            throw new DataImportException("Niezgodny typ danych w pliku "+FILE_NAME);
        }

    }
}
