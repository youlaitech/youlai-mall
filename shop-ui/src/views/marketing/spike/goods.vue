<template>
  <div class="app-container" id="spikePeriodGoods">

    <!-- 搜索表单::begin -->
    <el-card shadow="never">
      <el-form :inline="true">
        <el-col :span="20">
          <el-form-item>
            <el-input v-model="spikeTitle" readonly style="width: 200px"></el-input>
            <el-select v-model="form.spikePeriodId"
                       placeholder="请选择活动时间段"
                       clearable
                       filterable
                       @change="handleSelectSpikePeriodChange">
              <el-option
                v-for="item in spikePeriodOptions"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-edit" plain @click="handleOpenSelectGoods">选择商品</el-button>
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
               id="spikePeriodGoodsForm"
               :model="form"
               ref="spikePeriodGoodsForm"
      >
        <el-table
          :data="form.spikePeriodGoodsList"
          highlight-current-row
          border>
          <el-table-column label="编号" width="120">
            <template slot-scope="scope">
              {{scope.$index+1}}
            </template>
          </el-table-column>
          <el-table-column label="商品名称">
            <template slot-scope="scope">
              <el-form-item :prop="'spikePeriodGoodsList[' + scope.$index + '].goodsName'">
                {{ scope.row.goodsName}}
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="货号" width="200">
            <template slot-scope="scope">
              <el-form-item :prop="'spikePeriodGoodsList[' + scope.$index + '].goodsSn'">
                {{ scope.row.goodsSn}}
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column label="商品价格" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'spikePeriodGoodsList[' + scope.$index + '].goodsPrice'">
                {{ scope.row.goodsPrice|moneyFormat}}
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column label="剩余数量" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'spikePeriodGoodsList[' + scope.$index + '].goodsStock'">
                {{ scope.row.goodsStock}}
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="秒杀价格" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'spikePeriodGoodsList[' + scope.$index + '].spikePrice'" :rules="rules.spikePrice">
                <el-input-number v-model="scope.row.spikePrice" controls-position="right" :min="0"
                                 :max="scope.row.goodsPrice"></el-input-number>
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column label="秒杀数量" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'spikePeriodGoodsList[' + scope.$index + '].spikeCount'" :rules="rules.spikeCount">
                <el-input-number v-model="scope.row.spikeCount" controls-position="right" :min="0"
                                 :max="scope.row.goodsStock"></el-input-number>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="限购数量" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'spikePeriodGoodsList[' + scope.$index + '].spikeLimit'" :rules="rules.spikeLimit">
                <el-input-number v-model="scope.row.spikeLimit" controls-position="right" :min="0"
                                 :max="scope.row.goodsStock"></el-input-number>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="排序" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'spikePeriodGoodsList[' + scope.$index + '].sort'">
                <el-input-number v-model="scope.row.sort" controls-position="right"></el-input-number>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-form-item>
                <el-button type="danger" @click="handleRemoveSpikeGoods(scope.$index)">删除</el-button>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </el-card>

    <el-dialog
      :title="goodsDialog.title"
      :visible.sync="goodsDialog.visible"
      @close="closeGoodsDialog"
      :append-to-body="true"
      top="5vh"
      width="40%">
      <el-form :inline="true" :model="goodsQueryForm" class="demo-form-inline">
        <el-col :span="20">
          <el-form-item>
            <el-input v-model="goodsQueryForm.goodsName" placeholder="商品名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" plain icon="el-icon-search" @click="handleQueryGoods">搜索</el-button>
          </el-form-item>
        </el-col>

        <el-col :span="4" style="text-align: right">
          <el-button type="success" plain icon="el-icon-check" plain @click="handleConfirmSelectedGoods">确认选择
          </el-button>
        </el-col>
      </el-form>

      <el-table
        :data="goodsTableData"
        border
        ref="goodsMultiTable"
        @selection-change="handleGoodsSelectionChange"
        id="goodsTable"
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
        @pagination="handleQueryGoods"
      />
    </el-dialog>

  </div>
</template>

