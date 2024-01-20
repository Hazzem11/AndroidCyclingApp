package com.uottawa.josephsreih.grimpeurscyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventTypeList extends ArrayAdapter<EventType>  {
    private Activity context;
    List<EventType> eventTypes;

    public EventTypeList(Activity context, List<EventType> eventTypes) {
        super(context, R.layout.layout_event_type_list, eventTypes);
        this.context = context;
        this.eventTypes = eventTypes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_event_type_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewTypeOfEvent);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewDescription);

        EventType eventType = eventTypes.get(position);
        textViewName.setText(eventType.getTypeOfEvent());
        textViewPrice.setText(String.valueOf(eventType.getDescription()));
        return listViewItem;
    }
}
