package ir.vira.Models;

public class ModelFonts {

    private String fontName;
    private int fontImage;

    public ModelFonts(String fontName, int fontImage) {
        this.fontName = fontName;
        this.fontImage = fontImage;
    }

    public String getFontName() {
        return fontName;
    }

    public int getFontImage() {
        return fontImage;
    }
}
