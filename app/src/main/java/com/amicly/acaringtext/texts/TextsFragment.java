package com.amicly.acaringtext.texts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amicly.acaringtext.R;
import com.amicly.acaringtext.addtext.AddTextActivity;
import com.amicly.acaringtext.data.InMemoryTextsRepository;
import com.amicly.acaringtext.data.Text;
import com.amicly.acaringtext.data.TextsServiceApiImpl;

import java.util.ArrayList;
import java.util.List;

import static com.amicly.acaringtext.util.DateUtil.getFormattedDateAndTimeFromTimestamp;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class TextsFragment extends Fragment implements TextsContract.View {

    public static final String BASE_URL = " http://quotes.rest";
    private static final int REQUEST_ADD_TEXT = 1;

    private TextsContract.UserActionsListener mActionsListener;

    private TextsAdapter mListAdapter;

    public TextsFragment() {
    }

    public static TextsFragment newInstance() {
        return new TextsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new TextsAdapter(new ArrayList<Text>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadTexts(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        mActionsListener = new TextsPresenter(
                new InMemoryTextsRepository(new TextsServiceApiImpl()), this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If a note was successfully added, show snackbar
        if (REQUEST_ADD_TEXT == requestCode && Activity.RESULT_OK == resultCode) {
            Snackbar.make(getView(), getString(R.string.successfully_saved_text_message),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_texts, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.texts_list);
        recyclerView.setAdapter(mListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionsListener.addNewText();
            }
        });

        // Pull-to-refresh
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActionsListener.loadTexts(true);
            }
        });

        return root;
    }

    @Override
    public void setProgressIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showTexts(List<Text> texts) {
        mListAdapter.replaceData(texts);
    }

    @Override
    public void showAddText() {
        Intent intent = new Intent(getContext(),AddTextActivity.class);
        startActivityForResult(intent, REQUEST_ADD_TEXT);
    }

    @Override
    public void showTextDetailUi(String textId) {
//        Intent intent = new Intent(getContext(), TextDetailActivity.class);
//        intent.putExtra(TextDetailActivity.EXTRA_NOTE_ID, textId);
//        startActivity(intent);
    }

    /**
     * Listener for clicks on notes in the RecyclerView.
     */
    TextItemListener mItemListener = new TextItemListener() {
        @Override
        public void onTextClick(Text clickedText) {
            mActionsListener.openTextDetails(clickedText);
        }
    };


    private static class TextsAdapter extends RecyclerView.Adapter<TextsAdapter.ViewHolder> {

        private List<Text> mTexts;
        private TextItemListener mItemListener;

        public TextsAdapter(List<Text> texts, TextItemListener itemListener) {
            setList(texts);
            mItemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.item_text, parent, false);

            return new ViewHolder(noteView, mItemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Text text = mTexts.get(position);

            String dateTimeString = getFormattedDateAndTimeFromTimestamp(text.getmDateTime());

            viewHolder.dateTime.setText(dateTimeString);
            viewHolder.contact.setText(text.getmContact());
            viewHolder.message.setText(text.getmMessage());
        }

        private void setList(List<Text> texts) {
            mTexts = checkNotNull(texts);
        }

        public void replaceData(List<Text> texts){
            setList(texts);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mTexts.size();
        }

        public Text getItem(int position) {
            return mTexts.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView dateTime;
            public TextView contact;
            public TextView message;

            public ViewHolder(View itemView, TextItemListener listener) {
                super(itemView);
                mItemListener = listener;
                dateTime = (TextView) itemView.findViewById(R.id.text_detail_date_time);
                contact = (TextView) itemView.findViewById(R.id.text_detail_contact);
                message = (TextView) itemView.findViewById(R.id.text_detail_message);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Text text = getItem(position);
                mItemListener.onTextClick(text);
            }
        }
    }

    public interface TextItemListener {

        void onTextClick(Text clickedText);
    }



}
