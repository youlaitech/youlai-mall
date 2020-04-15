<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>商品介绍</span>
      </div>
      <el-form
        ref="spuForm"
        :rules="rules"
        :model="form" label-width="150px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.goods.name"/>
        </el-form-item>
        <el-form-item label="商品副标题" prop="name">
          <el-input v-model="form.goods.name"/>
        </el-form-item>
        <el-form-item label="是否新品" prop="is_new">
          <el-radio-group v-model="form.goods.is_new">
            <el-radio :label="0">非新品</el-radio>
            <el-radio :label="1">新品</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否热卖" prop="is_hot">
          <el-radio-group v-model="form.goods.is_hot">
            <el-radio :label="0">普通</el-radio>
            <el-radio :label="1">热卖</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否上架" prop="status">
          <el-radio-group v-model="form.goods.status">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="商品图片" prop="pic_url">
          <single-upload v-model="form.goods.pic_url"></single-upload>
        </el-form-item>

        <el-form-item label="商品图册" prop="pic_urls">
          <multi-upload v-model="form.goods.pic_urls"></multi-upload>
        </el-form-item>

        <el-form-item label="商品单位" prop="unit">
          <el-input v-model="form.goods.unit" placeholder="件 / 个 / 盒"/>
        </el-form-item>

        <el-form-item label="所属分类" prop="category_id">
          <el-cascader :v-model="form.goods.category_id" :options="categoryList" expand-trigger="hover" clearable/>
        </el-form-item>

        <el-form-item label="所属品牌" prop="brand_id">
          <el-select v-model="form.goods.brand_id" clearable>
            <el-option v-for="item in brandList" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>
        </el-form-item>

        <el-form-item label="商品描述" prop="description">
          <el-input v-model="form.goods.description"/>
        </el-form-item>

        <el-form-item label="商品详情" prop="detail">
          <editor v-model="form.goods.detail" :init="editorInit"/>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>规格</span>
        <el-button style="float: right; " type="primary" size="small"
                   @click="handleAddSpecification">添加规格
        </el-button>
      </div>
      <el-form size="small"
               ref="specForm"
               :model="form"
               :inline="true">
        <el-table
          :data="form.spec_list"
          highlight-current-row
          border>
          <el-table-column label="规格名">
            <template slot-scope="scope">
              <el-form-item :prop="'spec_list[' + scope.$index + '].name'">
                <el-input v-model="scope.row.name" @input="handleSpecificationNameChange"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="规格值">
            <template slot-scope="scope">
              <el-form-item :prop="'spec_list[' + scope.$index + '].value'">
                <el-input v-model="scope.row.value"
                          @input="handleSpecificationValueChange(scope.row,scope.$index)"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="规格图片">
            <template slot-scope="scope">
              <el-form-item :prop="'spec_list[' + scope.$index + '].pic_url'">
                <mini-card-upload v-model="scope.row.pic_url"></mini-card-upload>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-form-item>
                <el-button type="danger" @click="handleDeleteSpecification(scope.$index)">删除</el-button>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>商品库存</span>
      </div>

      <el-form size="small"
               ref="skuForm"
               :model="form"
               :inline="true"
      >
        <el-table
          :data="form.sku_list"
          highlight-current-row
          border>
          <el-table-column label="规格名">
            <template slot-scope="scope">
              <el-tag v-for="tag in scope.row.specs" :key="tag">
                {{ tag }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column property="price" label="货品售价">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].price'">
                <el-input v-model="scope.row.value"
                          @input="handleSpecificationValueChange(scope.row,scope.$index)"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column property="number" label="货品数量">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].number'">
                <el-input v-model="scope.row.value"
                          @input="handleSpecificationValueChange(scope.row,scope.$index)"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column property="url" label="货品图片">
            <template slot-scope="scope">
              <el-form-item :prop="'sku_list[' + scope.$index + '].pic_url'">
                <mini-card-upload v-model="scope.row.pic_url"></mini-card-upload>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>商品属性</span>
        <el-button style="float: right; " type="primary" size="small"
                   @click="handleAddAttribute">添加属性
        </el-button>
      </div>
      <el-form size="small"
               ref="attributeForm"
               :model="form"
               :inline="true"
      >
        <el-table
          :data="form.attribute_list"
          highlight-current-row
          border>

          <el-table-column property="name" label="商品参数名称">
            <template slot-scope="scope">
              <el-form-item :prop="'attribute_list[' + scope.$index + '].name'">
                <el-input v-model="scope.row.name"></el-input>
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column property="value" label="商品参数值">
            <template slot-scope="scope">
              <el-form-item :prop="'attribute_list[' + scope.$index + '].value'">
                <el-input v-model="scope.row.value"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-form-item>
                <el-button type="danger" @click="handleDeleteAttribute(scope.$index)">删除</el-button>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </el-card>

    <el-card class="box-card" style="text-align: right">
      <el-button @click="handleCancel">取 消</el-button>
      <el-button type="primary" @click="handleSubmit">提交</el-button>
    </el-card>
  </div>
