<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleView" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      </el-form-item>
      <el-form-item label="处理状态" prop="status">

        <el-select v-model="queryParams.status" placeholder="全部">
          <el-option label="待处理" :value="0"></el-option>
          <el-option label="退货中" :value="1"></el-option>
          <el-option label="已完成" :value="2"></el-option>
          <el-option label="已拒绝" :value="3"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="申请时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="datePickerOptions">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 搜索表单::end -->

    <!-- 数据表格::start -->
    <el-table
      id="dataTable"
      ref="multipleTable"
      :data="tableData"
      @selection-change="handleSelectionChange"
      @row-click="rowClick"
      border>
      <el-table-column
        type="selection"
        min-width="5%">
      </el-table-column>
      <el-table-column
        prop="id"
        label="服务单号"
        min-width="5%">
      </el-table-column>
      <el-table-column
        prop="orderNo"
        label="订单编号"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="申请时间"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="memberUsername"
        label="会员账号"
        min-width="11%">
      </el-table-column>

      <el-table-column
        prop="returnAmount"
        label="退款金额"
        min-width="11%">
        <template slot-scope="{row}">￥{{row| formatReturnAmount}}</template>
      </el-table-column>

      <el-table-column
        prop="returnAmount"
        label="实际退款金额"
        min-width="11%">
        <template slot-scope="{row}">￥{{row.returnAmount}}</template>
      </el-table-column>

      <el-table-column
        prop="status"
        label="申请状态"
        min-width="11%">
        <template slot-scope="{row}">
          <el-tag :type="getStatusType(row.status)">
            {{getStatusLabel(row.status)}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="处理时间" width="180" align="center">
        <template slot-scope="{row}">{{row.handleTime | formatTime}}</template>
      </el-table-column>
      <el-table-column label="操作" min-width="15%">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleView(scope.row)">
            订单详情

          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 数据表格::end-->

    <!-- 分页工具条::start -->
    <pagination
      v-show="pagination.total>0"
      :total="pagination.total"
      :page.sync="pagination.pageNum"
      :limit.sync="pagination.pageSize"
      @pagination="handleQuery"/>
    <!-- 分页工具条::end -->

    <!-- 表单弹窗::start -->
    <el-dialog
      :title="dialog.title"
      :visible.sync="dialog.visible"
      @close="cancel"
      :append-to-body="true"
      top="5vh"
      width="40%">
      <el-form id="dataForm" label-width="140px" :model="form" :rules="rules" ref="form">
        <el-form-item label="退货类型" prop="name">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否可用" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {page, postObj, putObj, delObj, updateStatus} from '@/api/oms/return'
  import {formatDate} from '@/utils/date';
  export default {
    data() {
      return {
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        pagination: {
          pageNum: 1,
          pageSize: 10,
          total: 0
        },
        dateRange: [],
        datePickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          }
        },
        queryParams: {
          name: undefined
        },
        tableData: [],
        dialog: {
          title: '',
          visible: false,
        },
        form: {
          id: undefined,
          name: undefined,
          sort: 1,
          status: 1
        },
        rules: {
          attributeName: [{
            required: true, message: '请输入属性名称', trigger: 'blur'
          }],
          attributeTypeId: [{
            required: true, message: '请选择商品类型', trigger: 'blur'
          }]
        },

      }
    },
    created() {
      this.handleQuery()
    },
    filters: {
      formatReturnAmount(row) {
        return row.goodsRealPrice * row.goodsCount;
      },
      formatTime(time) {
        if(time==null||time===''){
          return 'N/A';
        }
        let date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      }
    },
    methods: {
      handleQuery() {
        page(this.pagination.pageNum, this.pagination.pageSize, this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.tableData = response.data.records
          this.pagination.total = response.data.total
        })
      },
      resetQuery() {
        this.pagination = {
          pageNum: 1,
          pageSize: 10,
          total: 0
        }
        this.queryParams = {
          name: undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },
      handleAdd() {
        this.reset()
        this.dialog = {
          title: "新增退货原因",
          visible: true
        }
      },
      handleView(row) {
        this.$router.push({path: '/order/returnApplyDetail', query: {id: row.id}})
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.id
            if (id != undefined) {
              putObj(id, this.form).then(response => {
                this.$message.success(response.msg)
                this.dialog.visible = false
                this.handleQuery()
              })
            } else {
              postObj(this.form).then(response => {
                this.$message.success(response.msg)
                this.dialog.visible = false
                this.handleQuery()
              })
            }
          }
        })
      },
      handleDelete(row) {
        const ids = row.id || this.ids
        this.$confirm('是否确认删除选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          delObj(ids).then(() => {
            this.$message.success("删除成功")
            this.handleQuery()
          })
        }).catch(() =>
          this.$message.info("已取消删除")
        )
      },
      // 显示隐藏
      handleStatusChange(row) {
        let text = row.status === 0 ? '开启' : '关闭'
        let that = this
        this.$confirm('确认要' + text + row.name + '退货原因?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          updateStatus(row.id, row.status).then(response => {
            that.$message.success(response.msg)
          })
        }).catch(function () {
          row.status = row.status === 0 ? 1 : 0;
        })
      },
      rowClick(row) {
        this.$refs.multipleTable.toggleRowSelection(row);
      },
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      cancel() {
        this.dialog.visible = false;
        this.reset();
      },
      reset() {
        this.resetForm("form")
        this.form = {
          id: undefined,
          name: undefined,
          sort: 1,
          status: 1
        }
      },
      initPate() {
        this.handleQuery()
      },
      getStatusType(status) {
        if (status === 0) {
          return 'info'
        } else if (status === 1) {
          return ''
        } else if (status === 2) {
          return 'success'
        } else if (status === 3) {
          return 'danger'
        }
      },
      getStatusLabel(status) {
        if (status === 0) {
          return '待处理'
        } else if (status === 1) {
          return '退货中'
        } else if (status === 2) {
          return '已完成'
        } else if (status === 3) {
          return '已拒绝'
        }
      }
    }
  }
</script>

<style scoped>

</style>
