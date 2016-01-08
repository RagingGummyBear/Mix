package com.grizzlypenguins.dungeondart.ScrollViewPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grizzlypenguins.dungeondart.Activities.CreateMapActivity;
import com.grizzlypenguins.dungeondart.Activities.MainMenu;
import com.grizzlypenguins.dungeondart.R;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Darko on 08.11.2015.
 */

public class ListElementAdapter extends BaseAdapter {
    ArrayList<ListInput> myList = new ArrayList<ListInput>();
    LayoutInflater inflater;
    Context context;

    public ListElementAdapter(Context context, ArrayList<ListInput> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public void update(ListInput s)
    {
        myList.add(s);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public ListInput getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_layout, parent, false);
            mViewHolder = new MyViewHolder(convertView,position);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        if (mViewHolder!=null) {
            ListInput currentListData = getItem(position);
            mViewHolder.mapName.setText(currentListData.mapName);
            mViewHolder.mapScore.setText(currentListData.mapScore+"");
        }
        return convertView;
    }

    private class MyViewHolder {
        TextView mapName, mapScore;
        int num=0;
        public MyViewHolder(View item, final int n) {
            num = n;
            mapName = (TextView) item.findViewById(R.id.Text);
            mapScore = (TextView) item.findViewById(R.id.Time);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        MainMenu temp = (MainMenu) context;
                        if (temp != null) temp.pickLevelMap(mapName.getText().toString());

                    }
                    catch (Exception e)
                    {
                        CreateMapActivity temp2 = (CreateMapActivity) context;
                        if(temp2!=null)
                        {
                            temp2.chooseTile(mapName.getText().toString());
                            temp2.hideTileFragment();
                        }
                    }


                }
            });
        }
    }
}
