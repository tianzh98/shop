<template>
  <div>
    <div class="goBack">
      <el-page-header @back="goBack" content="添加商品" title="返回" />
    </div>
    <div class="addPage" style="height: calc( 100% - 100px );overflow: scroll;">
      <div class="my-form-wrap">
        <el-form
          :model="productDTO"
          :rules="rules"
          ref="productDTO"
          label-width="100px"
        >
          <el-collapse v-model="activeNames">
            <el-collapse-item title="商品信息" name="productInfo">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品分类" prop="productCategoryId">
                    <el-cascader
                      clearable
                      v-model="productCategoryIdArray"
                      :options="selectList.productCategoryDropDownList"
                      @visible-change="getProductCategoryDropDownList($event)"
                    ></el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="商品名称" prop="name">
                    <el-input clearable v-model="productDTO.name"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="副标题" prop="subTitle">
                    <el-input
                      clearable
                      v-model="productDTO.subTitle"
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="12">
                  <el-form-item label="商品品牌" prop="brandId">
                    <el-select
                      @visible-change="getBrandIdDropDownList($event)"
                      v-model="productDTO.brandId"
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
                      v-model="productDTO.description"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="商品货号" prop="productSn">
                    <el-input
                      clearable
                      v-model="productDTO.productSn"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品售价" prop="price">
                    <el-input
                      type="number"
                      placeholder="请输入内容"
                      v-model="productDTO.price"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="市场价" prop="originalPrice">
                    <el-input
                      type="number"
                      placeholder="请输入内容"
                      v-model="productDTO.originalPrice"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品库存" prop="stock">
                    <el-input
                      type="number"
                      placeholder="请输入内容"
                      v-model="productDTO.stock"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="计量单位" prop="unit">
                    <el-input clearable v-model="productDTO.unit"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品重量" prop="weight">
                    <el-input
                      type="number"
                      placeholder="请输入内容"
                      v-model="productDTO.weight"
                    >
                      <template slot="append">克</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="排序" prop="sort">
                    <el-input
                      type="number"
                      placeholder="请输入内容"
                      v-model="productDTO.sort"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-collapse-item>

            <el-collapse-item title="商品属性" name="productAttr">
              <el-row>
                <el-col :span="12">
                  <el-form-item
                    label="属性类型"
                    prop="productAttributeCategoryId"
                  >
                    <el-select
                      @visible-change="
                        getProductAttributeCategoryDropDown($event)
                      "
                      v-model="productDTO.productAttributeCategoryId"
                      @change="
                        productAttributeCategoryIdChange(
                          productDTO.productAttributeCategoryId
                        )
                      "
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
                  <el-form-item label="商品相册" prop="newPicIdList">
                    <multi-upload
                      :old-pic-id-list="productDTO.oldPicIdList"
                      @changeNewPicIdList="changeNewPicIdList"
                    ></multi-upload>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="商品参数" prop="productAttr">
                    <el-card shadow="never" class="cardBg">
                      <div
                        v-for="(item, index) in selectProductParam"
                        :key="index"
                        :class="{ littleMarginTop: index !== 0 }"
                      >
                        <div class="paramInputLabel">{{ item.name }}:</div>
                        <el-select
                          v-if="item.inputType === 1"
                          class="paramInput"
                          v-model="selectProductParam[index].value"
                        >
                          <el-option
                            v-for="item in getInputListArr(item.inputList)"
                            :key="item"
                            :label="item"
                            :value="item"
                          >
                          </el-option>
                        </el-select>
                        <el-input
                          v-else
                          class="paramInput"
                          v-model="selectProductParam[index].value"
                        ></el-input>
                      </div>
                    </el-card>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="商品规格" prop="productSpec">
                    <el-card shadow="never" class="cardBg">
                      <div
                        v-for="(productAttr, idx) in selectProductAttr"
                        :key="idx"
                      >
                        {{ productAttr.name }}：
                        <el-checkbox-group
                          v-if="productAttr.handAddStatus === 0"
                          v-model="selectProductAttr[idx].values"
                        >
                          <el-checkbox
                            v-for="item in getInputListArr(
                              productAttr.inputList
                            )"
                            :label="item"
                            :key="item"
                            class="littleMarginLeft"
                          ></el-checkbox>
                        </el-checkbox-group>
                        <div v-else>
                          <el-checkbox-group
                            v-model="selectProductAttr[idx].values"
                          >
                            <div
                              v-for="(item, index) in selectProductAttr[idx]
                                .options"
                              :key="index"
                              style="display: inline-block"
                              class="littleMarginLeft"
                            >
                              <el-checkbox
                                :label="item"
                                :key="item"
                              ></el-checkbox>
                              <el-button
                                type="text"
                                class="littleMarginLeft"
                                @click="
                                  handleRemoveProductAttrValue(idx, index)
                                "
                                >删除
                              </el-button>
                            </div>
                          </el-checkbox-group>
                          <el-input
                            v-model="addProductAttrValue[idx]"
                            @input="$forceUpdate()"
                            style="width: 160px;margin-left: 10px"
                            clearable
                          ></el-input>
                          <el-button
                            class="littleMarginLeft"
                            @click="handleAddProductAttrValue(idx)"
                            >增加
                          </el-button>
                        </div>
                      </div>
                    </el-card>
                    <el-table
                      style="width: 100%;margin-top: 20px"
                      :data="stockList"
                      border
                    >
                      <el-table-column
                        v-for="(item, index) in selectProductAttr"
                        :label="item.name"
                        :key="item.id"
                        align="center"
                      >
                        <template slot-scope="scope">
                          {{ getProductSkuSp(scope.row, index) }}
                        </template>
                      </el-table-column>
                      <el-table-column
                        label="销售价格"
                        width="80"
                        align="center"
                      >
                        <template slot-scope="scope">
                          <el-input v-model="scope.row.price"></el-input>
                        </template>
                      </el-table-column>
                      <el-table-column
                        label="商品库存"
                        width="80"
                        align="center"
                      >
                        <template slot-scope="scope">
                          <el-input v-model="scope.row.stock"></el-input>
                        </template>
                      </el-table-column>
                      <el-table-column
                        label="库存预警值"
                        width="80"
                        align="center"
                      >
                        <template slot-scope="scope">
                          <el-input v-model="scope.row.lowStock"></el-input>
                        </template>
                      </el-table-column>
                      <el-table-column label="SKU编号" align="center">
                        <template slot-scope="scope">
                          <el-input v-model="scope.row.productCode"></el-input>
                        </template>
                      </el-table-column>
                      <el-table-column label="操作" width="80" align="center">
                        <template slot-scope="scope">
                          <el-button
                            type="text"
                            @click="
                              handleRemoveProductSku(scope.$index, scope.row)
                            "
                            >删除
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-button
                      type="primary"
                      style="margin-top: 20px"
                      @click="handleRefreshProductSkuList"
                      >刷新/重置列表
                    </el-button>
                    <el-button
                      type="primary"
                      style="margin-top: 20px"
                      @click="handleSyncProductSkuList"
                      >同步当前列表数据库信息
                    </el-button>
                    <!--                    <el-button-->
                    <!--                      type="primary"-->
                    <!--                      style="margin-top: 20px"-->
                    <!--                      @click="handleSyncProductSkuStock"-->
                    <!--                      >同步库存-->
                    <!--                    </el-button>-->
                  </el-form-item>
                </el-col>
              </el-row>
            </el-collapse-item>
          </el-collapse>
        </el-form>
      </div>
    </div>
    <div>
      <el-button type="primary" @click="submitForm('productDTO')"
        >提交
      </el-button>
      <el-button @click="resetForm('productDTO')">重置</el-button>
    </div>
  </div>
