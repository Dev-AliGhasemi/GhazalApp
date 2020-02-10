package ir.vira.RoomDatabase.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ir.vira.RoomDatabase.DAO.DAO;
import ir.vira.RoomDatabase.Entities.Books;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.RoomDatabase.Entities.Poets;

@Database(entities = {Poets.class, Books.class, Poems.class} , version = 2 , exportSchema = false)
public abstract class DatabaseGhazal extends RoomDatabase {
    public abstract DAO DAO();
}
