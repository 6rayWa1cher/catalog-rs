<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<custom:page title="Категории" stylesheet="/home-page.css">
    <div class="section">
        <div class="container">
            <div class="level">
                <div class="level-left">
                    <div class="level-item">
                        <h1 class="title">
                            Категории
                        </h1>
                    </div>
                </div>
                <div class="level-right">
                    <div class="level-item">
                        <a class="button is-link" href="<c:url value="/products/categories/create"/>">Создать</a>
                    </div>
                </div>
            </div>
            <div class="columns is-multiline">
                <c:forEach items="${categories.content}" var="category">
                    <div class="column is-4-desktop is-3-widescreen is-half-tablet">
                        <div class="card">
                            <div class="card-content">
                                <div class="content">
                                    <h3>${category.title}</h3>
                                    <c:choose>
                                        <c:when test="${category.description != null}">
                                            <p>${category.description}</p>
                                        </c:when>
                                        <c:otherwise>
                                            <i>Описание отсутствует</i>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <footer class="card-footer">
                                <div class="card-footer-item">
                                    <span>
                                        <a href="${pageContext.request.contextPath}/?category=${category.id}">
                                            Перейти
                                        </a>
                                    </span>
                                </div>
                                <div class="card-footer-item">
                                    <span>
                                        <a href="${pageContext.request.contextPath}/products/categories/${category.id}/edit">
                                            Изменить
                                        </a>
                                    </span>
                                </div>
                            </footer>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <custom:page-navigation page="${categories}"/>
        </div>
    </div>
</custom:page>