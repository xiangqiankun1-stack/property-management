import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import pinia from './stores'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// ✅ 引入中文语言包
import zhCn from 'element-plus/es/locale/lang/zh-cn'

const app = createApp(App)

app.use(pinia)
app.use(router)
app.use(ElementPlus, {
  locale: zhCn,  // ✅ 设置中文
})

app.mount('#app')