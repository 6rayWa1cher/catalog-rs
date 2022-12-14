<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<custom:page title="${product.title}" stylesheet="/product-page.css">
    <section class="section">
        <div class="container content-wrapper">
            <div class="level">
                <div class="level-left">
                    <div class="level-item">
                        <h1 class="title is-1">
                                ${product.title}
                        </h1>
                    </div>
                </div>
                <div class="level-right">
                    <div class="level-item">
                        <h2 class="subtitle is-3">
                                ${product.priceAmount}RUB
                        </h2>
                    </div>
                    <div class="level-item">
                        <a class="button is-link is-outlined" href="<c:url value="/products/${product.id}/edit"/>">Изменить</a>
                    </div>
                </div>
            </div>
            <div class="columns">
                <div class="column is-two-thirds">
                    <figure class="image image-half-height">
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
                <div class="column is-one-third">
                    <table class="table">
                        <tbody>
                        <tr>
                            <th>Категория</th>
                            <td>
                                    ${category != null ? category.title : "Некатегоризирован"}
                            </td>
                        </tr>
                        <tr>
                            <th>Статус</th>
                            <td>
                                <custom:product-status status="${product.status}"/>
                            </td>
                        </tr>
                        <tr>
                            <th>Создан</th>
                            <td>${product.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))}</td>
                        </tr>
                        </tbody>
                    </table>
                    <h3 class="is-size-4">
                        Описание
                    </h3>
                    <c:choose>
                        <c:when test="${product.description != null}">
                            <p>
                                    ${fn:replace(product.description, newLineChar, '<br/>')}
                            </p>
                        </c:when>
                        <c:otherwise>
                            <i>Описание отсутствует</i>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>
</custom:page>