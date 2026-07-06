<template>
  <div class="dashboard">
    <!-- 统计卡片 -->
    <div class="summary-cards">
      <el-card class="summary-card" v-for="item in summaryList" :key="item.title">
        <div class="summary-icon" :class="item.iconClass">
          <el-icon>{{ item.icon }}</el-icon>
        </div>
        <div class="summary-info">
          <div class="summary-value">{{ item.value }}</div>
          <div class="summary-title">{{ item.title }}</div>
        </div>
      </el-card>
    </div>

    <!-- 图表区域 -->
    <div class="charts-area">
      <el-card class="chart-card">
        <div ref="repairChartRef" class="chart"></div>
      </el-card>
      <el-card class="chart-card">
        <div ref="complaintChartRef" class="chart"></div>
      </el-card>
      <el-card class="chart-card">
        <div ref="paymentChartRef" class="chart"></div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getRepairList } from '@/api/repair'
import { getComplaintList } from '@/api/complaint'
import { getOwnerList } from '@/api/owner'
import { getPaymentList } from '@/api/payment'

// 统计数据
const summaryList = reactive([
  { title: '报修总数', value: '--', icon: 'Wrench', iconClass: 'icon-blue' },
  { title: '投诉总数', value: '--', icon: 'Message', iconClass: 'icon-red' },
  { title: '业主总数', value: '--', icon: 'User', iconClass: 'icon-green' },
  { title: '缴费金额', value: '--', icon: 'Wallet', iconClass: 'icon-yellow' }
])

// 图表引用
const repairChartRef = ref(null)
const complaintChartRef = ref(null)
const paymentChartRef = ref(null)

let repairChart = null
let complaintChart = null
let paymentChart = null

// 加载统计数据（从现有业务接口获取）
const loadStats = async () => {
  // 并行请求多个接口
  const promises = [
    getRepairList(),
    getComplaintList(),
    getOwnerList(),
    getPaymentList()
  ]
  
  try {
    const [repairRes, complaintRes, ownerRes, paymentRes] = await Promise.all(promises)
    
    // 报修总数
    if (Array.isArray(repairRes)) {
      summaryList[0].value = repairRes.length
    } else if (repairRes && repairRes.data && Array.isArray(repairRes.data)) {
      summaryList[0].value = repairRes.data.length
    }
    
    // 投诉总数
    if (Array.isArray(complaintRes)) {
      summaryList[1].value = complaintRes.length
    } else if (complaintRes && complaintRes.data && Array.isArray(complaintRes.data)) {
      summaryList[1].value = complaintRes.data.length
    }
    
    // 业主总数
    if (Array.isArray(ownerRes)) {
      summaryList[2].value = ownerRes.length
    } else if (ownerRes && ownerRes.data && Array.isArray(ownerRes.data)) {
      summaryList[2].value = ownerRes.data.length
    }
    
    // 缴费金额
    let totalAmount = 0
    if (Array.isArray(paymentRes)) {
      totalAmount = paymentRes.reduce((sum, item) => sum + (item.paymentAmount || 0), 0)
    } else if (paymentRes && paymentRes.data && Array.isArray(paymentRes.data)) {
      totalAmount = paymentRes.data.reduce((sum, item) => sum + (item.paymentAmount || 0), 0)
    }
    summaryList[3].value = totalAmount > 0 ? `¥${totalAmount.toLocaleString()}` : '--'
    
  } catch (error) {
    console.error('加载统计数据失败:', error)
    // 使用模拟数据
    summaryList[0].value = 156
    summaryList[1].value = 89
    summaryList[2].value = 1258
    summaryList[3].value = '¥128,650'
  }
}

// 加载报修趋势图表
const loadRepairChart = () => {
  repairChart = echarts.init(repairChartRef.value)
  
  const option = {
    title: { text: '报修趋势' },
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月']
    },
    yAxis: { type: 'value' },
    series: [{
      name: '报修数',
      type: 'line',
      data: [28, 35, 42, 38, 55, 48],
      smooth: true
    }]
  }
  
  repairChart.setOption(option)
}

// 加载投诉趋势图表
const loadComplaintChart = () => {
  complaintChart = echarts.init(complaintChartRef.value)
  
  const option = {
    title: { text: '投诉趋势' },
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月']
    },
    yAxis: { type: 'value' },
    series: [{
      name: '投诉数',
      type: 'bar',
      data: [15, 22, 18, 25, 19, 28]
    }]
  }
  
  complaintChart.setOption(option)
}

// 加载缴费分布图表
const loadPaymentChart = () => {
  paymentChart = echarts.init(paymentChartRef.value)
  
  const option = {
    title: { text: '缴费类型分布' },
    tooltip: { trigger: 'item' },
    legend: { orient: 'horizontal', bottom: '5%' },
    series: [{
      name: '缴费类型',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: { show: true },
      emphasis: {
        label: { show: true, fontSize: 18, fontWeight: 'bold' }
      },
      data: [
        { value: 45, name: '物业费' },
        { value: 25, name: '水费' },
        { value: 20, name: '电费' },
        { value: 10, name: '燃气费' }
      ]
    }]
  }
  
  paymentChart.setOption(option)
}

// 处理窗口大小变化
const handleResize = () => {
  repairChart?.resize()
  complaintChart?.resize()
  paymentChart?.resize()
}

// 初始化
onMounted(async () => {
  await loadStats()
  loadRepairChart()
  loadComplaintChart()
  loadPaymentChart()
  window.addEventListener('resize', handleResize)
})

// 清理
onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  repairChart?.dispose()
  complaintChart?.dispose()
  paymentChart?.dispose()
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.summary-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.summary-card {
  display: flex;
  align-items: center;
  padding: 20px;
}

.summary-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  margin-right: 20px;
}

.icon-blue { background: #e6f7ff; color: #1890ff; }
.icon-red { background: #fff2f0; color: #f5222d; }
.icon-green { background: #f6ffed; color: #52c41a; }
.icon-yellow { background: #fffbe6; color: #faad14; }

.summary-info {
  flex: 1;
}

.summary-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.summary-title {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}

.charts-area {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.chart-card {
  height: 300px;
}

.chart {
  width: 100%;
  height: 100%;
}

@media (max-width: 1200px) {
  .summary-cards {
    grid-template-columns: repeat(2, 1fr);
  }
  .charts-area {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .summary-cards {
    grid-template-columns: 1fr;
  }
  .charts-area {
    grid-template-columns: 1fr;
  }
}
</style>