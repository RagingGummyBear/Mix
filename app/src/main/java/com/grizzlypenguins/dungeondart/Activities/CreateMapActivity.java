package com.grizzlypenguins.dungeondart.Activities;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.grizzlypenguins.dungeondart.CameraControl;
import com.grizzlypenguins.dungeondart.LevelMap;
import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.R;
import com.grizzlypenguins.dungeondart.ScrollViewPackage.ListElementAdapter;
import com.grizzlypenguins.dungeondart.ScrollViewPackage.ListInput;
import com.grizzlypenguins.dungeondart.myFactory;

import java.util.ArrayList;

public class CreateMapActivity extends Activity {

    CameraControl cameraControl;
    LevelMap levelMap;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {


                render();
           if(shouldRun) timerHandler.postDelayed(this, 500);
        }
    };

    private int mapWidth=100;
    private int mapHeight=100;
    float cameraZoom;

    SurfaceView mapSurfaceView;
    ListView tileSelect;

    Button moveUp;
    Button moveLeft;
    Button moveRight;
    Button moveDown;

    ArrayList<ListInput> listViewTiles = new ArrayList<ListInput>();
    String selectedTileType;

    Canvas canvas;

    private boolean once = true;
    private boolean shouldRun = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_create_map);
        initialize();
        set_listeners();
        if(cameraControl.tiles[1][1]==null)
        Log.v("Creating map","The tiles are null");
        render();
        timerHandler.postDelayed(timerRunnable, 500);


    }

    void initialize()
    {

        mapSurfaceView = (SurfaceView) findViewById(R.id.mapSurfaceView);
        canvas =  mapSurfaceView.getHolder().lockCanvas();
        moveDown = (Button) findViewById(R.id.moveDown);
        moveLeft = (Button) findViewById(R.id.moveLleft);
        moveRight = (Button) findViewById(R.id.moveRight);
        moveUp = (Button) findViewById(R.id.moveUp);

        mapHeight = (int)getIntent().getSerializableExtra("mapHeight");
        mapWidth = (int)getIntent().getSerializableExtra("mapWidth");

        levelMap = myFactory.getInstance().newBlankMovableMap(mapWidth,mapHeight,"CoolTestingMap");

        //double scaleX, double scaleY,double Ts,MyPoint poz,int playerMovement
        float screenSize = mapSurfaceView.getWidth();
        if(screenSize>mapSurfaceView.getHeight())
            screenSize = mapSurfaceView.getHeight();
         cameraZoom =  (float)screenSize / (float)(myFactory.TILESIZE * myFactory.TILENUMBER);
        cameraControl = new CameraControl(cameraZoom,cameraZoom, myFactory.TILESIZE,new MyPoint((mapWidth/2)+10,(mapHeight/2)+10),1);
        cameraControl.tiles = levelMap.getShowingTiles(cameraControl.player_position);
        if(cameraControl.tiles == null)
            Log.v("Creating map "," The tiles are null into the camera controll");

        selectedTileType = "notmovabletile";
        //mapSurfaceView.getHolder().unlockCanvasAndPost(canvas);
        tileSelect = (ListView) findViewById(R.id.tileListView);
        listViewTiles.add(new ListInput("movabletile", 0, 0));
        listViewTiles.add(new ListInput( "notmovabletile",0,0));
        listViewTiles.add(new ListInput( "finishtile",0,0));
        listViewTiles.add(new ListInput( "starttile",0,0));
        listViewTiles.add(new ListInput( "monsterstarttile",0,0));
        listViewTiles.add(new ListInput( "poweruptile",0,0));
        listViewTiles.add(new ListInput( "traptile",0,0));
        listViewTiles.add(new ListInput( "powerupandtraptile",0,0));
        tileSelect.setAdapter(new ListElementAdapter(CreateMapActivity.this, listViewTiles));


    }

    void set_listeners()
    {

        moveDown.setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        moveDown();
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        //gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        moveUp.setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        moveUp();
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        //gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        moveRight.setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        moveRight();
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        //gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        moveLeft.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        moveLeft();
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        //gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        mapSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //String temp = String.format("Touch coordinates : " +String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));

                    // Log.v("ontouch",temp);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    String temp = String.format("Moving coordinates : x: " + (event.getX()) / cameraZoom + "  x:  " + (event.getY()));
                   // Log.v("ontouch", temp);
                    float x = event.getX() / cameraZoom;
                    float y = event.getY() / cameraZoom;
                    if (x > 0 && y > 0 && y < 288 && x < 288) {
                        drawTileAtLocation(x, y);
                        render();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                }
                return true;
            }
        });
    }



    void drawTileAtLocation(float x,float y)
    {
        if(selectedTileType== null) {
            Log.v("CreatingMap","SelectedTile is null");
            return;
        }
        x=x/myFactory.TILESIZE;
        y=y/myFactory.TILESIZE;
        int xPressed = cameraControl.tiles[(int)x][(int)y].getX();
        int yPressed = cameraControl.tiles[(int)x][(int)y].getY();
        //Log.v("CreateMap ", "DrawingTiles the coordinates are x: " + x + " y: " + y);
        //cameraControl.tiles[(int)x][(int)y] = myFactory.;
        levelMap.tiles[xPressed][yPressed] = myFactory.getInstance().getTileOfType(selectedTileType);
        levelMap.tiles[xPressed][yPressed].setX(xPressed);
        levelMap.tiles[xPressed][yPressed].setY(yPressed);

       // updateShowingTiles();
    }

    void updateShowingTiles()
    {
        cameraControl.tiles = levelMap.getShowingTiles(cameraControl.player_position);
    }
    void moveUp()
    {
        MyPoint temp = cameraControl.player_position;
        if(temp.y>10)
        {
            cameraControl.player_position.y--;
        }
        render();
    }
    void moveDown()
    {
        MyPoint temp = cameraControl.player_position;
        if(temp.y<mapHeight+10)
        {
            cameraControl.player_position.y++;
        }
        render();
    }
    void moveLeft()
    {
        MyPoint temp = cameraControl.player_position;
        if(temp.x>10)
        {
            cameraControl.player_position.x--;
        }
        render();
    }
    void moveRight()
    {
        MyPoint temp = cameraControl.player_position;
        if(temp.x<mapWidth+10)
        {
            cameraControl.player_position.x++;
        }
        render();
    }

    public void chooseTile(String s)
    {
        selectedTileType = s;
        Log.v("CreatingMap","You have selected tile type: "+s);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_map, menu);
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

    void render()
    {
        //Log.v("CreateMap","Rendering");
        canvas =  mapSurfaceView.getHolder().lockCanvas();
        if (canvas != null)   {
            if(once){
                float screenSize = mapSurfaceView.getWidth();
                if (screenSize > mapSurfaceView.getHeight())
                    screenSize = mapSurfaceView.getHeight();
                cameraZoom = (float) screenSize / (float) (myFactory.TILESIZE * myFactory.TILENUMBER);
            }
            updateShowingTiles();
            //Log.v("CreateMap","Can enter the if");
            canvas.scale(cameraZoom, cameraZoom, 1, 1);
            canvas.save();
            try {
                cameraControl.render(canvas);
            } catch (Exception e) {

                e.printStackTrace();
            }
            //mapSurfaceView.draw(canvas);
            //canvas.restore();
            shouldRun = false;
          mapSurfaceView.getHolder().unlockCanvasAndPost(canvas);
        }
    }
}
