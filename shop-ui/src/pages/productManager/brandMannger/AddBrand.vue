<template>
  <div>
    <div class="goBack">
      <el-page-header @back="goBack" content="添加品牌" title="返回" />
    </div>
    <v-form
      labelWidth="80px"
      :itemWidth="7"
      :searchData="searchData"
      :searchForm="searchForm"
      :list="list"
      :rules="rules"
      :submit="insertBrand"
    />
  </div>
</template>
<script>
import * as product from "@/http/implement/product";

export default {
  name: "AddBrand",
  data() {
    return {
      searchForm: [
        {
          type: "Input",
          label: "品牌名字",
          prop: "name",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "品牌首字母",
          prop: "firstLetter",
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
          type: "Input",
          label: "品牌故事",
          prop: "brandStory",
          clearable: true,
          placeholder: "请输入"
        }
      ],
      searchHandle: [],
      total: 0,
      sortName: "",
      sortType: "",
      selection: [],
      rules: {
        name: [{ required: true, message: "请输入品牌名称", trigger: "blur" }]
      },
      searchData: {
        //品牌id下拉
        name: "",
        firstLetter: "",
        sort: "",
        brandStory: ""
      },
      list: {}
    };
  },
  created() {
    //一加载页面就运行
    this.getDetail();
  },
  watch: {},
  activated() {},
  methods: {
    goBack: function() {
      // this.$router.go(-1)
      this.$router.back();
    },
    insertBrand: function() {
      this.$confirm("是否确认提交？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      }).then(() => {
        product.insertAndUpdateBrand(this.searchData).then(res => {
          if (res.code === "0") {
            this.goBack();
          }
        });
      });
    },
    getDetail: function() {
      // $route.query.的参数 bool会被转成string  所以这里要转换一下
      let isEdit = eval(this.$route.query.isEdit);
      if (isEdit) {
        product.getBrandById({ id: this.$route.query.id }).then(res => {
          this.searchData = res.data;
        });
      }
    }
  }
};
</script>

<style scoped></style>
