package oshiru.springboot_template.logging;

public class LoggerFactory {

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }
}
