<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.a6raywa1cher.test.catalogrs.dao.ProductStatus" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<custom:page title="Домашняя страница" stylesheet="/home-page.css">
    <section class="section">
        <div class="container content-wrapper">
            <div class="level">
                <div class="level-left">
                    <div class="level-item">
                        <h1 class="title">
                            Продукты
                        </h1>
                    </div>
                </div>
                <div class="level-right">
                    <div class="level-item">
                        <a class="button is-link is-outlined" href="<c:url value="/products/create"/>">Создать</a>
                    </div>
                </div>
            </div>
            <form:form method="get" modelAttribute="searchForm">
                <div class="field is-grouped">
                    <div class="control is-expanded">
                        <form:input path="title" cssClass="input" placeholder="лампочка"/>
                    </div>
                    <div class="control">
                        <input type="submit" class="button is-link" value="Найти"/>
                    </div>
                </div>
                <div class="field is-horizontal">
                    <div class="field-label is-normal">
                        <label class="label">Цена</label>
                    </div>
                    <div class="field-body">
                        <div class="field">
                            <p class="control is-expanded">
                                <form:input path="fromPriceAmount" cssClass="input" placeholder="От"/>
                            </p>
                        </div>
                        <div class="field">
                            <p class="control is-expanded">
                                <form:input path="toPriceAmount" cssClass="input" placeholder="До"/>
                            </p>
                        </div>
                    </div>
                </div>
                <div class="field is-horizontal">
                    <div class="field-label is-normal">
                        <form:label path="category" cssClass="label">Категория</form:label>
                    </div>
                    <div class="field-body">
                        <div class="field is-narrow">
                            <div class="control">
                                <div class="select is-fullwidth">
                                    <form:select path="category">
                                        <form:option value="" label="Любая"/>
                                        <form:options items="${categories}"/>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="field is-horizontal">
                    <div class="field-label is-normal">
                        <form:label path="status" cssClass="label">Статус</form:label>
                    </div>
                    <div class="field-body">
                        <div class="field is-narrow">
                            <div class="control">
                                <div class="select is-fullwidth">
                                    <form:select path="status">
                                        <form:option value="" label="Любой"/>
                                        <c:forEach items="${ProductStatus.values()}" var="status">
                                            <form:option value="${status}">
                                                <custom:product-status status="${status}"/>
                                            </form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </section>
    <section class="section">
        <div class="container content-wrapper">
            <div class="columns is-multiline">
                <c:forEach items="${products.content}" var="product">
                    <div class="column is-4-desktop is-3-widescreen is-half-tablet">
                        <a href="${pageContext.request.contextPath}/products/${product.id}">
                            <div class="card">
                                <div class="card-image">
                                    <figure class="image">
                                        <img src="<c:choose>
                                            <c:when test="${product.imageUrl != null}">
                                                ${product.imageUrl}
                                            </c:when>
                                            <c:otherwise>
                                                <c:url value="/images/no_image_available.svg" />
                                            </c:otherwise>
                                        </c:choose>" alt="${product.title} picture"/>
                                    </figure>
                                </div>
                                <div class="card-content">
                                    <div class="content">
                                        <h3>${product.title}</h3>
                                        <p>${product.priceAmount}RUB</p>
                                        <p><custom:product-status status="${product.status}"/></p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
            <div>
                <custom:page-navigation page="${products}"/>
            </div>
        </div>
    </section>
</custom:page>