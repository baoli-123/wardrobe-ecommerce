import { createApp } from 'vue'
import App from './App.vue'
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
import router from "./router.js";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import store from "./store";

const app = createApp(App)
app.use(ElementPlus)
app.use(router); // 使用路由
app.use(store)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app')
