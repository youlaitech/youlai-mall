<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="order_id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="order_id"></el-input>
    </el-form-item>
    <el-form-item label="商品sku id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="商品sku id"></el-input>
    </el-form-item>
    <el-form-item label="商品sku编号" prop="skuCode">
      <el-input v-model="dataForm.skuCode" placeholder="商品sku编号"></el-input>
    </el-form-item>
    <el-form-item label="商品sku名字" prop="skuName">
      <el-input v-model="dataForm.skuName" placeholder="商品sku名字"></el-input>
    </el-form-item>
    <el-form-item label="商品sku图片" prop="skuPic">
      <el-input v-model="dataForm.skuPic" placeholder="商品sku图片"></el-input>
    </el-form-item>
    <el-form-item label="商品sku价格(分)" prop="skuPrice">
      <el-input v-model="dataForm.skuPrice" placeholder="商品sku价格(分)"></el-input>
    </el-form-item>
    <el-form-item label="商品购买的数量" prop="skuQuantity">
      <el-input v-model="dataForm.skuQuantity" placeholder="商品购买的数量"></el-input>
    </el-form-item>
    <el-form-item label="商品sku总价格(分)" prop="skuTotalPrice">
      <el-input v-model="dataForm.skuTotalPrice" placeholder="商品sku总价格(分)"></el-input>
    </el-form-item>
    <el-form-item label="spu_id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spu_id"></el-input>
    </el-form-item>
    <el-form-item label="spu_name" prop="spuName">
      <el-input v-model="dataForm.spuName" placeholder="spu_name"></el-input>
    </el-form-item>
    <el-form-item label="spu_pic" prop="spuPic">
      <el-input v-model="dataForm.spuPic" placeholder="spu_pic"></el-input>
    </el-form-item>
    <el-form-item label="品牌id" prop="brandId">
      <el-input v-model="dataForm.brandId" placeholder="品牌id"></el-input>
    </el-form-item>
    <el-form-item label="品牌名称" prop="brandName">
      <el-input v-model="dataForm.brandName" placeholder="品牌名称"></el-input>
    </el-form-item>
    <el-form-item label="商品分类id" prop="categoryId">
      <el-input v-model="dataForm.categoryId" placeholder="商品分类id"></el-input>
    </el-form-item>
    <el-form-item label="商品分类名称" prop="categoryName">
      <el-input v-model="dataForm.categoryName" placeholder="商品分类名称"></el-input>
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
          skuId: '',
          skuCode: '',
          skuName: '',
          skuPic: '',
          skuPrice: '',
          skuQuantity: '',
          skuTotalPrice: '',
          spuId: '',
          spuName: '',
          spuPic: '',
          brandId: '',
          brandName: '',
          categoryId: '',
          categoryName: '',
          deleted: '',
          gmtCreate: '',
          gmtModified: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: 'order_id不能为空', trigger: 'blur' }
          ],
          skuId: [
            { required: true, message: '商品sku id不能为空', trigger: 'blur' }
          ],
          skuCode: [
            { required: true, message: '商品sku编号不能为空', trigger: 'blur' }
          ],
          skuName: [
            { required: true, message: '商品sku名字不能为空', trigger: 'blur' }
          ],
          skuPic: [
            { required: true, message: '商品sku图片不能为空', trigger: 'blur' }
          ],
          skuPrice: [
            { required: true, message: '商品sku价格(分)不能为空', trigger: 'blur' }
          ],
          skuQuantity: [
            { required: true, message: '商品购买的数量不能为空', trigger: 'blur' }
          ],
          skuTotalPrice: [
            { required: true, message: '商品sku总价格(分)不能为空', trigger: 'blur' }
          ],
          spuId: [
            { required: true, message: 'spu_id不能为空', trigger: 'blur' }
          ],
          spuName: [
            { required: true, message: 'spu_name不能为空', trigger: 'blur' }
          ],
          spuPic: [
            { required: true, message: 'spu_pic不能为空', trigger: 'blur' }
          ],
          brandId: [
            { required: true, message: '品牌id不能为空', trigger: 'blur' }
          ],
          brandName: [
            { required: true, message: '品牌名称不能为空', trigger: 'blur' }
          ],
          categoryId: [
            { required: true, message: '商品分类id不能为空', trigger: 'blur' }
          ],
          categoryName: [
            { required: true, message: '商品分类名称不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/oms/ordergoods/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.orderGoods.orderId
                this.dataForm.skuId = data.orderGoods.skuId
                this.dataForm.skuCode = data.orderGoods.skuCode
                this.dataForm.skuName = data.orderGoods.skuName
                this.dataForm.skuPic = data.orderGoods.skuPic
                this.dataForm.skuPrice = data.orderGoods.skuPrice
                this.dataForm.skuQuantity = data.orderGoods.skuQuantity
                this.dataForm.skuTotalPrice = data.orderGoods.skuTotalPrice
                this.dataForm.spuId = data.orderGoods.spuId
                this.dataForm.spuName = data.orderGoods.spuName
                this.dataForm.spuPic = data.orderGoods.spuPic
                this.dataForm.brandId = data.orderGoods.brandId
                this.dataForm.brandName = data.orderGoods.brandName
                this.dataForm.categoryId = data.orderGoods.categoryId
                this.dataForm.categoryName = data.orderGoods.categoryName
                this.dataForm.deleted = data.orderGoods.deleted
                this.dataForm.gmtCreate = data.orderGoods.gmtCreate
                this.dataForm.gmtModified = data.orderGoods.gmtModified
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
              url: this.$http.adornUrl(`/oms/ordergoods/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderId': this.dataForm.orderId,
                'skuId': this.dataForm.skuId,
                'skuCode': this.dataForm.skuCode,
                'skuName': this.dataForm.skuName,
                'skuPic': this.dataForm.skuPic,
                'skuPrice': this.dataForm.skuPrice,
                'skuQuantity': this.dataForm.skuQuantity,
                'skuTotalPrice': this.dataForm.skuTotalPrice,
                'spuId': this.dataForm.spuId,
                'spuName': this.dataForm.spuName,
                'spuPic': this.dataForm.spuPic,
                'brandId': this.dataForm.brandId,
                'brandName': this.dataForm.brandName,
                'categoryId': this.dataForm.categoryId,
                'categoryName': this.dataForm.categoryName,
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
