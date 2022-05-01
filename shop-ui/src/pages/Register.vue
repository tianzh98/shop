<template>
  <div>
    <div class="goBack">
      <el-page-header @back="goBack" content="注册" title="返回" />
    </div>
    <v-form
      labelWidth="80px"
      :itemWidth="7"
      :searchData="searchData"
      :searchForm="searchForm"
      :list="list"
      :rules="rules"
      :submit="insertUser"
    />
  </div>
</template>
<script>
import * as common from "@/http/implement/common";
import localCache from "@/utils/cache";
import { base64encode } from "@/utils/common";

export default {
  name: "Register",
  data() {
    return {
      searchForm: [
        {
          type: "Input",
          label: "登录账号",
          prop: "account",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "用户昵称",
          prop: "userName",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "真实姓名",
          prop: "realName",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "密码",
          prop: "password",
          rowType: "password",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Select",
          label: "性别",
          prop: "gender",
          clearable: true,
          change: value => {
            this.searchData.gender = value;
          },
          placeholder: "请选择"
        },
        {
          type: "Date",
          label: "出生日期",
          prop: "birthday",
          clearable: true,
          change: () => {
            //转换日期格式
            this.searchData.birthday = this.dateFormat(
              this.searchData.birthday
            );
          },
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "用户邮箱",
          prop: "email",
          clearable: true,
          placeholder: "请输入"
        },
        {
          type: "Input",
          label: "用户电话",
          prop: "phone",
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
        userName: [
          { required: true, message: "请输入用户名称", trigger: "blur" }
        ],
        account: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        phone: [{ required: true, message: "请输入电话号码", trigger: "blur" }],
        email: [{ required: true, message: "请输入邮箱", trigger: "blur" }]
      },
      searchData: {
        account: "",
        userName: "",
        realName: "",
        password: "",
        gender: "",
        birthday: "",
        email: "",
        phone: ""
      },
      list: {
        genderList: ""
      }
    };
  },
  created() {
    //一加载页面就运行
    this.getGender();
  },
  watch: {},
  activated() {},
  methods: {
    //日期格式化（转年月日时分秒）
    dateFormat: function(datetime) {
      let date = new Date(datetime);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let hour = date.getHours();
      let minute = date.getMinutes();
      let second = date.getSeconds();
      return (
        year +
        "-" +
        this.formatTen(month) +
        "-" +
        this.formatTen(day) +
        " " +
        this.formatTen(hour) +
        ":" +
        this.formatTen(minute) +
        ":" +
        this.formatTen(second)
      );
    },
    formatTen: function(num) {
      return num > 9 ? num + "" : "0" + num;
    },
    goBack: function() {
      // this.$router.go(-1)
      this.$router.back();
    },
    getGender: function() {
      common.getGender().then(res => {
        this.list.genderList = res.data;
      });
    },
    insertUser: function() {
      this.searchData.password = base64encode(this.searchData.password);
      common.registerUser(this.searchData).then(res => {
        let that = this;
        if (res.code === "0") {
          common
            .loginDo({
              accountOrEmailOrPhone: that.searchData.account,
              password: base64encode(that.searchData.password)
            })
            .then(res => {
              let token = res.data.token;
              let userInfo = res.data.userInfo;

              // localCache.clearSession();
              localCache.setCache("userInfo", userInfo);
              // localCache.setCookie("userId", userInfo.userId);
              localCache.setCookie("token", token, 8 * 60 * 60); //by seconds

              that.$router.push({ path: "/" });
            });
        }
      });
    }
  }
};
</script>

<style scoped></style>
