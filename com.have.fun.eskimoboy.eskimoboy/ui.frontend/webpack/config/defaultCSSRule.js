const path = require('path');

const baseCSSLoaders = require("./baseCSSLoaders");
const storybookDependentLoaders = require('./storybookDependentLoaders');

module.exports = {
  test: /\.scss$/,
  use: [
    ...storybookDependentLoaders(),
    {
      loader: "css-loader",
      options: {
        url: false,
      }
    },
    ...baseCSSLoaders
  ]
};
