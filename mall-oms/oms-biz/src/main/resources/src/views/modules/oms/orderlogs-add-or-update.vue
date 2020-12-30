<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="订单id"></el-input>
    </el-form-item>
    <el-form-item label="操作人[用户；系统；后台管理员]" prop="user">
      <el-input v-model="dataForm.user" placeholder="操作人[用户；系统；后台管理员]"></el-input>
    </el-form-item>
    <el-form-item label="操作详情" prop="detail">
      <el-input v-model="dataForm.detail" placeholder="操作详情"></el-input>
    </el-form-item>
    <el-form-item label="操作时订单状态" prop="orderStatus">
      <el-input v-model="dataForm.orderStatus" placeholder="操作时订单状态"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
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
          user: '',
          detail: '',
          orderStatus: '',
          remark: '',
          deleted: '',
          gmtCreate: '',
          gmtModified: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: '订单id不能为空', trigger: 'blur' }
          ],
          user: [
            { required: true, message: '操作人[用户；系统；后台管理员]不能为空', trigger: 'blur' }
          ],
          detail: [
            { required: true, message: '操作详情不能为空', trigger: 'blur' }
          ],
          orderStatus: [
            { required: true, message: '操作时订单状态不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/oms/orderlogs/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.orderLogs.orderId
                this.dataForm.user = data.orderLogs.user
                this.dataForm.detail = data.orderLogs.detail
                this.dataForm.orderStatus = data.orderLogs.orderStatus
                this.dataForm.remark = data.orderLogs.remark
                this.dataForm.deleted = data.orderLogs.deleted
                this.dataForm.gmtCreate = data.orderLogs.gmtCreate
                this.dataForm.gmtModified = data.orderLogs.gmtModified
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
              url: this.$http.adornUrl(`/oms/orderlogs/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderId': this.dataForm.orderId,
                'user': this.dataForm.user,
                'detail': this.dataForm.detail,
                'orderStatus': this.dataForm.orderStatus,
                'remark': this.dataForm.remark,
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
