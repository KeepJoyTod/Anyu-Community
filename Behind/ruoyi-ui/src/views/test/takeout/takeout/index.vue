<template>
  <div class="app-container takeout-page">
    <!-- 搜索表单卡片 -->
    <div class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input
            v-model="queryParams.userId"
            placeholder="请输入用户ID"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="用户姓名" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入用户姓名"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="用户手机号" prop="userPhone">
          <el-input
            v-model="queryParams.userPhone"
            placeholder="请输入用户手机号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="用户房号" prop="userRoom">
          <el-input
            v-model="queryParams.userRoom"
            placeholder="请输入用户房号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="订单号" prop="orderNo">
          <el-input
            v-model="queryParams.orderNo"
            placeholder="请输入订单号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="商家名称" prop="shopName">
          <el-input
            v-model="queryParams.shopName"
            placeholder="请输入商家名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="支付时间" prop="paymentTime">
          <el-date-picker clearable
                          v-model="queryParams.paymentTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="支付时间">
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
            v-hasPermi="['takeout:takeout:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['takeout:takeout:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['takeout:takeout:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['takeout:takeout:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <!-- 表格卡片 -->
    <div class="table-card">
      <el-table
        v-loading="loading"
        :data="takeoutList"
        @selection-change="handleSelectionChange"
        stripe
        highlight-current-row
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="订单ID" align="center" prop="id" />
        <el-table-column label="用户姓名" align="center" prop="userName" />
        <el-table-column label="用户手机号" align="center" prop="userPhone" />
        <el-table-column label="用户房号" align="center" prop="userRoom" />
        <el-table-column label="订单号" align="center" prop="orderNo" />
        <el-table-column label="商家名称" align="center" prop="shopName" />
        <el-table-column label="订单金额" align="center" prop="totalAmount" />
        <el-table-column label="实付金额" align="center" prop="actualAmount" />

        <!-- 订单状态自动美化 -->
        <el-table-column label="订单状态" align="center" prop="orderStatus">
          <template slot-scope="scope">
            <el-tag :type="
                scope.row.orderStatus === '已完成' ? 'success' :
                scope.row.orderStatus === '待支付' ? 'warning' :
                scope.row.orderStatus === '已取消' ? 'danger' : 'info'
              ">
              {{ scope.row.orderStatus }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="支付时间" align="center" prop="paymentTime" width="170">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="配送地址" align="center" prop="deliveryAddress" />
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
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body class="takeout-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="用户姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户姓名" />
        </el-form-item>
        <el-form-item label="用户手机号" prop="userPhone">
          <el-input v-model="form.userPhone" placeholder="请输入用户手机号" />
        </el-form-item>
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="商家名称" prop="shopName">
          <el-input v-model="form.shopName" placeholder="请输入商家名称" />
        </el-form-item>
        <el-form-item label="订单总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="订单总金额" />
        </el-form-item>
        <el-form-item label="实付金额" prop="actualAmount">
          <el-input v-model="form.actualAmount" placeholder="实付金额" />
        </el-form-item>
        <el-form-item label="订单状态" prop="orderStatus">
          <el-input v-model="form.orderStatus" placeholder="订单状态" />
        </el-form-item>
        <el-form-item label="支付时间" prop="paymentTime">
          <el-date-picker v-model="form.paymentTime" type="date" value-format="yyyy-MM-dd" placeholder="支付时间" />
        </el-form-item>
        <el-form-item label="配送地址" prop="deliveryAddress">
          <el-input v-model="form.deliveryAddress" placeholder="配送地址" />
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
import { listTakeout, getTakeout, delTakeout, addTakeout, updateTakeout } from "@/api/takeout/takeout"

export default {
  name: "Takeout",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      takeoutList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        userPhone: null,
        userRoom: null,
        orderNo: null,
        shopName: null,
        shopPhone: null,
        totalAmount: null,
        deliveryFee: null,
        actualAmount: null,
        orderStatus: null,
        paymentMethod: null,
        paymentTime: null,
        deliveryTime: null,
        actualDeliveryTime: null,
        deliveryAddress: null,
        cancelReason: null,
        reservationId: null,
        complaintId: null,
      },
      form: {},
      rules: {
        userId: [{ required: true, message: "用户ID不能为空", trigger: "blur" }],
        userName: [{ required: true, message: "用户姓名不能为空", trigger: "blur" }],
        userPhone: [{ required: true, message: "用户手机号不能为空", trigger: "blur" }],
        orderNo: [{ required: true, message: "订单号不能为空", trigger: "blur" }],
        shopName: [{ required: true, message: "商家名称不能为空", trigger: "blur" }],
        totalAmount: [{ required: true, message: "订单总金额不能为空", trigger: "blur" }],
        actualAmount: [{ required: true, message: "实付金额不能为空", trigger: "blur" }],
        orderStatus: [{ required: true, message: "订单状态不能为空", trigger: "change" }],
        deliveryAddress: [{ required: true, message: "配送地址不能为空", trigger: "blur" }],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listTakeout(this.queryParams).then(response => {
        this.takeoutList = response.rows
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
        id: null, userId: null, userName: null, userPhone: null, userRoom: null,
        orderNo: null, shopName: null, totalAmount: null, actualAmount: null,
        orderStatus: null, paymentTime: null, deliveryAddress: null
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
      this.title = "添加外卖订单"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTakeout(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改外卖订单"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id) {
            updateTakeout(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addTakeout(this.form).then(() => {
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
      this.$modal.confirm('是否确认删除该订单？').then(() => {
        return delTakeout(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      })
    },
    handleExport() {
      this.download('takeout/takeout/export', {
        ...this.queryParams
      }, `takeout_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style lang="scss" scoped>
// 全局页面背景（清爽渐变）
.takeout-page {
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

    th {
      background: linear-gradient(135deg, #f0f7ff 0%, #e6f2ff 100%) !important;
      color: #2b579a;
      font-weight: 600;
      border-bottom: 1px solid #d6e8ff;
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

// 弹窗
::v-deep .takeout-dialog .el-dialog {
  border-radius: 16px;
  box-shadow: 0 12px 35px rgba(0, 0, 0, 0.15);
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
  border-radius: 4px;
  font-weight: 500;
}
</style>
