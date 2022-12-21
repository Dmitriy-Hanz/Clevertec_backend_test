# Clevertec_backend_test
### Консольное приложение, реализующее функционал формирования чека в магазине
## Общая информация
Версия JDK: 17
Сборщик проектов: Gradle 7.5
Фреймворки: Spring Boot, JUnit

## Инструкция по эксплуатации
Программа запускается консольной командой "java -jar Receipt-1.0-SNAPSHOT.jar " с передаваемыми аргументами.
Список возможных параметров:
- file-<имя файла с расширением>
- filepath-<полный путь к файлу с расшрирением>
- список параметров в формате "<штрихкод товара>-<количество>" через пробел, опционально указание параметра "card-<номер карты>"
Результатом работы программы будет вывод в консоль чека, а также его печать в файл с названием "receipt.txt"
