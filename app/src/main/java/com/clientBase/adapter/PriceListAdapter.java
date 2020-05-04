package com.clientBase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.PriceModel;
import com.clientBase.view.RoundedCornerImageView;

import java.util.List;

public class PriceListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<PriceModel> list_result;
    private int posIndex;
    private Context mContext;

    public PriceListAdapter(Context context, List<PriceModel> list_result) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.list_result = list_result;
    }

    @Override
    public int getCount() {
        return list_result.size();
    }

    @Override
    public Object getItem(int position) {
        return list_result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_friendmessage, null);
            holder = new ViewHolder();
            holder.uname = (TextView) convertView.findViewById(R.id.uname);
            holder.uhobby = (TextView) convertView.findViewById(R.id.uhobby);
            holder.mlogoGroup = (RoundedCornerImageView) convertView.findViewById(R.id.mlogoGroup);
            holder.mivAdd = (ImageView) convertView.findViewById(R.id.mivAdd);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.uname.setText(list_result.get(position).getPriceUserName()+"竞价"+list_result.get(position).getPriceMoney()+"元");
        holder.uhobby.setText("竞价时间：" + list_result.get(position).getPriceTime());
        holder.mlogoGroup.setImageResource(R.drawable.icon_huodong_logo);
//
//        if(MemberUserUtils.getLoginFlag(mContext).equals("2")){
//            holder.mivAdd.setVisibility(View.GONE);
//        }else{
//            holder.mivAdd.setVisibility(View.VISIBLE);
//
//        }


        return convertView;

    }

    private class ViewHolder {
        private TextView uname;
        private TextView uhobby;
        private ImageView mivAdd;

        private RoundedCornerImageView mlogoGroup;
    }

    public void setPos(int pos) {
        posIndex = pos;
    }

}
