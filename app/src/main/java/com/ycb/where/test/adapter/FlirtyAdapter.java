package com.ycb.where.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ycb.where.test.ImageDetailActivity;
import com.ycb.where.test.R;
import com.ycb.where.test.bean.FlirtyBean;
import com.ycb.where.test.bean.ImageDetailBean;
import com.ycb.where.test.utils.MyGsonRequestUtils;
import com.ycb.where.test.url.Url;
import com.ycb.where.test.utils.GlideUtils;

import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/20.
 */
public class FlirtyAdapter extends RecyclerView.Adapter<FlirtyAdapter.ListHolder> implements View
        .OnClickListener {
    private List<FlirtyBean.tngouDetail> datas;
    private Context mContext;
    public FlirtyAdapter(Context context, List<FlirtyBean.tngouDetail> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void addItem(List<FlirtyBean.tngouDetail> newdatas) {
        newdatas.addAll(datas);
        datas.removeAll(datas);
        datas.addAll(newdatas);
        notifyDataSetChanged();
    }

    public void addMore(List<FlirtyBean.tngouDetail> newdatas) {
        datas.addAll(newdatas);
        notifyDataSetChanged();
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, FlirtyBean.tngouDetail data);
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_flirty, null);
//            将创建的view注册点击事件
        view.setOnClickListener(this);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.setData(datas.get(position));
//            将数据保存在itemView的Tag中，以便点击时进行获取
//        holder.item_straggered_iv.setTag(datas.get(position));
//        Glide.with(mContext).load(datas.get(position)).into(holder.item_straggered_iv);
        holder.setIsRecyclable(false);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag的方法获取数据
            mOnItemClickListener.onItemClick(v, (FlirtyBean.tngouDetail) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class ListHolder extends RecyclerView.ViewHolder {
        TextView item_straggered_tv;
        ImageView item_straggered_iv;

        public ListHolder(View itemView) {
            super(itemView);

            item_straggered_tv = (TextView) itemView.findViewById(R.id.item_straggered_tv);
            item_straggered_iv = (ImageView) itemView.findViewById(R.id.item_straggered_iv);
        }

        public void setData(final FlirtyBean.tngouDetail bean) {
            item_straggered_tv.setText(bean.title);

            GlideUtils.GlideUtils(Url.IMG+bean.img,item_straggered_iv);
            item_straggered_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,bean.title,Toast.LENGTH_SHORT).show();
                    MyGsonRequestUtils<ImageDetailBean> imageDetailBeanMyGsonRequestUtils = new MyGsonRequestUtils<>(Request.Method.GET," http://www.tngou.net/tnfs/api/show?id=654", new Response
                            .ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mContext,"GGGGG",Toast.LENGTH_SHORT).show();
                        }
                    }, ImageDetailBean.class, new Response.Listener<ImageDetailBean>() {
                        @Override
                        public void onResponse(ImageDetailBean response) {
                            Intent intent = new Intent();
                            intent.putExtra("url",Url.IMAGE_DETAIL+bean.id);
                            intent.setClass(mContext, ImageDetailActivity.class);
                            mContext.startActivity(intent);
                        }
                    }, mContext);
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(imageDetailBeanMyGsonRequestUtils);
                }
            });
        }
    }
}
