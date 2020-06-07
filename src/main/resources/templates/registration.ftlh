<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>

<div>Всем привет</div>
<a href="/main">Главная</a>

Добавление нового пользователя
${message!" "}
<@l.login "/registration" />

</@c.page>
