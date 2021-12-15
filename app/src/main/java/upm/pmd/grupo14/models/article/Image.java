package upm.pmd.grupo14.models.article;

import android.graphics.Bitmap;

/**
 * Image model an Image
 */
public class Image {
    private Bitmap img;
    private String media_type;

    /**
     * Constructor
     * @param img the Bitmap of the image
     * @param media_type the extension of the image
     */
    public Image(Bitmap img, String media_type) {
        this.img = img;
        this.media_type = media_type;
    }

    /**
     * getter of the bitmap
     * @return return the Bitmap of the image
     */
    public Bitmap getImg() {
        return img;
    }

    /**
     * setter of the bitmap
     * @param img the Bitmap of the image
     */
    public void setImg(Bitmap img) {
        this.img = img;
    }

    /**
     * getter of the extension of the image
     * @return return the extension of the image
     */
    public String getMedia_type() {
        return media_type;
    }

    /**
     * setter of the extension of the image
     * @param media_type the extension of the image
     */
    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }
}
