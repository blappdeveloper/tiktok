package com.leeladher.shortvideo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leeladher.shortvideo.R;

public class commentviewholder extends RecyclerView.ViewHolder
{   ImageView cuimage;
    TextView cuname;
    TextView cumessage;
    TextView cudt;

    public commentviewholder(@NonNull View itemView) {
        super(itemView);

        cuimage=itemView.findViewById(R.id.cuimage);
        cuname=itemView.findViewById(R.id.cuname);
        cumessage=itemView.findViewById(R.id.cumessage);
        cudt=itemView.findViewById(R.id.cudt);
    }
}
