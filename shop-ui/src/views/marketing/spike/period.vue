<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleEdit" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
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
        prop="name"
        label="秒杀时间段名称"
        min-width="20%">
      </el-table-column>
      <el-table-column
        prop="startTime"
        label="每日开始时间"
        min-width="15%">
      </el-table-column>
      <el-table-column
        prop="endTime"
        label="每日结束时间"
        min-width="15%">
      </el-table-column>
      <el-table-column
        prop="status"
        label="启用"
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
      <el-table-column label="操作" min-width="11%">
        <template slot-scope="scope">
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
      :name="dialog.name"
      :visible.sync="dialog.visible"
      @close="cancel"
      :append-to-body="true"
      top="5vh"
      width="40%">
      <el-form id="dataForm" label-width="140px" :model="form" :rules="rules" ref="form">
        <el-form-item label="秒杀时间段名称" prop="name">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="每日开始时间" prop="startTime">
          <el-time-select
            placeholder="开始时间"
            v-model="form.startTime"
            value-format="HH:mm"
            :picker-options="{ start: '08:00',step: '01:00', end: '22:00' }">
          </el-time-select>
        </el-form-item>
        <el-form-item label="每日结束时间" prop="endTime">
          <el-time-select
            placeholder="结束时间"
            v-model="form.endTime"
            value-format="HH:mm"
            :picker-options="{ start: '08:00',step: '01:00', end: '22:00',  minTime:form.startTime }">
          </el-time-select>
        </el-form-item>
        <el-form-item label="启用" prop="status">
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
  import {page, getObj, postObj, putObj, delObj, updateStatus} from '@/api/marketing/spike/period'

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
          name: undefined
        },
        tableData: [],
        dialog: {
          name: '',
          visible: false,
        },
        form: {
          name: undefined,
          startTime: undefined,
          endTime: undefined,
          status: 0
        },
        rules: {
          name: [{
            required: true, message: '请填写活动标题', trigger: 'blur'
          }],
          startTime: [{
            required: true, message: '请选择每天开始时间', trigger: 'blur'
          }],
          endTime: [{
            required: true, message: '请选择每天结束时间', trigger: 'blur'
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
          name: "新增时间段",
          visible: true
        }

      },
      handleEdit(row) {
        this.reset();
        this.dialog = {
          name: "编辑时间段",
          visible: true
        }
        const id = row.id || this.ids
        getObj(id).then(response => {
          if (response.data) {
            this.form = response.data
            this.form.startTime=response.data.startTime
            this.form.endTime=response.data.endTime
            console.log(this.form.startTime,this.form.endTime)
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
        let text = row.status === 0 ? '启用' : '关闭'
        let that = this
        this.$confirm('确认要' + text + row.name + '秒杀活动?', "提示", {
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
          name: undefined,
          startTime: undefined,
          endTime: undefined,
          status: 0
        }
      },
      initPate() {
        this.handleQuery()
      },

    }
  }
</script>

<style scoped>

</style>
