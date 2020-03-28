<template>
  <div class="app-container">
    <!-- 操作栏::begin -->
    <div class="operate">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="success" @click="handleUpdate" :disabled="single">修改</el-button>
      <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      <el-select
        style="margin:0 10px 20px 10px"
        v-model="operateType" placeholder="批量操作">
        <el-option
          v-for="item in operates"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>

      <el-button
        class="search-button"
        @click="handleBatchOperate()"
        type="primary">
        确定
      </el-button>
    </div>
    <!-- 操作栏::end -->

    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item label="商品名称" prop="nickName">
        <el-input v-model="queryParams.name" placeholder="商品名称"></el-input>
      </el-form-item>
      <el-form-item label="商品货号" prop="nickName">
        <el-input v-model="queryParams.goodsSn" placeholder="商品货号"></el-input>
      </el-form-item>
      <el-form-item label="商品分类" prop="nickName">
        <el-cascader
          clearable
          v-model="selectGoodsCateValue"
          :options="goodsCateOptions">
        </el-cascader>
      </el-form-item>
      <el-form-item label="商品品牌" prop="nickName">
        <el-select
          v-model="queryParams.brandId"
          placeholder="请选择品牌">
          <el-option
            v-for="item in brandOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="上架状态" prop="nickName">
        <el-select v-model="queryParams.publishStatus" placeholder="全部" clearable>
          <el-option
            v-for="item in publishStatusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="nickName">
        <el-select v-model="queryParams.verifyStatus" placeholder="全部" clearable>
          <el-option
            v-for="item in verifyStatusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 搜索表单::end -->

    <!-- 数据表格::start -->
    <el-table
      id="dataTable"
      ref="multipleTable"
      :data="tableData"
      @selection-change="handleSelectionChange"
      border>
      <el-table-column
        type="selection"
        min-width="5%">
      </el-table-column>
      <el-table-column label="编号" width="100" align="center">
        <template slot-scope="scope">{{scope.row.id}}</template>
      </el-table-column>
      <el-table-column label="商品图片" width="120" align="center">
        <template slot-scope="scope"><img style="height: 80px" :src="scope.row.pic"></template>
      </el-table-column>
      <el-table-column label="商品名称" align="center">
        <template slot-scope="scope">
          <p>{{scope.row.name}}</p>
          <p>品牌：{{scope.row.brandName}}</p>
        </template>
      </el-table-column>
      <el-table-column label="价格/货号" width="120" align="center">
        <template slot-scope="scope">
          <p>价格：￥{{scope.row.price}}</p>
          <p>货号：{{scope.row.goodsSn}}</p>
        </template>
      </el-table-column>
      <el-table-column label="标签" width="140" align="center">
        <template slot-scope="scope">
          <p>上架：
            <el-switch
              @change="handlePublishStatusChange(scope.$index, scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.publishStatus">
            </el-switch>
          </p>
          <p>新品：
            <el-switch
              @change="handleNewStatusChange(scope.$index, scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.newStatus">
            </el-switch>
          </p>
          <p>推荐：
            <el-switch
              @change="handleRecommendStatusChange(scope.$index, scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.recommendStatus">
            </el-switch>
          </p>
        </template>
      </el-table-column>
      <el-table-column label="排序" width="100" align="center">
        <template slot-scope="scope">{{scope.row.sort}}</template>
      </el-table-column>
      <el-table-column label="SKU库存" width="100" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" @click="handleShowSkuEditDialog(scope.$index, scope.row)" circle></el-button>
        </template>
      </el-table-column>
      <el-table-column label="销量" width="100" align="center">
        <template slot-scope="scope">{{scope.row.sale}}</template>
      </el-table-column>
      <el-table-column label="审核状态" width="100" align="center">
        <template slot-scope="scope">
          <p>{{scope.row.verifyStatus | verifyStatusFilter}}</p>
          <p>
            <el-button
              type="text"
              @click="handleShowVerifyDetail(scope.$index, scope.row)">审核详情
            </el-button>
          </p>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template slot-scope="scope">
          <p>
            <el-button
              size="mini"
              @click="handleShowGoods(scope.$index, scope.row)">查看
            </el-button>
            <el-button
              size="mini"
              @click="handleShowLog(scope.$index, scope.row)">日志
            </el-button>
