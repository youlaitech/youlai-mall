<template>
  <div class="app-container">
    <el-form :model="form" :rules="rules" ref="goodsInfoForm" label-width="120px" style="margin:0 50px;width: 600px" size="small">
      <el-form-item label="商品分类：" prop="goodsCategoryId">
        <el-cascader
          v-model="selectGoodsCateValue"
          :options="goodsCateOptions">
        </el-cascader>
      </el-form-item>
      <el-form-item label="商品名称：" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="副标题：" prop="subTitle">
        <el-input v-model="form.subTitle"></el-input>
      </el-form-item>
      <el-form-item label="商品品牌：" prop="brandId">
        <el-select
          v-model="form.brandId"
          @change="handleBrandChange"
          placeholder="请选择品牌">
          <el-option
            v-for="item in brandOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品介绍：" prop="description">
        <el-input
          :autoSize="true"
          v-model="form.description"
          type="textarea"
          placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item label="商品货号："  prop="goodsSn">
        <el-input v-model="form.goodsSn"></el-input>
      </el-form-item>
      <el-form-item label="商品售价：" prop="price">
        <el-input v-model="form.price"></el-input>
      </el-form-item>
      <el-form-item label="市场价：" prop="originalPrice">
        <el-input v-model="form.originalPrice"></el-input>
      </el-form-item>
      <el-form-item label="商品库存：" prop="stock">
        <el-input v-model="form.stock"></el-input>
      </el-form-item>
      <el-form-item label="计量单位：" prop="unit">
        <el-input v-model="form.unit"></el-input>
      </el-form-item>
      <el-form-item label="商品重量：" prop="weight">
        <el-input v-model="form.weight" style="width: 300px"></el-input>
        <span style="margin-left: 20px">克</span>
      </el-form-item>
      <el-form-item label="排序：" prop="sort">
        <el-input v-model="form.sort"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" size="medium" @click="handleNext('goodsInfoForm')">下一步，填写商品促销</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {list as fetchBrandList} from '@/api/pms/brand'
  import {getObj,treeSelect} from '@/api/pms/category'

  export default {
    name: "goodsInfo",
    props: {
      goodsParamData: Object,
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    methods: {
      handleEditCreated(){
        if(this.form.goodsCategoryId!=null){
          getObj(this.form.goodsCategoryId).then(response => {
            this.selectGoodsCateValue.push(response.data.parentId+'');
            this.selectGoodsCateValue.push(this.form.goodsCategoryId+'');
          });
        }
        this.hasEditCreated=true;
      },
      getGoodsCateList() {
        treeSelect().then(response => {
          let list = response.data;
          this.goodsCateOptions = [];
          for (let i = 0; i < list.length; i++) {
            let children = [];
            if (list[i].children != null && list[i].children.length > 0) {
              for (let j = 0; j < list[i].children.length; j++) {
                children.push({label: list[i].children[j].label, value: list[i].children[j].id});
              }
            }
            if(list[i].id != 0){
              this.goodsCateOptions.push({label: list[i].label, value: list[i].id, children: children});
            }
          }
        });
      },
      getBrandList() {
        fetchBrandList().then(response => {
          this.brandOptions = [];
          let brandList = response.data;
          for (let i = 0; i < brandList.length; i++) {
            this.brandOptions.push({label: brandList[i].brandName, value: brandList[i].brandId});
          }
        });
      },
      getCateNameById(id){
        let name=null;
        for(let i=0;i<this.goodsCateOptions.length;i++){
          for(let j=0;j<this.goodsCateOptions[i].children.length;j++){
            if(this.goodsCateOptions[i].children[j].value===id){
              name = this.goodsCateOptions[i].children[j].label;
              return name;
            }
          }
        }
        return name;
      },

      handleNext(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$emit('nextStep');
          } else {
            this.$message({
              message: '验证失败',
              type: 'error',
              duration:1000
            });
            return false;
          }
        });
      },
      handleBrandChange(val) {
        let brandName = '';
        for (let i = 0; i < this.brandOptions.length; i++) {
          if (this.brandOptions[i].value === val) {
            brandName = this.brandOptions[i].label;
            break;
          }
        }
        this.form.brandName = brandName;
      }
    },
    data() {
      return {
        hasEditCreated:false,
        form:this.goodsParamData,
        selectGoodsCateValue: [],
        goodsCateOptions: [],
        brandOptions: [],
        rules: {
          name: [{required: true, message: '请输入商品名称', trigger: 'blur'},
                {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}],
          subTitle: [{required: true, message: '请输入商品副标题', trigger: 'blur'}],
          // goodsCategoryId: [{required: true, message: '请选择商品分类', trigger: 'blur'}],
          brandId: [{required: true, message: '请选择商品品牌', trigger: 'blur'}],
          description: [{required: true, message: '请输入商品介绍', trigger: 'blur'}],
          sort: [{
            type: 'number', message: '排序必须为数字',trigger: 'blur',
            // 在rules 中验证这个功能的时候，对填写的数值进行判断
            transform (value) {
              return _.toNumber(value)
            }
          }],
        }
      };
    },
    created() {
      this.getGoodsCateList();
      this.getBrandList();
    },
    computed:{
      //商品的编号
      goodsId(){
        return this.form.id;
      }
    },
    watch: {
      goodsParamData:function(newValue) {
        console.log(newValue)
        this.form = newValue;
      },
      goodsId:function(newValue){
        if(!this.isEdit)return;
        if(this.hasEditCreated)return;
        if(newValue===undefined||newValue==null||newValue===0)return;
        this.handleEditCreated();
      },
      selectGoodsCateValue: function (value) {
        console.log(this.selectGoodsCateValue);
        if (value != null && value.length === 2) {
          this.form.goodsCategoryId = value[1];
          this.form.goodsCategoryName = this.getCateNameById(this.form.goodsCategoryId);
        } else {
          this.form.goodsCategoryId = null;
          this.form.goodsCategoryName = null;
        }
      },
    },


  }
</script>

<style scoped>
</style>


