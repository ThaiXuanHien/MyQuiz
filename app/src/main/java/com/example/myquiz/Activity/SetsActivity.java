package com.example.myquiz.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.myquiz.Adapter.SetsAdapter;
import com.example.myquiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.myquiz.Activity.SplashActivity.catList;
import static com.example.myquiz.Activity.SplashActivity.selected_cat_index;

public class SetsActivity extends AppCompatActivity {
    private GridView grvSets;
    private FirebaseFirestore firestore;
    public static List<String> setList = new ArrayList<>();
    public static String catName;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        grvSets = (GridView)findViewById(R.id.gridviewSets);

        Toolbar toolbar = findViewById(R.id.toolbarSets);
        setSupportActionBar(toolbar);


        catName = getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(catList.get(selected_cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog = new Dialog(SetsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        firestore = FirebaseFirestore.getInstance();
        
        loadSets();
        

    }

    private void loadSets() {

        setList.clear();
        firestore.collection("QUIZ").document(catList.get(selected_cat_index).getId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                long noOfSets = (long) documentSnapshot.get("SETS");

                for (int i = 1; i <= noOfSets; i++) {
                    setList.add(documentSnapshot.getString("SET" + String.valueOf(i) + "_ID"));
                }
                SetsAdapter adapter = new SetsAdapter(setList.size());
                grvSets.setAdapter(adapter);
                loadingDialog.dismiss();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SetsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            SetsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
