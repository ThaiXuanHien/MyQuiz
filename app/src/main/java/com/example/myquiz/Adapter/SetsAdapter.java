package com.example.myquiz.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myquiz.Activity.CategoryActivity;
import com.example.myquiz.Activity.QuestionActivity;
import com.example.myquiz.Activity.SetsActivity;
import com.example.myquiz.R;
import com.example.myquiz.model.Question;

import java.util.List;
import java.util.Random;

public class SetsAdapter extends BaseAdapter {
    private int numOfSets;

    public SetsAdapter(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    @Override
    public int getCount() {
        return numOfSets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sets, parent, false);
        } else {
            view = convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), QuestionActivity.class);

                intent.putExtra("sets", String.valueOf(numOfSets));
                intent.putExtra("SETNO", position);
                parent.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.textviewItemSets)).setText(String.valueOf(position + 1));

        return view;
    }

}
