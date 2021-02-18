package org.paybook.com.exception;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.text.MessageFormat;
import java.util.Optional;

public class ExceptionFactory {

    private static Config propertiesConfig;

    public ExceptionFactory() {
        ExceptionFactory.propertiesConfig = ConfigProvider.getConfig();
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    public static RuntimeException throwException(String messageTemplate, String... args) {
        return new RuntimeException(format(messageTemplate, args));
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    public static RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, String... args) {
        String messageTemplate = getMessageTemplate(entityType, exceptionType);
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    public static RuntimeException throwExceptionWithId(EntityType entityType, ExceptionType exceptionType, String id, String... args) {
        String messageTemplate = getMessageTemplate(entityType, exceptionType).concat(".").concat(id);
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType, messageTemplate and args
     *
     * @param entityType
     * @param exceptionType
     * @param messageTemplate
     * @param args
     * @return
     */
    public static RuntimeException throwExceptionWithTemplate(EntityType entityType, ExceptionType exceptionType, String messageTemplate, String... args) {
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    private static RuntimeException throwException(ExceptionType exceptionType, String messageTemplate, String... args) {
        if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
            return new EntityNotFoundException(format(messageTemplate, args));
        } else if (ExceptionType.DUPLICATE_ENTITY.equals(exceptionType)) {
            return new DuplicateEntityException(format(messageTemplate, args));
        }
        return new RuntimeException(format(messageTemplate, args));
    }

    private static String getMessageTemplate(EntityType entityType, ExceptionType exceptionType) {
        return entityType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
    }

    private static String format(String template, String... args) {
        Optional<String> templateContent = propertiesConfig.getOptionalValue(template, String.class);
        if (templateContent.isPresent()) {
            return MessageFormat.format(templateContent.get(), (Object[]) args);
        }
        return String.format(template, (Object[]) args);
    }

    public static class ApplicationException extends RuntimeException {
        public ApplicationException(String message) {
            super(message);
        }
    }

    public static class EntityNotFoundException extends ApplicationException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateEntityException extends ApplicationException {
        public DuplicateEntityException(String message) {
            super(message);
        }
    }

}
