package com.uottawa.josephsreih.grimpeurscyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventList extends ArrayAdapter<Event>{
    private Activity context;
    List<Event> events;

    public EventList(Activity context, List<Event> events) {
        super(context, R.layout.layout_event_list, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_event_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);
        TextView textViewMaxParticipants = (TextView) listViewItem.findViewById(R.id.textViewMaxParticipants);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);
        TextView textViewLocation = (TextView) listViewItem.findViewById(R.id.textViewLocation);
        TextView textViewTypeOfEvent = (TextView) listViewItem.findViewById(R.id.textViewTypeOfEvent);
        TextView textViewClubName = (TextView) listViewItem.findViewById(R.id.textViewClubName);

        Event event = events.get(position);
        textViewClubName.setText("Club name: "+ event.getEventOrganizer().getName());
        textViewName.setText(event.getName());
        textViewTypeOfEvent.setText("Event Type: "+ event.getEventType().getTypeOfEvent());
        textViewPrice.setText("Price: "+ String.valueOf(event.getPrice())+ "$");
        textViewMaxParticipants.setText("Total participants: " +String.valueOf(event.getTotalParticipants())+ "/"+  String.valueOf(event.getMaxParticipants()));
        textViewDate.setText("Date: " + event.getDate());
        textViewLocation.setText(("Location: "+ event.getAddress().getLocation()));

        return listViewItem;
    }
}
