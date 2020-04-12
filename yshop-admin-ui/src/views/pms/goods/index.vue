<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleEdit" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      </el-form-item>

      <el-form-item label="商品名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="商品名称"></el-input>
      </el-form-item>

      <el-form-item label="商品编号" prop="goods_sn">
        <el-input v-model="queryParams.goods_sn" placeholder="商品编号"></el-input>
      </el-form-item>

      <el-form-item label="是否新品" prop="is_new">
        <el-select v-model="queryParams.is_new">
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
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
        prop="id"
        label="商品ID"
        min-width="50">
      </el-table-column>

      <el-table-column min-width="100" label="名称" prop="name"/>

      <el-table-column property="pic_url" label="图片">
        <template slot-scope="scope">
          <img :src="scope.row.pic_url" width="40">
        </template>
      </el-table-column>

      <el-table-column label="详情" prop="detail">
        <template slot-scope="scope">
          <el-dialog :visible.sync="detailDialogVisible" title="商品详情">
            <div class="goods-detail-box" v-html="goodsDetail"/>
          </el-dialog>
          <el-button type="primary" size="mini" @click="showDetail(scope.row.detail)">查看</el-button>
        </template>
      </el-table-column>

      <el-table-column label="市场售价" prop="counterPrice"/>

      <el-table-column label="当前价格" prop="retailPrice"/>

      <el-table-column label="是否新品" prop="is_new">
        <template slot-scope="scope">
          <el-tag :type="scope.row.is_new ? 'success' : 'error' ">{{ scope.row.is_new ? '新品' : '非新品' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="是否热品" prop="is_hot">
        <template slot-scope="scope">
          <el-tag :type="scope.row.is_hot ? 'success' : 'error' ">{{ scope.row.is_hot ? '热品' : '非热品' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="是否在售" prop="is_sale">
        <template slot-scope="scope">
          <el-tag :type="scope.row.is_sale ? 'success' : 'error' ">{{ scope.row.is_sale ? '在售' : '未售' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" min-width="100">
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
  </div>
</template>

<script>
  import {goodsPageList, goodsDelete} from '@/api/pms/goods'

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
          name: undefined,
          goods_sn: undefined,
          is_new: undefined
        },
        pageList: [],
        goodsDetail: undefined,
        detailDialogVisible: false,
      }
    },
    methods: {
      handleQuery() {
        goodsPageList(this.pagination.page, this.pagination.limit, this.queryParams).then(response => {
          this.pageList = response.data.records
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
          name: undefined,
          goods_sn: undefined,
          is_new: undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },
      showDetail(detail) {
        this.goodsDetail = detail
        this.detailDialogVisible = true
      },
      handleAdd() {
        this.$router.push({name: 'goodsAdd'})
      },
      handleEdit(row) {
        this.$router.push({name: 'goodsEdit', params: {id: row.id}})
      },
      handleDelete(row) {
        const ids = row.id || this.ids
        this.$confirm('是否确认删除选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return goodsDelete(ids)
        }).then(() => {
          this.$message.success("删除成功")
          this.handleQuery()
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
    },
    created() {
      this.handleQuery()
    }
  }
</script>

<style scoped>

</style>
