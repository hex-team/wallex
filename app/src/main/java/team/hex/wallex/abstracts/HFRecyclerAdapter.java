package team.hex.wallex.abstracts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by nobahari on 12/26/2016.
 */

public abstract class HFRecyclerAdapter<T> extends AbstractRecyclerViewAdapter<T> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private List<T> data;

    private boolean mWithHeader;
    private boolean mWithFooter;


    public HFRecyclerAdapter(List<T> data, boolean withHeader, boolean withFooter) {
        super(data);
        this.data = data;
        mWithHeader = withHeader;
        mWithFooter = withFooter;
    }



    //region Get View
    protected abstract RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent);
    //endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            return getItemView(inflater, parent);
        } else if (viewType == TYPE_HEADER) {
            return getHeaderView(inflater, parent);
        } else if (viewType == TYPE_FOOTER) {
            return getFooterView(inflater, parent);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        int itemCount = data.size();
        if (mWithHeader)
            itemCount++;
        if (mWithFooter)
            itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }


    public void withFooter(boolean withFooter) {
        mWithFooter = withFooter;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    protected T getItem(int position) {
        return mWithHeader ? data.get(position - 1) : data.get(position);
    }

    public void setData(List<T> ts) {
        data = ts;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    public void insertItem(T item, int position) {
        data.add(item);
        notifyItemInserted(position);
    }


    public void updateItem(T item, int position) {
        data.set(position, item);
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void insertMultipleItems(List<T> items) {
        int lastPos = getItemCount() - 1;
        data.addAll(items);
        notifyDataSetChanged();
       // notifyItemRangeInserted(lastPos, data.size());
    }



}
