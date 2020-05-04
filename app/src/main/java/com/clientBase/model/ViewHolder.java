package com.clientBase.model;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {
	
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View convertView, int id) {
		
		SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
		if( holder == null ) {
			holder = new SparseArray<View>();
			convertView.setTag(holder);
		}
		
		View view = holder.get(id);
		if( view == null ) {
			view = convertView.findViewById(id);
			holder.put(id, view);
		}
		return (T)view;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View convertView, int id, String tag) {
		
		SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
		if( holder == null ) {
			holder = new SparseArray<View>();
			convertView.setTag(holder);
		}
		
		View view = holder.get(id+tag.hashCode());
		if( view == null ) {
			view = convertView.findViewById(id);
			holder.put(id+tag.hashCode(), view);
		}
		return (T)view;
	}
}
