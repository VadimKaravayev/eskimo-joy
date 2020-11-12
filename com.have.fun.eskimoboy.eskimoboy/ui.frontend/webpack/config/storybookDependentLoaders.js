const MiniCssExtractPlugin = require("mini-css-extract-plugin");

const isStorybook = require('./isStorybook');

module.exports = () => isStorybook()
  && ['style-loader']
  || [MiniCssExtractPlugin.loader];
