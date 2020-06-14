<#macro login path isRegisterForm>

    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Имя пользователя:</label>
            <div class="col-sm-6">
                <input class="form-control" type="text" name="username" placeholder="Имя пользователя"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль:</label>
            <div class="col-sm-6">
                <input class="form-control" type="password" name="password" placeholder="Пароль"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">
            <#if isRegisterForm>
                Создать
            <#else>
                Войти
            </#if>
        </button>
        <#if !isRegisterForm>
            <a href="/registration">Добавить нового пользователя</a>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>

</#macro>

<#macro logout>
<#--    тут нужно условное оформление-->
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit">
            Выйти
        </button>
    </form>
</#macro>
