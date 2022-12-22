package com.neko233.common.file;


import org.apache.commons.io.Charsets;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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


}
