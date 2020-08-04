package com.example.myquiz.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    private TextView txtAppName;
    private Button btnStartSplash;
    public static List<String> catList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtAppName = (TextView) findViewById(R.id.textViewAppName);

        btnStartSplash = (Button) findViewById(R.id.buttonStartSplash);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.birdsofparadise);
        txtAppName.setTypeface(typeface);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_splash_screen);
        txtAppName.setAnimation(anim);


        firestore = FirebaseFirestore.getInstance();
        loadData();

        btnStartSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, CategoryActivity.class));
                SplashActivity.this.finish();
            }
        });

    }

    private void loadData() {
        catList.clear();
        firestore.collection("QUIZ").document("Categories").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        long count = (long) doc.get("COUNT");
                        for (int i = 1; i <= count; i++) {
                            String catName = doc.getString("CAT" + i);
                            catList.add(catName);
                        }
                    }
                    else{
                        Toast.makeText(SplashActivity.this, "No Categoty Document Exists!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(SplashActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
