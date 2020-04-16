<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleEdit" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      </el-form-item>
      <el-form-item label="" prop="member_id">
        <el-input v-model="queryParams.member_id" placeholder="会员ID"></el-input>
      </el-form-item>
      <el-form-item label="" prop="member_id">
        <el-input v-model="queryParams.order_sn" placeholder="订单号"></el-input>
      </el-form-item>
      <el-select v-model="queryParams.status" :multiple="false" style="width: 200px" class="filter-item" placeholder="请选择订单状态">
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
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
  import {orderPageList, orderAdd, orderDetail, orderUpdate, orderDelete} from '@/api/oms/order'
  const statusMap = {
    101: '未付款',
    102: '用户取消',
    103: '系统取消',
    201: '已付款',
    202: '申请退款',
    203: '已退款',
    301: '已发货',
    401: '用户收货',
    402: '系统收货'
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
        statusMap
      }
    },
    methods: {
      handleQuery() {
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
      handleEdit(row) {
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