</template>

<script>
  import {categoryCascadeList} from '@/api/pms/category'
  import {brandList} from '@/api/pms/brand'
  import SingleUpload from '@/components/Upload/singleUpload'
  import MiniCardUpload from '@/components/Upload/miniCardUpload'
  import MultiUpload from '@/components/Upload/multiUpload'
  import Editor from '@tinymce/tinymce-vue'
  import {fileUpload} from '@/api/fms'
  import {spuAdd} from '@/api/pms/spu'

  export default {
    components: {SingleUpload, MultiUpload, MiniCardUpload, Editor},
    data() {
      return {
        form: {
          goods: {
            is_new:0,
            is_hot:0,
            status: 1,
            pic_urls: []
          }, // 商品
          spec_list: [{}],  // 商品规格列表
          sku_list: [], // 商品sku列表
          attribute_list: [] // 商品属性列表
        },
        categoryList: [], //商品分类列表
        brandList: [], // 商品品牌列表
        rules: {
          name: [{required: true, message: '商品名称不能为空', trigger: 'blur'}]
        },
        editorInit: {
          language: 'zh_CN',
          height: 500,
          convert_urls: false,
          plugins: ['advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount'],
          toolbar: ['searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample', 'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'],
          images_upload_handler: function (blobInfo, success, failure) {
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
      handleAddSpecification() {
        this.form.spec_list.push({})
      },
      handleDeleteSpecification(index) {
        this.form.spec_list.splice(index, 1)
      },
      handleSpecificationNameChange(newValue) {
        this.handleGenerateSku()
      },
      handleSpecificationValueChange(row, index) {
        if (!row.value) {
          return
        }
        for (let i = 0; i < this.form.spec_list.length; i++) {
          if (i !== index && row.name === this.form.spec_list[i].name
            && row.value === this.form.spec_list[i].value) {
            this.$message.warning("规格值" + row.value + "已存在")
            this.form.spec_list[index].value = undefined
          }
        }
        this.handleGenerateSku()
      },

      handleCartesian() { //笛卡尔积运算
        return Array.prototype.reduce.call(arguments, function (a, b) {
          let result = [];
          a.forEach(function (a) {
            b.forEach(function (b) {
              result.push(a.concat([b]));
            });
          });
          return result;
        }, [[]]);
      },

      handleGenerateSku() { // 生成sku列表
        let specList = this.form.spec_list
        if (specList.length == 0) {
          return
        }
        // 根据specifications创建临时规格列表
        // [
        //    {'name':'颜色','value':'蓝色'},
        //    {'name':'颜色','value':'黑色'},
        //    {'name':'颜色','value':'玫瑰金'} ,
        //    {'name':'内存','value':'4G'},
        //    {'name':'内存','value':'6G'},
        //    {'name':'内存','value':'8G'},
        //    {'name':'存储','value':'32G'},
        //    {'name':'存储','value':'64G'},
        //    {'name':'存储','value':'128G'}
        // ]
        //    ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        // [['蓝色','黑色','玫瑰金'],['4G','6G','8G'],['32G','64G','128G']]

        let keyList = Array.from(new Set(specList.map(item => item.name)))

        let valueList = []
        keyList.forEach(key => {
          let value = []
          specList.forEach(spec => {
            if (spec.name === key) {
              value.push(spec.value)
            }
          })
          valueList.push(value)
        })
        let skuList = []
        this.handleCartesian(...valueList).forEach(item => {
          skuList.push({specs: item})
        })
        this.form.sku_list = skuList
      },
      handleAddAttribute() {
        this.form.attribute_list.push({})
      },
      handleDeleteAttribute(index) {
        this.form.attribute_list.splice(index, 1)
      },
      handleCancel: function () {
        this.$router.push({path: '/pms/spu'})
      },
      handleSubmit: function () {
        console.log(this.form.goods.pic_urls)
        return
        spuAdd(this.form).then(response => {
          if (response.code === 0) {
            this.$notify.success({
              title: '成功',
              message: '创建成功'
            })
            this.$router.push({path: '/pms/spu'})
          }
        })
      }
    }
  }

</script>
<style lang="less" scoped>
  .app-container {
    padding-bottom: 50px;
  }

  .box-card + {
    margin-top: 10px;
  }

  .el-tag + .el-tag {
    margin-left: 10px;
  }
</style>
