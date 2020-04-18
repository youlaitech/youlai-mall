<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
        <el-button type="success" @click="openSpikePeriodDialog">秒杀时间段列表</el-button>
      </el-form-item>
      <el-form-item label="活动标题" prop="name">
        <el-input v-model="queryParams.title" placeholder="活动标题"></el-input>
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
        label="编号"
        min-width="5%">
      </el-table-column>
      <el-table-column
        prop="title"
        label="活动标题"
        min-width="20%">
      </el-table-column>
      <el-table-column
        prop="status"
        label="活动状态"
        min-width="11%">
        <template slot-scope="{row}">
          {{formatStatus(row)}}
        </template>
        >
      </el-table-column>
      <el-table-column
        prop="startDate"
        label="开始时间"
        min-width="15%">
      </el-table-column>
      <el-table-column
        prop="endDate"
        label="结束时间"
        min-width="15%">
      </el-table-column>
      <el-table-column
        prop="status"
        label="上线/下线"
        min-width="15%">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="15%">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="openSpikeGoodsDialog(scope.row)">设置商品
          </el-button>
          <el-button
            size="mini"
            @click="handleEdit(scope.row)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)">删除
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
        <el-form-item label="活动标题" prop="title">
          <el-input v-model="form.title" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker
            v-model="form.startDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker
            v-model="form.endDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上线/下线" prop="status">
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
  import {page, getObj, postObj, putObj, delObj, updateStatus} from '@/api/sms/seckill'

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
        queryParams: {
          title: undefined
        },
        tableData: [],
        dialog: {
          title: '',
          visible: false,
        },
        form: {
          title: undefined,
          startDate: undefined,
          endDate: undefined,
          status: 0
        },
        rules: {
          title: [{
            required: true, message: '请填写活动标题', trigger: 'blur'
          }],
          startDate: [{
            required: true, message: '请选择活动开始日期', trigger: 'blur'
          }],
          endDate: [{
            required: true, message: '请选择活动结束日期', trigger: 'blur'
          }]
        },
      }
    },
    created() {
      this.handleQuery()
    },
    methods: {
      handleQuery() {
        page(this.pagination.pageNum, this.pagination.pageSize, this.queryParams).then(response => {
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
          title: "新增活动",
          visible: true
        }

      },
      handleEdit(row) {
        this.reset();
        this.dialog = {
          title: "编辑活动",
          visible: true
        }
        const id = row.id || this.ids
        getObj(id).then(response => {
          if (response.data) {
            this.form = response.data
          }
        })
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
        let text = row.status === 0 ? '下线' : '上线'
        let that = this
        this.$confirm('确认要' + text + row.title + '秒杀活动?', "提示", {
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
          title: undefined,
          startDate: undefined,
          endDate: undefined,
          status: 0
        }
      },
      initPate() {
        this.handleQuery()
      },
      formatStatus(row) {
        const now = new Date().getTime();
        const startTime = new Date(row.startDate).getTime();
        const endTime = new Date(row.endDate).getTime() + 24 * 60 * 60 * 1000;
        if (now < startTime) {
          return '活动未开始'
        } else if (now >= startTime && now < endTime) {
          return '活动进行中'
        } else if (now >= endTime) {
          return '活动已结束'
        } else {
          return ''
        }
      },
      openSpikePeriodDialog() {
        this.$router.push({name: 'spikePeriod'})
      },
      openSpikeGoodsDialog(row) {
        this.$router.push({path: '/marketing/goods', query: {spikeId: row.id, spikeTitle: row.title}})
      }
    }
  }
</script>

<style scoped>

</style>
