<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<custom:page title="${title}">
    <div class="section">
        <div class="container">
            <div class="level">
                <div class="level-left">
                    <div class="level-item">
                        <h1 class="title">
                                ${title}
                        </h1>
                    </div>
                </div>
                <c:if test="${categoryId != null}">
                    <div class="level-right">
                        <div class="level-item">
                            <form:form method="post"
                                       action="${pageContext.request.contextPath}/products/categories/${categoryId}/delete.do">
                                <button type="submit" class="button is-danger is-outlined">Удалить</button>
                            </form:form>
                        </div>
                    </div>
                </c:if>
            </div>
            <form:form method="post" action="${actionUrl}"
                       modelAttribute="category">
                <div class="field">
                    <form:label path="title" cssClass="label">Название</form:label>
                    <div class="control">
                        <form:input path="title" cssClass="input" type="text" cssErrorClass="input is-danger"/>
                    </div>
                    <form:errors path="title" cssClass="help is-danger" element="p"/>
                </div>
                <div class="field">
                    <form:label path="description" cssClass="label">Описание</form:label>
                    <div class="control">
                        <form:input path="description" cssClass="input" type="text" cssErrorClass="input is-danger"/>
                    </div>
                    <form:errors path="description" cssClass="help is-danger" element="p"/>
                </div>
                <div class="field is-grouped">
                    <div class="control">
                        <button type="submit" class="button is-link">Отправить</button>
                    </div>
                    <div class="control">
                        <button type="reset" class="button is-link is-light">Стереть</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</custom:page>