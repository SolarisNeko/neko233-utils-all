package com.neko233.common.file;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SolarisNeko
 */
@Slf4j
public class FileUtils233 {

    public static List<String> readLines(final File file, final Charset charset) throws IOException {
        try (InputStream inputStream = openInputStream(file)) {
            return readLines(inputStream, chooseCharset(charset));
        }
    }

    public static List<String> readLines(final InputStream input, final Charset charset) throws IOException {
        final InputStreamReader reader = new InputStreamReader(input, chooseCharset(charset));
        return readLines(reader);
    }

    public static List<String> readLines(final Reader reader) throws IOException {
        final BufferedReader bufReader = toBufferedReader(reader);
        final List<String> lineList = new ArrayList<>();
        String line;
        while ((line = bufReader.readLine()) != null) {
            lineList.add(line);
        }
        return lineList;
    }


    // ----------- base ---------------

    public static BufferedReader toBufferedReader(final Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static FileInputStream openInputStream(final File file) throws IOException {
        Objects.requireNonNull(file, "file");
        return new FileInputStream(file);
    }

    private static Charset chooseCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    /**
     * 读取 properties file
     *
     * @param propsFilename 文件名
     * @return Properties
     */
    public static Properties readPropertiesFromFilename(String propsFilename) {
        Properties archProps = new Properties();
        LinkedHashSet<ClassLoader> list = Stream.of(
                        Thread.currentThread().getContextClassLoader(),
                        ClassLoader.getSystemClassLoader(),
                        FileUtils233.class.getClassLoader()
                )
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (ClassLoader loader : list) {
            if (readPropertiesFromClassLoader(propsFilename, archProps, loader)) {
                return archProps;
            }
        }
        log.warn("Failed to load configuration file from classloader: {}", propsFilename);
        return archProps;
    }


    /**
     * ClassLoader 加载 properties 文件
     *
     * @param propsFilename
     * @param archProps
     * @param loader
     * @return
     */
    private static boolean readPropertiesFromClassLoader(String propsFilename, Properties archProps,
                                                         ClassLoader loader) {
        if (loader == null) {
            return false;
        }
        // Load the configuration file from the classLoader
        try {
            List<URL> resources = Collections.list(loader.getResources(propsFilename));
            if (resources.isEmpty()) {
                log.debug("No {} file found from ClassLoader {}", propsFilename, loader);
                return false;
            }
            if (resources.size() > 1) {
                log.warn("Configuration conflict: there is more than one {} file on the classpath: {}", propsFilename,
                        resources);
            }
            try (InputStream in = resources.get(0).openStream()) {
                if (in != null) {
                    archProps.load(in);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
