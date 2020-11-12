// To enable this script add property "validation" to the multifield node. Its value must comply with
// the following template
// field-limit-{number of items} e.g. "field-limit-2

$(window).adaptTo("foundation-registry").register("foundation.validation.validator", {

  selector: "[data-foundation-validation^='field-limit']",

  validate: function(el) {
    var validationName = el.getAttribute("data-validation");
    var numberOfItems = parseInt(validationName.replace("field-limit-", ""));

    if (numberOfItems && el.items.length !== numberOfItems){
        return "Expected number of items is " + numberOfItems;
    }
  }
});