<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm">
      <el-form-item label="收货人名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="收货人名称"/>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="queryParams.mobile" placeholder="手机号"/>
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
      :data="tableData"
      border>
      <el-table-column
        type="selection"
        min-width="5%">
      </el-table-column>
      <el-table-column
        prop="id"
        label="编号"
        min-width="10">
      </el-table-column>
      <el-table-column
        prop="user_id"
        label="用户ID"
        min-width="10">
      </el-table-column>

      <el-table-column
        prop="name"
        label="收货人名称"
        min-width="10">
      </el-table-column>

      <el-table-column
        label="区域地址"
        min-width="10">
        <template slot-scope="scope">
          <el-tag >{{ scope.row.province+ scope.row.city+ scope.row.area}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center"
                       min-width="10"
                       label="详细地址"
                       prop="address_detail"/>
      <el-table-column align="center"
                       min-width="10"
                       label="邮编"
                       prop="zip_code"/>

      <el-table-column
        prop="status"
        label="是否默认"
        min-width="10">
        <template slot-scope="scope">
          <el-tag>  {{ scope.row.isDefault ? '是' : '否' }}</el-tag>
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
  </div>
</template>

<script>
  import {addressPageList, addressDelete} from '@/api/ums/address'

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
          page: 1,
          limit: 10,
          total: 0
        },
        queryParams: {
          username: undefined,
          mobile:undefined
        },
        tableData: []
      }
    },
    created() {
      this.handleQuery()
    },
    methods: {
      handleQuery() {
        addressPageList(this.pagination.page, this.pagination.limit, this.queryParams).then(response => {
          this.tableData = response.data.records
          this.pagination.total = response.data.total
        })
      },
      handleResetQuery() {
        this.pagination = {
          page: 1,
          limit: 10,
          total: 0
        }
        this.queryParams = {
          username: undefined,
          mobile:undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },

      handleDelete(row) {
        const ids = row.id || this.ids
        this.$confirm('是否确认删除选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          addressDelete(ids).then(() => {
            this.$message.success("删除成功")
            this.handleQuery()
          })
        }).catch(() =>
          this.$message.info("已取消删除")
        )
      },
      initPate() {
        this.handleQuery()
      }
    }
  }
</script>

<style scoped>

</style>
