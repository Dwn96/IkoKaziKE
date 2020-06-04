package com.wambu.ikokazike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridViewActivity extends BaseAdapter {
    private Context mContext;
    private final String[] gridviewString;
    private final int[] gridViewImageID;


    CustomGridViewActivity(Context context, String[] gridviewString, int[] gridViewImageID){
        mContext=context;
        this.gridviewString=gridviewString;
        this.gridViewImageID=gridViewImageID;

    }



    @Override
    public int getCount() {
        return gridviewString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;

        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){

            gridViewAndroid = new View (mContext);
            gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
            TextView textViewAndroid = gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridviewString[i]);
            imageViewAndroid.setImageResource(gridViewImageID[i]);
        } else {
            gridViewAndroid = convertView;
        }

        return gridViewAndroid;

        }

    }

