package com.elsafty.roomwordssample.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase wordDb = WordRoomDatabase.getINSTANCE(application);
        mWordDao = wordDb.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insertWord(Word word) {
        new InsertWordAsyncTask(mWordDao).execute(word);
    }

    public void deleteAllWords() {
        new DeleteAllWordAsyncTask(mWordDao).execute();
    }

    public void deleteWord(Word word) {
        new DeleteWordAsyncTask(mWordDao).execute(word);
    }

    public void updateWord(Word word) {
        new UpdateWordAsyncTask(mWordDao).execute(word);
    }

    private class InsertWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mDao;

        public InsertWordAsyncTask(WordDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mDao.insertWord(words[0]);
            return null;
        }
    }

    private class DeleteAllWordAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao mDao;

        public DeleteAllWordAsyncTask(WordDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            return null;
        }
    }

    private class DeleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mDao;

        public DeleteWordAsyncTask(WordDao dao) {
            mDao = dao;
        }


        @Override
        protected Void doInBackground(Word... words) {
            mDao.deleteWord(words[0]);
            return null;
        }
    }

    private class UpdateWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mDao;

        public UpdateWordAsyncTask(WordDao mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mDao.update(words[0]);
            return null;
        }
    }

}
