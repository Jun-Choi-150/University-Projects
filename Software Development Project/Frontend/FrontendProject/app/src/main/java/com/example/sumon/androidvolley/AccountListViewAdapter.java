package com.example.sumon.androidvolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.sumon.androidvolley.app.AppController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author sabrinaf
 */
public class AccountListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<String> TitleList = null;
    private ArrayList<String> arraylist;
    private String ImageUrl;
    private String AccountUsername;
    private int[] reviews;
    private ArrayList<int[]> stars;
    private ArrayList<int[]> starArrayList;
    private ArrayList<String> reviewText;
    private ArrayList<String> reviewArraylist;

    /**
     * constructor for the AccountListViewAdapter class
     * @param context class context
     * @param TitleList list of all usernames
     */
    public AccountListViewAdapter(Context context, String ImageUrl, String AccountUsername,List<String> TitleList, ArrayList<int[]> stars, ArrayList<String> reviewText) {
        mContext = context;
        this.TitleList = TitleList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<String>();
        this.arraylist.addAll(TitleList);
        this.ImageUrl = ImageUrl;
        this.AccountUsername = AccountUsername;
        this.stars = stars;
        this.starArrayList = new ArrayList<int[]>();
        starArrayList.addAll(stars);
        this.reviewText = reviewText;
        this.reviewArraylist = new ArrayList<String>();
        this.reviewArraylist.addAll(reviewText);
    }

    /**
     * @author sabrinaf
     */
    public class ViewHolder {
        NetworkImageView userPic;
        TextView username;
        TextView title;
        TextView content;

//        ArrayList<int[]> starsList;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;
        TextView date;
    }


    /**
     * returns the int value of the count of the movie name list
     * @return count of the movie name list
     */
    @Override
    public int getCount() {
        return TitleList.size();
    }

    /**
     * this method returns a MovieName at a given position in the array
     * @param position Position of the item whose data we want within the adapter's
     * data set.
     * @return an item of the list at a certain position
     */
    @Override
    public String getItem(int position) {
        return TitleList.get(position);
    }


//    public int getRatingItem(int position) { return reviews[position];}

    /**
     * this method returns the long value of the Item ID of a MovieName at a given position
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return long value of the Item ID
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final AccountListViewAdapter.ViewHolder holder;
        if (view == null) {
            holder = new AccountListViewAdapter.ViewHolder();
            view = inflater.inflate(R.layout.account_list_view, null);
            // Locate the TextViews in listview_item.xml
            holder.userPic = (NetworkImageView) view.findViewById(R.id.accountPic);
            holder.username = (TextView) view.findViewById(R.id.accountUsername);
            holder.title = (TextView) view.findViewById(R.id.accountMovieTitle);
            holder.star1 = (ImageView) view.findViewById(R.id.star1);
            holder.star2 = (ImageView) view.findViewById(R.id.star2);
            holder.star3 = (ImageView) view.findViewById(R.id.star3);
            holder.star4 = (ImageView) view.findViewById(R.id.star4);
            holder.star5 = (ImageView) view.findViewById(R.id.star5);
            holder.content = (TextView) view.findViewById(R.id.postContentText);

            //start placer
            holder.date = (TextView) view.findViewById(R.id.postDate);


            view.setTag(holder);
        } else {
            holder = (AccountListViewAdapter.ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.userPic.setImageUrl(ImageUrl,imageLoader);
        holder.username.setText(AccountUsername);
        holder.title.setText(TitleList.get(position));
        holder.star1.setImageResource(starArrayList.get(position)[0]);
        holder.star2.setImageResource(starArrayList.get(position)[1]);
        holder.star3.setImageResource(starArrayList.get(position)[2]);
        holder.star4.setImageResource(starArrayList.get(position)[3]);
        holder.star5.setImageResource(starArrayList.get(position)[4]);
        holder.content.setText(reviewArraylist.get(position));








        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        TitleList.clear();
        if (charText.length() == 0) {
            TitleList.addAll(arraylist);
        } else {
            for (String wp : arraylist) {
                if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                    TitleList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
