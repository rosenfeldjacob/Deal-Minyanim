$(function () {
  $('[data-toggle="popover"]').popover()
})

$(function () {
  $('.example-popover').popover({
    container: 'body'
  })
})

//$(function () {
//  $('[data-toggle="tooltip"]').tooltip()
//})

$('.popover-dismiss').popover({
  trigger: 'focus'
})

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const start = urlParams.get('st');
if (start == "sh") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-shacharis-tab").classList.add("active");
    document.getElementById("pills-shacharis").classList.add("active", "show");
} else if (start == "mi") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-mincha-tab").classList.add("active");
    document.getElementById("pills-mincha").classList.add("active", "show");
} else if (start == "ar") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-maariv-tab").classList.add("active");
    document.getElementById("pills-maariv").classList.add("active", "show");
} else if (start == "se") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-selichos-tab").classList.add("active");
    document.getElementById("pills-selichos").classList.add("active", "show");
} else if (start == "mr") {
    Array.prototype.forEach.call(document.getElementsByClassName("nav-link"), e => e.classList.remove("active"));
    Array.prototype.forEach.call(document.getElementsByClassName("tab-pane"), e => e.classList.remove("active","show"));
    document.getElementById("pills-megila-tab").classList.add("active");
    document.getElementById("pills-megila").classList.add("active", "show");
}