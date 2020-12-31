<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="订单id"></el-input>
    </el-form-item>
    <el-form-item label="支付流水号" prop="paySn">
      <el-input v-model="dataForm.paySn" placeholder="支付流水号"></el-input>
    </el-form-item>
    <el-form-item label="应付总额(分)" prop="payAmount">
      <el-input v-model="dataForm.payAmount" placeholder="应付总额(分)"></el-input>
    </el-form-item>
    <el-form-item label="支付时间" prop="payTime">
      <el-input v-model="dataForm.payTime" placeholder="支付时间"></el-input>
    </el-form-item>
    <el-form-item label="支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】" prop="payType">
      <el-input v-model="dataForm.payType" placeholder="支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】"></el-input>
    </el-form-item>
    <el-form-item label="支付状态" prop="payStatus">
      <el-input v-model="dataForm.payStatus" placeholder="支付状态"></el-input>
    </el-form-item>
    <el-form-item label="确认时间" prop="confirmTime">
      <el-input v-model="dataForm.confirmTime" placeholder="确认时间"></el-input>
    </el-form-item>
    <el-form-item label="回调内容" prop="callbackContent">
      <el-input v-model="dataForm.callbackContent" placeholder="回调内容"></el-input>
    </el-form-item>
    <el-form-item label="回调时间" prop="callbackTime">
      <el-input v-model="dataForm.callbackTime" placeholder="回调时间"></el-input>
    </el-form-item>
    <el-form-item label="交易内容" prop="paySubject">
      <el-input v-model="dataForm.paySubject" placeholder="交易内容"></el-input>
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
          orderId: '',
          paySn: '',
          payAmount: '',
          payTime: '',
          payType: '',
          payStatus: '',
          confirmTime: '',
          callbackContent: '',
          callbackTime: '',
          paySubject: '',
          deleted: '',
          gmtCreate: '',
          gmtModified: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: '订单id不能为空', trigger: 'blur' }
          ],
          paySn: [
            { required: true, message: '支付流水号不能为空', trigger: 'blur' }
          ],
          payAmount: [
            { required: true, message: '应付总额(分)不能为空', trigger: 'blur' }
          ],
          payTime: [
            { required: true, message: '支付时间不能为空', trigger: 'blur' }
          ],
          payType: [
            { required: true, message: '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】不能为空', trigger: 'blur' }
          ],
          payStatus: [
            { required: true, message: '支付状态不能为空', trigger: 'blur' }
          ],
          confirmTime: [
            { required: true, message: '确认时间不能为空', trigger: 'blur' }
          ],
          callbackContent: [
            { required: true, message: '回调内容不能为空', trigger: 'blur' }
          ],
          callbackTime: [
            { required: true, message: '回调时间不能为空', trigger: 'blur' }
          ],
          paySubject: [
            { required: true, message: '交易内容不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/oms/orderpay/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.orderPay.orderId
                this.dataForm.paySn = data.orderPay.paySn
                this.dataForm.payAmount = data.orderPay.payAmount
                this.dataForm.payTime = data.orderPay.payTime
                this.dataForm.payType = data.orderPay.payType
                this.dataForm.payStatus = data.orderPay.payStatus
                this.dataForm.confirmTime = data.orderPay.confirmTime
                this.dataForm.callbackContent = data.orderPay.callbackContent
                this.dataForm.callbackTime = data.orderPay.callbackTime
                this.dataForm.paySubject = data.orderPay.paySubject
                this.dataForm.deleted = data.orderPay.deleted
                this.dataForm.gmtCreate = data.orderPay.gmtCreate
                this.dataForm.gmtModified = data.orderPay.gmtModified
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
              url: this.$http.adornUrl(`/oms/orderpay/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderId': this.dataForm.orderId,
                'paySn': this.dataForm.paySn,
                'payAmount': this.dataForm.payAmount,
                'payTime': this.dataForm.payTime,
                'payType': this.dataForm.payType,
                'payStatus': this.dataForm.payStatus,
                'confirmTime': this.dataForm.confirmTime,
                'callbackContent': this.dataForm.callbackContent,
                'callbackTime': this.dataForm.callbackTime,
                'paySubject': this.dataForm.paySubject,
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
