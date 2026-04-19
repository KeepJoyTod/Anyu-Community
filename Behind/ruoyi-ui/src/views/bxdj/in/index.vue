<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联维修ID" prop="repairId">
        <el-input
          v-model="queryParams.repairId"
          placeholder="请输入关联维修ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修人ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入报修人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修人姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入报修人姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修人房号" prop="userRoom">
        <el-input
          v-model="queryParams.userRoom"
          placeholder="请输入报修人房号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修人电话" prop="userPhone">
        <el-input
          v-model="queryParams.userPhone"
          placeholder="请输入报修人电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="维修地址" prop="repairAddress">
        <el-input
          v-model="queryParams.repairAddress"
          placeholder="请输入维修地址"
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
          v-hasPermi="['baoxiudanju:baoxiudanju:add']"
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
          v-hasPermi="['baoxiudanju:baoxiudanju:edit']"
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
          v-hasPermi="['baoxiudanju:baoxiudanju:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['baoxiudanju:baoxiudanju:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="baoxiudanjuList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="单据ID" align="center" prop="orderId" />
      <el-table-column label="关联维修ID" align="center" prop="repairId" />
      <el-table-column label="报修人ID" align="center" prop="userId" />
      <el-table-column label="报修人姓名" align="center" prop="userName" />
      <el-table-column label="报修人房号" align="center" prop="userRoom" />
      <el-table-column label="报修人电话" align="center" prop="userPhone" />
      <el-table-column label="报修类型" align="center" prop="repairType" />
      <el-table-column label="维修地址" align="center" prop="repairAddress" />
      <el-table-column label="故障描述" align="center" prop="repairDesc" />
      <el-table-column label="故障图片" align="center" prop="repairImages" />
      <el-table-column label="单据状态" align="center" prop="orderStatus" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['baoxiudanju:baoxiudanju:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['baoxiudanju:baoxiudanju:remove']"
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

    <!-- 添加或修改报修单据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联维修ID" prop="repairId">
          <el-input v-model="form.repairId" placeholder="请输入关联维修ID" />
        </el-form-item>
        <el-form-item label="报修人ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入报修人ID" />
        </el-form-item>
        <el-form-item label="报修人姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入报修人姓名" />
        </el-form-item>
        <el-form-item label="报修人房号" prop="userRoom">
          <el-input v-model="form.userRoom" placeholder="请输入报修人房号" />
        </el-form-item>
        <el-form-item label="报修人电话" prop="userPhone">
          <el-input v-model="form.userPhone" placeholder="请输入报修人电话" />
        </el-form-item>
        <el-form-item label="维修地址" prop="repairAddress">
          <el-input v-model="form.repairAddress" placeholder="请输入维修地址" />
        </el-form-item>
        <el-form-item label="故障描述" prop="repairDesc">
          <el-input v-model="form.repairDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="故障图片" prop="repairImages">
          <el-input v-model="form.repairImages" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
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
import { listBaoxiudanju, getBaoxiudanju, delBaoxiudanju, addBaoxiudanju, updateBaoxiudanju } from "@/api/baoxiudanju/baoxiudanju"

export default {
  name: "Baoxiudanju",
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
      // 报修单据表格数据
      baoxiudanjuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        repairId: null,
        userId: null,
        userName: null,
        userRoom: null,
        userPhone: null,
        repairType: null,
        repairAddress: null,
        repairDesc: null,
        repairImages: null,
        orderStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        repairId: [
          { required: true, message: "关联维修ID不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "报修人ID不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "报修人姓名不能为空", trigger: "blur" }
        ],
        userRoom: [
          { required: true, message: "报修人房号不能为空", trigger: "blur" }
        ],
        userPhone: [
          { required: true, message: "报修人电话不能为空", trigger: "blur" }
        ],
        repairType: [
          { required: true, message: "报修类型不能为空", trigger: "change" }
        ],
        repairAddress: [
          { required: true, message: "维修地址不能为空", trigger: "blur" }
        ],
        repairDesc: [
          { required: true, message: "故障描述不能为空", trigger: "blur" }
        ],
        orderStatus: [
          { required: true, message: "单据状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询报修单据列表 */
    getList() {
      this.loading = true
      listBaoxiudanju(this.queryParams).then(response => {
        this.baoxiudanjuList = response.rows
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
        orderId: null,
        repairId: null,
        userId: null,
        userName: null,
        userRoom: null,
        userPhone: null,
        repairType: null,
        repairAddress: null,
        repairDesc: null,
        repairImages: null,
        orderStatus: null,
        createTime: null,
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加报修单据"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const orderId = row.orderId || this.ids
      getBaoxiudanju(orderId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改报修单据"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateBaoxiudanju(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBaoxiudanju(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids
      this.$modal.confirm('是否确认删除报修单据编号为"' + orderIds + '"的数据项？').then(function() {
        return delBaoxiudanju(orderIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('baoxiudanju/baoxiudanju/export', {
        ...this.queryParams
      }, `baoxiudanju_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
