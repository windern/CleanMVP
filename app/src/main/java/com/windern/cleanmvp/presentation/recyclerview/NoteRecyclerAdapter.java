package com.windern.cleanmvp.presentation.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.windern.cleanmvp.R;
import com.windern.cleanmvp.data.database.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenxinlin on 2016/12/15.
 */

public class NoteRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Note> list;

    public NoteRecyclerAdapter(List<Note> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch (viewType) {
            //其他无法处理的情况使用normal
            default:
            case TYPE_NORMAL:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_note, parent, false);
                viewHolder = new ViewHolder(view);
                return viewHolder;
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_foot, parent, false);
                viewHolder = new FootViewHolder(view);
                return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //这时候 article是 null，先把 footer 处理了
        if (holder instanceof FootViewHolder) {
            ((FootViewHolder) holder).rcvLoadMore.spin();
            return;
        } else if (holder instanceof ViewHolder) {
            final Note note = list.get(position);
            ((ViewHolder) holder).tvId.setText(String.valueOf(note.getId()));
            ((ViewHolder) holder).tvId.setText(note.getText());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Note note = list.get(position);
        if (note != null) {
            return TYPE_NORMAL;
        } else {
            return TYPE_FOOTER;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rcv_load_more)
        ProgressWheel rcvLoadMore;

        public FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
