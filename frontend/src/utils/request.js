import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000
})

// 请求拦截
request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

// 响应拦截
request.interceptors.response.use(
  res => {
    if (res.data.code !== 200) {
      return Promise.reject(res.data.message)
    }
    return res.data
  },
  err => {
    return Promise.reject(err)
  }
)

export default request