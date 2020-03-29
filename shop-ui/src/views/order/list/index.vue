<template> 
  <div class="app-container">
    <!-- 批量操作栏::begin -->
    <div class="operate">
      <el-select
        style="margin:0 10px 20px 0px"
        v-model="operateType" placeholder="批量操作">
        <el-option
          v-for="item in operateOptions"
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
    <!-- 批量操作栏::end -->

    <!-- 搜索表单::begin -->
    <el-form :inline="true" :model="listQuery" ref="queryForm">
      <el-form-item label="订单编号：">
        <el-input v-model="listQuery.orderNo" class="input-width" placeholder="订单编号"></el-input>
      </el-form-item>
      <el-form-item label="收货人：">
        <el-input v-model="listQuery.receiverName" class="input-width" placeholder="收货人姓名/手机号码"></el-input>
      </el-form-item>
      <el-form-item label="提交时间：">
        <el-date-picker
          class="input-width"
          v-model="listQuery.createTime"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="请选择时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="订单状态：">
        <el-select v-model="listQuery.status" class="input-width" placeholder="全部" clearable>
          <el-option v-for="item in statusOptions"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单分类：">
        <el-select v-model="listQuery.orderType" class="input-width" placeholder="全部" clearable>
          <el-option v-for="item in orderTypeOptions"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单来源：">
        <el-select v-model="listQuery.sourceType" class="input-width" placeholder="全部" clearable>
          <el-option v-for="item in sourceTypeOptions"
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
    <el-table ref="orderTable"
              :data="list"
              style="width: 100%;"
              @selection-change="handleSelectionChange"
              v-loading="listLoading" border>
      <el-table-column type="selection" width="60" align="center"></el-table-column>
      <el-table-column label="编号" width="80" align="center">
        <template slot-scope="scope">{{scope.row.id}}</template>
      </el-table-column>
      <el-table-column label="订单编号" width="180" align="center">
        <template slot-scope="scope">{{scope.row.orderNo}}</template>
      </el-table-column>
      <el-table-column label="提交时间" width="180" align="center">
        <template slot-scope="scope">{{scope.row.createTime | formatCreateTime}}</template>
      </el-table-column>
      <el-table-column label="用户账号" align="center">
        <template slot-scope="scope">{{scope.row.memberUsername}}</template>
      </el-table-column>
      <el-table-column label="订单金额" width="120" align="center">
        <template slot-scope="scope">￥{{scope.row.totalAmount}}</template>
      </el-table-column>
      <el-table-column label="支付方式" width="120" align="center">
        <template slot-scope="scope">{{scope.row.payType | formatPayType}}</template>
      </el-table-column>
      <el-table-column label="订单来源" width="120" align="center">
        <template slot-scope="scope">{{scope.row.sourceType | formatSourceType}}</template>
      </el-table-column>
      <el-table-column label="订单状态" width="120" align="center">
        <template slot-scope="scope">{{scope.row.status | formatStatus}}</template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleViewOrder(scope.$index, scope.row)"
          >查看订单</el-button>
          <el-button
            size="mini"
            @click="handleCloseOrder(scope.$index, scope.row)"
            v-show="scope.row.status===0">关闭订单</el-button>
          <el-button
            size="mini"
            @click="handleDeliveryOrder(scope.$index, scope.row)"
            v-show="scope.row.status===1">订单发货</el-button>
          <el-button
            size="mini"
            @click="handleViewLogistics(scope.$index, scope.row)"
            v-show="scope.row.status===2||scope.row.status===3">订单跟踪</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDeleteOrder(scope.$index, scope.row)"
            v-show="scope.row.status===4">删除订单</el-button>
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
      title="关闭订单"
      :visible.sync="closeOrder.dialogVisible" width="30%">
      <span style="vertical-align: top">操作备注：</span>
      <el-input
        style="width: 80%"
        type="textarea"
        :rows="5"
        placeholder="请输入内容"
        v-model="closeOrder.content">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeOrder.dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleCloseOrderConfirm">确 定</el-button>
      </span>
    </el-dialog>
<!--    <logistics-dialog v-model="logisticsDialogVisible"></logistics-dialog>-->
    <!-- 表单弹窗::end -->

  </div>

