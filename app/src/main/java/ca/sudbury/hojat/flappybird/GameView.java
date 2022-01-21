package ca.sudbury.hojat.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private Bird bird;

    /**
     * This View is what we draw on Screen when the game starts.
     *
     * @param context
     * @param attrs
     */
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //creating the bird object.
        bird = new Bird();

        //setting the width and height of the bird.
        bird.setWidth(100 * Constants.SCREEN_WIDTH / 1080);
        bird.setHeight(100 * Constants.SCREEN_HEIGHT / 1920);

        //setting the coordinates of the bird at the start of the game.
        bird.setX((float) 100 * Constants.SCREEN_WIDTH / 1080);
        bird.setY((float) (Constants.SCREEN_HEIGHT / 2 - bird.getHeight() / 2));

        //the 2 pics that show different states of bird will be loaded into an array of bitmaps and fed to Bird object.
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird2));
        bird.setArrBms(arrBms);


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        bird.draw(canvas);//initial drawing of the bird on screen.
    }
}
