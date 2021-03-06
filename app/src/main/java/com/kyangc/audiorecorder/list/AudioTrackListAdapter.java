package com.kyangc.audiorecorder.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.kyangc.audiorecorder.utils.T;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Usage: Adapter for displaying files.
 *
 * Created by chengkangyang on 2017/2/19.
 */
public class AudioTrackListAdapter extends RecyclerView.Adapter<AudioTrackListAdapter.ItemVH> {

    private List<File> mData = new ArrayList<>();

    private OnItemLongClickListener mLongClickListener;

    private Context mContext;

    public AudioTrackListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemVH(new AudioItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ItemVH holder, int position) {
        if (holder != null
                && getDataAt(position) != null
                && holder.itemView instanceof AudioItemView) {
            ((AudioItemView) holder.itemView).setData(getDataAt(position));
            ((AudioItemView) holder.itemView).bindLongClickListener(mLongClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return getDataSet().size();
    }

    public List<File> getDataSet() {
        if (mData == null) mData = new ArrayList<>();
        return mData;
    }

    public File getDataAt(int pos) {
        if (pos >= 0 && pos < getDataSet().size()) {
            return getDataSet().get(pos);
        }
        return null;
    }

    public void setData(List<File> data) {
        getDataSet().clear();
        getDataSet().addAll(data);
        notifyDataSetChanged();
    }

    public void removeData(File file) {
        int index = getDataSet().indexOf(file);
        if (index != -1) {
            getDataSet().remove(index);
            notifyItemRemoved(index);
            if (file.delete()) {
                T.quick(mContext, "删除成功");
            } else {
                T.quick(mContext, "删除失败");
            }
        }
    }

    public AudioTrackListAdapter setLongClickListener(OnItemLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
        return this;
    }

    public interface OnItemLongClickListener {

        void onLongClick(File file);
    }

    public static class ItemVH extends RecyclerView.ViewHolder {

        public ItemVH(View itemView) {
            super(itemView);
        }
    }
}
