
# DragonBot

DragonBot — это автоматизированный бот для игры [DragonCave](https://dragcave.net/), позволяющий пользователям автоматически ловить интересующих их драконов. Бот работает путем предоставления логина и пароля от аккаунта в игре, позволяя ловить драконов как по выбранным видам, так и по имени с помощью пользовательских фильтров.

## Особенности

- Автоматический поиск и захват драконов по предопределенным фильтрам.
- Возможность использования через веб-интерфейс.
- Поддержка пользовательских фильтров для более точного поиска.

## Технологии

Проект разработан с использованием Java, Spring Boot, Maven для бэкенда и HTML/CSS для фронтенда.

## Установка и запуск

Для работы с проектом вам потребуется [Java](https://java.com/en/download/) и [Maven](https://maven.apache.org/).

1. Клонируйте репозиторий:
   ```
   git clone https://github.com/balalaika7771/DragonBot.git
   ```
2. Перейдите в директорию проекта:
   ```
   cd DragonBot
   ```
3. Соберите проект с помощью Maven:
   ```
   mvn clean install
   ```
4. Запустите приложение:
   ```
   java -jar target/dragonbot-0.0.1-SNAPSHOT.jar
   ```
После запуска приложение будет доступно через веб-браузер на порту `8080`.

## Использование

После запуска перейдите по адресу `http://localhost:8080` в вашем браузере. Для использования бота вам необходимо войти в систему, используя ваш логин и пароль от DragonCave.

### Настройка фильтров

Для настройки фильтров перейдите в раздел "Настройки" в веб-интерфейсе. Здесь вы можете указать критерии поиска драконов, такие как вид или имя.

## Вклад в проект

Мы приветствуем любой вклад в развитие проекта. Если вы хотите помочь, пожалуйста, ознакомьтесь с текущими задачами в разделе Issues на GitHub или предложите свои идеи через Pull Requests.

## Лицензия

Проект распространяется под лицензией MIT [LICENSE](LICENSE).
