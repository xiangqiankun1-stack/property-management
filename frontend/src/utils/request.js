import axios from 'axios'
import { ElMessage } from 'element-plus'

/* =========================
   创建 axios 实例
========================= */
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  timeout: 10000
})

/* =========================
   请求拦截器
========================= */
service.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')

    if (token) {
      // 统一使用 Bearer（更标准）
      config.headers.Authorization = `Bearer ${token}`
    }

    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

/* =========================
   响应拦截器
========================= */
service.interceptors.response.use(
  (response) => {
    const res = response.data

    // 兼容后端统一格式
    // { code: 200, data: xxx, msg: "" }
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
    } else {
      ElMessage.error('网络异常')
    }

    return Promise.reject(error)
  }
)

export default service