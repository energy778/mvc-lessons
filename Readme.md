
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
