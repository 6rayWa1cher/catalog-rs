function upsertQuery(href, name, value) {
    const url = new URL(location.href);
    url.searchParams.set(name, value);
    return url.toString();
}

function getCurrentPage() {
    return +(new URL(location.href).searchParams.get("page")) ?? 0;
}

$(document).ready(function () {

    // Check for click events on the navbar burger icon
    $(".navbar-burger").click(function () {

        // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
        $(".navbar-burger").toggleClass("is-active");
        $(".navbar-menu").toggleClass("is-active");

    });

    $(".button-prev-page").click(function () {
        window.location.href =
            upsertQuery(
                location.href,
                "page",
                getCurrentPage() - 1
            );
    });

    $(".button-next-page").click(function () {
        window.location.href =
            upsertQuery(
                location.href,
                "page",
                getCurrentPage() + 1
            );
    });
});