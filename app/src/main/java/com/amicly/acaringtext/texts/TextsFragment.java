package com.amicly.acaringtext.texts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amicly.acaringtext.Injection;
import com.amicly.acaringtext.R;
import com.amicly.acaringtext.addtext.AddTextActivity;
import com.amicly.acaringtext.data.Text;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class TextsFragment extends Fragment implements TextsContract.View {

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

        mActionsListener = new TextsPresenter(Injection.provideTextsRepository(getActivity()), this);

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

        return root;
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

            viewHolder.dateTime.setText(text.getmDateTime());
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
                dateTime = (TextView) itemView.findViewById(R.id.text_date_time);
                contact = (TextView) itemView.findViewById(R.id.text_contact);
                message = (TextView) itemView.findViewById(R.id.text_message);
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
