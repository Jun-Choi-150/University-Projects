package com.example.sumon.androidvolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.HashMap;

/**
 * the ListViewAdapter does sorting, counting, and a few other activities
 * to the array of MovieNames given
 * @author sabrinaf
 */
public class FriendListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<FriendNames> FriendNamesList = null;
    private ArrayList<FriendNames> arraylist;
    /**
     * constructor for the ListViewAdapter class
     * @param context class context
     * @param FriendNamesList list of all MovieNames
     */
    public FriendListViewAdapter(Context context, List<FriendNames> FriendNamesList) {
        mContext = context;
        this.FriendNamesList = FriendNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<FriendNames>();
        this.arraylist.addAll(FriendNamesList);
    }



    /**
     * @author sabrinaf
     */
    public class ViewHolder {
        TextView name;

    }

    /**
     * returns the int value of the count of the movie name list
     * @return count of the movie name list
     */
    @Override
    public int getCount() {
        return FriendNamesList.size();
    }



    /**
     * this method returns a MovieName at a given position in the array
     * @param position Position of the item whose data we want within the adapter's
     * data set.
     * @return an item of the list at a certain position
     */
    @Override
    public FriendNames getItem(int position) {
        return FriendNamesList.get(position);
    }





    /**
     * this method returns the long value of the Item ID of a MovieName at a given position
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return long value of the Item ID
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * this method returns the View of a MovieName at a given position
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param view The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return View of the MovieName at a given position
     */
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.user_list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.usernameText);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(FriendNamesList.get(position).getFriendName());

        return view;
    }

    /**
     * this method filters the MovieNameList with the given input from
     * the search bar
     * @param charText input value from the search bar
     */
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        FriendNamesList.clear();
        if (charText.length() == 0) {
            FriendNamesList.addAll(arraylist);
        } else {
            for (FriendNames wp : arraylist) {
                if (wp.getFriendName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    FriendNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}

