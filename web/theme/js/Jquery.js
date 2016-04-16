$(document).ready(function() {
    $(".clients").hide();
    $(".comptes").hide();
    $(".fermer").hide();
});
$(".choisirCompte").click(function() {
    $(".choisirCompte").slideUp();
    $(".fermer").show("slow");
    $(".comptes").hide();
    $(".clients").show("slow");
});
$(".fermer").click(function() {
    $(".choisirCompte").show("slow");
    $(".fermer").slideUp("slow");
    $(".clients").slideUp("slow");
});
function Affiche(Class) {
    $(".comptes").slideUp("slow");
    $(Class).slideDown("slow");
}