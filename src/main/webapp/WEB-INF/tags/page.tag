<%@ tag description="page layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="script" required="false" type="java.lang.String" %>
<%@ attribute name="stylesheet" required="false" type="java.lang.String" %>
<!DOCTYPE html>
<html>
<head>
    <title>${title} - Catalog RS</title>
    <link href="<c:url value="/style/bulma.min.css" />" rel="stylesheet" type="text/css"/>
    <style>
        <c:forTokens var="item" items="${stylesheet}" delims=",">
        @import url("<c:url value="/style"/>${item}");

        </c:forTokens>
    </style>

    <script type="text/javascript" src="<c:url value="/script/jquery-3.6.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/script/script.js"/>"></script>
    <c:forTokens var="item" items="${script}" delims=",">
        <script type="text/javascript" src="<c:url value="/script"/>${item}">
        </script>
    </c:forTokens>
    <jsp:invoke fragment="head"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
</head>
<body>
<nav class="navbar" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="navbar-item" href="${pageContext.request.contextPath}/">
            Catalog RS
        </a>

        <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
        </a>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
        <div class="navbar-start">
            <a class="navbar-item">
                Home
            </a>

            <a class="navbar-item">
                Documentation
            </a>

            <div class="navbar-item has-dropdown is-hoverable">
                <a class="navbar-link">
                    More
                </a>

                <div class="navbar-dropdown">
                    <a class="navbar-item">
                        About
                    </a>
                    <a class="navbar-item">
                        Jobs
                    </a>
                    <a class="navbar-item">
                        Contact
                    </a>
                    <hr class="navbar-divider">
                    <a class="navbar-item">
                        Report an issue
                    </a>
                </div>
            </div>
        </div>

        <div class="navbar-end">
            <div class="navbar-item">
                <div class="buttons">
                    <a class="button is-primary">
                        <strong>Sign up</strong>
                    </a>
                    <a class="button is-light">
                        Log in
                    </a>
                </div>
            </div>
        </div>
    </div>
</nav>
<div class="content">
    <jsp:doBody/>
</div>
</body>
</html>
