/**
 *  Banner区域
 */
let parentDiv = document.querySelector(".header");

function getBanner(bannerVal) {
    $.ajax({
        url: "/banner",
        type: "get",
        data: bannerVal,
        dataType: "html",
        success: function(data) {
            $('.header').append(data)
        }
    });
}

getBanner();

/**
 *  搜索
 */
let doc = $(document);
doc.on("click", '#serach', function() {
    $(".input").toggleClass("inclicked");
    $(".input").val('');
    $('#serach').toggleClass("clicked");
});

/**
 *  日记
 */
doc.on('mouseover', '.more', () => {
    $("#hidden").show();
    $("#studytips").addClass('change');

});
doc.on('mouseout', '.more', () => {
    $("#hidden").hide();
    $("#studytips").removeClass('change');
})