<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单号" prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="订单号"></el-input>
    </el-form-item>
    <el-form-item label="订单总额（分）" prop="totalAmount">
      <el-input v-model="dataForm.totalAmount" placeholder="订单总额（分）"></el-input>
    </el-form-item>
    <el-form-item label="商品总数" prop="totalQuantity">
      <el-input v-model="dataForm.totalQuantity" placeholder="商品总数"></el-input>
    </el-form-item>
    <el-form-item label="订单来源[0->PC订单；1->app订单]" prop="sourceType">
      <el-input v-model="dataForm.sourceType" placeholder="订单来源[0->PC订单；1->app订单]"></el-input>
    </el-form-item>
    <el-form-item label="订单状态【101->待付款；102->用户取消；103->系统取消；201->已付款；202->申请退款；203->已退款；301->待发货；401->已发货；501->用户收货；502->系统收货；901->已完成】" prop="status">
      <el-input v-model="dataForm.status" placeholder="订单状态【101->待付款；102->用户取消；103->系统取消；201->已付款；202->申请退款；203->已退款；301->待发货；401->已发货；501->用户收货；502->系统收货；901->已完成】"></el-input>
    </el-form-item>
    <el-form-item label="订单备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="订单备注"></el-input>
    </el-form-item>
    <el-form-item label="会员id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="会员id"></el-input>
    </el-form-item>
    <el-form-item label="使用的优惠券" prop="couponId">
      <el-input v-model="dataForm.couponId" placeholder="使用的优惠券"></el-input>
    </el-form-item>
    <el-form-item label="优惠券抵扣金额（分）" prop="couponAmount">
      <el-input v-model="dataForm.couponAmount" placeholder="优惠券抵扣金额（分）"></el-input>
    </el-form-item>
    <el-form-item label="运费金额（分）" prop="freightAmount">
      <el-input v-model="dataForm.freightAmount" placeholder="运费金额（分）"></el-input>
    </el-form-item>
    <el-form-item label="应付总额（分）" prop="payAmount">
      <el-input v-model="dataForm.payAmount" placeholder="应付总额（分）"></el-input>
    </el-form-item>
    <el-form-item label="支付时间" prop="payTime">
      <el-input v-model="dataForm.payTime" placeholder="支付时间"></el-input>
    </el-form-item>
    <el-form-item label="支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】" prop="payType">
      <el-input v-model="dataForm.payType" placeholder="支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】"></el-input>
    </el-form-item>
    <el-form-item label="发货时间" prop="deliveryTime">
      <el-input v-model="dataForm.deliveryTime" placeholder="发货时间"></el-input>
    </el-form-item>
    <el-form-item label="确认收货时间" prop="receiveTime">
      <el-input v-model="dataForm.receiveTime" placeholder="确认收货时间"></el-input>
    </el-form-item>
    <el-form-item label="评价时间" prop="commentTime">
      <el-input v-model="dataForm.commentTime" placeholder="评价时间"></el-input>
    </el-form-item>
    <el-form-item label="逻辑删除【0->正常；1->已删除】" prop="deleted">
      <el-input v-model="dataForm.deleted" placeholder="逻辑删除【0->正常；1->已删除】"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="gmtCreate">
      <el-input v-model="dataForm.gmtCreate" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="gmtModified">
      <el-input v-model="dataForm.gmtModified" placeholder="修改时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          orderSn: '',
          totalAmount: '',
          totalQuantity: '',
          sourceType: '',
          status: '',
          remark: '',
          memberId: '',
          couponId: '',
          couponAmount: '',
          freightAmount: '',
          payAmount: '',
          payTime: '',
          payType: '',
          deliveryTime: '',
          receiveTime: '',
          commentTime: '',
          deleted: '',
          gmtCreate: '',
          gmtModified: ''
        },
        dataRule: {
          orderSn: [
            { required: true, message: '订单号不能为空', trigger: 'blur' }
          ],
          totalAmount: [
            { required: true, message: '订单总额（分）不能为空', trigger: 'blur' }
          ],
          totalQuantity: [
            { required: true, message: '商品总数不能为空', trigger: 'blur' }
          ],
          sourceType: [
            { required: true, message: '订单来源[0->PC订单；1->app订单]不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '订单状态【101->待付款；102->用户取消；103->系统取消；201->已付款；202->申请退款；203->已退款；301->待发货；401->已发货；501->用户收货；502->系统收货；901->已完成】不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '订单备注不能为空', trigger: 'blur' }
          ],
          memberId: [
            { required: true, message: '会员id不能为空', trigger: 'blur' }
          ],
          couponId: [
            { required: true, message: '使用的优惠券不能为空', trigger: 'blur' }
          ],
          couponAmount: [
            { required: true, message: '优惠券抵扣金额（分）不能为空', trigger: 'blur' }
          ],
          freightAmount: [
            { required: true, message: '运费金额（分）不能为空', trigger: 'blur' }
          ],
          payAmount: [
            { required: true, message: '应付总额（分）不能为空', trigger: 'blur' }
          ],
          payTime: [
            { required: true, message: '支付时间不能为空', trigger: 'blur' }
          ],
          payType: [
            { required: true, message: '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】不能为空', trigger: 'blur' }
          ],
          deliveryTime: [
            { required: true, message: '发货时间不能为空', trigger: 'blur' }
          ],
          receiveTime: [
            { required: true, message: '确认收货时间不能为空', trigger: 'blur' }
          ],
          commentTime: [
            { required: true, message: '评价时间不能为空', trigger: 'blur' }
          ],
          deleted: [
            { required: true, message: '逻辑删除【0->正常；1->已删除】不能为空', trigger: 'blur' }
          ],
          gmtCreate: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          gmtModified: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/oms/order/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderSn = data.order.orderSn
                this.dataForm.totalAmount = data.order.totalAmount
                this.dataForm.totalQuantity = data.order.totalQuantity
                this.dataForm.sourceType = data.order.sourceType
                this.dataForm.status = data.order.status
                this.dataForm.remark = data.order.remark
                this.dataForm.memberId = data.order.memberId
                this.dataForm.couponId = data.order.couponId
                this.dataForm.couponAmount = data.order.couponAmount
                this.dataForm.freightAmount = data.order.freightAmount
                this.dataForm.payAmount = data.order.payAmount
                this.dataForm.payTime = data.order.payTime
                this.dataForm.payType = data.order.payType
                this.dataForm.deliveryTime = data.order.deliveryTime
                this.dataForm.receiveTime = data.order.receiveTime
                this.dataForm.commentTime = data.order.commentTime
                this.dataForm.deleted = data.order.deleted
                this.dataForm.gmtCreate = data.order.gmtCreate
                this.dataForm.gmtModified = data.order.gmtModified
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/oms/order/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderSn': this.dataForm.orderSn,
                'totalAmount': this.dataForm.totalAmount,
                'totalQuantity': this.dataForm.totalQuantity,
                'sourceType': this.dataForm.sourceType,
                'status': this.dataForm.status,
                'remark': this.dataForm.remark,
                'memberId': this.dataForm.memberId,
                'couponId': this.dataForm.couponId,
                'couponAmount': this.dataForm.couponAmount,
                'freightAmount': this.dataForm.freightAmount,
                'payAmount': this.dataForm.payAmount,
                'payTime': this.dataForm.payTime,
                'payType': this.dataForm.payType,
                'deliveryTime': this.dataForm.deliveryTime,
                'receiveTime': this.dataForm.receiveTime,
                'commentTime': this.dataForm.commentTime,
                'deleted': this.dataForm.deleted,
                'gmtCreate': this.dataForm.gmtCreate,
                'gmtModified': this.dataForm.gmtModified
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
