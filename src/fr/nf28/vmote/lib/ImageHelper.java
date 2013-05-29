package fr.nf28.vmote.lib;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageHelper {
	public static boolean saveBitmap(Bitmap bmp, Context cxt, String filename){
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    /*--- you can select your preferred CompressFormat and quality. 
	     * I'm going to use JPEG and 100% quality ---*/
	    bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		
		FileOutputStream fos = null;
		
	    try {
	        fos = cxt.openFileOutput(filename, Context.MODE_PRIVATE);
	        fos.write(bytes.toByteArray());
	        fos.close();
	        
	        return true;
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
