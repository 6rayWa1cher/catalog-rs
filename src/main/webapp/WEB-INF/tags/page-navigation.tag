<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ tag description="page navigation buttons" pageEncoding="UTF-8" %>
<%@ tag import="com.a6raywa1cher.test.catalogrs.utils.JspUtils" %>
<%@ attribute name="page" required="true" type="org.springframework.data.domain.Page" %>
<div class="field has-addons">
    <p class="control">
        <button class="button button-prev-page" ${page.first ? "disabled" : ""}>
          <span class="icon is-small">
            <img src="<c:url value="/images/icons/chevron-left.svg"/>" alt="Left icon"/>
          </span>
        </button>
    </p>
    <p class="control">
        <button class="button" disabled>
            <span>
                ${page.number + 1}
            </span>
        </button>
    </p>
    <p class="control">
        <button class="button button-next-page" to="${
        JspUtils.upsertQueryParam(requestScope["javax.servlet.forward.request_uri"], "page", "" + (page.number + 1))
        }" ${page.last ? "disabled" : ""}>
          <span class="icon is-small">
            <img src="<c:url value="/images/icons/chevron-right.svg"/>" alt="Left icon"/>
          </span>
        </button>
    </p>
</div>