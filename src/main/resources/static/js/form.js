$(document).ready(function () {
    var params = new URLSearchParams(window.location.search);
    var mode = params.get('mode') || 'add';
    var id = params.get('id');

    $('#tanggalLahir').attr('max', new Date().toISOString().split('T')[0]);

    initMode(mode, id);

    $('#btnKembali').on('click', function () {
        window.location.href = 'index.html';
    });

    $('#formDataPribadi').on('submit', function (e) {
        e.preventDefault();
        if (mode === 'add') {
            doSimpan();
        } else if (mode === 'edit') {
            doUbah(id);
        }
    });
});

function initMode(mode, id) {
    if (mode === 'add') {
        $('#formTitle').text('Tambah Data Baru');
        $('#btnSimpan').show();
        $('#btnUbah').hide();

    } else if (mode === 'edit') {
        $('#formTitle').text('Edit Data Pribadi');
        $('#btnSimpan').hide();
        $('#btnUbah').show();
        loadFormData(id, false);

    } else if (mode === 'detail') {
        $('#formTitle').text('Detail Data Pribadi');
        $('#btnSimpan').hide();
        $('#btnUbah').hide();
        loadFormData(id, true);
    }
}

function loadFormData(id, disabled) {
    $.get('/api/karyawan/' + id, function (data) {
        $('#nik').val(data.nik).prop('disabled', disabled);
        $('#namaLengkap').val(data.namaLengkap);
        $('input[name="jenisKelamin"][value="' + data.jenisKelamin + '"]').prop('checked', true);
        $('#tanggalLahir').val(data.tanggalLahir);
        $('#alamat').val(data.alamat);
        $('#negara').val(data.negara);

        if (disabled) {
            $('#namaLengkap, #tanggalLahir, #alamat, #negara').prop('disabled', true);
            $('input[name="jenisKelamin"]').prop('disabled', true);
        }
    }).fail(function (xhr) {
        bsAlert('Gagal memuat data: ' + (xhr.responseJSON?.message || 'Terjadi kesalahan'), function () {
            window.location.href = 'index.html';
        });
    });
}

function buildPayload() {
    return {
        nik: parseInt($('#nik').val()),
        namaLengkap: $('#namaLengkap').val().trim(),
        jenisKelamin: $('input[name="jenisKelamin"]:checked').val() || null,
        tanggalLahir: $('#tanggalLahir').val() || null,
        alamat: $('#alamat').val().trim() || null,
        negara: $('#negara').val() || null
    };
}

function validate(payload) {
    if (!payload.nik) {
        bsAlert('NIK tidak boleh kosong');
        return false;
    }
    if (!payload.namaLengkap) {
        bsAlert('Nama Lengkap tidak boleh kosong');
        return false;
    }
    if (!payload.jenisKelamin) {
        bsAlert('Jenis Kelamin harus dipilih');
        return false;
    }
    if (!payload.tanggalLahir) {
        bsAlert('Tanggal Lahir tidak boleh kosong');
        return false;
    }
    var today = new Date();
    var todayStr = today.getFullYear() + '-' + String(today.getMonth() + 1).padStart(2, '0') + '-' + String(today.getDate()).padStart(2, '0');
    if (payload.tanggalLahir > todayStr) {
        bsAlert('Tanggal Lahir tidak boleh di masa depan');
        return false;
    }
    return true;
}

function doSimpan() {
    var payload = buildPayload();
    if (!validate(payload)) return;

    $.ajax({
        url: '/api/karyawan',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        success: function () {
            window.location.href = 'index.html?success=tambah';
        },
        error: function (xhr) {
            bsAlert('Error: ' + (xhr.responseJSON?.message || 'Terjadi kesalahan'));
        }
    });
}

function doUbah(id) {
    var payload = buildPayload();
    if (!validate(payload)) return;

    $.ajax({
        url: '/api/karyawan/' + id,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        success: function () {
            window.location.href = 'index.html?success=ubah';
        },
        error: function (xhr) {
            bsAlert('Error: ' + (xhr.responseJSON?.message || 'Terjadi kesalahan'));
        }
    });
}
