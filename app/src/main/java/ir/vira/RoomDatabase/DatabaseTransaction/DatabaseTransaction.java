package ir.vira.RoomDatabase.DatabaseTransaction;

import android.content.Context;
import androidx.room.Room;

import java.util.List;

import ir.vira.RoomDatabase.Database.DatabaseGhazal;
import ir.vira.RoomDatabase.Entities.Books;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.RoomDatabase.Entities.Poets;

public class DatabaseTransaction {
    private static DatabaseGhazal databaseGhazal;
    private Context context;

    public DatabaseTransaction(Context context) {
        this.context = context;
        if (databaseGhazal == null)
            databaseGhazal = Room.databaseBuilder(context , DatabaseGhazal.class , "db_ghazal").allowMainThreadQueries().build();
    }

    public void insertIntoBooks(List<Books> books) {
        databaseGhazal.DAO().insertIntoBooks(books);
    }
    public void insertIntoPoets(List<Poets> poets){
        databaseGhazal.DAO().insertIntoPoets(poets);
    }
    public void insertIntoPoems(List<Poems> poems){
        databaseGhazal.DAO().insertIntoPoems(poems);
    }

    public void updateBookmark(Poems poems , boolean isBookmark){
        poems.setBookmark(isBookmark);
        databaseGhazal.DAO().updateBookmark(poems);
    }

    public void updateAddressesDownload(Poems poems){
        databaseGhazal.DAO().updateAddressesDownload(poems);
    }

    public Poets getPoet(int id){
        return databaseGhazal.DAO().getPoet(id);
    }
    public List<Poets> getPoets(){
       return databaseGhazal.DAO().getPoets();
    }
    public List<Books> getBooks(){
        return databaseGhazal.DAO().getBooks();
    }
    public List<Books> getBooks(int poetID){
        return databaseGhazal.DAO().getBooks(poetID);
    }
    public List<Poems> getPoems(){
        return databaseGhazal.DAO().getPoems();
    }

    public List<Poems> getDownloadedPoems(){
        return databaseGhazal.DAO().getDownloadedPoems();
    }

    public List<Poems> getPoems(boolean isBookmark){
        return databaseGhazal.DAO().getPoems(isBookmark);
    }
    public List<Poems> getPoems(int bookID){
        return databaseGhazal.DAO().getPoems(bookID);
    }

}
