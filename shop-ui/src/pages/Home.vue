<template>
  <el-container>
    <el-header>
      <Query
        size="mini"
        labelWidth="80px"
        :itemWidth="7"
        :searchData="queryParam"
        :searchForm="searchForm"
        :searchHandle="searchHandle"
        :list="dropDownList"
      />
    </el-header>

    <div
      class="infinite-list"
      v-infinite-scroll="load"
      style="overflow:auto"
      infinite-scroll-disabled="disabled"
      infinite-scroll-distance="10"
    >
      <el-row>
        <el-col
          :span="colSpan"
          v-for="item in productList"
          :key="item.id"
          class="list-item"
        >
          <el-card shadow="always" @click.native=showDetail(item) class="my-el-card">
            <div class="image">
              <el-image :src="item.coverUrl"></el-image>
            </div>
            <div class="description">
              <span>{{ item.name }}</span>
            </div>

            <div class="price">
              <span>￥{{ item.price }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div>
        <p v-if="loading">加载中...</p>
        <p v-if="noMore">没有更多了</p>
        <el-backtop target=".infinite-list"></el-backtop>
      </div>
      <el-backtop target=".infinite-list"></el-backtop>
    </div>


    <!--    商品详情弹框-->
    <el-dialog  class="my-el-dialog" title="商品详情" v-if="showingProduct" :visible.sync="productDetailShow">
      <div class="image">
        <el-image :src="showingProduct.coverUrl"></el-image>
      </div>
      <div class="description">
        <span>{{ showingProduct.name }}</span>
      </div>

      <div class="price">
        <span>￥{{ showingProduct.price }}</span>
      </div>
      <el-form :inline="true"  class="demo-form-inline">
<!--        <el-form-item label="活动区域">-->
<!--          <el-select v-model="" placeholder="活动区域">-->
<!--            <el-option label="区域一" value="shanghai"></el-option>-->
<!--            <el-option label="区域二" value="beijing"></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="活动区域">-->
<!--          <el-select v-model="" placeholder="活动区域">-->
<!--            <el-option label="区域一" value="shanghai"></el-option>-->
<!--            <el-option label="区域二" value="beijing"></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item>
          <el-button type="primary" @click="addToCart">加入购物车</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </el-container>
</template>

<script>
  import * as product from "@/http/implement/product";
  import {resolvePicFileRes} from "@/utils/fileUtil";

  export default {
    data() {
      return {
        // 商品详情
        // 是否弹出商品详情对话框
        productDetailShow: false,
        // 当前正在展示详情的商品信息
        showingProduct: null,
        cartItem: {},
        stockList:[],
        // el-col栅格占的格子数量。 一行总共24格，一个列占6格，那么一行可展示4个商品
        colSpan: 6,
        searchForm: [
          {
            type: "Input",
            label: "商品名称",
            prop: "name",
            placeholder: "请输入",
            clearable: true
          },
          {
            type: "Cascader",
            label: "商品分类",
            prop: "productCategoryId",
            multiple: false,
            change: value => {
              // 这里需要取value[1]，也就是二级分类。
              this.queryParam.productCategoryId = value[value.length - 1];
            },
            clearable: true,
            // visibleChange: visible => {
            //   if (visible) {
            //     // 下拉框展示时
            //     this.getProductCategory();
            //   }
            // },
            placeholder: "请选择"
          },
          {
            type: "Select",
            label: "商品品牌",
            prop: "brandId",
            multiple: false,
            change: value => {
              this.queryParam.brandId = value;
            },
            clearable: true,
            visibleChange: visible => {
              if (visible) {
                // 下拉框展示时
                this.getBrands();
              }
            },
            placeholder: "请选择"
          }
        ],
        searchHandle: [
          {
            label: "重置",
            page: "productList",
            handle: () => {
              this.queryParam.name = '';
              this.queryParam.brandId = null;
              this.queryParam.productCategoryId = null;

            }
          },
          {
            label: "查询",
            page: "productList",
            type: "primary",
            handle: () => {
              this.queryParam.pageNum = 0;
              this.productList = [];
              this.noMore = false;
              this.load();
            }
          }
        ],
        // 翻页 底部显示“加载中”
        loading: false,

        queryParam: {
          pageSize: 24,
          pageNum: 0,
          name: "",
          productCategoryId: null,
          brandId: null
        },
        dropDownList: {
          brandIdList: [],
          productCategoryIdList: []
        },
        productList: [],
        noMore: false
      };
    },
    computed: {
      disabled() {
        return this.loading || this.noMore;
      }
    },
    created() {
      this.getBrands();
      this.getProductCategory();
    },
    activated() {
      this.getBrands();
      this.getProductCategory();
    },
    methods: {
      load() {
        this.loading = true;
        this.queryParam.pageNum = this.queryParam.pageNum + 1;
        product.getProductList(this.queryParam).then(res => {
          let productList = res.data.records;
          if (productList && productList.length > 0) {
            for (let i = 0; i < productList.length; i++) {
              if (productList[i].coverPic) {
                productList[i].coverUrl = resolvePicFileRes(
                  productList[i].coverPic
                ).url;
              } else {
                productList[i].coverUrl = "";
              }
            }
            this.productList.push(...productList);
          } else {
            this.noMore = true;
          }
          this.loading = false;
        });
      },
      getBrands() {
        product.getBrands().then(res => {
          this.dropDownList.brandIdList = res.data;
        });
      },
      getProductCategory() {
        product.getProductCategory().then(res => {
          this.dropDownList.productCategoryIdList = res.data;
        });
      },
      showDetail(productDetail) {
        this.showingProduct = productDetail;
        // 查询stockList
        product.getStockList({ productId: productDetail.id }).then(res => {
          this.stockList = res.data;
          this.productDetailShow = true;
        });
      },
      addToCart(){

      }
    }
  };
</script>
<style>
  /*::-webkit-scrollbar {*/
  /*  width: 0 !important*/
  /*}*/

  .my-el-card {
    overflow: auto;
    height: 420px;
    margin: 15px;
    cursor: pointer;
  }

  .my-el-card .image {
    overflow: scroll;
    height: 250px;
    width: 100%;
    display: block;
  }

  .my-el-card .description {
    margin-top: 10px;
    height: 80px;
    overflow: scroll;
    text-align: left;
    font-size: 20px;
  }

  .my-el-card .price {
    text-align: left;
    font-size: 24px;
    color: red;
  }



  .my-el-dialog {
    height: calc( 80% - 10px);
    width: 100%;
    overflow: auto;
  }

  .my-el-dialog .image {
    overflow: scroll;
    height: 250px;
    width: 100%;
    display: block;
  }

  .my-el-dialog .description {
    margin-top: 10px;
    height: 80px;
    overflow: scroll;
    text-align: left;
    font-size: 20px;
  }

  .my-el-dialog .price {
    text-align: left;
    font-size: 24px;
    color: red;
  }


</style>
