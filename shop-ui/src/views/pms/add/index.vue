<template>
  <div class="app-container">
<!--    <el-steps :active="active" finish-status="success" simple style="margin-top: 20px">-->
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="填写商品信息" ></el-step>
      <el-step title="填写商品促销" ></el-step>
      <el-step title="填写商品属性" ></el-step>
<!--      <el-step title="选择商品关联" ></el-step>-->
    </el-steps>
    <goods-info
      v-show="showStatus[0]"
      v-model="goodsParam"
      :goodsParamData="goodsParam"
      :is-edit="isEdit"
      @nextStep="nextStep">
    </goods-info>
    <goods-promotion
      v-show="showStatus[1]"
      v-model="goodsParam"
      :goodsParamData="goodsParam"
      :is-edit="isEdit"
      @prevStep="prevStep"
      @nextStep="nextStep">
    </goods-promotion>
    <goods-attribute
      v-show="showStatus[2]"
      v-model="goodsParam"
      :goodsParamData="goodsParam"
      :is-edit="isEdit"
      @prevStep="prevStep"
      @finishCommit="finishCommit">
    </goods-attribute>
  </div>
</template>

<script>

  import GoodsInfo from './goodsInfo';
  import GoodsPromotion from './goodsPromotion';
  import GoodsAttribute from './goodsAttribute';
  import {postObj,getGoods,updateGoods} from '@/api/pms/add';

  export default {
    name: "index",
    components: {GoodsInfo,GoodsPromotion,GoodsAttribute},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
          goodsParam: Object.assign({},{
          goodsCategoryId: undefined,
          goodsCategoryName: undefined,
          name: undefined,
          subTitle: undefined,
          brandId: undefined,
          brandName: undefined,
          description: undefined,
          goodsSn: undefined,
          price: undefined,
          originalPrice: undefined,
          stock: 0,
          unit: undefined,
          weight: undefined,
          sort: 0,
          cateParentId: undefined,

          giftPoint: 0,
          giftGrowth: 0,
          usePointLimit: undefined,
          previewStatus: undefined,
          publishStatus: undefined,
          newStatus: undefined,
          recommendStatus: undefined,
          detailTitle: undefined,
          detailDesc: undefined,
          keywords: undefined,
          promotionType: undefined,
          promotionStartTime: undefined,
          promotionEndTime: undefined,
          promotionPrice: undefined,
          serviceIds: undefined,
          pic: undefined,
          albumPics: undefined,

          attributeTypeId: undefined,
          //商品sku库存信息
          skuStockList: [],
          detailHtml: undefined,
          detailMobileHtml: undefined,

          //商品阶梯价格
          goodsLadderList: [{count: 0,discount: 0,price: 0}],
          goodsFullReductionList: [{fullPrice: 0, reducePrice: 0}],

          goodsAttributeValueList: []
        }),
        showStatus: [true, false, false, false],
        active: 0,
      };
    },

    methods: {
      hideAll() {
        for (let i = 0; i < this.showStatus.length; i++) {
          this.showStatus[i] = false;
        }
      },
      prevStep() {
        if (this.active > 0 && this.active < this.showStatus.length) {
          this.active--;
          this.hideAll();
          this.showStatus[this.active] = true;
        }
      },
      nextStep() {
        if (this.active < this.showStatus.length - 1) {
          this.active++;
          this.hideAll();
          this.showStatus[this.active] = true;
        }
      },
      finishCommit(isEdit) {
        this.$confirm('是否要提交该产品', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if(isEdit){
            updateGoods(this.$route.query.id,this.npm).then(() =>{
              this.$message({
                type: 'success',
                message: '提交成功',
                duration:1000
              });
              this.$router.back();
            });
          }else {

            postObj(this.goodsParam).then(() => {
              this.$message({
                type: 'success',
                message: '提交成功',
                duration: 1000
              });
              location.reload();
            });
          }
        });
      }
    },
    created(){
      if(this.isEdit){
        getGoods(this.$route.query.id).then(response=>{
          this.goodsParam = response.data;
          });
      }
    },
  }
</script>

<style scoped>
  .app-container {
    left: 0;
    right: 0;
    width: 850px;
    padding: 35px 35px 15px 35px;
    margin: 20px auto;
  }
</style>


