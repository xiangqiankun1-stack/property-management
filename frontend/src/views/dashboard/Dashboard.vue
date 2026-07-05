<template>
  <div class="dashboard">

    <!-- 加载状态 -->
    <el-loading v-if="loading" text="加载中...">
      <template #spinner>
        <el-spinner type="ring" />
      </template>
    </el-loading>

    <!-- KPI 卡片（用 el-row 优化布局） -->
    <el-row :gutter="20" class="card-row">

      <el-col :span="6" v-for="item in summaryList" :key="item.title">
        <el-card class="card" shadow="hover">
          <div class="card-icon" :class="item.iconClass">
            <el-icon>{{ item.icon }}</el-icon>
          </div>
          <div class="card-info">
            <div class="card-title">{{ item.title }}</div>
            <div class="card-value">{{ item.value }}</div>
          </div>
        </el-card>
      </el-col>

    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-row">
      <el-col :span="12">
        <el-card class="chart-card">
          <div ref="repairChartRef" class="chart"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="chart-card">
          <div ref="complaintChartRef" class="chart"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="chart-card">
      <div ref="paymentChartRef" class="chart full"></div>
    </el-card>

  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'

import {
  getDashboardSummary,
  getRepairTrend,
  getComplaintTrend,
  getPaymentDistribution
} from '@/api/dashboard'

/* ========================
   加载状态
======================== */
const loading = ref(true)

/* ========================
   KPI 数据
======================== */
const summaryList = ref([])

/* ========================
   DOM 引用
======================== */
const repairChartRef = ref()
const complaintChartRef = ref()
const paymentChartRef = ref()

let repairChart, complaintChart, paymentChart

/* ========================
   统计数据
======================== */
const loadSummary = async () => {
  try {
    const res = await getDashboardSummary()

    summaryList.value = [
      { 
        title: '报修总数', 
        value: res.repairCount || 0,
        icon: 'Wrench',
        iconClass: 'icon-repair'
      },
      { 
        title: '投诉总数', 
        value: res.complaintCount || 0,
        icon: 'MessageSquare',
        iconClass: 'icon-complaint'
      },
      { 
        title: '已缴金额', 
        value: '¥' + (res.paidAmount || 0).toLocaleString(),
        icon: 'Wallet',
        iconClass: 'icon-paid'
      },
      { 
        title: '未缴金额', 
        value: '¥' + (res.unpaidAmount || 0).toLocaleString(),
        icon: 'Bell',
        iconClass: 'icon-unpaid'
      }
    ]
  } catch (error) {
    console.error('加载统计数据失败:', error)
    // 使用模拟数据
    summaryList.value = [
      { title: '报修总数', value: 128, icon: 'Wrench', iconClass: 'icon-repair' },
      { title: '投诉总数', value: 35, icon: 'MessageSquare', iconClass: 'icon-complaint' },
      { title: '已缴金额', value: '¥128,000', icon: 'Wallet', iconClass: 'icon-paid' },
      { title: '未缴金额', value: '¥35,000', icon: 'Bell', iconClass: 'icon-unpaid' }
    ]
  }
}

/* ========================
   报修趋势
======================== */
const loadRepairChart = async () => {
  const res = await getRepairTrend()

  repairChart = echarts.init(repairChartRef.value)

  repairChart.setOption({
    title: { text: '报修趋势' },
    xAxis: { type: 'category', data: res.days },
    yAxis: { type: 'value' },
    series: [{ data: res.counts, type: 'line', smooth: true }]
  })
}

/* ========================
   投诉趋势
======================== */
const loadComplaintChart = async () => {
  const res = await getComplaintTrend()

  complaintChart = echarts.init(complaintChartRef.value)

  complaintChart.setOption({
    title: { text: '投诉趋势' },
    xAxis: { type: 'category', data: res.days },
    yAxis: { type: 'value' },
    series: [{ data: res.counts, type: 'bar' }]
  })
}

/* ========================
   缴费分布
======================== */
const loadPaymentChart = async () => {
  const res = await getPaymentDistribution()

  paymentChart = echarts.init(paymentChartRef.value)

  paymentChart.setOption({
    title: { text: '缴费类型分布' },
    series: [
      {
        type: 'pie',
        radius: '50%',
        data: res.data
      }
    ]
  })
}

/* ========================
   初始化
======================== */
const initCharts = async () => {
  loading.value = true
  try {
    await loadSummary()
    await loadRepairChart()
    await loadComplaintChart()
    await loadPaymentChart()
  } catch (error) {
    console.error('初始化图表失败:', error)
  } finally {
    loading.value = false
  }
}

/* ========================
   响应式处理
======================== */
const handleResize = () => {
  repairChart?.resize()
  complaintChart?.resize()
  paymentChart?.resize()
}

onMounted(() => {
  initCharts()
  window.addEventListener('resize', handleResize)
})

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
  min-height: calc(100vh - 80px);
  background: #f5f7fa;
}

/* KPI */
.card-row {
  margin-bottom: 20px;
}

.card {
  display: flex;
  align-items: center;
  padding: 20px;
  height: 120px;
}

.card-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  margin-right: 20px;
}

.icon-repair {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
}

.icon-complaint {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  color: #fff;
}

.icon-paid {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  color: #fff;
}

.icon-unpaid {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
  color: #fff;
}

.card-info {
  flex: 1;
}

.card-title {
  color: #666;
  font-size: 14px;
  margin-bottom: 8px;
}

.card-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

/* 图表 */
.chart-row {
  margin-bottom: 20px;
}

.chart-card {
  padding: 15px;
}

.chart {
  width: 100%;
  height: 300px;
}

.full {
  height: 350px;
}
</style>