package com.example.chris.changiappv3;

/**
 * Created by chris on 25/11/2017.
 */
        import android.content.Context;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import java.util.Collections;
        import java.util.List;

public class AdapterLocation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataLocation> data= Collections.emptyList();
    DataLocation current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterLocation(Context context, List<DataLocation> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_location, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        final DataLocation current=data.get(position);
        myHolder.textLocationName.setText(current.loationName);
        myHolder.Locationtype.setText("Size: " + current.ratings);
        myHolder.textType.setText("Category: " + current.catName);
        myHolder.textPrice.setText("Price" + current.price);
        myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        // load image into imageview using glide
        //Glide.with(context).load("http://192.168.1.7/test/images/" + current.fishImage)
        Log.i("image",current.loationImage);
        Glide.with(context).load(current.loationImage)
              //  .placeholder(R.drawable.ic_img_error)
               // .error(R.drawable.ic_img_error)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(myHolder.ivLocation);

        final String name=current.loationName;
        ((MyHolder) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(context, " you clicked " + name,Toast.LENGTH_SHORT).show();
            }
        });

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textLocationName;
        ImageView ivLocation;
        TextView Locationtype;
        TextView textType;
        TextView textPrice;
        RelativeLayout relativeLayout;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textLocationName= (TextView) itemView.findViewById(R.id.textlocationName);
            ivLocation= (ImageView) itemView.findViewById(R.id.ivLocation);
            Locationtype = (TextView) itemView.findViewById(R.id.textSize);
            textType = (TextView) itemView.findViewById(R.id.textType);
            textPrice = (TextView) itemView.findViewById(R.id.locationPrice);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }

    }

}