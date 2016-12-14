package cs213.photoalbum;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shahab on 12/13/2016.
 */
class ListViewAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<Image> ImageList = null;
    private ArrayList<Image> arraylist;


    ListViewAdapter(Context mContext, List<Image> ImageList) {
        this.ImageList = ImageList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(ImageList);
    }

    private class ViewHolder {
        TextView person;
        TextView location;
        ImageView image;
    }

    @Override
    public int getCount() {
        return ImageList.size();
    }

    @Override
    public Image getItem(int position) {
        return ImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.person = (TextView) view.findViewById(R.id.person_tag);
            holder.location = (TextView) view.findViewById(R.id.location_tag);
            holder.image= (ImageView) view.findViewById(R.id.searchedimage);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.person.setText(ImageList.get(position).getPerson_tag());
        holder.location.setText(ImageList.get(position).getLocation_tag());
        holder.image.setImageURI(Uri.parse(ImageList.get(position).getImage_uri()));
        return view;
    }

    // Filter Class
    void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ImageList.clear();
        if (charText.length() == 0) {
            ImageList.addAll(arraylist);
        } else {
            for (Image wp : arraylist) {
                if ((wp.getPerson_tag()!=null && (wp.getPerson_tag().toLowerCase(Locale.getDefault()).contains(charText)))||
                        (wp.getLocation_tag()!=null && (wp.getLocation_tag().toLowerCase(Locale.getDefault()).contains(charText)))) {
                    ImageList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
