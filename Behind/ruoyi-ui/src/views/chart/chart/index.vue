<template>
  <div class="app-container chart-page">
    <!-- 搜索表单 -->
    <div class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="72px">
        <el-form-item label="数据内容" prop="chartData">
          <el-input
            v-model="queryParams.chartData"
            placeholder="请输入数据内容"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="更新日期" prop="updateDate">
          <el-date-picker clearable
                          v-model="queryParams.updateDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择更新日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 工具栏 -->
    <div class="tool-bar">
      <el-row :gutter="12">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['chart:chart:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['chart:chart:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['chart:chart:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['chart:chart:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <!-- 表格卡片 -->
    <div class="table-card">
      <el-table
        v-loading="loading"
        :data="chartList"
        @selection-change="handleSelectionChange"
        stripe
        highlight-current-row
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="图表ID" align="center" prop="chartId" />
        <el-table-column label="图表类型" align="center" prop="type" />
        <el-table-column label="数据内容" align="center" prop="chartData" />
        <el-table-column label="更新日期" align="center" prop="updateDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="150">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['chart:chart:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['chart:chart:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="pagination-box">
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <!-- 弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="540px" append-to-body class="chart-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="数据内容" prop="chartData">
          <el-input v-model="form.chartData" placeholder="请输入数据内容" />
        </el-form-item>
        <el-form-item label="更新日期" prop="updateDate">
          <el-date-picker clearable
                          v-model="form.updateDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择更新日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listChart, getChart, delChart, addChart, updateChart } from "@/api/chart/chart"

export default {
  name: "Chart",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      chartList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        chartData: null,
        updateDate: null
      },
      form: {},
      rules: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listChart(this.queryParams).then(response => {
        this.chartList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        chartId: null,
        type: null,
        chartData: null,
        updateDate: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.chartId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加图表数据"
    },
    handleUpdate(row) {
      this.reset()
      const chartId = row.chartId || this.ids
      getChart(chartId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改图表数据"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const func = this.form.chartId ? updateChart : addChart
          func(this.form).then(() => {
            this.$modal.msgSuccess(this.form.chartId ? "修改成功" : "新增成功")
            this.open = false
            this.getList()
          })
        }
      })
    },
    handleDelete(row) {
      const chartIds = row.chartId || this.ids
      this.$modal.confirm('是否确认删除编号为"' + chartIds + '"的数据项？').then(() => {
        return delChart(chartIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      })
    },
    handleExport() {
      this.download('chart/chart/export', { ...this.queryParams }, `chart_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style lang="scss" scoped>
// 页面整体背景 —— 高级柔和渐变
.chart-page {
  padding: 26px 32px;
  background: linear-gradient(135deg, #f5f8ff 0%, #eaf3ff 100%);
  min-height: calc(100vh - 80px);
}

// 搜索卡片
.search-card {
  background: rgba(255, 255, 255, 0.96);
  backdrop-filter: blur(12px);
  padding: 20px 24px;
  border-radius: 16px;
  margin-bottom: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 6px 22px rgba(0, 0, 0, 0.09);
  }
}

// 工具栏
.tool-bar {
  background: rgba(255, 255, 255, 0.96);
  padding: 16px 24px;
  border-radius: 16px;
  margin-bottom: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.06);
}

// 表格卡片
.table-card {
  background: rgba(255, 255, 255, 0.98);
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
  margin-bottom: 20px;
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.12);
  }

  ::v-deep .el-table {
    border-radius: 12px;
    overflow: hidden;
    border: none;

    // 表头渐变
    th {
      background: linear-gradient(135deg, #edf4ff 0%, #e0edff 100%) !important;
      color: #2b579a;
      font-weight: 600;
      border-bottom: 1px solid #d6e8ff;
    }

    // 行悬浮效果
    tr:hover {
      background-color: #f7fbff !important;
    }
  }
}

// 分页
.pagination-box {
  text-align: right;
  padding: 10px 0;
}

// 弹窗美化
::v-deep .chart-dialog .el-dialog {
  border-radius: 16px;
  box-shadow: 0 12px 35px rgba(0, 0, 0, 0.15);
}

.dialog-footer {
  text-align: center;
  padding-top: 10px;
}

// 按钮圆角
::v-deep .el-button--mini {
  border-radius: 6px;
}
</style>
