<template>
  <div>
    <div class="goBack">
      <el-page-header @back="goBack" content="添加或者编辑地址" title="返回" />
    </div>
    <v-form
      labelWidth="80px"
      :itemWidth="7"
      :searchData="searchData"
      :searchForm="searchForm"
      :list="list"
      :rules="rules"
      :submit="updateOrInsertAddress"
    />
  </div>
</template>
<script>
import * as product from "@/http/implement/product";

export default {
  name: "AddAddress",
  data() {
    return {
      searchForm: [
        {
          type: "Input",
          label: "收货人姓名",
          prop: "name",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "收货人电话号码",
          prop: "phoneNumber",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "邮政编码",
          prop: "postCode",
          clearable: true,
          placeholder: "请输入"
        },
        // {
        //   type: "Input",
        //   label: "省份",
        //   prop: "province",
        //   clearable: true,
        //   placeholder: "请输入"
        // },
        // {
        //   type: "Input",
        //   label: "城市",
        //   prop: "city",
        //   clearable: true,
        //   placeholder: "请输入"
        // },
        // {
        //   type: "Input",
        //   label: "地区",
        //   prop: "region",
        //   clearable: true,
        //   placeholder: "请输入"
        // },
        {
          type: "distpicker",
          label: "所在区域",
          province: "province",
          city: "city",
          area: "region",
          clearable: true,
          onSelectRegion: data => {
            this.searchData.province = data.province.value;
            this.searchData.city = data.city.value;
            this.searchData.region = data.area.value;
          }
        },
        {
          type: "Input",
          label: "具体地址",
          prop: "detailAddress",
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
        name: "",
        phoneNumber: "",
        postCode: "",
        province: "",
        city: "",
        region: "",
        detailAddress: ""
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
    updateOrInsertAddress: function() {
      this.$confirm("是否确认提交？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      }).then(() => {
        product.updateAndInsertAddress(this.searchData).then(res => {
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
        product.getAddressById({ id: this.$route.query.id }).then(res => {
          this.searchData = res.data;
        });
      }
    }
  }
};
</script>

<style scoped></style>
