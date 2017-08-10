package team.hex.wallex.abstracts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blackout on 2/14/2017.
 */

public abstract class AbstractRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> mDataSet;


    public AbstractRecyclerViewAdapter(List<T> mDataSet) {
        this.mDataSet = mDataSet;
    }

    protected abstract RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent);


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return getItemView(inflater, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    protected T getItem(int position) {
        return mDataSet.get(position);
    }

    public void setData(List<T> ts) {
        mDataSet = ts;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDataSet;
    }

    public void setFadeAnimation(View view, int duration) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public void setScaleAnimation(View view, int duration) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        anim.setDuration(duration);
        view.startAnimation(anim);
    }


    public void insertItem(T item, int position) {
        mDataSet.add(item);
        notifyItemInserted(position);
    }


    public void updateItem(T item, int position) {
        mDataSet.set(position, item);
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void deleteAll() {
        mDataSet = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void insertMultipleItems(List<T> items) {
        int lastPos = (getItemCount() > 0) ? getItemCount() : 0;
        mDataSet.addAll(items);
        notifyItemRangeChanged(lastPos, mDataSet.size());
    }

}
