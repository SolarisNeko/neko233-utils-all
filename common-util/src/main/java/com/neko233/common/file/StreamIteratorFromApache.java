package com.neko233.common.file;

import java.io.Closeable;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Wraps and presents a stream as a closable iterator resource that automatically closes itself when reaching the end
 * of stream.
 *
 * @param <E> The stream and iterator type.
 * @since 2.9.0
 */
class StreamIteratorFromApache<E> implements Iterator<E>, Closeable {


    private final Iterator<E> iterator;
    private final Stream<E> stream;

    private StreamIteratorFromApache(final Stream<E> stream) {
        this.stream = Objects.requireNonNull(stream, "stream");
        this.iterator = stream.iterator();
    }


    /**
     * Wraps and presents a stream as a closable resource that automatically closes itself when reaching the end of
     * stream.
     * <h2>Warning</h2>
     * <p>
     * In order to close the stream, the call site MUST either close the stream it allocated OR call the iterator until
     * the end.
     * </p>
     *
     * @param <T>    The stream and iterator type.
     * @param stream The stream iterate.
     * @return A new iterator.
     */
    @SuppressWarnings("resource") // Caller MUST close or iterate to the end.
    public static <T> Iterator<T> iterator(final Stream<T> stream) {
        return new StreamIteratorFromApache<>(stream).iterator;
    }


    @Override
    public boolean hasNext() {
        final boolean hasNext = iterator.hasNext();
        if (!hasNext) {
            close();
        }
        return hasNext;
    }

    @Override
    public E next() {
        final E next = iterator.next();
        if (next == null) {
            close();
        }
        return next;
    }

    /**
     * Closes the underlying stream.
     */
    @Override
    public void close() {
        stream.close();

    }

}
