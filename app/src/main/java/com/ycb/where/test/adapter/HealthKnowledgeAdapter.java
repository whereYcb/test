package com.ycb.where.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ycb.where.test.R;
import com.ycb.where.test.bean.HealthKnowledgeBean;
import com.ycb.where.test.utils.GlideUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/20.
 */
    public class HealthKnowledgeAdapter extends RecyclerView.Adapter<HealthKnowledgeAdapter.ListHolder> implements View.OnClickListener{
        private List<HealthKnowledgeBean.HealthKnowledgeDetail> datas;

        private Context mContext;

        public HealthKnowledgeAdapter(Context context, List<HealthKnowledgeBean.HealthKnowledgeDetail> datas){
            this.mContext=context;
            this.datas=datas;
        }
        private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void addItem(List<HealthKnowledgeBean.HealthKnowledgeDetail> newdatas) {
        newdatas.addAll(datas);
        datas.removeAll(datas);
        datas.addAll(newdatas);
        notifyDataSetChanged();
    }

    public void addMore(List<HealthKnowledgeBean.HealthKnowledgeDetail> newdatas) {
        datas.addAll(newdatas);
        notifyDataSetChanged();
    }
    public static interface OnRecyclerViewItemClickListener{
            void onItemClick(View view, HealthKnowledgeBean.HealthKnowledgeDetail data);
        }

        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(mContext, R.layout.item_health_knowledge, null);
//            将创建的view注册点击事件
            view.setOnClickListener(this);
            return new ListHolder(view);
        }
        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            holder.setData(datas.get(position));
//            将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(datas.get(position));
        }
                @Override
        public void onClick(View v) {
            if (mOnItemClickListener!=null){
                //注意这里使用getTag的方法获取数据
                mOnItemClickListener.onItemClick(v, (HealthKnowledgeBean.HealthKnowledgeDetail) v.getTag());
            }
        }
        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
                this.mOnItemClickListener = listener;
        }
        @Override
        public int getItemCount() {
            return datas.size();
        }



        class ListHolder extends RecyclerView.ViewHolder{
            TextView tvDesc;
            TextView data;
            ImageView ivImg;
            public ListHolder(View itemView) {
                super(itemView);
                tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
                data = (TextView) itemView.findViewById(R.id.time);
                ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
            }

            public void setData(HealthKnowledgeBean.HealthKnowledgeDetail bean) {
                tvDesc.setText(bean.description);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String time = format.format(bean.time);
                data.setText(time);
//                Glide.with(mContext).load("http://tnfs.tngou.net/image"+bean.img).into(ivImg);
                GlideUtils.GlideUtils("http://tnfs.tngou.net/image"+bean.img,ivImg);
            }
        }
    }
