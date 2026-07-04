import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建实例
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 10000
})

/* =========================
   请求拦截器
========================= */
service.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')

    if (token) {
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

    // 这里是后端统一格式（很重要）
    // { code: 200, data: xxx, msg: "" }

    if (res.code !== 200) {
      ElMessage.error(res.msg || '请求失败')
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
//test
export default service