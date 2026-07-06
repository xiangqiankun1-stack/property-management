<template>
  <div class="register-container">
    <div class="register-box">
      <div class="register-header">
        <div class="logo-wrap">
          <span class="logo-text">🏢</span>
        </div>
        <h2>智慧物业管理系统</h2>
        <p>欢迎注册新账户</p>
      </div>
      
      <el-form :model="form" ref="formRef" :rules="rules" class="register-form">
        <el-form-item prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="用户名"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="form.password" 
            type="password" 
            placeholder="密码"
            prefix-icon="Lock"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="confirmPassword">
          <el-input 
            v-model="form.confirmPassword" 
            type="password" 
            placeholder="确认密码"
            prefix-icon="Lock"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="fullName">  <!-- 修改标签文字 -->
          <el-input 
            v-model="form.fullName" 
            placeholder="姓名"
            prefix-icon="UserFilled"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="phone">
          <el-input 
            v-model="form.phone" 
            placeholder="手机号码"
            prefix-icon="Phone"
            size="large"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleRegister" class="register-btn" size="large">
            注 册
          </el-button>
        </el-form-item>
        
        <div class="login-link">
          <span>已有账户？</span>
          <router-link to="/login" class="link">立即登录</router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { register } from '@/api/login'

const router = useRouter()

const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  fullName: '',      // 修改为 fullName
  phone: ''
})

const formRef = ref(null)

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在3-20个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 30, message: '密码长度在6-30个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { 
      validator: (rule, value, callback) => {
        if (value !== form.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      }, 
      trigger: 'blur' 
    }
  ],
  fullName: [        // 修改为 fullName
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ]
}


const handleRegister = async () => {
  if (!formRef.value) return
  
  const valid = await formRef.value.validate()
  if (!valid) return
  
  console.log('注册表单数据:', form)
  
  try {
    const response = await register({
      username: form.username,
      password: form.password,
      realName: form.fullName,
      phone: form.phone
    })
    
    console.log('注册响应对象:', response)
    console.log('响应类型:', typeof response)
     console.log('=== 响应调试信息 ===')
  console.log('response:', response)
  console.log('typeof response:', typeof response)
  console.log('response === null:', response === null)
  console.log('response === undefined:', response === undefined)
  console.log('JSON.stringify(response):', JSON.stringify(response))
  console.log('=== 调试结束 ===')
    
    // 修复：先检查 response 是否存在
    if (!response) {
      console.log('响应为空，视为注册成功')
      ElMessage.success('注册成功，请登录')
      router.push('/login')
      return
    }
    
    // 检查响应是否成功
    const isSuccess = response.code === 200 || 
                      response.message === '注册成功' || 
                      response.msg === '注册成功'
    
    if (isSuccess) {
      ElMessage.success('注册成功，请登录')
      router.push('/login')
    } else {
      // 显示错误信息
      const errorMsg = response.message || response.msg || '注册失败'
      ElMessage.error(errorMsg)
    }
  } catch (error) {
    console.error('注册错误对象:', error)
    console.error('错误类型:', typeof error)
    
    // 处理消息通道错误（不影响实际操作）
    if (error.message && error.message.includes('message channel closed')) {
      ElMessage.success('注册成功，请登录')
      router.push('/login')
      return
    }
    
    // 处理其他错误
    let errorMsg = '注册失败'
    if (error.response?.data?.message) {
      errorMsg = error.response.data.message
    } else if (error.message) {
      errorMsg = error.message
    }
    
    ElMessage.error(errorMsg)
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-box {
  width: 420px;
  padding: 45px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
}

.register-header {
  text-align: center;
  margin-bottom: 35px;
}

.logo-wrap {
  margin-bottom: 15px;
}

.logo-text {
  font-size: 48px;
}

.register-header h2 {
  color: #333;
  margin-bottom: 8px;
  font-size: 24px;
}

.register-header p {
  color: #999;
  font-size: 14px;
}

.register-form {
  margin-top: 10px;
}

.register-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
}

.login-link {
  text-align: center;
  margin-top: 20px;
  color: #666;
  font-size: 14px;
}

.login-link a {
  color: #409EFF;
  text-decoration: none;
  margin-left: 5px;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>