module.exports = (env, options) => {
    return {
        devtool: options.mode == 'production' ? undefined : 'source-map',
        entry: {
            main : ['./frontend/src/main.js'],
            // signup : ['./frontend/src/signup.js']
        }, output: {
            path: __dirname + '/static/dist',
            filename: '[name].js',
            library:  {
                name: "[name]",
                type: "umd"
              }
        },
        module: {
            rules: [
                {
                    test: /\.jsx?$/,
                    exclude: /node_modules/,
                    loader: "babel-loader",
                    options: {
                        cacheDirectory: true,
                        cacheCompression: false //,
                        //envName: isProduction ? "production" : "development"
                    }
                },
                {
                    test: /\.css$/i,
                    use: ["style-loader", "css-loader"]
                }
            ]
        },
        resolve: {
            extensions: ['', '.js', '.jsx', '.css'],
            modules: [
                'node_modules'
            ]
        }
    }
}

