package com.vincode.moviecatalog.utils;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiskIOThreaExecutor implements Executor {

    private final Executor diskIO;

    DiskIOThreaExecutor() {
        diskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        diskIO.execute(command);
    }
}