</template>
<script>
  import {fetchList,closeOrder,deleteOrder} from '@/api/order/list'
  import {formatDate} from '@/utils/date';
  import LogisticsDialog from '@/views/order/list/logisticsDialog';

  const defaultListQuery = {
    orderNo: null,
    receiverName: null,
    status: null,
    orderType: null,
    sourceType: null,
    createTime: null,
  };
  export default {
    name: "orderList",
    components:{LogisticsDialog},
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        listQuery: Object.assign({}, defaultListQuery),
        listLoading: true,
        list: null,
        total: null,
        operateType: null,
        multipleSelection: [],
        closeOrder:{
          dialogVisible:false,
          content:null,
          orderIds:[]
        },
        statusOptions: [
          {
            label: '待付款',
            value: 0
          },
          {
            label: '待发货',
            value: 1
          },
          {
            label: '已发货',
            value: 2
          },
          {
            label: '已完成',
            value: 3
          },
          {
            label: '已关闭',
            value: 4
          }
        ],
        orderTypeOptions: [
          {
            label: '正常订单',
            value: 0
          },
          {
            label: '秒杀订单',
            value: 1
          }
        ],
        sourceTypeOptions: [
          {
            label: 'PC订单',
            value: 0
          },
          {
            label: 'APP订单',
            value: 1
          }
        ],
        operateOptions: [
          {
            label: "批量发货",
            value: 1
          },
          {
            label: "关闭订单",
            value: 2
          },
          {
            label: "删除订单",
            value: 3
          }
        ],
        logisticsDialogVisible:false
      }
    },
    created() {
      this.getList();
    },
    filters: {
      formatCreateTime(time) {
        let date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      },
      formatPayType(value) {
        if (value === 1) {
          return '支付宝';
        } else if (value === 2) {
          return '微信';
        } else {
          return '未支付';
        }
      },
      formatSourceType(value) {
        if (value === 1) {
          return 'APP订单';
        } else {
          return 'PC订单';
        }
      },
      formatStatus(value) {
        if (value === 1) {
          return '待发货';
        } else if (value === 2) {
          return '已发货';
        } else if (value === 3) {
          return '已完成';
        } else if (value === 4) {
          return '已关闭';
        } else if (value === 5) {
          return '无效订单';
        } else {
          return '待付款';
        }
      },
    },
    methods: {
      handleQuery() {
        this.getList();
      },
      resetQuery() {
        this.pageNum = 1;
        this.pageSize = 10;
        this.listQuery = Object.assign({}, defaultListQuery);
        this.resetForm("queryForm");
        },

      handleSelectionChange(val){
        this.multipleSelection = val;
      },
      handleViewOrder(index, row){
        this.$router.push({path:'/order/orderDetail',query:{id:row.id}})
      },
      handleCloseOrder(index, row){
        this.closeOrder.dialogVisible=true;
        this.closeOrder.orderIds=[row.id];
      },
      handleDeliveryOrder(index, row){
        let listItem = this.covertOrder(row);
        this.$router.push({path:'/order/deliverOrderList',query:{list:[listItem]}})
      },
      handleViewLogistics(index, row){
        this.logisticsDialogVisible=true;
      },
      handleDeleteOrder(index, row){
        let ids=[];
        ids.push(row.id);
        this.deleteOrder(ids);
      },
      handleBatchOperate(){
        if(this.multipleSelection==null||this.multipleSelection.length<1){
          this.$message({
            message: '请选择要操作的订单',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        if(this.operateType===1){
          //批量发货
          let list=[];
          for(let i=0;i<this.multipleSelection.length;i++){
            if(this.multipleSelection[i].status===1){
              list.push(this.covertOrder(this.multipleSelection[i]));
            }
          }
          if(list.length===0){
            this.$message({
              message: '选中订单中没有可以发货的订单',
              type: 'warning',
              duration: 1000
            });
            return;
          }
          this.$router.push({path:'/order/deliverOrderList',query:{list:list}})
        }else if(this.operateType===2){
          //关闭订单
          this.closeOrder.orderIds=[];
          for(let i=0;i<this.multipleSelection.length;i++){
            this.closeOrder.orderIds.push(this.multipleSelection[i].id);
          }
          this.closeOrder.dialogVisible=true;
        }else if(this.operateType===3){
          //删除订单
          let ids=[];
          for(let i=0;i<this.multipleSelection.length;i++){
            ids.push(this.multipleSelection[i].id);
          }
          this.deleteOrder(ids);
        }
      },
      handleSizeChange(val){
        this.listQuery.pageNum = 1;
        this.listQuery.pageSize = val;
        this.getList();
      },
      handleCurrentChange(val){
        this.listQuery.pageNum = val;
        this.getList();
      },
      handleCloseOrderConfirm() {
        if (this.closeOrder.content == null || this.closeOrder.content === '') {
          this.$message({
            message: '操作备注不能为空',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        let params = new URLSearchParams();
        params.append('ids', this.closeOrder.orderIds);
        params.append('note', this.closeOrder.content);
        closeOrder(params).then(response=>{
          this.closeOrder.orderIds=[];
          this.closeOrder.dialogVisible=false;
          this.getList();
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      getList() {
        this.listLoading = true;
        fetchList(this.pageNum,this.pageSize,this.listQuery).then(response => {
          this.listLoading = false;
          this.list = response.data.records;
          this.total = response.data.total;
        });
      },
      deleteOrder(ids){
        this.$confirm('是否要进行该删除操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = new URLSearchParams();
          params.append("ids",ids);
          deleteOrder(params).then(response=>{
            this.$message({
              message: '删除成功！',
              type: 'success',
              duration: 1000
            });
            this.getList();
          });
        })
      },
      covertOrder(order){
        let address=order.receiverProvince+order.receiverCity+order.receiverRegion+order.receiverDetailAddress;
        let listItem={
          orderId:order.id,
          orderNo:order.orderNo,
          receiverName:order.receiverName,
          receiverPhone:order.receiverPhone,
          receiverPostCode:order.receiverPostCode,
          address:address,
          deliveryCompany:null,
          deliveryNo:null
        };
        return listItem;
      }
    }
  }
</script>
<style scoped>
  .input-width {
    width: 203px;
  }
</style>


