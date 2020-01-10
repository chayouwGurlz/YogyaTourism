package com.ciaocollect.yogyatourism;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemClickSupport {
    private RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (mOnItemClickListener != null){
                view.setOnClickListener(mOnClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view){ }
    };

    private ItemClickSupport (RecyclerView recyclerView){
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static ItemClickSupport addTo (RecyclerView recyclerView){
        ItemClickSupport support = (ItemClickSupport)recyclerView.getTag(R.id.item_click_support);
        if (support == null){
            support = new ItemClickSupport(recyclerView);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView recyclerView){
        ItemClickSupport support = (ItemClickSupport)recyclerView.getTag(R.id.item_click_support);
        if (support != null){
            support.detach(recyclerView);
        }
        return support;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    private void detach(RecyclerView recyclerView){
        recyclerView.removeOnChildAttachStateChangeListener(mAttachListener);
        recyclerView.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener{
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }
}
