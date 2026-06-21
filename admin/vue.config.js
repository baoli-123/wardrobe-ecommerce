const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 7071,
    proxy: {
      '/wardrobe_back': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  },
  lintOnSave: false//关闭语法检查
})
