package com.neko233.common.file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileTreeUtils {

    /**
     * 线性化文件树， Tree -> List
     *
     * @param startDir 需要遍历的文件夹
     * @return file tree -> all file List
     */
    public static List<File> liner(String startDir) {
        List<File> fileList = new ArrayList<>();

        File directory = new File(startDir);
        if (!directory.exists() || !directory.isDirectory()) {
            return fileList;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        for (File file : files) {
            if (file.isDirectory()) {
                // loop
                List<File> liner = liner(file.getPath());
                fileList.addAll(liner);
                continue;
            }
            if (!file.isFile()) {
                log.warn("file = {} not a directory/file.", file.getPath());
                continue;
            }
            if (file.length() == 0) {
                continue;
            }
            fileList.add(file);
        }
        return fileList;
    }

}
