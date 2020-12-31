<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="秒杀订单超时关闭时间(分)" prop="flashOrderOvertime">
      <el-input v-model="dataForm.flashOrderOvertime" placeholder="秒杀订单超时关闭时间(分)"></el-input>
    </el-form-item>
    <el-form-item label="正常订单超时时间(分)" prop="normalOrderOvertime">
      <el-input v-model="dataForm.normalOrderOvertime" placeholder="正常订单超时时间(分)"></el-input>
    </el-form-item>
    <el-form-item label="发货后自动确认收货时间（天）" prop="confirmOvertime">
      <el-input v-model="dataForm.confirmOvertime" placeholder="发货后自动确认收货时间（天）"></el-input>
    </el-form-item>
    <el-form-item label="自动完成交易时间，不能申请退货（天）" prop="finishOvertime">
      <el-input v-model="dataForm.finishOvertime" placeholder="自动完成交易时间，不能申请退货（天）"></el-input>
    </el-form-item>
    <el-form-item label="订单完成后自动好评时间（天）" prop="commentOvertime">
      <el-input v-model="dataForm.commentOvertime" placeholder="订单完成后自动好评时间（天）"></el-input>
    </el-form-item>
    <el-form-item label="会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】" prop="memberLevel">
      <el-input v-model="dataForm.memberLevel" placeholder="会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】"></el-input>
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
          flashOrderOvertime: '',
          normalOrderOvertime: '',
          confirmOvertime: '',
          finishOvertime: '',
          commentOvertime: '',
          memberLevel: '',
          deleted: '',
          gmtCreate: '',
          gmtModified: ''
        },
        dataRule: {
          flashOrderOvertime: [
            { required: true, message: '秒杀订单超时关闭时间(分)不能为空', trigger: 'blur' }
          ],
          normalOrderOvertime: [
            { required: true, message: '正常订单超时时间(分)不能为空', trigger: 'blur' }
          ],
          confirmOvertime: [
            { required: true, message: '发货后自动确认收货时间（天）不能为空', trigger: 'blur' }
          ],
          finishOvertime: [
            { required: true, message: '自动完成交易时间，不能申请退货（天）不能为空', trigger: 'blur' }
          ],
          commentOvertime: [
            { required: true, message: '订单完成后自动好评时间（天）不能为空', trigger: 'blur' }
          ],
          memberLevel: [
            { required: true, message: '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/oms/ordersetting/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.flashOrderOvertime = data.orderSetting.flashOrderOvertime
                this.dataForm.normalOrderOvertime = data.orderSetting.normalOrderOvertime
                this.dataForm.confirmOvertime = data.orderSetting.confirmOvertime
                this.dataForm.finishOvertime = data.orderSetting.finishOvertime
                this.dataForm.commentOvertime = data.orderSetting.commentOvertime
                this.dataForm.memberLevel = data.orderSetting.memberLevel
                this.dataForm.deleted = data.orderSetting.deleted
                this.dataForm.gmtCreate = data.orderSetting.gmtCreate
                this.dataForm.gmtModified = data.orderSetting.gmtModified
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
              url: this.$http.adornUrl(`/oms/ordersetting/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'flashOrderOvertime': this.dataForm.flashOrderOvertime,
                'normalOrderOvertime': this.dataForm.normalOrderOvertime,
                'confirmOvertime': this.dataForm.confirmOvertime,
                'finishOvertime': this.dataForm.finishOvertime,
                'commentOvertime': this.dataForm.commentOvertime,
                'memberLevel': this.dataForm.memberLevel,
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
