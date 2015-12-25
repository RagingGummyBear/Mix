package com.grizzlypenguins.dungeondart.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.grizzlypenguins.dungeondart.R;

public class How_to_Play extends AppCompatActivity {

    public Button Next,Next1,Back,Back1,Back2;
    public TextView TextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to__play);
        int alpha = 0;
        TextView1 = (TextView)findViewById(R.id.TextView1);
        TextView1.setBackgroundColor(Color.parseColor("#E6b8bab8"));
        Next = (Button) findViewById(R.id.Next);
        Next1 = (Button) findViewById(R.id.Next1);
        Back = (Button) findViewById(R.id.back);
        Back1 = (Button) findViewById(R.id.back1);
        Back2 = (Button) findViewById(R.id.back2);
        Next1.setVisibility(View.INVISIBLE);
        Back1.setVisibility(View.INVISIBLE);
        Back2.setVisibility(View.INVISIBLE);
    }
    public void changeText(View v){
        Next1.setVisibility(View.VISIBLE);
        Back1.setVisibility(View.VISIBLE);
        Next.setVisibility(View.INVISIBLE);
        Back.setVisibility(View.INVISIBLE);
        Back2.setVisibility(View.INVISIBLE);
        TextView1.setText("Внимавајте на вашиот факел, користете го разумно со протресување на телефонот");
    }
    public void changeText1(View v){
        Next1.setVisibility(View.INVISIBLE);
        Next.setVisibility(View.INVISIBLE);
        Back.setVisibility(View.INVISIBLE);
        Back1.setVisibility(View.INVISIBLE);
        Back2.setVisibility(View.VISIBLE);
        TextView1.setText("Собирајте помагала за зголемување на брзина на херојот и времето на факелот");
    }
    public void changeText2(View v){
        Back2.setVisibility(View.INVISIBLE);
        Next1.setVisibility(View.INVISIBLE);
        Next.setVisibility(View.VISIBLE);
        Back.setVisibility(View.VISIBLE);
        Back1.setVisibility(View.INVISIBLE);
        TextView1.setText("Целта е едноставна двежете го вашиот херој низ мапата се додека не стигнете до излезот");
    }
    public void backToMain(View v){

        Intent ab = new Intent(How_to_Play.this, MainMenu.class);
        startActivity(ab);
    }
}
