<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="计费月份" prop="propertyMonth">
        <el-input
          v-model="queryParams.propertyMonth"
          placeholder="请输入计费月份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="房屋建筑面积" prop="houseArea">
        <el-input
          v-model="queryParams.houseArea"
          placeholder="请输入房屋建筑面积"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物业费单价" prop="unitPrice">
        <el-input
          v-model="queryParams.unitPrice"
          placeholder="请输入物业费单价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物业费总额" prop="totalFee">
        <el-input
          v-model="queryParams.totalFee"
          placeholder="请输入物业费总额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="缴费截止日期" prop="dueDate">
        <el-date-picker clearable
                        v-model="queryParams.dueDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择缴费截止日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="缴费时间" prop="paymentTime">
        <el-date-picker clearable
                        v-model="queryParams.paymentTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择缴费时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="缴费订单号" prop="paymentNo">
        <el-input
          v-model="queryParams.paymentNo"
          placeholder="请输入缴费订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="逾期滞纳金" prop="overdueAmount">
        <el-input
          v-model="queryParams.overdueAmount"
          placeholder="请输入逾期滞纳金"
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
          v-hasPermi="['wuyefei:wu:add']"
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
          v-hasPermi="['wuyefei:wu:edit']"
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
          v-hasPermi="['wuyefei:wu:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wuyefei:wu:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wuList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物业费ID" align="center" prop="propertyFeeId" />
      <el-table-column label="业主ID" align="center" prop="userId" />
      <el-table-column label="业主姓名" align="center" prop="userName" />
      <el-table-column label="业主房号" align="center" prop="userRoom" />
      <el-table-column label="计费月份" align="center" prop="propertyMonth" />
      <el-table-column label="房屋建筑面积" align="center" prop="houseArea" />
      <el-table-column label="物业费单价" align="center" prop="unitPrice" />
      <el-table-column label="物业费总额" align="center" prop="totalFee" />
      <el-table-column label="缴费状态" align="center" prop="feeStatus" />
      <el-table-column label="缴费截止日期" align="center" prop="dueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="缴费时间" align="center" prop="paymentTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="缴费订单号" align="center" prop="paymentNo" />
      <el-table-column label="逾期滞纳金" align="center" prop="overdueAmount" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wuyefei:wu:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wuyefei:wu:remove']"
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

    <!-- 添加或修改物业费管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="业主ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入业主ID" />
        </el-form-item>
        <el-form-item label="业主姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入业主姓名" />
        </el-form-item>
        <el-form-item label="业主房号" prop="userRoom">
          <el-input v-model="form.userRoom" placeholder="请输入业主房号" />
        </el-form-item>
        <el-form-item label="计费月份" prop="propertyMonth">
          <el-input v-model="form.propertyMonth" placeholder="请输入计费月份" />
        </el-form-item>
        <el-form-item label="房屋建筑面积" prop="houseArea">
          <el-input v-model="form.houseArea" placeholder="请输入房屋建筑面积" />
        </el-form-item>
        <el-form-item label="物业费单价" prop="unitPrice">
          <el-input v-model="form.unitPrice" placeholder="请输入物业费单价" />
        </el-form-item>
        <el-form-item label="物业费总额" prop="totalFee">
          <el-input v-model="form.totalFee" placeholder="请输入物业费总额" />
        </el-form-item>
        <el-form-item label="缴费截止日期" prop="dueDate">
          <el-date-picker clearable
                          v-model="form.dueDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择缴费截止日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="缴费时间" prop="paymentTime">
          <el-date-picker clearable
                          v-model="form.paymentTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择缴费时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="缴费订单号" prop="paymentNo">
          <el-input v-model="form.paymentNo" placeholder="请输入缴费订单号" />
        </el-form-item>
        <el-form-item label="逾期滞纳金" prop="overdueAmount">
          <el-input v-model="form.overdueAmount" placeholder="请输入逾期滞纳金" />
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
import { listWu, getWu, delWu, addWu, updateWu } from "@/api/wuyefei/wu"

export default {
  name: "Wu",
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
      // 物业费管理表格数据
      wuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        userRoom: null,
        propertyMonth: null,
        houseArea: null,
        unitPrice: null,
        totalFee: null,
        feeStatus: null,
        dueDate: null,
        paymentTime: null,
        paymentNo: null,
        overdueAmount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
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
        propertyMonth: [
          { required: true, message: "计费月份不能为空", trigger: "blur" }
        ],
        houseArea: [
          { required: true, message: "房屋建筑面积不能为空", trigger: "blur" }
        ],
        unitPrice: [
          { required: true, message: "物业费单价不能为空", trigger: "blur" }
        ],
        totalFee: [
          { required: true, message: "物业费总额不能为空", trigger: "blur" }
        ],
        feeStatus: [
          { required: true, message: "缴费状态不能为空", trigger: "change" }
        ],
        dueDate: [
          { required: true, message: "缴费截止日期不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询物业费管理列表 */
    getList() {
      this.loading = true
      listWu(this.queryParams).then(response => {
        this.wuList = response.rows
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
        propertyFeeId: null,
        userId: null,
        userName: null,
        userRoom: null,
        propertyMonth: null,
        houseArea: null,
        unitPrice: null,
        totalFee: null,
        feeStatus: null,
        dueDate: null,
        paymentTime: null,
        paymentNo: null,
        overdueAmount: null,
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
      this.ids = selection.map(item => item.propertyFeeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加物业费管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const propertyFeeId = row.propertyFeeId || this.ids
      getWu(propertyFeeId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改物业费管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.propertyFeeId != null) {
            updateWu(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addWu(this.form).then(response => {
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
      const propertyFeeIds = row.propertyFeeId || this.ids
      this.$modal.confirm('是否确认删除物业费管理编号为"' + propertyFeeIds + '"的数据项？').then(function() {
        return delWu(propertyFeeIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wuyefei/wu/export', {
        ...this.queryParams
      }, `wu_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
