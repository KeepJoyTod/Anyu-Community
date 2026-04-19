<template>
  <div class="app-container notice-read-page">
    <!-- 搜索表单卡片 -->
    <div class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="72px">
        <el-form-item label="通知ID" prop="noticeId">
          <el-input
            v-model="queryParams.noticeId"
            placeholder="请输入通知ID"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="业主ID" prop="userId">
          <el-input
            v-model="queryParams.userId"
            placeholder="请输入业主ID"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="阅读时间" prop="readTime">
          <el-date-picker clearable
                          v-model="queryParams.readTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择阅读时间">
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
            v-hasPermi="['noticeRead:noticeRead:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['noticeRead:noticeRead:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <!-- 表格卡片 -->
    <div class="table-card">
      <el-table
        v-loading="loading"
        :data="noticeReadList"
        @selection-change="handleSelectionChange"
        stripe
        highlight-current-row
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="通知ID" align="center" prop="noticeId" />
        <el-table-column label="业主ID" align="center" prop="userId" />
        <el-table-column label="阅读时间" align="center" prop="readTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.readTime, '{y}-{m}-{d}') }}</span>
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
              v-hasPermi="['noticeRead:noticeRead:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['noticeRead:noticeRead:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="540px" append-to-body class="read-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="通知ID" prop="noticeId">
          <el-input v-model="form.noticeId" placeholder="请输入通知ID" />
        </el-form-item>
        <el-form-item label="业主ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入业主ID" />
        </el-form-item>
        <el-form-item label="阅读时间" prop="readTime">
          <el-date-picker clearable
                          v-model="form.readTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择阅读时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="创建人" prop="createBy">
          <el-input v-model="form.createBy" placeholder="请输入创建人" />
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
import { listNoticeRead, getNoticeRead, delNoticeRead, addNoticeRead, updateNoticeRead } from "@/api/notice01/noticeRead/noticeRead"

export default {
  name: "NoticeRead",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      noticeReadList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeId: null,
        userId: null,
        readTime: null,
      },
      form: {},
      rules: {
        noticeId: [
          { required: true, message: "通知ID不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "业主ID不能为空", trigger: "blur" }
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
      listNoticeRead(this.queryParams).then(response => {
        this.noticeReadList = response.rows
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
        noticeId: null,
        userId: null,
        readTime: null,
        createBy: null,
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
      this.title = "添加通知阅读记录"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getNoticeRead(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改通知阅读记录"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const func = this.form.id ? updateNoticeRead : addNoticeRead
          func(this.form).then(() => {
            this.$modal.msgSuccess(this.form.id ? "修改成功" : "新增成功")
            this.open = false
            this.getList()
          })
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除阅读记录编号为"' + ids + '"的数据项？').then(() => {
        return delNoticeRead(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      })
    },
    handleExport() {
      this.download('noticeRead/noticeRead/export', {
        ...this.queryParams
      }, `noticeRead_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style lang="scss" scoped>
// 页面整体渐变背景
.notice-read-page {
  padding: 26px 32px;
  background: linear-gradient(135deg, #f8faff 0%, #eef5ff 50%, #e6f0ff 100%);
  min-height: calc(100vh - 80px);
}

// 搜索卡片
.search-card {
  background: rgba(255, 255, 255, 0.96);
  backdrop-filter: blur(12px);
  padding: 22px 26px;
  border-radius: 16px;
  margin-bottom: 20px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 6px 24px rgba(0, 0, 0, 0.09);
  }
}

// 工具栏
.tool-bar {
  background: rgba(255, 255, 255, 0.96);
  padding: 16px 24px;
  border-radius: 16px;
  margin-bottom: 20px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}

// 表格卡片
.table-card {
  background: rgba(255, 255, 255, 0.98);
  border-radius: 16px;
  padding: 22px;
  box-shadow: 0 6px 22px rgba(0, 0, 0, 0.08);
  margin-bottom: 20px;
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 10px 32px rgba(0, 0, 0, 0.12);
  }

  ::v-deep .el-table {
    border-radius: 12px;
    overflow: hidden;
    border: none;

    // 表头渐变
    th {
      background: linear-gradient(135deg, #f0f7ff 0%, #e6f2ff 100%) !important;
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
::v-deep .read-dialog .el-dialog {
  border-radius: 16px;
  box-shadow: 0 12px 35px rgba(0, 0, 0, 0.15);
}

.dialog-footer {
  text-align: center;
  padding-top: 10px;
}

// 按钮圆角优化
::v-deep .el-button--mini {
  border-radius: 6px;
}
</style>
