<#import "parts/common.ftlh" as c>

<@c.page>

    <h5>${username}</h5>
    ${message!}

<#--    скопировали из parts/login. почему не переиспользовали? потому что отличается специфика и в дальнейшем могут разойтись еще сильнее -->
    <form method="post">
<#--        убрали action, потому что форма будет отправляться на тот же адрес, с которого она пришла -->
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль:</label>
            <div class="col-sm-6">
                <input class="form-control" type="password" name="password" placeholder="Пароль"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email:</label>
            <div class="col-sm-6">
                <input class="form-control" type="email" name="email" placeholder="some@some.com" value="${email!''}"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">
            Сохранить
        </button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>

</@c.page>
