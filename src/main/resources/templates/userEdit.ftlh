<#import "parts/common.ftlh" as c>
<@c.page>

    Редактирование пользователя

    <form action="/user" method="post">
        <input type="text" name="newUsername" value="${user.username}">
        <#list allRoles as role>
            <div>
                <#--                чекбокс-->
                <label>
                    <input type="checkbox" name="${role}" ${user.roles ? seq_contains(role) ? string("checked", "")}>
                    ${role}
                </label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Сохранить</button>
    </form>

</@c.page>
