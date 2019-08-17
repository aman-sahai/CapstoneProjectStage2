package com.example.searchjob;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class JobViewModel extends AndroidViewModel {

    Repository movieRepository;

    private LiveData<List<Savejob>> listLiveData;

    public JobViewModel(Application application) {
        super(application);
        movieRepository = new Repository(application);
        listLiveData = movieRepository.listLiveData();
    }


    public Savejob findJobData(String id) {
        Savejob favMovieData = movieRepository.findJob(id);
        return favMovieData;
    }

    public LiveData<List<Savejob>> getListLiveData() {
        return listLiveData;
    }


    public void insert(Savejob saveJobData) {
        movieRepository.insertData(saveJobData);
    }

    public void deleteData(Savejob saveJobData) {
        movieRepository.deleteData(saveJobData);
    }

}
