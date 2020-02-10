package ir.vira.RoomDatabase.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Books implements Serializable {
    @PrimaryKey
    @SerializedName("ID")
    private int id;
    @ColumnInfo(name = "PoetID")
    @SerializedName("PoetID")
    private int poetID;
    @ColumnInfo(name = "Name")
    @SerializedName("Name")
    private String name;
    @ColumnInfo(name = "Publisher")
    @SerializedName("Publisher")
    private String publisher;
    @ColumnInfo(name = "Image")
    @SerializedName("Image")
    private String image;

    public Books(int id, int poetID, String name, String publisher, String image) {
        this.id = id;
        this.poetID = poetID;
        this.name = name;
        this.publisher = publisher;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getPoetID() {
        return poetID;
    }

    public String getName() {
        return name;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPoetID(int poetID) {
        this.poetID = poetID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
