<template>
  <div>
    <div class="goBack">
      <el-page-header @back="goBack" content="添加商品属性" title="返回" />
    </div>
    <v-form
      labelWidth="150px"
      :itemWidth="7"
      :searchData="searchData"
      :searchForm="searchForm"
      :list="list"
      :rules="rules"
      :submit="submit"
    />
  </div>
</template>
<script>
import VForm from "@/components/v-form";
import * as product from "@/http/implement/product";

export default {
  name: "ProductAttrType",
  components: { VForm },
  data() {
    return {
      searchForm: [
        {
          type: "Input",
          label: "属性名称",
          prop: "name",
          placeholder: "请输入",
          clearable: true
        },
        {
          type: "Select",
          label: "商品类型",
          prop: "productAttributeCategoryId",
          multiple: false,
          change: value => {
            this.searchData.productAttributeCategoryId = value;
          },
          visibleChange: visible => {
            if (visible) {
              // 下拉框展示时
              this.getProductAttributeCategoryDropDown(visible);
            }
          },
          placeholder: "请选择"
        },
        {
          type: "Radio",
          label: "属性录入方式",
          prop: "inputType",
          clearable: true,
          radios: [
            { value: "0", label: "手工录入" },
            { value: "1", label: "从列表选取" }
          ]
        },
        {
          type: "Radio",
          label: "属性选择类型",
          prop: "selectType",
          clearable: true,
          radios: [
            { value: "0", label: "唯一" },
            { value: "1", label: "单选" },
            { value: "2", label: "多选" }
          ]
        },
        {
          type: "Input",
          label: "可选值列表，逗号分开",
          prop: "inputList",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "排序",
          prop: "sort",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Radio",
          label: "规格是否支持手动新增",
          prop: "handAddStatus",
          clearable: true,
          radios: [
            { value: "0", label: "不支持" },
            { value: "1", label: "支持" }
          ]
        }
      ],
      rules: {
        name: [{ required: true, message: "请输属性名称", trigger: "blur" }]
      },
      list: {
        productAttributeCategoryIdList: []
      },
      searchData: {
        name: "",
        productAttributeCategoryId: null,
        selectType: "2",
        inputType: "1",
        inputList: null,
        sort: 1,
        handAddStatus: "1"
      } //search data
    };
  },
  created() {
    this.getDetail();
  },
  activated() {},
  methods: {
    // 返回上一级路由
    goBack: function() {
      // this.$router.go(-1)
      this.$router.back();
    },
    getDetail: function() {
      // $route.query.的参数 bool会被转成string  所以这里要转换一下
      let isEdit = eval(this.$route.query.isEdit);
      if (isEdit) {
        product.getProductCateDetail({ id: this.$route.query.id }).then(res => {
          this.searchData = res.data;
        });
      }
    },
    getProductAttributeCategoryDropDown: function(visible) {
      if (visible) {
        product.getProductAttributeCategoryDropDown().then(res => {
          res.data.forEach(x => (x.value = parseInt(x.value)));
          this.list.productAttributeCategoryIdList = res.data;
        });
      }
    },
    submit: function() {
      product.editProductCateDetail(this.searchData).then(res => {
        if (res.code === "0") {
          this.goBack();
        }
      });
    }
  }
};
</script>
<style lang="less" scoped></style>
