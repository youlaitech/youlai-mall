<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="queryParams.username" placeholder="用户名"/>
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
        prop="username"
        label="用户名"
        min-width="10">
      </el-table-column>
      <el-table-column
        prop="mobile"
        label="手机号码"
        min-width="10">
      </el-table-column>
      <el-table-column
        prop="gender"
        label="性别"
        min-width="10">
        <template slot-scope="scope">
          <el-tag >{{ genderDic[scope.row.gender] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="end_time"
        label="用户等级"
        min-width="10">
        <template slot-scope="scope">
          <el-tag >{{ levelDic[scope.row.level] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        min-width="10">
        <template slot-scope="scope">
          <el-tag>{{ statusDic[scope.row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="15%">
        <template slot-scope="scope">
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
  </div>
</template>

<script>
  import {userPageList, userDelete} from '@/api/ums/user'

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
        tableData: [],
        genderDic: ['未知', '男', '女'],
        levelDic: ['普通用户', 'VIP用户', '高级VIP用户'],
        statusDic: ['可用', '禁用', '注销']
      }
    },
    created() {
      this.handleQuery()
    },
    methods: {
      handleQuery() {
        userPageList(this.pagination.page, this.pagination.limit, this.queryParams).then(response => {
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
          userDelete(ids).then(() => {
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
