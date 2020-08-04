package com.example.myquiz.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myquiz.Activity.SetsActivity;
import com.example.myquiz.R;

import java.util.List;
import java.util.Random;

public class CategoryAdapter extends BaseAdapter {

    private List<String> categories;

    public CategoryAdapter(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        } else {
            view = convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), SetsActivity.class);
                intent.putExtra("category", categories.get(position));
                intent.putExtra("categoryId", position + 1);

                parent.getContext().startActivity(intent);
            }
        });
        ((TextView) view.findViewById(R.id.textviewItemGriview)).setText(categories.get(position));
        Random rd = new Random();
        int color = Color.argb(255, rd.nextInt(255), rd.nextInt(255), rd.nextInt(255));
        view.setBackgroundColor(color);
        return view;
    }
}
