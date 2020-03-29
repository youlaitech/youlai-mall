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
        prop="attributeId"
        label="编号"
        min-width="5%">
      </el-table-column>
      <el-table-column
        prop="attributeName"
        label="属性名称"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="attributeTypeName"
        label="商品类型"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="selectType"
        label="属性选择类型"
        min-width="11%"
        :formatter="formatSelectType"
      >
      </el-table-column>
      <el-table-column
        prop="inputType"
        label="属性录入方式"
        min-width="11%"
        :formatter="formatInputType"
      >
      </el-table-column>
      <el-table-column
        prop="inputList"
        label="可选值列表"
        min-width="15%">
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序"
        min-width="5%">
      </el-table-column>

      <el-table-column label="操作" min-width="15%">
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
      :append-to-body="true"
      top="5vh"
      width="40%">
      <el-form id="dataForm" label-width="140px" :model="form" :rules="rules" ref="form">
        <el-form-item label="属性名称" prop="attributeName">
          <el-input v-model="form.attributeName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品类型" prop="attributeTypeId">
          <el-select v-model="form.attributeTypeId">
            <el-option
              v-for="item in attributeTypeList"
              :key="item.attributeTypeId"
              :label="item.attributeTypeName"
              :value="item.attributeTypeId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分类筛选样式" prop="filterType">
          <el-radio-group v-model="form.filterType">
            <el-radio :label="0">普通</el-radio>
            <el-radio :label="1">颜色</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="检索类型" prop="searchType">
          <el-radio-group v-model="form.searchType">
            <el-radio :label="0">不需要检索</el-radio>
            <el-radio :label="1">关键字检索</el-radio>
            <el-radio :label="2">范围检索</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="商品属性关联" prop="isRelated">
          <el-radio-group v-model="form.isRelated">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="属性选择类型" prop="selectType">
          <el-radio-group v-model="form.selectType">
            <el-radio :label="0">唯一</el-radio>
            <el-radio :label="1">单选</el-radio>
            <el-radio :label="2">多选</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="属性值录入方式" prop="inputType">
          <el-radio-group v-model="form.inputType">
            <el-radio :label="0">手工录入</el-radio>
            <el-radio :label="1">从列表中选取</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="属性可选值列表" prop="inputList">
          <el-input type="textarea" v-model="form.inputList" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="是否支持手动新增" prop="isHandAdd">
          <el-radio-group v-model="form.isHandAdd">
            <el-radio :label="1">支持</el-radio>
            <el-radio :label="0">不支持</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" auto-complete="off"></el-input>
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
  import {page, getObj, postObj, putObj, delObj} from '@/api/pms/attribute'
  import {list as getAttributeTypeList} from '@/api/pms/attribute/type'

  export default {
    // 父页面传递的参数
    props: ['attributeType', "attributeTypeId"],
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
          type: this.attributeType  // 0--属性 1--参数
        },
        tableData: [],
        dialog: {
          title: '',
          visible: false,
        },
        form: {
          attributeId: undefined,
          attributeName: undefined,
          attributeTypeId: undefined,
          type: 0,
          selectType: 0,
          inputType: 0,
          inputList: undefined,
          sort: 0,
          filterType: 0,
          searchType: 0,
          isRelated: 1,
          isHandAdd: 1
        },
        rules: {
          attributeName: [{
            required: true, message: '请输入属性名称', trigger: 'blur'
          }],
          attributeTypeId: [{
            required: true, message: '请选择商品类型', trigger: 'blur'
          }]
        },
        attributeTypeList: undefined
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
        this.pagination = {
          pageNum: 1,
          pageSize: 10,
          total: 0
        }
        this.queryParams = {
          type: undefined,
          attributeTypeId:undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },
      formatSelectType: function (row) {
        if (row.selectType == 0) {
          return "唯一"
        } else if (row.selectType == 1) {
          return "单选"
        } else if (row.selectType == 2) {
          return "多选"
        }else {
          return ""
        }
      },
      formatInputType: function (row) {
        if (row.inputType == 0) {
          return "手工录入"
        } else if(row.inputType == 1) {
          return "从列表中获取"
        }else{
          return ""
        }
      },
      handleAdd() {
        this.reset()
        getAttributeTypeList().then(response => {
          this.attributeTypeList = response.data
          this.form.attributeTypeId = this.attributeTypeId
        })
        const attributeTypeName = this.attributeType == 0 ? '属性' : '参数'
        this.dialog = {
          title: "新增商品" + attributeTypeName,
          visible: true
        }

      },
      handleEdit(row) {
        this.reset();
        const attributeTypeName = this.attributeType == 0 ? '属性' : '参数'
        this.dialog = {
          title: "修改商品" + attributeTypeName,
          visible: true
        }
        getAttributeTypeList().then(response => {
          this.attributeTypeList = response.data
          const attributeId = row.attributeId || this.ids
          getObj(attributeId).then(res => {
            this.form = res.data
          })
        })
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.attributeId
            this.form.type = this.attributeType
            if (id != undefined) {
              putObj(id, this.form).then(() => {
                this.$message.success("修改成功")
                this.$emit('reloadAttributeType') //重新刷新父页面
                this.dialog.visible = false
                this.handleQuery()
              })
            } else {
              postObj(this.form).then(() => {
                this.$message.success("新增成功")
                this.$emit('reloadAttributeType')
                this.dialog.visible = false
                this.handleQuery()
              })
            }
          }
        })
      },
      handleDelete(row) {
        const ids = row.attributeId || this.ids
        this.$confirm('是否确认删除选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delObj(ids)
        }).then(() => {
          this.$emit('reloadAttributeType')
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
        this.ids = selection.map(item => item.attributeId)
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
          attributeId: undefined,
          attributeName: undefined,
          attributeTypeId: undefined,
          type: 0,
          selectType: 0,
          inputType: 0,
          inputList: undefined,
          sort: 0,
          filterType: 0,
          searchType: 0,
          isRelated: 1,
          isHandAdd: 1
        }
      },
      initPate() {
        this.queryParams={
          type : this.attributeType,
          attributeTypeId: this.attributeTypeId
        }
        this.handleQuery()
      }
    },
    created() {
      // this.handleQuery()
    }
  }
</script>

<style scoped>

</style>
