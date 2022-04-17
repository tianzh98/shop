<template>
  <div>
    <div class="goBack">
      <el-page-header @back="goBack" content="添加商品" title="返回"/>
    </div>
    <div class="addPage">
      <div class="my-form-wrap">
        <el-form
          :model="productDetail"
          :rules="rules"
          ref="productDetail"
          label-width="100px"
        >
          <el-collapse v-model="activeNames">
            <el-collapse-item title="商品信息" name="productInfo">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品分类" prop="productCategoryId">
                    <el-cascader
                      clearable
                      v-model="productDetail.productCategoryId"
                      :options="selectList.productCategoryDropDownList"
                      @visible-change="getProductCategoryDropDownList($event)"
                    ></el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="商品名称" prop="name">
                    <el-input clearable v-model="productDetail.name"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="副标题" prop="subTitle">
                    <el-input
                      clearable
                      v-model="productDetail.subTitle"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="12">
                  <el-form-item label="商品品牌" prop="brandId">
                    <el-select
                      @visible-change="getBrandIdDropDownList($event)"
                      v-model="productDetail.brandId"
                      placeholder="请选择"
                    >
                      <el-option
                        v-for="item in selectList.brandIdList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品介绍" prop="description">
                    <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入内容"
                      v-model="productDetail.description">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="商品货号" prop="productSn">
                    <el-input
                      clearable
                      v-model="productDetail.productSn"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品售价" prop="price">
                    <el-input type="number" placeholder="请输入内容" v-model="productDetail.price">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="市场价" prop="originalPrice">
                    <el-input type="number" placeholder="请输入内容" v-model="productDetail.originalPrice">
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品库存" prop="stock">
                    <el-input type="number" placeholder="请输入内容" v-model="productDetail.stock">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="计量单位" prop="unit">
                    <el-input
                      clearable
                      v-model="productDetail.unit"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品重量" prop="weight">
                    <el-input type="number" placeholder="请输入内容" v-model="productDetail.weight">
                      <template slot="append">克</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="排序" prop="sort">
                    <el-input type="number" placeholder="请输入内容" v-model="productDetail.sort">
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-collapse-item>

            <el-collapse-item title="商品属性" name="productAttr">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="属性类型" prop="productAttributeCategoryId">
                    <el-select
                      @visible-change="getProductAttributeCategoryIdList($event)"
                      v-model="productDetail.productAttributeCategoryId"
                      placeholder="请选择"
                    >
                      <el-option
                        v-for="item in selectList.productAttributeCategoryIdList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>

                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="商品规格" prop="productSn">
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品参数" prop="productSn">
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="商品相册" prop="productSn">
                  </el-form-item>
                </el-col>
              </el-row>
            </el-collapse-item>
          </el-collapse>
        </el-form>

        <div class="btnBoxs">
          <el-button type="primary" @click="submitForm('productDetail')"
          >提交
          </el-button>
          <el-button @click="resetForm('productDetail')">重置</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import * as product from "@/http/implement/product";

  export default {
    name: "ProductDetail",
    data() {
      return {
        activeNames: ["productInfo", "productAttr"],
        productDetail: {
          productCategoryId: null,
          name: "",
          subTitle: "",
          brandId: null,
          description: "",
          productSn: "",
          price: null,
          originalPrice: null,
          stock: null,
          unit: "",
          weight: null,
          sort: null,

          // 商品属性：
          productAttributeCategoryId: null
        },
        rules: {},
        selectList: {
          productCategoryDropDownList: [],
          brandIdList: [],
          productAttributeCategoryIdList: []
        }
      };
    },
    created() {
    },
    activated() {
    },
    methods: {
      goBack: function () {
        // this.$router.go(-1)
        this.$router.back();
      },
      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            this.submit();
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

      getProductCategoryDropDownList: function (visible) {
        if (visible) {
          product.getProductCategory().then(res => {
            res.data.forEach(x => (x.value = parseInt(x.value)));
            this.selectList.productCategoryDropDownList = res.data;
          });
        }
      },
      getBrandIdDropDownList: function (visible) {
        if (visible) {
          product.getBrands().then(res => {
            res.data.forEach(x => (x.value = parseInt(x.value)));
            this.selectList.brandIdList = res.data;
          });
        }
      },
      getProductAttributeCategoryIdList: function (visible) {
        if (visible) {
          product.getBrands().then(res => {
            res.data.forEach(x => (x.value = parseInt(x.value)));
            this.selectList.brandIdList = res.data;
          });
        }
      }

    }
  };
</script>
<style lang="less" scoped></style>
