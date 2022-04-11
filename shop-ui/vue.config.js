const path = require("path");
const UglifyJsPlugin = require("uglifyjs-webpack-plugin");
const OptimizeCSSAssetsPlugin = require("optimize-css-assets-webpack-plugin");
const CompressionPlugin = require("compression-webpack-plugin");
const debug = process.env.NODE_ENV !== "production";
const resolve = dir => path.join(__dirname, dir);
module.exports = {
  publicPath: process.env.VUE_APP_PUBLIC_PATH,
  assetsDir: "assets",
  // webpack配置
  chainWebpack: config => {
    //loader配置
    config.resolve.alias.set("@", resolve("src"));
    if (!debug) {
      const cdn = {
        css: ["//unpkg.com/element-ui@2.13.1/lib/theme-chalk/index.css"],
        js: [
          "//unpkg.com/vue@2.6.11/dist/vue.min.js", // 访问https://unpkg.com/vue/dist/vue.min.js获取最新版本
          "//unpkg.com/vue-router@3.1.6/dist/vue-router.min.js",
          "//unpkg.com/vuex@3.1.3/dist/vuex.min.js",
          "//unpkg.com/axios@0.19.2/dist/axios.min.js",
          "//unpkg.com/element-ui@2.13.1/lib/index.js"
        ]
      };
      // 如果使用多页面打包，使用vue inspect --plugins查看html是否在结果数组中
      config.plugin("html").tap(args => {
        // html中添加cdn
        args[0].cdn = cdn;
        return args;
      });
      config.module
        .rule("images")
        .use("image-webpack-loader")
        .loader("image-webpack-loader")
        .options({
          mozjpeg: { progressive: true, quality: 65 },
          optipng: { enabled: false },
          pngquant: { quality: [0.65, 0.9], speed: 4 },
          gifsicle: { interlaced: false }
          // webp: { quality: 75 }
        });
    }
  },
  configureWebpack: config => {
    // webpack配置，值为对象时会合并配置，为方法时会改写配置
    if (debug) {
      // 开发环境配置
      config.devtool = "cheap-module-eval-source-map";
    } else {
      // 生产环境配置
      config.devtool = "none";
      config.externals = {
        vue: "Vue",
        "element-ui": "ELEMENT",
        "vue-router": "VueRouter",
        vuex: "Vuex",
        axios: "axios"
      };
      config.optimization = {
        minimizer: [
          new UglifyJsPlugin({
            uglifyOptions: {
              compress: {
                drop_console: true, // console
                drop_debugger: false,
                pure_funcs: ["console.log"] // 移除console
              }
            }
          }),
          new OptimizeCSSAssetsPlugin("./css/[name].min.css"),
          new CompressionPlugin({
            algorithm: "gzip",
            test: /\.(js|css)$/, // 匹配文件名
            threshold: 10240, // 对超过10k的数据压缩
            deleteOriginalAssets: false, // 不删除源文件
            minRatio: 0.8 // 压缩比
          })
        ]
      };
    }
  },
  // 生产环境是否生成 sourceMap 文件
  productionSourceMap: false,
  css: {
    // 是否使用css分离插件 ExtractTextPlugin
    extract: true,
    sourceMap: false
  },
  parallel: require("os").cpus().length > 1,
  // webpack-dev-server 相关配置
  devServer: {
    open: false, //是否自动打开浏览器
    host: "0.0.0.0",
    port: 8080,
    https: false,
    hotOnly: false,
    proxy: {
      "/shop": {
        target: "http://localhost:8081",
        changeOrigin: true,
        ws: true
        //pathRewrite: { "^/cfb/api/installment": "/api/installment" }
      }
    } // 设置代理
  }
};
