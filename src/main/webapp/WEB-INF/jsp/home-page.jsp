<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="example" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<example:page title="Home page">
    <%--    <c:forEach items="${products.content}" var="product">--%>
    <%--        <aside>--%>
    <%--            <c:if test="${product.imageUrl != null}">--%>
    <%--                <img src="${product.imageUrl}" height="300" alt="${product.title} picture"/>--%>
    <%--            </c:if>--%>
    <%--            <h3>${product.title}</h3>--%>
    <%--            <p>${product.priceAmount}RUB</p>--%>
    <%--            <p>${product.status}</p>--%>
    <%--        </aside>--%>
    <%--    </c:forEach>--%>
    <section class="section">
        <div class="container content-wrapper">
            <h1 class="title">
                Продукты
            </h1>
            <div class="columns is-multiline">
                <c:forEach items="${products.content}" var="product">
                    <div class="column is-4-desktop is-3-widescreen is-half-tablet">
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
                                    <p>${product.status}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
</example:page>