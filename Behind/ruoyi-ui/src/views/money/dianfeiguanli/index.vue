<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="80px">
      <el-form-item label="业主姓名" prop="userName">
        <el-input v-model="queryParams.userName" placeholder="请输入业主姓名" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="房号" prop="userRoom">
        <el-input v-model="queryParams.userRoom" placeholder="请输入房号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="计费月份" prop="readMonth">
        <el-input v-model="queryParams.readMonth" placeholder="YYYY-MM" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="feeStatus">
        <el-select v-model="queryParams.feeStatus" placeholder="全部" clearable>
          <el-option label="未缴费" value="unpaid" />
          <el-option label="已缴费" value="paid" />
          <el-option label="逾期" value="overdue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="list">
      <el-table-column label="电费ID" align="center" prop="electricityId" width="90" />
      <el-table-column label="业主" align="center" prop="userName" />
      <el-table-column label="房号" align="center" prop="userRoom" />
      <el-table-column label="月份" align="center" prop="readMonth" />
      <el-table-column label="用电量" align="center" prop="usedAmount" />
      <el-table-column label="剩余电量" align="center" prop="surplusElectricity" />
      <el-table-column label="单价" align="center" prop="unitPrice" />
      <el-table-column label="金额" align="center" prop="totalFee" />
      <el-table-column label="余额" align="center" prop="balance" />
      <el-table-column label="状态" align="center" prop="feeStatus" />
      <el-table-column label="截止日期" align="center" prop="dueDate" width="160">
        <template slot-scope="scope">{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</template>
      </el-table-column>
      <el-table-column label="支付时间" align="center" prop="paymentTime" width="160">
        <template slot-scope="scope">{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d} {h}:{i}') }}</template>
      </el-table-column>
      <el-table-column label="订单号" align="center" prop="paymentNo" min-width="180" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listDianfei } from '@/api/dianfeiguanli/dianfeiguanli'

export default {
  name: 'Dianfeiguanli',
  data() {
    return {
      loading: false,
      total: 0,
      list: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        userRoom: null,
        readMonth: null,
        feeStatus: null
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listDianfei(this.queryParams).then(response => {
        this.list = response.rows || []
        this.total = response.total || 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    }
  }
}
</script>
