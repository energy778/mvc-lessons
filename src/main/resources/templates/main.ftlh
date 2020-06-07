<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>

    <div>
        <@l.logout />
        <span><a href="/user">Список пользователей</a> </span>
    </div>

    <div>
        <form method="post" action="/main" enctype="multipart/form-data">
            <input type="text" name="text" placeholder="Введите сообщение" />
            <input type="text" name="tag" placeholder="Тег" />
            <input type="file" name="file">
            <button type="submit">Добавить сообщение</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form>
    </div>

    <div>Список сообщений</div>

    <div>
        <form method="get" action="/main">
            <input type="text" name="filter" value="${filter!}">
            <button type="submit">Найти</button>
        </form>
    </div>

    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <div>
                <#if message.filename??>
                    <img src="/img/${message.filename}">
                </#if>
            </div>
        </div>
    <#else>
        No messages
    </#list>

</@c.page>

<!--

Чтобы отправлять AJAX запросы (ну вдруг просто кто-то захочет их внедрять), в тег <head> в common.ftlh нужно добавить
<script src="//ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<script>
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });
</script>

-->