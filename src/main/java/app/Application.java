package app;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс для запуска Spring приложения
 *
 * @author ViktoriaGatsulia
 * @version 1.0
 */
@SpringBootApplication
public class Application {
    /**
     * Переменная для логирования
     */
    private static final Logger log = Logger.getLogger(Application.class.getName());

    /**
     * Точка входа в программу
     *
     * @param args - список аргументов командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("Start application!");
    }

}
