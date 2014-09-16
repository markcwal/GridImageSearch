package com.yahoo.markcwal.gridimagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.markcwal.gridimagesearch.R;
import com.yahoo.markcwal.gridimagesearch.models.ImageResult;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultsAdapter(Context context, List<ImageResult> objects) {
		super(context, android.R.layout.simple_list_item_1, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//return super.getView(position, convertView, parent);
		ImageResult imageInfo = getItem(position);
		if (convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
		}
		
		ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
		
		//clear out image from last time
		ivImage.setImageResource(0);
		tvTitle.setText(Html.fromHtml(imageInfo.title));
		
		//remotely download image in background using picasso
		Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);
		
		return convertView;
	}
}
