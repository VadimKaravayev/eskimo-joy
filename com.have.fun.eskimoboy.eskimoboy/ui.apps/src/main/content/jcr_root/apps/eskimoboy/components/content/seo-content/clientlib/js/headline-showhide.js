(function ($, $document) {
    'use strict';

    var DIALOG_SELECTOR = 'form.cq-dialog:has(.seo-content)',
        ENABLE_HIDING_FIELD = '[data-enable-field-hiding="true"]',
        ENABLE_HIDING_TAB = '[data-hide-tab="true"]',
        IS_HIDDEN_HEADLINE = 'coral-checkbox[name="./isHiddenHeadline"]';

    $document.on('dialog-loaded', function () {
        var $dialog = $document.find(DIALOG_SELECTOR);
        $dialog.length && showOrHide($dialog.find(IS_HIDDEN_HEADLINE).prop('checked'));
    });

    $document.on('change', DIALOG_SELECTOR + ' ' + IS_HIDDEN_HEADLINE, function () {
        showOrHide($(this).prop('checked'));
    });

    function showOrHide(status) {
        if (status) {
            hideAndDisableFields();
            showOrHideTab(hideTabs, disableTabFields);
            return;
        }

        showAndEnableFields();
        showOrHideTab(showTabs, enableTabFields);
    }

    function hideAndDisableFields() {
        $document.find(ENABLE_HIDING_FIELD).each(function () {
            var $this = $(this);
            $this.attr('disabled', 'disabled');
            $this.parent().hide();
        });
    }

    function showAndEnableFields() {
        $document.find(ENABLE_HIDING_FIELD).each(function () {
            var $this = $(this);
            $this.removeAttr('disabled');
            $this.parent().show();
        });
    }

    function showOrHideTab(processTabs, processFields) {
        var $dialog = $document.find(DIALOG_SELECTOR),
            $panel = $dialog.find(ENABLE_HIDING_TAB).closest('coral-panel'),
            hidingTabIndexes = [];

        $dialog.find('coral-panel').each(function(index, value) {
            $(value).find(ENABLE_HIDING_TAB).length && hidingTabIndexes.push(index);
        });

        $dialog.find('coral-tab').each(processTabs.bind(hidingTabIndexes));
        $panel.find(':-foundation-submittable,.coral3-Multifield').each(processFields.bind(hidingTabIndexes));
    }

    function hideTabs(index, value) {
        this.includes(index) && $(value).hide();
    }

    function showTabs(index, value) {
        this.includes(index) && $(value).show();
    }

    function disableTabFields(index, value) {
        $(value).attr('disabled', 'disabled');
    }

    function enableTabFields(index, value) {
        $(value).removeAttr('disabled');
    }
})(Granite.$, Granite.$(document));