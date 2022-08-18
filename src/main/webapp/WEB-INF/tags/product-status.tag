<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="product tag name render" pageEncoding="UTF-8" %>
<%@ tag import="com.a6raywa1cher.test.catalogrs.dao.ProductStatus" %>
<%@ attribute name="status" required="true" %>
<c:choose>
    <c:when test="${status == ProductStatus.ACTIVE}">
        Активен
    </c:when>
    <c:when test="${status == ProductStatus.INACTIVE}">
        Неактивен
    </c:when>
    <c:otherwise>
        Статус неизвествен
    </c:otherwise>
</c:choose>