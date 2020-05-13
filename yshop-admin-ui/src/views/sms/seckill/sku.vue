<template>
  <div class="app-container" >

    <el-card shadow="never">
      <el-form :inline="true">
        <el-col :span="20">
          <el-form-item>
            <el-input v-model="title" readonly style="width: 200px"></el-input>
            <el-select v-model="form.promotion_session_id"
                       placeholder="请选择活动时间段"
                       clearable
                       filterable
                       @change="handleSelectSessionChange">
              <el-option
                v-for="item in sessionOptions"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-edit" plain @click="showSelectSkuDialog">选择商品</el-button>
            <el-button icon="el-icon-refresh" @click="refresh">刷新</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="4" style="text-align: right">
          <el-form-item>
            <el-button type="success" icon="el-icon-check" plain @click="handleSubmit">保存</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-card>
    <!-- 搜索表单::end -->

    <el-card shadow="never" style="margin-top: 10px">
      <div slot="header" class="clearfix">
        <span>商品列表</span>
      </div>
      <el-form size="mini"
               id="skuForm"
               :model="form"
               ref="skuForm"
      >
        <el-table
          :data="form.sku_list"
          highlight-current-row
          border>
          <el-table-column label="编号" width="120">
            <template slot-scope="scope">
              {{scope.$index+1}}
            </template>
          </el-table-column>
          <el-table-column
            label="商品图片"
            min-width="10">
            <template slot-scope="scope">
              <img :src="scope.row.pic_url"/>
            </template>
          </el-table-column>
          <el-table-column label="商品编号" width="200">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].spu_code'">
                {{ scope.row.spu_code}}
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="商品名称" min-width="10">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].spu_name'">
                {{ scope.row.spu_name}}
              </el-form-item>
            </template>
          </el-table-column>

     <!--     <el-table-column label="商品价格" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].price'">
                {{ scope.row.price|moneyFormat}}
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column label="库存数量" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].stock'">
                {{ scope.row.stock}}
              </el-form-item>
            </template>
          </el-table-column>-->
          <el-table-column label="秒杀价格" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].seckill_price'" :rules="rules.seckill_price">
                <el-input-number v-model="scope.row.seckill_price" controls-position="right" :min="0"/>
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column label="秒杀数量" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].seckill_count'" :rules="rules.seckill_count">
                <el-input-number v-model="scope.row.seckill_count" controls-position="right" :min="0" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="限购数量" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].seckill_limit'" :rules="rules.seckill_limit">
                <el-input-number v-model="scope.row.seckill_limit" controls-position="right" :min="0" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="排序" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].sort'">
                <el-input-number v-model="scope.row.sort" controls-position="right"></el-input-number>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-form-item>
                <el-button type="danger" @click="handleRemoveSku(scope.$index)">删除</el-button>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </el-card>

    <el-dialog
      :title="skuDialog.title"
      :visible.sync="skuDialog.visible"
      @close="closeSkuDialog"
      :append-to-body="true"
      top="5vh"
      width="70%">
      <el-form :inline="true" :model="skuQueryForm" class="demo-form-inline">
        <el-col :span="20">
          <el-form-item>
            <el-input v-model="skuQueryForm.sku_name" placeholder="商品名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" plain icon="el-icon-search" @click="handleQuerySku">搜索</el-button>
          </el-form-item>
        </el-col>

        <el-col :span="4" style="text-align: right">
          <el-button type="success" plain icon="el-icon-check" plain @click="handleConfirmSelectedGoods">确认选择
          </el-button>
        </el-col>
      </el-form>

      <el-table
        :data="skuTableData"
        border
        ref="multipleTable"
        @selection-change="handleSkuSelectionChange"
        @row-click="handleRowClick"
      >
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          label="商品图片"
          min-width="10">
          <template slot-scope="scope">
            <img :src="scope.row.pic_url"/>
          </template>
        </el-table-column>
        <el-table-column
          prop="spu_code"
          label="商品编号"
          min-width="10">
        </el-table-column>
        <el-table-column
          prop="spu_name"
          label="商品名称"
          min-width="10">
        </el-table-column>

        <el-table-column
          prop="specs"
          label="规格"
          min-width="20">
        </el-table-column>
        <el-table-column
          prop="sku_price"
          label="价格"
          min-width="10">
          <template slot-scope="scope">
            {{scope.row.price|moneyFormat }}
          </template>
        </el-table-column>
        <el-table-column
          prop="stock"
          label="库存"
          min-width="10">
        </el-table-column>
      </el-table>
      <pagination
        v-show="pagination.total>0"
        :total="pagination.total"
        :page.sync="pagination.page"
        :limit.sync="pagination.limit"
        @pagination="handleQuerySku"
      />
    </el-dialog>

  </div>
