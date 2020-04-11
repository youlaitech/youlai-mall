<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleEdit" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      </el-form-item>
      <el-form-item label="类型名称" prop="attributeTypeName">
        <el-input v-model="queryParams.attributeTypeName" placeholder="类型名称"></el-input>
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
        prop="attributeTypeId"
        label="类型编号"
        min-width="10%">
      </el-table-column>
      <el-table-column
        prop="attributeTypeName"
        label="类型名称"
        min-width="15%">
      </el-table-column>
      <el-table-column
        prop="attributeCount"
        label="属性数量"
        min-width="15%">
      </el-table-column>
      <el-table-column
        prop="paramCount"
        label="参数数量"
        min-width="15%">
      </el-table-column>

      <el-table-column label="设置" min-width="10%">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleAttributeSet(scope.row)">属性设置
          </el-button>
          <el-button
            size="mini"
            @click="handleParamSet(scope.row)">参数设置
          </el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作" min-width="10%">
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
      :title="dialog.title"
      :visible.sync="dialog.visible"
      @close="cancel"
      width="40%">
      <el-form id="dataForm" label-width="120px" :model="form" :rules="rules" ref="form">
        <el-form-item label="商品类型名称" prop="attributeTypeName">
          <el-input v-model="form.attributeTypeName" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 表单弹窗::end -->

    <!-- 属性列表弹窗::start -->
    <el-dialog
      :title="dialogAttribute.title"
      :visible.sync="dialogAttribute.visible"
      @opened="initAttributeIndex"
      width="70%">
      <attribute-index   ref="attributeIndex" :attributeType="attributeType" :attributeTypeId="attributeTypeId" @reloadAttributeType="handleQuery"></attribute-index>
    </el-dialog>
    <!-- 属性列表弹窗::end -->
  </div>
</template>

<script>
  import {page, getObj, postObj, putObj, delObj} from '@/api/pms/attribute/type'
  import AttributeIndex from "@/views/pms/attribute/index"

  export default {
    name: "index",
    components: {
      AttributeIndex
    },
    data() {
      return {
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        pagination:{
          pageNum: 1,
          pageSize: 10,
          total: 0
        },
        queryParams: {
          attributeTypeName: undefined
        },
        tableData: [],
        dialog:{
          title: '',
          visible: false,
        },
        dialogAttribute:{
          title:'',
          visible:false,
        },
        form: {
          attributeTypeId: undefined,
          attributeTypeName: undefined,
          attributeCount: 0,
          paramCount: 0,
        },
        rules: {
          attributeTypeName: [{
            required: true, message: '请输入类型名称', trigger: 'blur'
          }]
        },
        // 子页面传递参数
        attributeType: undefined,
        attributeTypeId: undefined
      }
    },
    methods: {
      handleQuery() {
        page(this.pagination.pageNum, this.pagination.pageSize, this.queryParams).then(response => {
          this.tableData = response.data.records
          this.pagination.total = response.data.total
        })
      },
      resetQuery() {
        this.pagination={
          pageNum : 1,
          pageSize: 10,
          total: 0
        }
        this.queryParams = {
          attributeTypeName: undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },
      handleAdd() {
        this.reset()
        this.dialog.title = "新增商品类型"
        this.dialog.visible = true
      },
      handleEdit(row) {
        this.reset();
        const attributeTypeId = row.attributeTypeId || this.ids
        getObj(attributeTypeId).then(response => {
          this.form = response.data
          this.dialog.visible = true
          this.dialog.title = "修改商品类型"
        })
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.attributeTypeId
            if (id != undefined) {
              putObj(id, this.form).then(() => {
                this.$message.success("修改成功")
                this.dialog.visible = false
                this.handleQuery()
              })
            } else {
              postObj(this.form).then(() => {
                this.$message.success("新增成功")
                this.dialog.visible = false
                this.handleQuery()
              })
            }
          }
        })
      },
      handleDelete(row) {
        const ids = row.attributeTypeId || this.ids
        this.$confirm('是否确认删除选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delObj(ids)
        }).then(() => {
          this.$message.success("删除成功")
          this.handleQuery()
        }).catch(() =>
          this.$message.info("已取消删除")
        )
      },
      rowClick(row) {
        this.$refs.multipleTable.toggleRowSelection(row);
      },
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.attributeTypeId)
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
          attributeTypeId: undefined,
          attributeTypeName: undefined,
          attributeCount: 0,
          paramCount: 0,
        }
      },
      handleAttributeSet(row) {
        this.dialogAttribute.title = "商品属性设置"
        this.dialogAttribute.visible = true
        this.attributeType = 0
        this.attributeTypeId =row.attributeTypeId
      },
      handleParamSet(row) {
        this.dialogAttribute.title = "商品参数设置"
        this.dialogAttribute.visible = true
        this.attributeType = 1
        this.attributeTypeId =row.attributeTypeId
      },
      initAttributeIndex(){
        // 调用子组件的数据加载方法
        this.$refs.attributeIndex.initPate()
      }
    },
    created() {
      this.handleQuery()
    }
  }
</script>

<style scoped>

</style>
