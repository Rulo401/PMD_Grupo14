package upm.pmd.grupo14.models.article;

import android.graphics.Bitmap;

public class Image {
    private Bitmap img;
    private String media_type;

    public Image(Bitmap img, String media_type) {
        this.img = img;
        this.media_type = media_type;
    }
}
