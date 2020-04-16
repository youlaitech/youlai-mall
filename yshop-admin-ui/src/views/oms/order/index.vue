<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item label="" prop="member_id">
        <el-input v-model="queryParams.member_id" placeholder="会员ID"></el-input>
      </el-form-item>
      <el-form-item label="" prop="member_id">
        <el-input v-model="queryParams.order_sn" placeholder="订单号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="queryParams.timeArray" type="datetimerange" class="filter-item" range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions"/>
      </el-form-item>
      <el-select v-model="queryParams.statusArray" :multiple="false" style="width: 200px" class="filter-item"
                 placeholder="请选择订单状态">
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value"/>
      </el-select>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" @click="handleResetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 搜索表单::end -->

    <!-- 数据表格::start -->
    <el-table
      id="dataTable"
      ref="multipleTable"
      :data="pageList"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
      border>
      <el-table-column
        type="selection"
        min-width="5%">
      </el-table-column>
      <el-table-column
        prop="order_sn"
        label="订单编号"
        min-width="10"
      />
      <el-table-column
        prop="member_id"
        label="会员ID"
        min-width="10"
      />

      <el-table-column
        prop="status"
        label="订单状态"
        min-width="10"
      >
        <template slot-scope="scope">
          <el-tag>{{scope.row.status|orderStatusFilter}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="total_amount"
        label="订单总额"
        min-width="10"
      />
      <el-table-column
        prop="pay_amount"
        label="支付金额"
        min-width="10"
      />

      <el-table-column
        prop="pay_time"
        label="支付时间"
        min-width="10"
      />

      <el-table-column
        prop="logistics_code"
        label="物流单号 "
        min-width="10"
      />

      <el-table-column
        prop="logistics_name"
        label="物流公司 "
        min-width="10"
      />


      <el-table-column
        label="操作"
        min-width="10">
        <template slot-scope="scope">

          <el-button
            size="mini"
            type="primary"
            @click="handleDetail(scope.row)">详情
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)">删除
          </el-button>

          <el-button
            size="mini"
            type="primary"
            @click="handleDeliver(scope.row)">发货
          </el-button>

          <el-button
            size="mini"
            type="primary"
            @click="handleRefund(scope.row)">退款
          </el-button>
        </template>
      </el-table-column>

    </el-table>
    <!-- 数据表格::end-->

    <!-- 分页工具条::start -->
    <pagination
      v-show="pagination.total>0"
      :total="pagination.total"
      :page.sync="pagination.page"
      :limit.sync="pagination.limit"
      @pagination="handleQuery"/>
    <!-- 分页工具条::end -->

    <!-- 表单弹窗::start -->
    <el-dialog
      :title="dialog.title"
      :visible.sync="dialog.visible"
      @close="cancel"
      center
      top="5vh"
      width="40%">
      <el-form id="dataForm"
               label-width="120px"
               :model="form"
               :rules="rules"
               ref="form">
        <el-form-item label="订单编号" prop="name">
          <el-input v-model="form.order_sn" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="收货人" prop="receiver_name">
          <el-input v-model="form.receiver_name" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="手机号码" prop="receiver_mobile">
          <el-input v-model="form.receiver_mobile" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮政编码" prop="receiver_zip">
          <el-input v-model="form.receiver_zip" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="收货地址" prop="receiver_address">
          <el-input v-model="form.receiver_address" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item>
            <el-select placeholder="请选择物流公司">


            </el-select>
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
  import {orderPageList, orderAdd, orderDetail, orderUpdate, orderDelete} from '@/api/oms/order'

  const statusMap = {
    0: '待付款',
    1: '待发货',
    2: '已发货',
    3: '已完成',
    4: '已关闭',
    5: '无效订单'
  }

  export default {
    filters: {
      orderStatusFilter(status) {
        return statusMap[status]
      }
    },
    data() {
      return {
        // 选中数组
        ids: [],
        // 限制一行选中按钮开启的控制【修改按钮】默认true不开启按钮
        single: true,
        // 限制一行或多行选中按钮就开启的控制 【删除按钮】 默认true不开启按钮
        multiple: true,
        pagination: {
          page: 1,
          limit: 10,
          total: 0
        },
        queryParams: {
          member_id: undefined,
          order_sn: undefined,
          statusArray: []
        },
        timeArray: [],
        pageList: [],
        dialog: {
          title: undefined,
          visible: false,
        },
        form: {
          status: 1
        },
        rules: {
          name: [{
            required: true, message: '请输入品牌名称', trigger: 'blur'
          }]
        },
        statusMap,
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }]
        },
      }
    },
    methods: {
      handleQuery() {
        if (this.timeArray && this.timeArray.length === 2) {
          this.queryParams.start_time = this.timeArray[0]
          this.queryParams.end_time = this.timeArray[1]
        } else {
          this.queryParams.start_time = undefined
          this.queryParams.end_time = undefined
        }
        orderPageList(this.pagination.page, this.pagination.limit, this.queryParams).then(response => {
          this.pageList = response.data.records;
          this.total = response.data.total
        });
      },
      handleResetQuery() {
        this.pagination = {
          page: 1,
          limit: 10,
          total: 0
        }
        this.queryParams = {
          name: undefined
        };
        this.handleResetForm()
        this.handleQuery()
      },
      handleRowClick(row) {
        this.$refs.multipleTable.toggleRowSelection(row);
      },
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id);
        this.single = selection.length != 1
        this.multiple = !selection.length
      },

      handleResetForm() {
        this.resetForm("form")
        this.form = {
          status: 1
        }
      },
      handleAdd() {
        this.handleResetForm()
        this.dialog = {
          title: "新增品牌",
          visible: true
        }
      },
      handleDetail(row) {
        this.handleResetForm()
        this.resetForm("form")
        this.dialog = {
          title: "修改品牌",
          visible: true
        }
        const id = row.id || this.ids
        orderDetail(id).then(response => {
          this.form = response.data
        })
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.id
            if (id != undefined) {
              orderUpdate(id, this.form).then(() => {
                this.$message.success("修改成功")
                this.dialog.visible = false
                this.handleQuery()
              })
            } else {
              orderAdd(this.form).then(() => {
                this.$message.success("新增成功")
                this.dialog.visible = false
                this.handleQuery()
              })
            }
          }
        })
      },
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
            orderDelete(ids).then(() => {
              this.$message.success("删除成功")
              this.handleQuery()
            })
          }
        ).catch(() =>
          this.$message.info("已取消删除")
        )
      },
      handleDeliver(row){

      },
      handleRefund(row){

      },
      cancel() {
        this.handleResetForm()
        this.dialog.visible = false;
      }
    },
    created() {
      this.handleQuery()
    }
  }
</script>

<style scoped>

</style>
