(function (document, $) {
    "use strict";

    // when dialog gets injected
    $(document).on("foundation-contentloaded", function (e) {
        // if there is already an inital value make sure the according target element becomes visible
        checkboxShowHideHandler($(".cq-dialog-checkbox-showhide", e.target));
    });

    $(document).on("change", ".cq-dialog-checkbox-showhide", function () {
        checkboxShowHideHandler($(this));
    });

    function checkboxShowHideHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-checkbox")) {
                // handle Coral3 base drop-down
                Coral.commons.ready(element, function (component) {
                    showHide(component, element);
                });
            }
        })
    }

    function showHide(component, element) {
        console.log('showing');

        // get the selector to find the target elements. its stored as data-.. attribute
        var target = $(element).data("cqDialogCheckboxShowhide");
        var $target = $(target);
        if (target) {
            component.checked ? $target.removeClass("hide") : $target.addClass("hide");
        }
    }
})(document, Granite.$);