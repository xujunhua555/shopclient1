package com.clientBase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.client.R;
import com.clientBase.model.ReviewBean;

import java.util.List;


//import com.tenant.model.CourseModel;

public class ReplyAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReviewBean> list_result;

    public ReplyAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public ReplyAdapter(Context mContext, List<ReviewBean> list_msg) {
        super();
        this.mContext = mContext;
        this.list_result = list_msg;
        notifyDataSetChanged();
    }

    public List<ReviewBean> setData(List<ReviewBean> list_str) {
        return list_result = list_str;

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
        ViewHolder vh = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_reply, null);
            vh = new ViewHolder();
            vh.message_name = (TextView) convertView.findViewById(R.id.message_name);
            vh.message_state = (TextView) convertView.findViewById(R.id.message_state);
            vh.messageContent = (TextView) convertView.findViewById(R.id.messageContent);
            vh.mtvReplyContent = (TextView) convertView.findViewById(R.id.mtvReplyContent);
            vh.mtvContent = (TextView) convertView.findViewById(R.id.mtvContent);

            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.message_state.setText(list_result.get(position).getReviewTime());
        vh.mtvContent.setText(list_result.get(position).getReviewContent());
        vh.message_name.setText(list_result.get(position).getReviewUserName());


        return convertView;
    }

    class ViewHolder {
        TextView message_name;
        TextView message_state;
        TextView messageContent;
        TextView mtvContent;
        TextView mtvReplyContent;
    }
}