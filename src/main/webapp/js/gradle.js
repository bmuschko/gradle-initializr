$(document).ready(function() {
    $('#type').change(function () {
        var typeValue = $(this).val();
        if (typeValue === 'java-application' || typeValue === 'java-library') {
            toggleTestFrameworkVisibility(true);
        } else {
            toggleTestFrameworkVisibility(false);
        }

        if (typeValue === 'basic' || typeValue === 'cpp-application' || typeValue === 'cpp-library') {
            togglePackageNameVisibility(false);
        } else {
            togglePackageNameVisibility(true);
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

    function togglePackageNameVisibility(show) {
        var packageNameRow = $('#packageNameRow');

        if (show) {
            packageNameRow.show();
        } else {
            packageNameRow.hide();
        }
    }
});