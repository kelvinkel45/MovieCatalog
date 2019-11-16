package com.vincode.moviecatalog.data.source.local.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;

@Database(entities = {MovieEntity.class, TvShowEntity.class},
        version = 1,
        exportSchema = false)
public abstract class CatalogDatabase extends RoomDatabase {

    private static CatalogDatabase INSTANCE;

    public abstract CatalogDao catalogDao();

    private static final Object sLock = new Object();

    public static CatalogDatabase getINSTANCE(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        CatalogDatabase.class, "Catalog.db")
                        .build();
            }
            return INSTANCE;
        }
    }

}