</template>

<script>
  import {skuPageList,skuAdd,skuList} from '@/api/sms/seckill/sku'
  import {sessionList} from '@/api/sms/seckill/session'

  export default {
    data() {
      return {
        // 选中数组
        ids: [],
        pagination: {
          page: 1,
          limit: 10,
          total: 0
        },
        skuQueryForm: {
          sku_name: undefined
        },
        skuTableData: [],
        dialog: {
          title: '',
          visible: false,
        },
        skuDialog: {
          title: '',
          visible: false
        },
        rules: {
          seckil_price: [{
            required: true, message: '请填写秒杀价格', trigger: 'blur'
          }],
          seckill_count: [{
            required: true, message: '请填写秒杀数量', trigger: 'blur'
          }],
          seckill_limit: [{
            required: true, message: '请填写秒杀数量', trigger: 'blur'
          }]
        },
        sessionOptions: [], // 秒杀时间段下拉数据
        selectedSkuList: [], // 新增选中的商品
        form: {
          promotion_id: undefined,
          promotion_session_id: undefined,
          sku_list: []
        },
        title: undefined
      }
    },
    created() {
      this.form.promotion_id = this.$route.query.id
      this.title = this.$route.query.title
      // 秒杀活动时间段数据加载
      sessionList().then(response => {
        if (response.data && response.data.length > 0) {
          this.sessionOptions = response.data
          this.form.promotion_session_id = response.data[0].id
          this.handleQuery()
        }
      })
    },
    methods: {
      handleQuery() {
        this.form.sku_list = []
        if (this.form.promotion_id && this.form.promotion_session_id) {
          let param={
            promotion_id:this.form.promotion_id,
            promotion_session_id:this.form.promotion_session_id
          }
          skuList(param).then(response => {
            this.form.sku_list = response.data
          })
        }
      },
      refresh() {
        this.handleQuery()
      },
      // 活动时间段下拉change事件
      handleSelectSessionChange(val) {
        this.form.sku_list = []
        const promotion_session_id = val
        if (this.form.promotion_id && promotion_session_id) {
          let param={
            promotion_id:this.form.promotion_id,
            promotion_session_id:promotion_session_id
          }
          skuList(param).then(response => {
            this.form.sku_list = response.data
          })
        }
      },
      handleRowClick(row) {
        this.$refs.multipleTable.toggleRowSelection(row);
      },
      handleSubmit() {
        this.$refs["skuForm"].validate((valid) => {
          if (valid) {
            skuAdd(this.form).then(response => {
              this.$message.success(response.msg)
              this.handleQuery()
            })
          }
        })
      },
      showSelectSkuDialog() {
        if (!this.form.promotion_session_id) {
          this.$message.warning("请选先择秒杀活动时间段")
          return
        }
        this.skuDialog = {
          title: "选择商品",
          visible: true
        }
        this.handleQuerySku()
      },
      closeSkuDialog() {
        this.skuDialog = {
          title: '',
          visible: false
        }
      },
      handleQuerySku() {
        skuPageList(this.pagination.page, this.pagination.limit, this.skuQueryForm).then(response => {
          this.skuTableData = response.data.records
          this.pagination.total = response.data.total
        })
      },
      handleSkuSelectionChange(selection) {  // 商品选择
        this.selectedSkuList = selection
      },
      handleConfirmSelectedGoods() { //确认选择
        if (this.selectedSkuList.length <= 0) {
          this.$message.warning("请选择要添加的商品")
          return
        }
        // 重复的sku_id集合
        let repeatSkuIds = []
        let skuList = this.form.sku_list
        this.selectedSkuList.forEach(item => {
          let flag = false  //是否被过滤标识
          for (let i = 0; i < skuList.length; i++) {
            if (item.id === skuList[i].sku_id) { // 重复添加的商品
              repeatSkuIds.push(item.id)
              flag = true
            }
          }
          if (flag === false) {
            let selectedSku = {
              id:undefined,
              sku_id: item.id,
              price: item.price,
              stock: item.stock,
              seckill_price: 0,
              seckill_count: 0,
              seckill_limit: 0,
              sort: 0,
              spu_code:item.spu_code,
              spu_name:item.spu_name,
              pic_url:item.pic_url,
              specs: item.specs,
            }
            this.form.sku_list.push(selectedSku)
          }
        })
        this.closeSkuDialog()
        if (repeatSkuIds.length > 0) {
          this.$alert('已自动过滤重复商品：' + repeatSkuIds.join(','), '商品重复提示', {
            confirmButtonText: '确定',
            callback: () => {
            }
          })
        }
      },
      handleRemoveSku(index) {
        this.form.sku_list.splice(index, 1)
      }
    }
  }
</script>

<style scoped>

</style>
