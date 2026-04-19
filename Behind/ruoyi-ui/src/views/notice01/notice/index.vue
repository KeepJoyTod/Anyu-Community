<template>
  <div class="app-container notice-page">
    <!-- 搜索表单卡片 -->
    <div class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="72px">
        <el-form-item label="通知标题" prop="noticeTitle">
          <el-input
            v-model="queryParams.noticeTitle"
            placeholder="请输入通知标题"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="发布人ID" prop="publishUserId">
          <el-input
            v-model="queryParams.publishUserId"
            placeholder="请输入发布人ID"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="发布人姓名" prop="publishUserName">
          <el-input
            v-model="queryParams.publishUserName"
            placeholder="请输入发布人姓名"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="发布时间" prop="publishTime">
          <el-date-picker clearable
                          v-model="queryParams.publishTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择发布时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否置顶" prop="topFlag">
          <el-input
            v-model="queryParams.topFlag"
            placeholder="请输入是否置顶"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="置顶结束时间" prop="topEndTime">
          <el-date-picker clearable
                          v-model="queryParams.topEndTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择置顶结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="阅读次数" prop="readCount">
          <el-input
            v-model="queryParams.readCount"
            placeholder="请输入阅读次数"
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
            v-hasPermi="['notice:notice:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['notice:notice:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <!-- 表格卡片 -->
    <div class="table-card">
      <el-table
        v-loading="loading"
        :data="noticeList"
        @selection-change="handleSelectionChange"
        stripe
        highlight-current-row
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="通知标题" align="center" prop="noticeTitle" />
        <el-table-column label="通知内容" align="center" prop="noticeContent" />
        <el-table-column label="通知类型" align="center" prop="noticeType" />
        <el-table-column label="发布人ID" align="center" prop="publishUserId" />
        <el-table-column label="发布人姓名" align="center" prop="publishUserName" />
        <el-table-column label="发布时间" align="center" prop="publishTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>

        <!-- 状态美化 -->
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '已发布' ? 'success' : 'info'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="是否置顶" align="center" prop="topFlag">
          <template slot-scope="scope">
            <el-tag type="warning" v-if="scope.row.topFlag === '是'">
              已置顶
            </el-tag>
            <el-tag type="info" v-else>
              未置顶
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="置顶结束时间" align="center" prop="topEndTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.topEndTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="阅读次数" align="center" prop="readCount" />
        <el-table-column label="备注信息" align="center" prop="remark" />
        <el-table-column label="操作" align="center" width="160">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['notice:notice:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['notice:notice:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body class="notice-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="通知标题" prop="noticeTitle">
          <el-input v-model="form.noticeTitle" placeholder="请输入通知标题" />
        </el-form-item>
        <el-form-item label="通知内容">
          <editor v-model="form.noticeContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="通知类型" prop="noticeType">
          <el-input v-model="form.noticeType" placeholder="请输入通知类型" />
        </el-form-item>
        <el-form-item label="发布人ID" prop="publishUserId">
          <el-input v-model="form.publishUserId" placeholder="请输入发布人ID" />
        </el-form-item>
        <el-form-item label="发布人姓名" prop="publishUserName">
          <el-input v-model="form.publishUserName" placeholder="请输入发布人姓名" />
        </el-form-item>
        <el-form-item label="发布时间" prop="publishTime">
          <el-date-picker clearable
                          v-model="form.publishTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择发布时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否置顶" prop="topFlag">
          <el-input v-model="form.topFlag" placeholder="请输入是否置顶" />
        </el-form-item>
        <el-form-item label="置顶结束时间" prop="topEndTime">
          <el-date-picker clearable
                          v-model="form.topEndTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择置顶结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="阅读次数" prop="readCount">
          <el-input v-model="form.readCount" placeholder="请输入阅读次数" />
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" :rows="3" />
        </el-form-item>
        <el-form-item label="发布状态" prop="status">
          <el-input v-model="form.status" placeholder="请输入发布状态" />
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
import { listNotice, getNotice, delNotice, addNotice, updateNotice } from "@/api/notice01/notice/notice"

export default {
  name: "Notice",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      noticeList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeTitle: null,
        noticeContent: null,
        noticeType: null,
        publishUserId: null,
        publishUserName: null,
        publishTime: null,
        status: null,
        topFlag: null,
        topEndTime: null,
        readCount: null,
      },
      form: {},
      rules: {
        noticeTitle: [
          { required: true, message: "通知标题不能为空", trigger: "blur" }
        ],
        noticeContent: [
          { required: true, message: "通知内容不能为空", trigger: "blur" }
        ],
        noticeType: [
          { required: true, message: "通知类型不能为空", trigger: "change" }
        ],
        publishUserId: [
          { required: true, message: "发布人ID不能为空", trigger: "blur" }
        ],
        publishUserName: [
          { required: true, message: "发布人姓名不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
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
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows
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
        noticeTitle: null,
        noticeContent: null,
        noticeType: null,
        publishUserId: null,
        publishUserName: null,
        publishTime: null,
        status: null,
        topFlag: null,
        topEndTime: null,
        readCount: null,
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
      this.title = "添加小区通知管理"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getNotice(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改小区通知管理"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateNotice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addNotice(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除小区通知管理编号为"' + ids + '"的数据项？').then(() => {
        return delNotice(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      })
    },
    handleExport() {
      this.download('notice/notice/export', {
        ...this.queryParams
      }, `notice_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style lang="scss" scoped>
// 页面整体 —— 高级渐变背景
.notice-page {
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

    // 行悬浮
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
::v-deep .notice-dialog .el-dialog {
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

// 状态标签
::v-deep .el-tag {
  border-radius: 4px;
  font-weight: 500;
}
</style>
