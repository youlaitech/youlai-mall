<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="sessionForm">
      <el-form-item label="秒杀时间段">
        <el-input v-model="sessionForm.seckillTitle" readonly style="width: 200px"></el-input>
        <el-select v-model="sessionForm.seckillSessionId" placeholder="请选择活动时间段" clearable>
          <el-option
            v-for="item in seckillSessionOptions"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleOpenSelectGoods">添加商品</el-button>
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
        prop="goodsName"
        label="商品名称"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="goodsSn"
        label="货号"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="goodsStock"
        label="剩余数量"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="seckillPrice"
        label="秒杀价格"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="seckillCount"
        label="秒杀数量"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="seckillLimit"
        label="限购数量"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序"
        min-width="11%">
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


    <!-- 表单弹窗::start -->
    <el-dialog
      :title="dialog.title"
      :visible.sync="dialog.visible"
      @close="cancel"
      :append-to-body="true"
      top="5vh"
      width="40%">
      <el-form id="dataForm" label-width="140px" :model="form" :rules="rules" size="small" ref="form">
        <el-form-item label="商品名称" prop="goodsName">
          <el-input v-model="form.goodsName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="货号" prop="goodsSn">
          <el-input v-model="form.goodsSn" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="goodsPrice">
          <el-input v-model="form.goodsPrice" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="秒杀价格" prop="seckillPrice">
          <el-input v-model="form.seckillPrice" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="剩余数量" prop="goodsStock">
          <el-input v-model="form.goodsStock" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="秒杀数量" prop="seckillCount">
          <el-input v-model="form.seckillCount" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="限购数量" prop="seckillLimit">
          <el-input v-model="form.seckillLimit" auto-complete="off"></el-input>
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


    <el-dialog
      :title="goodsDialog.title"
      :visible.sync="goodsDialog.visible"
      @close="closeGoodsDialog"
      :append-to-body="true"
      top="5vh"
      width="40%">
      <el-form :inline="true" :model="goodsQueryForm" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="goodsQueryForm.goodsName" placeholder="商品名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQueryGoods">查询</el-button>
          <el-button type="success" icon="el-icon-check" @click="handleSelectGoods">确认选择</el-button>
        </el-form-item>
      </el-form>

      <el-table
        :data="goodsTableData"
        border
        ref="goodsMultiTable"
        @selection-change="handleGoodsSelectionChange"
      >
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="goodsName"
          label="商品名称"
          min-width="20%">
        </el-table-column>
        <el-table-column
          prop="goodsSn"
          label="货号"
          min-width="20%">
        </el-table-column>
        <el-table-column
          prop="goodsPrice"
          label="价格"
          min-width="20%">
        </el-table-column>
      </el-table>
      <pagination
        v-show="pagination.total>0"
        :total="pagination.total"
        :page.sync="pagination.pageNum"
        :limit.sync="pagination.pageSize"
        @pagination="handleQueryGoods"/>
    </el-dialog>

  </div>
</template>

<script>
  import {list, page, getObj, postObj, putObj, delObj, updateStatus} from '@/api/marketing/seckill/goods'
  import {list as sessionList} from '@/api/marketing/seckill/session'

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
        sessionForm: {
          seckillId: undefined,
          seckillTitle: undefined,
          seckillSessionId: undefined
        },
        goodsQueryForm: {
          goodsName: undefined
        },
        tableData: [],
        goodsTableData: [],
        dialog: {
          title: '',
          visible: false,
        },
        goodsDialog: {
          title: '',
          visible: false
        },
        form: {
          id: undefined,
          seckillId: undefined,
          seckillSessionId: undefined,
          goodsId: undefined,
          seckillPrice: undefined,
          seckillCount: undefined,
          seckillLimit: undefined,
          sort: undefined,
          goodsName: undefined,
          goodsPrice: undefined,
          goodsStock: undefined,
          goodsSn: undefined
        },
        rules: {
          attributeName: [{
            required: true, message: '请输入属性名称', trigger: 'blur'
          }],
          attributeTypeId: [{
            required: true, message: '请选择商品类型', trigger: 'blur'
          }]
        },
        seckillSessionOptions: [],
        goodsIds: []
      }
    },
    created() {
      this.sessionForm.seckillId = this.$route.query.seckillId
      this.sessionForm.seckillTitle = this.$route.query.seckillTitle
      sessionList().then(response => {
        this.seckillSessionOptions = response.data
        this.sessionForm.seckillSessionId = response.data[0].id
        this.handleQuery()
      })
    },
    methods: {
      handleQuery() {
        list(this.pagination.pageNum, this.pagination.pageSize, this.sessionForm).then(response => {
          this.tableData = response.data
        })
      },
      resetQuery() {
        this.pagination = {
          pageNum: 1,
          pageSize: 10,
          total: 0
        }
        this.sessionForm = {
          name: undefined
        }
        this.resetForm("sessionForm")
        this.handleQuery()
      },
      handleEdit(row) {
        this.reset();
        this.dialog = {
          title: "修改退货原因",
          visible: true
        }
        const id = row.id || this.ids
        getObj(id).then(response => {
          if (response.data) {
            this.form = response.data
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
        let text = row.status === 0 ? '开启' : '关闭'
        let that = this
        this.$confirm('确认要' + text + row.name + '退货原因?', "提示", {
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
          id: undefined,
          seckillId: undefined,
          seckillSessionId: undefined,
          goodsId: undefined,
          seckillPrice: undefined,
          seckillCount: undefined,
          seckillLimit: undefined,
          sort: undefined,
          goodsName: undefined,
          goodsPrice: undefined,
          goodsStock: undefined,
          goodsSn: undefined
        }
      },
      initPate() {
        this.handleQuery()
      },
      handleOpenSelectGoods() {
       if( !this.sessionForm.seckillSessionId){
         this.$message.warning("请选先择秒杀活动时间段")
         return
       }
        this.goodsDialog = {
          title: "选择商品",
          visible: true
        }
        this.handleQueryGoods()
      },
      closeGoodsDialog() {
        this.goodsDialog = {
          title: '',
          visible: false
        }
      },
      handleQueryGoods() {
        page(this.pagination.pageNum, this.pagination.pageSize, this.goodsQueryForm).then(response => {
          this.goodsTableData = response.data.records
          this.pagination.total = response.data.total
        })
      },
      handleSelectGoods() { //确认选择


      },
      handleGoodsSelectionChange(selection) {
        this.goodsIds = selection.map(item => item.id)
      }
    }
  }
</script>

<style scoped>

</style>
