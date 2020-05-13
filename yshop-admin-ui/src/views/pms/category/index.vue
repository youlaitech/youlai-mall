<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item>
        <el-button type="primary" @click="handleAdd()">新增</el-button>
      </el-form-item>
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="分类名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" @click="handleResetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 搜索表单::end -->

    <!-- 数据表格::start -->
    <el-table
      id="dataTable"
      row-key="id"
      :data="list"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
        prop="name"
        label="分类名称"
        min-width="15%">
      </el-table-column>
      <el-table-column
        prop="icon_url"
        label="分类图标"
        min-width="11%">
        <template slot-scope="scope">
          <el-popover
            placement="right"
            title=""
            trigger="click">
            <img :src="scope.row.icon_url">
            <img slot="reference" :src="scope.row.icon_url"
                 style="max-height: 40px;max-width: 40px">
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="pic_url"
        label="分类图片"
        min-width="11%">
        <template slot-scope="scope">
          <el-popover
            placement="right"
            title=""
            trigger="click">
            <img :src="scope.row.pic_url">
            <img slot="reference" :src="scope.row.pic_url"
                 style="max-height: 40px;max-width: 40px">
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="is_show"
        label="是否显示"
        min-width="11%">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.is_show"
            :active-value="1"
            :inactive-value="0"
            @change="handleIsShowChange(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序"
        min-width="11%">
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="20%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            v-show="scope.row.level===1"
            @click="handleAdd(scope.row)"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 数据表格::end-->

    <!-- 表单弹窗::start -->
    <el-dialog
      :title="dialog.title"
      :visible.sync="dialog.visible"
      @close="cancel"
      top="5vh"
      width="50%">
      <el-form id="dataForm"
               label-width="150px"
               :model="form"
               :rules="rules"
               ref="form">
        <el-form-item label="上级分类" prop="parent_id">
          <el-select v-model="form.parent_id">
            <el-option :label="item.name" :value="item.id" v-for="item in categoryOptions"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入分类名称"/>
        </el-form-item>
        <el-form-item label="分类图标" prop="icon_url">
          <single-upload v-model="form.icon_url"></single-upload>
        </el-form-item>
        <el-form-item label="分类图片" prop="pic_url">
          <single-upload v-model="form.pic_url"></single-upload>
        </el-form-item>
        <el-form-item label="分类描述" prop="description">
          <el-input v-model="form.description"/>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort"/>
        </el-form-item>
        <el-form-item label="是否显示" prop="is_show">
          <el-radio-group v-model="form.is_show">
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
    <!-- 表单弹窗::end -->
  </div>
</template>

<script>
  import {
    categoryList,
    categoryAdd,
    categoryDetail,
    categoryUpdate,
    categoryDelete,
    categoryFirstLevelList,
    categoryIsShowUpdate
  } from '@/api/pms/category'
  import SingleUpload from '@/components/Upload/singleUpload'

  export default {
    components: {SingleUpload},
    data() {
      return {
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        queryParams: {
          name: undefined
        },
        list: [],
        categoryOptions: [],
        dialog: {
          title: '',
          visible: false
        },
        form: {
          is_show: 1
        },
        rules: {
          name: [{
            required: true, message: '请填写分类名称', trigger: 'blur'
          }]
        },
      }
    },
    methods: {
      handleQuery() {
        categoryList(this.queryParams).then(response => {
          this.list = response.data
        })
      },
      handleResetQuery() {
        this.queryParams = {
          name: undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },
      // 树形下拉选择器
      loadFirstLevelCategoryList() {
        categoryFirstLevelList().then(response => {
          this.categoryOptions = response.data
        });
      },

      handleAdd(row) {
        this.handleResetForm()
        this.dialog = {
          title: "新增分类",
          visible: true
        }
        this.loadFirstLevelCategoryList()
        if (row) {
          this.form.parent_id = row.id
          this.form.level = 2
        } else {
          this.form.parent_id = "0"
          this.form.level = 1
        }
      },
      handleEdit(row) {
        this.handleResetForm()
        this.loadFirstLevelCategoryList()
        const id = row.id || this.ids
        this.dialog = {
          title: "修改分类",
          visible: true
        }
        categoryDetail(id).then(response => {
          this.form = response.data
        })
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.id
            if (id != undefined) {
              categoryUpdate(id, this.form).then(() => {
                this.$message.success("修改成功")
                this.dialog.visible = false
                this.handleQuery()
              })
            } else {
              categoryAdd(this.form).then(() => {
                this.$message.success("新增成功")
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
          categoryDelete(ids).then(() => {
            this.$message.success("删除成功")
            this.handleQuery()
          })
        }).catch(() =>
          this.$message.info("已取消删除")
        )
      },
      handleRowClick(row) {
        this.$refs.multipleTable.toggleRowSelection(row);
      },
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      cancel() {
        this.dialog.visible = false
        this.handleResetForm()
      },
      handleResetForm() {
        this.resetForm("form")
        this.form = {
          is_show: 1
        }
      },
      handleIsShowChange(row) {
        categoryIsShowUpdate(row.id, row.is_show).then(() => {
          this.$message.success("更新分类可见状态成功")
        })
      }
    },

    created() {
      this.handleQuery()
    }
  }
</script>

<style scoped>

</style>
