<template>
  <div class="detail-container">
    <el-steps :active="formatStepStatus(order.status)" finish-status="success" align-center>
      <el-step title="提交订单" :description="order.create_time"></el-step>
      <el-step title="支付订单" :description="order.pay_time"></el-step>
      <el-step title="平台发货" :description="order.deliver_time"></el-step>
      <el-step title="确认收货" :description="order.receive_time"></el-step>
      <el-step title="完成评价" :description="order.comment_time"></el-step>
    </el-steps>

    <el-card shadow="never" style="margin-top: 15px">
      <div class="operate-container">
        <i class="el-icon-warning color-danger" style="margin-left: 20px"></i>
        <span class="color-danger">当前订单状态：{{order.status | formatStatus}}</span>
        <div class="operate-button-container" v-show="order.status===0">
          <el-button size="mini" @click="showUpdateReceiverDialog">修改收货人信息</el-button>
          <el-button size="mini" @click="showUpdateMoneyDialog">修改费用信息</el-button>
          <el-button size="mini" @click="showMessageDialog">发送站内信</el-button>
          <el-button size="mini" @click="showCloseOrderDialog">关闭订单</el-button>
          <el-button size="mini" @click="showMarkOrderDialog">备注订单</el-button>
        </div>
        <div class="operate-button-container" v-show="order.status===1">
          <el-button size="mini" @click="showUpdateReceiverDialog">修改收货人信息</el-button>
          <el-button size="mini" @click="showMessageDialog">发送站内信</el-button>
          <el-button size="mini" @click="showMarkOrderDialog">备注订单</el-button>
        </div>
        <div class="operate-button-container" v-show="order.status===2||order.status===3">
          <el-button size="mini" @click="showLogisticsDialog">订单跟踪</el-button>
          <el-button size="mini" @click="showMessageDialog">发送站内信</el-button>
          <el-button size="mini" @click="showMarkOrderDialog">备注订单</el-button>
        </div>
        <div class="operate-button-container" v-show="order.status===4">
          <el-button size="mini" @click="handleDeleteOrder">删除订单</el-button>
          <el-button size="mini" @click="showMarkOrderDialog">备注订单</el-button>
        </div>
      </div>

      <div style="margin-top: 20px">
        <svg-icon icon-class="marker" style="color: #606266"></svg-icon>
        <span class="font-small">基本信息</span>
      </div>
      <div class="table-layout">
        <el-row>
          <el-col :span="4" class="table-cell-title">订单编号</el-col>
          <el-col :span="4" class="table-cell-title">会员名称</el-col>
          <el-col :span="4" class="table-cell-title">支付方式</el-col>
          <el-col :span="4" class="table-cell-title">订单来源</el-col>
          <el-col :span="4" class="table-cell-title">订单类型</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="table-cell">{{order.order_sn}}</el-col>
          <el-col :span="4" class="table-cell">{{order.member_name}}</el-col>
            <el-col :span="4" class="table-cell">{{order.pay_type | formatPayType}}</el-col>
          <el-col :span="4" class="table-cell">{{order.sourceType | formatSourceType}}</el-col>
          <el-col :span="4" class="table-cell">{{order.type | formatOrderType}}</el-col>
        </el-row>
      </div>
      <div class="table-layout">
        <el-row>
          <el-col :span="6" class="table-cell-title">收货人</el-col>
          <el-col :span="6" class="table-cell-title">手机号码</el-col>
          <el-col :span="6" class="table-cell-title">邮政编码</el-col>
          <el-col :span="6" class="table-cell-title">收货地址</el-col>
        </el-row>
        <el-row>
          <el-col :span="6" class="table-cell">{{order.receiver_name}}</el-col>
          <el-col :span="6" class="table-cell">{{order.receiver_phone}}</el-col>
          <el-col :span="6" class="table-cell">{{order.receiver_zip_code}}</el-col>
          <el-col :span="6" class="table-cell">{{order | formatAddress}}</el-col>
        </el-row>
      </div>

      <div style="margin-top: 20px">
        <svg-icon icon-class="marker" style="color: #606266"></svg-icon>
        <span class="font-small">商品信息</span>
      </div>
      <el-table
        :data="order.order_item_list"
        style="width: 100%;margin-top: 20px"
        border>
        <el-table-column label="商品图片" width="120" align="center">
          <template slot-scope="scope">
            <img :src="scope.row.pic_url" style="height: 80px">
          </template>
        </el-table-column>
        <el-table-column label="商品名称" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.spu_name}}</p>
            <p>品牌：{{scope.row.spu_brand}}</p>
          </template>
        </el-table-column>
        <el-table-column label="价格/货号" width="120" align="center">
          <template slot-scope="scope">
            <p>价格：￥{{scope.row.sku_price}}</p>
            <p>货号：{{scope.row.spu_sn}}</p>
          </template>
        </el-table-column>
        <el-table-column label="属性" width="120" align="center">
          <template slot-scope="scope">
            {{scope.row.specs | formatSpecification}}
          </template>
        </el-table-column>
        <el-table-column label="数量" width="120" align="center">
          <template slot-scope="scope">
            {{scope.row.sku_quantity}}
          </template>
        </el-table-column>
        <el-table-column label="小计" width="120" align="center">
          <template slot-scope="scope">
            ￥{{scope.row.sku_price*scope.row.sku_quantity}}
          </template>
        </el-table-column>
      </el-table>
      <div style="float: right;margin: 20px">
        合计：<span class="color-danger">￥{{order.total_amount}}</span>
      </div>
    </el-card>
  </div>
