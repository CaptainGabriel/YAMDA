package com.example.dev.moviedb.views.adapters;

import com.example.dev.moviedb.utils.Utils;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Base class of our hierarchy of adapters.
 * It specifies the common behavior between adapters.
 *
 * @version 0.0.2
 */
public abstract class AbstractListAdapter<T> extends ArrayAdapter<T> {

    /**
     * Debug purposes
     */
    protected final String TAG = Utils.makeLogTag(getClass());

    /**
     * The list of items to be displayed in the adapter.
     */
    private List<T> mItems;

    /**
     * The application context.
     * using the application's context minimizes the dependency this component
     * has to the exterior environment,
     */
    protected Application ctx;

    /**
     * Ctor that initiates the adapter with the collection of items.
     *
     *
     * @param ctx
     *      The application's context.
     * @param items
     *      The list of items to adapt.
     */
    public AbstractListAdapter(Application ctx, List<T> items) {
        super(ctx, 0, items);
        this.ctx = ctx;
        mItems = items;
    }

    /** {@inheritDoc} */
    @Override
    public int getCount() {
        return mItems.size();
    }

    /** {@inheritDoc} */
    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }

    /** {@inheritDoc} */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Any thing related to the layout like inflating or so must be done here.
     */
    protected abstract View inflateParticularLayout(Context ctx);

    /**
     * Populate the view holder fields for a better performance searching for
     * widgets.
     *
     * @param convertView
     *      The root view of your layout. Extract widgets from here.
     */
    protected abstract SearchListViewHolder buildViewHolder(View convertView);

    /**
     * Implement your "magic" to each field of your view.
     * Populate each widget you see fit according with your layout.
     * This method is called inside the ArrayAdapter#getView method.
     *
     * @param ctx
     *      The application's context
     * @param holder
     *      The view holder.
     * @param position
     *      Position of the item which view should be built.
     */
    protected abstract void populateViewComponents(Application ctx, SearchListViewHolder holder,
                                                   int position);


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = inflateParticularLayout(parent.getContext());

        SearchListViewHolder holder = (SearchListViewHolder) convertView.getTag();
            /*
             * If the call to getTag() on the row returns null , we know we need
             * to create a new ViewHolder, which we then attach to the row
             * via setTag() for later reuse. Then,accessing the child
             * widgets is merely a matter of accessing the data members on the
             * holder.
             * It makes use of the recycling done by ArrayAdapter in terms of reusing views,
             * so we simply have to create our ViewHolder when needed and reuse
             * the existing ViewHolder when a row gets recycled.
             */
        if (holder == null) {
            holder = buildViewHolder(convertView);
            convertView.setTag(holder);
        }

        populateViewComponents(ctx, holder, position);

        return convertView;
    }
}
