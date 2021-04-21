package org.paybook.com;

import javax.validation.groups.Default;

/**
 * Validation groups for bean validation.
 * <pre>
 *  Bean class:
 *  {@code @NotNull(groups = ValidationGroups.Post.class)}
 *
 *  Use:
 *   public void post(@Valid @ConvertGroup(to = ValidationGroups.Post.class) Bean bean)
 * </pre>
 */
public interface ValidationGroups {
    interface Put extends Default {
    }

    interface Post extends Default {
    }

    interface Path extends Default {
    }
}
