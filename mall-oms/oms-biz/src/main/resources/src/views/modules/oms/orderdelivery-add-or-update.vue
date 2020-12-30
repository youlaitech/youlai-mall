<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="订单id"></el-input>
    </el-form-item>
    <el-form-item label="物流公司(配送方式)" prop="deliveryCompany">
      <el-input v-model="dataForm.deliveryCompany" placeholder="物流公司(配送方式)"></el-input>
    </el-form-item>
    <el-form-item label="物流单号" prop="deliverySn">
      <el-input v-model="dataForm.deliverySn" placeholder="物流单号"></el-input>
    </el-form-item>
    <el-form-item label="收货人姓名" prop="receiverName">
      <el-input v-model="dataForm.receiverName" placeholder="收货人姓名"></el-input>
    </el-form-item>
    <el-form-item label="收货人电话" prop="receiverPhone">
      <el-input v-model="dataForm.receiverPhone" placeholder="收货人电话"></el-input>
    </el-form-item>
    <el-form-item label="收货人邮编" prop="receiverPostCode">
      <el-input v-model="dataForm.receiverPostCode" placeholder="收货人邮编"></el-input>
    </el-form-item>
    <el-form-item label="省份/直辖市" prop="receiverProvince">
      <el-input v-model="dataForm.receiverProvince" placeholder="省份/直辖市"></el-input>
    </el-form-item>
    <el-form-item label="城市" prop="receiverCity">
      <el-input v-model="dataForm.receiverCity" placeholder="城市"></el-input>
    </el-form-item>
    <el-form-item label="区" prop="receiverRegion">
      <el-input v-model="dataForm.receiverRegion" placeholder="区"></el-input>
    </el-form-item>
    <el-form-item label="详细地址" prop="receiverDetailAddress">
      <el-input v-model="dataForm.receiverDetailAddress" placeholder="详细地址"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="物流状态【0->运输中；1->已收货】" prop="deliveryStatus">
      <el-input v-model="dataForm.deliveryStatus" placeholder="物流状态【0->运输中；1->已收货】"></el-input>
    </el-form-item>
    <el-form-item label="发货时间" prop="deliveryTime">
      <el-input v-model="dataForm.deliveryTime" placeholder="发货时间"></el-input>
    </el-form-item>
    <el-form-item label="确认收货时间" prop="receiveTime">
      <el-input v-model="dataForm.receiveTime" placeholder="确认收货时间"></el-input>
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
          deliveryCompany: '',
          deliverySn: '',
          receiverName: '',
          receiverPhone: '',
          receiverPostCode: '',
          receiverProvince: '',
          receiverCity: '',
          receiverRegion: '',
          receiverDetailAddress: '',
          remark: '',
          deliveryStatus: '',
          deliveryTime: '',
          receiveTime: '',
          deleted: '',
          gmtCreate: '',
          gmtModified: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: '订单id不能为空', trigger: 'blur' }
          ],
          deliveryCompany: [
            { required: true, message: '物流公司(配送方式)不能为空', trigger: 'blur' }
          ],
          deliverySn: [
            { required: true, message: '物流单号不能为空', trigger: 'blur' }
          ],
          receiverName: [
            { required: true, message: '收货人姓名不能为空', trigger: 'blur' }
          ],
          receiverPhone: [
            { required: true, message: '收货人电话不能为空', trigger: 'blur' }
          ],
          receiverPostCode: [
            { required: true, message: '收货人邮编不能为空', trigger: 'blur' }
          ],
          receiverProvince: [
            { required: true, message: '省份/直辖市不能为空', trigger: 'blur' }
          ],
          receiverCity: [
            { required: true, message: '城市不能为空', trigger: 'blur' }
          ],
          receiverRegion: [
            { required: true, message: '区不能为空', trigger: 'blur' }
          ],
          receiverDetailAddress: [
            { required: true, message: '详细地址不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          deliveryStatus: [
            { required: true, message: '物流状态【0->运输中；1->已收货】不能为空', trigger: 'blur' }
          ],
          deliveryTime: [
            { required: true, message: '发货时间不能为空', trigger: 'blur' }
          ],
          receiveTime: [
            { required: true, message: '确认收货时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/oms/orderdelivery/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.orderDelivery.orderId
                this.dataForm.deliveryCompany = data.orderDelivery.deliveryCompany
                this.dataForm.deliverySn = data.orderDelivery.deliverySn
                this.dataForm.receiverName = data.orderDelivery.receiverName
                this.dataForm.receiverPhone = data.orderDelivery.receiverPhone
                this.dataForm.receiverPostCode = data.orderDelivery.receiverPostCode
                this.dataForm.receiverProvince = data.orderDelivery.receiverProvince
                this.dataForm.receiverCity = data.orderDelivery.receiverCity
                this.dataForm.receiverRegion = data.orderDelivery.receiverRegion
                this.dataForm.receiverDetailAddress = data.orderDelivery.receiverDetailAddress
                this.dataForm.remark = data.orderDelivery.remark
                this.dataForm.deliveryStatus = data.orderDelivery.deliveryStatus
                this.dataForm.deliveryTime = data.orderDelivery.deliveryTime
                this.dataForm.receiveTime = data.orderDelivery.receiveTime
                this.dataForm.deleted = data.orderDelivery.deleted
                this.dataForm.gmtCreate = data.orderDelivery.gmtCreate
                this.dataForm.gmtModified = data.orderDelivery.gmtModified
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
              url: this.$http.adornUrl(`/oms/orderdelivery/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderId': this.dataForm.orderId,
                'deliveryCompany': this.dataForm.deliveryCompany,
                'deliverySn': this.dataForm.deliverySn,
                'receiverName': this.dataForm.receiverName,
                'receiverPhone': this.dataForm.receiverPhone,
                'receiverPostCode': this.dataForm.receiverPostCode,
                'receiverProvince': this.dataForm.receiverProvince,
                'receiverCity': this.dataForm.receiverCity,
                'receiverRegion': this.dataForm.receiverRegion,
                'receiverDetailAddress': this.dataForm.receiverDetailAddress,
                'remark': this.dataForm.remark,
                'deliveryStatus': this.dataForm.deliveryStatus,
                'deliveryTime': this.dataForm.deliveryTime,
                'receiveTime': this.dataForm.receiveTime,
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
