package com.example.searchjob;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao
{
    @Query("select * from  savedjobs")
    LiveData<List<Savejob>> getall();

    @Insert
    void insert(Savejob jobsdata);

    @Delete
    void delete(Savejob jobsdata);

    @Query("select * from savedjobs where id == :id")
    Savejob search(String id);


}
