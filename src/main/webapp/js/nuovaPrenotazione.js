const calendar = $('#calendar');

const today = new Date().toISOString().split('T')[0];

calendar.attr('min',today);

calendar.on('change',function () {

    const date = calendar.val();

    if (date === today){
        $('#btnPrenotazioneSingola').show();
        $('#btnPrenotazioneGruppo').hide();
        $('#dateInputSingolo').attr('value',date);
    }else if (date > today){
        $('#btnPrenotazioneSingola').show();
        $('#btnPrenotazioneGruppo').show();
        $('#dateInputSingolo').attr('value',date);
        $('#dateInputGruppo').attr('value',date);
    }else{
        $('#btnPrenotazioneSingola').hide();
        $('#btnPrenotazioneGruppo').hide();
        $('#dateInputSingolo').attr('value',"");
        $('#dateInputGruppo').attr('value',"");
    }

})

$('#btnPrenotazioneSingola').click(function (){

})

$('#btnPrenotazioneGruppo').click(function (){

})