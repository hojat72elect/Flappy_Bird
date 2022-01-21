package ca.sudbury.hojat.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Hojat Ghasemi at 2022-01-19
 * contact the author at "https://github.com/hojat72elect"
 */


public class Bird extends BaseObject {
    //an array of all the bitmaps that show the bird in various situations.
    private ArrayList<Bitmap> arrBms = new ArrayList<>();

    public Bird() {
    }

    //drawing the bird's Bitmap on the Canvas.
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.getBm(), this.x, this.y, null);
    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    /**
     * The bitmaps that are set into this Bird object should be re-scaled according to the width and height of the bird object for each screen (remember that width and height of the Bird object are set according to the device's screen dimensions).
     *
     * @param arrBms
     */
    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;
        for (int i = 0; i < arrBms.size(); i++) {
            this.arrBms.set(i, Bitmap.createScaledBitmap(this.arrBms.get(i), this.width, this.height, true));//rescaling a bitmap in android.
        }
    }

    /**
     * implementing Bitmap getter of its parent(only returns a single bitmap not an array of them).
     *
     * @return
     */
    @Override
    public Bitmap getBm() {
        return this.getArrBms().get(0);
    }
}
