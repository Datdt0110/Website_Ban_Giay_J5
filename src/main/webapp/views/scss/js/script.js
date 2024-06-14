const hamBurger = document.querySelector(".toggle-btn");

hamBurger.addEventListener("click", function () {
    document.querySelector("#sidebar").classList.toggle("expand");
});


// Initialization for ES Users
import { Input, initMDB } from "mdb-ui-kit";

initMDB({ Input });