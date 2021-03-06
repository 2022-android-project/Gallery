package com.example.myloginapp.HelperClasses.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.DesReviewInfo;
import com.example.myloginapp.Description.Description;
import com.example.myloginapp.Object;
import com.example.myloginapp.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomExhibitionAdapter extends RecyclerView.Adapter<RandomExhibitionAdapter.Holder> {
    static List<Integer> list=new ArrayList<Integer>();
    boolean isStop=false;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);

        RandomExhibitionAdapter.Holder featuredViewHolder = new RandomExhibitionAdapter.Holder(view);

        return featuredViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(!isStop)
            list = RandNum();
        holder.image.setImageBitmap(Object.art.get(list.get(position)).getImage());
        holder.title.setText(Object.art.get(list.get(position)).getName());
        holder.desc.setText(Object.art.get(list.get(position)).getDesc());
        System.out.println(list);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public List<Integer> RandNum(){
        int a[] = new int[10];
        List<Integer> list = new ArrayList<>();
        long seed = System.currentTimeMillis();
        Random r = new Random(seed);
        for(int i=0;i<10;i++)
        {
            if(Object.art.size() > 0) {
                a[i] = r.nextInt(Object.art.size())+1;
            }
            for(int j=0;j<i;j++)
            {
                if(a[i]==a[j])
                {
                    i--;
                }
            }
        }

        for (int value : a) {
            list.add(value);
        }

        Collections.sort(list);
        isStop=true;
        return list;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public TextView title, desc;
        public ImageView image;

        StringBuilder ArtTime;
        String getArtTime;
        byte[] ImageBt;
        String ArtName;
        String ArtDsc;
        ArrayList<DesReviewInfo> ReviewList; //?????? ??? ????????? ???????????????

        public Holder(@NonNull View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position=getAdapterPosition(); //????????? ?????? ?????? position. 4?????????????????? Object.art.get(4)??????????????????,
                    //????????? ?????????????????? ?????? ????????? ???????????? position??? ???????????????
                    ArtTime=(StringBuilder)  Object.art.get(position).PrintArt();
                    getArtTime=ArtTime.toString(); //StringBuilder??? ArtTime??? String????????? ??????
                    ImageBt=bitmap2Bytes(Object.art.get(position).getImage()); //bitmap??? ???????????? byte????????? ??????
                    ArtName=(String)Object.art.get(position).getName();
                    ArtDsc=(String)Object.art.get(position).getDesc();

                    Intent intent=new Intent(v.getContext(),Description.class); //intent??? ????????? Activity??????
                    //????????? putExtra??? ?????? ???????????? Description.java?????? GetExtra??? ?????? ?????????
                    intent.putExtra("ArtTime",getArtTime);
                    intent.putExtra("Image",ImageBt); //?????? byte?????? ????????? getExtra?????? byte??? bitmap?????? ?????? ??????
                    intent.putExtra("Name",ArtName);
                    intent.putExtra("ArtInfo",ArtDsc);

                    v.getContext().startActivity(intent);
                }

                private byte[] bitmap2Bytes(Bitmap bitmap) { //Bitmap??? byte???????????? ???????????? ?????????
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    return baos.toByteArray();
                }

            });

            //hooks
            image = (ImageView) view.findViewById(R.id.exhibition_image);
            title = (TextView) view.findViewById(R.id.exhibition_title);
            desc = (TextView) view.findViewById(R.id.exhibition_description);
        }
    }
}
