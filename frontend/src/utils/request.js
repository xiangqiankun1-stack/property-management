// utils/request.js
import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  timeout: 10000
})

service.interceptors.request.use(
  (config) => {
    // ✅ 只改这里！
    config.url = `http://localhost:8080${config.url}`
    
    const isLogin = config.url.includes('/login') && config.method === 'post';
    
    if (!isLogin) {
      const token = localStorage.getItem('token')
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
    }

    if (isLogin && !config.headers['Content-Type']) {
      config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
    }

    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  (response) => {
    const res = response.data
    const isLogin = response.config.url.includes('/login') && response.config.method === 'post';
    
    if (isLogin) {
      return res
    }

    if (res.code !== 200) {
      ElMessage.error(res.msg || res.message || '请求失败')
      return Promise.reject(res)
    }

    return res.data
  },
  (error) => {
    const status = error.response?.status

    if (status === 401) {
      ElMessage.error('登录已过期，请重新登录')
      localStorage.removeItem('token')
      window.location.href = '/login'
    } else if (status === 500) {
      const msg = error.response?.data?.message || '服务器内部错误，请稍后重试'
      ElMessage.error(msg)
    } else if (status === 404) {
      ElMessage.error('接口不存在，请检查路径')
    } else {
      ElMessage.error('网络异常')
    }

    return Promise.reject(error)
  }
)

export default service