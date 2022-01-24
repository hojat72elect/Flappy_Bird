package ca.sudbury.hojat.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Hojat Ghasemi at 2022-01-23
 * contact the author at "https://github.com/hojat72elect"
 */
public class Pipe extends BaseObject {
    public static int speed;

    public Pipe(float x, float y, int width, int height) {
        super(x, y, width, height);
        speed = 10 * Constants.SCREEN_WIDTH / 1080;//the speed of the game is 10.
    }

    /**
     * drawing the pipes in the game world.
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        this.x -= speed;//the pipes go from right to left.
        canvas.drawBitmap(this.bm, this.x, this.y, null);
    }

    /**
     * pipes have random heights.
     */
    public void randomY() {
        //this method is a little bit creepy; it has side effects.
        Random r = new Random();
        this.y = r.nextInt((this.height / 4) + 1) - (float) this.height / 4;
    }

    @Override
    public void setBm(Bitmap bm) {
        this.bm = Bitmap.createScaledBitmap(bm, width, height, true);
    }
}
