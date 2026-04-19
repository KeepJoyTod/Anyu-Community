<template>
  <div class="app-container complaint-page">
    <!-- 搜索表单卡片 -->
    <div class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
        <el-form-item label="投诉人姓名" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="投诉人姓名"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="投诉标题" prop="complaintTitle">
          <el-input
            v-model="queryParams.complaintTitle"
            placeholder="投诉标题"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="处理人姓名" prop="handleUserName">
          <el-input
            v-model="queryParams.handleUserName"
            placeholder="处理人姓名"
            clearable
            @keyup.enter.native="handleQuery"
          />
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
          >新增投诉建议</el-button>
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
        :data="complaintList"
        @selection-change="handleSelectionChange"
        stripe
        highlight-current-row
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="投诉人姓名" align="center" prop="userName" />
        <el-table-column label="投诉人手机号" align="center" prop="userPhone" />
        <el-table-column label="投诉标题" align="center" prop="complaintTitle" />
        <el-table-column label="投诉内容" align="center" prop="complaintContent" />

        <!-- 投诉类型显示中文 -->
        <el-table-column label="投诉类型" align="center" prop="complaintType">
          <template slot-scope="scope">
            <span v-if="scope.row.complaintType == '0'">环境</span>
            <span v-else-if="scope.row.complaintType == '1'">安全</span>
            <span v-else-if="scope.row.complaintType == '2'">服务</span>
            <span v-else-if="scope.row.complaintType == '3'">噪音</span>
            <span v-else>{{ scope.row.complaintType }}</span>
          </template>
        </el-table-column>

        <el-table-column label="投诉状态" align="center" prop="complaintStatus">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.complaintStatus)" round>{{ scope.row.complaintStatus }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="处理人姓名" align="center" prop="handleUserName" />
        <el-table-column label="处理结果" align="center" prop="handleResult" />
        <el-table-column label="处理时间" align="center" prop="handleTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.handleTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
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
              style="color:#f56c6c"
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
    <el-dialog :title="title" :visible.sync="open" width="580px" append-to-body class="complaint-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="投诉人姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入投诉人姓名" />
        </el-form-item>
        <el-form-item label="投诉人手机号" prop="userPhone">
          <el-input v-model="form.userPhone" placeholder="请输入投诉人手机号" />
        </el-form-item>
        <el-form-item label="投诉标题" prop="complaintTitle">
          <el-input v-model="form.complaintTitle" placeholder="请输入投诉标题" />
        </el-form-item>
        <el-form-item label="投诉类型" prop="complaintType">
          <el-select v-model="form.complaintType" placeholder="请选择投诉类型">
            <el-option
              v-for="dict in complaintTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="投诉内容">
          <editor v-model="form.complaintContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="投诉状态" prop="complaintStatus">
          <el-select v-model="form.complaintStatus" placeholder="请选择投诉状态" clearable>
            <el-option label="待处理" value="待处理" />
            <el-option label="处理中" value="处理中" />
            <el-option label="已解决" value="已解决" />
            <el-option label="已驳回" value="已驳回" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理人姓名" prop="handleUserName">
          <el-input v-model="form.handleUserName" placeholder="请输入处理人姓名" />
        </el-form-item>
        <el-form-item label="处理结果" prop="handleResult">
          <el-input v-model="form.handleResult" type="textarea" placeholder="处理结果" :rows="3" />
        </el-form-item>
        <el-form-item label="处理时间" prop="handleTime">
          <el-date-picker clearable
                          v-model="form.handleTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="处理时间">
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
import { listComplaint, getComplaint, delComplaint, addComplaint, updateComplaint } from "@/api/complaint/complaint"

export default {
  name: "Complaint",
  data() {
    return {
      complaintTypeOptions: [
        { dictLabel: "环境", dictValue: "0" },
        { dictLabel: "安全", dictValue: "1" },
        { dictLabel: "服务", dictValue: "2" },
        { dictLabel: "噪音", dictValue: "3" }
      ],
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      complaintList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        userPhone: null,
        complaintTitle: null,
        complaintContent: null,
        complaintType: null,
        complaintStatus: null,
        handleUserId: null,
        handleUserName: null,
        handleResult: null,
        handleTime: null,
      },
      form: {},
      rules: {
        userId: [
          { required: true, message: "投诉人ID不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "投诉人姓名不能为空", trigger: "blur" }
        ],
        complaintTitle: [
          { required: true, message: "投诉标题不能为空", trigger: "blur" }
        ],
        complaintContent: [
          { required: true, message: "投诉内容不能为空", trigger: "blur" }
        ],
        complaintType: [
          { required: true, message: "投诉类型不能为空", trigger: "change" }
        ],
        complaintStatus: [
          { required: true, message: "投诉状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getStatusType(status) {
      const map = {
        '待处理': 'warning',
        '处理中': 'primary',
        '已解决': 'success',
        '已驳回': 'danger'
      }
      return map[status] || 'info'
    },
    getList() {
      this.loading = true
      listComplaint(this.queryParams).then(response => {
        this.complaintList = response.rows
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
        userId: null,
        userName: null,
        userPhone: null,
        complaintTitle: null,
        complaintContent: null,
        complaintType: null,
        complaintStatus: null,
        handleUserId: null,
        handleUserName: null,
        handleResult: null,
        handleTime: null,
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
      this.title = "添加投诉建议"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getComplaint(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改投诉建议"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id) {
            updateComplaint(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addComplaint(this.form).then(() => {
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
      this.$modal.confirm('是否确认删除该投诉记录？').then(() => {
        return delComplaint(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      })
    },
    handleExport() {
      this.download('complaint/complaint/export', {
        ...this.queryParams
      }, `complaint_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style lang="scss" scoped>
// 全局统一高级主题背景
.complaint-page {
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
::v-deep .complaint-dialog .el-dialog {
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
