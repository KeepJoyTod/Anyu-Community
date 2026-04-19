<template>
  <div class="app-container appointment-page">
    <!-- 搜索表单卡片 -->
    <div class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
        <el-form-item label="业主姓名" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="业主姓名"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="业主手机号" prop="userPhone">
          <el-input
            v-model="queryParams.userPhone"
            placeholder="业主手机号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="预约时间" prop="reservationTime">
          <el-date-picker clearable
                          v-model="queryParams.reservationTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="预约时间">
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
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <!-- 表格卡片 -->
    <div class="table-card">
      <el-table
        v-loading="loading"
        :data="appointmentsList"
        @selection-change="handleSelectionChange"
        stripe
        highlight-current-row
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="业主姓名" align="center" prop="userName" />
        <el-table-column label="业主手机号" align="center" prop="userPhone" />
        <el-table-column label="预约时间" align="center" prop="reservationTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.reservationTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>

        <!-- 预约状态自动美化 -->
        <el-table-column label="预约状态" align="center" prop="reservationStatus">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.reservationStatus)" round>{{ scope.row.reservationStatus }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="服务地址" align="center" prop="serviceAddress" />
        <el-table-column label="拒绝原因" align="center" prop="rejectReason" />
        <el-table-column label="操作" align="center" width="160">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              style="color:#f56c6c"
              @click="handleDelete(scope.row)"
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
    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body class="appointment-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="业主姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="业主姓名" />
        </el-form-item>
        <el-form-item label="业主手机号" prop="userPhone">
          <el-input v-model="form.userPhone" placeholder="业主手机号" />
        </el-form-item>
        <el-form-item label="预约时间" prop="reservationTime">
          <el-date-picker v-model="form.reservationTime" type="date" value-format="yyyy-MM-dd" placeholder="预约时间" />
        </el-form-item>
        <el-form-item label="服务地址" prop="serviceAddress">
          <el-input v-model="form.serviceAddress" placeholder="服务地址" />
        </el-form-item>
        <el-form-item label="预约状态" prop="reservationStatus">
          <el-select v-model="form.reservationStatus" placeholder="请选择预约状态" clearable>
            <el-option label="待审核" value="待审核" />
            <el-option label="已通过" value="已通过" />
            <el-option label="已拒绝" value="已拒绝" />
            <el-option label="已完成" value="已完成" />
          </el-select>
        </el-form-item>
        <el-form-item label="拒绝原因" prop="rejectReason">
          <el-input v-model="form.rejectReason" type="textarea" placeholder="拒绝原因" :rows="3" />
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
import { listAppointments, getAppointments, delAppointments, addAppointments, updateAppointments } from "@/api/appointments/appointments"

export default {
  name: "Appointments",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      appointmentsList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        userPhone: null,
        reservationType: null,
        reservationTime: null,
        reservationStatus: null,
        serviceAddress: null,
        rejectReason: null,
      },
      form: {},
      rules: {
        userId: [{ required: true, message: "业主ID不能为空", trigger: "blur" }],
        userName: [{ required: true, message: "业主姓名不能为空", trigger: "blur" }],
        userPhone: [{ required: true, message: "业主手机号不能为空", trigger: "blur" }],
        reservationType: [{ required: true, message: "预约类型不能为空", trigger: "change" }],
        reservationTime: [{ required: true, message: "预约时间不能为空", trigger: "blur" }],
        reservationStatus: [{ required: true, message: "预约状态不能为空", trigger: "change" }],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 预约状态颜色美化
    getStatusType(status) {
      const map = {
        '待审核': 'warning',
        '已通过': 'success',
        '已拒绝': 'danger',
        '已完成': 'primary'
      }
      return map[status] || 'info'
    },
    getList() {
      this.loading = true
      listAppointments(this.queryParams).then(response => {
        this.appointmentsList = response.rows
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
        id: null, userId: null, userName: null, userPhone: null,
        reservationType: null, reservationTime: null, reservationStatus: null,
        serviceAddress: null, rejectReason: null, remark: null
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
      this.title = "添加预约记录"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getAppointments(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改预约记录"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id) {
            updateAppointments(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAppointments(this.form).then(() => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除该预约记录？').then(() => {
        return delAppointments(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      })
    },
    handleExport() {
      this.download('appointments/appointments/export', {
        ...this.queryParams
      }, `appointments_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style lang="scss" scoped>
// 全局统一高级主题背景
.appointment-page {
  padding: 28px 32px;
  background: linear-gradient(135deg, #f9fcff 0%, #eef5ff 100%);
  min-height: calc(100vh - 80px);
}

// 搜索卡片
.search-card {
  background: rgba(255, 255, 255, 0.97);
  backdrop-filter: blur(12px);
  padding: 24px 26px;
  border-radius: 16px;
  margin-bottom: 20px;
  box-shadow: 0 4px 18px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 6px 24px rgba(0, 0, 0, 0.09);
  }
}

// 工具栏
.tool-bar {
  background: rgba(255, 255, 255, 0.97);
  padding: 16px 24px;
  border-radius: 16px;
  margin-bottom: 20px;
  box-shadow: 0 4px 18px rgba(0, 0, 0, 0.06);
}

// 表格卡片
.table-card {
  background: rgba(255, 255, 255, 0.98);
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 6px 24px rgba(0, 0, 0, 0.08);
  margin-bottom: 20px;
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 10px 32px rgba(0, 0, 0, 0.12);
  }

  ::v-deep .el-table {
    border-radius: 12px;
    overflow: hidden;
    border: none;
  }

  ::v-deep .el-table__header th {
    background: linear-gradient(135deg, #f0f7ff 0%, #e6f2ff 100%) !important;
    color: #2b579a;
    font-weight: 600;
    border-bottom: 1px solid #d6e8ff;
  }

  ::v-deep .el-table__body tr:hover {
    background-color: #f7fbff !important;
  }
}

// 分页
.pagination-box {
  text-align: right;
  padding: 10px 0;
}

// 弹窗美化
::v-deep .appointment-dialog .el-dialog {
  border-radius: 16px;
  box-shadow: 0 12px 35px rgba(0, 0, 0, 0.15);
  border: none;
}

.dialog-footer {
  text-align: center;
}

// 按钮圆角
::v-deep .el-button--mini {
  border-radius: 6px;
}

// 状态标签
::v-deep .el-tag {
  border-radius: 12px;
  font-weight: 500;
  border: none;
}
</style>
