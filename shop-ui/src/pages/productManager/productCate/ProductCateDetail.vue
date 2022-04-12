<template>
  <div>
    <div>
      <el-page-header @back="goBack" content="添加商品分类" title="返回" />
    </div>
    <v-form
      labelWidth="80px"
      :itemWidth="7"
      :searchData="searchData"
      :searchForm="searchForm"
      :searchHandle="searchHandle"
      :list="list"
      :rules="rules"
      :submit="submit"
      :file-list="fileList"
      @uploadFile="uploadFile"
    />
  </div>
</template>
<script>
import VForm from "@/components/v-form";
export default {
  name: "ProductDetail",
  components: { VForm },
  data() {
    return {
      fileList: {
        icon: []
      },

      searchForm: [
        {
          type: "Input",
          label: "分类名称",
          prop: "name",
          placeholder: "请输入",
          clearable: true
        },

        {
          type: "Select",
          label: "上级分类",
          prop: "parentId",
          multiple: true,
          change: value => {
            this.searchData.userStatusCodeArray = value;
          },
          placeholder: "请选择"
        },
        {
          type: "Input",
          label: "数量单位",
          prop: "productUnit",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "排序",
          prop: "sortNum",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Radio",
          label: "是否显示",
          prop: "isShow",
          clearable: true,
          radios: [
            { value: "1", label: "是" },
            { value: "0", label: "否" }
          ]
        },
        {
          type: "Upload",
          label: "分类图标",
          prop: "icon",
          clearable: true,
          multiple: true
        }
      ],
      searchHandle: [
        {
          label: "提交",
          page: "user",
          type: "primary",
          handle: () => {}
        }
      ],
      rules: {
        name: [{ required: true, message: "请输入活动名称", trigger: "blur" }]
      },
      list: {
        userStatusCodeArrayList: [],
        orgNameList: []
      },
      searchData: {
        pageNum: this.$route.query.pageNum
          ? parseInt(this.$route.query.pageNum)
          : 1,
        pageSize: 200,
        userName: "",
        mobile: "",
        orgName: "",
        userStatusCodeArray: [],
        isShow: 0,
        icon: []
      } //search data
    };
  },
  created() {},
  activated() {},
  methods: {
    goBack() {
      // this.$router.go(-1)
      this.$router.back();
    },
    submit: function() {
      console.log("submit!!!");
    },
    uploadFile: function() {
      console.log(this.searchData);
    }
  }
};
</script>
<style lang="less" scoped>
.ces-search {
  overflow: hidden;
}

a {
  margin-left: 10px;
}

.el-select-dropdown__item {
  font-size: 12px;
}

.el-input--small {
  font-size: 12px;
}
</style>
