<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>商品介绍</span>
      </div>
      <el-form ref="goodsForm" :rules="rules" :model="form.goods" label-width="150px">
        <el-form-item label="商品编号" prop="goods_sn">
          <el-input v-model="form.goods.goods_sn"/>
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.goods.name"/>
        </el-form-item>
        <el-form-item label="市场售价" prop="market_price">
          <el-input v-model="form.goods.market_price" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="是否新品" prop="is_new">
          <el-radio-group v-model="form.goods.is_new">
            <el-radio :label="true">新品</el-radio>
            <el-radio :label="false">非新品</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否热卖" prop="is_hot">
          <el-radio-group v-model="form.goods.is_hot">
            <el-radio :label="false">普通</el-radio>
            <el-radio :label="true">热卖</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否在售" prop="is_sale">
          <el-radio-group v-model="form.goods.is_sale">
            <el-radio :label="true">在售</el-radio>
            <el-radio :label="false">未售</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="商品图片">
          <single-upload v-model="form.goods.pic_url"></single-upload>
        </el-form-item>

        <el-form-item label="商品相册">
          <multi-upload v-model="albumPics"></multi-upload>
        </el-form-item>

        <el-form-item label="商品单位">
          <el-input v-model="form.goods.unit" placeholder="件 / 个 / 盒"/>
        </el-form-item>


        <el-form-item label="所属分类">
          <el-cascader :v-model="form.goods.category_id" :options="categoryList" expand-trigger="hover" clearable/>
        </el-form-item>

        <el-form-item label="所属品牌">
          <el-select v-model="form.goods.brand_id" clearable>
            <el-option v-for="item in brandList" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>
        </el-form-item>

        <el-form-item label="商品简介">
          <el-input v-model="form.goods.brief"/>
        </el-form-item>

        <el-form-item label="商品详情">
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
               :model="form"
               ref="specificationForm"
               :inline="true"
      >
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
               :model="form"
               ref="skuForm"
               :inline="true"
      >
        <el-table
          :data="form.sku_list"
          highlight-current-row
          border>
          <el-table-column label="规格名">
            <template slot-scope="scope">
              <el-tag v-for="tag in scope.row.specifications" :key="tag">
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
               :model="form"
               ref="attributeForm"
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

    <div class="op-container">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleSubmit">上架</el-button>
    </div>

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

  import {goodsAdd} from '@/api/pms/goods'

  export default {
    components: {SingleUpload, MultiUpload, MiniCardUpload, Editor},
    data() {
      return {
        form: {
          goods: {
            album: ''
          }, // 商品
          spec_list: [{}],  // 商品规格列表
          sku_list: [], // 商品sku列表
          attribute_list: [] // 商品属性列表
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
    computed: {
      albumPics: {
        get: function () {
          if (!this.form.goods.album) {
            return []
          }
          let a = JSON.parse(this.form.goods.album)
          return a
        },
        set: function (newValue, oldValue) {
          if (newValue === oldValue) return
          this.form.goods.album = JSON.stringify(newValue)
          console.log(this.form.goods.album)
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
        this.handleSpecToSku()
      },
      handleSpecificationValueChange(row, index) {
        if (!row.value) {
          return
        }
        for (let i = 0; i < this.form.spec_list.length; i++) {
          if (i !== index && row.name === this.form.spec_list[i].name
            && row.value === this.form.spec_list[i].value) {
            this.$message.warning("规格值" + row.value + "已存在，已自动清除")
            this.form.spec_list[index].value = undefined
          }
        }
        this.handleSpecToSku()
      },

      handleSpecToSku() {
        if (this.form.spec_list.length == 0) {
          return
        }
        // 根据specifications创建临时规格列表
        let specValues = []
        let spec = this.form.spec_list[0].name
        let values = []
        values.push(0)

        for (let i = 1; i < this.form.spec_list.length; i++) {
          const currSpec = this.form.spec_list[i].name
          if (currSpec === spec) {
            values.push(i)
          } else {
            specValues.push(values)
            spec = currSpec
            values = []
            values.push(i)
          }
        }
        specValues.push(values)

        // 根据临时规格列表生产货品规格
        // 算法基于 https://blog.csdn.net/tyhj_sf/article/details/53893125
        let skuIndex = 0
        let skuList = []
        let combination = []
        let n = specValues.length
        for (let s = 0; s < n; s++) {
          combination[s] = 0
        }

        let index = 0
        let isContinue = false
        do {
          let specifications = []
          for (let x = 0; x < n; x++) {
            let z = specValues[x][combination[x]]
            specifications.push(this.form.spec_list[z].value)
          }
          skuList[skuIndex] = {id: skuIndex, specifications: specifications, price: 0.00, number: 0, pic_url: ''}
          skuIndex++

          index++
          combination[n - 1] = index
          for (let j = n - 1; j >= 0; j--) {
            if (combination[j] >= specValues[j].length) {
              combination[j] = 0
              index = 0
              if (j - 1 >= 0) {
                combination[j - 1] = combination[j - 1] + 1
              }
            }
          }
          isContinue = false
          for (let p = 0; p < n; p++) {
            if (combination[p] !== 0) {
              isContinue = true
            }
          }
        } while (isContinue)
        this.form.sku_list = skuList
      },
      handleAddAttribute() {
        this.form.attribute_list.push({})
      },
      handleDeleteAttribute() {
        this.form.attribute_list.splice(index, 1)
      },
      handleCancel: function () {
        this.$router.push({path: '/pms/goods/index'})
      },
      handleSubmit: function () {
        goodsAdd(this.form).then(response => {
          if (response.code === 0) {
            this.$notify.success({
              title: '成功',
              message: '创建成功'
            })
            this.$router.push({path: '/pms/goods'})
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

  .el-tag + .el-tag {
    margin-left: 10px;
  }
</style>
