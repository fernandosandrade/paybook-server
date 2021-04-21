package org.paybook.com.db;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Provide default constructor for firebase collection path
 */
public final class CollectionPath {

    private static final String DELIMITER = "/";

    private final String path;

    private CollectionPath(String path) {
        this.path = path;
    }

    /**
     * Return new collection path based on the paths entered.
     * <p>
     * Do not enter the delimiter, only the paths.
     * </p>
     * For the following enter
     * <pre>
     * {@code CollectionPath.of("cobranca", "101", "111")}
     * </pre>
     * produces
     * <pre>
     * {@code cobrancas/101/111}
     * </pre>
     *
     * @param path path
     * @param more others paths
     * @return
     */
    public static final CollectionPath of(String path, @Nullable String... more) {
        Objects.requireNonNull(path);
        return new CollectionPath(path +
                (more.length != 0 ? DELIMITER + String.join(DELIMITER, more) : ""));
    }

    /** Return the formated path. */
    public final String getPath() {
        return this.path;
    }
}
