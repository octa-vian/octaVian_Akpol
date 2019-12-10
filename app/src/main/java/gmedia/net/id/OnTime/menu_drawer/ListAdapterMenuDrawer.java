package gmedia.net.id.OnTime.menu_drawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gmedia.net.id.OnTime.R;

public class ListAdapterMenuDrawer extends ArrayAdapter {
    private Context context;
    private List<SetGetMenuDrawer> isiMenu;
    private int showPosition;

    public ListAdapterMenuDrawer(Context context, List<SetGetMenuDrawer> isiMenu) {
        super(context, R.layout.view_menu_drawer, isiMenu);
        this.context = context;
        this.isiMenu = isiMenu;
    }

    private static class ViewHolder {
        private ImageView icon;
        private TextView textIcon;
        private RelativeLayout utama;
    }

    @Override
    public int getCount() {
        return isiMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return isiMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.view_menu_drawer, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.gambar);
            holder.textIcon = (TextView) convertView.findViewById(R.id.textDrawer);
            holder.utama = (RelativeLayout) convertView.findViewById(R.id.layoutMenuUtama);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SetGetMenuDrawer itemSelected = isiMenu.get(position);
        holder.icon.setImageDrawable(convertView.getResources().getDrawable(itemSelected.getIconMenu()));
        holder.textIcon.setText(itemSelected.getTextMenu());
        holder.utama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked " + isiMenu.get(position), Toast.LENGTH_LONG).show();
            }
        });
        /*items.remove(2);
        notifyDataSetChanged();*/
        return convertView;
    }
}
