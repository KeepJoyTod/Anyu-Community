<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="访客姓名" prop="visitorName">
        <el-input
          v-model="queryParams.visitorName"
          placeholder="请输入访客姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="访客身份证号" prop="visitorIdCard">
        <el-input
          v-model="queryParams.visitorIdCard"
          placeholder="请输入访客身份证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="访客手机号" prop="visitorPhone">
        <el-input
          v-model="queryParams.visitorPhone"
          placeholder="请输入访客手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="被访业主ID" prop="visitUserId">
        <el-input
          v-model="queryParams.visitUserId"
          placeholder="请输入被访业主ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="被访业主姓名" prop="visitUserName">
        <el-input
          v-model="queryParams.visitUserName"
          placeholder="请输入被访业主姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="被访业主房号" prop="visitUserRoom">
        <el-input
          v-model="queryParams.visitUserRoom"
          placeholder="请输入被访业主房号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入住/进入时间" prop="checkInTime">
        <el-date-picker clearable
                        v-model="queryParams.checkInTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择入住/进入时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="离开时间" prop="checkOutTime">
        <el-date-picker clearable
                        v-model="queryParams.checkOutTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择离开时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="门岗操作员" prop="gateOperator">
        <el-input
          v-model="queryParams.gateOperator"
          placeholder="请输入门岗操作员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['visiter:visi:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['visiter:visi:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['visiter:visi:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['visiter:visi:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="visiList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="访客姓名" align="center" prop="visitorName" />
      <el-table-column label="访客身份证号" align="center" prop="visitorIdCard" />
      <el-table-column label="访客手机号" align="center" prop="visitorPhone" />
      <el-table-column label="被访业主ID" align="center" prop="visitUserId" />
      <el-table-column label="被访业主姓名" align="center" prop="visitUserName" />
      <el-table-column label="被访业主房号" align="center" prop="visitUserRoom" />
      <el-table-column label="访问类型" align="center" prop="visitType" />
      <el-table-column label="入住/进入时间" align="center" prop="checkInTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkInTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="离开时间" align="center" prop="checkOutTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkOutTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="访客状态" align="center" prop="visitorStatus" />
      <el-table-column label="门岗操作员" align="center" prop="gateOperator" />
      <el-table-column label="备注信息" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['visiter:visi:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['visiter:visi:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改小区访客管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="访客姓名" prop="visitorName">
          <el-input v-model="form.visitorName" placeholder="请输入访客姓名" />
        </el-form-item>
        <el-form-item label="访客身份证号" prop="visitorIdCard">
          <el-input v-model="form.visitorIdCard" placeholder="请输入访客身份证号" />
        </el-form-item>
        <el-form-item label="访客手机号" prop="visitorPhone">
          <el-input v-model="form.visitorPhone" placeholder="请输入访客手机号" />
        </el-form-item>
        <el-form-item label="被访业主ID" prop="visitUserId">
          <el-input v-model="form.visitUserId" placeholder="请输入被访业主ID" />
        </el-form-item>
        <el-form-item label="被访业主姓名" prop="visitUserName">
          <el-input v-model="form.visitUserName" placeholder="请输入被访业主姓名" />
        </el-form-item>
        <el-form-item label="被访业主房号" prop="visitUserRoom">
          <el-input v-model="form.visitUserRoom" placeholder="请输入被访业主房号" />
        </el-form-item>
        <el-form-item label="入住/进入时间" prop="checkInTime">
          <el-date-picker clearable
                          v-model="form.checkInTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择入住/进入时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离开时间" prop="checkOutTime">
          <el-date-picker clearable
                          v-model="form.checkOutTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择离开时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="门岗操作员" prop="gateOperator">
          <el-input v-model="form.gateOperator" placeholder="请输入门岗操作员" />
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listVisi, getVisi, delVisi, addVisi, updateVisi } from "@/api/visiter/visi"

export default {
  name: "Visi",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 小区访客管理表格数据
      visiList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        visitorName: null,
        visitorIdCard: null,
        visitorPhone: null,
        visitUserId: null,
        visitUserName: null,
        visitUserRoom: null,
        visitType: null,
        checkInTime: null,
        checkOutTime: null,
        visitorStatus: null,
        gateOperator: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        visitorName: [
          { required: true, message: "访客姓名不能为空", trigger: "blur" }
        ],
        visitorIdCard: [
          { required: true, message: "访客身份证号不能为空", trigger: "blur" }
        ],
        visitorPhone: [
          { required: true, message: "访客手机号不能为空", trigger: "blur" }
        ],
        visitUserId: [
          { required: true, message: "被访业主ID不能为空", trigger: "blur" }
        ],
        visitUserName: [
          { required: true, message: "被访业主姓名不能为空", trigger: "blur" }
        ],
        visitUserRoom: [
          { required: true, message: "被访业主房号不能为空", trigger: "blur" }
        ],
        visitType: [
          { required: true, message: "访问类型不能为空", trigger: "change" }
        ],
        checkInTime: [
          { required: true, message: "入住/进入时间不能为空", trigger: "blur" }
        ],
        visitorStatus: [
          { required: true, message: "访客状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询小区访客管理列表 */
    getList() {
      this.loading = true
      listVisi(this.queryParams).then(response => {
        this.visiList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        visitorName: null,
        visitorIdCard: null,
        visitorPhone: null,
        visitUserId: null,
        visitUserName: null,
        visitUserRoom: null,
        visitType: null,
        checkInTime: null,
        checkOutTime: null,
        visitorStatus: null,
        gateOperator: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加小区访客管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getVisi(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改小区访客管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateVisi(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addVisi(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除小区访客管理编号为"' + ids + '"的数据项？').then(function() {
        return delVisi(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('visiter/visi/export', {
        ...this.queryParams
      }, `visi_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
