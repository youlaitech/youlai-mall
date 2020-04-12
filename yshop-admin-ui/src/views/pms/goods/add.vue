<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>商品介绍</span>
      </div>
      <el-form ref="goods" :rules="rules" :model="goods" label-width="150px">
        <el-form-item label="商品编号" prop="goodsSn">
          <el-input v-model="goods.goodsSn"/>
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="goods.name"/>
        </el-form-item>
        <el-form-item label="市场售价" prop="market_price">
          <el-input v-model="goods.market_price" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="是否新品" prop="is_new">
          <el-radio-group v-model="goods.is_new">
            <el-radio :label="true">新品</el-radio>
            <el-radio :label="false">非新品</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否热卖" prop="is_hot">
          <el-radio-group v-model="goods.is_hot">
            <el-radio :label="false">普通</el-radio>
            <el-radio :label="true">热卖</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否在售" prop="is_sale">
          <el-radio-group v-model="goods.is_sale">
            <el-radio :label="true">在售</el-radio>
            <el-radio :label="false">未售</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="商品图片">
          <el-upload
            :action="uploadAction"
            :show-file-list="false"
            :headers="headers"
            :on-success="handleUploadPicSuccess"
            class="avatar-uploader"
            accept=".jpg,.jpeg,.png,.gif"
            :auto-upload="true"
          >
            <img v-if="goods.pic_url" :src="goods.pic_url" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"/>
          </el-upload>
        </el-form-item>

        <el-form-item label="商品相册">
          <el-upload
            :action="uploadAction"
            :limit="5"
            :headers="headers"
            :on-exceed="handleExceed"
            :on-success="handleUploadGallerySuccess"
            :on-remove="handleRemove"
            multiple
            accept=".jpg,.jpeg,.png,.gif"
            list-type="picture-card"
            :auto-upload="true"
          >
            <i class="el-icon-plus"/>
          </el-upload>
        </el-form-item>

        <el-form-item label="商品单位">
          <el-input v-model="goods.unit" placeholder="件 / 个 / 盒"/>
        </el-form-item>


        <el-form-item label="所属分类">
          <el-cascader :options="categoryList" expand-trigger="hover" clearable @change="handleCategoryChange"/>
        </el-form-item>

        <el-form-item label="所属品牌">
          <el-select v-model="goods.brandId" clearable>
            <el-option v-for="item in brandList" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>

        <el-form-item label="商品简介">
          <el-input v-model="goods.brief"/>
        </el-form-item>

        <el-form-item label="商品详细介绍">
          <tinymce :width="595" :height="300" v-model="goods.detail"></tinymce>
        </el-form-item>
      </el-form>
    </el-card>

  </div>
</template>

<script>
  import {getToken} from '@/utils/auth'
  import Tinymce from '@/components/Tinymce'
  import {deleteFile} from '@/api/fms'

  export default {
    name: "add",
    components: {
      Tinymce
    },
    data() {
      return {

        goods: {pic_url: '', gallery: [], is_hot: false, is_new: true, is_sale: true},
        rules: {
          goodsSn: [{required: true, message: '商品编号不能为空', trigger: 'blur'}],
          name: [{required: true, message: '商品名称不能为空', trigger: 'blur'}]
        },

        uploadAction: this.uploadAction,
        headers: {authorization: getToken()},

      }
    },
    methods:
      {
        handleExceed() {
          this.$message.error('上传文件个数超出限制!最多上传5张图片!')
        },
        handleUploadPicSuccess(response) {
          if (response.code === 0) {
            this.goods.pic_url = response.data.filePath
          }
        },
        handleUploadGallerySuccess(response) {
          if (response.code === 0) {
            this.goods.gallery.push(response.data.filePath)
          }
        },
        handleRemove(file) {
          deleteFile(file.response.data.filePath).then(() => {
            for (let i = 0; i < this.goods.gallery.length; i++) {
              let filePath = file.response.data.filePath
              if (this.goods.gallery[i] === filePath) {
                this.goods.gallery.splice(i, 1)
              }
            }
          })
        },
      }
  }
</script>

<style scoped>

</style>
