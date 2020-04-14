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
          <multi-upload v-model="albumPics"></multi-upload>
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

        <el-form-item label="商品详情">
          <editor v-model="goods.detail" :init="editorInit" />
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>商品规格</span>
        <el-button style="float: right; " type="primary"  size="small"
                   @click="onAddSpecificationClick">添加规格</el-button>
      </div>
      <el-form size="small"
               id="specificationForm"
               :model="goods"
               ref="specificationForm"
      >
      <el-table
        :data="goods.specificationList"
        highlight-current-row
        border>

        <el-table-column label="规格名">
          <template slot-scope="scope">
            <el-form-item :prop="'specificationList[' + scope.$index + '].name'">
                <el-input v-model="scope.row.name"></el-input>
            </el-form-item>
          </template>
        </el-table-column>

        <el-table-column label="规格值">
          <template slot-scope="scope">
            <el-form-item :prop="'specificationList[' + scope.$index + '].value'">
              <el-input v-model="scope.row.value"></el-input>
            </el-form-item>
          </template>
        </el-table-column>

        <el-table-column label="规格图片">
          <template slot-scope="scope">
            <el-form-item :prop="'specificationList[' + scope.$index + '].pic_url'">
              <single-upload v-model="scope.row.pic_url"></single-upload>
            </el-form-item>
          </template>
        </el-table-column>

      </el-table>
      </el-form>
    </el-card>

  </div>
</template>

<script>
  import {categoryCascadeList} from '@/api/pms/category'
  import {brandList} from '@/api/pms/brand'

  import SingleUpload from '@/components/Upload/singleUpload'
  import MultiUpload from '@/components/Upload/multiUpload'

  import Editor from '@tinymce/tinymce-vue'
  import {fileUpload} from  '@/api/fms'

  export default {
    components: {SingleUpload, MultiUpload, Editor},
    data() {
      return {
        goods: {
          pic_url: '',
          gallery: [],
          is_hot: false,
          is_new: true,
          is_sale: true,
          album : undefined,
          specificationList:[{

          }]
        },
        categoryList: [],
        brandList: [],
        rules: {
          goods_sn: [{required: true, message: '商品编号不能为空', trigger: 'blur'}],
          name: [{required: true, message: '商品名称不能为空', trigger: 'blur'}]
        },
        editorInit: {
          language: 'zh_CN',
          height: 500,
          convert_urls: false,
          plugins: ['advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount'],
          toolbar: ['searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample', 'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'],
          images_upload_handler: function(blobInfo, success, failure) {
            const formData = new FormData()
            formData.append('file', blobInfo.blob())
            fileUpload(formData).then(response => {
              console.log(response)
              success(response.data.url)
            }).catch(() => {
              failure('上传失败，请重新上传')
            })
          }
        }
      }
    },
    computed:{
      albumPics:{
        get:function () {
          if(!this.goods.album){
            return []
          }
          return JSON.parse(this.goods.album)
        },
        set:function (newValue) {
          this.goods.album=JSON.stringify(newValue)
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

      },
      onAddSpecificationClick(){
        this.specificationList.push({})
      }
    }
  }
</script>
<style lang="less" scoped>
.app-container{
  padding-bottom: 50px;
}
</style>
