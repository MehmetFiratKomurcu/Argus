package com.arkmfk.argus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<ItemRow> itemList;

    //getting the context and product list with constructor
    public ItemAdapter(Context mCtx, List<ItemRow> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_row, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        //getting the product of the specified position
        ItemRow item = itemList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewCompany.setText(item.getCompany());
        holder.textViewDate.setText(String.valueOf(item.getDate()));
        holder.textViewDistance.setText(String.valueOf(item.getDistance()));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewCompany, textViewDate, textViewDistance;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCompany = itemView.findViewById(R.id.textViewCompany);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewDistance = itemView.findViewById(R.id.textViewDistance);
        }
    }
}
