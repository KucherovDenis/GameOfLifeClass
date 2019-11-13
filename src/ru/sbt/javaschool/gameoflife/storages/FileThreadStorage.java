package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.entities.Equals;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

import java.util.List;
import java.util.concurrent.*;

public class FileThreadStorage extends FileStorage implements StorageCloseable {

    private ExecutorService threadPool;
    private ExecutorCompletionService<GenerationBroker> compService;

    private void init() {
        threadPool = Executors.newCachedThreadPool();
        compService = new ExecutorCompletionService<>(threadPool);
    }

    public FileThreadStorage(String folder, Equals<GenerationBroker> equalsImpl) {
        super(folder, equalsImpl);
        init();
    }

    public FileThreadStorage(String folder, Equals<GenerationBroker> equalsImpl, FileStorageType storageType) {
        super(folder, equalsImpl, storageType);
        init();
    }

    @Override
    public boolean contains(GenerationBroker generation) {
        boolean result = false;
        List<String> files = FileUtils.readFilesFromDirectory(getFolder());


        for (int i = files.size() - 1; i >= 0; i--) {
            String fileName = files.get(i);
            TaskFileLoader job = new TaskFileLoader(fileName);
            compService.submit(job);
        }

        for (int i = files.size() - 1; i >= 0; i--) {
            GenerationBroker current;
            try {
                Future<GenerationBroker> future = compService.take();
                current = future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new GameException(MSG_STORAGE_LOAD, e);
            }

            result = equalsTo(generation, current);
            if (result) {
                threadPool.shutdownNow();
                break;
            }
        }

        return result;
    }

    @Override
    public void close() {
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }
}
