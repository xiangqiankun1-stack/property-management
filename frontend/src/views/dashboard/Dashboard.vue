<template>
  <div class="dashboard">
    <el-row :gutter="20">

      <el-col :span="6">
        <el-card>
          <div class="card-title">用户数</div>
          <div class="card-num">128</div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div class="card-title">小区数量</div>
          <div class="card-num">12</div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div class="card-title">报修工单</div>
          <div class="card-num">36</div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div class="card-title">缴费率</div>
          <div class="card-num">87%</div>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<style scoped>
.card-title { color:#666; font-size:14px; }
.card-num { font-size:28px; font-weight:bold; margin-top:10px; }
</style>