(function (document, $) {
    'use strict';

    // when dialog gets injected
    $(document).on('foundation-contentloaded', function (e) {
        // if there is already an inital value make sure the according target element becomes visible
        checkboxShowHideHandler($('.cq-dialog-checkbox-showhide', e.target));
    });

    $(document).on('change', '.cq-dialog-checkbox-showhide', function () {
        checkboxShowHideHandler($(this));
    });

    function checkboxShowHideHandler(el) {
        if (el && el.is('coral-checkbox')) {
          showHide(el);
        }
    }

    function showHide(element) {
        console.log('showing');
        var target = $(element).data('cqDialogCheckboxShowhide');
        var $target = $(target);
        if (target) {
            element.prop('checked') ? $target.removeClass('hide') : $target.addClass('hide');
        }
    }
})(document, Granite.$);