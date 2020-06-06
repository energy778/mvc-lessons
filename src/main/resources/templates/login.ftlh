<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>

Страница входа
<@l.login "/login" />
<a href="/registration">Добавить нового пользователя</a>

</@c.page>
