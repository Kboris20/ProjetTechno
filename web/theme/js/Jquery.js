function getClients() {
    $.get("GetClients", function(responseText) {
        $("#resultat").html(responseText);
    });
}

$(document).ready(function() {
    $(".clients").hide();
    $(".comptes").hide();
    $(".fermer").hide();
    $(".add").hide();
    getClients();
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

$(".addC").click(function() {
    $(".add").show("slow");
    $(".addC").slideUp("slow");
});


$(".retour").click(function() {
    $(".addC").show("slow");
    $(".add").slideUp("slow");
});