<!--            <el-button-->
<!--              size="mini"-->
<!--              @click="handleUpdateProduct(scope.$index, scope.row)">编辑-->
<!--            </el-button>-->
<!--          </p>-->
<!--          <p>-->

<!--            <el-button-->
<!--              size="mini"-->
<!--              type="danger"-->
<!--              @click="handleDelete(scope.$index, scope.row)">删除-->
<!--            </el-button>-->
          </p>
        </template>
      </el-table-column>
    </el-table>
    <!-- 数据表格::end-->

    <!-- 分页工具条::start -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="pageNum"
      :limit.sync="pageSize"
      @pagination="handleQuery"/>
    <!-- 分页工具条::end -->

    <!-- 表单弹窗::start -->
    <el-dialog
      title="编辑货品信息"
      :visible.sync="editSkuInfo.dialogVisible"
      width="40%">
      <span>商品货号：</span>
      <span>{{editSkuInfo.goodsSn}}</span>
      <el-input placeholder="按sku编号搜索" v-model="editSkuInfo.keyword" size="small" style="width: 50%;margin-left: 20px">
        <el-button slot="append" icon="el-icon-search" @click="handleSearchEditSku"></el-button>
      </el-input>
      <el-table style="width: 100%;margin-top: 20px"
                :data="editSkuInfo.stockList"
                border>
        <el-table-column
          label="SKU编号"
          align="center">
          <template slot-scope="scope">
            <el-input v-model="scope.row.skuCode"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          v-for="(item,index) in editSkuInfo.goodsAttr"
          :label="item.name"
          :key="item.id"
          align="center">
          <template slot-scope="scope">
            {{getGoodsSkuSp(scope.row,index)}}
          </template>
        </el-table-column>
        <el-table-column
          label="销售价格"
          width="80"
          align="center">
          <template slot-scope="scope">
            <el-input v-model="scope.row.price"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          label="商品库存"
          width="80"
          align="center">
          <template slot-scope="scope">
            <el-input v-model="scope.row.stock"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          label="库存预警值"
          width="100"
          align="center">
          <template slot-scope="scope">
            <el-input v-model="scope.row.lowStock"></el-input>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editSkuInfo.dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditSkuConfirm">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 表单弹窗::end -->

  </div>
</template>

