package com.example.salesforceux.android_sample_app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.salesforceux.android_sample_app.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    final List<String[]> DETAILS = new ArrayList<String[]>();

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DETAILS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<String[]> mValues;

        public SimpleItemRecyclerViewAdapter(List<String[]> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lightning_detail_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mContentName.setText(mValues.get(position)[0]);
            holder.mContentValue.setText(mValues.get(position)[1]);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TypeFaceTextView mContentName;
            public final TypeFaceTextView mContentValue;


            public String mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentName = (TypeFaceTextView) view.findViewById(R.id.title);
                mContentValue = (TypeFaceTextView) view.findViewById(R.id.value);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DummyContent.DummyItem mItem = DummyContent.ITEM_MAP.get(getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));

        setContentView(R.layout.lightning_activity_item_detail);

        TypeFaceTextView headerTitle = (TypeFaceTextView) this.findViewById(R.id.headerTitle);
        headerTitle.setText(mItem.name);

        TypeFaceTextView headerType = (TypeFaceTextView) this.findViewById(R.id.headerType);
        headerType.setText(mItem.type);

        TypeFaceTextView headerPhone = (TypeFaceTextView) this.findViewById(R.id.headerPhone);
        headerPhone.setText(mItem.phone);

        TypeFaceTextView headerWebsite = (TypeFaceTextView) this.findViewById(R.id.headerWebsite);
        headerWebsite.setText(mItem.website);

        String[] owner = {"Account Owner", mItem.owner};
        DETAILS.add(owner);

        String[] name = {"Account Name", mItem.name};
        DETAILS.add(name);

        String[] number = {"Account Number", mItem.number};
        DETAILS.add(number);

        String[] type = {"Type", mItem.type};
        DETAILS.add(type);

        String[] industry = {"Industry", mItem.industry};
        DETAILS.add(industry);

        String[] website = {"Website", mItem.website};
        DETAILS.add(website);

        String[] phone = {"Phone", mItem.phone};
        DETAILS.add(phone);

        ImageView accountIcon = (ImageView) findViewById(R.id.accountsIcon);
        accountIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.StandardIcons.StandardAccount, 150, Color.WHITE));

        ImageView callIcon = (ImageView) findViewById(R.id.callIcon);
        callIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionCall, 150, Color.WHITE));

        ImageView taskIcon = (ImageView) findViewById(R.id.taskIcon);
        taskIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionNewTask, 150, Color.WHITE));

        ImageView eventIcon = (ImageView) findViewById(R.id.eventIcon);
        eventIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionNewEvent, 150, Color.WHITE));

        ImageView postIcon = (ImageView) findViewById(R.id.postIcon);
        postIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionSharePost, 150, Color.WHITE));

        ImageView moreIcon = (ImageView) findViewById(R.id.moreIcon);
        moreIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionMore, 150, Color.WHITE));

        View recyclerView = findViewById(R.id.detail_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }
}
