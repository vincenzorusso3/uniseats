var emailGlobal = false;


function emailObserver() {

    var email = $('#email');
    var label = $('#email_l');

    var email_text = email.val()

    var reg = new RegExp(/^([a-zA-Z0-9_.+-])+\@studenti.unisa.it/);

    if (!reg.test(email_text) || email_text.length < 10 || email_text.length > 40) {
        email.css('border-color', 'red');
        label.css('color', 'red');
        label.text("Inserire email istituzionale!");

        emailGlobal = false;

    } else {
        label.text("Email istituzionale");
        label.css('color', '#9E9E9E');
        email.css('border-color', 'lightgray');

        emailGlobal=  true;
    }
}

function nomeObserver() {

    var nome = $('#nome');
    var label = $('#nome_l');

    var nome_text = nome.val();

    var reg = new RegExp(/^[a-zA-Z ]{1,20}$/);

    if (!reg.test(nome_text)) {
        nome.css('border-color', 'red');
        label.css('color', 'red');
        label.text("Nome non valido!");

        return false;

    } else {
        label.text("Nome");
        label.css('color', '#9E9E9E');
        nome.css('border-color', 'lightgray');

        return true;
    }
}


function cognomeObserver() {

    var cognome = $('#cognome');
    var label = $('#cognome_l');

    var cognome_text = cognome.val();

    var reg = new RegExp(/^[a-zA-Z ]{1,20}$/);

    if (!reg.test(cognome_text)) {
        cognome.css('border-color', 'red');
        label.css('color', 'red');
        label.text("Cognome non valido!");

        return false;
    } else {
        label.text("Cognome");
        label.css('color', '#9E9E9E');
        cognome.css('border-color', 'lightgray');

        return true;
    }

}

function passwordObserver() {

    var password = $('#password');
    var label = $('#password_l');
    var password_text = password.val();

    var reg = new RegExp( /^[a-zA-Z0-9!@#%*)(+=._-]{8,20}$/g);

    if (!reg.test(password_text)) {
        password.css('border-color', 'red');
        label.css('color', 'red');
        label.text("Password non valida, mimino 8 caratteri!");


        return false;

    } else {
        label.text("Password");
        label.css('color', '#9E9E9E');
        password.css('border-color', 'lightgray');


        return true;

    }

}


function matricolaObserver() {

    var matricola = $('#matricola');
    var label = $('#matricola_l');
    var matricola_text = matricola.val();

    var reg = new RegExp(/^.{10}$/);

    if (!reg.test(matricola_text)) {
        matricola.css('border-color', 'red');
        label.css('color', 'red');
        label.text("Matricola non valida!");


        return false;

    } else {
        label.text("Matricola");
        label.css('color', '#9E9E9E');
        matricola.css('border-color', 'lightgray');


        return true;

    }

}





$(document).on("submit", "#registrazione", function (event) {

    emailObserver();
    let nome = nomeObserver();
    let cognome = cognomeObserver();
    let password = passwordObserver();
    let matricola= matricolaObserver();

    if (emailGlobal && nome && cognome && password && matricola){

        return true;
    } else {
        return false;
    }
});







