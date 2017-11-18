$(document).ready(function() {
    toggleTestFrameworkVisibility(false);

    $('#type').change(function () {
        var typeValue = $(this).val();
        if (typeValue === 'java-application' || typeValue === 'java-library') {
            toggleTestFrameworkVisibility(true);
        } else {
            toggleTestFrameworkVisibility(false);
        }
    });

    function toggleTestFrameworkVisibility(show) {
        var testFrameworkRow = $('#testFrameworkRow');

        if (show) {
            testFrameworkRow.show();
        } else {
            testFrameworkRow.hide();
        }
    }
});