package ca.sudbury.hojat.flappybird;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private Bird bird;
    private Handler handler;
    private Runnable r;
    private ArrayList<Pipe> arrPipes;
    private int sumPipes;
    private int distance;//the distance between pipes, the gap that the bird flies through
    private int score, bestScore = 0;
    private boolean start;
    private Context context;
    private int soundJump; // each sound in the game has an int ID (which we use to play or unload the sound).
    private float volume;
    private boolean loadedSound;
    private SoundPool soundPool;

    /**
     * This View is what we draw on Screen when the game starts.
     *
     * @param context
     * @param attrs
     */
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //Loading the highest score at the beginning of the game
        SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if (sp != null) {
            bestScore = sp.getInt("bestscore", 0);
        }

        score = 0;
        start = false;
        initBird();
        initPipe();
        //boilerplate code for the handler
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();/*Invalidate the whole view. we need to invalidate this view
                                before calling the draw() method again.*/
            }
        };
        //loading the sounds
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME) // the usage of this sound is for game
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
        this.soundPool = builder.build();
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadedSound = true; // After the sound is completely loaded
            }
        });
        // Load the sound into the "soundJump" object
        soundJump = this.soundPool.load(context, R.raw.jump_02, 1);
    }

    private void initPipe() {

        sumPipes = 6;
        distance = 300 * Constants.SCREEN_HEIGHT / 1920;
        arrPipes = new ArrayList<>();
        for (int i = 0; i < sumPipes; i++) {
            if (i < sumPipes / 2) {
                this.arrPipes.add(new Pipe(Constants.SCREEN_WIDTH + i * ((Constants.SCREEN_WIDTH + 200.0f * Constants.SCREEN_WIDTH / 1080) / (sumPipes / 2.0f)), 0.0f, 200 * Constants.SCREEN_WIDTH / 1080, Constants.SCREEN_HEIGHT / 2));
                this.arrPipes.get(this.arrPipes.size() - 1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe2));
                this.arrPipes.get(this.arrPipes.size() - 1).randomY();
            } else {
                this.arrPipes.add(new Pipe(
                        this.arrPipes.get(i - sumPipes / 2).getX(),
                        this.arrPipes.get(i - sumPipes / 2).getY() + this.arrPipes.get(i - sumPipes / 2).getHeight() + this.distance,
                        Constants.SCREEN_WIDTH / 1080,
                        200 * Constants.SCREEN_HEIGHT / 2));
                this.arrPipes.get(this.arrPipes.size() - 1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe1));

            }
        }
    }

    private void initBird() {
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
        //if(start) then we will draw the bird and the rest of the game.
        if (start) {
            bird.draw(canvas);//initial drawing of the bird on screen.
            for (int i = 0; i < sumPipes; i++) {
                //if the bird collides with pipes, then show game over:
                if (bird.getRect().intersect(arrPipes.get(i).getRect()) || bird.getY() - bird.getHeight() < 0 || bird.getY() > Constants.SCREEN_HEIGHT) {
                    Pipe.speed = 0;
                    MainActivity.txt_score_over.setText(MainActivity.txt_score.getText());
                    MainActivity.txt_best_score.setText("best: " + bestScore);
                    MainActivity.txt_score.setVisibility(INVISIBLE);
                    MainActivity.rl_game_over.setVisibility(VISIBLE);
                }
                if (this.bird.getX() + this.bird.getWidth() > arrPipes.get(i).getX() + arrPipes.get(i).getWidth() / 2f
                        && this.bird.getX() + this.bird.getWidth() <= arrPipes.get(i).getX() + arrPipes.get(i).getWidth() / 2.0f + Pipe.speed
                        && i < sumPipes / 2) {
                    score++;
                    if (score > bestScore) {
                        //the highest score will be saved in a SharedPreferences;
                        bestScore = score;
                        SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("bestscore", bestScore);
                        editor.apply();
                    }

                    MainActivity.txt_score.setText("" + score);
                }
                if (this.arrPipes.get(i).getX() < -arrPipes.get(i).getWidth()) {
                    this.arrPipes.get(i).setX(Constants.SCREEN_WIDTH);
                    if (i < sumPipes / 2) {
                        arrPipes.get(i).randomY();
                    } else {
                        arrPipes.get(i).setY(this.arrPipes.get(i - sumPipes / 2).getY() + this.arrPipes.get(i - sumPipes / 2).getHeight() + this.distance);
                    }
                }
                this.arrPipes.get(i).draw(canvas);
            }
        } else {
            //the user has not clicked on the start button yet:
            if (bird.getY() > Constants.SCREEN_HEIGHT / 2f) {
                bird.setDrop(-15 * Constants.SCREEN_HEIGHT / 1920f);
            }
            bird.draw(canvas);
        }
        handler.postDelayed(r, 10);//the runnable will be called each 10 milliseconds.
    }

    /**
     * managing touch events on the game's screen.
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bird.setDrop(-15); // the bird goes up.
            if (loadedSound) { //play the jump sound.
                this.soundPool.play(this.soundJump, 0.5f, 0.5f, 1, 0, 1f);
                // the streamID returned by this method can be used for further controlling the sound but I don't need it for now.
            }
        }
        return true;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * Resetting the game from a game over situation.
     */
    public void reset() {
        MainActivity.txt_score.setText("0");
        score = 0;
        initPipe();
        initBird();
    }
}