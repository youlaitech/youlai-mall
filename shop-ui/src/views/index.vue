<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleUpdate" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-input v-model="queryParams.customerName" placeholder="客户名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 搜索表单::end -->

    <!-- 数据表格::start -->
    <el-table
      v-loading="loading"
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
        prop="customerNo"
        label="客户编号"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="customerName"
        label="客户名称"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="address"
        label="客户地址"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="linkman"
        label="联系人"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="tel"
        label="联系电话"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="remark"
        label="备注"
        min-width="17%">
      </el-table-column>
    </el-table>
    <!-- 数据表格::end-->

    <!-- 分页工具条::start -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="handleQuery"/>
    <!-- 分页工具条::end -->

    <!-- 表单弹窗::start -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      @close="cancel"
      center
      width="40%">
      <el-form label-width="120px" :model="form" :rules="rules" ref="form"  >
        <el-form-item label="客户编号" prop="customerNo">
          <el-input v-model="form.customerNo" auto-complete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="linkman">
          <el-input v-model="form.linkman" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="tel">
          <el-input v-model="form.tel" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="客户地址" prop="address">
          <el-input v-model="form.address" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="form.remark" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 表单弹窗::end -->

  </div>
</template>

<script>
  import {page, getObj, postObj, putObj, delObj} from '@/api/system/user'

  export default {
    name: "index",
    data() {
      return {
        PREFIX_KEY: this.global.PREFIX_KEY,
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          customerName: undefined
        },
        tableData: [],
        total: 0,
        title: "",
        open: false,
        form: {
          id: undefined,
          customerNo: undefined,
          customerName: undefined,
          address: undefined,
          linkman: undefined,
          tel: undefined,
          email: undefined,
          remark: undefined
        },
        rules: {
          customerNo: [{
            required: true, message: '客户编号不能为空', trigger: 'blur'
          }],
          customerName: [{
            required: true, message: '请输入客户名称', trigger: 'blur'
          }],
          linkman: [{
            required: true, message: '请填写联系人', trigger: 'blur'
          }],
          tel: [{
            required: true, message: '请填写联系电话', trigger: 'blur'
          }]
        }
      }
    },
    methods: {
      handleQuery() {
        this.loading = true;
        page(this.queryParams).then(response => {
          console.log(response.data)
          this.tableData = response.data.records
          this.total = response.data.total
          this.loading = false
        })
      },
      resetQuery() {
        this.queryParams = {
          pageNum: 1,
          pageSize: 10,
          storeAreaNo: undefined,
          storeAreaName: undefined,
          storeHouseId: undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },
      handleAdd() {
        this.reset()
        this.title = "新增客户"
        this.open = true
      },
      handleUpdate(row) {
        this.reset();
        const customerId = row.id || this.ids
        getObj(customerId).then(response => {
          this.form = response.data
          this.open = true
          this.title = "修改客户"
        })
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.id
            if (id != undefined) {
              putObj(id, this.form).then(() => {
                this.msgSuccess("修改成功")
                this.open = false
                this.handleQuery()
              })
            } else {
              postObj(this.form).then(() => {
                this.msgSuccess("新增成功")
                this.open = false
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
        }).then(function () {
          return delObj(ids)
        }).then(() => {
          this.msgSuccess("删除成功")
          this.handleQuery()
        }).catch(() =>
          this.msgInfo("已取消删除")
        )
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
        this.open = false;
        this.reset();
      },
      reset() {
        this.resetForm("form")
        this.form = {
          id: undefined,
          customerNo: undefined,
          customerName: undefined,
          address: undefined,
          linkman: undefined,
          tel: undefined,
          email: undefined,
          remark: undefined
        }
      }
    },
    created() {
      this.handleQuery();
    }
  }
</script>

<style scoped>

</style>
