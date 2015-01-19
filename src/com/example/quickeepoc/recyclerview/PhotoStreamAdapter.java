package com.example.quickeepoc.recyclerview;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quickeepoc.R;
import com.example.quickeepoc.recyclerview.PhotoStreamViewHolder.NotifyChange;

public class PhotoStreamAdapter extends RecyclerView.Adapter<PhotoStreamViewHolder> {
	
	
	private List<ResolveInfo> mApps;
	private Activity parent;
	
	
	
    public PhotoStreamAdapter(Activity act) {
        parent = act;
        loadApps();
    }

   
    
    
    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mApps = parent.getPackageManager().queryIntentActivities(mainIntent, 0);
    }
    
	@Override
	public int getItemCount() {
		return mApps.size();
	}

	@Override
	public void onBindViewHolder(PhotoStreamViewHolder viewholder, int position) {
		//viewholder.icon.setImageResource(PHOTOS_RESOURCES[position]);
		
		ResolveInfo info = mApps.get(position);
		viewholder.icon.setImageDrawable(info.activityInfo.loadIcon(parent.getPackageManager()));
        
	}

	@Override
	public PhotoStreamViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.photostream_item, viewGroup, false);
        NotifyChange change = new PhotoStreamViewHolder.NotifyChange() {
			
			@Override
			public void notfiyMe() {
				notifyDataSetChanged();
				
			}
		};
        return new PhotoStreamViewHolder(itemView, change);
	}

}
