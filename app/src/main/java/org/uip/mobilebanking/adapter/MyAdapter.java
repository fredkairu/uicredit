package org.uip.mobilebanking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.uip.mobilebanking.R;
import org.uip.mobilebanking.models.accounts.loan.TitleDeed;

import java.util.ArrayList;

/**
 * Created by Oclemmy on 4/23/2016 for ProgrammingWizards Channel.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<TitleDeed> titleDeeds;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, TitleDeed obj, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public MyAdapter(Context c, ArrayList<TitleDeed> titles) {
        this.c = c;
        this.titleDeeds = titles;
    }

    //INITIALIE VH
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
       holder.nameTxt.setText(titleDeeds.get(position).productName+"\nKSh "+ titleDeeds.get(position).unitPrice);
        Glide.with(c).load(R.mipmap.ic_launcher).into(holder.imageView);
//        holder.brief.setText("You qualify for KSh "+titleDeeds.get(position).loanAmount+" (40% of property value)");
        holder.brief.setText(titleDeeds.get(position).titleNumber);
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, titleDeeds.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleDeeds.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
