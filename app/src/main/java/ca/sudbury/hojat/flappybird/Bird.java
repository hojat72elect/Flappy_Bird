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

    //drawing the bird on the screen
    public void draw(Canvas canvas) {

    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;
    }
}
