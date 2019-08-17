package com.example.searchjob;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Repository { private MyDao jobDao;
    private LiveData<List<Savejob>> saveJobData;

    public Repository(Application application) {
        AppData database = AppData.getDatabase(application);
        jobDao = database.myDao();
        saveJobData = jobDao.getall();
    }

    public Savejob findJob(String id) {
        Savejob jobData = jobDao.search(id);
        return jobData;
    }

    public LiveData<List<Savejob>> listLiveData() {
        return saveJobData;
    }

    public void insertData(Savejob saveJobData) {
        new MyTask(jobDao).execute(saveJobData);
    }

    public void deleteData(Savejob saveJobData) {
        new MyDeleteTask(jobDao).execute(saveJobData);
    }

    public class MyTask extends AsyncTask<Savejob, Void, Void> {

        public MyDao mDao;

        public MyTask(MyDao jobDao) {
            mDao = jobDao;
        }

        @Override
        protected Void doInBackground(Savejob... saveJobData) {
            mDao.insert(saveJobData[0]);
            return null;
        }
    }

    public class MyDeleteTask extends AsyncTask<Savejob, Void, Void> {

        public MyDao dao;

        public MyDeleteTask(MyDao jobDao) {
            dao = jobDao;
        }

        @Override
        protected Void doInBackground(Savejob... saveJobData) {
            dao.delete(saveJobData[0]);
            return null;
        }
    }

}
