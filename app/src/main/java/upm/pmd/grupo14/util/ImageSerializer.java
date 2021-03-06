package upm.pmd.grupo14.util;

import androidx.annotation.RequiresApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

/**
 * Class containing the method for serializing and deserializing.
 */
public class ImageSerializer {
    /**
     * Deserialize an image from a String into a Bitmap.
     * @param input String format of the image
     * @return Bitmap format of the image
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static Bitmap base64StringToImg(String input)
    {
        try {
            byte[] decodedBytes = Base64.decode(input, Base64.NO_WRAP);
            return BitmapFactory.decodeByteArray(decodedBytes, 0,
                    decodedBytes.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Serialize an image from a Bitmap into a String.
     * @param image Bitmap format of the image
     * @return String format of the image
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static String imgToBase64String(Bitmap image)
    {
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
        int quality = 100;
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }
}
