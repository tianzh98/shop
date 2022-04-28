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
          <el-card
            shadow="always"
            @click.native="showDetail(item)"
            class="my-el-card"
          >
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
    <el-dialog
      class="my-el-dialog"
      title="商品详情"
      v-if="showingProduct"
      :visible.sync="productDetailShow"
    >
      <div class="image">
        <el-carousel indicator-position="outside">
          <el-carousel-item v-for="item in picUrls" :key="item.id">
            <el-image :src="item.url"></el-image>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="description">
        <span>{{ showingProduct.name }}</span>
      </div>

      <div class="price">
        <span>￥{{ price }}</span>
      </div>
      <div>
        <el-form label-width="200px">
          <el-form-item
            v-for="(value, key) in productAttrList"
            :key="key"
            :label="value[0]"
          >
            <!--            这里需要使用@input="$forceUpdate()"，要不然选项点击不了 https://my.oschina.net/huashijun/blog/5503005-->
            <el-radio-group
              @input="$forceUpdate()"
              v-model="selectedProductAttr[value[0]]"
              @change="productAttrChange()"
            >
              <el-radio
                border
                v-for="op in value[1]"
                :key="op"
                :label="op"
              ></el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item v-if="productAttrList.size > 0" label="数量">
            <el-input-number
              :min="1"
              :max="10000"
              placeholder="最大输入10000"
              v-model="cartItem.quantity"
            ></el-input-number>
          </el-form-item>
          <el-form-item>
            <el-button v-if="selectedStock.id && selectedStock.stock <= 0"
              >抱歉，库存不足！</el-button
            >
            <el-button
              v-else-if="
                productAttrList.size > 0 &&
                  selectedStock.id &&
                  selectedStock.stock > 0
              "
              type="primary"
              @click="addToCart"
              >加入购物车
            </el-button>
            <span v-else-if="productAttrList.size > 0">请选择规格</span>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
import * as product from "@/http/implement/product";
import {getFileById} from "@/http/implement/common";
import { resolvePicFileRes ,resolvePicFileResultContext} from "@/utils/fileUtil";

export default {
  data() {
    return {
      // 商品详情
      // 是否弹出商品详情对话框
      productDetailShow: false,
      // 当前正在展示详情的商品信息
      showingProduct: null,
      stockList: [],
      // 商品规格 {key,options}
      productAttrList: new Map(),
      // 选中的商品规格 {key,value}
      selectedProductAttr: {},
      // 根据选中的商品规格 从stockList拿出对应的stock库存信息
      selectedStock: {
        stock: 0
      },
      cartItem: {
        productId: null,
        //商品分类
        productCategoryId: null,
        //stock表的id
        productStockId: null,
        // 货号
        productSn: null,
        // 品牌
        productBrand: null,
        // 购买数量
        quantity: 1,
        // 添加到购物车的价格
        price: null,
        // 商品主图
        mainPicId: null,
        // 商品名称
        productName: null,
        //商品副标题（卖点）
        productSubTitle: null,
        //商品条码
        productCode: null,
        //商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
        productAttr: null
      },
      // 商品轮播图 [{id,url},{id,url}]
      picUrls: [],

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
            this.queryParam.name = "";
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
        publishStatus: 1,
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
    },
    price() {
      if (this.selectedStock.id) {
        return this.selectedStock.price;
      } else {
        return this.showingProduct.price;
      }
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
      this.selectedStock = {
        stock: 0
      };
      this.productAttrList = new Map();
      this.selectedProductAttr = {};
      this.stockList = [];
      // 查询stockList
      product.getStockList({ productId: productDetail.id }).then(res => {
        this.stockList = res.data;

        // 构造商品规格选项
        if (this.stockList) {
          this.productAttrList = new Map();
          this.stockList.forEach(stock => {
            let spDataList = JSON.parse(stock.spData);
            if (spDataList) {
              for (let i = 0; i < spDataList.length; i++) {
                let spData = spDataList[i];
                let options = this.productAttrList.get(spData.key);
                if (!options) {
                  options = new Set();
                  // 初始化可选规格列表
                  this.productAttrList.set(spData.key, options);
                  // 初始化 已选中的规格为 空字符串
                  // this.selectedProductAttr.set(spData.key,"");
                  this.selectedProductAttr[spData.key] = "";
                }
                options.add(spData.value);
              }
            }
          });
        }

        // 获取商品轮播图
        this.picUrls = [];
        let oldPicIdList = productDetail.oldPicIdList;
        oldPicIdList.forEach(id => {
          getFileById({ id: id }).then(res => {
            this.picUrls.push(resolvePicFileResultContext(res)) ;
          });
        });
        this.productDetailShow = true;
      });
    },
    addToCart() {
      if (!this.selectedStock.id) {
        this.$message.warning("请选择商品规格");
        return;
      }
      // 构造cartItem信息
      let carItem = {
        productId: this.showingProduct.id,
        //商品分类
        productCategoryId: this.showingProduct.productCategoryId,
        //stock表的id
        productStockId: this.selectedStock.id,
        // 货号
        productSn: this.showingProduct.productSn,
        // 品牌
        productBrand: this.showingProduct.brandName,
        // 购买数量
        quantity: this.cartItem.quantity,
        // 添加到购物车的价格
        price: this.price,
        // 商品主图
        mainPicId: this.picUrls[0].id,
        // 商品名称
        productName: this.showingProduct.name,
        //商品副标题（卖点）
        productSubTitle: this.showingProduct.subTitle,
        //商品条码
        productCode: this.selectedStock.productCode,
        //商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
        productAttr: this.selectedStock.spData
      };
      product.addToCart(carItem).then(res => {
        if (res.code === "0") {
          this.$message.success(res.info);
        } else {
          this.$message.error(res.info);
        }
      });
      // console.log(this.selectedProductAttr)
    },
    productAttrChange() {
      // 选中了某一个规格时触发
      // 先置空
      this.selectedStock = {
        stock: 0
      };
      for (let j = 0; j < this.stockList.length; j++) {
        let stock = this.stockList[j];
        let spDataList = JSON.parse(stock.spData);
        // 校验这个spData是否和selectedProductAttr相等
        let hit = false;
        for (let i = 0; i < spDataList.length; i++) {
          let spData = spDataList[i];
          let key = spData.key;
          let value = spData.value;
          let selectedValue = this.selectedProductAttr[key];
          if (value === selectedValue) {
            hit = true;
          } else {
            hit = false;
            break;
          }
        }
        if (hit) {
          this.selectedStock = stock;
          break;
        }
      }
      //
      // // this.selectedProductAttr.set(key,option);
      // console.log(this.selectedProductAttr)
      // console.log(this.selectedStock)
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
  height: calc(80% - 10px);
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
