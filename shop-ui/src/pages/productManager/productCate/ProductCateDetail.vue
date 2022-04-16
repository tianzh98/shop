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
import * as product from "@/http/implement/product";

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
          multiple: false,
          change: value => {
            this.searchData.parentId = value;
          },
          visibleChange: visible => {
            if (visible) {
              // 下拉框展示时
              this.getParentDropdown();
            }
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
          prop: "sort",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Radio",
          label: "是否显示",
          prop: "showStatus",
          clearable: true,
          radios: [
            { value: "1", label: "是" },
            { value: "0", label: "否" }
          ]
        },
        // {
        //   type: "Upload",
        //   label: "分类图标",
        //   prop: "icon",
        //   clearable: true,
        //   multiple: true
        // },
        {
          type: "Input",
          label: "分类描述",
          prop: "description",
          clearable: true,
          placeholder: "请输入"
        }
      ],
      rules: {
        name: [{ required: true, message: "请输入分类名称", trigger: "blur" }],
        parentId: [
          { required: true, message: "请选择上级分类", trigger: "blur" }
        ]
      },
      list: {
        parentIdList: []
      },
      searchData: {
        name: "",
        parentId: null,
        productUnit: "",
        sort: 1,
        showStatus: "",
        description: ""
      } //search data
    };
  },
  created() {
    this.getDetail();
  },
  activated() {},
  methods: {
    goBack() {
      // this.$router.go(-1)
      this.$router.back();
    },
    getDetail: function() {
      if (this.$route.query.isEdit) {
        product.getProductCateDetail({ id: this.$route.query.id }).then(res => {
          this.searchData = res.data;
        });
      }
    },
    // 获取上级分类
    getParentDropdown: function() {
      product.getParentDropdown().then(res => {
        this.list.parentIdList = res.data;
      });
    },
    submit: function() {
      product.editProductCateDetail(this.searchData).then(res => {
        if (res.code === "0") {
          this.goBack();
        }
      });
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
