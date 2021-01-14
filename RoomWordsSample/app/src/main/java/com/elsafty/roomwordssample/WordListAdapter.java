package com.elsafty.roomwordssample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsafty.roomwordssample.db.Word;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private Context mContext;
    private List<Word> mWords;

    public WordListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setWords(List<Word> mWords) {
        this.mWords = mWords;
        notifyDataSetChanged();
    }

    public Word getWordAtPosition(int position){
        return mWords.get(position);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word currentWord = mWords.get(position);
        holder.wordTextView.setText(currentWord.getWord());
    }

    @Override
    public int getItemCount() {
        if (mWords != null) {
            return mWords.size();
        } else return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView wordTextView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.word_textView);
        }
    }
}
