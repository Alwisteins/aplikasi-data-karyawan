function bsAlert(message, onClose) {
    $('#modalAlertBody').text(message);
    var modalEl = document.getElementById('modalAlert');
    var modal = bootstrap.Modal.getOrCreateInstance(modalEl);
    if (onClose) {
        $(modalEl).one('hidden.bs.modal', onClose);
    }
    modal.show();
}

function bsConfirm(message, onConfirm) {
    $('#modalConfirmBody').text(message);
    var modalEl = document.getElementById('modalConfirm');
    var modal = bootstrap.Modal.getOrCreateInstance(modalEl);
    $('#btnConfirmYes').off('click').on('click', function () {
        modal.hide();
        onConfirm();
    });
    modal.show();
}
