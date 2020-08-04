package com.example.myquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myquiz.R;

public class ScoreActivity extends AppCompatActivity {

    private Button btnDone;
    private TextView txtTitleCategory, txtTitleSets, txtScroce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        AnhXa();
        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null) {*/
            String titleCategory = getIntent().getStringExtra("CAT");
            txtTitleCategory.setText(titleCategory);
            String titleSets = getIntent().getStringExtra("SET");
            txtTitleSets.setText(titleSets);
        //}
        String score = getIntent().getStringExtra("SCORE");
        txtScroce.setText(score);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScoreActivity.this, SplashActivity.class));
                ScoreActivity.this.finish();
            }
        });
    }

    private void AnhXa() {
        btnDone = (Button) findViewById(R.id.buttonDone);
        txtScroce = (TextView) findViewById(R.id.textViewScore);
        txtTitleCategory = (TextView) findViewById(R.id.textViewTitleCategory);
        txtTitleSets = (TextView) findViewById(R.id.textViewTitleSets);

    }
}
