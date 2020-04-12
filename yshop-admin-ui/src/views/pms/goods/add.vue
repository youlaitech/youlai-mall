<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>商品介绍</span>
      </div>
      <el-form ref="goods" :rules="rules" :model="goods" label-width="150px">
        <el-form-item label="商品编号" prop="goods_sn">
          <el-input v-model="goods.goods_sn"/>
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
          <single-upload v-model="goods.pic_url"></single-upload>
        </el-form-item>

        <el-form-item label="商品相册">
          <multi-upload v-model="goods.album"></multi-upload>
        </el-form-item>

        <el-form-item label="商品单位">
          <el-input v-model="goods.unit" placeholder="件 / 个 / 盒"/>
        </el-form-item>


        <el-form-item label="所属分类">
          <el-cascader :options="categoryList" expand-trigger="hover" clearable @change="handleCategoryChange"/>
        </el-form-item>

        <el-form-item label="所属品牌">
          <el-select v-model="goods.brand_id" clearable>
            <el-option v-for="item in brandList" :key="item.id" :label="item.name" :value="item.id"/>
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
  import Tinymce from '@/components/Tinymce'
  import {categoryCascadeList} from '@/api/pms/category'
  import {brandList} from '@/api/pms/brand'

  import SingleUpload from '@/components/Upload/singleUpload'
  import MultiUpload from '@/components/Upload/multiUpload'

  export default {
    components: {SingleUpload, MultiUpload, Tinymce},
    data() {
      return {
        goods: {
          pic_url: '',
          gallery: [],
          is_hot: false,
          is_new: true,
          is_sale: true,
          album:undefined
        },
        categoryList: [],
        brandList: [],
        rules: {
          goods_sn: [{required: true, message: '商品编号不能为空', trigger: 'blur'}],
          name: [{required: true, message: '商品名称不能为空', trigger: 'blur'}]
        }
      }
    },
    computed:{

      albumPics:{
        get:function () {

        },
        set:function (newValue) {

        }
      }

    },
    created() {
      this.initData()
    },
    methods: {
      initData() {
        categoryCascadeList().then(response => {
          this.categoryList = response.data
        })
        brandList().then(response => {
          this.brandList = response.data
        })
      },
      handleCategoryChange() {

      }
    }
  }
</script>

<style scoped>

</style>
