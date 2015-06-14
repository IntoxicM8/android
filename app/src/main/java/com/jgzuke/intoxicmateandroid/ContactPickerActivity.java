package com.jgzuke.intoxicmateandroid;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by jgzuke on 15-06-13.
 */

public class ContactPickerActivity extends Activity {
    private static int mMontactPosition = 0;
    private static ArrayList<String> mPopulatedNumberList = null;
    private static ArrayList<String> mPopulatedNameList = null;
    private static ContactPickerActivity contactPickerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMontactPosition = getIntent().getExtras().getInt("contact_position");
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(android.R.id.content) == null) {
            ActualList list = new ActualList();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
        contactPickerActivity = this;
    }

    private void contactChosen(int pos) {
        Bundle contactData = new Bundle();
        contactData.putInt("contact_position", mMontactPosition);
        contactData.putString("contact_number", mPopulatedNumberList.get(pos));
        contactData.putString("contact_name", mPopulatedNameList.get(pos));
        Intent intent = new Intent();
        intent.putExtras(contactData);
        setResult(1, intent);
        finish();
    }

    private static void populateLists(ArrayList<String> populatedList, ArrayList<String> populatedIDList) {
        mPopulatedNameList = populatedList;
        mPopulatedNumberList = populatedIDList;
    }

    public static class ActualList extends ListFragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, getNameDetails());
            setListAdapter(adapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        @Override
        public void onListItemClick(ListView l, View v, int position, long id)
        {
            contactPickerActivity.contactChosen(position);
        }
        public ArrayList<String> getNameDetails()
        {
            ArrayList<String> populatedNameList = new ArrayList<>();
            ArrayList<String> populatedNumberList = new ArrayList<>();
            Context context = getActivity();
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[] {ContactsContract.RawContacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.DATA};
            String order = "CASE WHEN "
                    + ContactsContract.Contacts.DISPLAY_NAME
                    + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                    + ContactsContract.Contacts.DISPLAY_NAME
                    + ", "
                    + ContactsContract.CommonDataKinds.Email.DATA
                    + " COLLATE NOCASE";
            String filter;
            Cursor cur;
            filter = ContactsContract.CommonDataKinds.Phone.DATA + " NOT LIKE ''";
            cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, filter, null, order);

            if (cur.moveToFirst())
            {
                do {
                    populatedNameList.add(cur.getString(1));
                    populatedNumberList.add(cur.getString(2));
                } while (cur.moveToNext());
            }
            cur.close();

            populateLists(populatedNameList, populatedNumberList);

            return populatedNameList;
        }
    }
}
