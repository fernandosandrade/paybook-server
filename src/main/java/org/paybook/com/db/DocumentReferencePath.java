package org.paybook.com.db;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Provide default constructor for firebase document reference path.
 * <p>
 * CollectionPath can refer to a collection or to a document, since in firestore a document can be a collection too.
 * </p>
 */
public final class DocumentReferencePath {

    private static final String DELIMITER = "/";

    private final String path;

    private DocumentReferencePath(String path) {
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
    public static final DocumentReferencePath of(String path, @Nullable String... more) {
        Objects.requireNonNull(path);
        return new DocumentReferencePath(path +
                (more.length != 0 ? DELIMITER + String.join(DELIMITER, more) : ""));
    }

    /** Return the formated path. */
    public final String getPath() {
        return this.path;
    }
}