<script>
  import {page, delObj,updatePublishStatus,updateNewStatus,updateRecommendStatus,updateDeleteStatus,fetchSkuStockList,updateSkuStockList} from '@/api/pms/list'
  import {list as fetchBrandList} from '@/api/pms/brand'
  import {treeSelect} from '@/api/pms/category'
  import {page as fetchGoodsAttrList} from '@/api/pms/attribute'

  const defaultQueryParams = {
    name: undefined,
    publishStatus: undefined,
    verifyStatus: undefined,
    recommendStatus: undefined,
    goodsSn: undefined,
    goodsCategoryId: undefined,
    brandId: undefined
  };

  export default {
    name: "index",
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        records: undefined,
        selectGoodsCateValue: [],
        goodsCateOptions: [],
        brandOptions: [],
        tableData: [],
        total: 0,
        publishStatusOptions: [{
          value: 1,
          label: '上架'
        }, {
          value: 0,
          label: '下架'
        }],
        verifyStatusOptions: [{
          value: 1,
          label: '审核通过'
        }, {
          value: 0,
          label: '未审核'
        }],
        operates: [
          {
            label: "商品上架",
            value: "publishOn"
          },
          {
            label: "商品下架",
            value: "publishOff"
          },
          {
            label: "设为推荐",
            value: "recommendOn"
          },
          {
            label: "取消推荐",
            value: "recommendOff"
          },
          {
            label: "设为新品",
            value: "newOn"
          },
          {
            label: "取消新品",
            value: "newOff"
          },
          {
            label: "转移到分类",
            value: "transferCategory"
          },
          {
            label: "移入回收站",
            value: "recycle"
          }
        ],
        operateType: undefined,
        // 选中数组
        ids: [],
        // 限制一行选中按钮开启的控制【修改按钮】默认true不开启按钮
        single: true,
        // 限制一行或多行选中按钮就开启的控制 【删除按钮】 默认true不开启按钮
        multiple: true,

        queryParams: Object.assign({},defaultQueryParams),

        editSkuInfo:{
          dialogVisible: false,
          goodsId: undefined,
          goodsSn:'',
          goodsAttributeCategoryId: undefined,
          stockList:[],
          goodsAttr:[],
          keyword: undefined
        },
      }
    },
    watch: {
      selectGoodsCateValue: function (value) {
        if (value != null && value.length === 2) {
          this.queryParams.goodsCategoryId = value[1];
        } else {
          this.queryParams.goodsCategoryId = null;
        }
      }
    },
    filters: {
      verifyStatusFilter(value) {
        if (value === 1) {
          return '审核通过';
        } else {
          return '未审核';
        }
      }
    },
    methods: {
      handleAdd() {
        this.$router.push({path:'/shop/add'});
      },

      handleUpdate() {
        const id = this.ids[0];

        this.$router.push({path:'/shop/update',query:{id:id}});
      },
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delObj(ids)
        }).then(() => {
          this.$message.success("删除成功")
          this.handleQuery()
        }).catch(() =>
          this.$message.info("已取消删除")
        )
      },

      getGoodsCateList() {
        treeSelect().then(response => {
          let list = response.data;
          this.goodsCateOptions = [];
          for (let i = 0; i < list.length; i++) {
            let children = [];
            if (list[i].children != null && list[i].children.length > 0) {
              for (let j = 0; j < list[i].children.length; j++) {
                children.push({label: list[i].children[j].label, value: list[i].children[j].id});
              }
            }
            if(list[i].id != 0){
              this.goodsCateOptions.push({label: list[i].label, value: list[i].id, children: children});
            }
          }
        });
      },
      getBrandList() {
        fetchBrandList().then(response => {
          this.brandOptions = [];
          let brandList = response.data;
          for (let i = 0; i < brandList.length; i++) {
            this.brandOptions.push({label: brandList[i].brandName, value: brandList[i].brandId});
          }
        });
      },

      handleQuery() {
        // console.log(this.queryParams);
        page(this.pageNum,this.pageSize,this.queryParams).then(response => {
          this.tableData = response.data.records;
          this.total = response.data.total
        });
      },

      resetQuery() {
        this.queryParams = {
          name: undefined,
          pageNum: 1,
          pageSize: 10,
        };
        this.selectGoodsCateValue = [];
        this.resetForm("queryForm");
        this.handleQuery()
      },

      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id);
        this.single = selection.length != 1
        this.multiple = !selection.length
      },

      cancel() {
        this.open = false;
        this.reset();
      },
      reset() {
        this.resetForm("form")
        this.form = {
          id: undefined,
          name: undefined,
          firstLetter: undefined,
          sort: undefined,
          factoryStatus: undefined,
          showStatus: undefined
        };
      },

      handleBatchOperate() {
        if(this.operateType==null){
          this.$message({
            message: '请选择操作类型',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        if(this.ids ==null||this.ids.length<1){
          this.$message({
            message: '请选择要操作的商品',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        this.$confirm('是否要进行该批量操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids=[];
          for(let i=0;i<this.ids.length;i++){
            ids.push(this.ids[i]);
          }
          switch (this.operateType) {
            case this.operates[0].value:
              this.updatePublishStatus(1,ids);
              break;
            case this.operates[1].value:
              this.updatePublishStatus(0,ids);
              break;
            case this.operates[2].value:
              this.updateRecommendStatus(1,ids);
              break;
            case this.operates[3].value:
              this.updateRecommendStatus(0,ids);
              break;
            case this.operates[4].value:
              this.updateNewStatus(1,ids);
              break;
            case this.operates[5].value:
              this.updateNewStatus(0,ids);
              break;
            case this.operates[6].value:
              break;
            case this.operates[7].value:
              this.updateDeleteStatus(1,ids);
              break;
            default:
              break;
          }
          this.handleQuery();
        });
      },

      updateDeleteStatus(deleteStatus, ids) {
        let params = new URLSearchParams();
        params.append('ids', ids);
        params.append('deleteStatus', deleteStatus);
        updateDeleteStatus(params).then(response => {
          this.$message({
            message: '删除成功',
            type: 'success',
            duration: 1000
          });
        });
        this.handleQuery();
      },

      handlePublishStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updatePublishStatus(row.publishStatus, ids);
      },
      updatePublishStatus(publishStatus, ids) {
        let params = new URLSearchParams();
        params.append('ids', ids);
        params.append('publishStatus', publishStatus);
        updatePublishStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },

      handleNewStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateNewStatus(row.newStatus, ids);
      },
      updateNewStatus(newStatus, ids) {
        let params = new URLSearchParams();
        params.append('ids', ids);
        params.append('newStatus', newStatus);
        updateNewStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },

      handleRecommendStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateRecommendStatus(row.recommendStatus, ids);
      },
      updateRecommendStatus(recommendStatus, ids) {
        let params = new URLSearchParams();
        params.append('ids', ids);
        params.append('recommendStatus', recommendStatus);
        updateRecommendStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },

      handleShowSkuEditDialog(index,row){
        this.editSkuInfo.dialogVisible=true;
        this.editSkuInfo.goodsId=row.id;
        this.editSkuInfo.goodsSn=row.goodsSn;
        this.editSkuInfo.goodsAttributeCategoryId = row.goodsAttributeCategoryId;
        this.editSkuInfo.keyword=null;
        fetchSkuStockList(row.id,{keyword:this.editSkuInfo.keyword}).then(response=>{
          this.editSkuInfo.stockList=response.data;
        });
        if(row.goodsAttributeCategoryId!=null){
          let param = {type: 0,attributeTypeId: row.goodsAttributeCategoryId};
          fetchGoodsAttrList(1,100,param).then(response=>{
            this.editSkuInfo.goodsAttr=response.data.records;
          });
        }
      },
      handleSearchEditSku(){
        fetchSkuStockList(this.editSkuInfo.goodsId,{keyword:this.editSkuInfo.keyword}).then(response=>{
          this.editSkuInfo.stockList=response.data;
        });
      },
      handleEditSkuConfirm(){
        if(this.editSkuInfo.stockList==null||this.editSkuInfo.stockList.length<=0){
          this.$message({
            message: '暂无sku信息',
            type: 'warning',
            duration: 1000
          });
          return
        }
        this.$confirm('是否要进行修改', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          updateSkuStockList(this.editSkuInfo.goodsId,this.editSkuInfo.stockList).then(response=>{
            this.$message({
              message: '修改成功',
              type: 'success',
              duration: 1000
            });
            this.editSkuInfo.dialogVisible=false;
          });
        });
      },
      handleShowVerifyDetail(index,row){
        console.log("handleShowVerifyDetail",row);
      },

      handleShowGoods(index,row){
        console.log("handleShowProduct",row);
      },
    },
    created() {
      this.getGoodsCateList();
      this.getBrandList();
      this.handleQuery();
    },
  }
</script>

<style scoped>

</style>
