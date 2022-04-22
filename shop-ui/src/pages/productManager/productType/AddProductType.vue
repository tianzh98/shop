<template>
  <div>
    <div class="goBack">
      <el-page-header @back="goBack" content="添加或者更新商品类型" title="返回" />
    </div>
    <v-form
      :searchData="searchData"
      :searchForm="searchForm"
      :list="list"
      :rules="rules"
      :submit="insertOrUpdate"
    />
  </div>
</template>
<script>
import * as product from "@/http/implement/product";
import VForm from "@/components/v-form";
export default {
  name: "AddProductType",
  components: { VForm },
  data() {
    return {
      searchForm: [
        {
          type: "Input",
          label: "商品类型名称",
          prop: "name",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "商品属性数量",
          prop: "attributeCount",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "商品参数数量",
          prop: "paramCount",
          clearable: true,
          placeholder: "请输入"
        },
      ],
      rules: {
        name:[{ required: true, message: "请输入品牌类型名称", trigger: "blur" }]
      },
      searchData: {
        name:"",
        attributeCount:"",
        paramCount:"",
      },
      list:null,
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
    insertOrUpdate: function()
    {  this.$confirm("是否确认提交？", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "info"
    }).then(() => {
      product.insertOrUpdateProductAttributeCategoryById(this.searchData).then(res => {
        //如果更新或者插入商品类型成功
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
        product.getProductAttributeCategoryById({ id: this.$route.query.id }).then(res => {
          this.searchData = res.data;
        });
      }
    },
  }
};
</script>

<style scoped></style>
