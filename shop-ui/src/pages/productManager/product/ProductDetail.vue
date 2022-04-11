<template>
  <div>
    <div>
      <el-page-header @back="goBack" content="添加商品" title="返回" />
    </div>
    <Query
      size="mini"
      labelWidth="80px"
      :itemWidth="7"
      :searchData="searchData"
      :searchForm="searchForm"
      :searchHandle="searchHandle"
      :list="list"
    />
  </div>
</template>
<script>
export default {
  name: "ProductDetail",
  data() {
    return {
      searchForm: [
        {
          type: "Input",
          label: "用户名称",
          prop: "userName",
          placeholder: "请输入",
          clearable: true
        },
        {
          type: "Input",
          label: "用户账号",
          prop: "mobile",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Select",
          label: "用户状态",
          prop: "userStatusCodeArray",
          multiple: true,
          change: value => {
            this.searchData.userStatusCodeArray = value;
          },
          placeholder: "请选择"
        }
      ],
      searchHandle: [
        {
          label: "查询",
          page: "user",
          type: "primary",
          handle: () => {
            this.getTableData();
          }
        }
      ],
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
        userStatusCodeArray: []
      } //search data
    };
  },
  created() {},
  activated() {},
  methods: {
    goBack() {
      // this.$router.go(-1)
      this.$router.back();
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
