package ir.vira.RoomDatabase.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Poems implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "BookID")
    @SerializedName("BookID")
    private int bookID;
    @ColumnInfo(name = "Name")
    @SerializedName("Name")
    private String name;
    @ColumnInfo(name = "Text")
    @SerializedName("Text")
    private String text;
    @ColumnInfo(name = "Voice")
    @SerializedName("Voice")
    private String voice;
    @ColumnInfo(name = "AddressDownloadedVoice")
    private String addressDownloadedVoice;
    @ColumnInfo(name = "isBookmark")
    private boolean isBookmark;

    public Poems(int id, int bookID, String name, String text, String voice) {
        this.id = id;
        this.bookID = bookID;
        this.name = name;
        this.text = text;
        this.voice = voice;
    }

    public int getId() {
        return id;
    }

    public int getBookID() {
        return bookID;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getVoice() {
        return voice;
    }

    public String getAddressDownloadedVoice() {
        return addressDownloadedVoice;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setAddressDownloadedVoice(String addressDownloadedVoice) {
        this.addressDownloadedVoice = addressDownloadedVoice;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }
}
