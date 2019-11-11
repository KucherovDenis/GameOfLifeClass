package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.entities.Equals;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

import java.util.List;
import java.util.concurrent.*;

public class FileThreadStorage extends FileStorage {
    public FileThreadStorage(String folder, Equals<GenerationBroker> equalsImpl) {
        super(folder, equalsImpl);
    }

    public FileThreadStorage(String folder, Equals<GenerationBroker> equalsImpl, FileStorageType storageType) {
        super(folder, equalsImpl, storageType);
    }

    @Override
    public boolean contains(GenerationBroker generation) {
        boolean result = false;
        List<String> files = FileUtils.readFilesFromDirectory(getFolder());

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        ExecutorCompletionService<GenerationBroker> compService = new ExecutorCompletionService<>(threadPool);


        for (int i = files.size() - 1; i >= 0; i--) {
            String fileName = files.get(i);
            ThreadLoader job = new ThreadLoader(fileName);
            compService.submit(job);
        }
        threadPool.shutdown();

        //System.out.println();

        for (int i = files.size() - 1; i >= 0; i--) {
            GenerationBroker current = null;
            try {
                Future<GenerationBroker> future = compService.take();
                current = future.get();
                //System.out.println(current.getCurrentGeneration());
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
}
