const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

const moduleCSSRule = require('./webpack/config/moduleCSSRule');
const collectEntryPoints = require('./webpack/entries/collectEntryPoints');

module.exports = {
  output: {
    path: path.resolve(__dirname, 'tmp'),
  },
  entry: () => collectEntryPoints(path.resolve(__dirname, 'src'), ['module'], 'scss'),
  resolve: {
    extensions: ['.scss'],
  },
  module: {
    rules: [
      moduleCSSRule,
    ]
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: 'clientlib-site/css/[name].bundle.css',
    }),
  ],
  stats: {
    assetsSort: 'chunks',
    builtAt: true,
    children: false,
    chunkGroups: true,
    chunkOrigins: true,
    colors: false,
    errors: true,
    errorDetails: true,
    env: true,
    modules: false,
    performance: true,
    providedExports: false,
    source: false,
    warnings: true
  },
};
