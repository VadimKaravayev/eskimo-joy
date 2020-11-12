module.exports = [
    {
        loader: 'postcss-loader',
        options: {
            plugins() {
                return [
                    require('autoprefixer')
                ];
            }
        }
    },
    {
        loader: "sass-loader",
        options: {
            url: false
        }
    },
    {
        loader: "webpack-import-glob-loader",
        options: {
            url: false
        }
    }
]
