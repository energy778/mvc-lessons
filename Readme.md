
Приложение Sweater - Аналог твиттера
***

[канал let's code](https://www.youtube.com/channel/UC1g3kT0ZcSXt4_ZyJOshKJQ)
***

#### Этапы

* подготовка
    * идем на spring.io
    * в гайдах ищем MVC
    * создаём проект maven
    
          
* урок 1 (Hello, World)
    * pom.xml
        * указываем используемую версию java
        * секцию **parent** для spring boot
        * зависимости:
            * thymeleaf (шаблонизатор)
            * starter-web
            * dev-tools (для удобного перезапуска приложения)
            * тестирование
        * а также плагин для сборки
    * добавляем GreetingController
    * а также класс запуска приложения ServingWebContentApplication
    * и страницу greeting.html, которая просто отображает имя, переданное через get-параметр name


* урок 1 (Hello, World). часть 2. замена thymeleaf на mustache (шаблонизатор попроще)
    * изменяем dependency
    * greeting.html на greeting.mustache
        * убираем пространство имен thymeleaf-а
        * изменяем шаблон вывода сообщения
    * в контроллере вместо модели теперь используется Map<String, Object>
    * а также добавлен обработчик для главной страницы
    
    
* урок 2. подключение БД
    * устанавливаем postgres (опционально, вместо MySQL из гайда спринга)
        * [Postgre SQL для Windows](https://www.postgresql.org/download/windows/)
        * если при установке не был запрошен пароль, то по [инструкции](https://overcoder.net/q/9607/%D1%8F-%D0%B7%D0%B0%D0%B1%D1%8B%D0%BB-%D0%BF%D0%B0%D1%80%D0%BE%D0%BB%D1%8C-%D0%BA%D0%BE%D1%82%D0%BE%D1%80%D1%8B%D0%B9-%D0%B2%D0%B2%D0%B5%D0%BB-%D0%BF%D1%80%D0%B8-%D1%83%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B5-postgres) сбрасываем пароль и устанавливаем новый
        * проверяем подключение к БД например через вкладку Database в Intellij IDEA
        * также доступ к БД можно осуществить через консоль pgAdmin
        * основной синтаксис можно посмотреть например [здесь](https://metanit.com/sql/postgresql/1.1.php)
    * добавляем lombok (опционально)
    * ищем гайд подключения БД на сайте спринга (MySQL)
        * добавляем зависимости JPA и postgresql
        * добавляем properties и прописываем настройки подключения БД
        * добавляем package domain для хранения сущностей БД
            * добавляем в него entity "сообщений"
                * для класса entity нужен конструктор по умолчанию
        * добавляем package repo для доступа к сущностям БД с помощью JPA
            * создаем репозиторий MessageRepository на основе CrudRepository
        * выводим на главную страницу список сообщений
            * добавляем форму на страницу ([manual](http://mustache.github.io/mustache.5.html) по mustache)
            * переписываем меппинг main в контроллере, используя MessageRepository
        * даем пользователям возможность добавлять сообщения в БД
            * рисуем форму ввода для добавления сообщений в БД
            * добавляем обработчик в контроллер (при сохранении в БД используем [redirect](https://www.baeldung.com/spring-redirect-and-forward))
        * фильтры сообщений
            * добавляем форму отбора по тегу
            * навешиваем post обработчик
            * добавляем в MessageRepository поиск сообщений по тегу используя [DSL Spring Data](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
            