package ru.otus.october.http.server;

public class Application {
    // Домашнее задание:
    // 1. Избавиться от sout'ов и подключить логгер
    // 2. Сделайте парсинг заголовков запросов в Map<String, String>
    // 3. * Добавьте обработку 405 Method Not Allowed

    public static void main(String[] args) {
        new HttpServer(8189).start();
    }
}
