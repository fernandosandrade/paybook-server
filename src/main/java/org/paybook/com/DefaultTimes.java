package org.paybook.com;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
        return Instant.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
