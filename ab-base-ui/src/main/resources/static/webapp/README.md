# bpm-explorer 
## 前端项目 

前端使用 angular bootstrap-table bootstrap  等 js组件，详细可以看 assets/js/plugins 目录


## rest服务端 ctx
在 assets/app-conf.js 中配置、默认为  /agile-bpm-platform 
流程设计器在flow-editor/editor-app/app-cfg.js
修改后需要打包


## webpack打包

前端公共js css 我们使用webpack 打包

因为webpack模块化管理会让前端开发变得略微麻烦。故此，我们约定，只有公共js，angular的 service 才会要求打包处理。


列表页面可参考 sysPropertiesList.html
编辑页可参考 sysPropertiesEdit.html


#### 安装nodejs 安装webpack
npm install

#### 实时监控资源修改情况进行打包 webpack -w 

如果只负责前端也可以使用webpack 的server 