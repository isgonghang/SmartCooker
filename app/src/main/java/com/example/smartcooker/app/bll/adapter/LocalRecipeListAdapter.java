package com.example.smartcooker.app.bll.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.irecyclerview.IViewHolder;
import com.bumptech.glide.Glide;
import com.example.frame_lib.frame.application.BaseApplication;
import com.example.smartcooker.R;
import com.example.smartcooker.app.dal.model.CloudRecipeListModel;
import com.example.smartcooker.app.dal.model.LocalRecipeListModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ke on 2018/4/29.
 */

public class LocalRecipeListAdapter extends RecyclerView.Adapter<IViewHolder> {

    private List<LocalRecipeListModel> modelList;

    private OnItemClickListener<LocalRecipeListModel> mOnItemClickListener;

    public LocalRecipeListAdapter() {
        modelList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener<LocalRecipeListModel> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setList(List<LocalRecipeListModel> list) {
        modelList.clear();
        this.modelList = list;
        notifyDataSetChanged();
    }

//    public void append(List<Image> images) {
//        int positionStart = mImages.size();
//        int itemCount = images.size();
//        mImages.addAll(images);
//        if (positionStart > 0 && itemCount > 0) {
//            notifyItemRangeInserted(positionStart, itemCount);
//        } else {
//            notifyDataSetChanged();
//        }
//    }
//
//    public void remove(int position) {
//        mImages.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public void clear() {
//        mImages.clear();
//        notifyDataSetChanged();
//    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_local_recipe_list, null);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Note:
                 * in order to get the right position, you must use the method with i- prefix in
                 * {@link IViewHolder} eg:
                 * {@code IViewHolder.getIPosition()}
                 * {@code IViewHolder.getILayoutPosition()}
                 * {@code IViewHolder.getIAdapterPosition()}
                 */
                final int position = holder.getIAdapterPosition();
                final Long id = modelList.get(position).getId();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(id);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(IViewHolder holder, int position) {
        View view = holder.itemView;
        ImageView content = view.findViewById(R.id.image_left);
        TextView name = view.findViewById(R.id.food_name);
        LocalRecipeListModel model = modelList.get(position);
        name.setText(model.getName());
        Glide.with(content.getContext()).load(model.getImage_left()).dontAnimate().into(content);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    static class ViewHolder extends IViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}