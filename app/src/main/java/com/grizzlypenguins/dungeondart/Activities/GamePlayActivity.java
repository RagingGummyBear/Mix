package com.grizzlypenguins.dungeondart.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.formats.NativeContentAdView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.grizzlypenguins.dungeondart.Activities.uiScalingClasses.ScaleGamePlayActivity;
import com.grizzlypenguins.dungeondart.PackedLevel;
import com.grizzlypenguins.dungeondart.R;
import com.grizzlypenguins.dungeondart.myFactory;

public class GamePlayActivity extends Activity implements SensorEventListener {


    private SensorManager sensorManager;
    private long lastUpdate;
    ScaleGamePlayActivity scaleGamePlayActivity;

    Button move_up;
    Button move_down;
    Button move_left;
    Button move_right;

    PackedLevel level ;
    GamePanel gamePanel;

    Handler timerHandler = new Handler();

    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {

            if(gamePanel.gameFinished)
                change_Activity();
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_play);
        Log.v("GamePlayActivity ", "    Started ");
        initialize();
        set_listeners();
        resizeLayouts();

        timerHandler.postDelayed(timerRunnable, 500);
        Log.v("GamePlayActivity ", "    DoneInitialize ");


        //System.out.println(level.cameraControl.player_position.x + " "+ level.difficulty.starNumber + " " +level.mainCharacter.speed + "  "+ level.levelMap.tileNumber );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void initialize()
    {
        level = (PackedLevel) getIntent().getSerializableExtra("PackedLevel");
        scaleGamePlayActivity = (ScaleGamePlayActivity) getIntent().getSerializableExtra("ScaleGamePlayActivity");

        gamePanel = (GamePanel) findViewById(R.id.GamePanel);

        move_up = (Button)findViewById(R.id.moveUp);
        move_down = (Button)findViewById(R.id.moveDown);
        move_left = (Button)findViewById(R.id.moveLeft);
        move_right = (Button)findViewById(R.id.moveRight);


        gamePanel.level = level;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

    }
    void resizeLayouts()
    {

        RelativeLayout.LayoutParams layout;
        /*
        move_left.setHeight((int) (getWindow().getDecorView().getHeight()*0.05));
        move_left.setWidth((int) (getWindow().getDecorView().getWidth()*0.05));
        */
        layout = (RelativeLayout.LayoutParams) move_left.getLayoutParams();
        move_left.getLayoutParams().width = scaleGamePlayActivity.middleButtonWidth;   //(int) (getResources().getDisplayMetrics().density*(getWindow().getDecorView().getWidth()*0.05));
        move_left.getLayoutParams().height = scaleGamePlayActivity.middleButtonHeight; //(int)  (getResources().getDisplayMetrics().density*(getWindow().getDecorView().getHeight()*0.02));
        Log.v("GamePlayActivity","width: " + scaleGamePlayActivity.middleButtonWidth+" height: " + scaleGamePlayActivity.middleButtonHeight);

        // layout.height = (int) (getWindow().getDecorView().getHeight()*0.2);
       // layout.width = (int) (getWindow().getDecorView().getWidth()*0.2);
        //move_left.setLayoutParams(new RelativeLayout.LayoutParams(layout));

        move_up.getLayoutParams().width = scaleGamePlayActivity.notMiddleButtonWidth;
        move_up.getLayoutParams().height = scaleGamePlayActivity.notMiddleButtonHeight;

        move_down.getLayoutParams().width = scaleGamePlayActivity.notMiddleButtonWidth;
        move_down.getLayoutParams().height = scaleGamePlayActivity.notMiddleButtonHeight;

        move_right.getLayoutParams().width = scaleGamePlayActivity.middleButtonWidth;
        move_right.getLayoutParams().height = scaleGamePlayActivity.middleButtonHeight;

        gamePanel.getLayoutParams().width = scaleGamePlayActivity.GamePanel;
        gamePanel.getLayoutParams().height = scaleGamePlayActivity.GamePanel;

        ((RelativeLayout.LayoutParams) move_left.getLayoutParams()).setMargins(scaleGamePlayActivity.leftButtonLeftMargin , scaleGamePlayActivity.leftButtonTopMargin,0,0);
        ((RelativeLayout.LayoutParams) move_right.getLayoutParams()).setMargins(scaleGamePlayActivity.rightButtonLeftMargin,0,0,0);
        ((RelativeLayout.LayoutParams) move_down.getLayoutParams()).setMargins(scaleGamePlayActivity.downButtonLeftMargin,0,0,0);
        ((RelativeLayout.LayoutParams) move_up.getLayoutParams()).setMargins(0,0,0,0);

        if(android.os.Build.VERSION.SDK_INT < 16) {
            move_left.setBackgroundDrawable(new BitmapDrawable(getResources(), myFactory.getInstance().arrowL));
            move_up.setBackgroundDrawable(new BitmapDrawable(getResources(), myFactory.getInstance().arrowR));
            move_down.setBackgroundDrawable(new BitmapDrawable(getResources(), myFactory.getInstance().arrowD));
            move_right.setBackgroundDrawable(new BitmapDrawable(getResources(), myFactory.getInstance().arrowU));
        }
        else {
            move_left.setBackground(new BitmapDrawable(getResources(),myFactory.getInstance().arrowL));
            move_up.setBackground(new BitmapDrawable(getResources(),myFactory.getInstance().arrowU));
            move_down.setBackground(new BitmapDrawable(getResources(),myFactory.getInstance().arrowD));
            move_right.setBackground(new BitmapDrawable(getResources(),myFactory.getInstance().arrowR));

        }



        // PackedLevel level ;
       // GamePanel gamePanel;
    }
    void set_listeners()
    {

        move_up.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        gamePanel.pressedButton("up");
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        move_down.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        gamePanel.pressedButton("down");
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        move_left.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               // Log.v("GamePlayActivity", "moveLeft button pressed");

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        gamePanel.pressedButton("left");
                     //   Log.v("GamePlayActivity", "moveLeft button pressed");
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });
        move_right.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        gamePanel.pressedButton("right");
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            getAccelerometer(event);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y+z*z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 3) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            //Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT).show();
            gamePanel.shake_shake();
        }
    }

    /**
     * Mora da se popolnat za da imame oporavuvanje od pad
     * t.e. da se stavi vo bundle packedLevel
     */


    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        sensorManager.unregisterListener(this);
        gamePanel.stop();
    }

    void change_Activity()
    {

        timerHandler.removeCallbacks(timerRunnable);
        Intent myIntent = new Intent(GamePlayActivity.this,
                MainMenu.class);
        myIntent.putExtra("scoring", this.gamePanel.level.playerScoring);
        MainMenuSettings mainMenuSettings = new MainMenuSettings();
        mainMenuSettings.scoreScreen = true;
        myIntent.putExtra("mainMenuSettings", mainMenuSettings);
        startActivity(myIntent);
    }



}

