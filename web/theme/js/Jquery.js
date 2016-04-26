//###################################### fonctions privées ##########################################################

function popup() {
    // ici on insère dans notre page html notre div gris
    $(".customPopup").before('<div class="grayBack"></div>');
//    $(".customPopup").show("slow");
//    
    // maintenant, on récupère la largeur et la hauteur de notre popup
    var popupH = $(".customPopup").height();
    var popupW = $(".customPopup").width();

    // ensuite, on crée des marges négatives pour notre popup, chacune faisant la moitié de la largeur/hauteur du popup
    $(".customPopup").css("margin-top", -(popupH / 2, 40), "px");
    $(".customPopup").css("margin-left", -popupW / 2, "px");

    // enfin, on fait apparaître en 300 ms notre div gris de fond, et une fois son apparition terminée, on fait apparaître en fondu notre popup
    $(".grayBack").css('opacity', 0).fadeTo(300, 0.5, function() {
        $(".customPopup").fadeIn(500);
    });
}

function doLoader() {
    $(".customPopup").html('<center><img src=\"http://localhost:8080/crud/theme/img/ajax-loader.gif\" alt=\"Loading\"/></center>');
}

//############################################## Un peu d'ajax ##########################################################
function getTransfers() {
    $(".resultatTransactions").html('<center><img src=\"http://localhost:8080/crud/theme/img/ajax-loader1.gif\" alt=\"Loading\"/></center>');
    $.get("TransactionAjax", function(responseText) {
        $(".resultatTransactions").html(responseText);
    });
}

function addAccount(parameter) {
    doLoader();
    popup();

    $.get("addAccount.jsp", {idCli: parameter})
            .done(function(responseText) {
                $(".customPopup").html(responseText);
            });
}

function modify(p_id) {
    doLoader();
    popup();

    $.get("modifier", {id: p_id})
            .done(function(responseText) {
                $(".customPopup").html(responseText);
            });
}


function modAccount(p_id, p_idCli) {
    doLoader();
    popup();

    $.get("modifierCompte", {id: p_id, idCli: p_idCli})
            .done(function(responseText) {
                $(".customPopup").html(responseText);
            });
}
//####################################################################################################################

//Au chargement d'une page si des div sous-mentionnée y sont contenues, elle de s'afficheront pas;
// l'autre méthode serait de mettre un attribut display:none; dans le CSS pour ces div.
$(document).ready(function() {
    $(".clients").hide();
    $(".comptes").hide();
    $(".fermer").hide();
    $(".add").hide();
    getTransfers();

    setTimeout(function() {
        $(".popupInformation").fadeOut('fast');
    }, 5000);
});

function affiche(Class) {
    $(".comptes").slideUp("slow");
    $(Class).slideDown("slow");
}

//#################################################### Liste des clients ###################################################

// Quand on click sur le (+) pour l'ajout d'un nouveau client
$(".addC").click(function() {
    //On affiche le formulaire d'ajout et on cache le boutton (+) l'inverse pour le retour
    $(".add").show("slow");
    $(".addC").slideUp("slow");
});


$(".retour").click(function() {
    $(".addC").show("slow");
    $(".add").slideUp("slow");
});


//#######################################################################################################################


/*################################### Popup customiser pour le choix des comptes ###########################################*/


function showPopup() {
    //On fait d'abord disparaitre notre boutton choisir
    $(".choisirCompte").fadeOut('fast', function() {
        $(this).hide();
    });

    //On fait en sorte qu'aucun compte de s'affiche quand on click sur choisir un compte
    $(".comptes").hide();

    popup();

}

function hidePopup() {
    $(".choisirCompte").fadeIn(500);

    // on fait disparaître le fond gris rapidement
    $(".grayBack").fadeOut('fast', function() {
        $(this).remove();
    });

    $(".confirmPopup").fadeOut('fast', function() {
        $(this).hide();
    });

    // on fait disparaître le popup à la même vitesse
    $(".customPopup").fadeOut('fast', function() {
        $(this).hide();
    });
}

//Popup confirmations
$(".confirm").ready(function() {
    $(".confirmPopup").before('<div class="grayBack"></div>');
    var popupH = $(".confirmPopup").height();
    var popupW = $(".confirmPopup").width();

    $(".confirmPopup").css("margin-top", -(popupH / 2, 40), "px");
    $(".confirmPopup").css("margin-left", -popupW / 2, "px");

    $(".grayBack").css('opacity', 0).fadeTo(300, 0.5, function() {
        $(".confirmPopup").fadeIn(500);
    });
});

//###################################################################################################################################
