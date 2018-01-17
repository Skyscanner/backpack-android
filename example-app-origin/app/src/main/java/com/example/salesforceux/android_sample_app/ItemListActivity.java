package com.example.salesforceux.android_sample_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.salesforceux.android_sample_app.dummy.*;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean serviceCloudContext = false;

    public void onClick(View v) {


        setContentView(R.layout.lightning_activity_item_list);

        View header = findViewById(R.id.header_master);
        header.setOnClickListener(this);

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        ImageView accountsIcon = (ImageView) findViewById(R.id.accountsIcon);
        accountsIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.StandardIcons.StandardAccount, 150, Color.WHITE));

        ImageView chevronDown = (ImageView) findViewById(R.id.chevronDown);
        chevronDown.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.UtilityIcons.UtilityChevrondown, 60));

        ImageView filterIcon = (ImageView) findViewById(R.id.filterIcon);
        filterIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionFilter, 150, Color.WHITE));

        ImageView sortIcon = (ImageView) findViewById(R.id.sortIcon);
        sortIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionSort, 150, Color.WHITE));

        ImageView newIcon = (ImageView) findViewById(R.id.newIcon);
        newIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionNew, 150, Color.WHITE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lightning_activity_item_list);

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        final Context context = this.getApplicationContext();

        View header = findViewById(R.id.header_master);

        ImageView accountsIcon = (ImageView) findViewById(R.id.accountsIcon);
        accountsIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.StandardIcons.StandardAccount, 150, Color.WHITE));

        ImageView chevronDown = (ImageView) findViewById(R.id.chevronDown);
        chevronDown.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.UtilityIcons.UtilityChevrondown, 60));

        ImageView filterIcon = (ImageView) findViewById(R.id.filterIcon);
        filterIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionFilter, 150, Color.WHITE));

        ImageView sortIcon = (ImageView) findViewById(R.id.sortIcon);
        sortIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionSort, 150, Color.WHITE));

        ImageView newIcon = (ImageView) findViewById(R.id.newIcon);
        newIcon.setImageBitmap(Icons.getBitmap(this.getApplicationContext(), Icons.ActionIcons.ActionNew, 150, Color.WHITE));

        header.setOnClickListener(this);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lightning_item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            holder.mContentName.setText(mValues.get(position).name);
            holder.mContentOwner.setText(mValues.get(position).owner);
            holder.mContentState.setText(mValues.get(position).state);
            holder.mContentType.setText(mValues.get(position).type);
            holder.mContentPhone.setText(mValues.get(position).phone);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    intent.putExtra("serviceCloudContext", serviceCloudContext);

                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TypeFaceTextView mContentName;
            public final TypeFaceTextView mContentState;
            public final TypeFaceTextView mContentPhone;
            public final TypeFaceTextView mContentType;
            public final TypeFaceTextView mContentOwner;


            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentName = (TypeFaceTextView) view.findViewById(R.id.title);
                mContentState = (TypeFaceTextView) view.findViewById(R.id.state);
                mContentPhone = (TypeFaceTextView) view.findViewById(R.id.phone);
                mContentType = (TypeFaceTextView) view.findViewById(R.id.type);
                mContentOwner = (TypeFaceTextView) view.findViewById(R.id.owner);
            }
        }
    }
}
