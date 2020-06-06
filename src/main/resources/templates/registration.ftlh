<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>

<div>Hello, user</div>
<a href="/main">Main page</a>

Добавление нового пользователя
${message!" "}
<@l.login "/registration" />

</@c.page>