<script>
  import {list, page, postObj} from '@/api/marketing/spike/goods'
  import {list as spikePeriodList} from '@/api/marketing/spike/period'

  export default {
    data() {
      return {
        // 选中数组
        ids: [],
        pagination: {
          pageNum: 1,
          pageSize: 10,
          total: 0
        },
        goodsQueryForm: {
          goodsName: undefined
        },
        goodsTableData: [],
        dialog: {
          title: '',
          visible: false,
        },
        goodsDialog: {
          title: '',
          visible: false
        },
        rules: {
          spikePrice: [{
            required: true, message: '请填写秒杀价格', trigger: 'blur'
          }],
          spikeCount: [{
            required: true, message: '请填写秒杀数量', trigger: 'blur'
          }],
          spikeLimit: [{
            required: true, message: '请填写秒杀数量', trigger: 'blur'
          }]
        },
        spikePeriodOptions: [], // 秒杀时间段下拉数据
        selectedGoods: [], // 新增选中的商品
        form: {
          spikeId: undefined,
          spikePeriodId: undefined,
          spikePeriodGoodsList: []
        },
        spikeTitle: undefined
      }
    },
    created() {
      this.form.spikeId = this.$route.query.spikeId
      this.spikeTitle = this.$route.query.spikeTitle
      // 秒杀活动时间段数据加载
      spikePeriodList().then(response => {
        if (response.data && response.data.length > 0) {
          this.spikePeriodOptions = response.data
          this.form.spikePeriodId = response.data[0].id
          this.handleQuery()
        }
      })
    },
    methods: {
      handleQuery() {
        this.form.spikePeriodGoodsList = []
        if (this.form.spikeId && this.form.spikePeriodId) {
          list(this.form.spikeId, this.form.spikePeriodId).then(response => {
            this.form.spikePeriodGoodsList = response.data
          })
        }
      },
      refresh() {
        this.handleQuery()
      },
      // 活动时间段下拉change事件
      handleSelectSpikePeriodChange(val) {
        this.form.spikePeriodGoodsList = []
        const spikePeriodId = val
        if (this.form.spikeId && spikePeriodId) {
          list(this.form.spikeId, spikePeriodId).then(response => {
            this.form.spikePeriodGoodsList = response.data
          })
        }
      },
      handleSubmit() {
        this.$refs["spikePeriodGoodsForm"].validate((valid) => {
          if (valid) {
            postObj(this.form).then(response => {
              this.$message.success(response.msg)
              this.handleQuery()
            })
          }
        })
      },
      handleOpenSelectGoods() {
        if (!this.form.spikePeriodId) {
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
      handleGoodsSelectionChange(selection) {  // 商品选择
        this.selectedGoods = selection
      },
      handleConfirmSelectedGoods() { //确认选择
        if (this.selectedGoods.length <= 0) {
          this.$message.warning("请选择要添加的商品")
          return
        }
        // 校验添加的商品是否重复
        let repeatGoods = []
        let tempSpikeGoodsList = this.form.spikePeriodGoodsList
        this.selectedGoods.forEach(item => {
          let flag = false  //是否被过滤标识
          for (let i = 0; i < tempSpikeGoodsList.length; i++) {
            if (item.goodsId === tempSpikeGoodsList[i].goodsId) { // 重复添加的商品
              console.log(item.goodsId)
              repeatGoods.push(item.goodsName)
              flag = true
            }
          }
          if (flag === false) {
            let selectedGoods = {
              id: undefined,
              goodsId: item.goodsId,
              goodsName: item.goodsName,
              goodsPrice: item.goodsPrice,
              goodsStock: item.goodsStock,
              goodsSn: item.goodsSn,
              spikePrice: 0,
              spikeCount: 0,
              spikeLimit: 0,
              sort: 1,
            }
            this.form.spikePeriodGoodsList.push(selectedGoods)
          }
        })
        this.closeGoodsDialog()
        if (repeatGoods.length > 0) {
          this.$alert('已自动过滤重复商品：' + repeatGoods.join(','), '商品重复提示', {
            confirmButtonText: '确定',
            callback: () => {
            }
          })
        }
      },
      handleRemoveSpikeGoods(index) {
        this.form.spikePeriodGoodsList.splice(index, 1)
      }
    }
  }
</script>

<style scoped>

</style>
