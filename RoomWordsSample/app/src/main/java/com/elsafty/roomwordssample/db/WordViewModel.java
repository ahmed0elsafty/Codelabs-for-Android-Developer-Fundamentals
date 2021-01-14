package com.elsafty.roomwordssample.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getmAllWords() {
        return mAllWords;
    }

    public void insertWord(Word word) {
        mRepository.insertWord(word);
    }

    public void deleteAllWords() {
        mRepository.deleteAllWords();
    }

    public void deleteWord(Word word) {
        mRepository.deleteWord(word);
    }
}
