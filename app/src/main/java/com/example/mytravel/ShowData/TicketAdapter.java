package com.example.mytravel.ShowData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder>
{
    Context context;
    ArrayList<MyTicket> myTicket;

    public TicketAdapter(Context c, ArrayList<MyTicket> p)
    {
        context = c;
        myTicket= p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myticket, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        myViewHolder.xnama_tiket.setText(myTicket.get(i).getNama_wisata());
        myViewHolder.xlokasi.setText(myTicket.get(i).getLokasi());
        myViewHolder.xjumlah_tiketku.setText(myTicket.get(i).getJumlah_tiket());

        myViewHolder.itemView.setOnClickListener(v ->
        {

        });
    }

    @Override
    public int getItemCount() {
        return myTicket.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView xnama_tiket, xlokasi, xjumlah_tiketku;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            xnama_tiket = itemView.findViewById(R.id.xnama_tiket);
            xlokasi     = itemView.findViewById(R.id.xlokasi);
            xjumlah_tiketku = itemView.findViewById(R.id.xjumlah_tiketku);
        }
    }
}
