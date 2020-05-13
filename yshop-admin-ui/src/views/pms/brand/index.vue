<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleEdit" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      </el-form-item>
      <el-form-item label="" prop="name">
        <el-input v-model="queryParams.name" placeholder="品牌名称"></el-input>
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
        prop="name"
        label="品牌名称"
        min-width="10"
      >
      </el-table-column>
      <el-table-column
        prop="first_letter"
        label="品牌首字母"
        min-width="10"
      >
      </el-table-column>
      <el-table-column
        prop="logo_url"
        label="品牌LOGO"
        min-width="10"
      >
        <template slot-scope="scope">
          <el-popover
            placement="right"
            title=""
            trigger="click">
            <img :src="scope.row.logo_url">
            <img slot="reference" :src="scope.row.logo_url" :alt="scope.row.logo_url"
                 style="max-height: 60px;max-width: 60px">
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="pic_url"
        label="专区大图"
        min-width="10"
      >
        <template slot-scope="scope">
          <el-popover
            placement="right"
            title=""
            trigger="click">
            <img :src="scope.row.pic_url">
            <img slot="reference" :src="scope.row.pic_url" :alt="scope.row.pic_url"
                 style="max-height: 60px;max-width: 60px">
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序"
        min-width="10"
      >
      </el-table-column>
      <el-table-column
        prop="status"
        label="是否显示"
        min-width="10"
      >
        <template slot-scope="scope">
          <el-switch
            @change="handleStatusChange(scope.row.id, scope.row)"
            :active-value="1"
            :inactive-value="0"
            v-model="scope.row.status">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        min-width="10">
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
      <el-form id="dataForm" label-width="120px" :model="form" :rules="rules" ref="form">
        <el-form-item label="品牌名称" prop="name">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="品牌首字母" prop="first_letter">
          <el-input v-model="form.first_letter" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="品牌LOGO" prop="logo_url" label-width="120px">
          <single-upload v-model="form.logo_url"></single-upload>
        </el-form-item>
        <el-form-item label="专区大图" prop="pic_url" label-width="120px">
          <single-upload v-model="form.pic_url"></single-upload>
        </el-form-item>
        <el-form-item label="品牌说明" prop="description">
          <el-input v-model="form.description" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否显示" prop="status">
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
  import {brandPageList, brandAdd, brandDetail, brandUpdate, brandDelete,brandStatusUpdate} from '@/api/pms/brand'
  import SingleUpload from '@/components/Upload/singleUpload'

  export default {
    components: {SingleUpload},
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
          name: undefined
        },
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
      }
    },
    methods: {
      handleQuery() {
        brandPageList(this.pagination.page, this.pagination.limit, this.queryParams).then(response => {
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
      handleEdit(row) {
        this.handleResetForm()
        this.resetForm("form")
        this.dialog = {
          title: "修改品牌",
          visible: true
        }
        const id = row.id || this.ids
        brandDetail(id).then(response => {
          this.form = response.data
        })
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.id
            if (id != undefined) {
              brandUpdate(id, this.form).then(() => {
                this.$message.success("修改成功")
                this.dialog.visible = false
                this.handleQuery()
              })
            } else {
              brandAdd(this.form).then(() => {
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
            brandDelete(ids).then(() => {
              this.$message.success("删除成功")
              this.handleQuery()
            })
          }
        ).catch(() =>
          this.$message.info("已取消删除")
        )
      },
      cancel() {
        this.handleResetForm()
        this.dialog.visible = false;
      },

      handleStatusChange(id, row) {
        brandStatusUpdate(id, row.status).then(() => {
          this.$message.success("修改成功")
        });
      }
    },
    created() {
      this.handleQuery()
    }
  }
</script>

<style scoped>

</style>
