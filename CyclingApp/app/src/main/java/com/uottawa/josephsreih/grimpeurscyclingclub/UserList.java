package com.uottawa.josephsreih.grimpeurscyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserList extends ArrayAdapter<User>  {
    private Activity context;
    List<User> users;

    public UserList(Activity context, List<User> users) {
        super(context, R.layout.layout_user_list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);

        TextView textViewNameOfUser = (TextView) listViewItem.findViewById(R.id.textViewNameOfUser);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textViewEmail);

        User user = users.get(position);
        textViewNameOfUser.setText(user.getName());
        textViewEmail.setText(String.valueOf(user.getEmail()));
        return listViewItem;
    }
}
