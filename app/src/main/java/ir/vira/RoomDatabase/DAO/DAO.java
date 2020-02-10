package ir.vira.RoomDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import ir.vira.RoomDatabase.Entities.Books;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.RoomDatabase.Entities.Poets;

@Dao
public interface DAO {

    @Insert
    void insertIntoBooks(List<Books> books);
    @Insert
    void insertIntoPoets(List<Poets> poets);
    @Insert
    void insertIntoPoems(List<Poems> poems);
    @Update
    void updateBookmark(Poems poems);
    @Update
    void updateAddressesDownload(Poems poems);

    @Query("SELECT * FROM Poets")
    List<Poets> getPoets();
    @Query("SELECT * FROM poets WHERE ID = :id ")
    Poets getPoet(int id);
    @Query("SELECT * FROM Books WHERE PoetID = :poetID")
    List<Books> getBooks(int poetID);
    @Query("SELECT * FROM Books")
    List<Books> getBooks();
    @Query("SELECT * FROM Poems WHERE BookID = :bookID ")
    List<Poems> getPoems(int bookID);
    @Query("SELECT * FROM Poems")
    List<Poems> getPoems();
    @Query("SELECT * FROM Poems WHERE isBookmark = :isBookmark")
    List<Poems> getPoems(boolean isBookmark);
    @Query("SELECT * FROM Poems WHERE AddressDownloadedVoice != ''")
    List<Poems> getDownloadedPoems();
}
