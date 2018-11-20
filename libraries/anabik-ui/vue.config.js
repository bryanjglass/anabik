module.exports = {
    outputDir: 'build/www',
    devServer: {
        proxy: 'http://localhost:8080'
    },
    css: {
        loaderOptions: {
            sass: {
                implementation: require("sass")
            }
        }
    }
};
