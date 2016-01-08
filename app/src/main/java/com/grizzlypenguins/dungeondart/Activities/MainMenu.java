package com.grizzlypenguins.dungeondart.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grizzlypenguins.dungeondart.Activities.uiScalingClasses.ScaleCreateMapActivity;
import com.grizzlypenguins.dungeondart.Activities.uiScalingClasses.ScaleGamePlayActivity;
import com.grizzlypenguins.dungeondart.Difficulty;
import com.grizzlypenguins.dungeondart.Level;
import com.grizzlypenguins.dungeondart.LevelMap;
import com.grizzlypenguins.dungeondart.PlayerScoring;
import com.grizzlypenguins.dungeondart.R;
import com.grizzlypenguins.dungeondart.ScrollViewPackage.ListElementAdapter;
import com.grizzlypenguins.dungeondart.ScrollViewPackage.ListInput;
import com.grizzlypenguins.dungeondart.myFactory;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainMenu extends Activity {

    RelativeLayout mainScreen;
    FrameLayout createGameScreen;
    RelativeLayout scoreScreen;
    FrameLayout createMapScreen;

    ListView mapView;
    ArrayList<ListInput> mapList = new ArrayList<ListInput>();

    Button newGame;
    Button exitGame;
    Button startGame;
    Button back;
    Button okScore,howToPlay,google;
    Button backCreateMap;
    Button nextCreateMap;

    TextView timePlayed;
    TextView scoredPoints;

    RatingBar ratingBar;
    Level startNewLevel;
    Window window;

    LevelMap pickedMapLevel;
    MainMenuSettings mainMenuSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initialize();
        set_listeners();

        //TEST MAPS
        //should remove these
        LevelMap temp = myFactory.getInstance().test_map_1();
        mapList.add(new ListInput(temp.getMapName(), 0, temp.getId()));
        temp = myFactory.getInstance().test_map_2(200);
        mapList.add(new ListInput(temp.getMapName(), 0, temp.getId()));
        temp = myFactory.getInstance().test_map_3();
        mapList.add(new ListInput(temp.getMapName(), 0, temp.getId()));
        temp = myFactory.getInstance().test_map_4();
        ListInput temp2 = new ListInput(temp.getMapName(), 0, temp.getId());
        this.add_map(temp2);
        window = this.getWindow();

    }
    public void GoogleLoging(View v){
        Intent ab = new Intent(MainMenu.this,GoogleLogin.class);
        startActivity(ab);
    }
    public void promeni(View v){
        Intent ab = new Intent(MainMenu.this,How_to_Play.class);
        startActivity(ab);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pickedMapLevel = null;
    }

    public void createMapButtonPressed(View v){
                mainMenuSettings.mainmenu = false;
                mainMenuSettings.scoreScreen = false;
                mainMenuSettings.newGameScree = false;
                mainMenuSettings.createMapScreen = true;
                toggle_layout();
    }

    private void initialize() {



        mainScreen = (RelativeLayout)findViewById(R.id.firstScreen);
        createGameScreen = (FrameLayout)findViewById(R.id.createGameScreen);
        scoreScreen = (RelativeLayout)findViewById(R.id.scoreScreenLayout);
        createMapScreen = (FrameLayout) findViewById(R.id.createMapScreen);

        newGame = (Button)findViewById(R.id.newGameButton);

        exitGame = (Button)findViewById(R.id.startGameButton);
        startGame = (Button) findViewById(R.id.startGameButton);
        back = (Button) findViewById(R.id.backButton);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        okScore = (Button) findViewById(R.id.scoreOkButton);
        howToPlay = (Button)findViewById(R.id.How_to_Play);
        google = (Button)findViewById(R.id.google);
        backCreateMap = (Button) findViewById(R.id.createMapBack);
        nextCreateMap = (Button)findViewById(R.id.mapCreateNext);



        timePlayed = (TextView) findViewById(R.id.timeFinished);
        scoredPoints = (TextView) findViewById(R.id.scoreText);

        mapView = (ListView) findViewById(R.id.listView);

        mainMenuSettings = (MainMenuSettings) getIntent().getSerializableExtra("mainMenuSettings");
        if(mainMenuSettings!=null)
        {
            toggle_layout();
        }
        else
        {
            mainMenuSettings = new MainMenuSettings();
            mainMenuSettings.mainmenu = true;
            toggle_layout();
        }
        PlayerScoring playerScoring = (PlayerScoring) getIntent().getSerializableExtra("scoring");
        if(playerScoring!=null)
        {
            timePlayed.setText(playerScoring.getTime());
            scoredPoints.setText(String.format(playerScoring.score+""));

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    void toggle_layout()
    {

        if(mainMenuSettings.mainmenu)
        {

            mainScreen.setVisibility(View.VISIBLE);
            mainScreen.setClickable(true);
        }
        else
        {

            mainScreen.setClickable(false);
            mainScreen.setVisibility(View.INVISIBLE);
        }
        if(mainMenuSettings.newGameScree)
        {

            createGameScreen.setVisibility(View.VISIBLE);
            createGameScreen.setClickable(true);
        }
        else
        {
            createGameScreen.setClickable(false);
            createGameScreen.setVisibility(View.INVISIBLE);

        }
        if(mainMenuSettings.createMapScreen)
        {
            createMapScreen.setVisibility(View.VISIBLE);
            createMapScreen.setClickable(true);

        }
        else
        {
            createMapScreen.setClickable(false);
            createMapScreen.setVisibility(View.INVISIBLE);
        }
        if(mainMenuSettings.scoreScreen)
        {

            scoreScreen.setVisibility(View.VISIBLE);
            scoreScreen.setClickable(true);
        }
        else
        {
            scoreScreen.setClickable(false);
            scoreScreen.setVisibility(View.INVISIBLE);

        }

    }

    void set_listeners()
    {

        backCreateMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                mainMenuSettings.mainmenu = true;
                mainMenuSettings.createMapScreen = false;
                mainMenuSettings.newGameScree = false;
                mainMenuSettings.scoreScreen = false;
                toggle_layout();
            }
        });

        nextCreateMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                initializeBitmaps();
                mainMenuSettings.mainmenu = true;
                mainMenuSettings.createMapScreen = false;
                mainMenuSettings.newGameScree = false;
                mainMenuSettings.scoreScreen = false;
                toggle_layout();

                Intent myIntent = new Intent(MainMenu.this, CreateMapActivity.class);
                EditText temp = (EditText)findViewById(R.id.mapWidth);
                int temp2 = Integer.parseInt(temp.getText().toString());
                myIntent.putExtra("mapWidth",temp2);
                temp = (EditText)findViewById(R.id.mapHeight);
                temp2 = Integer.parseInt(temp.getText().toString());
                myIntent.putExtra("mapHeight",temp2);

                ScaleCreateMapActivity scaleCreateMapActivity= new ScaleCreateMapActivity();
                scaleCreateMapActivity.setAll(getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight());

                myIntent.putExtra("ScaleCreateMapActivity", scaleCreateMapActivity);



                startActivity(myIntent);
            }
        });


        newGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                mainMenuSettings.mainmenu = false;
                mainMenuSettings.createMapScreen = false;
                mainMenuSettings.newGameScree = true;
                mainMenuSettings.scoreScreen = false;
                toggle_layout();
            }
        });

        exitGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                finish();
                System.exit(0);

            }
        });
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(pickedMapLevel == null)
                {
                    Toast.makeText(MainMenu.this,"You need to choose level before you start",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ratingBar.getRating()==0)
                {
                    Toast.makeText(MainMenu.this,"You need to choose difficulty before you start",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent myIntent = new Intent(MainMenu.this,
                        GamePlayActivity.class);
                Difficulty dif = new Difficulty((int) ratingBar.getRating());

                startNewLevel = new Level(dif, pickedMapLevel,window.getDecorView().getWidth(),window.getDecorView().getHeight());
                startNewLevel.start();

                initializeBitmaps();



                ScaleGamePlayActivity scaleGamePlayActivity= new ScaleGamePlayActivity();
                scaleGamePlayActivity.setAll(getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight());

                myIntent.putExtra("ScaleGamePlayActivity", scaleGamePlayActivity);

                while(startNewLevel.running){
                }

                myIntent.putExtra("PackedLevel", startNewLevel.packedLevel);

                startActivity(myIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {

                        mainMenuSettings.mainmenu = true;
                        mainMenuSettings.createMapScreen = false;
                        mainMenuSettings.newGameScree = false;
                        mainMenuSettings.scoreScreen = false;
                        toggle_layout();

                    }
        });

        okScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainMenuSettings.mainmenu = true;
                mainMenuSettings.createMapScreen = false;
                mainMenuSettings.newGameScree = false;
                mainMenuSettings.scoreScreen = false;
                toggle_layout();
            }
        });

    }
    private void initializeBitmaps()
            {

                if(getWindow().getDecorView().getHeight() < getWindow().getDecorView().getWidth())
                myFactory.getInstance().set_Size(getWindow().getDecorView().getHeight());
                else
                    myFactory.getInstance().set_Size(getWindow().getDecorView().getWidth());

                myFactory.getInstance().arrowL = BitmapFactory.decodeResource(getResources(),R.drawable.arrowl);
                myFactory.getInstance().arrowR = BitmapFactory.decodeResource(getResources(),R.drawable.arrowr);
                myFactory.getInstance().arrowU = BitmapFactory.decodeResource(getResources(),R.drawable.arrowu);
                myFactory.getInstance().arrowD = BitmapFactory.decodeResource(getResources(),R.drawable.arrowd);


                myFactory.getInstance().TileNotMovable = BitmapFactory.decodeResource(getResources(), R.drawable.notmovabletile);
                myFactory.getInstance().TileMovable = BitmapFactory.decodeResource(getResources(), R.drawable.movabletile);
                myFactory.getInstance().TorchLight = BitmapFactory.decodeResource(getResources(),R.drawable.beginingfog);
                myFactory.getInstance().TileStart = BitmapFactory.decodeResource(getResources(),R.drawable.choosenstart);
                myFactory.getInstance().TileFinish = BitmapFactory.decodeResource(getResources(),R.drawable.finishworking);
                myFactory.getInstance().TileNFinish = BitmapFactory.decodeResource(getResources(),R.drawable.finishnotworking);

                myFactory.getInstance().EvilMonster = BitmapFactory.decodeResource(getResources(),R.drawable.evilmonster);
                myFactory.getInstance().Character = BitmapFactory.decodeResource(getResources(),R.drawable.character);

                myFactory.getInstance().PowerUpR = BitmapFactory.decodeResource(getResources(),R.drawable.powerupr);
                myFactory.getInstance().PowerUpB = BitmapFactory.decodeResource(getResources(),R.drawable.powerupb);
                myFactory.getInstance().PowerUpG = BitmapFactory.decodeResource(getResources(),R.drawable.powerupg);
                myFactory.getInstance().PowerUpP = BitmapFactory.decodeResource(getResources(),R.drawable.powerupp);
                myFactory.getInstance().PowerUpY = BitmapFactory.decodeResource(getResources(),R.drawable.powerupy);

                myFactory.getInstance().TrapR = BitmapFactory.decodeResource(getResources(),R.drawable.trapr);
                myFactory.getInstance().TrapY = BitmapFactory.decodeResource(getResources(),R.drawable.trapy);
                myFactory.getInstance().TrapB = BitmapFactory.decodeResource(getResources(),R.drawable.trapb);

                myFactory.getInstance().mainCharacterPictures.put("standStillD",BitmapFactory.decodeResource(getResources(),R.drawable.standstilld));
                myFactory.getInstance().mainCharacterPictures.put("standStillU",BitmapFactory.decodeResource(getResources(),R.drawable.standstillu));
                myFactory.getInstance().mainCharacterPictures.put("standStillR",BitmapFactory.decodeResource(getResources(),R.drawable.standstillr));
                myFactory.getInstance().mainCharacterPictures.put("standStillL",BitmapFactory.decodeResource(getResources(),R.drawable.standstilll));

                myFactory.getInstance().mainCharacterPictures.put("walkPicture1D",BitmapFactory.decodeResource(getResources(),R.drawable.walk1d));
                myFactory.getInstance().mainCharacterPictures.put("walkPicture1U",BitmapFactory.decodeResource(getResources(),R.drawable.walk1u));
                myFactory.getInstance().mainCharacterPictures.put("walkPicture1R",BitmapFactory.decodeResource(getResources(),R.drawable.walk1r));
                myFactory.getInstance().mainCharacterPictures.put("walkPicture1L",BitmapFactory.decodeResource(getResources(),R.drawable.walk1l));

                myFactory.getInstance().mainCharacterPictures.put("walkPicture2D",BitmapFactory.decodeResource(getResources(),R.drawable.walk2d));
                myFactory.getInstance().mainCharacterPictures.put("walkPicture2U",BitmapFactory.decodeResource(getResources(),R.drawable.walk2u));
                myFactory.getInstance().mainCharacterPictures.put("walkPicture2R",BitmapFactory.decodeResource(getResources(),R.drawable.walk2r));
                myFactory.getInstance().mainCharacterPictures.put("walkPicture2L",BitmapFactory.decodeResource(getResources(),R.drawable.walk2l));

                myFactory.getInstance().mainCharacterPictures.put("walkPicture3D",BitmapFactory.decodeResource(getResources(),R.drawable.walk3d));
                myFactory.getInstance().mainCharacterPictures.put("walkPicture3U",BitmapFactory.decodeResource(getResources(),R.drawable.walk3u));
                myFactory.getInstance().mainCharacterPictures.put("walkPicture3R",BitmapFactory.decodeResource(getResources(),R.drawable.walk3r));
                myFactory.getInstance().mainCharacterPictures.put("walkPicture3L",BitmapFactory.decodeResource(getResources(),R.drawable.walk3l));

                myFactory.getInstance().evilMonster.put("standStillD",BitmapFactory.decodeResource(getResources(),R.drawable.emstandstilld));
                myFactory.getInstance().evilMonster.put("standStillU",BitmapFactory.decodeResource(getResources(),R.drawable.emstandstillu));
                myFactory.getInstance().evilMonster.put("standStillR",BitmapFactory.decodeResource(getResources(),R.drawable.emstandstillr));
                myFactory.getInstance().evilMonster.put("standStillL",BitmapFactory.decodeResource(getResources(),R.drawable.emstandstilll));

                myFactory.getInstance().evilMonster.put("walkPicture1D",BitmapFactory.decodeResource(getResources(),R.drawable.emwalk1d));
                myFactory.getInstance().evilMonster.put("walkPicture1U",BitmapFactory.decodeResource(getResources(),R.drawable.emwalk1u));
                myFactory.getInstance().evilMonster.put("walkPicture1R",BitmapFactory.decodeResource(getResources(),R.drawable.emwalk1r));
                myFactory.getInstance().evilMonster.put("walkPicture1L",BitmapFactory.decodeResource(getResources(),R.drawable.emwalk1l));

                myFactory.getInstance().evilMonster.put("walkPicture2D",BitmapFactory.decodeResource(getResources(),R.drawable.emwalk2d));
                myFactory.getInstance().evilMonster.put("walkPicture2U",BitmapFactory.decodeResource(getResources(),R.drawable.emwalk2u));
                myFactory.getInstance().evilMonster.put("walkPicture2R",BitmapFactory.decodeResource(getResources(),R.drawable.emwalk2r));
                myFactory.getInstance().evilMonster.put("walkPicture2L",BitmapFactory.decodeResource(getResources(),R.drawable.emwalk2l));




                myFactory.getInstance().resize();

            }

    public void pickLevelMap(String name)
    {
        //id = mapList.get(id).get_ID();
        switch (name)
        {
            case "a":
            {
            pickedMapLevel = myFactory.getInstance().test_map_1();
                break;
            }
            case "s":
            {
                pickedMapLevel = myFactory.getInstance().test_map_2(200);
                break;
            }
            case "TestMap3":
            {
                pickedMapLevel = myFactory.getInstance().test_map_3();
                break;
            }
            case "test_map_4":
            {
                pickedMapLevel = myFactory.getInstance().test_map_4();
                break;
            }
            default:
            {
                pickedMapLevel=null;
                break;
            }
        }
    }

    public void addAllMaps(LinkedList<ListInput> listInputs)
    {
        mapList.clear();
        for(int i = 0;i<listInputs.size();i++)
        {
            mapList.add(listInputs.get(i));

        }
        mapView.setAdapter(new ListElementAdapter(MainMenu.this, mapList));
    }
    public void add_map(ListInput listInput)
    {
        mapList.add(listInput);
        mapView.setAdapter(new ListElementAdapter(MainMenu.this, mapList));
    }

}
