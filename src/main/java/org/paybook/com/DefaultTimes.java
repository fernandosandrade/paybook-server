package org.paybook.com;

import java.time.Instant;

public abstract class DefaultTimes {

    /**
     * Default {@code now()} for document objects.
     * <p>
     * Truncate {@code now} on {@code ChronoUnit.MILLIS}
     * </p>
     *
     * @return
     */
    public static final Instant now() {
        return Instant.now();
    }
//    public static final Instant now() {
//        return Instant.now().truncatedTo(ChronoUnit.MILLIS);
//    }
}
