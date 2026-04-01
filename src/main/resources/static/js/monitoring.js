var currentPage = 0;
var currentKeyword = '';
var pageSize = 10;

$(document).ready(function () {
    var params = new URLSearchParams(window.location.search);
    var success = params.get('success');
    if (success === 'tambah') {
        showSuccessAlert('Data berhasil ditambahkan.');
    } else if (success === 'ubah') {
        showSuccessAlert('Data berhasil diubah.');
    }

    loadData(0);

    $('#btnSearch').on('click', function () {
        currentKeyword = $('#inputNik').val().trim() || $('#inputNama').val().trim();
        loadData(0);
    });

    $('#inputNik, #inputNama').on('keypress', function (e) {
        if (e.which === 13) $('#btnSearch').click();
    });

    $('#btnAdd').on('click', function () {
        window.location.href = 'form.html?mode=add';
    });
});

function loadData(page) {
    currentPage = page;
    var url = currentKeyword
        ? '/api/karyawan/search?keyword=' + encodeURIComponent(currentKeyword) + '&page=' + page + '&size=' + pageSize
        : '/api/karyawan?page=' + page + '&size=' + pageSize;

    $.get(url, function (data) {
        renderTable(data.content, page);
        renderPagination(data);
    }).fail(function () {
        if (currentKeyword) {
            bsAlert('Gagal melakukan pencarian');
        } else {
            $('#tbody').html('<tr><td colspan="9" class="text-center text-danger">Gagal memuat data</td></tr>');
        }
        $('#paginationArea').hide();
    });
}

function renderTable(data, page) {
    var tbody = $('#tbody');
    if (!data || data.length === 0) {
        tbody.html('<tr><td colspan="9" class="text-center text-muted">Tidak ada data</td></tr>');
        return;
    }
    tbody.empty();
    $.each(data, function (i, d) {
        var rowNum = (page * pageSize) + i + 1;
        var $linkDetail = $('<a>').addClass('link-detail').attr('href', 'form.html?mode=detail&id=' + d.id).text('Detail');
        var $linkEdit   = $('<a>').addClass('link-edit').attr('href', 'form.html?mode=edit&id=' + d.id).text('Edit');
        var $linkDelete = $('<a>').addClass('link-delete').attr('href', '#').text('Delete')
                            .data('id', d.id).data('nama', d.namaLengkap);

        var $tr = $('<tr>').append(
            $('<td>').text(rowNum),
            $('<td>').text(d.nik),
            $('<td>').text(d.namaLengkap),
            $('<td>').text(d.umur),
            $('<td>').text(formatDate(d.tanggalLahir)),
            $('<td>').text(d.jenisKelamin),
            $('<td>').text(d.alamat || '-'),
            $('<td>').text(d.negara || '-'),
            $('<td>').addClass('action-col').append($linkDetail, ' ', $linkEdit, ' ', $linkDelete)
        );
        tbody.append($tr);
    });

    $(document).off('click', '.link-delete').on('click', '.link-delete', function (e) {
        e.preventDefault();
        var id = $(this).data('id');
        var nama = $(this).data('nama');
        bsConfirm('Anda yakin menghapus data ' + nama + '?', function () {
            $.ajax({
                url: '/api/karyawan/' + id,
                method: 'DELETE',
                success: function () {
                    loadData(currentPage);
                    showSuccessAlert('Data berhasil dihapus.');
                },
                error: function () {
                    bsAlert('Gagal menghapus data');
                }
            });
        });
    });
}

function renderPagination(pageData) {
    var totalElements = pageData.totalElements;
    var totalPages = pageData.totalPages;
    var page = pageData.number;
    var size = pageData.size;

    if (totalElements === 0) {
        $('#paginationArea').hide();
        return;
    }

    var from = page * size + 1;
    var to = Math.min(from + pageData.numberOfElements - 1, totalElements);
    $('#paginationInfo').text('Menampilkan ' + from + '-' + to + ' dari ' + totalElements + ' data');

    var $ul = $('#paginationControls').empty();

    var $prev = $('<li>').addClass('page-item').toggleClass('disabled', page === 0)
        .append($('<a>').addClass('page-link').attr('href', '#').text('‹').on('click', function (e) {
            e.preventDefault();
            if (page > 0) loadData(page - 1);
        }));
    $ul.append($prev);

    for (var i = 0; i < totalPages; i++) {
        (function (p) {
            var $li = $('<li>').addClass('page-item').toggleClass('active', p === page)
                .append($('<a>').addClass('page-link').attr('href', '#').text(p + 1).on('click', function (e) {
                    e.preventDefault();
                    loadData(p);
                }));
            $ul.append($li);
        })(i);
    }

    var $next = $('<li>').addClass('page-item').toggleClass('disabled', page >= totalPages - 1)
        .append($('<a>').addClass('page-link').attr('href', '#').text('›').on('click', function (e) {
            e.preventDefault();
            if (page < totalPages - 1) loadData(page + 1);
        }));
    $ul.append($next);

    $('#paginationArea').show();
}

function showSuccessAlert(message) {
    $('#successAlertMsg').text(message);
    $('#successAlert').removeClass('d-none');
    setTimeout(function () {
        $('#successAlert').addClass('d-none');
    }, 4000);
}

function formatDate(dateStr) {
    if (!dateStr) return '-';
    var parts = dateStr.split('-');
    return parts[2] + '/' + parts[1] + '/' + parts[0];
}
