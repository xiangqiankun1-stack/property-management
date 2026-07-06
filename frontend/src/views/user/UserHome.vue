<template>
  <div class="user-home">
    <!-- 顶部导航 -->
    <header class="header">
      <div class="header-left">
        <span class="logo">🏢 智慧物业</span>
      </div>
      <div class="header-right">
        <span class="user-name">{{ userInfo.fullName || '用户' }}</span>
        <el-button type="danger" size="small" @click="handleLogout">退出</el-button>
      </div>
    </header>

    <!-- 功能卡片 -->
    <div class="main-content">
      <div class="cards-grid">
        <!-- 报修卡片 -->
        <el-card class="function-card" shadow="hover" @click="activeTab = 'repair'">
          <div class="card-icon repair-icon">🔧</div>
          <h3>在线报修</h3>
          <p>提交维修申请，快速响应</p>
          <el-tag size="small" type="danger" v-if="repairCount > 0">
            待处理 {{ repairCount }}
          </el-tag>
        </el-card>

        <!-- 投诉卡片 -->
        <el-card class="function-card" shadow="hover" @click="activeTab = 'complaint'">
          <div class="card-icon complaint-icon">📢</div>
          <h3>投诉建议</h3>
          <p>反馈问题，提升服务</p>
          <el-tag size="small" type="warning" v-if="complaintCount > 0">
            待处理 {{ complaintCount }}
          </el-tag>
        </el-card>

        <!-- 缴费卡片 -->
        <el-card class="function-card" shadow="hover" @click="activeTab = 'payment'">
          <div class="card-icon payment-icon">💰</div>
          <h3>在线缴费</h3>
          <p>一键缴纳物业费用</p>
          <el-tag size="small" type="success" v-if="unpaidCount > 0">
            待缴 {{ unpaidCount }}
          </el-tag>
        </el-card>
      </div>

      <!-- 内容区域 -->
      <el-card class="content-card">
        <div class="content-header">
          <span class="content-title">{{ currentTabName }}</span>
          <el-button type="primary" size="small" @click="showAddDialog = true">
            新增{{ currentTabName }}
          </el-button>
        </div>

        <!-- 报修列表 -->
        <div v-show="activeTab === 'repair'">
          <el-table :data="repairList" border stripe style="width: 100%">
            <el-table-column prop="id" label="ID" width="60" align="center" />
            <el-table-column prop="repairNo" label="报修编号" width="150" />
            <el-table-column prop="repairType" label="类型" width="100" />
            <el-table-column prop="repairContent" label="报修内容" min-width="200" show-overflow-tooltip />
            <el-table-column prop="status" label="状态" width="100" align="center">
              <template #default="{ row }">
                <el-tag :type="getRepairStatusType(row.status)">
                  {{ getRepairStatusText(row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="时间" width="170" />
          </el-table>
        </div>

        <!-- 投诉列表 -->
        <div v-show="activeTab === 'complaint'">
          <el-table :data="complaintList" border stripe style="width: 100%">
            <el-table-column prop="id" label="ID" width="60" align="center" />
            <el-table-column prop="complaintNo" label="投诉编号" width="150" />
            <el-table-column prop="complaintTitle" label="投诉标题" width="150" show-overflow-tooltip />
            <el-table-column prop="complaintCategory" label="分类" width="100" />
            <el-table-column prop="complaintContent" label="投诉内容" min-width="200" show-overflow-tooltip />
            <el-table-column prop="status" label="状态" width="100" align="center">
              <template #default="{ row }">
                <el-tag :type="getComplaintStatusType(row.status)">
                  {{ getComplaintStatusText(row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="时间" width="170" />
          </el-table>
        </div>

        <!-- 缴费列表 -->
        <div v-show="activeTab === 'payment'">
          <el-table :data="paymentList" border stripe style="width: 100%">
            <el-table-column prop="id" label="ID" width="60" align="center" />
            <el-table-column prop="billNo" label="账单编号" width="150" />
            <el-table-column prop="feeName" label="费用项目" width="120" />
            <el-table-column prop="amount" label="金额(元)" width="100" align="center">
              <template #default="{ row }">
                <span style="color: #e6a23c;">¥{{ row.amount }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="dueDate" label="到期日" width="120" />
            <el-table-column prop="status" label="状态" width="100" align="center">
              <template #default="{ row }">
                <el-tag :type="row.status === 0 ? 'danger' : row.status === 1 ? 'warning' : 'success'">
                  {{ row.status === 0 ? '待支付' : row.status === 1 ? '部分支付' : '已支付' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120" align="center">
              <template #default="{ row }">
                <el-button 
                  size="small" 
                  type="primary" 
                  @click="handlePay(row)"
                  v-if="row.status !== 2"
                >
                  支付
                </el-button>
                <span v-else style="color: #67c23a;">已支付</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>

    <!-- 新增报修/投诉弹窗 -->
    <el-dialog 
      v-model="showAddDialog" 
      :title="'新增' + currentTabName"
      width="500px"
    >
      <el-form :model="addForm" ref="formRef" :rules="formRules" label-width="100px">
        <!-- 报修表单 -->
        <template v-if="activeTab === 'repair'">
          <el-form-item label="报修类型" prop="repairType">
            <el-select v-model="addForm.repairType" placeholder="请选择报修类型" style="width: 100%">
              <el-option label="水电" value="水电" />
              <el-option label="门窗" value="门窗" />
              <el-option label="设施" value="设施" />
              <el-option label="其他" value="其他" />
            </el-select>
          </el-form-item>
          <el-form-item label="报修内容" prop="repairContent">
            <el-input v-model="addForm.repairContent" type="textarea" placeholder="请描述问题详情" />
          </el-form-item>
          <el-form-item label="联系电话" prop="contactPhone">
            <el-input v-model="addForm.contactPhone" placeholder="请输入联系电话" />
          </el-form-item>
        </template>

        <!-- 投诉表单 -->
        <template v-if="activeTab === 'complaint'">
          <el-form-item label="投诉标题" prop="complaintTitle">
            <el-input v-model="addForm.complaintTitle" placeholder="请输入投诉标题" />
          </el-form-item>
          <el-form-item label="投诉分类" prop="complaintCategory">
            <el-select v-model="addForm.complaintCategory" placeholder="请选择分类" style="width: 100%">
              <el-option label="服务态度" value="服务态度" />
              <el-option label="环境卫生" value="环境卫生" />
              <el-option label="安全问题" value="安全问题" />
              <el-option label="设施维护" value="设施维护" />
              <el-option label="其他" value="其他" />
            </el-select>
          </el-form-item>
          <el-form-item label="投诉内容" prop="complaintContent">
            <el-input v-model="addForm.complaintContent" type="textarea" placeholder="请详细描述投诉内容" />
          </el-form-item>
          <el-form-item label="联系电话" prop="contactPhone">
            <el-input v-model="addForm.contactPhone" placeholder="请输入联系电话" />
          </el-form-item>
        </template>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="submitAddForm" :loading="submitting">提交</el-button>
      </template>
    </el-dialog>

    <!-- 缴费支付弹窗 -->
    <el-dialog v-model="showPayDialog" title="确认支付" width="400px">
      <div style="text-align: center; padding: 20px 0;">
        <div style="font-size: 48px; margin-bottom: 10px;">💳</div>
        <p style="font-size: 16px; color: #333;">确认支付 <strong style="color: #e6a23c;">¥{{ payAmount }}</strong> 元</p>
        <p style="font-size: 14px; color: #999;">支付方式：微信支付</p>
      </div>
      <template #footer>
        <el-button @click="showPayDialog = false">取消</el-button>
        <el-button type="success" @click="confirmPay" :loading="paying">确认支付</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

// ===== 路由 =====
const router = useRouter()

// ===== 用户信息 =====
const userInfo = ref({
  fullName: '用户',
  id: 1
})

// ===== 当前激活的Tab =====
const activeTab = ref('repair')

// ===== Tab名称 =====
const currentTabName = computed(() => {
  const map = {
    repair: '报修',
    complaint: '投诉',
    payment: '缴费'
  }
  return map[activeTab.value] || '报修'
})

// ===== 统计数据 =====
const repairCount = ref(0)
const complaintCount = ref(0)
const unpaidCount = ref(0)

// ===== 列表数据 =====
const repairList = ref([])
const complaintList = ref([])
const paymentList = ref([])

// ===== 弹窗控制 =====
const showAddDialog = ref(false)
const showPayDialog = ref(false)
const submitting = ref(false)
const paying = ref(false)
const formRef = ref(null)
const payAmount = ref(0)
const payBillId = ref(null)

// ===== 新增表单 =====
const addForm = reactive({
  // 报修
  repairType: '',
  repairContent: '',
  // 投诉
  complaintTitle: '',
  complaintCategory: '',
  complaintContent: '',
  // 通用
  contactPhone: ''
})

// ===== 表单验证 =====
const formRules = {
  repairType: [{ required: true, message: '请选择报修类型', trigger: 'change' }],
  repairContent: [{ required: true, message: '请输入报修内容', trigger: 'blur' }],
  complaintTitle: [{ required: true, message: '请输入投诉标题', trigger: 'blur' }],
  complaintCategory: [{ required: true, message: '请选择投诉分类', trigger: 'change' }],
  complaintContent: [{ required: true, message: '请输入投诉内容', trigger: 'blur' }],
  contactPhone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ]
}

// ===== 状态转换 =====
// 报修状态
const getRepairStatusText = (status) => {
  const map = { 0: '待处理', 1: '处理中', 2: '已完成', 3: '已撤销' }
  return map[status] || '未知'
}
const getRepairStatusType = (status) => {
  const map = { 0: 'danger', 1: 'warning', 2: 'success', 3: 'info' }
  return map[status] || 'info'
}

// 投诉状态
const getComplaintStatusText = (status) => {
  const map = { 0: '待处理', 1: '处理中', 2: '已处理', 3: '已撤销' }
  return map[status] || '未知'
}
const getComplaintStatusType = (status) => {
  const map = { 0: 'danger', 1: 'warning', 2: 'success', 3: 'info' }
  return map[status] || 'info'
}

// ===== 加载数据 =====
const loadData = async () => {
  loadRepairList()
  loadComplaintList()
  loadPaymentList()
}

const loadRepairList = async () => {
  // 模拟数据
  repairList.value = [
    { id: 1, repairNo: 'RP20260706001', repairType: '水电', repairContent: '厨房水龙头漏水', status: 0, createTime: '2026-07-06 10:00' },
    { id: 2, repairNo: 'RP20260705001', repairType: '门窗', repairContent: '卧室窗户关不上', status: 1, createTime: '2026-07-05 14:30' },
    { id: 3, repairNo: 'RP20260704001', repairType: '设施', repairContent: '楼道灯不亮', status: 2, createTime: '2026-07-04 09:00' }
  ]
  repairCount.value = repairList.value.filter(item => item.status === 0 || item.status === 1).length
}

const loadComplaintList = async () => {
  complaintList.value = [
    { id: 1, complaintNo: 'CP20260706001', complaintTitle: '物业态度差', complaintCategory: '服务态度', complaintContent: '前台工作人员态度恶劣', status: 0, createTime: '2026-07-06 11:00' },
    { id: 2, complaintNo: 'CP20260705001', complaintTitle: '垃圾未及时清理', complaintCategory: '环境卫生', complaintContent: '小区垃圾桶满溢', status: 1, createTime: '2026-07-05 16:30' },
    { id: 3, complaintNo: 'CP20260703001', complaintTitle: '电梯故障', complaintCategory: '安全问题', complaintContent: '电梯运行异常', status: 2, createTime: '2026-07-03 08:30' }
  ]
  complaintCount.value = complaintList.value.filter(item => item.status === 0 || item.status === 1).length
}

const loadPaymentList = async () => {
  paymentList.value = [
    { id: 1, billNo: 'BL20260701001', feeName: '物业费', amount: 280.00, dueDate: '2026-07-31', status: 0 },
    { id: 2, billNo: 'BL20260701002', feeName: '水费', amount: 85.50, dueDate: '2026-07-20', status: 0 },
    { id: 3, billNo: 'BL20260701003', feeName: '电费', amount: 160.00, dueDate: '2026-07-15', status: 1 },
    { id: 4, billNo: 'BL20260601001', feeName: '物业费', amount: 280.00, dueDate: '2026-06-30', status: 2 }
  ]
  unpaidCount.value = paymentList.value.filter(item => item.status === 0 || item.status === 1).length
}

// ===== 提交新增 =====
const submitAddForm = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return

  submitting.value = true
  try {
    if (activeTab.value === 'repair') {
      // 新增报修
      repairList.value.unshift({
        id: Date.now(),
        repairNo: 'RP' + Date.now().toString().slice(-10),
        repairType: addForm.repairType,
        repairContent: addForm.repairContent,
        status: 0,
        createTime: new Date().toLocaleString()
      })
      repairCount.value++
      ElMessage.success('报修提交成功')
    } else if (activeTab.value === 'complaint') {
      // 新增投诉
      complaintList.value.unshift({
        id: Date.now(),
        complaintNo: 'CP' + Date.now().toString().slice(-10),
        complaintTitle: addForm.complaintTitle,
        complaintCategory: addForm.complaintCategory,
        complaintContent: addForm.complaintContent,
        status: 0,
        createTime: new Date().toLocaleString()
      })
      complaintCount.value++
      ElMessage.success('投诉提交成功')
    }
    showAddDialog.value = false
    // 重置表单
    Object.assign(addForm, {
      repairType: '',
      repairContent: '',
      complaintTitle: '',
      complaintCategory: '',
      complaintContent: '',
      contactPhone: ''
    })
  } catch (error) {
    ElMessage.error('提交失败')
  } finally {
    submitting.value = false
  }
}

// ===== 支付 =====
const handlePay = (row) => {
  payAmount.value = row.amount
  payBillId.value = row.id
  showPayDialog.value = true
}

const confirmPay = async () => {
  paying.value = true
  try {
    // 模拟支付
    await new Promise(resolve => setTimeout(resolve, 1500))
    const bill = paymentList.value.find(item => item.id === payBillId.value)
    if (bill) {
      bill.status = 2
    }
    unpaidCount.value = paymentList.value.filter(item => item.status === 0 || item.status === 1).length
    ElMessage.success('支付成功')
    showPayDialog.value = false
  } catch (error) {
    ElMessage.error('支付失败')
  } finally {
    paying.value = false
  }
}

// UserHome.vue - 退出登录
const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // ✅ 清除所有登录信息
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    // 如果有 userInfo 也清除
    localStorage.removeItem('userInfo')
    
    router.push('/login')
    ElMessage.success('已退出')
  }).catch(() => {})
}

// ===== 初始化 =====
onMounted(() => {
  loadData()
})
</script>

<style scoped>
.user-home {
  min-height: 100vh;
  background: #f0f2f5;
  display: flex;
  flex-direction: column;
}

/* ===== 头部 ===== */
.header {
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  padding: 16px 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
}

.header-left .logo {
  color: #fff;
  font-size: 22px;
  font-weight: 700;
  letter-spacing: 2px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-name {
  color: rgba(255, 255, 255, 0.85);
  font-size: 14px;
}

/* ===== 主要内容 ===== */
.main-content {
  flex: 1;
  padding: 24px 40px;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

/* ===== 功能卡片 ===== */
.cards-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  margin-bottom: 24px;
}

.function-card {
  text-align: center;
  padding: 30px 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  border-radius: 12px;
}

.function-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
}

.card-icon {
  font-size: 48px;
  margin-bottom: 12px;
}

.function-card h3 {
  font-size: 18px;
  color: #333;
  margin-bottom: 8px;
}

.function-card p {
  font-size: 14px;
  color: #999;
  margin: 0;
}

/* ===== 内容卡片 ===== */
.content-card {
  border-radius: 12px;
  min-height: 400px;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid #eee;
}

.content-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

/* ===== 响应式 ===== */
@media (max-width: 768px) {
  .main-content {
    padding: 16px;
  }
  
  .cards-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }
  
  .header {
    padding: 12px 20px;
  }
}
</style>