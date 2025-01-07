function toggleMenuVisibility() {
    let menu = document.getElementById("menu");
    let hamburger = document.getElementById("hamburger");
    let visibility = menu.style.visibility;

    if (visibility === "visible") {
        menu.style.visibility = "hidden";
        hamburger.src = "https://localhost:8181/icons/menu.png"
    } else {
        menu.style.visibility = "visible";
        hamburger.src = "https://localhost:8181/icons/cross.png"
    }
}

function toggleSearchbarVisibility() {
    let searchbar = document.getElementById("searchbar");
    let lens = document.getElementById("lens");
    let display = searchbar.style.display;

    if (display === "inline") {
        searchbar.style.display = "none";
        lens.src = "https://localhost:8181/icons/search.png"
    } else {
        searchbar.style.display = "inline";
        searchbar.focus();
        lens.src = "https://localhost:8181/icons/cross.png"
    }
}