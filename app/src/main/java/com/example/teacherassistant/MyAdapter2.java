package com.example.teacherassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyAdapter2 extends ArrayAdapter<String> {
    private ArrayList<String> names ;
    //private Bitmap[] pics;
    private Context mContext;

    public MyAdapter2(Context context, ArrayList<String> studentNames)//, Bitmap [] studentPictures){
    {  super(context, R.layout.activity_list_view2);
        this.names = studentNames;
        //   this.pics = studentPictures;
        this.mContext = context;

    }

    @Override

    public int getCount() {

        return names.size();

    }
    public void removeItem(int position){
        //convert array to ArrayList, delete item and convert back to array
        ArrayList<String> a = new ArrayList<>(names);
        a.remove(position);
        this.names = a;
        notifyDataSetChanged(); //refresh your listview based on new data

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_list_view2, parent, false);
            // mViewHolder.mPicture = convertView.findViewById(R.id.imageView_listView);
            mViewHolder.mName = convertView.findViewById(R.id.textView_listView2);
            convertView.setTag(mViewHolder);
        }
        else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        //  mViewHolder.mPicture.setImageBitmap(pics[position]);
        mViewHolder.mName.setText(names.get(position));


        return convertView;
    }
    static class ViewHolder{
        //    ImageView mPicture;
        TextView mName;
    }
}
