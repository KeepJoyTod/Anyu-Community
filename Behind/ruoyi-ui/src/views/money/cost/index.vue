<template>
  <div class="app-container cost-page">
    <!-- 搜索表单区域 -->
    <div class="search-box">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="72px">
        <el-form-item label="业主ID" prop="userId">
          <el-input
            v-model="queryParams.userId"
            placeholder="请输入业主ID"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="业主姓名" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入业主姓名"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="业主房号" prop="userRoom">
          <el-input
            v-model="queryParams.userRoom"
            placeholder="请输入业主房号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="缴费金额" prop="paymentAmount">
          <el-input
            v-model="queryParams.paymentAmount"
            placeholder="请输入缴费金额"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="订单号" prop="paymentNo">
          <el-input
            v-model="queryParams.paymentNo"
            placeholder="请输入缴费订单号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="缴费时间" prop="paymentTime">
          <el-date-picker clearable
                          v-model="queryParams.paymentTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择缴费时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="滞纳金" prop="overdueAmount">
          <el-input
            v-model="queryParams.overdueAmount"
            placeholder="请输入逾期滞纳金"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="截止时间" prop="dueTime">
          <el-date-picker clearable
                          v-model="queryParams.dueTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择截止时间">
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
            v-hasPermi="['cost:cos:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['cost:cos:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['cost:cos:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['cost:cos:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <!-- 表格卡片 -->
    <div class="table-card">
      <el-table v-loading="loading" :data="cosList" @selection-change="handleSelectionChange" stripe highlight-current-row>
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键ID" align="center" prop="id" />
        <el-table-column label="业主ID" align="center" prop="userId" />
        <el-table-column label="业主姓名" align="center" prop="userName" />
        <el-table-column label="业主房号" align="center" prop="userRoom" />
        <el-table-column label="缴费类型" align="center" prop="paymentType" />
        <el-table-column label="缴费金额" align="center" prop="paymentAmount" />
        <el-table-column label="缴费状态" align="center" prop="paymentStatus">
          <template slot-scope="scope">
            <el-tag :type="scope.row.paymentStatus === '已缴费' ? 'success' : 'warning'">
              {{ scope.row.paymentStatus }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="缴费订单号" align="center" prop="paymentNo" />
        <el-table-column label="缴费时间" align="center" prop="paymentTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="逾期滞纳金" align="center" prop="overdueAmount" />
        <el-table-column label="缴费截止时间" align="center" prop="dueTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.dueTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注信息" align="center" prop="remark" />
        <el-table-column label="操作" align="center" width="160">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['cost:cos:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['cost:cos:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body class="cost-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="业主ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入业主ID" />
        </el-form-item>
        <el-form-item label="业主姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入业主姓名" />
        </el-form-item>
        <el-form-item label="业主房号" prop="userRoom">
          <el-input v-model="form.userRoom" placeholder="请输入业主房号" />
        </el-form-item>
        <el-form-item label="缴费金额" prop="paymentAmount">
          <el-input v-model="form.paymentAmount" placeholder="请输入缴费金额" />
        </el-form-item>
        <el-form-item label="缴费订单号" prop="paymentNo">
          <el-input v-model="form.paymentNo" placeholder="请输入缴费订单号" />
        </el-form-item>
        <el-form-item label="缴费时间" prop="paymentTime">
          <el-date-picker clearable
                          v-model="form.paymentTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择缴费时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="逾期滞纳金" prop="overdueAmount">
          <el-input v-model="form.overdueAmount" placeholder="请输入逾期滞纳金" />
        </el-form-item>
        <el-form-item label="缴费截止时间" prop="dueTime">
          <el-date-picker clearable
                          v-model="form.dueTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择缴费截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" :rows="3" />
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
import { listCos, getCos, delCos, addCos, updateCos } from "@/api/cost/cos"

export default {
  name: "Cos",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      cosList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        userRoom: null,
        paymentType: null,
        paymentAmount: null,
        paymentStatus: null,
        paymentNo: null,
        paymentTime: null,
        overdueAmount: null,
        dueTime: null,
      },
      form: {},
      rules: {
        userId: [
          { required: true, message: "业主ID不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "业主姓名不能为空", trigger: "blur" }
        ],
        userRoom: [
          { required: true, message: "业主房号不能为空", trigger: "blur" }
        ],
        paymentType: [
          { required: true, message: "缴费类型不能为空", trigger: "change" }
        ],
        paymentAmount: [
          { required: true, message: "缴费金额不能为空", trigger: "blur" }
        ],
        paymentStatus: [
          { required: true, message: "缴费状态不能为空", trigger: "change" }
        ],
        dueTime: [
          { required: true, message: "缴费截止时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listCos(this.queryParams).then(response => {
        this.cosList = response.rows
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
        id: null, userId: null, userName: null, userRoom: null,
        paymentType: null, paymentAmount: null, paymentStatus: null,
        paymentNo: null, paymentTime: null, overdueAmount: null,
        dueTime: null, remark: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加小区缴费管理"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getCos(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改小区缴费管理"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const func = this.form.id ? updateCos : addCos
          func(this.form).then(res => {
            this.$modal.msgSuccess(this.form.id ? "修改成功" : "新增成功")
            this.open = false
            this.getList()
          })
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除编号为"' + ids + '"的数据项？').then(() => {
        return delCos(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      })
    },
    handleExport() {
      this.download('cost/cos/export', { ...this.queryParams }, `cos_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style lang="scss" scoped>
// 页面整体背景 —— 高级渐变
.cost-page {
  padding: 24px 30px;
  background: linear-gradient(135deg, #f7f9fc 0%, #eef5ff 100%);
  min-height: calc(100vh - 80px);
}

// 搜索框卡片
.search-box {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  padding: 20px 24px;
  border-radius: 16px;
  margin-bottom: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
  }
}

// 工具栏
.tool-bar {
  background: rgba(255, 255, 255, 0.95);
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
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  }

  ::v-deep .el-table {
    border-radius: 12px;
    overflow: hidden;
    border: none;

    th {
      background: linear-gradient(135deg, #f0f7ff 0%, #e6f2ff 100%) !important;
      color: #2b579a;
      font-weight: 600;
      border-bottom: 1px solid #e6f2ff;
    }

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
::v-deep .cost-dialog .el-dialog {
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

::v-deep .cost-dialog .el-form-item__label {
  font-weight: 500;
}

.dialog-footer {
  text-align: center;
  padding-top: 10px;
}

// 按钮样式优化
::v-deep .el-button--mini {
  border-radius: 6px;
}

// 状态标签
::v-deep .el-tag {
  border-radius: 4px;
  font-weight: 500;
}
</style>
