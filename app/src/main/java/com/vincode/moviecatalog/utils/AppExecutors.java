package com.vincode.moviecatalog.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.VisibleForTesting;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final int THREAD_COUNT = 3;

    private final Executor diskIO;

    private final Executor networkIO;

    private final Executor mainThread;

    @VisibleForTesting
    AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public AppExecutors() {
        this(new DiskIOThreaExecutor(), Executors.newFixedThreadPool(THREAD_COUNT), new MainThreadExecutor());
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NotNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }

}
