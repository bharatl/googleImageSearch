package com.bharatl.imagesearcher;

import java.util.List;

import com.squareup.picasso.Picasso;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageSearchAdapter extends ArrayAdapter<ImgSearch>{

	public ImageSearchAdapter(Context context, List<ImgSearch> img) {
		
		super(context, R.layout.layout_image_search, img);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ImgSearch img = getItem(position);
		
		if (convertView == null){
			
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_image_search, parent, false);
		}
		
		ImageView imgView = (ImageView) convertView.findViewById(R.id.imgSearch);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
		tvTitle.setText(Html.fromHtml(img.getCaption()));
		//imgView.getLayoutParams().height = img.getHeight();
		//imgView.getLayoutParams().width = img.getWidth();
		Picasso.with(getContext()).load(img.getUrl()).into(imgView);
		
		return convertView;
		
		
	}

}
