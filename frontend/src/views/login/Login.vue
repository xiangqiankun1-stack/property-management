
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
          <el-button type="primary" @click="handleLogin" class="login-btn" size="large">
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
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { login } from '@/api/login'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const form = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  if (!form.username.trim()) {
    ElMessage.warning('请输入用户名')
    return
  }
  if (!form.password.trim()) {
    ElMessage.warning('请输入密码')
    return
  }
  
  try {
    const response = await login(form)
    
    if (response && response.code === 200) {
      localStorage.setItem('token', response.data)
      userStore.token = response.data
      
      ElMessage.success('登录成功')
      router.push('/dashboard')
    } else {
      ElMessage.error(response.message || '登录失败')
    }
  } catch (error) {
    ElMessage.error('登录失败，请检查网络或账号密码')
    console.error('登录失败:', error)
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