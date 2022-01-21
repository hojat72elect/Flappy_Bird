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

    private int count//the number of iterations.
            , vFlap//if (count == vFlap) then Bird's Bitmap will be changed.
            , idCurrentBitmap//id of the Bitmap currently shown on screen
            ;

    public Bird() {
        this.count = 0;
        this.vFlap = 5;
        this.idCurrentBitmap = 0;

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
        //each time Bitmap is refreshed, we count it.
        count++;
        if (this.count == this.vFlap) {
            //the bitmap needs to be updated
            for (int i = 0; i < arrBms.size(); i++) {
                if (i == arrBms.size() - 1) {
                    this.idCurrentBitmap = 0;
                    break;
                } else if (this.idCurrentBitmap == i) {
                    idCurrentBitmap = i + 1;
                    break;
                }
            }
            count = 0;
        }

        return this.arrBms.get(idCurrentBitmap);
    }
}