</template>
<script>
import * as product from "@/http/implement/product";
import MultiUpload from "@/components/Upload/multiUpload";

export default {
  name: "ProductDetail",
  components: { MultiUpload },

  data() {
    return {
      activeNames: ["productInfo", "productAttr"],
      // 商品分类级联下拉框的绑定值，取下标为1的即可
      productCategoryIdArray: [],
      // 商品信息
      productDTO: {
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
        productAttributeCategoryId: null,

        oldPicIdList: [],
        newPicIdList: []
      },
      // 商品属性 列表
      selectProductAttr: [],
      // 商品规格 列表
      selectProductParam: [],
      //商品库存信息{lowStock: 0, pic: '', price: 0, sale: 0, skuCode: '', spData: '', stock: 0}
      stockList: [],

      // 商品所具有的属性(编辑模式下获取商品原来具有的属性)
      productAttributeValueList: [],
      // 增加自定义的属性值(临时变量，不需要传给后端)
      addProductAttrValue: [null, null, null],

      // 表单校验规则
      rules: {
        productCategoryId: [
          { required: true, message: "请输入分类名称", trigger: "blur" }
        ],
        name: [{ required: true, message: "请输入商品名称", trigger: "blur" }],
        subTitle: [
          { required: true, message: "请输入副标题", trigger: "blur" }
        ],
        brandId: [{ required: true, message: "请选择品牌", trigger: "blur" }],
        newPicIdList: [
          {
            type: "array",
            required: true,
            message: "请至少上传一张图片",
            trigger: "blur"
          }
        ]
      },
      // 下拉框可选值
      selectList: {
        productCategoryDropDownList: [],
        brandIdList: [],
        productAttributeCategoryIdList: []
      }
    };
  },
  created() {
    this.getProductCategoryDropDownList(true);
    this.getBrandIdDropDownList(true);
    this.getProductAttributeCategoryDropDown(true);
    this.getStockList();
    this.getProductDetail();
    this.getProductAttributeValueList();
  },
  activated() {},
  computed: {
    isEdit() {
      return eval(this.$route.query.isEdit);
    }
  },
  watch: {
    productCategoryIdArray: function(newValue) {
      if (newValue && newValue.length === 2) {
        this.productDTO.productCategoryId = newValue[1];
      } else {
        this.productDTO.productCategoryId = null;
      }
    }
  },
  methods: {
    goBack: function() {
      // this.$router.go(-1)
      this.$router.back();
    },
    changeNewPicIdList: function(newPicIdList) {
      this.productDTO.newPicIdList = newPicIdList;
    },
    submitForm(formName) {
      // console.log(this.productDTO);
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm("是否确认提交？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "info"
          }).then(() => {
            let data = {
              id: this.$route.query.id,
              ...this.productDTO,
              stockList: this.stockList,
              selectProductAttr: this.selectProductAttr,
              selectProductParam: this.selectProductParam
            };
            product.saveOrUpdateProductDetail(data).then(res => {
              this.$message({
                message: res.info,
                type: "success"
              });
              this.goBack();
            });
          });
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    getProductCategoryDropDownList: function(visible) {
      if (visible) {
        product.getProductCategory().then(res => {
          this.selectList.productCategoryDropDownList = res.data;
        });
      }
    },
    getBrandIdDropDownList: function(visible) {
      if (visible) {
        product.getBrands().then(res => {
          res.data.forEach(x => (x.value = parseInt(x.value)));
          this.selectList.brandIdList = res.data;
        });
      }
    },
    getProductAttributeCategoryDropDown: function(visible) {
      if (visible) {
        product.getProductAttributeCategoryDropDown().then(res => {
          res.data.forEach(x => (x.value = parseInt(x.value)));
          this.selectList.productAttributeCategoryIdList = res.data;
        });
      }
    },
    getProductDetail: function() {
      if (this.isEdit) {
        product
          .getProductList({ pageSize: 1, pageNum: 1, id: this.$route.query.id })
          .then(res => {
            this.productDTO = res.data.records[0];

            // 获取商品规格，参数
            this.productAttributeCategoryIdChange(
              this.productDTO.productAttributeCategoryId
            );

            // 初始化商品分类下拉框
            for (
              let i = 0;
              i < this.selectList.productCategoryDropDownList.length;
              i++
            ) {
              // 寻找匹配的children
              let parentDropDown = this.selectList.productCategoryDropDownList[
                i
              ];
              for (
                let j = 0;
                parentDropDown.children && j < parentDropDown.children.length;
                j++
              ) {
                let child = parentDropDown.children[j];
                if (
                  child &&
                  child.value === String(this.productDTO.productCategoryId)
                ) {
                  // this.productCategoryIdArray.push(parentDropDown.value,child.value) ;
                  // 这里必须下面这样写 不然级联下拉框不能回显
                  this.productCategoryIdArray = [
                    parentDropDown.value,
                    child.value
                  ];
                  return;
                }
              }
            }
          });
      }
    },
    getStockList: function() {
      // $route.query.的参数 bool会被转成string  所以这里要转换一下
      if (this.isEdit) {
        product.getStockList({ productId: this.$route.query.id }).then(res => {
          this.stockList = res.data;
        });
      }
    },
    getProductAttributeValueList: function() {
      if (this.isEdit) {
        product
          .getProductAttributeValueList({ productId: this.$route.query.id })
          .then(res => {
            this.productAttributeValueList = res.data;
          });
      }
    },
    productAttributeCategoryIdChange: function(productAttributeCategoryId) {
      // 获取商品规格，参数
      product
        .getProductAttribute({
          productAttributeCategoryId: productAttributeCategoryId
        })
        .then(res => {
          // 0->规格；1->参数
          let selectProductAttrList = res.data.filter(item => item.type === 0);
          let selectProductParamList = res.data.filter(item => item.type === 1);

          // 商品规格
          this.selectProductAttr = [];
          for (let i = 0; i < selectProductAttrList.length; i++) {
            let options = [];
            let values = [];
            if (this.isEdit) {
              if (selectProductAttrList[i].handAddStatus === 1) {
                //编辑状态下获取手动添加编辑属性
                options = this.getEditAttrOptions(selectProductAttrList[i].id);
              }
              //编辑状态下获取选中属性
              values = this.getEditAttrValues(i);
            }
            this.selectProductAttr.push({
              id: selectProductAttrList[i].id,
              name: selectProductAttrList[i].name,
              handAddStatus: selectProductAttrList[i].handAddStatus,
              inputList: selectProductAttrList[i].inputList,
              type: selectProductAttrList[i].type,
              inputType: selectProductAttrList[i].inputType,
              values: values,
              options: options
            });
          }

          // // todo delete me
          // if (this.isEdit) {
          //   //编辑模式下刷新商品属性图片
          //   this.refreshProductAttrPics();
          // }

          // 商品属性
          this.selectProductParam = [];
          for (let i = 0; i < selectProductParamList.length; i++) {
            let value = null;
            if (this.isEdit) {
              //编辑模式下获取参数属性
              value = this.getEditParamValue(selectProductParamList[i].id);
            }
            this.selectProductParam.push({
              id: selectProductParamList[i].id,
              name: selectProductParamList[i].name,
              type: selectProductParamList[i].type,
              value: value,
              inputType: selectProductParamList[i].inputType,
              inputList: selectProductParamList[i].inputList
            });
          }
        });
    },
    getEditAttrOptions(id) {
      let options = [];
      for (let i = 0; i < this.productAttributeValueList.length; i++) {
        let attrValue = this.productAttributeValueList[i];
        if (attrValue.productAttributeId === id) {
          let strArr = attrValue.value.split(",");
          for (let j = 0; j < strArr.length; j++) {
            options.push(strArr[j]);
          }
          break;
        }
      }
      return options;
    },
    //获取选中的属性值
    getEditAttrValues(index) {
      let values = new Set();
      if (index === 0) {
        for (let i = 0; i < this.stockList.length; i++) {
          let sku = this.stockList[i];
          let spData = JSON.parse(sku.spData);
          if (spData !== null && spData.length >= 1) {
            values.add(spData[0].value);
          }
        }
      } else if (index === 1) {
        for (let i = 0; i < this.stockList.length; i++) {
          let sku = this.stockList[i];
          let spData = JSON.parse(sku.spData);
          if (spData !== null && spData.length >= 2) {
            values.add(spData[1].value);
          }
        }
      } else {
        for (let i = 0; i < this.stockList.length; i++) {
          let sku = this.stockList[i];
          let spData = JSON.parse(sku.spData);
          if (spData !== null && spData.length >= 3) {
            values.add(spData[2].value);
          }
        }
      }
      return Array.from(values);
    },
    //获取属性的值
    getEditParamValue(id) {
      for (let i = 0; i < this.productAttributeValueList.length; i++) {
        if (id === this.productAttributeValueList[i].productAttributeId) {
          return this.productAttributeValueList[i].value;
        }
      }
    },
    getProductSkuSp(row, index) {
      let spData = JSON.parse(row.spData);
      if (spData !== null && index < spData.length) {
        return spData[index].value;
      } else {
        return null;
      }
    },
    getInputListArr(inputList) {
      return inputList.split(",");
    },
    handleRemoveProductAttrValue(idx, index) {
      this.selectProductAttr[idx].options.splice(index, 1);
    },
    handleAddProductAttrValue(idx) {
      let options = this.selectProductAttr[idx].options;
      if (
        this.addProductAttrValue[idx] === null ||
        this.addProductAttrValue[idx] === ""
      ) {
        this.$message({
          message: "属性值不能为空",
          type: "warning",
          duration: 1000
        });
        return;
      }
      if (options.indexOf(this.addProductAttrValue[idx]) !== -1) {
        this.$message({
          message: "属性值不能重复",
          type: "warning",
          duration: 1000
        });
        return;
      }
      this.selectProductAttr[idx].options.push(this.addProductAttrValue[idx]);
      this.addProductAttrValue[idx] = null;
    },
    handleRefreshProductSkuList() {
      this.$confirm("刷新列表将导致sku信息重新生成，是否要刷新?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          // this.refreshProductAttrPics();
          // 清空原始组合信息
          this.stockList = [];
          this.refreshProductSkuList();
        })
        .catch(() => {});
    },

    handleSyncProductSkuList() {
      if (this.isEdit) {
        this.$confirm("是否同步当前规格参数的原始数据?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "info"
        })
          .then(() => {
            product
              .getStockList({ productId: this.$route.query.id })
              .then(res => {
                let stockListRes = res.data;
                let skuList = JSON.parse(JSON.stringify(this.stockList));
                if (skuList && stockListRes) {
                  for (let i = 0; i < skuList.length; i++) {
                    let stock = skuList[i];
                    for (let j = 0; j < stockListRes.length; j++) {
                      let stockRes = stockListRes[j];
                      if (stock.spData === stockRes.spData) {
                        stock.price = stockRes.price;
                        stock.stock = stockRes.stock;
                        stock.lowStock = stockRes.lowStock;
                        stock.productCode = stockRes.productCode;
                        stock.id = stockRes.id;
                        break;
                      }
                    }
                  }
                }
                this.stockList = skuList;
              });
          })
          .catch(() => {});
      }
    },
    // handleSyncProductSkuPrice() {
    //   this.$confirm("将同步第一个sku的价格到所有sku,是否继续", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   }).then(() => {
    //     if (this.stockList !== null && this.stockList.length > 0) {
    //       let tempSkuList = [];
    //       tempSkuList = tempSkuList.concat(tempSkuList, this.stockList);
    //       let price = this.stockList[0].price;
    //       for (let i = 0; i < tempSkuList.length; i++) {
    //         tempSkuList[i].price = price;
    //       }
    //       this.stockList = [];
    //       this.stockList = this.stockList.concat(this.stockList, tempSkuList);
    //     }
    //   });
    // },
    // handleSyncProductSkuStock() {
    //   this.$confirm("将同步第一个sku的库存到所有sku,是否继续", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   }).then(() => {
    //     if (this.stockList !== null && this.stockList.length > 0) {
    //       let tempSkuList = [];
    //       tempSkuList = tempSkuList.concat(tempSkuList, this.stockList);
    //       let stock = this.stockList[0].stock;
    //       let lowStock = this.stockList[0].lowStock;
    //       for (let i = 0; i < tempSkuList.length; i++) {
    //         tempSkuList[i].stock = stock;
    //         tempSkuList[i].lowStock = lowStock;
    //       }
    //       this.stockList = [];
    //       this.stockList = this.stockList.concat(this.stockList, tempSkuList);
    //     }
    //   });
    // },

    refreshProductSkuList() {
      this.stockList = [];
      let skuList = this.stockList;
      //只有一个属性时
      if (this.selectProductAttr.length === 1) {
        let attr = this.selectProductAttr[0];
        for (let i = 0; i < attr.values.length; i++) {
          skuList.push({
            spData: JSON.stringify([{ key: attr.name, value: attr.values[i] }])
          });
        }
      } else if (this.selectProductAttr.length === 2) {
        let attr0 = this.selectProductAttr[0];
        let attr1 = this.selectProductAttr[1];
        for (let i = 0; i < attr0.values.length; i++) {
          if (attr1.values.length === 0) {
            skuList.push({
              spData: JSON.stringify([
                { key: attr0.name, value: attr0.values[i] }
              ])
            });
            continue;
          }
          for (let j = 0; j < attr1.values.length; j++) {
            let spData = [];
            spData.push({ key: attr0.name, value: attr0.values[i] });
            spData.push({ key: attr1.name, value: attr1.values[j] });
            skuList.push({
              spData: JSON.stringify(spData)
            });
          }
        }
      } else if (this.selectProductAttr.length === 3) {
        let attr0 = this.selectProductAttr[0];
        let attr1 = this.selectProductAttr[1];
        let attr2 = this.selectProductAttr[2];
        for (let i = 0; i < attr0.values.length; i++) {
          if (attr1.values.length === 0) {
            skuList.push({
              spData: JSON.stringify([
                { key: attr0.name, value: attr0.values[i] }
              ])
            });
            continue;
          }
          for (let j = 0; j < attr1.values.length; j++) {
            if (attr2.values.length === 0) {
              let spData = [];
              spData.push({ key: attr0.name, value: attr0.values[i] });
              spData.push({ key: attr1.name, value: attr1.values[j] });
              skuList.push({
                spData: JSON.stringify(spData)
              });
              continue;
            }
            for (let k = 0; k < attr2.values.length; k++) {
              let spData = [];
              spData.push({ key: attr0.name, value: attr0.values[i] });
              spData.push({ key: attr1.name, value: attr1.values[j] });
              spData.push({ key: attr2.name, value: attr2.values[k] });
              skuList.push({
                spData: JSON.stringify(spData)
              });
            }
          }
        }
      } else {
        this.$message.error("商品规格不支持三种及以上组合");
      }
    },
    // refreshProductAttrPics() {
    //   this.selectProductAttrPics = [];
    //   if (this.selectProductAttr.length >= 1) {
    //     let values = this.selectProductAttr[0].values;
    //     for (let i = 0; i < values.length; i++) {
    //       let pic = null;
    //       if (this.isEdit) {
    //         //编辑状态下获取图片
    //         pic = this.getProductSkuPic(values[i]);
    //       }
    //       this.selectProductAttrPics.push({ name: values[i], pic: pic });
    //     }
    //   }
    // },

    //获取商品相关属性的图片
    getProductSkuPic(name) {
      for (let i = 0; i < this.stockList.length; i++) {
        let spData = JSON.parse(this.stockList[i].spData);
        if (name === spData[0].value) {
          return this.stockList[i].pic;
        }
      }
      return null;
    },
    handleRemoveProductSku(index) {
      let list = this.stockList;
      if (list.length === 1) {
        list.pop();
      } else {
        list.splice(index, 1);
      }
    }
  }
};
</script>
<style lang="less" scoped></style>
