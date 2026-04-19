<template>
  <div class="app-container station-page">
    <!-- 搜索表单卡片 -->
    <div class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
        <el-form-item label="快递单号" prop="expressNo">
          <el-input
            v-model="queryParams.expressNo"
            placeholder="快递单号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="业主姓名" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="业主姓名"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="业主房号" prop="userRoom">
          <el-input
            v-model="queryParams.userRoom"
            placeholder="业主房号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="快递公司" prop="expressCompany">
          <el-input
            v-model="queryParams.expressCompany"
            placeholder="快递公司"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="入库时间" prop="storageTime">
          <el-date-picker clearable
                          v-model="queryParams.storageTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="入库时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="取件时间" prop="pickTime">
          <el-date-picker clearable
                          v-model="queryParams.pickTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="取件时间">
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
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
          >删除</el-button>
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
        :data="stationList"
        @selection-change="handleSelectionChange"
        stripe
        highlight-current-row
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" prop="id" />
        <el-table-column label="快递单号" align="center" prop="expressNo" />
        <el-table-column label="业主姓名" align="center" prop="userName" />
        <el-table-column label="业主房号" align="center" prop="userRoom" />
        <el-table-column label="快递公司" align="center" prop="expressCompany" />

        <!-- 驿站状态自动美化 -->
        <el-table-column label="驿站状态" align="center" prop="stationStatus">
          <template slot-scope="scope">
            <el-tag :type="
                scope.row.stationStatus === '已取件' ? 'success' :
                scope.row.stationStatus === '待取件' ? 'warning' :
                scope.row.stationStatus === '已入库' ? 'primary' : 'info'
              ">
              {{ scope.row.stationStatus }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="入库时间" align="center" prop="storageTime" width="170">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.storageTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="取件时间" align="center" prop="pickTime" width="170">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.pickTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作员" align="center" prop="operator" />
        <el-table-column label="操作" align="center" width="160">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="580px" append-to-body class="station-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="快递单号" prop="expressNo">
          <el-input v-model="form.expressNo" placeholder="请输入快递单号" />
        </el-form-item>
        <el-form-item label="业主ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入业主ID" />
        </el-form-item>
        <el-form-item label="业主姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入业主姓名" />
        </el-form-item>
        <el-form-item label="业主房号" prop="userRoom">
          <el-input v-model="form.userRoom" placeholder="请输入业主房号" />
        </el-form-item>
        <el-form-item label="快递公司" prop="expressCompany">
          <el-input v-model="form.expressCompany" placeholder="请输入快递公司" />
        </el-form-item>
        <el-form-item label="驿站状态" prop="stationStatus">
          <el-input v-model="form.stationStatus" placeholder="请输入驿站状态" />
        </el-form-item>
        <el-form-item label="入库时间" prop="storageTime">
          <el-date-picker v-model="form.storageTime" type="date" value-format="yyyy-MM-dd" placeholder="入库时间" />
        </el-form-item>
        <el-form-item label="取件时间" prop="pickTime">
          <el-date-picker v-model="form.pickTime" type="date" value-format="yyyy-MM-dd" placeholder="取件时间" />
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="备注信息" :rows="3" />
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
import { listStation, getStation, delStation, addStation, updateStation } from "@/api/station/station"

export default {
  name: "Station",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      stationList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        expressNo: null,
        userId: null,
        userName: null,
        userPhone: null,
        userRoom: null,
        expressCompany: null,
        stationStatus: null,
        storageTime: null,
        pickTime: null,
        operator: null,
      },
      form: {},
      rules: {
        expressNo: [{ required: true, message: "快递单号不能为空", trigger: "blur" }],
        userId: [{ required: true, message: "业主ID不能为空", trigger: "blur" }],
        userName: [{ required: true, message: "业主姓名不能为空", trigger: "blur" }],
        expressCompany: [{ required: true, message: "快递公司不能为空", trigger: "blur" }],
        stationStatus: [{ required: true, message: "驿站状态不能为空", trigger: "change" }],
        storageTime: [{ required: true, message: "入库时间不能为空", trigger: "blur" }],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listStation(this.queryParams).then(response => {
        this.stationList = response.rows
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
        id: null,
        expressNo: null,
        userId: null,
        userName: null,
        userPhone: null,
        userRoom: null,
        expressCompany: null,
        stationStatus: null,
        storageTime: null,
        pickTime: null,
        operator: null,
        remark: null
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
      this.title = "添加驿站服务记录"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStation(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改驿站服务记录"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id) {
            updateStation(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStation(this.form).then(() => {
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
      this.$modal.confirm('是否确认删除该驿站记录？').then(() => {
        return delStation(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      })
    },
    handleExport() {
      this.download('station/station/export', {
        ...this.queryParams
      }, `station_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style lang="scss" scoped>
// 页面整体背景（统一主题）
.station-page {
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
::v-deep .station-dialog .el-dialog {
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
