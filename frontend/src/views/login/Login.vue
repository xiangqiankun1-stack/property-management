<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <div class="logo-wrap">
          <span class="logo-text">🏢</span>
        </div>
        <h2>智慧物业管理系统</h2>
        <p>欢迎登录后台管理系统</p>
      </div>
      
      <el-form :model="form" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="用户名"
            prefix-icon="User"
            size="large"
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="form.password" 
            type="password" 
            placeholder="密码"
            prefix-icon="Lock"
            size="large"
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleLogin" class="login-btn" size="large" :loading="loading">
            登 录
          </el-button>
        </el-form-item>

        <div class="register-link">
          <span>还没有账户？</span>
          <a href="/register">立即注册</a>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { login } from '@/api/login'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

// ===== 解析 JWT Token =====
const parseJwt = (token) => {
  try {
    const base64Url = token.split('.')[1]
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(c => {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
    }).join(''))
    return JSON.parse(jsonPayload)
  } catch {
    return {}
  }
}

const handleLogin = async () => {
  if (!form.username.trim()) {
    ElMessage.warning('请输入用户名')
    return
  }
  if (!form.password.trim()) {
    ElMessage.warning('请输入密码')
    return
  }
  
  loading.value = true
  
  try {
    const res = await login(form)
    console.log('登录响应:', res)
    
    if (res.code === 200) {
      const token = res.data
      if (token) {
        // 清除旧角色
        localStorage.removeItem('role')
        
        // 保存 Token
        localStorage.setItem('token', token)
        userStore.setToken(token)
        
        // ✅ 从 Token 解析用户信息
        try {
          const claims = parseJwt(token)
          console.log('Token 解析结果:', claims)
          
          // ✅ 从 claims 中获取角色
          // 根据你的 Token 结构，claims 包含用户信息
          const userClaims = claims.claims || claims
          console.log('用户信息:', userClaims)
          
          // ✅ 获取角色（从 claims 中读取 role，如果没有则根据用户名判断）
          let role = userClaims.role || 'user'
          
          // ✅ 如果 Token 中没有 role，根据用户名判断
          if (!userClaims.role) {
            const adminUsers = ['admin', '管理员']
            role = adminUsers.includes(form.username) ? 'admin' : 'user'
            console.log('根据用户名判断角色:', role)
          }
          
          localStorage.setItem('role', role)
          console.log('最终角色:', role)
          
          ElMessage.success('登录成功')
          
          // 根据角色跳转
          if (role === 'admin') {
            router.push('/dashboard')
          } else {
            router.push('/user-home')
          }
        } catch (parseError) {
          console.warn('Token 解析失败，根据用户名判断角色', parseError)
          // ✅ 解析失败时根据用户名判断
          const adminUsers = ['admin', '管理员']
          const role = adminUsers.includes(form.username) ? 'admin' : 'user'
          localStorage.setItem('role', role)
          ElMessage.success('登录成功')
          router.push(role === 'admin' ? '/dashboard' : '/user-home')
        }
      } else {
        ElMessage.error('Token 获取失败')
      }
    } else {
      ElMessage.error(res.message || '登录失败')
    }
  } catch (error) {
    console.error('登录失败:', error)
    if (error.response) {
      ElMessage.error(error.response.data?.message || `请求失败: ${error.response.status}`)
    } else {
      ElMessage.error('网络异常，请检查后端服务是否启动')
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 420px;
  padding: 45px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
}

.login-header {
  text-align: center;
  margin-bottom: 35px;
}

.logo-wrap {
  margin-bottom: 15px;
}

.logo-text {
  font-size: 48px;
}

.login-header h2 {
  color: #333;
  margin-bottom: 8px;
  font-size: 24px;
}

.login-header p {
  color: #999;
  font-size: 14px;
}

.login-form {
  margin-top: 10px;
}

.login-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
}

.register-link {
  text-align: center;
  margin-top: 20px;
  color: #666;
  font-size: 14px;
}

.register-link a {
  color: #409EFF;
  text-decoration: none;
  margin-left: 5px;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>