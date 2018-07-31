package com.example.quang.timnhatro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quang.timnhatro.R;
import com.example.quang.timnhatro.object.ItemHouse;

import java.util.ArrayList;

public class HouseAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ItemHouse> arrItem;

    public HouseAdapter(Context context, int layout, ArrayList<ItemHouse> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        TextView tvTitle;
        ImageView imView;
    }

    @Override
    public int getCount() {
        return arrItem.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewRow = view;
        if(viewRow == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                viewRow = inflater.inflate(layout,viewGroup,false);
            }

            ViewHolder holder = new ViewHolder();
            if (viewRow != null) {
                holder.tvTitle = viewRow.findViewById(R.id.tvItemTitle);
            }
            if (viewRow != null) {
                holder.imView = viewRow.findViewById(R.id.imItemHouse);
            }


            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        ItemHouse item = arrItem.get(i);
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }
        if (holder != null) {
            holder.tvTitle.setText(item.getTitle());
        }
        if (holder != null) {
            Glide.with(context).load(item.getImage()).into(holder.imView);
        }

        return viewRow;
    }
}