</template>

<script>
  import {orderDetail} from '@/api/oms/order'

  export default {
    name: "detail",
    filters: {
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
          return '微信订单';
        }
      },
      formatOrderType(value) {
        if (value === 1) {
          return '秒杀订单';
        } else {
          return '正常订单';
        }
      },
      formatAddress(order){
        let address = order.receiver_province
        if (order.receiver_city != null) {
          address += "  " + order.receiver_city
        }
        if(order.receiver_area){
          address += "  " + order.receiver_area
        }
        if(order.receiver_detail_address){
          address += "  " + order.receiver_detail_address
        }
        return address
      },
      formatSpecification(specs){
        return specs
      }
    },
    data() {
      return {
        id: null,
        order: {}
      }
    },
    created() {
      this.initData()
    },
    methods: {
      initData() {
        this.id = this.$route.query.id;
        orderDetail(this.id).then(response => {
          this.order = response.data
        })
      },
      formatStepStatus(value) {
        if (value === 1) {
          //待发货
          return 2
        } else if (value === 2) {
          //已发货
          return 3
        } else if (value === 3) {
          //已完成
          return 4
        } else {
          //待付款、已关闭、无限订单
          return 1
        }
      },



      // TODO...
      showUpdateReceiverDialog() {

      },
      showUpdateMoneyDialog() {

      },
      showMessageDialog() {

      },
      showCloseOrderDialog() {

      },
      showMarkOrderDialog() {

      },
      showLogisticsDialog() {

      },
      handleDeleteOrder() {

      }
    },
  }
</script>

<style scoped>
  .detail-container {
    width: 80%;
    padding: 20px 20px 20px 20px;
    margin: 20px auto;
  }

  .operate-container {
    background: #F2F6FC;
    height: 80px;
    margin: -20px -20px 0;
    line-height: 80px;
  }

  .operate-button-container {
    float: right;
    margin-right: 20px
  }

  .table-layout {
    margin-top: 20px;
    border-left: 1px solid #DCDFE6;
    border-top: 1px solid #DCDFE6;
  }

  .table-cell {
    height: 60px;
    line-height: 40px;
    border-right: 1px solid #DCDFE6;
    border-bottom: 1px solid #DCDFE6;
    padding: 10px;
    font-size: 14px;
    color: #606266;
    text-align: center;
    overflow: hidden;
  }

  .table-cell-title {
    border-right: 1px solid #DCDFE6;
    border-bottom: 1px solid #DCDFE6;
    padding: 10px;
    background: #F2F6FC;
    text-align: center;
    font-size: 14px;
    color: #303133;
  }
</style>
