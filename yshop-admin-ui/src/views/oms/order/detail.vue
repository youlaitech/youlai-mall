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
          <el-button size="mini" @click="showUpdateFeeDialog">修改费用信息</el-button>
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
          <el-col :span="4" class="table-cell-title">会员ID</el-col>
          <el-col :span="4" class="table-cell-title">物流单号</el-col>
          <el-col :span="4" class="table-cell-title">支付方式</el-col>
          <el-col :span="4" class="table-cell-title">订单来源</el-col>
          <el-col :span="4" class="table-cell-title">订单类型</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="table-cell">{{order.order_sn}}</el-col>
          <el-col :span="4" class="table-cell">{{order.member_id}}</el-col>
          <el-col :span="4" class="table-cell">{{order.logistics_number}}</el-col>
          <el-col :span="4" class="table-cell">{{order.pay_type | formatPayType}}</el-col>
          <el-col :span="4" class="table-cell">{{order.sourceType | formatSourceType}}</el-col>
          <el-col :span="4" class="table-cell">{{order.type | formatOrderType}}</el-col>
        </el-row>
      </div>

      <div style="margin-top: 20px">
        <svg-icon icon-class="marker" style="color: #606266"></svg-icon>
        <span class="font-small">收货人信息</span>
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
          <el-col :span="6" class="table-cell">{{order.receiver_mobile}}</el-col>
          <el-col :span="6" class="table-cell">{{order.receiver_zip_code}}</el-col>
          <el-col :span="6" class="table-cell">{{order | formatAddress}}</el-col>
        </el-row>
      </div>

      <div style="margin-top: 20px">
        <svg-icon icon-class="marker" style="color: #606266"></svg-icon>
        <span class="font-small">商品信息</span>
      </div>
      <el-table
        :data="order_item_list"
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

    <div style="margin-top: 60px">
      <svg-icon icon-class="marker" style="color: #606266"></svg-icon>
      <span class="font-small">费用信息</span>
    </div>
    <div class="table-layout">
      <el-row>
        <el-col :span="4" class="table-cell-title">商品合计</el-col>
        <el-col :span="4" class="table-cell-title">运费</el-col>
        <el-col :span="4" class="table-cell-title">优惠券</el-col>
        <el-col :span="6" class="table-cell-title">订单总金额</el-col>
        <el-col :span="6" class="table-cell-title">应付款金额</el-col>
      </el-row>
      <el-row>
        <el-col :span="4" class="table-cell">￥{{order.total_amount}}</el-col>
        <el-col :span="4" class="table-cell">￥{{order.freight_amount}}</el-col>
        <el-col :span="4" class="table-cell">-￥{{order.coupon_amount}}</el-col>
        <el-col :span="6" class="table-cell">
          <span class="color-danger">￥{{order.total_amount+order.freight_amount}}</span>
        </el-col>
        <el-col :span="6" class="table-cell">
          <span class="color-danger">￥{{order.pay_amount}}</span>
        </el-col>
      </el-row>
    </div>
    <el-dialog title="修改收货人信息"
               :visible.sync="receiverDialogVisible"
               width="40%">
      <el-form :model="receiverInfo"
               ref="receiverInfoForm"
               label-width="150px">
        <el-form-item label="收货人姓名：">
          <el-input v-model="receiverInfo.receiver_name" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="手机号码：">
          <el-input v-model="receiverInfo.receiver_mobile" style="width: 200px">
          </el-input>
        </el-form-item>
        <el-form-item label="邮政编码：">
          <el-input v-model="receiverInfo.receiver_zip_code" style="width: 200px">
          </el-input>
        </el-form-item>
        <el-form-item label="所在区域：">
          <v-distpicker :province="receiverInfo.receiver_province"
                        :city="receiverInfo.receiver_city"
                        :area="receiverInfo.receiver_area"
                        @selected="onSelectArea"></v-distpicker>
        </el-form-item>
        <el-form-item label="详细地址：">
          <el-input v-model="receiverInfo.receiver_detail_address" type="textarea" rows="3">
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="receiverDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleUpdateReceiverInfo">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改费用信息"
               :visible.sync="feeDialogVisible"
               width="40%">
      <div class="table-layout">
        <el-row>
          <el-col :span="4" class="table-cell-title">商品合计</el-col>
          <el-col :span="4" class="table-cell-title">运费</el-col>
          <el-col :span="4" class="table-cell-title">优惠券</el-col>
          <el-col :span="6" class="table-cell-title">订单总金额</el-col>
          <el-col :span="6" class="table-cell-title">应付款金额</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="table-cell">￥{{order.total_amount}}</el-col>
          <el-col :span="4" class="table-cell">
            <el-input v-model.number="feeInfo.freight_amount" size="mini">
              <template slot="prepend">￥</template>
            </el-input>
          </el-col>
          <el-col :span="4" class="table-cell">-￥{{order.coupon_amount}}</el-col>
          <el-col :span="6" class="table-cell">
            <span class="color-danger">￥{{order.total_amount+feeInfo.freight_amount}}</span>
          </el-col>
          <el-col :span="6" class="table-cell">
            <span class="color-danger">￥{{order.total_amount+feeInfo.freight_amount-order.coupon_amount}}</span>
          </el-col>
        </el-row>
      </div>
      <span slot="footer" class="dialog-footer">
      <el-button @click="feeDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleUpdateFeeInfo">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  import {orderDetail, orderUpdate} from '@/api/oms/order'
  import VDistpicker from 'v-distpicker'

  const defaultReceiverInfo = {
    id: undefined,
    receiver_name: undefined,
    receiver_mobile: undefined,
    receiver_zip_code: undefined,
    receiver_detail_address: undefined,
    receiver_province: undefined,
    receiver_city: undefined,
    receiver_area: undefined
  };

  export default {
    components: {VDistpicker},
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
      formatAddress(order) {
        let address = order.receiver_province
        if (order.receiver_city != null) {
          address += "  " + order.receiver_city
        }
        if (order.receiver_area) {
          address += "  " + order.receiver_area
        }
        if (order.receiver_detail_address) {
          address += "  " + order.receiver_detail_address
        }
        return address
      },
      formatSpecification(specs) {
        return specs
      }
    },
    data() {
      return {
        id: null,
        order: {
          status: undefined
        },
        order_item_list: [],
        receiverInfo: Object.assign({}, defaultReceiverInfo),
        receiverDialogVisible: false,
        feeDialogVisible: false,
        feeInfo: {id: undefined, freight_amount: 0, pay_amount: undefined},
      }
    },
    created() {
      this.initData()
    },
    methods: {
      initData() {
        this.id = this.$route.query.id;
        orderDetail(this.id).then(response => {
          this.order = response.data.order
          this.order_item_list = response.data.order_item_list
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

      onSelectArea(data) {
        this.receiverInfo.receiver_province = data.province.value;
        this.receiverInfo.receiver_city = data.city.value;
        this.receiverInfo.receiver_area = data.area.value;
      },
      showUpdateReceiverDialog() {
        this.receiverDialogVisible = true
        this.receiverInfo = {
          id: this.order.id,
          receiver_name: this.order.receiver_name,
          receiver_mobile: this.order.receiver_mobile,
          receiver_zip_code: this.order.receiver_zip_code,
          receiver_detail_address: this.order.receiver_detail_address,
          receiver_province: this.order.receiver_province,
          receiver_city: this.order.receiver_city,
          receiver_area: this.order.receiver_area,
        }
      },
      handleUpdateReceiverInfo() {
        this.$confirm('是否要修改收货信息?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          orderUpdate(this.id, this.receiverInfo).then(response => {
            this.receiverDialogVisible = false
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            orderDetail(this.id).then(response => {
              this.order = response.data.order
              this.order_item_list = response.data.order_item_list
            })
          })
        })
      },

      showUpdateFeeDialog() {
        this.feeDialogVisible = true
        this.feeInfo.id = this.order.id
        this.feeInfo.freight_amount = this.order.freight_amount
      },

      handleUpdateFeeInfo() {
        this.$confirm('是否要修改费用信息?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.feeInfo.pay_amount=this.order.total_amount+this.feeInfo.freight_amount-this.order.coupon_amount
          orderUpdate(this.id, this.feeInfo).then(response => {
            this.feeDialogVisible = false
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            orderDetail(this.id).then(response => {
              this.order = response.data.order
              this.order_item_list = response.data.order_item_list
            })
          })
        })
      },
      // TODO...
      showMessageDialog() {

      },
      showCloseOrderDialog() {

      },
      showMarkOrderDialog() {

      },
      showLogisticsDialog() {

      },
      handleDeleteOrder() {

      },

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
