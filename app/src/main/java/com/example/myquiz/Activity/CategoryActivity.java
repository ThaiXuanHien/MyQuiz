package com.example.myquiz.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.myquiz.Adapter.CategoryAdapter;
import com.example.myquiz.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private GridView grvCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbarCategory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        grvCategory = (GridView) findViewById(R.id.gridviewCategory);

        /*List<String> categories = new ArrayList<>();
        categories.add("Java");
        categories.add("C++");
        categories.add("C#");
        categories.add("Python");
        categories.add("JavaScript");
        categories.add("SQL");
        categories.add("FrontEnd");
        categories.add("Cloud Computing");
        categories.add("Hardware Computer");
        categories.add("Software Computer");
        categories.add("Communication and Computer Networks");*/
        CategoryAdapter adapter = new CategoryAdapter(SplashActivity.catList);
        grvCategory.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            CategoryActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
