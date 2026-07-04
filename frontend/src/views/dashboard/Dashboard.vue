<template>
  <div class="dashboard">

    <!-- KPI 卡片 -->
    <div class="card-row">
      <el-card v-for="item in summaryList" :key="item.title" class="card">
        <div class="card-title">{{ item.title }}</div>
        <div class="card-value">{{ item.value }}</div>
      </el-card>
    </div>

    <!-- 图表区域 -->
    <div class="chart-row">

      <div ref="repairChartRef" class="chart"></div>

      <div ref="complaintChartRef" class="chart"></div>

    </div>

    <div ref="paymentChartRef" class="chart full"></div>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

import {
  getDashboardSummary,
  getRepairTrend,
  getComplaintTrend,
  getPaymentDistribution
} from '@/api/dashboard'

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
   初始化数据
======================== */
const loadSummary = async () => {
  const res = await getDashboardSummary()

  summaryList.value = [
    { title: '报修总数', value: res.repairCount },
    { title: '投诉总数', value: res.complaintCount },
    { title: '已缴金额', value: res.paidAmount },
    { title: '未缴金额', value: res.unpaidAmount }
  ]
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
    series: [
      {
        data: res.counts,
        type: 'line',
        smooth: true
      }
    ]
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
    series: [
      {
        data: res.counts,
        type: 'bar'
      }
    ]
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
onMounted(() => {
  loadSummary()
  loadRepairChart()
  loadComplaintChart()
  loadPaymentChart()
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.card-row {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.card {
  flex: 1;
  text-align: center;
}

.card-title {
  font-size: 14px;
  color: #666;
}

.card-value {
  font-size: 24px;
  font-weight: bold;
  margin-top: 10px;
}

.chart-row {
  display: flex;
  gap: 20px;
}

.chart {
  width: 60%;
  height: 300px;
  background: #fff;
  padding: 10px;
}

.full {
  width: 100%;
  margin-top: 20px;
}
</style>
