const menu = document.querySelector(".navbar__menu");
const burger = document.querySelector(".navbar__burger");

burger.addEventListener("click", () => {
    menu.classList.toggle('active');
})