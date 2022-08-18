<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.a6raywa1cher.test.catalogrs.dao.ProductStatus" %>
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
                <c:if test="${productId != null}">
                    <div class="level-right">
                        <div class="level-item">
                            <form:form method="post"
                                       action="${pageContext.request.contextPath}/products/${productId}/delete.do">
                                <button type="submit" class="button is-danger is-outlined">Удалить</button>
                            </form:form>
                        </div>
                    </div>
                </c:if>
            </div>
            <form:form method="post" action="${actionUrl}" modelAttribute="product" enctype="multipart/form-data">
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
                        <form:textarea path="description" cssClass="textarea" type="text"
                                       cssErrorClass="textarea is-danger" rows="10"/>
                    </div>
                    <form:errors path="description" cssClass="help is-danger" element="p"/>
                </div>
                <div class="field">
                    <form:label path="priceAmount" cssClass="label">Цена</form:label>
                    <div class="control">
                        <form:input path="priceAmount" cssClass="input" type="number" min="0" step="0.01"
                                    cssErrorClass="input is-danger" placeholder="99.99"/>
                    </div>
                    <form:errors path="priceAmount" cssClass="help is-danger" element="p"/>
                </div>
                <div class="field">
                    <form:label path="category" cssClass="label">Категория</form:label>
                    <div class="control">
                        <div class="select is-fullwidth">
                            <form:select path="category">
                                <form:options items="${categories}"/>
                            </form:select>
                        </div>
                    </div>
                    <form:errors path="category" cssClass="help is-danger" element="p"/>
                </div>
                <div class="field">
                    <form:label path="image" cssClass="label">Изображение</form:label>
                    <div class="control">
                        <form:input path="image" cssClass="input" type="file" cssErrorClass="input is-danger"/>
                    </div>
                    <form:errors path="image" cssClass="help is-danger" element="p"/>
                </div>
                <div class="field">
                    <form:label path="status" cssClass="label">Статус</form:label>
                    <div class="control">
                        <div class="select is-fullwidth">
                            <form:select path="status">
                                <c:forEach items="${ProductStatus.values()}" var="status">
                                    <form:option value="${status}">
                                        <custom:product-status status="${status}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <form:errors path="status" cssClass="help is-danger" element="p"/>
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