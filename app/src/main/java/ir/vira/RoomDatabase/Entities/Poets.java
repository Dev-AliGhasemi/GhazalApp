package ir.vira.RoomDatabase.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Poets implements Serializable {
    @PrimaryKey
    @SerializedName("ID")
    @ColumnInfo(name = "ID")
    private int id;
    @ColumnInfo(name = "Name")
    @SerializedName("Name")
    private String name;
    @ColumnInfo(name = "DateBorn")
    @SerializedName("DateBorn")
    private String dateBorn;
    @ColumnInfo( name = "LocationBorn")
    @SerializedName("LocationBorn")
    private String locationBorn;
    @ColumnInfo(name = "Description")
    @SerializedName("Description")
    private String description;
    @ColumnInfo(name = "Image")
    @SerializedName("Image")
    private String image;

    public Poets(int id, String name, String dateBorn, String locationBorn, String description, String image) {
        this.id = id;
        this.name = name;
        this.dateBorn = dateBorn;
        this.locationBorn = locationBorn;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateBorn() {
        return dateBorn;
    }

    public String getLocationBorn() {
        return locationBorn;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
